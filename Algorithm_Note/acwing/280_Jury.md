# 280. 陪审团

在一个遥远的国家，一名嫌疑犯是否有罪需要由陪审团来决定。

陪审团是由法官从公民中挑选的。

法官先随机挑选 N 个人（编号 1,2…,N）作为陪审团的候选人，然后再从这 N 个人中按照下列方法选出 M 人组成陪审团。

首先，参与诉讼的控方和辩方会给所有候选人打分，分值在 0 到 20 之间。

第 ii 个人的得分分别记为 p[i] 和 d[i]。

为了公平起见，法官选出的 M 个人必须满足：辩方总分 D 和控方总分 P 的差的绝对值 |D−P| 最小。

如果选择方法不唯一，那么再从中选择辨控双方总分之和 D+P 最大的方案。

求最终的陪审团获得的辩方总分 D、控方总分 P，以及陪审团人选的编号。

**注意**：若陪审团的人选方案不唯一，则任意输出一组合法方案即可。

#### 输入格式

输入包含多组测试数据。

每组测试数据第一行包含两个整数 N 和 M。

接下来 N 行，每行包含两个整数 p[i] 和 d[i]。

每组测试数据之间隔一个空行。

当输入数据 N=0，M=0 时，表示结束输入，该数据无需处理。

#### 输出格式

对于每组数据，第一行输出 `Jury #C`，C 为数据编号，从 1 开始。

第二行输出 `Best jury has value P for prosecution and value D for defence:`，P 为控方总分，D 为辩方总分。

第三行输出按升序排列的陪审人选编号，每个编号前输出一个空格。

每组数据输出完后，输出一个空行。

#### 数据范围

1≤N≤200, 1≤M≤20, 0≤p[i],d[i]≤20

#### 输入样例：

```
4 2
1 2
2 3
4 1
6 2
0 0
```

#### 输出样例：

```
Jury #1
Best jury has value 6 for prosecution and value 4 for defence:
 2 3
```



## DP

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        int base = 400, T = 1, maxN = 810;
        Scanner sc = new Scanner(System.in);
        while (true) {
            int N = sc.nextInt(), M = sc.nextInt();
            if (N==0 && M==0) return;
            int[] D = new int[N+1], P = new int[N+1];
            int[][][] f = new int[N+1][M+1][maxN];  
            for (int i = 1; i <= N; i++) {
                D[i] = sc.nextInt(); P[i] = sc.nextInt();
            }
            for (int[][] ff: f) for (int[] i: ff) Arrays.fill(i, 0xf3f3f3f3);
            f[0][0][base] = 0;  // f[i][j][k]:前i个人选j个差值为k的 方案集合 的最大值

            for (int i = 1; i <= N; i++) {
                for (int j = 0; j <= M; j++) {
                    for (int k = 0; k < maxN; k++) {
                        f[i][j][k] = f[i-1][j][k];
                        int tmp = k - (D[i] - P[i]);
                        if (tmp < 0 || tmp >= maxN) continue;
                        if (j == 0) continue;
                        f[i][j][k] = Math.max(f[i][j][k], f[i-1][j-1][tmp] + D[i] + P[i]);
                    }
                }
            }

            int v = 0, k = 0;
            while (f[N][M][base-v]<0 && f[N][M][base+v]<0) v++;
            if (f[N][M][base + v] > f[N][M][base - v]) k = base + v;
            else k = base - v;

            int cnt = 0, i = N, j = M;
            int[] res = new int[M];
            while (j > 0) {
                if (f[i][j][k] == f[i-1][j][k]) i--;
                else {
                    res[cnt++] = i;
                    k -= (D[i] - P[i]);
                    i--; j--;
                }
            }

            int sd = 0, sp = 0;
            for (i = 0; i < cnt; i ++ ) {
                sd += D[res[i]]; sp += P[res[i]];
            }

            System.out.printf("Jury #%d\n", T++);
            System.out.printf("Best jury has value %d for prosecution and value %d for defence:\n", sd, sp);
            Arrays.sort(res);
            for (i = 0; i < cnt; i++) System.out.print(" "+res[i]);
            System.out.println("\n");
        }
    }
}
```

