# 动态规划-背包专题

## 2.01背包

有 N 件物品和一个容量是 V 的背包。每件物品只能使用一次。

第 i 件物品的体积是 vi，价值是 wi。

求解将哪些物品装入背包，可使这些物品的总体积不超过背包容量，且总价值最大。
输出最大价值。

**输入格式**

第一行两个整数，N，V，用空格隔开，分别表示物品数量和背包容积。

接下来有 N 行，每行两个整数 vi,wi，用空格隔开，分别表示第 i 件物品的体积和价值。

**输出格式**

输出一个整数，表示最大价值。

**输入样例**

```
4 5
1 2
2 4
3 4
4 5
```

**输出样例：**

```
8
```

```java
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), V = sc.nextInt();

        int[] dp = new int[V+1];
        for (int s = 1; s <= N; s ++ ) {
            int size = sc.nextInt(), val = sc.nextInt();
            for (int v = V; v >= size; v--) {  // 01，反向
                dp[v] = Math.max(dp[v], dp[v-size] + val); 
            }
        }
        System.out.println(dp[V]);
    }
}
```

## 3.完全背包

有 N 种物品和一个容量是 V 的背包，每种物品都有无限件可用。

第 i 种物品的体积是 vi，价值是 wi。

求解将哪些物品装入背包，可使这些物品的总体积不超过背包容量，且总价值最大。
输出最大价值。

**输入格式**

第一行两个整数，N，V，用空格隔开，分别表示物品种数和背包容积。

接下来有 N 行，每行两个整数 vi,wi，用空格隔开，分别表示第 i 种物品的体积和价值。

**输出格式**

输出一个整数，表示最大价值。

**输入样例：**

```
4 5
1 2
2 4
3 4
4 5
```

**输出样例：**

```
10
```

```java
class Main {
    public static void main(String[] args) {
        Scanner sc  =new Scanner(System.in);
        int N = sc.nextInt(), V = sc.nextInt();
        int[] dp = new int[V+1];
        for (int s = 0; s < N; s ++ ) {
            int size = sc.nextInt(), val = sc.nextInt();
            for (int v = size; v <= V; v++) {
                dp[v] = Math.max(dp[v], dp[v-size] + val);
            }
        }
        System.out.println(dp[V]);
    }
}
```

## 4.多重背包

有 N 种物品和一个容量是 V 的背包。

第 i 种物品最多有 si 件，每件体积是 vi，价值是 wi。

求解将哪些物品装入背包，可使物品体积总和不超过背包容量，且价值总和最大。
输出最大价值。

**输入格式**

第一行两个整数，N，V，用空格隔开，分别表示物品种数和背包容积。

接下来有 N 行，每行三个整数vi,wi,si，用空格隔开，分别表示第 i 种物品的体积、价值和数量。

**输出格式**

输出一个整数，表示最大价值。

**输入样例：**

```
4 5
1 2 3
2 4 1
3 4 3
4 5 2
```

**输出样例：**

```
10
```

```java
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), V = sc.nextInt();

        int[] dp = new int[V + 1];
        for (int s = 1; s <= N; s ++ ) {
            int size = sc.nextInt(), val = sc.nextInt(), cout =  sc.nextInt();
            
            for (int v = V; v >= size; v--) {  // 01,反向
                for (int c = 0; c<=cout && c*size<=v; c++) {  // 遍历个数
                    dp[v] = Math.max(dp[v], dp[v - c*size] + c*val);
                }
            }
        }
        
        System.out.println(dp[V]);
    }
}
```

## 5.多重背包 II（二进制优化）

```jav
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), V = sc.nextInt();

        // 预处理
        int[] vals = new int[200002], wgts = new int[200002];
        int idx = 0;
        for (int s = 0; s < N; s++) {
            int size = sc.nextInt(), val = sc.nextInt(), cout = sc.nextInt();

            for (int k = 1; k < cout; k *= 2) {
                wgts[idx] = k * size;
                vals[idx] = k * val;
                idx++;
                cout -= k;
            }
            if (cout > 0) {
                wgts[idx] = cout * size;
                vals[idx] = cout * val;
                idx++;
            }
        }

        // 01背包模板
        int[] dp = new int[V + 1];
        int res = 0;
        for (int i = 0; i < idx; i++) {	// 注意这里是 idx
            for (int v = V; v >= wgts[i]; v--) {  // 01, 遍历组合方案
                dp[v] = Math.max(dp[v], dp[v-wgts[i]] + vals[i]);
                res = Math.max(res, dp[v]);
            }
        }

        System.out.println(res);
    }
}
```

## 12. 背包问题求具体方案

有 N 件物品和一个容量是 V 的背包。每件物品只能使用一次。

第 i 件物品的体积是 vi，价值是 wi。

求解将哪些物品装入背包，可使这些物品的总体积不超过背包容量，且总价值最大。

输出 **字典序最小的方案**。这里的字典序是指：所选物品的编号所构成的序列。物品的编号范围是 1…N。

#### 输入格式

第一行两个整数，N，V，用空格隔开，分别表示物品数量和背包容积。

接下来有 N 行，每行两个整数 vi,wi，用空格隔开，分别表示第 i 件物品的体积和价值。

#### 输出格式

输出一行，包含若干个用空格隔开的整数，表示最优解中所选物品的编号序列，且该编号序列的字典序最小。

物品编号范围是 1…N。

#### 数据范围

0<N,V≤1000, 0<vi,wi≤1000

#### 输入样例：

```
4 5
1 2
2 4
3 4
4 6
```

#### 输出样例：

```
1 4
```
![](pic\Knapsack_1.png)
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

