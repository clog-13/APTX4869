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

    public void backtrack(List<String> res, StringBuilder str, int le, int ri, int n) {
        if (str.length() == n * 2) {
            res.add(str.toString());
            return;
        }
        if (le < n) {
            str.append('(');
            backtrack(res, str, le + 1, ri, n);
            str.deleteCharAt(str.length() - 1);
        }
        if (ri < le) {
            str.append(')');
            backtrack(res, str, le, ri + 1, n);
            str.deleteCharAt(str.length() - 1);
        }
    }
}
```



## IQ & ST Enum

```java
class Solution {
    List[] memo = new ArrayList[100];
    public List<String> generateParenthesis(int n) {
        return generate(n);
    }
    
    public List<String> generate(int n) {
        if (memo[n] != null) return memo[n];
        List<String> res = new ArrayList<String>();
        if (n == 0) {
            res.add("");  // !!!
        } else {
            for (int i = 0; i < n; i++) {
                for (String le: generate(i)) {
                    for (String ri: generate(n - i - 1)) {
                        res.add("(" + le + ")" + ri);
                    }
                }
            }
        }
        memo[n] = res;
        return res;
    }
}
```

