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

# 空的interface（`runtime.eface`）

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

### 非空`interface`

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