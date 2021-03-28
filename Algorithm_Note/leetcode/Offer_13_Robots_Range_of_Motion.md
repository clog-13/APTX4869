# 剑指 Offer 13. 机器人的运动范围

地上有一个m行n列的方格，从坐标 `[0,0]` 到坐标 `[m-1,n-1]` 。一个机器人从坐标 `[0, 0] `的格子开始移动，它每次可以向左、右、上、下移动一格（不能移动到方格外），也不能进入行坐标和列坐标的数位之和大于k的格子。例如，当k为18时，机器人能够进入方格 [35, 37] ，因为3+5+3+7=18。但它不能进入方格 [35, 38]，因为3+5+3+8=19。请问该机器人能够到达多少个格子？

 

**示例：**

```
输入：m = 2, n = 3, k = 1
输出：3
```



## DFS

```java
class Solution {
    boolean[][] visited;
    public int movingCount(int m, int n, int k) {
        visited = new boolean[m][n]; 
        return dfs(0, 0, m, n, k);
    }

    private int dfs(int x, int y, int m, int n, int k) {
        if (x >= m || y >= n || visited[x][y] || (x % 10 + x / 10 + y % 10 + y / 10) > k) {
            return 0;
        }
        visited[x][y] = true;
        return 1 + dfs(x + 1, y, m, n, k) + dfs(x, y + 1, m, n, k);
    }
}
```



## 递推（DP？）

```java
class Solution {
    public int movingCount(int m, int n, int k) {
        if (k == 0) return 1;
        boolean[][] vis = new boolean[m][n];
        int res = 1;
        vis[0][0] = true;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if ((i == 0 && j == 0) || get(i)+get(j)>k) {
                    continue;
                }
                // 搜索方向只需朝下或朝右，因此只会从 (i - 1, j) 或者 (i, j - 1) 两个格子走过来
                if (i - 1 >= 0) vis[i][j] |= vis[i - 1][j];
                if (j - 1 >= 0) vis[i][j] |= vis[i][j - 1];
                res += vis[i][j] ? 1 : 0;
            }
        }
        return res;
    }

    private int get(int x) {
        int res = 0;
        while (x != 0) {
            res += x % 10;
            x /= 10;
        }
        return res;
    }
}
```

