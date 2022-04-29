# 470. 用 Rand7() 实现 Rand10()
给定方法 rand7 可生成 [1,7] 范围内的均匀随机整数，试写一个方法 rand10 生成 [1,10] 范围内的均匀随机整数。

你只能调用 rand7() 且不能调用其他方法。请不要使用系统的 Math.random() 方法。

每个测试用例将有一个内部参数 n，即你实现的函数 rand10() 在测试时将被调用的次数。请注意，这不是传递给 rand10() 的参数。

**示例:**
```
输入: 3
输出: [3,8,10]
```

## 拒绝采样
```go
func rand10() int {
    for {
        row, col := rand7(), rand7()
        idx := (row-1)*7 + col
        if idx <= 40 {
            return 1 + (idx-1)%10
        }
    }
}
```

**优化**
```go
func rand10() int {
    for {
        a, b := rand7(), rand7()
        idx := (a-1)*7 + b
        if idx <= 40 {  // 采样空间为40
            return 1 + (idx-1)%10
        }
        a = idx - 40
        b = rand7()
        idx = (a-1)*7 + b
        if idx <= 60 {  // 采样空间为60
            return 1 + (idx-1)%10
        }
        a = idx - 60
        b = rand7()
        idx = (a-1)*7 + b
        if idx <= 20 {  // 采样空间为20
            return 1 + (idx-1)%10
        }
    }
}
```