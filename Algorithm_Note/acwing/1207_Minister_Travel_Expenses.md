# 1207. 大臣的旅费

王国修建了大量的快速路，用于连接首都和王国内的各大城市。任何一个大城市都能从首都直接或者通过其他大城市间接到达。

同时，如果不重复经过大城市，从首都到达每个大城市的方案都是唯一的。

J是T国重要大臣，他巡查于各大城市之间，体察民情。

所以，从一个城市马不停蹄地到另一个城市成了J最常做的事情。

他有一个钱袋，用于存放往来城市间的路费。

聪明的J发现，如果不在某个城市停下来修整，在连续行进过程中，他所花的路费与他已走过的距离有关，在走第x千米到第x+1千米这一千米中（x是整数），他花费的路费是x+10这么多。也就是说走1千米花费11，走2千米要花费23。

J大臣想知道：他从某一个城市出发，中间不休息，到达另一个城市，所有可能花费的路费中最多是多少呢？

#### 输入格式

输入的第一行包含一个整数 n，表示包括首都在内的T王国的城市数。

城市从 1 开始依次编号，1 号城市为首都。

接下来 n−1 行，描述T国的高速路（T国的高速路一定是 n−1 条）。

每行三个整数 Pi,Qi,Di，表示城市 Pi 和城市 Qi 之间有一条**双向**高速路，长度为 Di 千米。

#### 输出格式

输出一个整数，表示大臣J最多花费的路费是多少。

#### 数据范围

1≤n≤10^5^, 1≤Pi,Qi≤n, 1≤Di≤1000

#### 输入样例：

```
5 
1  2  2 
1  3  1 
2  4  5 
2  5  4 
```

#### 输出样例：

```
135
```



## 树形DP

```java
import java.io.*;
import java.util.*;

public class Main {
    int idx, res, maxN = 100010, maxM = maxN * 2;
    int[] info = new int[maxN];
    int[] from = new int[maxM], to = new int[maxM],  val = new int[maxM];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());

        Arrays.fill(info, -1);
        for (int i = 0; i < N-1; i++) {
            String[] str = br.readLine().split(" ");
            int a = Integer.parseInt(str[0]), b = Integer.parseInt(str[1]);
            int c = Integer.parseInt(str[2]);
            add(a, b, c); add(b, a, c);
        }

        dfs(1, -1);  // 找到 1点 往下走的最大长度
        System.out.println(res * 11L + ((long)(res - 1) * res) / 2);
    }

    // 找到u点往下走的最大长度
    int dfs(int u, int f) {
        int d1 = 0, d2 = 0;  // 最大值, 次大值
        for (int i = info[u];i != -1;i = from[i]) {
            int t = to[i];
            if (t == f) continue;
            int d = dfs(t, u) + val[i];  // dfs

            if (d > d1) {
                d2 = d1; d1 = d;
            } else if (d > d2) {
                d2 = d;
            }
        }

        res = Math.max(res, d1 + d2);  // 更新答案
        return d1;  // 找到u点往下走的最大长度
    }

    void add(int a, int b, int c) {
        from[idx] = info[a];
        to[idx] = b;
        val[idx] = c;
        info[a] = idx++;
    }
}
```



## DFS, BFS

```java
import java.util.*;

public class Main{
    int N = 100010,M = 200010, idx;
    int[] info = new int[N], dist = new int[N];
    int[] from = new int[M], to = new int[M], val = new int[M];

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Arrays.fill(info, -1);
        for (int i = 0; i < n-1; i++) {
            int a = sc.nextInt(), b = sc.nextInt(), c = sc.nextInt();
            add(a, b, c); add(b, a, c);
        }

        int u = 1, res = -1;
        dfs(1, -1, 0);  // 选随意一个点, 找到距离该点 最远的点 u
        for (int i = 2; i <= n; i++) {
            if (dist[i] > dist[u]) u = i;
        }
        dfs(u, -1, 0);  // 找到离 u 最远的点
        for (int i = 1; i <= n; i++) {
            if (dist[i] > res) res = dist[i];
        }

        System.out.println(res * 11L + ((long)(res - 1) * res) / 2);
    }

    void dfs(int u, int f, int dis) {
        dist[u] = dis;  // 联通图是树
        for (int i = info[u]; i != -1; i = from[i]) {
            int t = to[i];
            if (t != f) dfs(t, u, dis + val[i]);
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

```java
import java.util.*;

public class Main {
    int maxN = 100010, maxM = 2*maxN, idx;
    int[] info = new int[maxN], dist = new int[maxN];
    int[] from = new int[maxM],  to = new int[maxM], val = new int[maxM];
    boolean[] st = new boolean[maxN];

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        Arrays.fill(info, -1);
        for (int i = 0; i < N-1; i++) {
            int a = sc.nextInt(), b = sc.nextInt(), c = sc.nextInt();
            add(a, b, c); add(b, a, c);
        }

        bfs(1);  // 找到任意点x找到距离最远的点y
        int u = 1, res = -1;
        for (int i = 1; i <= N; i++) {
            if (dist[i] > dist[u]) u = i;
        }
        bfs(u);  // 找到离y最远的点的距离
        for (int i = 1; i <= N; i++) {
            if (dist[i] > res) res = dist[i];
        }
        System.out.println(res * 11L + ((long)(res - 1) * res) / 2);
    }

    void bfs(int u) {
        dist[u] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(u);
        Arrays.fill(st, false);
        st[u] = true;
        while(!queue.isEmpty()) {
            int cur = queue.poll();
            for(int i = info[cur]; i != -1; i = from[i]) {
                int t = to[i];
                if(st[t]) continue;
                st[t] = true;

                dist[t] = dist[cur] + val[i];
                queue.add(t);
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

