# 1135. 新年好

重庆城里有 n 个车站，m 条 **双向** 公路连接其中的某些车站。

每两个车站最多用一条公路连接，从任何一个车站出发都可以经过一条或者多条公路到达其他车站，但不同的路径需要花费的时间可能不同。

在一条路径上花费的时间等于路径上所有公路需要的时间之和。

佳佳的家在车站 1，他有五个亲戚，分别住在车站 a,b,c,d,e。

过年了，他需要从自己的家出发，拜访每个亲戚（顺序任意），给他们送去节日的祝福。

怎样走，才需要最少的时间？

#### 输入格式

第一行：包含两个整数 n,m，分别表示车站数目和公路数目。

第二行：包含五个整数 a,b,c,d,e，分别表示五个亲戚所在车站编号。

以下 m 行，每行三个整数 x,y,t，表示公路连接的两个车站编号和时间。

#### 输出格式

输出仅一行，包含一个整数 T，表示最少的总时间。

#### 数据范围

1≤n≤50000, 1≤m≤105, 1<a,b,c,d,e≤n, 1≤x,y≤n, 1≤t≤100

#### 输入样例：

```
6 6
2 3 4 5 6
1 2 8
2 3 3
3 4 4
4 5 5
5 6 2
1 6 7
```

#### 输出样例：

```
21
```



## 最短路 + DFS

```java
import java.util.*;
import java.io.*;

public class Main{
    static int maxN = 50010, maxM = 100010 * 2;
    static int N, M, INF = 0x3f3f3f3f;
    static boolean[] vis = new boolean[maxN];
    static int[] id = new int[maxN], info = new int[maxN];
    static int[] from = new int[maxM], to = new int[maxM], val = new int[maxM];
    static int[][] dists = new int[6][maxN];
    static int idx = 0;
    static int res = INF;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        M = Integer.parseInt(str[1]);

        str = br.readLine().split(" ");
        id[0] = 1;
        for (int i = 1; i <= 5; i++) id[i] = Integer.parseInt(str[i-1]);

        Arrays.fill(info, -1);
        while (M-- > 0) {
            str = br.readLine().split(" ");
            int a = Integer.parseInt(str[0]);
            int b = Integer.parseInt(str[1]);
            int c = Integer.parseInt(str[2]);
            add(a, b, c); add(b, a, c);
        }

        for (int i = 0; i <= 5; i++) {  // 对每个起点构建最短数组（结果保存在不同数组）
//            spfa(id[i], dists[i]);
            dijkstra(id[i], dists[i]);
        }
        
        Arrays.fill(vis, false);
        dfs(1, 0, 0);

        System.out.println(res);
    }

    private static void dijkstra(int start, int[] dist) {
        Arrays.fill(vis, false);
        Arrays.fill(dist, INF);
        dist[start] = 0;
        PriorityQueue<Node> queue = new PriorityQueue<>((a, b) -> (a.dis - b.dis));
        queue.add(new Node(start, 0));

        while (!queue.isEmpty()) {
            int cur = queue.poll().ver;
            if (vis[cur]) continue;
            vis[cur] = true;
            for (int i = info[cur]; i != -1; i = from[i]) {
                int t = to[i];
                if (dist[t] > dist[cur] + val[i]) {
                    dist[t] = dist[cur] + val[i];
                    queue.add(new Node(t, dist[t]));
                }
            }
        }
    }

    private static void spfa(int start, int[] dist) {
        Arrays.fill(vis, false);
        Arrays.fill(dist, INF);
        dist[start] = 0;
        Queue<Integer> heap = new LinkedList<>();
        heap.add(start);

        while (!heap.isEmpty()) {
            int cur = heap.poll();
            vis[cur] = false;
            for (int i = info[cur]; i != -1; i = from[i]) {
                int t = to[i];
                if (dist[t] > dist[cur] + val[i]) {
                    dist[t] = dist[cur] + val[i];
                    if (!vis[t]) {
                        heap.add(t);
                        vis[t] = true;
                    }
                }
            }
        }
    }

    // u表示已经走了多少个点，start表示当前点，dis表示已走路程
    private static void dfs(int u, int start, int dis) {
        if (dis >= res) return; // 最优性剪枝
        if (u > 5) {
            res = dis;
            return;
        }
        for (int i = 1; i <= 5; i++) {
            if (vis[i]) continue;

            vis[i] = true;
            dfs(u+1, i, dis + dists[start][id[i]]);
            vis[i] = false;
        }
    }

    private static void add(int a,int b,int c) {
        from[idx] = info[a];
        to[idx] = b;
        val[idx] = c;
        info[a] = idx++;
    }

    private static class Node {
        int ver, dis;
        public Node (int v, int d) {
            ver = v; dis = d;
        }
    }
}
```

