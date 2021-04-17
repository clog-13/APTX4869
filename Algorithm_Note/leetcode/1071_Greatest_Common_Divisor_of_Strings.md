# 1071. 字符串的最大公因子

对于字符串 `S` 和 `T`，只有在 `S = T + ... + T`（`T` 与自身连接 1 次或多次）时，我们才认定 “`T` 能除尽 `S`”。

返回最长字符串 `X`，要求满足 `X` 能除尽 `str1` 且 `X` 能除尽 `str2`。

**示例 1：**

```
输入：str1 = "ABCABC", str2 = "ABC"
输出："ABC"
```

**示例 2：**

```
输入：str1 = "LEET", str2 = "CODE"
输出：""
```



## GCD

题目要求最大公因子，所以是最大公约数（而不是第二大公约数或最小公约数...）

考虑 str1:aaaaa, str2:aaa

aaaaa aaa

aaa aaaaa

```c++
class Solution {
public:
    string gcdOfStrings(string str1, string str2) {
        if (str1 + str2 != str2 + str1) return "";
        return str1.substr(0, __gcd((int)str1.length(), (int)str2.length()));
    }
};
```



## 枚举

```java
class Solution {
    public String gcdOfStrings(String str1, String str2) {
        int len1 = str1.length(), len2 = str2.length();
        for (int i = Math.min(len1, len2); i >= 1; i--) { // 从长度大的开始枚举(可优化成二分)
            if (len1 % i == 0 && len2 % i == 0) {
                String X = str1.substring(0, i);
                if (check(X, str1) && check(X, str2)) {
                    return X;
                }
            }
        }
        return "";
    }

    public boolean check(String t, String s) {
        int lenx = s.length() / t.length();
        StringBuffer res = new StringBuffer();
        for (int i = 1; i <= lenx; ++i) {
            res.append(t);
        }
        return res.toString().equals(s);
    }
}
```

