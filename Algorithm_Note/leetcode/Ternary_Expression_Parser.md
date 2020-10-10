# 439. 三元表达式解析器
给定一个以字符串表示的任意嵌套的三元表达式，计算表达式的值。你可以假定给定的表达式始终都是有效的并且只包含数字 0-9, ?, :, T 和 F (T 和 F 分别表示真和假）。

注意：

给定的字符串长度 ≤ 10000。
所包含的数字都只有一位数。
条件表达式从右至左结合（和大多数程序设计语言类似）。
条件是 T 和 F其一，即条件永远不会是数字。
表达式的结果是数字 0-9, T 或者 F。

**示例：**
输入： "F?1:T?4:5"
输出： "4"

## 栈
不能直接在else 的三元表达式后面写 stack.pop()， 因为要把 T 时的 储存在stack的 F 部分去掉。
```java
class Solution {
    public String parseTernary(String expression) {
        Stack<Character> stack = new Stack<>();
        char[] c = expression.toCharArray();
        for (int i = c.length-1; i >= 2; i-=2) {
            if (c[i-1] == ':')
                stack.push(c[i]);   // push FALSE
            else {  // X? 所以是 i-2, c[i]是T， pop是F
                c[i-2] = c[i-2] == 'T' ? c[i] : stack.peek();
                stack.pop();
            }
        }
        return ""+c[0];
    }
}
```

## 递归 + 分治
```java
class Solution {
    public String parseTernary(String expression) {
        int N = expression.length();
        int checkLevel = 0;
        for (int j = 1; j < N; j++) {   //  必须从 1 开始
            if (expression.charAt(j) == '?') checkLevel++;
            if (expression.charAt(j) == ':') checkLevel--;
            if (checkLevel == 0) {
                return (expression.charAt(0) == 'T') ? parseTernary(expression.substring(2, j)) : 
                                                       parseTernary(expression.substring(j+1, N));
            }
        }
        return expression;
    }
}
```