# 5. Longest Palindromic Substring

Given a string `s`, return *the longest palindromic substring* in `s`.

 

**Example 1:**

```
Input: s = "babad"
Output: "bab"
Explanation: "aba" is also a valid answer.
```

**Example 2:**

```
Input: s = "cbbd"
Output: "bb"
```



## Manacher

```go
func longestPalindrome(s string) string {
	start, end := 0, -1
	t := "#"
	for i := 0; i < len(s); i++ {
		t += string(s[i]) + "#"
	}
	t += "#"
	s = t
	
    /** 当在位置 i 开始进行中心拓展时，我们可以先找到 i 关于 j 的对称点 2 * j - i
    那么如果点 2 * j - i 的臂长等于 n，就可以知道，点 i 的臂长至少为 min(j + length - i, n)
    那么我们就可以直接跳过 i 到 i + min(j + length - i, n) 这部分
    从 i + min(j + length - i, n) + 1 开始拓展。
    */
    arm_len := []int{}
	ri, j := -1, -1
	for i := 0; i < len(s); i++ {
		var cur_arm_len int
		if ri >= i {
			i_sym := j*2 - i
			min_arm_len := min(arm_len[i_sym], ri-i)
			cur_arm_len = expand(s, i-min_arm_len, i+min_arm_len)
		} else {
			cur_arm_len = expand(s, i, i)
		}
		
        arm_len = append(arm_len, cur_arm_len)
		if i + cur_arm_len > ri {
			ri = i + cur_arm_len
			j = i
		}

		if cur_arm_len*2+1 > end-start {
			start = i - cur_arm_len
			end = i + cur_arm_len
		}
	}
    
	res := ""
	for i := start; i <= end; i++ {
		if s[i] != '#' {
			res += string(s[i])
		}
	}
	return res
}

func expand(s string, le, ri int) int {
	for le >= 0 && ri < len(s) && s[le] == s[ri] {
        le, ri = le-1, ri+1
	}
	return (ri - le - 2) / 2
}

func min(x, y int) int {
	if x < y {
		return x
	}
	return y
}
```



## 中心拓展算法

```go
func longestPalindrome(s string) string {
	if s == "" {
		return ""
	}
	start, end := 0, 0
	for i := 0; i < len(s); i++ {
		left1, right1 := expandAroundCenter(s, i, i)
		left2, right2 := expandAroundCenter(s, i, i+1)
		if right1-left1 > end-start {
			start, end = left1, right1
		}
		if right2-left2 > end-start {
			start, end = left2, right2
		}
	}
	return s[start : end+1]
}

func expandAroundCenter(s string, left, right int) (int, int) {
	for left >= 0 && right < len(s) && s[left] == s[right] {
		left, right = left-1, right+1
	}
	return left + 1, right - 1
}

```



## DP

```java
class Solution {
    public String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2) return s;

        int maxLen = 1, begin = 0;
        boolean[][] dp = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }

        char[] charArray = s.toCharArray();
        for (int L = 2; L <= len; L++) {
            for (int le = 0; le < len; le++) {  // 枚举左边界，左边界的上限设置可以宽松一些
                int ri = L + le - 1;  // 由 L 和 le 可以确定右边界，即 ri - le + 1 = L 得
                if (ri >= len) break;  // 如果右边界越界，就可以退出当前循环

                if (charArray[le] != charArray[ri]) {
                    dp[le][ri] = false;
                } else {
                    if (ri - le < 3) {  // !!!
                        dp[le][ri] = true;
                    } else {
                        dp[le][ri] = dp[le + 1][ri - 1];
                    }
                }

                // 只要 dp[le][L] == true 成立，就表示子串 s[le..L] 是回文，
                if (dp[le][ri] && ri - le + 1 > maxLen) {
                    maxLen = ri - le + 1;
                    begin = le;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }
}
```

