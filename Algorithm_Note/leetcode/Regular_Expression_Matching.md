# 10. 正则表达式匹配

给你一个字符串 `s` 和一个字符规律 `p`，请你来实现一个支持 `'.'` 和 `'*'` 的正则表达式匹配。

```
'.' 匹配任意单个字符
'*' 匹配零个或多个前面的那一个元素
```

所谓匹配，是要涵盖 **整个** 字符串 `s`的，而不是部分字符串。

**说明:**

- `s` 可能为空，且只包含从 `a-z` 的小写字母。
- `p` 可能为空，且只包含从 `a-z` 的小写字母，以及字符 `.` 和 `*`。

**示例 1:**

```
输入:
s = "aa"
p = "a"
输出: false
解释: "a" 无法匹配 "aa" 整个字符串。
```

**示例 2:**

```
输入:
s = "ab"
p = ".*"
输出: true
解释: ".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。
```



## 状态机
```java
class Solution {
    Set<Integer> nextStates = new HashSet<>();
    public boolean isMatch(String s, String p) {
        int idx = 0;
        nextMatch(0, p, nextStates);
        while (!nextStates.isEmpty()) {
            Set<Integer> curStates = nextStates;
            nextStates = new HashSet<>();
            for (int state : curStates) {
                if (idx >= s.length() && state >= p.length()) return true;
                else if (state >= p.length()) continue;
                else if (idx < s.length()){
                    if (p.charAt(state) == '.' || s.charAt(idx) == p.charAt(state)) {
                        if (state+1 < p.length() && p.charAt(state+1) == '*') {
                            nextMatch(state, p, nextStates);
                        } else {
                            nextMatch(state+1, p, nextStates);
                        }
                    }
                }
            }
            idx++;
        }
        return false;
    }

    private void nextMatch(int state, String p, Set<Integer> nextStates) {
        nextStates.add(state);
        if (state+1 < p.length() && p.charAt(state+1) == '*') {
            nextMatch(state+2, p, nextStates);
        }
    }
}
```



## 动态规划
**状态**
首先状态 dp 一定能自己想出来。
dp\[i\]\[j\] 表示 s 的前 i 个是否能被 p 的前 j 个匹配

**转移方程**
怎么想转移方程？首先想的时候从已经求出了 dp\[i-1\]\[j-1\] 入手，再加上已知 s\[i\]、p\[j\]，要想的问题就是怎么去求 dp\[i\]\[j\]。

已知 dp\[i-1\]\[j-1\] 意思就是前面子串都匹配上了，不知道新的一位的情况。
那就分情况考虑，所以对于新的一位 p[j] s[i] 的值不同，要分情况讨论：

1. p\[j\] == s\[i\] : dp\[i\]\[j\] = dp\[i-1\]\[j-1\]

然后从 p\[j\] 可能的情况来考虑，让 p\[j\]=各种能等于的东西。

2. p\[j\] == "." : dp\[i\]\[j\] = dp\[i-1\]\[j-1\]

3. p\[j\] ==" * ":

首先给了 *，明白 * 的含义是 匹配零个或多个前面的那一个元素，所以要考虑他前面的元素 p[j-1]。* 跟着他前一个字符走，前一个能匹配上 s[i]，* 才能有用，前一个都不能匹配上 s[i]，* 也无能为力，只能让前一个字符消失，也就是匹配 0 次前一个字符。
所以按照 p[j-1] 和 s[i] 是否相等，我们分为两种情况：

  3.1 p\[j-1\] != s\[i\]
这就是刚才说的那种前一个字符匹配不上的情况。
比如(ab, abc* )。遇到 * 往前看两个，发现前面 s[i] 的 ab 对 p[j-2] 的 ab 能匹配，虽然后面是 c*，但是可以看做匹配 0 次 c，相当于直接去掉 c *，所以也是 True。注意 (ab, abc**) 是 False。

  3.2 p[j-1] == s[i] or p[j-1] == "." 
\* 前面那个字符，能匹配 s[i]，或者 \* 前面那个字符是万能的 .
因为 . * 就相当于 . .，那就只要看前面可不可以匹配就行。
比如 (\##b , \###b \*)，或者 ( \##b , \### . * ) 只看 ### 后面一定是能够匹配上的。
所以要看 b 和 b * 前面那部分 \## 的地方匹不匹配。
第二个难想出来的点：怎么判断前面是否匹配

```
dp[i][j] = dp[i-1][j] 	// 多个字符匹配的情况	dp[i][j+1] = dp[i-1][j+1]
dp[i][j] = dp[i-1][j-1]	// 单个字符匹配的情况	dp[i][j+1] = dp[i-1][j]
dp[i][j] = dp[i][j-2]	// 没有字符匹配的情况	dp[i][j+1] = dp[i][j-1]
```

```java
class Solution {
    public boolean isMatch(String s, String p) {
        int S = s.length(), P = p.length();

        boolean[][] dp = new boolean[S+1][P+1];
        dp[0][0] = true;
        for (int i = 0; i <= S; ++i) {
            for (int j = 0; j < P; ++j) {
                if (p.charAt(j) == '*') {
                    if (matches(s, p, i - 1, j - 1)) {
                        dp[i][j + 1] = dp[i][j - 1] || dp[i - 1][j + 1];    // 跳过 或 多个匹配
                    } else {
                        dp[i][j + 1] = dp[i][j - 1];    // 跳过
                    }
                } else {
                    if (matches(s, p, i - 1, j)) {
                        dp[i][j + 1] = dp[i - 1][j];    // 单个匹配
                    }
                }
            }
        }
        return dp[S][P];
    }

    public boolean matches(String s, String p, int i, int j) {
        if (i < 0) return false;
        if (p.charAt(j) == '.') return true;
        if (s.charAt(i) == p.charAt(j)) return true;
        return false;
    }
}
```

