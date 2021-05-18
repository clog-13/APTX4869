# 272. 最长公共上升子序列

#### 输入格式

第一行包含一个整数N，表示数列A，B的长度。

第二行包含N个整数，表示数列A。

第三行包含N个整数，表示数列B。

#### 输出格式

输出一个整数，表示最长公共上升子序列的长度。

#### 数据范围

1≤N≤3000,序列中的数字均不超过2^(31)−1

#### 输入样例：

```
4
2 2 1 3
2 1 2 3
```

#### 输出样例：

```
2
```



## DP

```java
import java.util.*;

class Main {
    static int maxN = 3010;
    static int[] arr = new int[maxN], brr = new int[maxN];
    static int[][] dp = new int[maxN][maxN];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        for (int i = 1; i <= N; i++) arr[i] = sc.nextInt();
        for (int i = 1; i <= N; i++) brr[i] = sc.nextInt();

        for (int i = 1; i <= N; i++) {
            int max = 1;  // a里以b[i](==a[i])结尾的最长(公共上升子序列)长度 + 1
            for (int j = 1; j <= N; j++) {
                dp[i][j] = dp[i-1][j];  // dp[i][j]:arr[i]brr[j]的LCAS
                if (brr[j] < arr[i]) max = Math.max(max, dp[i-1][j] + 1);
                if (brr[j]== arr[i]) dp[i][j] = max;  // lCAS:相同时才能更新
            }
        }
        // LAS 的 二位数组DP写法
        // for (int i = 1; i <= N; i++) {
        //     int max = 1;
        //     for (int j = 1; j <= N; j++) {
        //         dp[i][j] = dp[i-1][j];
        //         if (arr[j] < arr[i]) max = Math.max(max, dp[i-1][j] + 1);
        //         dp[i][i] = max;  // 这里j>i时就可以退出了
        //     }
        // }

        // LCS 的 二维数组DP写法
        // for (int i = 1; i <= N; i++) {
        //     for (int j = 1; j <= M; j++) {
        //         if (c1[i] == c2[j]) dp[i][j] = dp[i-1][j-1] + 1;
        //         else dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
        //     }
        // }

        int res = 0;
        for (int i = 1; i <= N; i++) res = Math.max(res, dp[N][i]);
        System.out.println(res);
    }
}
```

