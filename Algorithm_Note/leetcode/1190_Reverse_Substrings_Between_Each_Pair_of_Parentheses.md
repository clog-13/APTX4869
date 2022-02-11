# 1190. Reverse Substrings Between Each Pair of Parentheses

You are given a string `s` that consists of lower case English letters and brackets.

Reverse the strings in each pair of matching parentheses, starting from the innermost one.

Your result should **not** contain any brackets.

 

**Example 1:**

```
Input: s = "(abcd)"
Output: "dcba"
```

**Example 2:**

```
Input: s = "(u(love)i)"
Output: "iloveu"
Explanation: The substring "love" is reversed first, then the whole string is reversed.
```



## Stack

```go
func reverseParentheses(s string) string {
    stack := [][]byte{}
    str := []byte{}
    for i := range s {
        if s[i] == '(' {
            stack = append(stack, str)
            str = []byte{}
        } else if s[i] == ')' {
            for j, n := 0, len(str); j < n/2; j++ {  // swap
                str[j], str[n-1-j] = str[n-1-j], str[j]
            }
            str = append(stack[len(stack)-1], str...)  // insert(0, stack.pop())
            stack = stack[:len(stack)-1]
        } else {
            str = append(str, s[i])
        }
    }
    return string(str)
}
```



## Simulate LinkedList

```go
func reverseParentheses(s string) string {
    n := len(s)
    next := make([]int, n)
    stack := []int{}
    for i, b := range s {
        if b == '(' {
            stack = append(stack, i)
        } else if b == ')' {
            j := stack[len(stack)-1]
            stack = stack[:len(stack)-1]
            next[i], next[j] = j, i
        }
    }

    res := []byte{}
    for i, dir := 0, 1; i < n; i += dir {
        if s[i] == '(' || s[i] == ')' {
            i = next[i]
            dir = -dir
        } else {
            res = append(res, s[i])
        }
    }
    return string(res)
}
```

