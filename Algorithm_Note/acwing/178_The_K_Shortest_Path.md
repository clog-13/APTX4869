# 178. 第K短路

给定一张N个点（编号1,2…N），M条边的有向图，求从起点S到终点T的第K短路的长度，路径允许重复经过点或边。

**注意：** 每条最短路中至少要包含一条边。

#### 输入格式

第一行包含两个整数N和M。

接下来M行，每行包含三个整数A,B和L，表示点A与点B之间存在有向边，且边长为L。

最后一行包含三个整数S,T和K，分别表示起点S，终点T和第K短路。

#### 输出格式

输出占一行，包含一个整数，表示第K短路的长度，如果第K短路不存在，则输出“-1”。

#### 数据范围

1≤S,T≤N≤1000, 0≤M≤105, 1≤K≤1000, 1≤L≤100

#### 输入样例：

```
2 2
1 2 5
2 1 4
1 2 2
```

#### 输出样例：

```
14
```

## Dijkstra

```java
import java.io.*;
import java.util.*;

class Main {
    int N, M, S, E, K, maxN = 1010, maxM = 200010, INF = 0x3f3f3f3f, idx;
    int[] from = new int[maxM], to = new int[maxM], val = new int[maxM];
    int[] info = new int[maxN], rnfo = new int[maxN];
    int[] dist = new int[maxN], f = new int[maxN], st = new int[maxN];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }
    
    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]); M = Integer.parseInt(str[1]);

        Arrays.fill(info, -1); Arrays.fill(rnfo, -1);
        while (M-- > 0) {
            str = br.readLine().split(" ");
            int a = Integer.parseInt(str[0]), b = Integer.parseInt(str[1]);
            int c = Integer.parseInt(str[2]);
            add(0, a, b, c); add(1, b, a, c);
        }

        str = br.readLine().split(" ");
        S = Integer.parseInt(str[0]); E = Integer.parseInt(str[1]); K = Integer.parseInt(str[2]);
        if (S == E) K++;  // “最短路中至少要包含一条边”

        dijk();  // 反向遍历每个点最短路
        System.out.println(aStar());
    }

    void dijk() {
        PriorityQueue<PII> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a.dis));
        heap.add(new PII(E, 0));  // 反向
        Arrays.fill(dist, INF);
        dist[E] = 0;

        while (!heap.isEmpty()) {
            PII cur = heap.poll();
            int ver = cur.ver;

            if (st[ver] == 1) continue;
            st[ver] = 1;

            for (int i = rnfo[ver]; i != -1; i = from[i]) {
                int t = to[i];
                if (dist[t] > dist[ver] + val[i]) {
                    dist[t] = dist[ver] + val[i];
                    heap.add(new PII(t, dist[t]));
                }
            }
        }

        System.arraycopy(dist, 0, f, 0, maxN);  // 反向最短路的值做 启发值
    }

    int aStar() {
        PriorityQueue<Node> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a.f_val));
        heap.add(new Node(f[S], new PII(S, 0)));  // dijk的最短路做启发值(到E的最短值)
        Arrays.fill(st, 0);

        while (!heap.isEmpty()) {
            Node cur = heap.poll();
            int ver = cur.pii.ver, dis = cur.pii.dis;
            
            if (st[ver] >= K) continue;
            st[ver]++;  // 又一次遍历到了该点
            if (ver == E && st[ver] == K) return dis;  // 第K短路
            
            for (int i = info[ver]; i != -1; i = from[i]) {
                int t = to[i];
                if (st[t] < K) {  // 因为这里所以上面所有遍历到的点都要加一，剪枝
                    heap.add(new Node(dis+val[i]+f[t], new PII(t, dis+val[i])));
                }
            }
        }

        return -1;
    }

    void add(int r, int a, int b, int c) {
        to[idx] = b;
        val[idx] = c;
        if (r == 0) {
            from[idx] = info[a];
            info[a] = idx++;
        } else {
            from[idx] = rnfo[a];
            rnfo[a] = idx++;
        }
    }

    static class PII {
        int ver, dis;
        public PII(int i, int d) {
            ver = i; dis = d;
        }
    }

    static class Node {
        int f_val;  // 启发值
        PII pii;
        public Node(int v, PII p) {
            f_val = v;
            pii = p;
        }
    }
}
```

## SPFA

```java
import java.io.*;
import java.util.*;

class Main {
    static int N, M, S, E, K;
    static int maxN = 1010, INF = 0x3f3f3f3f;
    static int[] dist = new int[maxN], f = new int[maxN], st = new int[maxN];
    static List<Node>[] G = new ArrayList[1005];
    static List<Node>[] GG = new ArrayList[1005];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        M = Integer.parseInt(str[1]);

        for (int i = 0; i <= N; i++) G[i] = new ArrayList<>();
        for (int i = 0; i <= N; i++) GG[i] = new ArrayList<>();

        while (M-- > 0) {
            str = br.readLine().split(" ");
            int a = Integer.parseInt(str[0]);
            int b = Integer.parseInt(str[1]);
            int c = Integer.parseInt(str[2]);
            G[a].add(new Node(b, c));
            GG[b].add(new Node(a, c));
        }
        
        str = br.readLine().split(" ");
        S = Integer.parseInt(str[0]);
        E = Integer.parseInt(str[1]);
        K = Integer.parseInt(str[2]);
        if (S == E) K++;

        spfa();
        aStar();
    }

    private static void spfa() {
        Arrays.fill(dist, INF); dist[E] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(E);  // 反向
        
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            st[cur] = 0;
            int size = GG[cur].size();
            for (int i = 0; i < GG[cur].size(); i++) {
                Node tmp = GG[cur].get(i);
                if (dist[tmp.e] > dist[cur] + tmp.v) {
                    dist[tmp.e] = dist[cur] + tmp.v;
                    if (st[tmp.e] == 0) {
                        st[tmp.e] = 1;
                        queue.add(tmp.e);
                    }
                }
            }
        }
        System.arraycopy(dist, 0, f, 0, maxN);
    }

    private static void aStar() {
        if (dist[S] == INF) { System.out.println(-1); return; }
        PriorityQueue<PIII> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));
        heap.add(new PIII(0, new PII(S, 0)));
        int cnt = 0;
        while (!heap.isEmpty()) {
            PIII cur = heap.poll();
            if (cur.pii.ver == E) {
                if (++cnt == K) {
                    System.out.println(cur.pii.dis);
                    return;
                }
            }
            for (int i = 0; i < G[cur.pii.ver].size(); i++) {
                // dis + val[i] + f[t] = cur.pii.dis + G[cur.pii.ver].get(i).v + f[G[cur.pii.ver].get(i).e];
                // t = G[cur.pii.ver].get(i).e
                // dis + val[i] = cur.pii.dis + G[cur.pii.ver].get(i).v
                
                // heap.add(new PIII(dis+val[i]+f[t], new PII(t, dis+val[i])));
                // 启发值 路径+终点距离
                heap.add(new PIII(cur.pii.dis + G[cur.pii.ver].get(i).v + f[G[cur.pii.ver].get(i).e],
                         new PII(G[cur.pii.ver].get(i).e,
                         cur.pii.dis + G[cur.pii.ver].get(i).v)));
            }
        }

        System.out.println(-1);
    }

    private static class Node {
        int e, v;
        public Node (int ee, int vv) {e=ee; v=vv;};
    }

    private static class PII {
        int ver, dis;
        public PII(int i, int d) {
            ver = i; dis = d;
        }
    }

    private static class PIII {
        int val;
        PII pii;
        public PIII(int v, PII p) {
            val = v; pii = p;
        }
    }
}
```

