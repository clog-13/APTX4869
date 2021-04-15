# 剑指 Offer 19. 正则表达式匹配

请实现一个函数用来匹配包含`'. '`和`'*'`的正则表达式。模式中的字符`'.'`表示任意一个字符，而`'*'`表示它前面的字符可以出现任意次（含0次）。在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串`"aaa"`与模式`"a.a"`和`"ab*ac*a"`匹配，但与`"aa.a"`和`"ab*a"`均不匹配。

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

**示例 3:**

```
输入:
s = "aab"
p = "c*a*b"
输出: true
解释: 因为 '*' 表示零个或多个，这里 'c' 为 0 个, 'a' 被重复一次。因此可以匹配字符串 "aab"。
```



## DP

```java
class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();

        boolean[][] f = new boolean[m + 1][n + 1];
        f[0][0] = true;
        for (int i = 0; i <= m; ++i) {      // s
            for (int j = 1; j <= n; ++j) {  // p
                if (p.charAt(j - 1) == '*') {
                    if (matches(s, p, i, j - 1)) { 
                        f[i][j] = f[i][j-2] || f[i-1][j];  // *=1 || *=n
                    } else {
                        f[i][j] = f[i][j-2];  // * = 0
                    }
                } else {
                    if (matches(s, p, i, j)) f[i][j] = f[i - 1][j - 1];
                }
            }
        }
        return f[m][n];
    }

    public boolean matches(String s, String p, int i, int j) {
        if (i == 0) return false;
        if (p.charAt(j - 1) == '.') return true;
        return s.charAt(i-1) == p.charAt(j-1);
    }
}
```



## 递归

```c++
class Solution {
public:
    bool isMatch(string s, string p) {
        if (p.empty()) return s.empty();
        bool first_match = !s.empty() && (s[0] == p[0] || '.' == p[0]);  // 第一个字符匹配
        if (p.size() >= 2 && p[1] == '*') {  // *前字符重复>=1次 || *前字符重复0次（不出现）
            return (first_match && isMatch(s.substr(1), p)) || isMatch(s, p.substr(2));
        } else {  // 不是*，剪去已经匹配成功的头部，继续比较
            return first_match && isMatch(s.substr(1), p.substr(1));    
        }
    }
};
```



## 记忆化搜索

```java
class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        StringBuilder s1 = new StringBuilder(s), p1 = new StringBuilder(p);
        s1.insert(0, '0'); p1.insert(0, '0');
        int[][] f = new int[m+1][n+1];
        for (int i=0; i<=m; i++) Arrays.fill(f[i], -1);
        
        return isMatch(m, n, s1, p1, f)==1;
    }

    public int isMatch(int i,int j,StringBuilder s,StringBuilder p,int[][] f){  //备忘录方法递归求解
        if (f[i][j] >= 0) return f[i][j];
        if (i==0 && j==0) return f[i][j] = 1;

        if (i == 0) {
            if (p.charAt(j)!='*') return f[i][j] = 0;
            else return f[i][j] = isMatch(i, j-2, s, p, f);
        }
        if (j == 0) return f[i][j] = 0;

        if (p.charAt(j) == '*') {
            f[i][j] = isMatch(i, j-2, s, p, f);  // *=0 or *=1
            if (f[i][j]==1) return f[i][j];
            if (s.charAt(i)==p.charAt(j-1) || p.charAt(j-1)=='.') {  // i match j
                return f[i][j]=isMatch(i-1, j, s, p, f);  // *=n
            }
        } else {
            if (s.charAt(i)==p.charAt(j) || p.charAt(j)=='.') {
                return f[i][j] = isMatch(i-1, j-1, s, p, f);
            } else {
                return f[i][j] = 0;
            }
        }
        return f[i][j];
    }
}
```

