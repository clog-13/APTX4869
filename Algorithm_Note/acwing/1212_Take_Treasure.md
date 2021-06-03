# 1212. 地宫取宝

X 国王有一个地宫宝库，是 n×m 个格子的矩阵，每个格子放一件宝贝，每个宝贝贴着价值标签。

地宫的入口在左上角，出口在右下角。

小明被带到地宫的入口，国王要求他只能向右或向下行走。

走过某个格子时，如果那个格子中的宝贝价值比小明手中任意宝贝价值都大，小明就可以拿起它（当然，也可以不拿）。

当小明走到出口时，如果他手中的宝贝恰好是 k 件，则这些宝贝就可以送给小明。

请你帮小明算一算，在给定的局面下，他有多少种不同的行动方案能获得这 k 件宝贝。

#### 输入格式

第一行 3 个整数，n,m,k，含义见题目描述。

接下来 n 行，每行有 m 个整数 Ci 用来描述宝库矩阵每个格子的宝贝价值。

#### 输出格式

输出一个整数，表示正好取 k 个宝贝的行动方案数。

该数字可能很大，输出它对 1000000007 取模的结果。

#### 数据范围

1≤n,m≤50, 1≤k≤12, 0≤Ci≤12

#### 输入样例1：

```
2 3 2
1 2 3
2 1 5
```

#### 输出样例1：

```
14
```



## DP

```java
import java.util.Scanner;

class Main {
    static int MOD = 1000000007;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), M = sc.nextInt(), K = sc.nextInt();
        int[][] arr = new int[N+1][M+1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                arr[i][j] = sc.nextInt() + 1;
            }
        }

        // dp[i][j][c][v]: i行 j列 拿了c个 最高价值v 的方案数
        int[][][][] dp = new int[N+1][M+1][K+1][14];
        dp[1][1][1][arr[1][1]] = 1;  // 第一格 拿， 只能拿 arr[1][1]价值 的一个东西
        dp[1][1][0][0] = 1;  // 第一格 不拿
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                if (i == 1 && j == 1) continue;
                for (int c = 0; c <= K; c++) {  // 循环 c 个数
                    for (int v = 0; v <= 13; v++) {  // 循环 v 最高价值
                        int tmp = (dp[i-1][j][c][v]+dp[i][j-1][c][v]) % MOD;  // 不拿

                        if (v == arr[i][j] && c > 0) {  // 拿,则最高价值为当前arr[i][j]
                            for (int s = 0; s < v; s++) {  // 从相邻两个方向c-1,s<arr[i][j]的情况转移
                                tmp = (tmp+dp[i-1][j][c-1][s]) % MOD;
                                tmp = (tmp+dp[i][j-1][c-1][s]) % MOD;
                            }
                        }

                        dp[i][j][c][v] = tmp % MOD;
                    }
                }
            }
        }

        int res = 0;
        for (int i = 0; i <= 13; i++) res = (res+dp[N][M][K][i]) % MOD;
        System.out.println(res);
    }
}
```

