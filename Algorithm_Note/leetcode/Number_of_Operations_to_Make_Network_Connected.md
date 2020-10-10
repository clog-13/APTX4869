#1319. 连通网络的操作次数
用以太网线缆将 n 台计算机连接成一个网络，计算机的编号从 0 到 n-1。线缆用 connections 表示，其中 connections[i] = [a, b] 连接了计算机 a 和 b。

网络中的任何一台计算机都可以通过网络直接或者间接访问同一个网络中其他任意一台计算机。

给你这个计算机网络的初始布线 connections，你可以拔开任意两台直连计算机之间的线缆，并用它连接一对未直连的计算机。请你计算并返回使所有计算机都连通所需的最少操作次数。如果不可能，则返回 -1 。 

**示例：**
![](../pic/Number_of_Operations_to_Make_Network_Connected.png)
输入：n = 6, connections = [[0,1],[0,2],[0,3],[1,2],[1,3]]
输出：2

##并查集

```java
带权优化
class Solution {
    public int makeConnected(int n, int[][] connections) {
        if (connections.length < n - 1) return -1;
        UnionFind uf = new UnionFind(n);
        for(int i = 0; i < connections.length; i++)
            uf.union(connections[i][0], connections[i][1]);

        return uf.cout - 1;
    }

    private class UnionFind {
        int[] parent;
        int[] size;
        int cout;       // 连通分量个数
        public UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            cout = n;
            for(int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public void union(int i, int j) {
            int pi = find(i);
            int pj = find(j);

            if(pi != pj) {
                if(size[pi] < size[pj]) {
                    parent[pi] = pj;	// 小树去连大树
                    size[pj] += size[pi];
                }else {
                    parent[pj] = pi;
                    size[pi] += size[pj];
                }
                cout--;     // cout 注意要写在里面
            }
        }

        public int find(int x) {
            while(x != parent[x]) {
                parent[x] = parent[parent[x]];  // 路径压缩（隔代压缩）
                x = parent[x];
            }
            return x;
        }
    }
}


```

##DFS
```java
class Solution {
    List<Integer>[] graph;
    Set<Integer> vis = new HashSet<>();
    public int makeConnected(int n, int[][] connections) {
        if (connections.length < n-1) return -1;
        graph = new ArrayList[n];
        for(int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();
        for(int i = 0; i < connections.length; i++) {
            graph[connections[i][0]].add(connections[i][1]);
            graph[connections[i][1]].add(connections[i][0]);
        }
        int res = 0;
        for(int i = 0; i < n; i++) {
            if(!vis.contains(i)) {
                dfs(i);
                res++;
            }
        }
        return res-1;
    }

    public void dfs(int n) {
        vis.add(n);
        for(int i : graph[n])
            if(!vis.contains(i)) dfs(i);
    }
}
```