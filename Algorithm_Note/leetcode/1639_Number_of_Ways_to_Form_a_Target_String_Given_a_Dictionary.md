# 1639. Number of Ways to Form a Target String Given a Dictionary

You are given a list of strings of the **same length** `words` and a string `target`.

Your task is to form `target` using the given `words` under the following rules:

- `target` should be formed from left to right.
- To form the `ith` character (**0-indexed**) of `target`, you can choose the `kth` character of the `jth` string in `words` if `target[i] = words[j][k]`.
- Once you use the `kth` character of the `jth` string of `words`, you **can no longer** use the `xth` character of any string in `words` where `x <= k`. In other words, all characters to the left of or at index `k` become unusuable for every string.
- Repeat the process until you form the string `target`.

**Notice** that you can use **multiple characters** from the **same string** in `words` provided the conditions above are met.

Return *the number of ways to form `target` from `words`*. Since the answer may be too large, return it **modulo** `109 + 7`.

 

**Example:**

```
Input: words = ["acca","bbbb","caca"], target = "aba"
Output: 6
Explanation: There are 6 ways to form target.
"aba" -> index 0 ("acca"), index 1 ("bbbb"), index 3 ("caca")
"aba" -> index 0 ("acca"), index 2 ("bbbb"), index 3 ("caca")
"aba" -> index 0 ("acca"), index 1 ("bbbb"), index 3 ("acca")
"aba" -> index 0 ("acca"), index 2 ("bbbb"), index 3 ("acca")
"aba" -> index 1 ("caca"), index 2 ("bbbb"), index 3 ("acca")
"aba" -> index 1 ("caca"), index 2 ("bbbb"), index 3 ("caca")
```

## DP

```java
class Solution {
    public int numWays(String[] words, String target) {
        int W = words[0].length(), T = target.length(), MOD = 1000000007;
        char[] tar = (" "+target).toCharArray();  // W >= T
        long[][] cout = new long[W+1][26], dp = new long[W+1][T+1];
        for (int i = 1; i <= W; i++) {
            for (String w : words) {
                cout[i][w.charAt(i-1)-'a']++;    // cout[i][x]: i 层 x 字符的数量
            }
        }

        dp[0][0] = 1;   // dp[i][t]: 前i层构成 target前t个字符 的方案数
        for (int i = 1; i <= W; i++) {
            for (int t = 0; t <= Math.min(i, T); t++) {
                dp[i][t] = dp[i-1][t];  // 前一个字符在上一层的数量 * 这一层当前字符的数量
                if (t > 0) dp[i][t] = (dp[i][t] + dp[i-1][t-1]*cout[i][tar[t]-'a']) % MOD;
            }
        }
        return (int) dp[W][T];
    }
}
```

```java
// fast  ?TODO
class Solution {
    public int numWays(String[] words, String target) {
        int W = words[0].length(), T = target.length(), MOD = (int)1e9 + 7;
        long[] dp = new long[W];
        long[][] cout = new long[W][26];
        for (String w : words) {
            for (int i = 0; i < W; i++) {
                cout[i][w.charAt(i) - 'a']++;
            }
        }
        int c = target.charAt(0) - 'a';
        for (int i = 0; i <= W - T; i++) {
            dp[i] = cout[i][c];
            if (i > 0) dp[i] += dp[i - 1];
        }
        for (int t = 1; t < T; t++) {
            c = target.charAt(t) - 'a';
            for (int i = W-T + t; i >= t; i--) {
                dp[i] = dp[i - 1] * cout[i][c];
                dp[i] %= MOD;
            }
            for (int i = t + 1; i <= W-T + t; i++) {
                dp[i] += dp[i - 1];
                dp[i] %= MOD;
            }
        }
        return (int)dp[W - 1];
    }
}
```



## DFS(TLE)

```java
class Solution {
    int res = 0, wLen, tLen;
    
    public int numWays(String[] words, String target) {
		wLen = words[0].length(); tLen = target.length();
        char[] arr = target.toCharArray();
        dfs(0, 0, words, arr);
        
        return res;
    }
    
    private void dfs(int wIdx, int tIdx, String[] words, char[] target) {
        if (tIdx >= target.length) {
            res++; return;
        }
        if (wIdx >= words[0].length() || wLen-wIdx < tLen-tIdx) return;
        
        for (int i = 0; i < words.length; i++) {
            if (words[i].charAt(wIdx) == target[tIdx]) {
                dfs(wIdx+1, tIdx+1, words, target);  // 当前层 选这个单词
            }
        }
        dfs(wIdx+1, tIdx, words, target);  // 当前层 不选这个单词
    }
}
```
