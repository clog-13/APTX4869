# 1134. 最短路计数

给出一个 N 个顶点 M 条边的无向无权图，顶点编号为 1 到 N。

问从顶点 1 开始，到其他每个点的最短路有几条。

#### 输入格式

第一行包含 2 个正整数 N,M，为图的顶点数与边数。

接下来 M 行，每行两个正整数 x,y，表示有一条顶点 x 连向顶点 y 的边，请注意可能有自环与重边。

#### 输出格式

输出 N 行，每行一个非负整数，第 i 行输出从顶点 1 到顶点 i 有多少条不同的最短路，由于答案有可能会很大，你只需要输出对 100003 取模后的结果即可。

如果无法到达顶点 i 则输出 0。

#### 数据范围

1≤N≤10^5, 1≤M≤2×10^5

#### 输入样例：

```
5 7
1 2
1 3
2 4
3 4
2 3
4 5
4 5
```

#### 输出样例：

```
1
1
1
2
4
```



## 最短路 拓扑排序(队列)

```java
import java.util.*;
class Main {
    int N, M, idx, maxN = 100010, maxM = maxN*4, MOD = 100003, INF = 0x3f3f3f3f;
    int[] dist = new int[maxN], res = new int[maxN], info = new int[maxN];
    int[] from = new int[maxM], to = new int[maxM];

    public static void main(String[] args) {
        new Main().init();
    }

    void init() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); M = sc.nextInt();
        Arrays.fill(info, -1);
        while (M-- > 0) {
            int a = sc.nextInt(), b = sc.nextInt();
            add(a, b); add(b, a);
        }

        dijkstra();

        for (int i = 1; i <= N; i++) System.out.println(res[i]);
    }

    void dijkstra() {
        Arrays.fill(dist, INF);
        dist[1] = 0; res[1] = 1;

        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        while(!queue.isEmpty()) {
            int cur = queue.poll();
            for (int i = info[cur]; i != -1; i = from[i]) {
                int t = to[i];
                if (dist[t] > dist[cur]+1) {
                    dist[t] = dist[cur]+1;
                    queue.add(t);
                    res[t] = res[cur];  // t点最短距离被更新, 方案数也要被更新
                } else if (dist[t] == dist[cur]+1) {
                    res[t] = (res[t]+res[cur]) % MOD;  // 累加方案数
                }
            }
        }
    }

    void add(int a, int b) {
        from[idx] = info[a];
        to[idx] = b;
        info[a] = idx++;
    }
}
```

