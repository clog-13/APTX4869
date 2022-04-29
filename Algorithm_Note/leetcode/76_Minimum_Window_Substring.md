# 76. Minimum Window Substring

Given two strings `s` and `t` of lengths `m` and `n` respectively, return *the **minimum window substring** of* `s` *such that every character in* `t` *(**including duplicates**) is included in the window. If there is no such substring**, return the empty string* `""`*.*

The testcases will be generated such that the answer is **unique**.

A **substring** is a contiguous sequence of characters within the string.

 

**Example 1:**

```
Input: s = "ADOBECODEBANC", t = "ABC"
Output: "BANC"
Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.
```



## Slide Window

```go
func minWindow(s string, t string) (res string) {
	var freq, need [58]int
	tcnt, cout := 0, 0

	for i := 0; i < len(t); i++ {
		if need[t[i]-'A'] == 0 {
			tcnt++ // 不同字符数量增加
		}
		need[t[i]-'A']++
	}

	le, ri := 0, -1
	for le < len(s) {
		if ri+1 < len(s) && tcnt > cout {
			ri++
			freq[s[ri]-'A']++
			if freq[s[ri]-'A'] == need[s[ri]-'A'] {
				cout++
			}
		} else {
			freq[s[le]-'A']--
			if freq[s[le]-'A'] < need[s[le]-'A'] {
				cout--
			}
			le++
		}

		if cout == tcnt {
			if res == "" || ri-le+1 < len(res) {
				res = s[le : ri+1]
			}
		}
	}
	return
}
```

