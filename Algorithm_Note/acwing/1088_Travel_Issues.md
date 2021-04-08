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



## 单调队列

```java
import java.io.*;

public class Main {
    static int N = 100000 * 2 + 10;
    static int[] oil = new int[N], dist = new int[N], q = new int[N];
    static long[] preSum = new long[N];
    static boolean[] st = new boolean[N];  // 表示从i点出发是否有解

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        for (int i = 1; i <= n; i++) {
            String[] str = br.readLine().split(" ");
            oil[i] = Integer.parseInt(str[0]);
            dist[i] = Integer.parseInt(str[1]);
        }
        
        // 顺时针
        for (int i = 1; i <= n; i ++) preSum[i] = preSum[i + n] = oil[i] - dist[i];
        for (int i = 1; i <= n*2; i ++) preSum[i] += preSum[i - 1];  // 剩余油量
        int hh = 0, tt = -1;
        for (int i = n*2; i >= 1; i--) {
            if (hh<=tt && i <= q[hh]-n) hh++;  // 等价于在[i,i+n-1]中, 对任意的j, i<=j<=i+n-1
            while (hh<=tt && preSum[i] <= preSum[q[tt]]) tt--;  // 均有s[j]-s[i-1]>=0
            q[++tt] = i;
            if (i<=n && preSum[q[hh]] - preSum[i-1] >= 0) st[i] = true;
        }

        // 逆时针
        dist[0] = dist[n];
        for (int i = n; i >= 1; i--) preSum[i] = preSum[i + n] = oil[i] - dist[i - 1];
        for (int i = n*2; i >= 1; i--) preSum[i] += preSum[i+1];  // 这里求前缀用的是 i+1
        hh = 0; tt = -1;
        for (int i = 1; i <= n*2; i++) {
            if (hh<=tt && i >= q[hh]+n) hh++;
            while (hh<=tt && preSum[i] <= preSum[q[tt]]) tt--;
            q[++tt] = i;
            if (i>=n+1 && preSum[q[hh]] - preSum[i+1] >= 0) st[i-n] = true;
        }

        for (int i = 1; i <= n; i++) {
            if (st[i]) bw.write("TAK\n");
            else bw.write("NIE\n");
        }
        bw.flush();        
    }
}
```

