# 298. 围栏

有 N 块木板从左到右排成一行，有 M 个工匠对这些木板进行粉刷，每块木板至多被粉刷一次。

第 i 个木匠要么不粉刷，要么粉刷包含木板 Si 的，长度不超过 Li 的连续的一段木板，每粉刷一块可以得到 Pi 的报酬。

不同工匠的 Si 不同。

请问如何安排能使工匠们获得的总报酬最多。

#### 输入格式

第一行包含两个整数 N 和 M。

接下来 M 行，每行包含三个整数 Li,Pi,Si。

#### 输出格式

输出一个整数，表示结果。

#### 数据范围

1≤N≤16000, 1≤M≤100, 1≤Pi≤10000

#### 输入样例：

```
8 4
3 2 2
3 2 3
3 3 5
1 1 7 
```

#### 输出样例：

```
17
```



## DP

```java
import java.util.*;

public class Main {
    int N, M, maxN = 16010, maxM = 110;
    int[] q = new int[maxN];
    int[][] f = new int[maxM][maxN];


    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); M = sc.nextInt();
        Car[] arr = new Car[M];
        for (int i = 0; i < M; i++) {
            arr[i] = new Car(sc.nextInt(), sc.nextInt(), sc.nextInt());
        }
        Arrays.sort(arr, Comparator.comparingInt(a -> a.s));

        for (int i = 1; i <= M; i++) {  // 遍历木匠
            int hh = 0, tt = -1;
            for (int j = 0; j <= N; j++) {  // 遍历木板
                f[i][j] = f[i-1][j];  // 到第i个木匠 i木匠刷 j 的最大报酬 (即j是连续木板的右端)
                if (j > 0) f[i][j] = Math.max(f[i][j], f[i][j-1]);

                int l = arr[i-1].l, p = arr[i-1].p, s = arr[i-1].s;
                if (hh <= tt && q[hh]+l < j) hh++;
                if (j < s) {  // 并不是单调队列模板DP
                    // 如果刷了还没前个的情况高，就不刷
                    while (hh <= tt && f[i-1][q[tt]] + (j-q[tt])*p <= f[i-1][j]) tt--;  
                    q[++tt] = j;  // 保留可能的 刷了比前一个报酬高的局部方案
                }
                if (j >= s && hh <= tt) {  // 枚举的 木匠刷到j 大于s (合法状态)
                    f[i][j] = Math.max(f[i][j], f[i-1][q[hh]] + (j-q[hh])*p);
                }
            }
        }
        System.out.println(f[M][N]);
    }

    static class Car {
        int l, p, s;
        public Car(int ll, int pp, int ss) {
            l = ll; p = pp; s = ss;
        }
    }
}
```

