# 1653. Minimum Deletions to Make String Balanced

You are given a string `s` consisting only of characters `'a'` and `'b'`.

You can delete any number of characters in `s` to make `s` **balanced**. `s` is **balanced** if there is no pair of indices `(i,j)` such that `i < j` and `s[i] = 'b'` and `s[j]= 'a'`.

Return *the **minimum** number of deletions needed to make* `s` ***balanced***.

 

**Example:**

```
Input: s = "aababbab"
Output: 2
Explanation: You can either:
Delete the characters at 0-indexed positions 2 and 6 ("aababbab" -> "aaabbb"), or
Delete the characters at 0-indexed positions 3 and 6 ("aababbab" -> "aabbbb").
```

## DP

```java
class Solution {
    public int minimumDeletions(String s) {
        int N = s.length();
        int[][] dp = new int[2][N]; // 只考虑前N个数，结尾是 a 或 b 的最小修改次数
        dp[0][0] = s.charAt(0) == 'a' ? 0 : 1;
        dp[1][0] = s.charAt(0) == 'b' ? 0 : 1;
        for (int i = 1; i < N; i++) {
            if (s.charAt(i) == 'a') {
                dp[0][i] = dp[0][i-1];
                dp[1][i] = Math.min(dp[0][i-1], dp[1][i-1]) + 1;
            } else {
                dp[0][i] = dp[0][i-1] + 1;
                dp[1][i] = Math.min(dp[0][i-1], dp[1][i-1]);
            }
        }
        return Math.min(dp[0][N-1], dp[1][N-1]);
    }
}
```

## preSum

```java
class Solution {
    public int minimumDeletions(String s) {
        int N = s.length();
        int[] a = new int[N+1], b = new int[N+1];
        // 正向, 当前位置开始为b的地盘需要的花费
        for (int i = 0; i < N; i++) {
            a[i+1] = a[i] + (s.charAt(i)=='a' ? 0 : 1);
        }
        // 逆向, 当前位置开始为b的地盘需要的花费
        for (int i = N-1; i >= 0; i--) {
            b[i] = b[i+1] + (s.charAt(i)=='b' ? 0 : 1);
        }
        int res = N;
        for (int i = 0; i < N; i++) {
            res = Math.min(res, a[i]+b[i+1]);
        }
        return res;
    }
}
```

##  Stack

```c
int minimumDeletions(char * s) {
    int N = strlen(s), res = 0, b = 0;
    for (int i = 0; i < N; i++) {
        if (s[i] == 'b') {
            b++;
        } else {
            if (b != 0) {
                res++;
                b--;
            }
        }
    }
    return res;
}
```

