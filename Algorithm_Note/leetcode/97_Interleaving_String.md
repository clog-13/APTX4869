# 97. 交错字符串

给定三个字符串 `s1`、`s2`、`s3`，请你帮忙验证 `s3` 是否是由 `s1` 和 `s2` **交错** 组成的。

两个字符串 `s` 和 `t` **交错** 的定义与过程如下，其中每个字符串都会被分割成若干 **非空** 子字符串：

- `s = s1 + s2 + ... + sn`
- `t = t1 + t2 + ... + tm`
- `|n - m| <= 1`
- **交错** 是 `s1 + t1 + s2 + t2 + s3 + t3 + ...` 或者 `t1 + s1 + t2 + s2 + t3 + s3 + ...`

 

**示例：**

```
输入：s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
输出：true
```



## DP

```java
class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        int n = s1.length(), m = s2.length(), t = s3.length();
        if (n + m != t) return false;

        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[0][0] = true;
        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j <= m; ++j) {
                int p = i + j - 1;
                if (i > 0) {
                    dp[i][j] = (dp[i-1][j] && s1.charAt(i-1) == s3.charAt(p));
                }
                if (j > 0) {
                    dp[i][j] |= (dp[i][j-1] && s2.charAt(j-1) == s3.charAt(p));
                }
            }
        }

        return dp[n][m];
    }
}
```

```java
class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        int n = s1.length(), m = s2.length(), t = s3.length();
        if (n + m != t) return false;

        boolean[] dp = new boolean[m + 1];
        dp[0] = true;
        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j <= m; ++j) {
                int p = i + j - 1;
                if (i > 0) {
                    dp[j] = dp[j] && s1.charAt(i-1) == s3.charAt(p);
                }
                if (j > 0) {
                    dp[j] |= (dp[j-1] && s2.charAt(j-1) == s3.charAt(p));
                }
            }
        }

        return dp[m];
    }
}
```



## 记忆化回溯

```java
class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length(), n = s2.length(), p = s3.length();
        if (m+n != p) return false;
        int[][] F = new int[m+1][n+1];
        return isValid(s1, s2, s3, 0, 0, 0, F);
              
    }

    boolean isValid(String s1, String s2, String s3, int i, int j, int k, int[][] F) {
        if (k == s1.length()+s2.length()) return true;

        if (F[i][j] == 1) return true;
        else if (F[i][j] == 2) return false;

        boolean rt = false;
        if (i<s1.length() && s1.charAt(i)==s3.charAt(k)) {
            rt = isValid(s1, s2, s3, i+1, j, k+1, F);      
        }
        if (j<s2.length() && s2.charAt(j)==s3.charAt(k)) {
            rt |= isValid(s1, s2, s3, i, j+1, k+1, F);
        }

        if (rt) F[i][j] = 1;
        else F[i][j] = 2;

        return rt;
    }
}
```

