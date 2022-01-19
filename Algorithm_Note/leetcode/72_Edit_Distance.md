# 72. Edit Distance

Given two strings `word1` and `word2`, return *the minimum number of operations required to convert `word1` to `word2`*.

You have the following three operations permitted on a word:

- Insert a character
- Delete a character
- Replace a character

 

**Example 1:**

```
Input: word1 = "horse", word2 = "ros"
Output: 3
Explanation: 
horse -> rorse (replace 'h' with 'r')
rorse -> rose (remove 'r')
rose -> ros (remove 'e')
```

**Example 2:**

```
Input: word1 = "intention", word2 = "execution"
Output: 5
Explanation: 
intention -> inention (remove 't')
inention -> enention (replace 'i' with 'e')
enention -> exention (replace 'n' with 'x')
exention -> exection (replace 'n' with 'c')
exection -> execution (insert 'u')
```



## DP

```java
class Solution {
    public int minDistance(String word1, String word2) {
        int N = word1.length(), M = word2.length();
        if (N * M == 0) return N + M;
        int[][] dp = new int[N + 1][M + 1];

        for (int i = 0; i < N + 1; i++) dp[i][0] = i;
        for (int j = 0; j < M + 1; j++) dp[0][j] = j;
        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < M + 1; j++) {
                if (word1.charAt(i-1) == word2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i-1][j-1],  Math.min(dp[i-1][j], dp[i][j-1]));
                }
            }
        }
        return dp[N][M];
    }
}
```

```go
func minDistance(word1 string, word2 string) int {
	N, M := len(word1), len(word2)
	dp := make([][]int, N+1)
	for i := range dp {
		dp[i] = make([]int, M+1)
	}
	for i := 0; i < N+1; i++ {
		dp[i][0] = i // word1[i] 变成 word2[0], 删掉 word1[i], 需要 i 部操作
	}
	for j := 0; j < M+1; j++ {
		dp[0][j] = j // word1[0] 变成 word2[j], 插入 word1[j]，需要 j 部操作
	}
	for i := 1; i < N+1; i++ {
		for j := 1; j < M+1; j++ {
			if word1[i-1] == word2[j-1] {
				dp[i][j] = dp[i-1][j-1]
			} else { // Min(插入，删除，替换)
				dp[i][j] = Min(dp[i][j-1], dp[i-1][j], dp[i-1][j-1]) + 1
			}
		}
	}
	return dp[N][M]
}
func Min(args ...int) int {
	min := args[0]
	for _, item := range args {
		if item < min {
			min = item
		}
	}
	return min
}
```

