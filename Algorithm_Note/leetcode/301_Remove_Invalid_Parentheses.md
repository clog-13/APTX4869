# 301. 删除无效的括号

给你一个由若干括号和字母组成的字符串 `s` ，删除最小数量的无效括号，使得输入的字符串有效。

返回所有可能的结果。答案可以按 **任意顺序** 返回。

 

**示例 1：**

```
输入：s = "()())()"
输出：["(())()","()()()"]
```

**示例 2：**

```
输入：s = "(a)())()"
输出：["(a())()","(a)()()"]
```



## 回溯+剪枝

```go
func removeInvalidParentheses(s string) (res []string) {
    lrm, rrm := 0, 0
    for _, ch := range s {
        if ch == '(' {
            lrm++
        } else if ch == ')' {
            if lrm > 0 {
                lrm--
            } else {
                rrm++
            }
        }
    }

    helper(&res, s, 0, lrm, rrm)
    return
}

func helper(res *[]string, str string, start, lrm, rrm int) {
    if lrm == 0 && rrm == 0 {
        if isValid(str) {
            *res = append(*res, str)
        }
        return
    }

    for i := start; i < len(str); i++ {
        if i != start && str[i] == str[i-1] {
            continue
        }
        // 如果剩余的字符无法满足去掉的数量要求，直接返回
        if lrm+rrm > len(str)-i {
            return
        }
        // 尝试去掉一个左括号
        if lrm > 0 && str[i] == '(' {
            helper(res, str[:i]+str[i+1:], i, lrm-1, rrm)
        }
        // 尝试去掉一个右括号
        if rrm > 0 && str[i] == ')' {
            helper(res, str[:i]+str[i+1:], i, lrm, rrm-1)
        }
    }
}

func isValid(str string) bool {
    cnt := 0
    for _, ch := range str {
        if ch == '(' {
            cnt++
        } else if ch == ')' {
            cnt--
            if cnt < 0 {
                return false
            }
        }
    }
    return cnt == 0
}
```



## BFS

```go
func removeInvalidParentheses(s string) (res []string) {
    curSet := map[string]struct{}{s: {}}
    for {
        for str := range curSet {
            if isValid(str) {
                res = append(res, str)
            }
        }
        if len(res) > 0 {  // 要求最少删除
            return
        }
        nexSet := map[string]struct{}{}
        for str := range curSet {
            for i, ch := range str {
                // if i > 0 && byte(ch) == str[i-1] {
                //     continue
                // }
                if ch == '(' || ch == ')' {
                    nexSet[str[:i]+str[i+1:]] = struct{}{}
                }
            }
        }
        curSet = nexSet
    }
}

func isValid(str string) bool {
    cnt := 0
    for _, ch := range str {
        if ch == '(' {
            cnt++
        } else if ch == ')' {
            cnt--
            if cnt < 0 {
                return false
            }
        }
    }
    return cnt == 0
}
```



## 状态枚举

```go
package main

import "math/bits"

func removeInvalidParentheses(s string) (res []string) {
	var lIdx, rIdx []int
	lrm, rrm := 0, 0
	for i, ch := range s {
		if ch == '(' {
			lIdx = append(lIdx, i)
			lrm++
		} else if ch == ')' {
			rIdx = append(rIdx, i)
			if lrm == 0 {
				rrm++
			} else {
				lrm--
			}
		}
	}

	var lst, rst []int
	for i := 0; i < 1<<len(lIdx); i++ {
		if bits.OnesCount(uint(i)) == lrm {
			lst = append(lst, i)
		}
	}
	for i := 0; i < 1<<len(rIdx); i++ {
		if bits.OnesCount(uint(i)) == rrm {
			rst = append(rst, i)
		}
	}

	set := map[string]struct{}{}
	for _, lmask := range lst {
		for _, rmask := range rst {
			if checkValid(s, lmask, rmask, lIdx, rIdx) {
				set[recoverStr(s, lmask, rmask, lIdx, rIdx)] = struct{}{}
			}
		}
	}
	for str := range set {
		res = append(res, str)
	}
	return
}

func checkValid(str string, lmask, rmask int, lIdx, rIdx []int) bool {
	cnt, pos1, pos2 := 0, 0, 0
	for i := range str {
		if pos1 < len(lIdx) && i == lIdx[pos1] {
			if lmask>>pos1&1 == 0 {
				cnt++
			}
			pos1++
		} else if pos2 < len(rIdx) && i == rIdx[pos2] {
			if rmask>>pos2&1 == 0 {
				cnt--
				if cnt < 0 {
					return false
				}
			}
			pos2++
		}
	}
	return cnt == 0
}

func recoverStr(str string, lmask, rmask int, lIdx, rIdx []int) string {
	var res []rune
	pos1, pos2 := 0, 0
	for i, ch := range str {
		if pos1 < len(lIdx) && i == lIdx[pos1] {
			if lmask>>pos1&1 == 0 {
				res = append(res, ch)
			}
			pos1++
		} else if pos2 < len(rIdx) && i == rIdx[pos2] {
			if rmask>>pos2&1 == 0 {
				res = append(res, ch)
			}
			pos2++
		} else {
			res = append(res, ch)
		}
	}
	return string(res)
}
```

