# 934. 最短的桥
在给定的二维二进制数组 A 中，存在两座岛。（岛是由四面相连的 1 形成的一个最大组。）

现在，我们可以将 0 变为 1，以使两座岛连接起来，变成一座岛。

返回必须翻转的 0 的最小数目。（可以保证答案至少是 1。）

**示例：**
输入：[[0,1,0],[0,0,0],[0,0,1]]
输出：2

## DFS + BFS
```java
import java.util.*;

class Solution {
    private int N;
    int[][] A;
    int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    Queue<Integer> queue = new LinkedList<>();
    public int shortestBridge(int[][] A) {
        N = A.length;
        this.A = A;

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(A[i][j] == 1) {
                    dfs(i, j);      // 处理第一个岛
                    return bfs();   // 处理第二个岛，返回结果
                }
            }
        }
        return 0;
    }

    private void dfs(int x, int y) {
        if(x >= 0 && x < N && y >= 0 && y < N) {
            if(A[x][y] == 1) {          // 如果是第一个岛相连的部分
                A[x][y] = 2;
                for(int i = 0; i < 4; i++)
                    dfs(x+dir[i][0], y+dir[i][1]);
            }else if(A[x][y] == 0) {    // 如果是海洋(且在第一个岛旁边)
                A[x][y] = -1;
                queue.offer(x*N + y);
            }
        }
    }

    private int bfs() {
        int res = 1;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                int cur = queue.poll();
                for(int j = 0; j < 4; j++) {
                    int tx = (cur / N) + dir[j][0];
                    int ty = (cur % N) + dir[j][1];
                    if(tx >= 0 && tx < N && ty >= 0 && ty < N) {
                        if(A[tx][ty] == 1) {        // 如果是另一个岛
                            return res;
                        }else if(A[tx][ty] == 0) {  // 如果是海洋
                            A[tx][ty] = -1;         // 剪枝
                            queue.offer(tx*N + ty);
                        }
                    }
                }
            }
            res++;
        }
        return 0;
    }
}
```


## DFS + BFS (TLE)
```java
class Solution {
    int[][] arr;
    int row, col;
    int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    public int shortestBridge(int[][] A) {
        arr = A;
        row = A.length;
        col = A[0].length;

        // DFS
        for(int i = 0; i < row; i++)
            for(int j = 0; j < col; j++)
                if(arr[i][j] == 1) {
                    dfs(i, j);
                    return bfs();
                }

        return -1;
    }

    private void dfs(int x, int y) {
        arr[x][y] = -1;
        for(int i = 0; i < 4; i++) {
            int tx = x + dir[i][0];
            int ty = y + dir[i][1];
            if(tx >= 0 && tx < row && ty >= 0 && ty < col
                    && arr[tx][ty] == 1) {
                dfs(tx, ty);
            }
        }
    }

    private int bfs() {
        int res = Integer.MAX_VALUE;
        for(int i = 0; i < row; i++)
            for(int j = 0; j < col; j++)
                if(arr[i][j] == 1) {
                    int r = bfs(i, j);
                    if(r >= 0) res = Math.min(res, bfs(i, j));
                }
        return res;
    }

    private int bfs(int x, int y) {
        Set<Integer> set = new HashSet<>();
        set.add(x*row + y);
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, y, -1});
        while(!queue.isEmpty()) {
            int[] cur = queue.poll();
            if(set.contains(cur[0]*row + cur[1]));
            for(int i = 0; i < 4; i++) {
                int tx = cur[0] + dir[i][0];
                int ty = cur[1] + dir[i][1];
                if(tx >= 0 && tx < row && ty >= 0 && ty < col) {
                    if(arr[tx][ty] == -1)
                        return cur[2] + 1;
                    else if(arr[tx][ty] == 0)
                        queue.offer(new int[]{tx, ty, cur[2]+1});
                    set.add(tx*row + ty);
                }
            }
        }
        return -1;
    }
}

```