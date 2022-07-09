# for 和 range 的性能比较

### map range

- 和切片不同的是，迭代过程中，删除还未迭代到的键值对，则该键值对不会被迭代。

- 在迭代过程中，如果创建新的键值对，那么新增键值对，可能被迭代，也可能不会被迭代。

### channel

  ```go
  ch := make(chan string)
  go func() {
      ch <- "Go"
      ch <- "语言"
      ch <- "高性能"
      ch <- "编程"
      close(ch)
  }()
  for n := range ch {
      fmt.Println(n)
  }
  ```

  - 发送给信道(channel) 的值可以使用 for 循环迭代，直到信道被关闭。
  - 如果是 nil 信道，循环将永远阻塞。

# []struct

那如果是稍微复杂一点的 `[]struct` 类型呢？

```
type Item struct {
	id  int
	val [4096]byte
}

func BenchmarkForStruct(b *testing.B) {
	var items [1024]Item
	for i := 0; i < b.N; i++ {
		length := len(items)
		var tmp int
		for k := 0; k < length; k++ {
			tmp = items[k].id
		}
		_ = tmp
	}
}

func BenchmarkRangeIndexStruct(b *testing.B) {
	var items [1024]Item
	for i := 0; i < b.N; i++ {
		var tmp int
		for k := range items {
			tmp = items[k].id
		}
		_ = tmp
	}
}

func BenchmarkRangeStruct(b *testing.B) {
	var items [1024]Item
	for i := 0; i < b.N; i++ {
		var tmp int
		for _, item := range items {
			tmp = item.id
		}
		_ = tmp
	}
}
```

先看下 Benchmark 的结果：

```
$ go test -bench=Struct$ .
goos: darwin
goarch: amd64
pkg: example/hpg-range
BenchmarkForStruct-8             3769580               324 ns/op
BenchmarkRangeIndexStruct-8      3597555               330 ns/op
BenchmarkRangeStruct-8              2194            467411 ns/op
```

- 仅遍历下标的情况下，for 和 range 的性能几乎是一样的。
- `items` 的每一个元素的类型是一个结构体类型 `Item`，`Item` 由两个字段构成，一个类型是 int，一个是类型是 `[4096]byte`，也就是说**每个 `Item` 实例需要申请约 4KB 的内存。**
- 在这个例子中，for 的性能大约是 range (同时遍历下标和值) 的 2000 倍。

# []int 和 []struct{} 的性能差异

**与 for 不同的是，`range` 对每个迭代值都创建了一个拷贝。因此如果每次迭代的值内存占用很小的情况下，for 和 range 的性能几乎没有差异，但是如果每个迭代值内存占用很大，例如上面的例子中，每个结构体需要占据 4KB 的内存，这种情况下差距就非常明显了。**

我们可以用一个非常简单的例子来证明 range 迭代时，返回的是拷贝。

```
persons := []struct{ no int }{{no: 1}, {no: 2}, {no: 3}}
for _, s := range persons {
    s.no += 10
}
for i := 0; i < len(persons); i++ {
    persons[i].no += 100
}
fmt.Println(persons) // [{101} {102} {103}]
```

- `persons` 是一个长度为 3 的切片，每个元素是一个结构体。
- **使用 `range` 迭代时，试图将每个结构体的 no 字段增加 10，但修改无效，因为 range 返回的是拷贝。**
- 使用 `for` 迭代时，将每个结构体的 no 字段增加 100，修改有效。

# []*struct{}

那如果切片中是指针，而不是结构体呢？

```
func generateItems(n int) []*Item {
	items := make([]*Item, 0, n)
	for i := 0; i < n; i++ {
		items = append(items, &Item{id: i})
	}
	return items
}

func BenchmarkForPointer(b *testing.B) {
	items := generateItems(1024)
	for i := 0; i < b.N; i++ {
		length := len(items)
		var tmp int
		for k := 0; k < length; k++ {
			tmp = items[k].id
		}
		_ = tmp
	}
}

func BenchmarkRangePointer(b *testing.B) {
	items := generateItems(1024)
	for i := 0; i < b.N; i++ {
		var tmp int
		for _, item := range items {
			tmp = item.id
		}
		_ = tmp
	}
}
```

运行结果如下：

```
goos: darwin
goarch: amd64
pkg: example/hpg-range
BenchmarkForPointer-8             271279              4160 ns/op
BenchmarkRangePointer-8           264068              4194 ns/op
```

**切片元素从结构体 `Item` 替换为指针 `*Item` 后，for 和 range 的性能几乎是一样的。而且使用指针还有另一个好处，可以直接修改指针对应的结构体的值。**

# 总结

range 在迭代过程中返回的是迭代值的拷贝，如果每次迭代的元素的内存占用很低，那么 for 和 range 的性能几乎是一样，例如 `[]int`。但是如果迭代的元素内存占用较高，例如一个包含很多属性的 struct 结构体，那么 for 的性能将显著地高于 range，有时候甚至会有上千倍的性能差异。对于这种场景，建议使用 for，如果使用 range，建议只迭代下标，通过下标访问迭代值，这种使用方式和 for 就没有区别了。如果想使用 range 同时迭代下标和值，则需要将切片/数组的元素改为指针，才能不影响性能。