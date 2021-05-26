# 176. 装满的油箱

有N个城市（编号0、1…N-1）和M条道路，构成一张无向图。

在每个城市里边都有一个加油站，不同的加油站的单位油价不一样。

现在你需要回答不超过100个问题，在每个问题中，请计算出一架油箱容量为C的车子，从起点城市S开到终点城市E至少要花多少油钱？

**注意：** 假定车子初始时油箱是空的。

#### 输入格式

第一行包含两个整数N和M。

第二行包含N个整数，代表N个城市的单位油价，第i个数即为第i个城市的油价pi。

接下来M行，每行包括三个整数u,v,d，表示城市u与城市v之间存在道路，且车子从u到v需要消耗的油量为d。

接下来一行包含一个整数q，代表问题数量。

接下来q行，每行包含三个整数C、S、E，分别表示车子油箱容量、起点城市S、终点城市E。

#### 输出格式

对于每个问题，输出一个整数，表示所需的最少油钱。

如果无法从起点城市开到终点城市，则输出”impossible”。

每个结果占一行。

#### 数据范围

1≤N≤1000, 1≤M≤10000, 1≤pi≤100, 1≤d≤100, 1≤C≤100

#### 输入样例：

```
5 5
10 10 20 12 13
0 1 9
0 2 8
1 2 1
1 3 11
2 3 7
2
10 0 3
20 1 4
```

#### 输出样例：

```
170
impossible
```

## Dijk变形

```java
import java.util.*;

public class Main {
    int N, M, C, S, E, idx = 0, maxN = 1010, maxM = 200010, INF = 0x3f3f3f3f;
    int[] price = new int[maxN], info = new int[maxN];
    int[] from = new int[maxM], to = new int[maxM], val = new int[maxM];
    int[][] dist = new int[maxN][maxN], vis = new int[maxN][maxN];

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); M = sc.nextInt();
        for (int i = 0; i < N; i++) price[i] = sc.nextInt();

        Arrays.fill(info, -1);
        for (int i = 0; i < M; i++) {  // 构建图
            int a = sc.nextInt(), b = sc.nextInt(), c = sc.nextInt();
            add(a, b, c); add(b, a, c);
        }

        int T = sc.nextInt();
        while (T-- > 0) {  // 处理每组查询输入
            C = sc.nextInt(); S = sc.nextInt(); E = sc.nextInt();
            int res = dijk();
            if (res == -1) System.out.println("impossible");
            else System.out.println(res);
        }
    }

    int dijk() {
        for (int[] d : dist) Arrays.fill(d, INF);
        for (int[] v : vis) Arrays.fill(v, 0);
        dist[S][0] = 0;
        PriorityQueue<Node> heap = new PriorityQueue<>((a, b) -> (a.dis - b.dis));
        heap.add(new Node(0, S, 0));

        while (!heap.isEmpty()) {
            Node cur = heap.poll();
            int dis = cur.dis, idx = cur.idx, cout = cur.cout;

            if (idx == E) return dis;
            if (vis[idx][cout] == 1) continue;
            vis[idx][cout] = 1;

            // 这里用for或者if都可以, if会一直循环直到到一个能到下一站的油量(heap循环)，for则是全家桶
            if (cout < C) {  // // for (int i = cout; i < C; i++)
                if (dist[idx][cout+1] > dist[idx][cout]+price[idx]) {
                    dist[idx][cout+1] = dist[idx][cout]+price[idx];	// 加上一升油的钱
                    heap.add(new Node(dist[idx][cout+1], idx, cout+1));	// 加上一升油的状态
                }
            }
            for (int i = info[idx]; i != -1; i = from[i]) {
                int t = to[i];
                if (cout >= val[i] && dist[t][cout-val[i]] > dis) {
                    dist[t][cout-val[i]] = dis;  // dis：油价 val[i]：路程
                    heap.add(new Node(dis, t, cout-val[i]));  // cout：剩余油量
                }
            }
        }

        return -1;
    }

    void add(int a, int b, int c) {
        from[idx] = info[a];
        to[idx] = b;
        val[idx] = c;
        info[a] = idx++;
    }

    static class Node {
        int dis, idx, cout;

        public Node(int d, int i, int c) {
            dis = d; idx = i; cout = c;
        }
    }
}
```



