# 01 矩阵
给定一个由 0 和 1 组成的矩阵，找出每个元素到最近的 0 的距离。

两个相邻元素间的距离为 1 。

**示例:**
输入:
>0 0 0
0 1 0
1 1 1

输出:
>0 0 0
0 1 0
1 2 1

## DP
```java
class Solution {
    public int[][] updateMatrix(int[][] matrix) {
        int row = matrix.length, col = matrix[0].length;
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                if(matrix[i][j] == 1) matrix[i][j] = row + col;
                if(i > 0) matrix[i][j] = Math.min(matrix[i][j], matrix[i-1][j] + 1);
                if(j > 0) matrix[i][j] = Math.min(matrix[i][j], matrix[i][j-1] + 1);
            }
        }
        for(int i = row-1; i >= 0; i--) {
            for(int j = col-1; j >= 0; j--) {
                if(i < row-1) matrix[i][j] = Math.min(matrix[i][j], matrix[i+1][j] + 1);
                if(j < col-1) matrix[i][j] = Math.min(matrix[i][j], matrix[i][j+1] + 1);
            }
        }
        return matrix;
    }
}
```

## BFS
```java
class Solution {
    int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    public int[][] updateMatrix(int[][] matrix) {
        int row = matrix.length, col = matrix[0].length;
        Queue<int[]> queue = new LinkedList<>();
        for(int i = 0; i < row; i++) 
            for(int j = 0; j < col; j++) {
                if(matrix[i][j] == 0)
                    queue.offer(new int[]{i, j});
                else 
                    matrix[i][j] = row + col;
            }

        while(!queue.isEmpty()) {
            int[] cur = queue.poll();
            for(int i = 0; i < 4; i++) {
                int tx = cur[0] + dir[i][0];
                int ty = cur[1] + dir[i][1];
                if(tx >= 0 && tx < row && ty >= 0 && ty < col
                  && matrix[tx][ty] > matrix[cur[0]][cur[1]] + 1) {
                    matrix[tx][ty] = matrix[cur[0]][cur[1]] + 1;
                    queue.offer(new int[]{tx, ty});
                }
            }
        }
        return matrix;
    }
}
```
## DFS
```java
class Solution {
    private int row;
    private int col;
    private int[][] dir = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public int[][] updateMatrix(int[][] matrix) {
        row = matrix.length;
        col = matrix[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // 优化：如果元素在 0 附近，保留元素值 1，不在 0 附近，初始化为一个较大值
                if (matrix[i][j] == 1
                        && !((i > 0 && matrix[i - 1][j] == 0)
                        || (i < row - 1 && matrix[i + 1][j] == 0)
                        || (j > 0 && matrix[i][j - 1] == 0)
                        || (j < col - 1 && matrix[i][j + 1] == 0))) {
                    matrix[i][j] = row + col;
                }
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                // 优化：将元素值为 1 的点作为深搜起点，降低递归深度
                if (matrix[i][j] == 1) {
                    updateMatrix(matrix, i, j);
                }
            }
        }
        return matrix;
    }

    private void updateMatrix(int[][] matrix, int r, int c) {
        // 搜索上下左右四个方向
        for (int[] v : dir) {
            int nr = r + v[0], nc = c + v[1];
            if (nr >= 0 && nr < row
                    && nc >= 0 && nc < col
                    && matrix[nr][nc] > matrix[r][c] + 1) {
                matrix[nr][nc] = matrix[r][c] + 1;
                updateMatrix(matrix, nr, nc);
            }
        }
    }
}
```
