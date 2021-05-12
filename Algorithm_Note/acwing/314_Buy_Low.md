# 314. 低买

给定一段时间内股票的每日售价（正 16 位整数）。

你可以选择在任何一天购买股票。

每次你选择购买时，当前的股票价格必须严格低于你之前购买股票时的价格。

编写一个程序，确定你应该在哪些天购进股票，可以使得你能够购买股票的次数最大化。

例如，下面是一个股票价格时间表：

```
 Day   1  2  3  4  5  6  7  8  9 10 11 12

Price 68 69 54 64 68 64 70 67 78 62 98 87
```

如果每次购买都必须遵循当前股票价格严格低于之前购买股票时的价格，那么投资者最多可以购买四次该股票。

买进方案之一为：

```
Day    2  5  6 10

Price 69 68 64 62
```

#### 输入格式

第 1 行包含整数 N，表示给出的股票价格的天数。

第 2 至最后一行，共包含 N 个整数，每行 10 个，最后一行可能不够 10 个，表示 N 天的股票价格。

同一行数之间用空格隔开。

#### 输出格式

输出占一行，包含两个整数，分别表示最大买进股票次数以及可以达到最大买进次数的方案数。

如果两种方案的买入日序列不同，但是价格序列相同，则认为这是相同的方案（只计算一次）。

#### 数据范围

1≤N≤5000

#### 输入样例1：

```
12
68 69 54 64 68 64 70 67 78 62
98 87
```

#### 输出样例1：

```
4 2
```

#### 输入样例2：

```
5
4 3 2 1 1
```

#### 输出样例2：

```
4 1
```



## DP

```java
import java.util.*;
public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), max = 0, cnt = 0;
        int[] arr = new int[N+1], f = new int[N+1], g = new int[N+1];
        for (int i = 1; i <= N; i++) arr[i] = sc.nextInt();
        Arrays.fill(f, 1);
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j < i; j++) {  // LIS
                if (arr[j] > arr[i]) f[i] = Math.max(f[i], f[j]+1);
            }
            for (int j = 1; j < i; j++) {
                if (arr[i] == arr[j]) g[j] = 0;  // 价格序列相同，则认为这是相同的方案
                else if (arr[j]>arr[i] && f[i]==f[j]+1) {
                    g[i] += g[j];
                }
            }
            max = Math.max(max, f[i]);
            if (f[i]==1) g[i] = 1;
        }
        
        for (int i = 1; i <= N; i++) if (f[i]==max) cnt += g[i];
        System.out.println(max+" "+cnt);
    }
}
```

