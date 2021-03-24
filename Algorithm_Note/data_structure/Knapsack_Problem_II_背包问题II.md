# Knapsack_Problem_II_背包问题II



## 11. 背包问题求方案数

有 N 件物品和一个容量是 V 的背包。每件物品只能使用一次。

第 i 件物品的体积是 vi，价值是 wi。

求解将哪些物品装入背包，可使这些物品的总体积不超过背包容量，且总价值最大。

输出 **最优选法的方案数**。注意答案可能很大，请输出答案模 10^9^+7 的结果。

**输入格式**

第一行两个整数，N，V，用空格隔开，分别表示物品数量和背包容积。

接下来有 N 行，每行两个整数 vi,wi，用空格隔开，分别表示第 i 件物品的体积和价值。

**输出格式**

输出一个整数，表示 **方案数** 模 109+7109+7 的结果。

**数据范围**

0<N,V≤1000, 0<vi,wi≤1000

**输入样例**

```
4 5
1 2
2 4
3 4
4 6
```

**输出样例：**

```
2
```

```java
// cnt[i]为背包容积为 i 时总价值为最佳的方案数

// 先初始化所有的 cnt[i] 为 1，因为背包最大值为 0 也是一种方案

// 如果装新物品的方案总价值更大，那么用 dp[j−v]+w 来更新 dp[j]，用 cnt[j−v] 更新 cnt[j]
// 如果总价值相等，那么最大价值的方案数就多了 cnt[j−v] 种
import java.util.*;
class Main {
    static int MOD = 1000000007;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), S = sc.nextInt();
        
        int[] dp = new int[S+1];  // dp[i]: 容积为 i 时的最大值
        int[] cnt = new int[S+1]; //cnt[i]: 容积为 i 时最大值 的数量
        Arrays.fill(cnt, 1);
        
        for (int i = 0; i < N; i++) {
            int size = sc.nextInt(), val = sc.nextInt();
            for (int s = S; s >= size; s--) {
                int tmp = dp[s-size]+val;
                if (tmp > dp[s]) {
                    dp[s] = tmp;
                    cnt[s] = cnt[s-size];
                } else if (tmp == dp[v]) {
                    cnt[s] = (cnt[s]+cnt[s-size])%MOD;
                }
            }
        }
        System.out.println(cnt[S]);
    }
}
```



## 12. 背包问题求具体方案

有 N 件物品和一个容量是 V 的背包。每件物品只能使用一次。

第 i 件物品的体积是 vi，价值是 wi。

求解将哪些物品装入背包，可使这些物品的总体积不超过背包容量，且总价值最大。

输出 **字典序最小的方案**。这里的字典序是指：所选物品的编号所构成的序列。物品的编号范围是 1…N。

**输入格式**

第一行两个整数，N，V，用空格隔开，分别表示物品数量和背包容积。

接下来有 N 行，每行两个整数 vi,wi，用空格隔开，分别表示第 i 件物品的体积和价值。

**输出格式**

输出一行，包含若干个用空格隔开的整数，表示最优解中所选物品的编号序列，且该编号序列的字典序最小。

物品编号范围是 1…N。

**数据范围**

0<N,V≤1000, 0<vi,wi≤1000

**输入样例**

```
4 5
1 2
2 4
3 4
4 6
```

**输出样例：**

```
1 4
```
```java
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), S = sc.nextInt();
        int[][] dp = new int[N+2][S+1];
        int[] wgts = new int[N+1], vals = new int[N+1];

        for (int i = 1; i <= N; i ++ ) {
            wgts[i] = sc.nextInt();
            vals[i] = sc.nextInt();
        }
        
        // 倒序01背包
        for (int i = N; i > 0; i -- ) {
            for (int s = 0; s <= S; s ++ ) {
                dp[i][s] = dp[i+1][s];
                if (s >= wgts[i]) {
                    dp[i][s] = Math.max(dp[i][s], dp[i+1][s-wgts[i]] + vals[i]);
                }
            }
        }

        int cur_s = S;
        for (int i = 1; i <= N; i ++ ) {
            if (i == N && cur_s >= wgts[i]) {  // 如果是最后一个元素，特判一下，防止越界即可
                System.out.printf("%d ", i);
                return;
            }
            if (cur_s <= 0) return;  // 判断下标是否越界
            if (cur_s-wgts[i] >= 0 && dp[i][cur_s] == dp[i+1][cur_s - wgts[i]]+vals[i]) {
                System.out.printf("%d ", i);
                cur_s -=  wgts[i];    // 选了第i个物品，剩余容量就要减小。
            }
        }
    }
}
```