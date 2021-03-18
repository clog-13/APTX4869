# 274. 移动服务

一个公司有三个移动服务员，最初分别在位置 1，2，3 处。

如果某个位置（用一个整数表示）有一个请求，那么公司必须指派某名员工赶到那个地方去。

某一时刻只有一个员工能移动，且不允许在同样的位置出现两个员工。

从 p 到 q 移动一个员工，需要花费 c(p,q)。

这个函数不一定对称，但保证 c(p,p)=0。

给出 N 个请求，请求发生的位置分别为 p1∼pN。

公司必须按顺序依次满足所有请求，**且过程中不能去其他额外的位置**，目标是最小化公司花费，请你帮忙计算这个最小花费。

#### 输入格式

第 1 行有两个整数 L,N，其中 L 是位置数量，N 是请求数量，每个位置从 1 到 L 编号。

第 2 至 L+1 行每行包含 L 个非负整数，第i+1 行的第 j 个数表示 c(i,j)，并且它小于 2000。

最后一行包含 N 个整数，是请求列表。

一开始三个服务员分别在位置 1，2，3。

#### 输出格式

输出一个整数 M，表示最小花费。

#### 数据范围

3≤L≤200, 1≤N≤1000

#### 输入样例：

```
5 9
0 1 1 1 1
1 0 2 3 2
1 1 0 4 1
2 1 5 0 1
4 2 3 4 0
4 2 4 1 5 4 3 2 1
```

#### 输出样例：

```
5
```



## DP

```java
import java.util.*;
class Main {
    int L, N, maxL = 210, maxN = 1010, INF = 0x3f3f3f3f;
    int[][] w = new int[maxL][maxL];
    int[] q = new int[maxN];
    int[][][] dp = new int[maxN][maxL][maxL];


    public static void main(String[] args) {
        new Main().init();
    }

    void init() {
        Scanner sc = new Scanner(System.in);
        L = sc.nextInt(); N = sc.nextInt();

        for (int i = 1; i <= L; i++) {
            for (int j = 1; j <= L; j++) {
                w[i][j] = sc.nextInt();
            }
        }
        for (int i = 1; i <= N; i++) q[i] = sc.nextInt();
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= L; j++) {
                Arrays.fill(dp[i][j], INF);
            }
        }

        q[0] = 1;
        dp[0][2][3] = 0;  // dp[i][x][y]:服务员分别在q[i], x, y 的所有方案的最小值
        for (int i = 0; i < N; i++) {
            int u = q[i], to = q[i+1];  // 注意这里的u, 是代码的一个难点
            for (int x = 1; x <= L; x++) {
                for (int y = 1; y <= L; y++) {
                    if (u==x || u==y || x==y) continue;
                    int cur = dp[i][x][y];
                    dp[i+1][x][y] = Math.min(dp[i+1][x][y], cur + w[u][to]);
                    dp[i+1][u][y] = Math.min(dp[i+1][u][y], cur + w[x][to]);
                    dp[i+1][x][u] = Math.min(dp[i+1][x][u], cur + w[y][to]);
                }
            }
        }

        int res = INF;
        for (int x = 1; x <= L; x++) {
            for (int y = 1; y <= L; y++) {
                if (q[N]==x || q[N]==y || x==y) continue;
                res = Math.min(res, dp[N][x][y]);
            }
        }
        System.out.println(res);
    }
}
```

