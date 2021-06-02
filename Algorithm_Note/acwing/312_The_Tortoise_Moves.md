# 312. 乌龟棋

小明过生日的时候，爸爸送给他一副乌龟棋当作礼物。

乌龟棋的棋盘只有一行，该行有 N 个格子，每个格子上一个分数（非负整数）。

棋盘第 1 格是唯一的起点，第 N 格是终点，游戏要求玩家控制一个乌龟棋子从起点出发走到终点。

乌龟棋中共有 M 张爬行卡片，分成 4 种不同的类型（M 张卡片中不一定包含所有 4 种类型的卡片），每种类型的卡片上分别标有 1、2、3、4 四个数字之一，表示使用这种卡片后，乌龟棋子将向前爬行相应的格子数。

游戏中，玩家每次需要从所有的爬行卡片中选择一张之前没有使用过的爬行卡片，控制乌龟棋子前进相应的格子数，每张卡片只能使用一次。

游戏中，乌龟棋子自动获得起点格子的分数，并且在后续的爬行中每到达一个格子，就得到该格子相应的分数。

玩家最终游戏得分就是乌龟棋子从起点到终点过程中到过的所有格子的分数总和。

很明显，用不同的爬行卡片使用顺序会使得最终游戏的得分不同，小明想要找到一种卡片使用顺序使得最终游戏得分最多。

现在，告诉你棋盘上每个格子的分数和所有的爬行卡片，你能告诉小明，他最多能得到多少分吗？

#### 输入格式

输入文件的每行中两个数之间用一个空格隔开。

第 1 行 2 个正整数 N 和 M，分别表示棋盘格子数和爬行卡片数。

第 2 行 N 个非负整数，a1,a2,……,aN，其中 ai 表示棋盘第 i 个格子上的分数。

第 3 行 M 个整数，b1,b2,……,bM，表示 M 张爬行卡片上的数字。

输入数据保证到达终点时刚好用光 M 张爬行卡片。

#### 输出格式

输出只有 1 行，包含 1 个整数，表示小明最多能得到的分数。

#### 数据范围

1≤N≤350, 1≤M≤120, 0≤ai≤100, 1≤bi≤4, 每种爬行卡片的张数不会超过 40。

#### 输入样例：

```
9 5
6 10 14 2 8 8 18 5 17
1 3 1 2 1
```

#### 输出样例：

```
73
```



## DP

```java
import java.util.*;

public class Main {
    int N, M, maxN = 360, maxM = 41;
    int[] arr = new int[maxN], cout = new int[5];
    int[][][][] f = new int[maxM][maxM][maxM][maxM];

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); M = sc.nextInt();
        for (int i = 0; i < N; i++) arr[i] = sc.nextInt();
        for (int i = 0; i < M; i++) cout[sc.nextInt()]++;

        for (int A = 0; A <= cout[1]; A++) {
            for (int B = 0; B <= cout[2]; B++) {
                for (int C = 0; C <= cout[3]; C++) {
                    for (int D = 0; D <= cout[4]; D++) {
                        int cur = arr[A + 2*B + 3*C + 4*D];
                        int max = cur;  // f[b1][b2][b3][b4] 表示用了b1,b2,b3,b4张 时累计的最大分
                        if (A > 0) max = Math.max(max, f[A-1][B][C][D] + cur);
                        if (B > 0) max = Math.max(max, f[A][B-1][C][D] + cur);
                        if (C > 0) max = Math.max(max, f[A][B][C-1][D] + cur);
                        if (D > 0) max = Math.max(max, f[A][B][C][D-1] + cur);
                        f[A][B][C][D] = max;
                    }
                }
            }
        }
        System.out.println(f[cout[1]][cout[2]][cout[3]][cout[4]]);
    }
}
```
