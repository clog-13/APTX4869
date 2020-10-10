#1162. 地图分析
你现在手里有一份大小为 N x N 的「地图」（网格） grid，上面的每个「区域」（单元格）都用 0 和 1 标记好了。其中 0 代表海洋，1 代表陆地，请你找出一个海洋区域，这个海洋区域到离它最近的陆地区域的距离是**最大的**。

我们这里说的距离是「曼哈顿距离」（ Manhattan Distance）：(x0, y0) 和 (x1, y1) 这两个区域之间的距离是 |x0 - x1| + |y0 - y1| 。

如果我们的地图上只有陆地或者海洋，请返回 -1。

**示例：**

![](../pic/As_Far_from_Land_as_Possible01.jpeg)

输入：[[1,0,1],[0,0,0],[1,0,1]]
输出：2
解释：
海洋区域 (1, 1) 和所有陆地区域之间的距离都达到最大，最大距离为 2。

##DP
![](../pic/As_Far_from_Land_as_Possible02.png)
```java
class Solution {
    public int maxDistance(int[][] grid) {
        int[][] dp = new int[105][105];
        int n = grid.length;

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                dp[i][j] = (grid[i][j]==1 ? 0 : 201);

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == 1) continue;
                if (i - 1 >= 0) dp[i][j] = Math.min(dp[i][j], dp[i-1][j] + 1);
                if (j - 1 >= 0) dp[i][j] = Math.min(dp[i][j], dp[i][j-1] + 1);
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (grid[i][j] == 1) continue;
                if (i+1 < n) dp[i][j] = Math.min(dp[i][j], dp[i+1][j] + 1);
                if (j+1 < n) dp[i][j] = Math.min(dp[i][j], dp[i][j+1] + 1);
            }
        }
        int res = -1;
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j)
                if (grid[i][j] == 0)
                    res = Math.max(res, dp[i][j]);

        if (res == 201) return -1;
        else return res;
    }
}
```

##BFS
![](../pic/As_Far_from_Land_as_Possible03.png)
```java
class Solution {
    public int maxDistance(int[][] grid) {
        int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int n = grid.length;
        Queue<int[]> queues = new LinkedList<>();
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                if(grid[i][j] == 1) 
                    queues.offer(new int[]{i, j});
        boolean flag = false;
        int[] tmp = null;
        while(!queues.isEmpty()) {
            tmp = queues.poll();
            for(int i = 0; i < 4; i++) {
                int nx = tmp[0] + dir[i][0];
                int ny = tmp[1] + dir[i][1];
                if(nx<0 || nx>=n || ny<0 || ny>=n || grid[nx][ny] != 0)
                    continue;
                grid[nx][ny] = grid[tmp[0]][tmp[1]] + 1;
                flag = true;
                queues.offer(new int[]{nx, ny});
            }
        }
        if(tmp == null || !flag)
            return -1;

        return grid[tmp[0]][tmp[1]]-1;
    }
}
```