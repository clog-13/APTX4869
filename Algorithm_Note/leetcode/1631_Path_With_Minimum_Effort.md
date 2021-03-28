# 1631. 最小体力消耗路径

你准备参加一场远足活动。给你一个二维 `rows x columns` 的地图 `heights` ，其中 `heights[row][col]` 表示格子 `(row, col)` 的高度。一开始你在最左上角的格子 `(0, 0)` ，且你希望去最右下角的格子 `(rows-1, columns-1)` （注意下标从 **0** 开始编号）。你每次可以往 **上**，**下**，**左**，**右** 四个方向之一移动，你想要找到耗费 **体力** 最小的一条路径。

一条路径耗费的 **体力值** 是路径上相邻格子之间 **高度差绝对值** 的 **最大值** 决定的。

请你返回从左上角走到右下角的最小 **体力消耗值** 。



**示例：**

```
输入：heights = 
[[1,2,1,1,1]
,[1,2,1,2,1]
,[1,2,1,2,1]
,[1,2,1,2,1]
,[1,1,1,2,1]]
输出：0
```



## 二分

从左上角开始进行深度优先搜索或者广度优先搜索，在搜索的过程中只允许经过边权不超过 x 的边，搜索结束后判断是否能到达右下角即可。

```java
class Solution {
    int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int minimumEffortPath(int[][] heights) {
        int row = heights.length, col = heights[0].length;
        int le = 0, ri = 1000000, res = 0;
        while (le < ri) {
            int mid = (le + ri) >> 1;
            Queue<int[]> queue = new LinkedList<int[]>();
            queue.offer(new int[]{0, 0});
            boolean[] vis = new boolean[row * col];
            vis[0] = true;
            while (!queue.isEmpty()) {
                int[] cell = queue.poll();
                int x = cell[0], y = cell[1];
                for (int i = 0; i < 4; ++i) {
                    int tx = x + dirs[i][0];
                    int ty = y + dirs[i][1];
                    if (tx >= 0 && tx < row && ty >= 0 && ty < col && 
                    !vis[tx * col + ty] && Math.abs(heights[x][y]-heights[tx][ty]) <= mid) {
                        queue.offer(new int[]{tx, ty});
                        vis[tx * col + ty] = true;
                    }
                }
            }
            if (vis[row * col - 1]) {
                res = mid;
                ri = mid;
            } else {
                le = mid + 1;
            }
        }
        return res;
    }
}
```



## 并查集

将图中的所有边按照权值从小到大进行排序，并依次加入并查集中。当我们加入一条权值为 x 的边之后，如果左上角和右下角从非连通状态变为连通状态，那么 x 即为答案。

```java
class Solution {
    public int minimumEffortPath(int[][] heights) {
        int row = heights.length, col = heights[0].length;
        List<int[]> edges = new ArrayList<int[]>();
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                int id = i * col + j;
                if (i > 0) {  // 横向联通量
                    edges.add(new int[]{id - col, id, Math.abs(heights[i][j] - heights[i - 1][j])});
                }
                if (j > 0) {  // 竖向联通量
                    edges.add(new int[]{id - 1, id, Math.abs(heights[i][j] - heights[i][j - 1])});
                }
            }
        }

        Collections.sort(edges, new Comparator<int[]>() {
            public int compare(int[] edge1, int[] edge2) {
                return edge1[2] - edge2[2];
            }
        });

        UnionFind uf = new UnionFind(row * col);
        int res = 0;
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1], v = edge[2];
            uf.unite(x, y);
            if (uf.connected(0, row * col - 1)) {
                res = v;
                break;
            }
        }
        return res;
    }
}

class UnionFind {
    int N, setCount;  // 当前连通分量数目
    int[] parent, size;

    public UnionFind(int N) {
        this.N = N;
        this.setCount = N;
        this.parent = new int[N];
        this.size = new int[N];
        Arrays.fill(size, 1);
        for (int i = 0; i < N; ++i) {
            parent[i] = i;
        }
    }
    
    public int findset(int x) {
        if (parent[x] != x) parent[x] = findset(parent[x]);
        return parent[x];
        // return parent[x] == x ? x : (parent[x] = findset(parent[x]));
    }
    
    public boolean unite(int x, int y) {
        x = findset(x);
        y = findset(y);
        if (x == y) return false;
        if (size[x] < size[y]) {
            int temp = x;
            x = y;
            y = temp;
        }
        parent[y] = x;  // 小size 联向 大size
        size[x] += size[y];
        setCount--;
        return true;
    }
    
    public boolean connected(int x, int y) {
        return findset(x) == findset(y);
    }
}
```



## aStar

```java
class Solution {
    int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int minimumEffortPath(int[][] heights) {
        int row = heights.length, col = heights[0].length;
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
            public int compare(int[] edge1, int[] edge2) {
                return edge1[2] - edge2[2];
            }
        });
        pq.offer(new int[]{0, 0, 0});

        int[] dist = new int[row * col];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;
        boolean[] vis = new boolean[row * col];
        while (!pq.isEmpty()) {
            int[] edge = pq.poll();
            int x = edge[0], y = edge[1], d = edge[2];
            int id = x * col + y;
            if (vis[id]) continue;
            vis[id] = true;
            if (x==row-1 && y==col-1) break;  // 剪枝
            for (int i = 0; i < 4; ++i) {
                int tx = x + dirs[i][0];
                int ty = y + dirs[i][1];
                if (tx >= 0 && tx < row && ty >= 0 && ty < col 
                && Math.max(d, Math.abs(heights[x][y]-heights[tx][ty]))<dist[tx*col+ty]) {
                    dist[tx * col + ty] = Math.max(d, Math.abs(heights[x][y] - heights[tx][ty]));
                    pq.offer(new int[]{tx, ty, dist[tx * col + ty]});
                }
            }
        }
        
        return dist[row * col - 1];
    }
}
```

