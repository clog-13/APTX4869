# 1249. Minimum Remove to Make Valid Parentheses

Given a string s of `'('` , `')'` and lowercase English characters.

Your task is to remove the minimum number of parentheses ( `'('` or `')'`, in any positions ) so that the resulting *parentheses string* is valid and return **any** valid string.

Formally, a *parentheses string* is valid if and only if:

- It is the empty string, contains only lowercase characters, or
- It can be written as `AB` (`A` concatenated with `B`), where `A` and `B` are valid strings, or
- It can be written as `(A)`, where `A` is a valid string.

 

**Example 1:**

```
Input: s = "lee(t(c)o)de)"
Output: "lee(t(c)o)de"
Explanation: "lee(t(co)de)" , "lee(t(c)ode)" would also be accepted.
```

**Example 2:**

```
Input: s = "a)b(c)d"
Output: "ab(c)d"
```

**Example 3:**

```
Input: s = "))(("
Output: ""
Explanation: An empty string is also valid.
```



## Stack

```java
class Solution {
    public String minRemoveToMakeValid(String s) {
        Set<Integer> rm = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } if (s.charAt(i) == ')') {
                if (stack.isEmpty()) {
                    rm.add(i);
                } else {
                    stack.pop();
                }
            }
        }
        while (!stack.isEmpty()) rm.add(stack.pop());

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!rm.contains(i)) res.append(s.charAt(i));
        }
        return res.toString();
    }
}
```



## 2-Loops

```java
class Solution {
    public String minRemoveToMakeValid(String s) {
        StringBuilder res = rmInvalidClose(s, '(', ')');
        res = rmInvalidClose(res.reverse(), ')', '(');
        return res.reverse().toString();
    }
    StringBuilder rmInvalidClose(CharSequence string, char open, char close) {
        StringBuilder res = new StringBuilder();
        var balance = 0;
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c == open) {
                balance++;
            } else if (c == close) {
                if (balance == 0) continue;
                balance--;
            }
            res.append(c);
        }  
        return res;
    }
}
```



## 2-Loops pro

```java
class Solution {
    public String minRemoveToMakeValid(String s) {
        // Remove invalid ")"
        StringBuilder tmp = new StringBuilder();
        int leCnt = 0, balance = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                leCnt++;
                balance++;
            } if (c == ')') {
                if (balance == 0) continue;
                balance--;
            }
            tmp.append(c);
        }

        // Remove "("
        StringBuilder res = new StringBuilder();
        int openToKeep = leCnt - balance;  // b>0 : have invalid "("
        for (int i = 0; i < tmp.length(); i++) {
            char c = tmp.charAt(i);
            if (c == '(') {
                if (--openToKeep < 0) continue;
            }
            res.append(c);
        }

        return res.toString();
    }
}
```

