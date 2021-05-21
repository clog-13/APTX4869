# 1088. 旅行问题

John 打算驾驶一辆汽车周游一个环形公路。

公路上总共有 n 个车站，每站都有若干升汽油（有的站可能油量为零），每升油可以让汽车行驶一千米。

John 必须从某个车站出发，一直按顺时针（或逆时针）方向走遍所有的车站，并回到起点。

在一开始的时候，汽车内油量为零，John 每到一个车站就把该站所有的油都带上（起点站亦是如此），行驶过程中不能出现没有油的情况。

任务：判断以每个车站为起点能否按条件成功周游一周。

#### 输入格式

第一行是一个整数 n，表示环形公路上的车站数；

接下来 n 行，每行两个整数 pi,di，分别表示表示第 i 号车站的存油量和第 i 号车站到 **顺时针方向** 下一站的距离。

#### 输出格式

输出共 n 行，如果从第 i 号车站出发，一直按顺时针（或逆时针）方向行驶，能够成功周游一圈，则在第 i 行输出 TAK，否则输出 NIE。

#### 数据范围

3≤n≤10^6^, 0≤pi≤2×10^9^, 0≤di≤2×10^9^

#### 输入样例：

```
5
3 1
1 2
5 2
0 1
5 4
```

#### 输出样例：

```
TAK
NIE
TAK
NIE
TAK
```



## 单调队列 + 前缀和

```java
import java.io.*;

public class Main {
    static int maxN = 200010;
    static int[] oil = new int[maxN], dist = new int[maxN], q = new int[maxN];
    static long[] preSum = new long[maxN];
    static boolean[] st = new boolean[maxN];  // 表示从i点出发是否有解

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        for (int i = 1; i <= N; i++) {
            String[] str = br.readLine().split(" ");
            oil[i] = Integer.parseInt(str[0]);
            dist[i] = Integer.parseInt(str[1]);
        }

        // 顺时针
        for (int i = 1; i <= N; i++) preSum[i] = preSum[N+i] = oil[i] - dist[i];
        for (int i = 1; i <= N*2; i++) preSum[i] += preSum[i - 1];  // 剩余油量
        int hh = 0, tt = -1;
        for (int i = N*2; i >= 1; i--) {  // 倒序遍历前缀数组
            if (hh<=tt && i <= q[hh]-N) hh++;
            while (hh<=tt && preSum[i] <= preSum[q[tt]]) tt--;  // 最小栈
            q[++tt] = i;
            if (i<=N && preSum[q[hh]] - preSum[i-1] >= 0) st[i] = true;  // i<=N
        }

        // 逆时针
        dist[0] = dist[N];
        for (int i = N; i >= 1; i--) preSum[i] = preSum[N+i] = oil[i] - dist[i - 1];
        for (int i = N*2; i >= 1; i--) preSum[i] += preSum[i+1];  // 反向前缀和 (q[hh] < i+1)
        hh = 0; tt = -1;
        for (int i = 1; i <= N*2; i++) {  // 正序遍历前缀数组
            if (hh<=tt && i >= q[hh]+N) hh++;
            while (hh<=tt && preSum[i] <= preSum[q[tt]]) tt--;  // 最小栈
            q[++tt] = i;
            if (i>=N+1 && preSum[q[hh]] - preSum[i+1] >= 0) st[i-N] = true;  // i>=N+1
        }

        for (int i = 1; i <= N; i++) {
            if (st[i]) bw.write("TAK\n");
            else bw.write("NIE\n");
        }
        bw.flush();
    }
}
```

