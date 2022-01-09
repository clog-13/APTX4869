# 22. Generate Parentheses

Given `n` pairs of parentheses, write a function to *generate all combinations of well-formed parentheses*.

 

**Example 1:**

```
Input: n = 3
Output: ["((()))","(()())","(())()","()(())","()()()"]
```



## BackTrack

```java
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<String>();
        backtrack(res, new StringBuilder(), 0, 0, n);
        return res;
    }

    public void backtrack(List<String> res, StringBuilder str, int open, int close, int max) {
        if (str.length() == max * 2) {
            res.add(str.toString());
            return;
        }
        if (open < max) {
            str.append('(');
            backtrack(res, str, open + 1, close, max);
            str.deleteCharAt(str.length() - 1);
        }
        if (close < open) {
            str.append(')');
            backtrack(res, str, open, close + 1, max);
            str.deleteCharAt(str.length() - 1);
        }
    }
}
s
```



## IQ & ST Enum

```java
class Solution {
    List[] cache = new ArrayList[100];
    public List<String> generateParenthesis(int n) {
        return generate(n);
    }
    
    public List<String> generate(int n) {
        if (cache[n] != null) return cache[n];
        List<String> res = new ArrayList<String>();
        if (n == 0) {
            res.add("");  // !!!
        } else {
            for (int i = 0; i < n; i++) {
                for (String le: generate(i)) {
                    for (String ri: generate(n - 1 - i)) {
                        res.add("(" + le + ")" + ri);
                    }
                }
            }
        }
        cache[n] = res;
        return res;
    }
}
```

