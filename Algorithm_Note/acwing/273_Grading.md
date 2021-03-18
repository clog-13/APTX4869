# 273. 分级

给定长度为 N 的序列 A，构造一个长度为 N 的序列 B，满足：

1. B 非严格单调。
2. 最小化 S=∑i=1N|Ai−Bi|。

只需要求出这个最小值 SS。

#### 输入格式

第一行包含一个整数 N。

接下来 N 行，每行包含一个整数 Ai。

#### 输出格式

输出一个整数，表示最小 SS 值。

#### 数据范围

1≤N≤2000, 0≤Ai≤109

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
    List<Integer> a = new ArrayList<>(), c = new ArrayList<>();

    public static void main(String[] args) {
        new Main().init();
    }

    void init() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();

        for (int i = 0 ; i < N; i++){
            int x = sc.nextInt();
            a.add(x); c.add(x);
        }
        c.sort((x,y)->(x-y));
        
        int resAsc = helper();
        Collections.reverse(a);  // 翻转a，相当于再按递增从后扫描一遍数组，即得到正向的递减序列
        int resDesc = helper();

        System.out.println(Math.min(resAsc, resDesc));
    }

    int helper() {
        int[][] dp = new int[maxN][maxN];

        for (int i = 1 ; i <= N ; i++){
            int minV = Integer.MAX_VALUE;
            for (int j = 1 ; j <= N ; j++){
                minV = Math.min(minV, dp[i-1][j]);  // 这里minV实现了 非严格 递增
                dp[i][j] = minV + Math.abs(c.get(j-1) - a.get(i-1));
            }
        }

        int res = Integer.MAX_VALUE;
        for (int i = 1 ; i <= N; i++) res = Math.min(res, dp[N][i]);
        return res;
    }
}
```

