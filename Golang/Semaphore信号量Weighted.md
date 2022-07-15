# P/V 操作

Dijkstra 在他的论文中为信号量定义了两个操作 P 和 V。P 操作（descrease、wait、acquire）是减少信号量的计数值，而 V 操作（increase、signal、release）是增加信号量的计数值。

如果信号量蜕变成二进位信号量，那么，它的 P/V 就和互斥锁的 Lock/Unlock 一样了。

# Go 官方扩展库的实现

在运行时，Go 内部使用信号量来控制 goroutine 的阻塞和唤醒。我们在学习基本并发原语的实现时也看到了，比如互斥锁的第二个字段：

```go
type Mutex struct {    
    state int32    
    sema  uint32
}
```

信号量的 P/V 操作是通过函数实现的：

```go
func runtime_Semacquire(s *uint32)
func runtime_SemacquireMutex(s *uint32, lifo bool, skipframes int)
func runtime_Semrelease(s *uint32, handoff bool, skipframes int)
```

遗憾的是，它是 Go 运行时内部使用的，并没有封装暴露成一个对外的信号量并发原语，原则上我们没有办法使用。不过没关系，Go 在它的扩展包中提供了信号量semaphore，不过这个信号量的类型名并不叫 Semaphore，而是叫 Weighted。之所以叫做 Weighted，我想，应该是因为可以在初始化创建这个信号量的时候设置权重（初始化的资源数），其实我觉得叫 Semaphore 或许会更好。

![img](https://static001.geekbang.org/resource/image/1a/b0/1a13a551346cd6b910f38f5ed2bfc6b0.png?wh=702*174)

我们来分析下这个信号量的几个实现方法。

Acquire 方法：相当于 P 操作，你可以一次获取多个资源，如果没有足够多的资源，调用者就会被阻塞。它的第一个参数是 Context，**可以通过 Context 增加超时或者 cancel 的机制。如果是正常获取了资源，就返回 nil；否则，就返回 ctx.Err()，信号量不改变。**

Release 方法：相当于 V 操作，可以将 n 个资源释放，返还给信号量。

TryAcquire 方法：**尝试获取 n 个资源，但是它不会阻塞，要么成功获取 n 个资源，返回 true，要么一个也不获取，返回 false。**

# Worker Pool 的例子:

```go

var (
    maxWorkers = runtime.GOMAXPROCS(0)                    // worker数量
    sema       = semaphore.NewWeighted(int64(maxWorkers)) //信号量
    task       = make([]int, maxWorkers*4)                // 任务数，是worker的四倍
)

func main() {
    ctx := context.Background()

    for i := range task {
        // 如果没有worker可用，会阻塞在这里，直到某个worker被释放
        if err := sema.Acquire(ctx, 1); err != nil {
            break
        }

        // 启动worker goroutine
        go func(i int) {
            defer sema.Release(1)
            time.Sleep(100 * time.Millisecond) // 模拟一个耗时操作
            task[i] = i + 1
        }(i)
    }

    // 请求所有的worker,这样能确保前面的worker都执行完
    if err := sema.Acquire(ctx, int64(maxWorkers)); err != nil {
        log.Printf("获取所有的worker失败: %v", err)
    }

    fmt.Println(task)
}
```

如果在实际应用中，你想等所有的 Worker 都执行完，就可以获取最大计数值的信号量。

# Weighted

Go 扩展库中的信号量是使用互斥锁 +List 实现的。

互斥锁实现其它字段的保护，而 List 实现了一个等待队列，等待者的通知是通过 Channel 的通知机制实现的。

信号量 Weighted 的数据结构：

```go

type Weighted struct {
    size    int64         
    cur     int64         
    mu      sync.Mutex    
    waiters list.List     // 等待队列
}
```

Acquire 不仅仅要监控资源是否可用，而且还要检测 Context 的 Done 是否已关闭。

```go
func (s *Weighted) Acquire(ctx context.Context, n int64) error {
	s.mu.Lock()
	// fast path, 如果有足够的资源，都不考虑ctx.Done的状态，将cur加上n就返回
	if s.size-s.cur >= n && s.waiters.Len() == 0 {
		s.cur += n
		s.mu.Unlock()
		return nil
	}

	// 如果是不可能完成的任务，请求的资源数大于能提供的最大的资源数
	if n > s.size {
		s.mu.Unlock()
		// 依赖ctx的状态返回，否则一直等待
		<-ctx.Done()
		return ctx.Err()
	}

	// 否则就需要把调用者加入到等待队列中
	// 创建了一个ready chan,以便被通知唤醒
	ready := make(chan struct{})
	w := waiter{n: n, ready: ready}
	elem := s.waiters.PushBack(w)
	s.mu.Unlock()

	// 等待
	select {
	case <-ctx.Done(): // context的Done被关闭
		err := ctx.Err()
		s.mu.Lock()
		select {
		case <-ready: // 如果被唤醒了，忽略ctx的状态
			err = nil
		default:
			// 通知waiter
			isFront := s.waiters.Front() == elem
			s.waiters.Remove(elem)
			// 通知其它的waiters,检查是否有足够的资源
			if isFront && s.size > s.cur {
				s.notifyWaiters()
			}
		}
		s.mu.Unlock()
		return err
	case <-ready: // !!! 被唤醒了
		return nil
	}
}
```

为了提高性能，这个方法中的 fast path 之外的代码，可以抽取成 acquireSlow 方法，以便其它 Acquire 被内联。

Release 方法将当前计数值减去释放的资源数 n，并唤醒等待队列中的调用者，看是否有足够的资源被获取。

```go
func (s *Weighted) Release(n int64) {
	s.mu.Lock()
	s.cur -= n
	if s.cur < 0 {
		s.mu.Unlock()
		panic("semaphore: released more than held")
	}
	s.notifyWaiters()
	s.mu.Unlock()
}
```

notifyWaiters 方法就是逐个检查等待的调用者，如果资源不够，或者是没有等待者了，就返回：

```go
func (s *Weighted) notifyWaiters() {
	for {
		next := s.waiters.Front()
		if next == nil {
			break // No more waiters blocked.
		}

		w := next.Value.(waiter)
		if s.size-s.cur < w.n {
			// 避免饥饿，这里还是按照先入先出的方式处理
			break
		}

		s.cur += w.n
		s.waiters.Remove(next)
		close(w.ready)
	}
}
```

notifyWaiters 方法是按照先入先出的方式唤醒调用者。当释放 100 个资源的时候，如果第一个等待者需要 101 个资源，那么，队列中的所有等待者都会继续等待，即使有的等待者只需要 1 个资源。这样做的目的是避免饥饿，否则的话，资源可能总是被那些请求资源数小的调用者获取，这样一来，请求资源数巨大的调用者，就没有机会获得资源了。

# 使用信号量的常见错误

保证信号量不出错的前提是正确地使用它，否则，公平性和安全性就会受到损害，导致程序 panic。

在使用信号量时，最常见的几个错误如下：

- 请求了资源，但是忘记释放它；

- 释放了从未请求的资源；长时间持有一个资源，即使不需要它；

- 不持有一个资源，却直接使用它。


即使你规避了这些坑，在同时使用多种资源，不同的信号量控制不同的资源的时候，也可能会出现死锁现象，比如哲学家就餐问题。

就 Go 扩展库实现的信号量来说，在调用 Release 方法的时候，你可以传递任意的整数。但是，如果你传递一个比请求到的数量大的错误的数值，程序就会 panic。如果传递一个负数，会导致资源永久被持有。如果你请求的资源数比最大的资源数还大，那么，调用者可能永远被阻塞。

所以，使用信号量遵循的原则就是请求多少资源，就释放多少资源。你一定要注意，必须使用正确的方法传递整数，不要“耍小聪明”，而且，请求的资源数一定不要超过最大资源数。



# 其它信号量的实现

除了官方扩展库的实现，实际上，我们还有很多方法实现信号量，比较典型的就是使用 Channel 来实现。

根据之前的 Channel 类型的介绍以及 Go 内存模型的定义，使用一个 buffer 为 n 的 Channel 很容易实现信号量。

比如下面的代码，我们就是使用 chan struct{}类型来实现的。在初始化这个信号量的时候，我们设置它的初始容量，代表有多少个资源可以使用。它使用 Lock 和 Unlock 方法实现请求资源和释放资源，正好实现了 Locker 接口。

```go
// Semaphore 数据结构，并且还实现了Locker接口
type semaphore struct {
	ch chan struct{}
}

// 创建一个新的信号量
func NewSemaphore(capacity int) sync.Locker {
	if capacity <= 0 {
		capacity = 1 // 容量为1就变成了一个互斥锁
	}
	return &semaphore{ch: make(chan struct{}, capacity)}
}

// 请求一个资源
func (s *semaphore) Lock() {
	s.ch <- struct{}{}
}

// 释放资源
func (s *semaphore) Unlock() {
	<-s.ch
}
```

官方的实现方式有这样一个功能：**它可以一次请求多个资源，这是通过 Channel 实现的信号量所不具备的。**

除了 Channel，marusama/semaphore也实现了一个可以动态更改资源容量的信号量，也是一个非常有特色的实现。**如果你的资源数量并不是固定的，而是动态变化的，我建议你考虑一下这个信号量库。**总结