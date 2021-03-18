# 275. 传纸条
一次素质拓展活动中，班上同学安排坐成一个 m 行 n 列的矩阵，而小渊和小轩被安排在矩阵对角线的两端，因此，他们就无法直接交谈了。

幸运的是，他们可以通过传纸条来进行交流。

纸条要经由许多同学传到对方手里，小渊坐在矩阵的左上角，坐标(1,1)，小轩坐在矩阵的右下角，坐标(m,n)。

从小渊传到小轩的纸条只可以向下或者向右传递，从小轩传给小渊的纸条只可以向上或者向左传递。 

在活动进行中，小渊希望给小轩传递一张纸条，同时希望小轩给他回复。

班里每个同学都可以帮他们传递，但只会帮他们一次，也就是说如果此人在小渊递给小轩纸条的时候帮忙，那么在小轩递给小渊的时候就不会再帮忙，反之亦然。 

还有一件事情需要注意，全班每个同学愿意帮忙的好感度有高有低（注意：小渊和小轩的好心程度没有定义，输入时用0表示），可以用一个0-100的自然数来表示，数越大表示越好心。

小渊和小轩希望尽可能找好心程度高的同学来帮忙传纸条，即找到来回两条传递路径，使得这两条路径上同学的好心程度之和最大。

现在，请你帮助小渊和小轩找到这样的两条路径。

**输入格式**
第一行有2个用空格隔开的整数 m 和 n，表示学生矩阵有 m 行 n 列。

接下来的 m 行是一个 m∗n 的矩阵，矩阵中第 i 行 j 列的整数表示坐在第 i 行 j 列的学生的好心程度，每行的 n 个整数之间用空格隔开。

**输出格式**
输出一个整数，表示来回两条路上参与传递纸条的学生的好心程度之和的最大值。

**数据范围**
1≤n,m≤50
**输入样例：**
>3 3
0 3 9
2 8 5
5 7 0

**输出样例：**
>34

## DP

1、f[i1, j1, i2, j2]表示所有从 (1,1) 分别走到(i1,j1),(i2,j2)的路径的最大值

2、由于走两次可以看成是两条路径同时走，因此k表示两条路线当前走到的各自的横纵坐标之和k \== i1 + j1 \== i2 + j2

注意：在i1 + j1 \== i2 + j2时，两条路径走到的当前格子可能（只是可能）重合

因为 k−1 \== i1 + j1 − 1 \== i2 + j2 − 1

由f\[i1]\[j1−1]\[i2]\[j2−1] 转化为 f\[k−1]\[i1]\[i2]

同理可得：（一个格子只能被取一次）

f\[i1−1]\[j1]\[i2−1]\[j2]\==f[k−1]\[i1−1][i2−1]

f\[i1]\[j1−1]\[i2−1]\[j2]\==f[k−1]\[i1][i2−1]

f\[i1−1]\[j1]\[i2]\[j2−1]\==f[k−1]\[i1−1][i2]



f\[k]\[i1]\[i2] 表示从(1, 1)和(1, 1)分别走到(i1, k-i1)和(i2, k-i2)的路径最大值

```java
import java.util.*;

public class Main {
    int maxN = 55;
    int R, C;
    int[][] cost = new int[maxN][maxN];
    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        R = sc.nextInt();
        C = sc.nextInt();

        for (int i = 1 ; i <= R ; i++)
            for (int j = 1 ; j <= C ; j++)
                cost[i][j] = sc.nextInt();

        int res = dp1();
        // int res = dp2();

        System.out.println(res);
    }

    int dp1() {
        int[][][] f = new int[maxN << 1][maxN][maxN];

        for (int k = 2 ; k <= R+C ; k++) {
            // le:至少已經走了 C 步，則至少需要從 k-C 行開始
            // ri:1 <= x <= R,
            int le = Math.max(1, k-C);
            int ri = Math.min(k-1, R);
            for (int x1 = le; x1 <= ri; x1++) {
                for (int x2 = le; x2 <= ri; x2++) {
                    int v = cost[x1][k-x1];
                    if (x1 != x2) v += cost[x2][k-x2];

                    // k = x1+y1 = x2+y2
                    int t = 0;
                    t = Math.max(t, f[k-1][x1][x2]);      // dp[x1][y1-1][x2][y2-1]
                    t = Math.max(t, f[k-1][x1][x2-1]);    // dp[x1][y1-1][x2-1][y2]
                    t = Math.max(t, f[k-1][x1-1][x2]);    // dp[x1-1][y1][x2][y2-1]
                    t = Math.max(t, f[k-1][x1-1][x2-1]);  // dp[x1-1][y1][x2-1][y2]
                    f[k][x1][x2] = t + v;
                }
            }
        }

        return f[C+R][R][R];
    }

    int dp2(){
        int[][][][] f = new int[maxN][maxN][maxN][maxN];

        for (int x1 = 1; x1 <= R; x1++) {
            for (int x2 = 1; x2 <= R; x2++) {
                for (int y1 = 1; y1 <= C; y1++) {
                    for (int y2 = 1; y2 <= C; y2 ++) {
                        int v = cost[x1][y1];
                        if (x1 != x2 && y1 != y2) v += cost[x2][y2];
                        
                        int t = 0;
                        t = Math.max(t, f[x1-1][y1][x2-1][y2]);
                        t = Math.max(t, f[x1][y1-1][x2-1][y2]);
                        t = Math.max(t, f[x1-1][y1][x2][y2-1]);
                        t = Math.max(t, f[x1][y1-1][x2][y2-1]);
                        f[x1][y1][x2][y2] = t + v;
                    }
                }
            }
        }

        return f[R][C][R][C];
    }
}
```

