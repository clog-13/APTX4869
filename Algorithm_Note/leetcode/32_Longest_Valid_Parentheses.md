# 32. 最长有效括号
给你一个只包含 `'('` 和 `')'` 的字符串，找出最长有效（格式正确且连续）括号子串的长度。

**示例：**
```
输入：s = "(())"
输出：4
解释：最长有效括号子串是 "(())"
```



## DP

```java
class Solution {
    public int longestValidParentheses(String s) {
        int res = 0;
        int[] dp = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i-1) == '(') {
                    dp[i] = (i-2 >= 0 ? dp[i-2] : 0) + 2;
                } else if (i-dp[i-1]-1 >= 0 && s.charAt(i-dp[i-1]-1) == '(') {
                    dp[i] = dp[i-1] + (i-dp[i-1]-2 >= 0 ? dp[i-dp[i-1]-2] : 0) + 2;
                }
                res = Math.max(res, dp[i]);
            }
        }
        return res;
    }
}
```



## 栈

```java
class Solution {
    public int longestValidParentheses(String s) {
        int res = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);  // Vailded!!!
                } else {
                    res = Math.max(res, i-stack.peek());
                }
            }
        }
        return res;
    }
}
```



## 扫描(优化空间复杂度的栈)

```java
class Solution {
    public int longestValidParentheses(String s) {
        int res = 0, le = 0, ri = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') le++;
            else ri++;
            if (le == ri) {
                res = Math.max(res, 2*ri);
            } else if (le < ri) {  // Invalid
                le = ri = 0;
            }
        }

        le = ri = 0;
        for (int i = s.length()-1; i >= 0; i--) {
            if (s.charAt(i) == '(') le++;
            else ri++;
            if (le == ri) {
                res = Math.max(res, 2*le);
            } else if (le > ri) {  // Invalid
                le = ri = 0;
            }
        }
        return res;
    }
}
```

