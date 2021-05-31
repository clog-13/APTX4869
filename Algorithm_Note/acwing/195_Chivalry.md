# 195. 骑士精神

在一个 5×5 的棋盘上有 12 个白色的骑士和 12 个黑色的骑士，且有一个空位。

在任何时候一个骑士都能按照骑士的走法（它可以走到和它横坐标相差为 1，纵坐标相差为 2 或者横坐标相差为 2，纵坐标相差为 1 的格子）移动到空位上。

给定一个初始的棋盘，怎样才能经过移动变成如下目标棋盘：为了体现出骑士精神，他们必须以最少的步数完成任务。

![aa.jpg.gif](https://www.acwing.com/media/article/image/2019/01/17/19_710140aa19-aa.jpg.gif)

#### 输入格式

第一行有一个正整数 T，表示一共有 T 组数据。

接下来有 T 个 5×5 的矩阵，0 表示白色骑士，1 表示黑色骑士，`*` 表示空位。

两组数据之间没有空行。

#### 输出格式

每组数据输出占一行。

如果能在 15 步以内（包括 15 步）到达目标状态，则输出步数，否则输出 −1。

#### 数据范围

1≤T≤10

#### 输入样例：

```
2
10110
01*11
10111
01001
00000
01011
110*1
01110
01010
00100
```

#### 输出样例：

```
7
-1
```



## IDA*(嗯搜)

```
import java.io.*;

class Main {
    int N, maxN = 5;
    char[][] arr = new char[maxN][maxN];
    int[] dx = { 1, 1, 2, 2,-2,-2,-1,-1}, dy = {-2, 2,-1, 1,-1, 1,-2, 2};
    char[][] stand = {
            {'1', '1', '1', '1', '1'},
            {'0', '1', '1', '1', '1'},
            {'0', '0', '*', '1', '1'},
            {'0', '0', '0', '0', '1'},
            {'0', '0', '0', '0', '0'}};

    public static void main (String [] args) throws IOException {
        new Main().init();
    }

    void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        while (N-- > 0) {
            for (int i = 0; i < 5; i++)  arr[i] = br.readLine().toCharArray();

            int x = 0, y = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (arr[i][j] == '*') {
                        x = i; y = j;
                    }
                }
            }

            int depth = 0;
            while (depth <= 15 && !dfs(x, y, 0, depth)) depth++;
            if (depth <= 15) System.out.println(depth);
            else System.out.println(-1);

            // boolean flag = false;
            // for (int depth = 0; depth <= 15; depth++) {
            //     if (dfs(x, y, 0, depth)) {
            //         System.out.println(depth);
            //         flag = true;
            //         break;
            //     }
            // }
            // if (!flag) System.out.println(-1);
        }
    }

    boolean dfs(int x, int y, int u, int depth) {
        int val = f();
        if (val == 0) return true;
        if (u+val > depth) return false;

        for (int i = 0; i < 8; i++) {
            int tx = x+dx[i], ty = y+dy[i];
            if (tx<0 || tx>=5 || ty<0 || ty>=5) continue;
            swap(x, y, tx, ty);
            if (dfs(tx, ty, u+1, depth)) return true;
            swap(tx, ty, x, y);
        }
        return false;
    }

    int f() {
        int res = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (stand[i][j]!='*' && arr[i][j]!=stand[i][j]) res++;
            }
        }
        return res;
    }

    void swap(int x, int y, int tx, int ty) {
        char tmp = arr[x][y];
        arr[x][y] = arr[tx][ty];
        arr[tx][ty] = tmp;
    }
}
```