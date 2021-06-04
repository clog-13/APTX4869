# 456. 车站分级

一条单向的铁路线上，依次有编号为1, 2, …, n 的n个火车站。

每个火车站都有一个级别，最低为1级。

现有若干趟车次在这条线路上行驶，每一趟都满足如下要求：如果这趟车次停靠了火车站x，则始发站、终点站之间所有级别大于等于火车站x的都必须停靠。（注意：起始站和终点站自然也算作事先已知需要停靠的站点） 

例如，下表是5趟车次的运行情况。

其中，前4趟车次均满足要求，而第5趟车次由于停靠了3号火车站（2级）却未停靠途经的6号火车站（亦为2级）而不满足要求。

![](pic\456.jpg)

现有m趟车次的运行情况（全部满足要求），试推算这n个火车站至少分为几个不同的级别。

#### 输入格式

第一行包含 2 个正整数 n, m，用一个空格隔开。

第 i + 1 行（1 ≤ i ≤ m）中，首先是一个正整数 si（2 ≤ si ≤ n），表示第 i 趟车次有 si 个停靠站；接下来有si个正整数，表示所有停靠站的编号，从小到大排列。

每两个数之间用一个空格隔开。输入保证所有的车次都满足要求。

#### 输出格式

输出只有一行，包含一个正整数，即 n 个火车站最少划分的级别数。

#### 数据范围

1≤n,m≤1000

#### 输入样例：

```
9 3 
4 1 3 5 6 
3 3 5 6 
3 1 5 9 
```

#### 输出样例：

```
3
```

## 拓扑排序+最长路

临时停靠站 ≥ 始发站、终点站 

非停靠站＜停靠站

**之前车次停靠的站 < 之后车次不停靠的站（拓扑的关键）**

```java
import java.util.*;
import java.io.*;

public class Main {
    int N, M, maxN = 2010, maxM = 1000010, idx, qidx;
    int[] info = new int[maxN];
    int[] from = new int[maxM], to = new int[maxM], val = new int[maxM];
    int[] d = new int[maxN], qv = new int[maxN], dist = new int[maxN];
    boolean[] vis = new boolean[maxN];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]); M = Integer.parseInt(str[1]);

        Arrays.fill(info, -1);
        for (int i = 1; i <= M; i++) {  // 输入信息
            Arrays.fill(vis, false);
            str = br.readLine().split(" ");
            int start = N, end = 1;
            for (int j = 1; j < str.length; j++) {
                int x = Integer.parseInt(str[j]);
                start = Math.min(start, x); end = Math.max(end, x);
                vis[x] = true;
            }

            int ver = N + i;
            for (int j = start; j <= end; j++) {
                if (vis[j]) {  // 停靠点
                    add(ver, j, 1);   // 虚拟结点 向 停靠点 连一条权值为1的边
                    d[j]++;
                } else {  // 非停靠点
                    add(j, ver, 0);   // 非停点 向 虚拟结点 连一条权值为0的边
                    d[ver]++;
                }
            }
        }

        topsort();

        for (int i = 1; i <= N; i++) dist[i] = 1;  // 防止所有站都停靠
        for (int i = 0; i <= N+M; i++) {
            int cur = qv[i];
            for (int j = info[cur]; j != -1; j = from[j]) {
                int t = to[j];
                dist[t] = Math.max(dist[t], dist[cur] + val[j]);
            }
        }
        int res = 0;
        for (int i = 1; i <= N; i++) res = Math.max(res, dist[i]);
        System.out.println(res);
    }

    void topsort() {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N+M; i++) {
            if (d[i] == 0) {  // 全部班次都没有停靠过的点 || 全停靠的班次的虚拟节点
                queue.add(i);
                qv[qidx++] = i;
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();  // 遍历该节点为起始的所有边
            for (int i = info[cur]; i != -1; i = from[i]) {
                int t = to[i];
                if (--d[t] == 0) {
                    queue.add(t);
                    qv[qidx++] = t;
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
}
```



