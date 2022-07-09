# 切片(slice)性能及陷阱

### 3.1 大量内存得不到释放

在已有切片的基础上进行切片，不会创建新的底层数组。因为原来的底层数组没有发生变化，内存会一直占用，直到没有变量引用该数组。因此很可能出现这么一种情况，原切片由大量的元素构成，但是我们在原切片的基础上切片，虽然只使用了很小一段，但底层数组在内存中仍然占据了大量空间，得不到释放。比较推荐的做法，使用 `copy` 替代 `re-slice`。

```
func lastNumsBySlice(origin []int) []int {
	return origin[len(origin)-2:]
}

func lastNumsByCopy(origin []int) []int {
	result := make([]int, 2)
	copy(result, origin[len(origin)-2:]) // !!!
	return result
}
```

上述两个函数的作用是一样的，取 origin 切片的最后 2 个元素。

- 第一个函数直接在原切片基础上进行切片。
- 第二个函数创建了一个新的切片，将 origin 的最后两个元素拷贝到新切片上，然后返回新切片。

我们可以写两个测试用例来比较这两种方式的性能差异：

在此之前呢，我们先实现 2 个辅助函数：

```
func generateWithCap(n int) []int {
	rand.Seed(time.Now().UnixNano())
	nums := make([]int, 0, n)
	for i := 0; i < n; i++ {
		nums = append(nums, rand.Int())
	}
	return nums
}

func printMem(t *testing.T) {
	t.Helper()
	var rtm runtime.MemStats
	runtime.ReadMemStats(&rtm)
	t.Logf("%.2f MB", float64(rtm.Alloc)/1024./1024.)
}
```

- `generateWithCap` 用于随机生成 n 个 int 整数，64位机器上，一个 int 占 8 Byte，128 * 1024 个整数恰好占据 1 MB 的空间。
- `printMem` 用于打印程序运行时占用的内存大小。

接下来分别为 `lastNumsBySlice` 和 `lastNumsByCopy` 实现测试用例：

```
func testLastChars(t *testing.T, f func([]int) []int) {
	t.Helper()
	ans := make([][]int, 0)
	for k := 0; k < 100; k++ {
		origin := generateWithCap(128 * 1024) // 1M
		ans = append(ans, f(origin))
	}
	printMem(t)
	_ = ans
}

func TestLastCharsBySlice(t *testing.T) { testLastChars(t, lastNumsBySlice) }
func TestLastCharsByCopy(t *testing.T)  { testLastChars(t, lastNumsByCopy) }
```

- **测试用例内容非常简单，随机生成一个大小为 1 MB 的切片( 128*1024 个 int 整型，恰好为 1 MB)。**
- 分别调用 `lastNumsBySlice` 和 `lastNumsByCopy` 取切片的最后两个元素。
- 最后然后打印程序所占用的内存。

运行结果如下：

```
$ go test -run=^TestLastChars  -v
=== RUN   TestLastCharsBySlice
--- PASS: TestLastCharsBySlice (0.31s)
    slice_test.go:73: 100.14 MB
=== RUN   TestLastCharsByCopy
--- PASS: TestLastCharsByCopy (0.28s)
    slice_test.go:74: 3.14 MB
PASS
ok      example 0.601s
```

**结果差异非常明显，`lastNumsBySlice` 耗费了 100.14 MB 内存，也就是说，申请的 100 个 1 MB 大小的内存没有被回收。因为切片虽然只使用了最后 2 个元素，但是因为与原来 1M 的切片引用了相同的底层数组，底层数组得不到释放，因此，最终 100 MB 的内存始终得不到释放。**而 `lastNumsByCopy` 仅消耗了 3.14 MB 的内存。这是因为，**通过 `copy`，指向了一个新的底层数组，当 origin 不再被引用后，内存会被垃圾回收(garbage collector, GC)。**

**如果我们在循环中，显示地调用 `runtime.GC()`，效果会更加地明显：**

```
func testLastChars(t *testing.T, f func([]int) []int) {
	t.Helper()
	ans := make([][]int, 0)
	for k := 0; k < 100; k++ {
		origin := generateWithCap(128 * 1024) // 1M
		ans = append(ans, f(origin))
		runtime.GC()
	}
	printMem(t)
	_ = ans
}
```

`lastNumsByCopy` 内存占用直接下降到 0.15 MB。

```
$ go test -run=^TestLastChars  -v
=== RUN   TestLastCharsBySlice
--- PASS: TestLastCharsBySlice (0.37s)
    slice_test.go:75: 100.14 MB
=== RUN   TestLastCharsByCopy
--- PASS: TestLastCharsByCopy (0.34s)
    slice_test.go:76: 0.15 MB
PASS
ok      example 0.723s
```