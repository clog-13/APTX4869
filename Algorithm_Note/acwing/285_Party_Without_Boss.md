# 285. 没有上司的舞会

Ural 大学有 N 名职员，编号为 1∼N。

他们的关系就像一棵以校长为根的树，父节点就是子节点的直接上司。

每个职员有一个快乐指数，用整数 Hi 给出，其中 1≤i≤N。

现在要召开一场周年庆宴会，不过，没有职员愿意和直接上司一起参会。

在满足这个条件的前提下，主办方希望邀请一部分职员参会，使得所有参会职员的快乐指数总和最大，求这个最大值。

#### 输入格式

第一行一个整数 N。

接下来 N 行，第 i 行表示 i 号职员的快乐指数 Hi。

接下来 N−1 行，每行输入一对整数 L,K，表示 K 是 L 的直接上司。

#### 输出格式

输出最大的快乐指数。

#### 数据范围

1≤N≤6000, −128≤Hi≤127

#### 输入样例：

```
7
1
1
1
1
1
1
1
1 3
2 3
6 4
7 4
4 5
3 5
```

#### 输出样例：

```
5
```



## DFS

```java
import java.util.*;

class Main {
    static int N, idx = 0, maxN = 6010;
    static int[] arr = new int[maxN], leader = new int[maxN], ind = new int[maxN];
    static int[] from = new int[maxN], to = new int[maxN], info = new int[maxN];
    static int[][] dp = new int[maxN][2];
    static boolean[] has_fa = new boolean[maxN];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        for (int i = 1; i <= N; i ++) arr[i] = sc.nextInt();

        Arrays.fill(info, -1);
        for (int i = 0; i < N-1; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            add(b, a);
            has_fa[a] = true;
        }

        int root = 1;
        while(has_fa[root]) root++;
        dfs(root);

        System.out.println(Math.max(dp[root][0], dp[root][1]));
    }

    private static void dfs(int u) {
        dp[u][1] = arr[u];
        for(int i = info[u]; i != -1; i = from[i]) {
            int t = to[i];
            
            dfs(t);
            
            dp[u][1] += dp[t][0];
            dp[u][0] += Math.max(dp[t][1], dp[t][0]);
        }
    }

    private static void add(int a,int b) {
        to[idx] = b;
        from[idx] = info[a];
        info[a] = idx++;
    }
}
```



## 自底向上

```java
import java.util.*;

class Main {
    static int N, maxN = 6010;
    static int[] arr = new int[maxN], leader = new int[maxN], ind = new int[maxN];
    static int[][] dp = new int[maxN][2];
    static boolean[] vis = new boolean[maxN];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        for (int i = 1; i <= N; i ++) arr[i] = sc.nextInt();

        for (int i = 1; i < N; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            leader[a] = b;
            ind[b]++;  
        }

        for (int i = 1; i <= N; i++) {
            if (!vis[i] && ind[i] == 0) dfs(i);  // 叶子节点的ind为 0
        }
        // dp[0][]：不选当前 dp[1][]：选当前
        System.out.println(Math.max(dp[0][0], dp[0][1] + arr[0]));
    }

    private static void dfs(int u) {
        if (u == 0) return;
        vis[u] = true;

        dp[leader[u]][0] += Math.max(dp[u][1]+arr[u], dp[u][0]);
        dp[leader[u]][1] += dp[u][0];
        ind[leader[u]]--;
        if (ind[leader[u]] == 0) dfs(leader[u]);
    }
}
```

