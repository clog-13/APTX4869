# 面试题 08.14. 布尔运算

给定一个布尔表达式和一个期望的布尔结果 result，布尔表达式由 `0` (false)、`1` (true)、`&` (AND)、 `|` (OR) 和 `^` (XOR) 符号组成。实现一个函数，算出有几种可使该表达式得出 result 值的括号方法。

**示例:**

```
输入: s = "1^0|0|1", result = 0

输出: 2
解释: 两种可能的括号方法是
1^(0|(0|1))
1^((0|0)|1)
```



## 记忆化回溯

```java
class Solution {
    private char[] arr;
    private int[][][] dp;

    public int countEval(String s, int tar) {
        arr = s.toCharArray();
        int N = arr.length;
        dp = new int[N][N][2];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        return helper(0, N-1, tar);
    }

    private int helper(int start, int end, int tar) {
        if (start == end) {
            return arr[start] - '0' == tar ? 1 : 0;
        }

        if (dp[start][end][tar] != -1) {
            return dp[start][end][tar];
        }

        int res = 0;
        for (int k = start; k < end; k+=2) {
            char operator = arr[k + 1];
            for (int i = 0; i <= 1; i++) {
                for (int j = 0; j <= 1; j++) {
                    if (getBoolAns(i, j, operator) == tar) {
                        res += helper(start, k, i) * helper(k + 2, end, j);
                    }
                }
            }
        }

        dp[start][end][tar] = res;
        return res;
    }

    private int getBoolAns(int val1, int val2, char operator) {
        switch (operator) {
            case '&': return val1 & val2;
            case '|': return val1 | val2;
            case '^': return val1 ^ val2;
        }
        return 0;
    }
}
```

