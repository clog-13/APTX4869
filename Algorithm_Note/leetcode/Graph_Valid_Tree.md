# 261. 以图判树
给定从 0 到 n-1 标号的 n 个结点，和一个无向边列表（每条边以结点对来表示），请编写一个函数用来判断这些边是否能够形成一个合法有效的树结构。

**示例**：
输入: n = 5, 边列表 edges = [[0,1], [1,2], [2,3], [1,3], [1,4]]
输出: false

### 并查集
```java
class Solution {
    public boolean validTree(int n, int[][] edges) {
    	if (edges.length >= n) return false;
        UF uf = new UF(n);
        for (int[] e : edges)
            if (!uf.union(e[0], e[1])) return false;    // 环
        return uf.cnt == 1; // 是否是同一棵树
    }

    private class UF {
        int[] parent, rank;
        int cnt;

        public UF(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
            rank = new int[n];
            cnt = n;
        }

        private int find(int x) {
            while (parent[x] != x) {
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return x;
        }

        private boolean union(int a, int b) {
            int pa = find(a);
            int pb = find(b);
            if (pa == pb) return false; // 环

            if (rank[pa] == rank[pb]) {
                parent[pa] = pb;
                rank[pb]++;
            } else if (rank[pa] < rank[pb]) {
                parent[pa] = pb;
            } else if (rank[pa] > rank[pb]) {
                parent[pb] = pa;
            }
            cnt--;
            return true;
        }
    }
}
```

### 邻接表
```java
class Solution {
    int id = 0, maxN = 10010;
    int[] info = new int[maxN], from = new int[maxN], to = new int[maxN];

    public boolean validTree(int n, int[][] edges) {
    	if (edges.length >= n) return false;
        int[][] graph = new int[n][n];
        Arrays.fill(info, -1);
        for (int[] e : edges) {
            add(e[0], e[1]);
            add(e[1], e[0]);
        }

        boolean[] vis = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        vis[0] = true;
        queue.add(0);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int i = info[cur]; i != -1; i = from[i]) {
                int t = to[i];
                if (graph[cur][t] != 1) {
                    if (vis[t]) return false;
                    vis[t] = true;	// 环
                    graph[t][cur] = 1;
                    queue.add(t);
                }
            }
        }

        for (int i = 0; i < n; i++)
            if (!vis[i]) return false;	// 是否是同一棵树
        return true;
    }

    private void add(int a, int b) {
        from[id] = info[a];
        to[id] = b;
        info[a] = id++;
    }
}
```