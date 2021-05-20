# 1137. 选择最佳线路

现在给定你一张城市交通路线图，上面包含城市的公交站台以及公交线路的具体分布。

已知城市中共包含 n 个车站（编号1~n）以及 m 条公交线路。

每条公交线路都是 **单向的**，从一个车站出发直接到达另一个车站，两个车站之间可能存在多条公交线路。

琪琪的朋友住在 s 号车站附近。

琪琪可以在任何车站选择换乘其它公共汽车。

请找出琪琪到达她的朋友家（附近的公交车站）需要花费的最少时间。

#### 输入格式

输入包含多组测试数据。

每组测试数据第一行包含三个整数 n,m,s，分别表示车站数量，公交线路数量以及朋友家附近车站的编号。

接下来 m 行，每行包含三个整数 p,q,t，表示存在一条线路从车站 p 到达车站 q，用时为 t。

接下来一行，包含一个整数 w，表示琪琪家附近共有 w 个车站，她可以在这 w 个车站中选择一个车站作为始发站。

再一行，包含 w 个整数，表示琪琪家附近的 w 个车站的编号。

#### 输出格式

每个测试数据输出一个整数作为结果，表示所需花费的最少时间。

如果无法达到朋友家的车站，则输出 -1。

每个结果占一行。

#### 数据范围

n≤1000,m≤20000,  1≤s≤n,  0<w<n,  0<t≤1000

#### 输入样例：

```
5 8 5
1 2 2
1 5 3
1 3 4
2 4 7
2 5 6
2 3 5
3 5 1
4 5 1
2
2 3
4 3 4
1 2 3
1 3 4
2 3 2
1
1
```

#### 输出样例：

```
1
-1
```



## 最短路 （超级源点）

也可以反转边，从尾(E)找到头(虚拟源点)

```java
import java.util.*;
class Main {
    int N, M, E, idx, maxN = 1010, maxM = 40010, INF = 0x3f3f3f3f;
    int[] dist = new int[maxN], info = new int[maxN], vis = new int[maxN];
    int[] from = new int[maxM], to = new int[maxM], val = new int[maxM];

    public static void main(String[] args) {
        new Main().init();
    }

    void init() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            N = sc.nextInt(); M = sc.nextInt(); E = sc.nextInt();
            Arrays.fill(info, -1); idx = 0;
            while (M-- > 0) {
                int a = sc.nextInt(), b = sc.nextInt(), c = sc.nextInt();
                add(a, b, c);  // 有向边
            }
            int T = sc.nextInt();  // 超级虚拟源点 (琪琪可以选择一个车站作为始发站)
            while (T-- > 0) add(0, sc.nextInt(), 0);

            dijk();

            if (dist[E] == INF) System.out.println(-1);
            else System.out.println(dist[E]);
        }
    }

    void dijk() {
        Arrays.fill(dist, INF); Arrays.fill(vis, 0);
        dist[0] = 0;
        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.offer(new Node(0, 0));  // 虚拟源点
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            int id = cur.id;
            if (vis[id] == 1) continue;
            vis[id] = 1;

            for (int i = info[id]; i != -1; i = from[i]) {
                int t = to[i];
                if (dist[t] > dist[id]+val[i]) {
                    dist[t] = dist[id]+val[i];
                    queue.offer(new Node(t, dist[t]));
                }
            }
        }
    }

    void add(int a, int b, int c) {
        from[idx] = info[a];
        to[idx] = b;
        val[idx] = c;
        info[a] = idx++;
    }

    static class Node implements Comparable<Node> {
        int id, dis;
        public Node(int i, int d) {
            id = i; dis = d;
        }

        @Override
        public int compareTo(Node node) {
            return dis - node.dis;
        }
    }
}
```

