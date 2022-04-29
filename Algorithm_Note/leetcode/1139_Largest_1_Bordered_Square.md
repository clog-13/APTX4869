# 1139. Largest 1-Bordered Square
Given a 2D grid of 0s and 1s, return the number of elements in the largest square subgrid that has all 1s on its border, or 0 if such a subgrid doesn't exist in the grid.

**Example 1:**
```
Input: grid = [[1,1,1],[1,0,1],[1,1,1]]
Output: 9
```

## DP
```java
class Solution {
    public int largest1BorderedSquare(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][][] dp = new int[m+1][n+1][2];
        int res = 0;
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (grid[i-1][j-1] == 0) continue;  // 如果是0则不要继续了
                dp[i][j][0] = dp[i-1][j][0] + 1;    // 求出up  情况下连续的个数
                dp[i][j][1] = dp[i][j-1][1] + 1;    // 求出left情况下连续的个数
                int min = Math.min(dp[i][j][0], dp[i][j][1]);
                for (int k = min; k >= 0; k--) {   // !!! 最小长度1（点）
                    if (dp[i-k][j][1] >= k+1 && dp[i][j-k][0] >= k+1) { // !!!
                        res = Math.max(res, k+1); // !!!
                        break;
                    }
                }
            }
        }
        return res * res;
    }
}
```

## Mono
```java
class Solution {
    public int largest1BorderedSquare(int[][] grid) {
        int res = 0, row = grid.length, col = grid[0].length;
        
        for (int i = 0; i < row; i++) {
            outer: for (int j = 0; j < col; j++) {
                if (grid[i][j] == 0) continue;
                
                int cur = res;
                while (i+cur < row && j+cur < col) {
                    // top
                    for (int k = j+1; k <= j+cur; k++) {
                        if (grid[i][k] == 0) continue outer;
                    }
                    // left
                    for (int k = i+1; k <= i+cur; k++) {
                        if (grid[k][j] == 0) continue outer;
                    }
                    boolean flag = true;
                    // down
                    for (int k = j+1; k <= j+cur; k++) {
                        if (grid[i+cur][k] == 0) {
                            cur++;
                            flag = false;
                            break;
                        }
                    }
                    if (!flag) continue;
                    // right
                    for (int k = i+1; k <= i+cur; k++) {
                        if (grid[k][j+cur] == 0) {
                            cur++;
                            flag = false;
                            break;
                        }
                    }
                    if (!flag) continue;
                    
                    res = ++cur;
                }
            }
        }
        return res*res;
    }
}
```