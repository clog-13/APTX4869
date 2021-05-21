# 1072. 树的最长路径

给定一棵树，树中包含 n 个结点（编号1~n）和 n−1 条无向边，每条边都有一个权值。

现在请你找到树中的一条最长路径。

换句话说，要找到一条路径，使得使得路径两端的点的距离最远。

注意：路径中可以只包含一个点。

#### 输入格式

第一行包含整数 n。

接下来 n−1 行，每行包含三个整数 ai,bi,ci，表示点 ai 和 bi 之间存在一条权值为 ci 的边。

#### 输出格式

输出一个整数，表示树的最长路径的长度。

#### 数据范围

1≤n≤10000, 1≤ai,bi≤n, −105≤ci≤105

#### 输入样例：

```
6
5 1 6
1 4 5
6 3 9
2 6 8
6 1 7
```

#### 输出样例：

```
22
```



## DFS

```java
import java.io.*;
import java.util.*;

class Main {
    static int N, maxN = 100010, maxM = 2*maxN, idx;
    static int[] info = new int[maxN], dist = new int[maxN];
    static int[] from = new int[maxM], to = new int[maxM], val = new int[maxM];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        Arrays.fill(info, -1);
        for (int i = 1; i < N; i++) {
            String[] str = br.readLine().split(" ");
            int a = Integer.parseInt(str[0]), b = Integer.parseInt(str[1]);
            int c = Integer.parseInt(str[2]);
            add(a, b, c); add(b, a, c);
        }

        int x = 1, res = 0;
        dfs(1, -1, 0);  // 先随便从一个节点出发找到最长路径节点
        for (int i = 1; i <= N; i++) {
            if (res < dist[i]) {    // 找出端点x
                res = dist[i];
                x = i;
            }
        }

        Arrays.fill(dist, 0);
        dfs(x, -1, 0);  // 再从之前的最长路径节点出发找另一头
        for (int i = 1; i <= N; i++) res = Math.max(res, dist[i]);
        
        System.out.println(res);
    }

    private static void dfs(int u, int f, int cnt) {
        dist[u] = Math.max(dist[u], cnt);
        for (int i = info[u]; i != -1; i = from[i]) {
            int t = to[i];
            if (t == f) continue;
            dfs(t, u, cnt+val[i]);
        }
    }

    private static void add(int a, int b, int c) {
        from[idx] = info[a];
        to[idx] = b;
        val[idx] = c;
        info[a] = idx++;
    }
}
```

## 回溯

```java
import java.util.*;
class Main {
    int N, idx, res, maxN = 10010, maxM = 2*maxN, INF = 0x3f3f3f3f;
    int[] info = new int[maxN];
    int[] from = new int[maxM], to = new int[maxM], val = new int[maxM];

    public static void main(String[] args) {
        new Main().init();
    }

    void init() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();

        Arrays.fill(info, -1);
        for (int i = 1; i < N; i++) {
            int a = sc.nextInt(), b= sc.nextInt(), c = sc.nextInt();
            add(a, b, c); add(b, a, c);
        }

        dfs(1, -1);

        System.out.println(res);
    }

    int dfs(int u, int f) {
        int d1 = 0, d2 = 0;
        for (int i = info[u]; i != -1; i = from[i]) {
            int t = to[i];
            if (t == f) continue;
            int d = dfs(t, u) + val[i];

            if (d >= d1) {  // >=
                d2 = d1; d1 = d;
            } else if (d > d2) {  // >
                d2 = d;
            }
        }

        res = Math.max(res, d1+d2);  // 记录当前点包含的最长路径（左右两边）
        return d1;  // 返回当前点的最长单边路径
    }

    void add(int a, int b, int c) {
        from[idx] = info[a];
        to[idx] = b;
        val[idx] = c;
        info[a] = idx++;
    }
}
```



## 回溯 + DP

```java
import java.util.*;

class Main{
    int N, res, idx, maxN = 20010;
    int[] info = new int[maxN], dist = new int[maxN], fa = new int[maxN];
    int[] from = new int[maxN], to = new int[maxN], val = new int[maxN];

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();

        Arrays.fill(info, -1);
        for (int i = 1; i < N; i++) {
            int a = sc.nextInt(), b = sc.nextInt(), c = sc.nextInt();
            add(a, b, c); add(b, a, c);
        }

        dfs(1, -1);
        System.out.println(res);
    }

    void dfs(int u, int f) {
        int preMax = 0;
        for (int i = info[u]; i != -1; i = from[i]) {
            int t = to[i];
            if (t == f) continue;
            dfs(t, u);

            if (res < preMax+dist[t]+val[i]) {
                res = preMax+dist[t]+val[i];
            }
            if (preMax < dist[t]+val[i]) {  // 当前t之前的最长边
                preMax = dist[t]+val[i];
            }
        }
        dist[u] = preMax;
    }

    void add(int a, int b, int c) {
        from[idx] = info[a];
        to[idx] = b;
        val[idx] = c;
        info[a] = idx++;
    }
}
```