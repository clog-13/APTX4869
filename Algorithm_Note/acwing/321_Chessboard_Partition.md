# 321. 棋盘分割

将一个 8×8 的棋盘进行如下分割：将原棋盘割下一块矩形棋盘并使剩下部分也是矩形，再将剩下的部分继续如此分割，这样割了 (n−1) 次后，连同最后剩下的矩形棋盘共有 n 块矩形棋盘。(每次切割都只能沿着棋盘格子的**边**进行)

原棋盘上每一格有一个分值，一块矩形棋盘的总分为其所含各格分值之和。

现在需要把棋盘按上述规则分割成 n 块矩形棋盘，并使各矩形棋盘总分的均方差最小。

请编程对给出的棋盘及 n，求出均方差的最小值。

#### 输入格式

第 1 行为一个整数 n。

第 2 行至第 9 行每行为 8 个小于 100 的非负整数，表示棋盘上相应格子的分值。每行相邻两数之间用一个空格分隔。

#### 输出格式

输出最小均方差值（四舍五入精确到小数点后三位）。

#### 数据范围

1<n<15

#### 输入样例：

```
3
1 1 1 1 1 1 1 3
1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 1
1 1 1 1 1 1 1 0
1 1 1 1 1 1 0 3
```

#### 输出样例：

```
1.633
```



## DFS

```java
import java.io.*;

class Main {
    static int N, INF = 0x3f3f3f3f, maxN = 15, maxM = 9;
    static double X_;
    static double[][] preSum = new double[maxM][maxM];  // 矩阵前缀和
    static double[][][][][] dp = new double[maxM][maxM][maxM][maxM][maxN];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        for (int i = 1; i <= 8; i++) {
            String[] arr = br.readLine().split(" ");
            for(int j = 1; j <= 8; j++){
                preSum[i][j] = 
                    Integer.parseInt(arr[j-1]) + preSum[i-1][j] + preSum[i][j-1] - preSum[i-1][j-1];
            }
        }
        X_ = preSum[8][8] / N;

        dfs(1, 1, 8, 8, N);

        System.out.printf("%.3f", Math.sqrt(dp[1][1][8][8][N]));
    }

    public static double dfs(int x1, int y1, int x2, int y2, int k) {
        double val = dp[x1][y1][x2][y2][k];
        if (val > 0) return val;
        if (k == 1) return dp[x1][y1][x2][y2][k] = getVariance(x1, y1, x2, y2);

        // dp[x1][y1][x2][y2][k]: (x1, y1)到(x2, y2)的矩阵切 k 下的最小方差
        double res = INF;
        for (int i = x1; i < x2; i++) {  // 横切（上下）
            res = Math.min(res, dfs(x1 , y1, i , y2, k-1) + getVariance(i+1, y1, x2, y2));
            res = Math.min(res, dfs(i+1, y1, x2, y2, k-1) + getVariance(x1 , y1, i , y2));
        }

        for (int i = y1; i < y2; i++) {  // 纵切（左右）
            res = Math.min(res, dfs(x1, y1 , x2, i , k-1) + getVariance(x1, i+1, x2, y2));
            res = Math.min(res, dfs(x1, i+1, x2, y2, k-1) + getVariance(x1, y1 , x2, i ));
        }

        return dp[x1][y1][x2][y2][k] = res;
    }

    public static double getVariance(int x1 , int y1 , int  x2 , int y2){
        double sum = preSum[x2][y2] - preSum[x2][y1-1] - preSum[x1-1][y2] + preSum[x1-1][y1-1];
        sum -= X_;
        return sum * sum / N;
    }
}
```