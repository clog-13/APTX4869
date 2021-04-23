# 65. 有效数字

**有效数字**（按顺序）可以分成以下几个部分：

1. 一个 **小数** 或者 **整数**
2. （可选）一个 `'e'` 或 `'E'` ，后面跟着一个 **整数**

**小数**（按顺序）可以分成以下几个部分：

1. （可选）一个符号字符（`'+'` 或 `'-'`）
2. 下述格式之一：
   1. 至少一位数字，后面跟着一个点 `'.'`
   2. 至少一位数字，后面跟着一个点 `'.'` ，后面再跟着至少一位数字
   3. 一个点 `'.'` ，后面跟着至少一位数字

**整数**（按顺序）可以分成以下几个部分：

1. （可选）一个符号字符（`'+'` 或 `'-'`）
2. 至少一位数字

部分有效数字列举如下：

- `["2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789"]`

部分无效数字列举如下：

- `["abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53"]`

给你一个字符串 `s` ，如果 `s` 是一个 **有效数字** ，请返回 `true` 。



## 状态机DFA

![](pic\65_1.jpg)

![](pic\65_2.png)

```java
class Solution {
    public boolean isNumber(String s) {
        int state = 0;
        int finals = 0b101101000;
        int[][] transfer = new int[][]{
               // b  +  x  .  e
                { 0, 1, 6, 2,-1},  // 0 blank
                {-1,-1, 6, 2,-1},  // 1 +
                {-1,-1, 3,-1,-1},  // 2 ./+.
                { 8,-1, 3,-1, 4},  // 3 .x/.xx/xx (T)
                {-1, 7, 5,-1,-1},  // 4 .xe/xe
                { 8,-1, 5,-1,-1},  // 5 ex/e+x (T)
                { 8,-1, 6, 3, 4},  // 6 x  (T)
                {-1,-1, 5,-1,-1},  // 7 .xe+/xe+ 
                { 8,-1,-1,-1,-1}}; // 8 blank (T)
        char[] ss = s.toCharArray();
        for (char c : ss) {
            int id = make(c);
            if (id == -1) return false;
            state = transfer[state][id];
            if (state < 0) return false;
        }
        return (finals & (1 << state)) > 0;
    }

    public int make(char c) {
        switch(c) {
            case ' ': return 0;
            case '+':
            case '-': return 1;
            case '.': return 3;
            case 'E':
            case 'e': return 4;
            default: if(c >= 48 && c <= 57) return 2;
        }
        return -1;
    }
}
```

