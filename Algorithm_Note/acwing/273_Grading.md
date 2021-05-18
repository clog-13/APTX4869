# 273. 分级

给定长度为 N 的序列 A，构造一个长度为 N 的序列 B，满足：

1. B 非严格单调。
2. 最小化 S= i=1∑N |Ai−Bi|。

只需要求出这个最小值 S。

#### 输入格式

第一行包含一个整数 N。

接下来 N 行，每行包含一个整数 Ai。

#### 输出格式

输出一个整数，表示最小 S 值。

#### 数据范围

1≤N≤2000, 0≤Ai≤10^9^

#### 输入样例：

```
7
1
3
2
4
5
3
9
```

#### 输出样例：

```
3
```



## DP

```java
import java.util.*;
class Main {
    int N, maxN = 2010;
    List<Integer> A = new ArrayList<>(), C = new ArrayList<>();

    public static void main(String[] args) {
        new Main().init();
    }

    void init() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();

        for (int i = 0; i < N; i++) {
            int x = sc.nextInt();
            A.add(x); C.add(x);
        }

        Collections.sort(C);

        int resAsc = helper();
        Collections.reverse(A);  // 翻转a，相当于再按递增从后扫描一遍数组，即得到正向的递减序列
        int resDes = helper();

        System.out.println(Math.min(resAsc, resDes));
    }

    int helper() {
        int[][] dp = new int[maxN][maxN];  // dp[i][j]: B的 第i位为 C[j] 的最小S值

        for (int i = 1; i <= N; i++) {
            int minS = Integer.MAX_VALUE;
            for (int j = 1; j <= N; j++) {
                minS = Math.min(minS, dp[i-1][j]);  // 这里minV实现了 非严格 递增
                dp[i][j] = minS + Math.abs(A.get(i-1) - C.get(j));
            }
        }

        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= N; i++) res = Math.min(res, dp[N][i]);
        return res;
    }
}
```

