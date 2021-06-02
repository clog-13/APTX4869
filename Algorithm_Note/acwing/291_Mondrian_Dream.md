# 291. 蒙德里安的梦想

求把 N×M 的棋盘分割成若干个 1×2 的的长方形，有多少种方案。

例如当 N=2，M=4 时，共有 5 种方案。当 N=2，M=3 时，共有 3 种方案。

如下图所示：

![2411_1.jpg](https://www.acwing.com/media/article/image/2019/01/26/19_4dd1644c20-2411_1.jpg)

#### 输入格式

输入包含多组测试用例。

每组测试用例占一行，包含两个整数 N 和 M。

当输入用例 N=0，M=0 时，表示输入终止，且该用例无需处理。

#### 输出格式

每个测试用例输出一个结果，每个结果占一行。

#### 数据范围

1≤N,M≤11

#### 输入样例：

```
1 2
1 3
1 4
2 2
2 3
2 4
2 11
4 11
0 0
```

#### 输出样例：

```
1
0
1
2
3
5
144
51205
```



## 状态压缩DP

```java
import java.util.*;

class Main {
    int N, M, maxN = 12, maxM = 1<<maxN;
    boolean[] states = new boolean[maxM];
    long[][] f = new long[maxN][maxM];

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            N = sc.nextInt(); M = sc.nextInt();
            if (N==0 && M==0) return;

            Arrays.fill(states, true);
            for (int st = 0; st < 1<<N; st++) {  // 枚举每一列的占位状态
                int cnt = 0;
                for (int i = 0; i < N; i++) {  // 判断当前 列st 的状况
                    if ((st>>i & 1) == 1) { // 把连续空格数为奇数的状态设定为false
                        if ((cnt&1) == 1) states[st] = false;  // 单个空格无法摆放竖着的方块
                        cnt = 0;
                    } else {
                        cnt++;
                    }
                }
                if ((cnt&1) == 1) states[st] = false;
            }

            for (int i = 0; i < maxN; i++) Arrays.fill(f[i], 0L);
            f[0][0] = 1;  // f[i][cur]:已将前i-1列摆满，i-1列伸出到i列的cur状态的方案数
            for (int i = 1; i <= M; i++) {  // 对于每一列
                for (int cur = 0; cur < 1<<N; cur++) {  // 枚举i列每一种状态
                    for (int pre = 0; pre < 1<<N; pre++) {  // 枚举i-1列每一种状态
                        if ((cur&pre)==0 && states[cur|pre]) {  // cur&pre==0: 不矛盾
                            f[i][cur] += f[i-1][pre];  // st[cur|pre]:剩下的格子可以由竖块填满
                        }
                    }
                }
            }

            System.out.println(f[M][0]);  // 求的是第m-1行排满，并且第m-1行不向外伸出块的情况
        }
    }
}
```

