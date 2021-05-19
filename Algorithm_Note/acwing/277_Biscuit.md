# 277. 饼干

圣诞老人共有 M 个饼干，准备全部分给 N 个孩子。

每个孩子有一个贪婪度，第 i 个孩子的贪婪度为 g[i]。

如果有 a[i] 个孩子拿到的饼干数比第 i 个孩子多，那么第 i 个孩子会产生 g[i]×a[i] 的怨气。

给定 N、M 和序列 g，圣诞老人请你帮他安排一种分配方式，使得每个孩子至少分到一块饼干，并且所有孩子的怨气总和最小。

#### 输入格式

第一行包含两个整数 N,M。

第二行包含 N 个整数表示 g1∼gN。

#### 输出格式

第一行一个整数表示最小怨气总和。

第二行 N 个空格隔开的整数表示每个孩子分到的饼干数，若有多种方案，输出任意一种均可。

#### 数据范围

1≤N≤30, N≤M≤5000, 1≤gi≤107

#### 输入样例：

```
3 20
1 2 3
```

#### 输出样例：

```
2
2 9 9
```



## DP

只在意孩子之间的相对高度，而不在意绝对高度，举个例子：2 4 4 6 产生的结果和 1 2 2 3 是一样的。

假设我们已经处理到了第 i 个孩子，我们给她分配糖糖的时候就很轻松。

要么让她得到大于1个糖果，那么这个状态什么也不用考虑，可以直接继承上一个状态，

要么让她得到1个糖果，那么这个状态就要考虑前面有多少个孩纸跟她得到同样多的糖果（也就是1个糖果）。
假设前面有K个孩子跟她糖果数相同（K包括她自己），那么就有（i-k）个孩子比她们多得糖果，
那么怨气值就是 前（i-k）个孩子（管他怎么分配的）最小怨气值+（i-k）× 这K个孩子的贪婪度.

```java
import java.util.*;

class Main {
    int N, M, maxN = 35, maxM = 5050;
    Node[] arr;
    int[][] dp = new int[maxN][maxM];
    int[] preSum = new int[maxN], res = new int[maxN];

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); M = sc.nextInt();

        arr = new Node[N];
        for (int i = 0; i < N; i++) arr[i] = new Node(sc.nextInt(), i);
        Arrays.sort(arr, (o1, o2) -> (o2.g - o1.g));  // 怒气降序
        for (int i = 1; i <= N; i++) preSum[i] = preSum[i-1] + arr[i-1].g;  // 怒气前缀和
        for (int i = 0; i <= N; i++) Arrays.fill(dp[i], 0x3f3f3f3f);

        dp[0][0] = 0;  // dp[i][j]:前i个人分j个饼干的最小怒气值
        for (int i = 1; i <= N; i++) {  // 前i个人
            for (int j = 1; j <= M; j++) {  // 饼干数量
                if (j >= i) dp[i][j] = dp[i][j-i];  // 所有人都增加或減少相同數量 怒气值不会变化
                for (int k = 1; k <= i && k <= j; k++) {  // K:只有一个糖果 的孩子 的数量
                    dp[i][j] = Math.min(dp[i][j], dp[i-k][j-k] + (preSum[i]-preSum[i-k])*(i-k));
                }
            }
        }
        System.out.println(dp[N][M]);

        int i = N, j = M, h = 0;
        while (i>0 && j>0) {
            if (j>=i && dp[i][j] == dp[i][j-i]) {  // j-i
                j -= i;
                h++;  // base高度
            } else {
                for (int k = 1; k<=i && k<=j; k++) {
                    if (dp[i][j] == dp[i-k][j-k] + (preSum[i]-preSum[i-k])*(i-k)) {
                        for (int u = i; u > i-k; u--) res[arr[u-1].id] = 1 + h;
                        i -= k; j-= k;
                        break;
                    }
                }
            }
        }
        for (i = 0; i < N; i++) System.out.print(res[i]+" ");
    }

    static class Node {
        int g, id;
        public Node(int gg, int i) {
            g = gg; id = i;
        }
    }
}
```

