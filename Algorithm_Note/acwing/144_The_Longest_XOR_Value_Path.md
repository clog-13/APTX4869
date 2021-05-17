# 144. 最长异或值路径

给定一个树，树上的边都具有权值。

树中一条路径的异或长度被定义为路径上所有边的权值的异或和。

⊕ 为异或符号。

给定上述的具有 n 个节点的树，你能找到异或长度最大的路径吗？

#### 输入格式

第一行包含整数 n，表示树的节点数目。

接下来 n−1 行，每行包括三个整数 u，v，w，表示节点 u 和节点 v 之间有一条边权重为 w。

#### 输出格式

输出一个整数，表示异或长度最大的路径的最大异或和。

#### 数据范围

1≤n≤100000, 0≤u,v<n, 0≤w<2^31^

#### 输入样例：

```
4
0 1 3
1 2 4
1 3 6
```

#### 输出样例：

```
7
```

#### 样例解释

样例中最长异或值路径应为 `0->1->2`，值为 7(=3⊕4)



## 字典树

```java
import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[][] edges = new int[N - 1][3];
        for (int i = 0; i < N - 1; ++i) {
            edges[i][0] = sc.nextInt(); edges[i][1] = sc.nextInt();
            edges[i][2] = sc.nextInt();
        }
        List<int[]>[] graph = buildGraph(edges, N);  // 原始数据建图
        int[] arr = new int[N];
        dfs(arr, graph, 0, -1, 0);  // 以0为根 生成前缀树
        System.out.println(maxXor(arr));
    }

    private static void dfs(int[] arr, List<int[]>[] graph, int u, int p, int xor) {
        arr[u] = xor;
        for (int[] next: graph[u]) {
            if (next[0] != p) {
                dfs(arr, graph, next[0], u, xor ^ next[1]);
            }
        }
    }

    private static int maxXor(int[] arr) {
        Node tr = new Node();
        for (int a : arr) insert(a, tr);
        int res = 0;
        for (int a : arr) {
            res = Math.max(res, query(a, tr, res));
        }
        return res;
    }

    private static int query(int a, Node tr, int curMax) {
        int res = 0;
        for (int i = 30; i >= 0; i--) {
            int cur = (a >> i) & 1;
            if (tr.sons[1 ^ cur] != null) {
                res ^= (1 << i);
                tr = tr.sons[cur ^ 1];
            } else {
                tr = tr.sons[cur];
            }
            if (curMax > (res|(1<<(i+1))-1)) return curMax;
        }
        return res;
    }

    private static void insert(int a, Node tr) {
        for (int i = 30; i >= 0; i--) {
            int cur = (a >> i) & 1;
            if (tr.sons[cur] == null) tr.sons[cur] = new Node();
            tr = tr.sons[cur];
        }
    }

    private static List<int[]>[] buildGraph(int[][] edges, int N) {
        List<int[]>[] graph = new ArrayList[N];
        for (int i = 0; i < N; i++) graph[i] = new ArrayList<>();
        for (int[] e: edges) {
            graph[e[0]].add(new int[]{e[1], e[2]});
            graph[e[1]].add(new int[]{e[0], e[2]});
        }
        return graph;
    }

    static class Node {
        Node[] sons;
        public Node() {
            sons = new Node[2];
        }
    }
}
```

