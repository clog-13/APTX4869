# 383. 观光

每天公共汽车都会从一座城市开往另一座城市。

沿途汽车可能会在一些城市（零或更多）停靠。

旅行社计划旅途从 S 城市出发，到 F 城市结束。

由于不同旅客的景点偏好不同，所以为了迎合更多旅客，旅行社将为客户提供多种不同线路。

游客可以选择的行进路线有所限制，要么满足所选路线总路程为 S 到 F 的最小路程，要么满足所选路线总路程仅比最小路程多一个单位长度。

现在给定比荷卢经济联盟的公交路线图以及两个城市 S 和 F，请你求出旅行社最多可以为旅客提供多少种不同的满足限制条件的线路。

#### 输入格式

第一行包含整数 T，表示共有 T 组测试数据。

每组数据第一行包含两个整数 N 和 M，分别表示总城市数量和道路数量。

接下来 M 行，每行包含三个整数 A,B,L，表示有一条线路从城市 A 通往城市 B，长度为 L。

需注意，线路是 **单向的**，存在从 A 到 B 的线路不代表一定存在从 B 到 A 的线路，另外从**城市 A 到城市 B 可能存在多个不同的线路**。

接下来一行，包含两个整数 S 和 F，数据保证 S 和 F 不同，并且 S、F 之间至少存在一条线路。

#### 输出格式

每组数据输出一个结果，每个结果占一行。

数据保证结果不超过 10^9^。

#### 数据范围

2≤N≤1000,  1≤M≤10000,  1≤L≤1000， 1≤A,B,S,F≤N

#### 输入样例：

```
2
5 8
1 2 3
1 3 2
1 4 5
2 3 1
2 5 3
3 4 2
3 5 4
4 5 3
1 5
5 6
2 3 1
3 2 1
3 1 10
4 5 2
5 2 7
5 2 7
4 1
```

#### 输出样例：

```
3
2
```



## 最短路 计数

```java
import java.util.*;
class Main {
    int T, N, M, S, E, idx, INF = 0x3f3f3f3f, maxN = 1010, maxM = 10010;
    int[] from = new int[maxM], to = new int[maxM], val = new int[maxM];
    int[] info = new int[maxN];
    int[][] dist = new int[2][maxN], cout = new int[2][maxN], vis = new int[2][maxN];

    public static void main(String[] args) {
        new Main().init();
    }

    void init() {
        Scanner sc = new Scanner(System.in);
        T = sc.nextInt();
        while (T-- > 0) {
            N = sc.nextInt(); M = sc.nextInt();
            Arrays.fill(info, -1); idx = 0;
            while (M-- > 0) {
                int a = sc.nextInt(), b = sc.nextInt(), c = sc.nextInt();
                add(a, b, c);
            }
            S = sc.nextInt(); E = sc.nextInt();

            dijk();

            int res = cout[0][E];
            if (dist[0][E]+1 == dist[1][E]) res += cout[1][E];
            System.out.println(res);
        }
    }

    void dijk() {
        for (int i = 0; i < 2; i++) {
            Arrays.fill(dist[i], INF);
            Arrays.fill(cout[i], 0);
            Arrays.fill(vis[i], 0);
        }
        dist[0][S] = 0; cout[0][S] = 1;
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparing((a)->(a.dis)));
        queue.add(new Node(S, 0, 0));
        
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            int id = cur.id, type = cur.type;
            if (vis[type][id] == 1) continue;
            vis[type][id] = 1;

            for (int i = info[id]; i != -1; i = from[i]) {
                int t = to[i];
                if (dist[0][t] > dist[type][id] + val[i]) {
                    // dist[1][]为第二短路径,最后再判断是不是 最短+1
                    dist[1][t] = dist[0][t]; cout[1][t] = cout[0][t];  
                    dist[0][t] = dist[type][id] + val[i]; cout[0][t] = cout[type][id];
                    queue.offer(new Node(t, 0, dist[0][t]));
                    queue.offer(new Node(t, 1, dist[1][t]));
                } else if (dist[0][t] == dist[type][id]+val[i]) {
                    cout[0][t] += cout[type][id];
                } else if (dist[1][t] > dist[type][id]+val[i]) {
                    dist[1][t] = dist[type][id]+val[i]; cout[1][t] = cout[type][id];
                    queue.offer(new Node(t, 1, dist[1][t]));
                } else if (dist[1][t] == dist[type][id]+val[i]) {
                    cout[1][t] += cout[type][id];
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

    static class Node {
        int id, type, dis;

        public Node(int i, int t, int d) {
            id = i; type = t; dis = d;
        }
    }
}
```

