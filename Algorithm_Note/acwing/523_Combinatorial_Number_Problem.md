# 523. 组合数问题

组合数 mCn 表示的是从 n 个物品中选出 m 个物品的方案数。 

举个例子，从 (1, 2, 3) 三个物品中选择两个物品可以有 (1, 2), (1, 3), (2, 3) 这三种选择方法。 

小葱想知道如果给定 n, m 和 k ，对于所有的 0 ≤ i ≤ n, 0 ≤ j ≤ min(i, m) 有多少对 (i, j) 满足 jCi 是 k 的倍数。

#### 输入格式

第一行有两个整数 t, k ，其中 t 代表该测试点总共有多少组测试数据，k 的意义见问题描述。

接下来 t 行每行两个整数 n, m。

#### 输出格式

共 t 行，每行一个整数代表所有的 0 ≤ i ≤ n, 0 ≤ j ≤ min(i, m) 有多少对 (i, j) 满足 jCi 是 k 的倍数。

#### 数据范围

1≤n,m≤2000,  2≤k≤21,  1≤t≤10^4^

#### 输入样例：

```
1 2
3 3
```

#### 输出样例：

```
1
```



## 前缀和 + DP

```java
import java.util.*;

public class Main {
    int T, K, maxN = 2010;
    int[][] c = new int[maxN][maxN];
    int[][] preSum = new int[maxN][maxN];

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        T = sc.nextInt(); K = sc.nextInt();

        for (int i = 0; i < maxN; i++) {
            for (int j = 0; j <= i; j++) {
                if (j == 0) c[i][j] = 1;  // c[i][j]: 前i个选j个的组合数
                else c[i][j] = (c[i-1][j-1] + c[i-1][j]) % K;  // 排列组合公式

                if (c[i][j] == 0) preSum[i][j] = 1;
            }
        }

        for (int i = 0; i < maxN; i++) {  // 生成二维前缀和
            for (int j = 0; j < maxN; j++) {
                if (i > 0) preSum[i][j] += preSum[i-1][j];
                if (j > 0) preSum[i][j] += preSum[i][j-1];
                if (i > 0 && j > 0) preSum[i][j] -= preSum[i-1][j-1];
            }
        }

        while (T-- > 0) {
            System.out.println(preSum[sc.nextInt()][sc.nextInt()]);
        }
    }
}
```



