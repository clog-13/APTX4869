# Knapsack_Problem_II_背包问题II



## 9. 分组背包问题

有 N 组物品和一个容量是 V 的背包。

每组物品有若干个，同一组内的物品最多只能选一个。
每件物品的体积是 vij，价值是 wij，其中 i 是组号，j 是组内编号。

求解将哪些物品装入背包，可使物品总体积不超过背包容量，且总价值最大。

输出最大价值。

**输入格式**

第一行有两个整数 N，V，用空格隔开，分别表示物品组数和背包容量。

接下来有 N 组数据：

- 每组数据第一行有一个整数 Si，表示第 i 个物品组的物品数量；
- 每组数据接下来有 Si 行，每行有两个整数 vij,wij，用空格隔开，分别表示第 i 个物品组的第 j 个物品的体积和价值；

**输出格式**

输出一个整数，表示最大价值。

**数据范围**

0<N,V≤100, 0<Si≤100, 0<vij,wij≤100

**输入样例**

```
3 5
2
1 2
2 4
1
3 4
1
4 5
```

**输出样例：**

```
8
```

```java
import java.util.*;
class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), S = sc.nextInt();
        int[] wgts = new int[20002], vals = new int[20002];
        int[] dp = new int[S+1];

        for (int t = 0; t < N; t++) {
            int C = sc.nextInt();
            for (int c = 0; c < C; c++) {
                wgts[c] = sc.nextInt(); vals[c] = sc.nextInt();
            }

            // 这是一个 01背包 和 完全背包 的组合问题
            // 01: for (s = S; s >= 0; s--)
            // 完全:for (s = 0; s <= S; s++)

            for (int s = S; s >= 0; s--) {  // 01 （下一层循环正反向不影响01性质）
                for (int c = 0; c < C; c++) {  // 在当前 容量大小下 遍历 组内 每个物品 
                    if (s >= wgts[c]) dp[s] = Math.max(dp[s], dp[s-wgts[c]]+vals[c]);
                }
            }
        }
        System.out.println(dp[S]);
    }
}
```



## 11. 背包问题求方案数

有 N 件物品和一个容量是 V 的背包。每件物品只能使用一次。

第 i 件物品的体积是 vi，价值是 wi。

求解将哪些物品装入背包，可使这些物品的总体积不超过背包容量，且总价值最大。

输出 **最优选法的方案数**。注意答案可能很大，请输出答案模 10^9+7 的结果。

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
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), V = sc.nextInt();
        int[] wgts = new int[N+1], vals = new int[N+1];
        int[][] dp = new int[N+2][V+1];

        for (int i = 1; i <= N; i++) {
            wgts[i] = sc.nextInt();
            vals[i] = sc.nextInt();
        }

        for (int s = N; s > 0; s--) {  // 倒序
            for (int v = 0; v <= V; v++) {  // 二维数组，所以01不要求倒序
                dp[s][v] = dp[s+1][v];
                if (v >= wgts[s]) {
                    dp[s][v] = Math.max(dp[s][v], dp[s+1][v-wgts[s]] + vals[s]);
                }
            }
        }

        int cur_v = V;
        for (int i = 1; i < N; i++) {
            if (cur_v <= 0) break;  // 结束循环
            if (cur_v-wgts[i] >= 0 && dp[i][cur_v] == dp[i+1][cur_v - wgts[i]]+vals[i]) {
                System.out.printf("%d ", i);
                cur_v = cur_v - wgts[i];    // 选了第i个物品，剩余容量就要减小。
            }
        }
        if (cur_v >= wgts[N]) System.out.printf("%d ", N);
    }
}
```



## 1013. 机器分配

总公司拥有M台 **相同** 的高效设备，准备分给下属的N个分公司。

各分公司若获得这些设备，可以为国家提供一定的盈利。盈利与分配的设备数量有关。

问：如何分配这M台设备才能使国家得到的盈利最大？

求出最大盈利值。

分配原则：每个公司有权获得任意数目的设备，但总台数不超过设备数M。

**输入格式**

第一行有两个数，第一个数是分公司数N，第二个数是设备台数M；

接下来是一个N*M的矩阵，矩阵中的第 i 行第 j 列的整数表示第 i 个公司分配 j 台机器时的盈利。

**输出格式**

第一行输出最大盈利值；

接下N行，每行有2个数，即分公司编号和该分公司获得设备台数。

答案不唯一，输入任意合法方案即可。

**数据范围**

1≤N≤10,  1≤M≤15

**输入样例：**

```
3 3
30 40 50
20 30 50
20 25 30
```

**输出样例：**

```
70
1 1
2 1
3 1
```

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), S = sc.nextInt();
        int[][] arr = new int[N+1][S+1], dp = new int[N+1][S+1];

        // 分组背包问题 (1个机器的盈利*2 不等于 2个机器的盈利)
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= S; j++) arr[i][j] = sc.nextInt();

            for (int j = S; j > 0; j--) {  // 01背包
                for (int c = 0; c <= j; c++) {  // 在这个容量区间遍历每个物品
                    dp[i][j] = Math.max(dp[i][j], dp[i-1][j-c]+arr[i][c]);
                }
            }
        }

        System.out.println(dp[N][S]);

        int cur = S;
        for (int i = N; i >= 1; i--) {
            for (int k = 0; k <= cur; k++) {
                if (dp[i][cur] == dp[i-1][cur-k] + arr[i][k]) {
                    System.out.println(i + " " + k);
                    cur -= k;
                    break;
                }
            }
        }
    }
}
```



## 487. 金明的预算方案

如果要买归类为附件的物品，必须先买该附件所属的主件。

每个主件可以有0个、1个或2个附件。

附件不再有从属于自己的附件。

金明想买的东西很多，肯定会超过妈妈限定的N元。

于是，他把每件物品规定了一个重要度，分为5等：用整数1~5表示，第5等最重要。

他还从因特网上查到了每件物品的价格（都是10元的整数倍）。

他希望在不超过N元（可以等于N元）的前提下，使每件物品的价格与重要度的乘积的总和最大。

设第j件物品的价格为v[j]，重要度为w[j]，共选中了k件物品，编号依次为j1，j2，…，jk，则所求的总和为：

v[j1]∗w[j1]+v[j2]∗w[j2]+…+v[jk]∗w[jk]（其中*为乘号）

请你帮助金明设计一个满足要求的购物单。

**输入格式**

输入文件的第1行，为两个正整数，用一个空格隔开：N m，其中N表示总钱数，m为希望购买物品的个数。

从第2行到第m+1行，第j行给出了编号为j-1的物品的基本数据，每行有3个非负整数v p q，其中v表示该物品的价格，p表示该物品的重要度（1~5），q表示该物品是主件还是附件。

如果q=0，表示该物品为主件，如果q>0，表示该物品为附件，q是所属主件的编号。

**输出格式**

输出文件只有一个正整数，为不超过总钱数的物品的价格与重要度乘积的总和的最大值（<200000）。

**数据范围**

N<32000, m<60, v<10000

**输入样例：**

```
1000 5
800 2 0
400 5 1
300 5 1
400 3 0
500 2 0
```

**输出样例：**

```
2200
```

```java
import java.util.*;

class Main {
    static int maxN = 61;
    static PII[] master = new PII[maxN];
    static List<PII>[] servant = new ArrayList[maxN];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int S = sc.nextInt(), N = sc.nextInt();
        int[] dp = new int[S+1];
        for (int i = 1; i <= N; i++) {  // 初始化
            master[i] = new PII(0, 0);
            servant[i] = new ArrayList<>();
        }

        for (int i = 1; i <= N; i++) {  // 输入
            int size = sc.nextInt(), priority = sc.nextInt(), parent = sc.nextInt();
            if (parent == 0) master[i].set(size, size * priority);
            else servant[parent].add(new PII(size, size * priority));
        }

        for (int i = 1; i <= N; i++) {
            for (int s = S; s >= 0; s--) {  // 01背包, 分组背包
                // 枚举状态（00，01，10，11,...）
                for (int st = 0; st < (1<<servant[i].size()); st++) {
                    int ts = master[i].size, tv = master[i].val;
                    for (int u = 0; u < servant[i].size(); u++) {   // 偏移量
                        if (((st >> u) & 1) == 1) {
                            ts += servant[i].get(u).size;
                            tv += servant[i].get(u).val;
                        }
                    }
                    if (s >= ts) dp[s] = Math.max(dp[s], dp[s - ts] + tv);
                }
            }
        }

        System.out.println(dp[S]);
    }

    private static class PII {
        int size, val;
        public PII(int s, int v) {size = s; val = v;}
        public void set(int s, int v) {size = s; val = v;}
    }
}
```

