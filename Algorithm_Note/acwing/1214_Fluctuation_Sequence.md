# 1214. 波动数列

观察这个数列：

1 3 0 2 -1 1 -2 …

这个数列中后一项总是比前一项增加2或者减少3，**且每一项都为整数**。

栋栋对这种数列很好奇，他想知道长度为 n 和为 s 而且后一项总是比前一项增加 a 或者减少 b 的整数数列可能有多少种呢？

#### 输入格式

共一行，包含四个整数 n,s,a,b，含义如前面所述。

#### 输出格式

共一行，包含一个整数，表示满足条件的方案数。

由于这个数很大，请输出方案数除以 100000007 的余数。

#### 数据范围

1≤n≤1000, −109≤s≤109, 1≤a,b≤106

#### 输入样例：

```
4 10 2 3
```

#### 输出样例：

```
2
```

#### 样例解释

两个满足条件的数列分别是2 4 1 3和7 4 1 -2。

```java
import java.util.Scanner;

public class Main {
    static int MOD = 100000007;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), S = sc.nextInt();
        int A = sc.nextInt(), B = sc.nextInt();
        // S%=N; A%=N; B%=N;  // 这里可以取余，也可以不取

        int[][] dp = new int[N][1010];
        dp[0][0] = 1;   // dp[i][j]: 前 i 项总和 除以n的余数是 j 的方案数
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < N; j++) {  // 这里 j 的遍历顺序可以随意
                // 总和: S = nx + (n-1)d1 + (n-2)d2 + (n-3)d3 + ... + dn
                // 可得: x = (S - (n-1)d1 + (n-2)d2 + (n-3)d3 + ... + dn) / n
                // 最后一项 加A 或者 减B
                dp[i][j] = (dp[i-1][getMOD(j - (N-i)*A, N)] + dp[i-1][getMOD(j + (N-i)*B, N)]) % MOD;
            }
        }
        System.out.println(dp[N-1][getMOD(S, N)]);
    }

    // 求余 是为了能够压缩，同时不漏，不重
    private static int getMOD(int a, int b) {
        return (a % b + b) % b;
    }
}
```

