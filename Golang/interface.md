# interface

`iface` 和 `eface` 都是 Go 中描述接口的底层结构体，区别在于 `iface` 描述的接口包含方法，而 `eface` 则是不包含任何方法的空接口：`interface{}`。

`iface` 内部维护两个指针，**`tab` 指向一个 `itab` 实体， 它表示接口的类型以及赋给这个接口的实体类型。`data` 则指向接口具体的值，一般而言是一个指向堆内存的指针。**

```golang
type iface struct {
	tab  *itab
	data unsafe.Pointer
}

type itab struct {
	inter  *interfacetype
	_type  *_type
	link   *itab
	hash   uint32 // copy of _type.hash. Used for type switches.
	bad    bool   // type does not implement interface
	inhash bool   // has this itab been added to hash?
	unused [2]byte
	fun    [1]uintptr // variable sized
}
```

再来仔细看一下 `itab` 结构体：`_type` 字段描述了实体的类型，包括内存对齐方式，大小等；`inter` 字段则描述了接口的类型。`fun` 字段放置和接口方法对应的具体数据类型的方法地址，实现接口调用方法的动态分派，一般在每次给接口赋值发生转换时会更新此表，或者直接拿缓存的 itab。

这里只会列出实体类型和接口相关的方法，实体类型的其他方法并不会出现在这里。如果你学过 C++ 的话，这里可以类比虚函数的概念。

另外，你可能会觉得奇怪，为什么 `fun` 数组的大小为 1，要是接口定义了多个方法可怎么办？实际上，这里存储的是第一个方法的函数指针，如果有更多的方法，在它之后的内存空间里继续存储。从汇编角度来看，通过增加地址就能获取到这些函数指针，没什么影响。顺便提一句，这些方法是按照函数名称的字典序进行排列的。

再看一下 `interfacetype` 类型，它描述的是接口的类型：

```golang
type interfacetype struct {
	typ     _type
	pkgpath name
	mhdr    []imethod
}
```

可以看到，它包装了 `_type` 类型，`_type` 实际上是描述 Go 语言中各种数据类型的结构体。我们注意到，这里还包含一个 `mhdr` 字段，表示接口所定义的函数列表， `pkgpath` 记录定义了接口的包名。

这里通过一张图来看下 `iface` 结构体的全貌：

![iface 结构体全景](https://golang.design/go-questions/interface/assets/0.png)

接着来看一下 `eface` 的源码：

```golang
type eface struct {
    _type *_type
    data  unsafe.Pointer
}
```

相比 `iface`，`eface` 就比较简单了。只维护了一个 `_type` 字段，表示空接口所承载的具体的实体类型。`data` 描述了具体的值。

![eface 结构体全景](https://golang.design/go-questions/interface/assets/1.png)

我们来看个例子：

```golang
func main() {
	x := 200
	var any interface{} = x
	fmt.Println(any)

	g := Gopher{"Go"}
	var c coder = g
	fmt.Println(c)
}

type coder interface {
	code()
	debug()
}

type Gopher struct {
	language string
}

func (p Gopher) code() {
	fmt.Printf("I am coding %s language\n", p.language)
}

func (p Gopher) debug() {
	fmt.Printf("I am debuging %s language\n", p.language)
}
```

执行命令，打印出汇编语言：

```shell
go tool compile -S ./src/main.go
```

可以看到，main 函数里调用了两个函数：

```shell
func convT2E64(t *_type, elem unsafe.Pointer) (e eface)
func convT2I(tab *itab, elem unsafe.Pointer) (i iface)
```

上面两个函数的参数和 `iface` 及 `eface` 结构体的字段是可以联系起来的：两个函数都是将参数`组装`一下，形成最终的接口。

作为补充，我们最后再来看下 `_type` 结构体：

```golang
type _type struct {
    // 类型大小
	size       uintptr
    ptrdata    uintptr
    // 类型的 hash 值
    hash       uint32
    // 类型的 flag，和反射相关
    tflag      tflag
    // 内存对齐相关
    align      uint8
    fieldalign uint8
    // 类型的编号，有bool, slice, struct 等等等等
	kind       uint8
	alg        *typeAlg
	// gc 相关
	gcdata    *byte
	str       nameOff
	ptrToThis typeOff
}
```

Go 语言各种数据类型都是在 `_type` 字段的基础上，增加一些额外的字段来进行管理的：

```golang
type arraytype struct {
	typ   _type
	elem  *_type
	slice *_type
	len   uintptr
}

type chantype struct {
	typ  _type
	elem *_type
	dir  uintptr
}

type slicetype struct {
	typ  _type
	elem *_type
}

type structtype struct {
	typ     _type
	pkgPath name
	fields  []structfield
}
```

这些数据类型的结构体定义，是反射实现的基础。

# interface内部结构

Go 语言根据接口类型是否包含一组方法将接口类型分成了两类：

- 使用 `runtime.iface` 结构体表示包含方法的接口
- 使用 `runtime.eface` 结构体表示不包含任何方法的 `interface{}` 类型；

`runtime.iface`结构体在`Go`语言中的定义是这样的：

```go
type eface struct { // 16 字节
    _type *_type
    data  unsafe.Pointer
}
```

这里只包含指向底层数据和类型的两个指针，从这个`type`我们也可以推断出Go语言的任意类型都可以转换成`interface`。

另一个用于表示接口的结构体是 `runtime.iface`，这个结构体中有指向原始数据的指针 `data`，不过更重要的是 `runtime.itab` 类型的 `tab` 字段。

```go
type iface struct { // 16 字节
    tab  *itab
    data unsafe.Pointer
}
```

## runtime._type

`runtime._type`是 Go 语言类型的**运行时表示**。下面是运行时包中的结构体，其中包含了很多类型的元信息，例如：类型的大小、哈希、对齐以及种类等。

```
type _type struct {
    size       uintptr
    ptrdata    uintptr
    hash       uint32
    tflag      tflag
    align      uint8
    fieldAlign uint8
    kind       uint8
    equal      func(unsafe.Pointer, unsafe.Pointer) bool
    gcdata     *byte
    str        nameOff
    ptrToThis  typeOff
}
```

这里我只对几个比较重要的字段进行讲解：

- `size` 字段存储了类型占用的内存空间，为内存空间的分配提供信息；
- `hash` 字段能够帮助我们快速确定**类型是否相等；**
- `equal` 字段用于判断**当前类型的多个对象是否相等**，该字段是为了减少 Go 语言二进制包大小从 `typeAlg` 结构体中迁移过来的)；

## runtime.itab

`runtime.itab`结构体是接口类型的核心组成部分，**每一个 `runtime.itab` 都占 32 字节**，我们可以将其看成**接口类型和具体类型的组合**，它们分别用 `inter` 和 `_type` 两个字段表示：

```
type itab struct { // 32 字节
    inter *interfacetype
    _type *_type
    hash  uint32
    _     [4]byte
    fun   [1]uintptr
}
```

`inter`和`_type`是用于表示类型的字段，`hash`是对`_type.hash`的拷贝，当我们想将 `interface` 类型转换成具体类型时，可以使用该字段快速判断目标类型和具体类型 `runtime._type`是否一致，`fun`是一个动态大小的数组，它是一个用于动态派发的虚函数表，存储了一组函数指针。虽然该变量被声明成大小固定的数组，但是在使用时会通过原始指针获取其中的数据，所以 `fun` 数组中保存的元素数量是不确定的；

## 空的interface（`runtime.eface`）

定义函数入参如下：

```
func doSomething(v interface{}){    
}
```

这个函数的入参是`interface`类型，要注意的是，`interface`类型不是任意类型，他与C语言中的`void *`不同，如果我们将类型转换成了 `interface{}` 类型，变量在运行期间的类型也会发生变化，获取变量类型时会得到 `interface{}`，**之所以函数可以接受任何类型是在 go 执行时传递到函数的任何类型都被自动转换成 `interface{}`。**

既然空的 interface 可以接受任何类型的参数，那么一个 `interface{}`类型的 slice 是不是就可以接受任何类型的 slice ？下面我们就来尝试一下：

```
import (
 	"fmt"
)

func printStr(str []interface{}) {
    for _, val := range str {
    	fmt.Println(val)
    }
}

func main(){
 	names := []string{"stanley", "david", "oscar"}
 	printStr(names)
}
```

运行上面代码，会出现如下错误：`./main.go:15:10: cannot use names (type []string) as type []interface {} in argument to printStr`。

这里我也是很疑惑，为什么`Go`没有帮助我们自动把`slice`转换成`interface`类型的`slice`，这里简单总结一下：**`interface`会占用两个字长的存储空间，一个是自身的 methods 数据，一个是指向其存储值的指针，也就是 interface 变量存储的值，因而 slice []interface{} 其长度是固定的`N*2`，但是 []T 的长度是`N*sizeof(T)`，两种 slice 实际存储值的大小是有区别的。**

既然这种方法行不通，那可以怎样解决呢？我们可以直接使用元素类型是interface的切片。

```
var dataSlice []int = foo()
var interfaceSlice []interface{} = make([]interface{}, len(dataSlice))
for i, d := range dataSlice {
    interfaceSlice[i] = d
}
```

## 非空`interface`

`Go`语言实现接口时，既可以结构体类型的方法也可以是使用指针类型的方法。`Go`语言中并没有严格规定实现者的方法是值类型还是指针，那我们猜想一下，如果同时使用值类型和指针类型方法实现接口，会有什么问题吗？

先看这样一个例子：

```
package main

import (
    "fmt"
)

type Person interface {
    GetAge () int
    SetAge (int)
}


type Man struct {
    Name string
    Age int
}

func(s Man) GetAge()int {
	return s.Age
}

func(s *Man) SetAge(age int) {
	s.Age = age
}


func f(p Person){
    p.SetAge(10)
    fmt.Println(p.GetAge())
}

func main() {
    p := Man{}
    f(&p) 
}
```

看上面的代码，大家对`f(&p)`这里的入参是否会有疑问呢？如果不取地址，直接传过去会怎么样？试了一下，编译错误如下：`./main.go:34:3: cannot use p (type Man) as type Person in argument to f: Man does not implement Person (SetAge method has pointer receiver)`。透过注释我们可以看到，因为`SetAge`方法的`receiver`是指针类型，那么传递给`f`的是`P`的一份拷贝，在进行`p`的拷贝到`person`的转换时，`p`的拷贝是不满足`SetAge`方法的`receiver`是个指针类型，这也正说明一个问题**go中函数都是按值传递**。

上面的例子是因为发生了值传递才会导致出现这个问题。**实际上不管接收者类型是值类型还是指针类型，都可以通过值类型或指针类型调用，这里面实际上通过语法糖起作用的。实现了接收者是值类型的方法，相当于自动实现了接收者是指针类型的方法；而实现了接收者是指针类型的方法，不会自动生成对应接收者是值类型的方法。**

举个例子：

```
type Animal interface {
    Walk()
    Eat()
}


type Dog struct {
    Name string
}

func (d *Dog)Walk()  {
    fmt.Println("go")
}

func (d *Dog)Eat()  {
    fmt.Println("eat shit")
}

func main() {
    var d Animal = &Dog{"nene"}
    d.Eat()
    d.Walk()
}
```

上面定义了一个接口`Animal`，接口定义了两个函数：

```
Walk()
Eat()
```

接着定义了一个结构体`Dog`，他实现了两个方法，一个是值接受者，一个是指针接收者。我们通过接口类型的变量调用了定义的两个函数是没有问题的，如果我们改成这样呢：

```
func main() {
    var d Animal = Dog{"nene"}
    d.Eat()
    d.Walk()
}
```

这样直接就会报错，我们只改了一部分，第一次将`&Dog{"nene"}`赋值给了`d`；第二次则将`Dog{"nene"}`赋值给了`d`。第二次报错是因为，`d`没有实现`Animal`。这正解释了上面的结论，所以，当实现了一个接收者是值类型的方法，就可以自动生成一个接收者是对应指针类型的方法，因为两者都不会影响接收者。但是，当实现了一个接收者是指针类型的方法，如果此时自动生成一个接收者是值类型的方法，原本期望对接收者的改变（通过指针实现），现在无法实现，因为值类型会产生一个拷贝，不会真正影响调用者。

总结一句话就是：**如果实现了接收者是值类型的方法，会隐含地也实现了接收者是指针类型的方法。**



# 接口类型和 `nil` 作比较

`iface`包含两个字段：`tab` 是接口表指针，指向类型信息；`data` 是数据指针，则指向具体的数据。它们分别被称为`动态类型`和`动态值`。而接口值包括`动态类型`和`动态值`。

## 接口类型和 `nil` 作比较

**接口值的零值是指`动态类型`和`动态值`都为 `nil`。当仅且当这两部分的值都为 `nil` 的情况下，这个接口值就才会被认为 `接口值 == nil`。**

来看个例子：

```golang
type Coder interface {
	code()
}

type Gopher struct {
	name string
}

func (g Gopher) code() {
	fmt.Printf("%s is coding\n", g.name)
}

func main() {
	var c Coder
	fmt.Println(c == nil)
	fmt.Printf("c: %T, %v\n", c, c)

	var g *Gopher
	fmt.Println(g == nil)

	c = g
	fmt.Println(c == nil)
	fmt.Printf("c: %T, %v\n", c, c)
}
```
输出：

```shell
true
c: <nil>, <nil>
true
false
c: *main.Gopher, <nil>
```

一开始，`c` 的 动态类型和动态值都为 `nil`，`g` 也为 `nil`，当把 `g` 赋值给 `c` 后，`c` 的**动态类型**变成了 `*main.Gopher`，仅管 `c` 的**动态值**仍为 `nil`，但是当 `c` 和 `nil` 作比较的时候，结果就是 `false` 了。

## 例子：

```golang
type MyError struct {}

func (i MyError) Error() string {
	return "MyError"
}

func main() {
	err := Process()
	fmt.Println(err)

	fmt.Println(err == nil)
}

func Process() error {
	var err *MyError = nil
	return err
}
```

函数运行结果：

```shell
<nil>
false
```

这里先定义了一个 `MyError` 结构体，实现了 `Error` 函数，也就实现了 `error` 接口。`Process` 函数返回了一个 `error` 接口，这块隐含了类型转换。所以，虽然它的值是 `nil`，**其实它的类型是 `*MyError`**，最后和 `nil` 比较的时候，结果为 `false`。

## 如何打印出接口的动态类型和值？

直接看代码：

```golang
type iface struct {
	itab, data uintptr
}

func main() {
	var a interface{} = nil

	var b interface{} = (*int)(nil)

	x := 5
	var c interface{} = (*int)(&x)
	
	ia := *(*iface)(unsafe.Pointer(&a))
	ib := *(*iface)(unsafe.Pointer(&b))
	ic := *(*iface)(unsafe.Pointer(&c))

	fmt.Println(ia, ib, ic)

	fmt.Println(*(*int)(unsafe.Pointer(ic.data)))
}
```

代码里直接定义了一个 `iface` 结构体，用两个指针来描述 `itab` 和 `data`，之后将 a, b, c 在内存中的内容强制解释成我们自定义的 `iface`。**最后就可以打印出动态类型和动态值的地址。**

运行结果如下：

```shell
{0 0} {17426912 0} {17426912 842350714568}
5
```

a 的动态类型和动态值的地址均为 0，也就是 nil；b 的动态类型和 c 的动态类型一致，都是 `*int`；最后，c 的动态值为 5。



# 编译器自动检测类型是否实现接口

经常看到一些开源库里会有一些类似下面这种奇怪的用法：

```golang
var _ io.Writer = (*myWriter)(nil)
```

这时候会有点懵，不知道作者想要干什么，实际上这就是此问题的答案。编译器会由此检查 `*myWriter` 类型是否实现了 `io.Writer` 接口。

来看一个例子：

```golang
type myWriter struct {

}

/*func (w myWriter) Write(p []byte) (n int, err error) {
	return
}*/

func main() {
    // 检查 *myWriter 类型是否实现了 io.Writer 接口
    var _ io.Writer = (*myWriter)(nil)

    // 检查 myWriter 类型是否实现了 io.Writer 接口
    var _ io.Writer = myWriter{}
}
```

注释掉为 myWriter 定义的 Write 函数后，运行程序：

```golang
src/main.go:14:6: cannot use (*myWriter)(nil) (type *myWriter) as type io.Writer in assignment:
	*myWriter does not implement io.Writer (missing Write method)
src/main.go:15:6: cannot use myWriter literal (type myWriter) as type io.Writer in assignment:
	myWriter does not implement io.Writer (missing Write method)
```

报错信息：*myWriter/myWriter 未实现 io.Writer 接口，也就是未实现 Write 方法。

解除注释后，运行程序不报错。

实际上，上述赋值语句会发生隐式地类型转换，在转换的过程中，编译器会检测等号右边的类型是否实现了等号左边接口所规定的函数。

总结一下，可通过在代码中添加类似如下的代码，用来检测类型是否实现了接口：

```golang
var _ io.Writer = (*myWriter)(nil)
var _ io.Writer = myWriter{}
```