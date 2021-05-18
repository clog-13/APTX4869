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

假设某个最优解如下图所示，其中 {Ai} 是原序列，{Ai′} 是将原序列排序后的序列，图中红色圆圈表示每个 Bi。

![](https://cdn.acwing.com/media/article/image/2019/10/01/1_861faab8e3-acwing_273.png)

考虑每个位于 Ai′,Ai+1′之间的一段 Bi，比如上图中粉色框中的部分。
则我们在 {Ai} 中粉色框对应的这段里统计出大于等于 A′i+1 的数的个数 x，小于等于 Ai′ 的数的个数 y，那么：

- 如果 x>y，将粉色框中的 Bi 整体上移，使最高的一个圆圈达到上边界，结果会变好；

- 如果 x<y，将粉色框中的 Bi 整体下移，使最低的一个圆圈达到下边界，结果会变好；

- 如果 x=y，则上面两种方式均可，结果不会变差；

综上所述，只要存在某个 Bi 的值不在原序列中，我们一定可以将它调整成原序列中的值，且结果不会变差。

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
                minS = Math.min(minS, dp[i-1][j]);  // 这里minS实现了 非严格 递增
                dp[i][j] = minS + Math.abs(A.get(i-1) - C.get(j));
            }
        }

        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= N; i++) res = Math.min(res, dp[N][i]);
        return res;
    }
}
```



## Greedy

```java
import java.util.*;

class Main {
    int N, arr[];

    public static void main(String[] args) {
        new Main().init();
    }

    void init() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        arr = new int[N];
        for (int i = 0; i < N; i++) arr[i] = sc.nextInt();

        long res = helper();
        arr = reverse();
        res = Math.min(res, helper());

        System.out.println(res);
    }

    long helper() {
        long res = 0;
        PriorityQueue<Integer> heap = new PriorityQueue<>((a,b) -> (b-a));
        for (int i = 0; i < N; i++) {
            heap.add(arr[i]);
            if (arr[i] < heap.peek()) {
                res += heap.poll() - arr[i];
                heap.add(arr[i]);
            }
        }
        return res;
    }

    int[] reverse() {
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[arr.length-i-1] = arr[i];
        }
        return res;
    }
}
```

