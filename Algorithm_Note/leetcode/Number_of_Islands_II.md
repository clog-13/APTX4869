# 305. 岛屿数量 II
假设你设计一个游戏，用一个 m 行 n 列的 2D 网格来存储你的游戏地图。

起始的时候，每个格子的地形都被默认标记为「水」。我们可以通过使用 addLand 进行操作，将位置 (row, col) 的「水」变成「陆地」。

你将会被给定一个列表，来记录所有需要被操作的位置，然后你需要返回计算出来 每次 addLand 操作后岛屿的数量。

注意：一个岛的定义是被「水」包围的「陆地」，通过水平方向或者垂直方向上相邻的陆地连接而成。你可以假设地图网格的四边均被无边无际的「水」所包围。

请仔细阅读下方示例与解析，更加深入了解岛屿的判定。

**示例:**
输入: m = 3, n = 3, positions = [[0,0], [0,1], [1,2], [2,1]]
输出: [1,1,2,3]

### 并查集
```java
class Solution {
    int[] dx = { 0, 0,-1, 1};
    int[] dy = {-1, 1, 0, 0};
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> res = new ArrayList<>();
        int[][] map = new int[m][n];
        UF uf = new UF(m*n);
        for (int s = 0; s < positions.length; s++) {
            int x = positions[s][0];
            int y = positions[s][1];
            if (map[x][y] == 1) {
                res.add(uf.cnt);
                continue;
            }
            map[x][y] = 1;
            uf.cnt++;

            for (int i = 0; i < 4; i++) {
                int tx = x + dx[i];
                int ty = y + dy[i];
                if (tx >= 0 && tx < m && ty >= 0 && ty < n) {
                    if (map[tx][ty] == 1) uf.union(x*n+y, tx*n+ty);
                }
            }
            res.add(uf.cnt);
        }
        return res;
    }

    private class UF {
        int[] parent;
        int cnt = 0;

        public UF(int N) {
            parent = new int[N];
            for (int i = 0; i < N; i++)
                parent[i] = i;
        }

        private void union(int a, int b) {
            int pa = find(a);
            int pb = find(b);
            if (pa != pb) {
                parent[pb] = pa;
                cnt--;
            }
        }

        private int find(int x) {
            while (x != parent[x]) {
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return x;
        }
    }
}
```