# 292. 炮兵阵地

司令部的将军们打算在 N×M 的网格地图上部署他们的炮兵部队。

一个 N×M 的地图由 N 行 M 列组成，地图的每一格可能是山地（用 `H` 表示），也可能是平原（用 `P` 表示），如下图。

在每一格**平原地形上最多可以布置一支炮兵部队（山地上不能够部署炮兵部队）**；一支炮兵部队在地图上的攻击范围如图中黑色区域所示：

![1185_1.jpg](https://www.acwing.com/media/article/image/2019/02/16/19_d512cdba31-1185_1.jpg)

如果在地图中的灰色所标识的平原上部署一支炮兵部队，则图中的黑色的网格表示它能够攻击到的区域：沿横向左右各两格，沿纵向上下各两格。图上其它白色网格均攻击不到。

从图上可见炮兵的攻击范围不受地形的影响。

现在，将军们规划如何部署炮兵部队，在防止误伤的前提下（保证任何两支炮兵部队之间不能互相攻击，即任何一支炮兵部队都不在其他支炮兵部队的攻击范围内），在整个地图区域内最多能够摆放多少我军的炮兵部队。

#### 输入格式

第一行包含两个由空格分割开的正整数，分别表示 N 和 M；

接下来的 N 行，每一行含有连续的 M 个字符(`P` 或者 `H`)，中间没有空格。按顺序表示地图中每一行的数据。

#### 输出格式

仅一行，包含一个整数 K，表示最多能摆放的炮兵部队的数量。

#### 数据范围

N≤100,  M≤10

#### 输入样例：

```
5 4
PHPP
PPHH
PPPP
PHPP
PHHP
```

#### 输出样例：

```
6
```



## 状压DP

```java
import java.util.*;
public class Main {
    int N, M, maxN = 110, maxM = 1<<10;
    int[] arr = new int[maxN], cout = new int[maxM];
    int[][][] f = new int[2][maxM][maxM];
    List<Integer> state = new ArrayList<>();

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); M = sc.nextInt();
        for (int i = 1; i <= N; i++) {  // 读入地形
            char[] str = sc.next().toCharArray();
            int st = 0;
            for (int j = 0; j < M; j++) {  //不可填表示1
                if (str[j] == 'H') st |= 1<<j;
            }
            arr[i] = st;
        }

        for (int st = 0; st < 1<< M; st++) {  // 预处理出 非法相邻的状态, 离散化
            if ((st>>1&st)>0 || (st>>2&st)>0) continue;
            state.add(st);
            cout[st] = count(st);
        }

        for (int i = 1; i <= N; i++) {  // dp[i][j][k]: 第i行状态为j, 第i-1行状态为k
            for (int j = 0; j < state.size(); j++) {      // i
                for (int k = 0; k < state.size(); k++) {  // i-1
                    for (int u = 0; u < state.size(); u++) {  //  i-2
                        int cur = state.get(j), p1 = state.get(k), p2 = state.get(u);

                        if ((cur&p1)>0 || (cur&p2)>0 || (p1&p2)>0) continue;  // 判断枚举的状态
                        if ((arr[i]&cur)>0 || (arr[i-1]&p1)>0) continue;  // 判断地形(i-2会被前面过滤掉)
                        f[i & 1][j][k] = Math.max(f[i & 1][j][k], f[(i-1)&1][k][u] + cout[cur]);  // 滚动数组
                    }
                }
            }
        }

        int res = 0;
        for (int j = 0; j < state.size(); j++) {
            for (int k = 0; k < state.size(); k++) {
                res = Math.max(res,f[N & 1][j][k]);
            }
        }
        System.out.println(res);
    }

    int count(int n) {
        int res = 0;
        for ( ; n > 0; n -= n&-n) res++;
        return res;
    }
}
```

.