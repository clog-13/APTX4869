# 194. 涂满它

在游戏开始时，系统将随机生成 N×N 的方形区域，并且区域内的每个网格都被涂成了六种颜色中的一种。

玩家从左上角开始游戏。

在每个步骤中，玩家选择一种颜色并将与左上角连通的所有格子（包括左上角）都变成该种颜色。

这里连通定义为：两个格子有公共边，并且颜色相同。

通过这种方式，玩家可以从左上角开始将所有格子都变为同一种颜色。

下图显示了 4×4 游戏的最早步骤（颜色标记为 0 到 5）：

![2.png](https://www.acwing.com/media/article/image/2019/01/17/19_c741618419-2.png)

请你求出，给定最初区域以后，最少要多少步才能把所有格子的颜色变成一样的。

#### 输入格式

输入包含不超过 20 个测试用例。

每个测试用例，第一行包含一个整数 N，表示方形区域大小。

接下里 N 行，每行包含 NN 个整数（0−5），第 i 行第 j 个整数表示第 i 行第 j 列的格子的颜色。

当输入样例 N=0 时，表示输入终止，该用例无需处理。

#### 输出格式

每个测试用例输出一个占据一行的整数，表示所需最少步数。

#### 数据范围

2≤N≤8

#### 输入样例：

```
2
0 0 
0 0
3
0 1 2
1 1 2
2 2 1
0
```

#### 输出样例：

```
0
3
```



## BFS

```java
import java.util.*;

class Main {
    int N, maxN = 10;
    int[][] arr = new int[maxN][maxN], st = new int[maxN][maxN];
    int[][][] st_backup = new int[70][maxN][maxN];
    int[] dx = {-1, 1, 0, 0}, dy = {0, 0,-1, 1};

    public static void main(String[] args) {
        new Main().init();
    }

    private void init() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            N = sc.nextInt(); if (N == 0) break;
            for (int[] s : st) Arrays.fill(s, 0);

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) arr[i][j] = sc.nextInt();
            }

            fill(0, 0, arr[0][0]);
            int depth = f();
            while (!dfs(0, depth)) depth++;
            System.out.println(depth);
        }
    }

    boolean dfs(int u, int depth) {
        int h = f();
        if (u+h > depth) return false;
        if (h == 0) return true;
        for (int c = 0; c <= 5; c++) {
            copy(st, st_backup[u]);
            boolean flag = false;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (st[i][j] == 2 && arr[i][j] == c) {  // 相邻且是现在假设要的颜色
                        fill(i, j, c);
                        flag = true;
                    }
                }
            }
            if (!flag) continue;  // 当前颜色不存在
            if (dfs(u+1, depth)) return true;
            copy(st_backup[u], st);
        }
        return false;
    }

    void fill(int x, int y, int c) {
        st[x][y] = 1;  // 已经合法
        for (int s = 0; s < 4; s++) {
            int tx = x+dx[s], ty = y+dy[s];
            if (tx>=0 && tx<N && ty>=0 && ty<N && st[tx][ty] != 1) {
                if (arr[tx][ty] != c) st[tx][ty] = 2;  // 相邻，待判断
                else fill(tx, ty, c);
            }
        }
    }

    int f() {
        boolean[] cout = new boolean[6];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (st[i][j] != 1) cout[arr[i][j]] = true;  // 不合法
            }
        }
        int cnt = 0;  // 外面还有多少种颜色
        for (int i = 0; i <= 5; i++) if (cout[i]) cnt++;
        return cnt;
    }

    void copy(int[][] src, int[][] des) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, des[i], 0, src[0].length);
        }
    }
}
```

