# 152. 城市游戏

有一天，小猫 rainbow 和 freda 来到了湘西张家界的天门山玉蟾宫，玉蟾宫宫主蓝兔盛情地款待了它们，并赐予它们一片土地。

这片土地被分成 N×M 个格子，每个格子里写着 `R` 或者 `F`，`R` 代表这块土地被赐予了 rainbow，`F` 代表这块土地被赐予了 freda。

现在 freda 要在这里卖萌。。。它要找一块矩形土地，要求这片土地都标着 `F` 并且面积最大。

但是 rainbow 和 freda 的 OI 水平都弱爆了，找不出这块土地，而蓝兔也想看 freda 卖萌（她显然是不会编程的……），所以它们决定，如果你找到的土地面积为 S，它们将给你 3×S 两银子。

#### 输入格式

第一行包括两个整数 N,M，表示矩形土地有 N 行 M 列。

接下来 N 行，每行 M 个用空格隔开的字符 `F` 或 `R`，描述了矩形土地。

每行末尾没有多余空格。

#### 输出格式

输出一个整数，表示你能得到多少银子，即(3×3×最大 `F` 矩形土地面积)的值。

#### 数据范围

1≤N,M≤1000

#### 输入样例：

```
5 6
R F F F F F
F F F F F F
R R R F F F
F F F F F F
F F F F F F
```

#### 输出样例：

```
45
```



## 悬线法

```java
import java.io.*;

class Main{
    static StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
    static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
    static int nextInt() throws IOException {
        in.nextToken();
        return (int)in.nval;
    }
    static String next() throws IOException {
        in.nextToken();
        return in.sval;
    }

    public static void main(String[] args) throws IOException {
        int N = nextInt(), M = nextInt(), res = 0;
        int[][] arr = new int[N][M], up = new int[N][M];
        int[][] left = new int[N][M], rigt = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                arr[i][j] = next().equals("F") ? 1 : 0;
                left[i][j] = j; rigt[i][j] = j; up[i][j] = 1;
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 1; j < M; j++) {
                if (arr[i][j] == 1 && arr[i][j - 1] == 1) {
                    left[i][j] = left[i][j - 1];  // 连续 1 最左坐标
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = M - 2; j >= 0; j--) {
                if (arr[i][j] == 1 && arr[i][j + 1] == 1) {
                    rigt[i][j] = rigt[i][j + 1];  // 连续 1 最右坐标
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (arr[i][j] != 0) {  // 遍历每个 1
                    int le = left[i][j], ri = rigt[i][j];
                    if (i > 0 && arr[i - 1][j] == 1) {
                        up[i][j] = up[i - 1][j] + 1;
                        le = Math.max(le, left[i - 1][j]);
                        ri = Math.min(ri, rigt[i - 1][j]);
                    }
                    left[i][j] = le; rigt[i][j] = ri;
                    res = Math.max((ri - le + 1) * up[i][j], res);
                }
            }
        }

        out.println(res*3); out.close();
    }
}
```



## 单调栈

```java
import java.io.*;
import java.util.*;

class Main {
    static StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
    static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
    static int nextInt() throws IOException {
        in.nextToken();
        return (int)in.nval;
    }
    static String next() throws IOException {
        in.nextToken();
        return in.sval;
    }

    static ArrayDeque<Integer> queue = new ArrayDeque<>();
    public static void main(String[] args) throws IOException {
        int N = nextInt(), M = nextInt();
        int[][] hi = new int[N+2][M+2];
        int[] le = new int[M+2], ri = new int[M+2];
        for (int i = 0; i <= N; i++) hi[i][0] = hi[i][M+1] = -1;  // 将两侧初始化为-1
        for (int i = 1; i <= N; i++) {  // 读入数据
            for (int j = 1; j <= M; j++) {
                hi[i][j] = next().equals("R") ? 0 : hi[i-1][j] + 1;
            }
        }
        int res = 0;
        for (int i = 1; i <= N; i++) {  // 按行遍历
            res = Math.max(res, helper(hi[i], le, ri, M));
        }
        out.println(res*3); out.close();
    }

    static int helper(int[] hi, int[] le, int[] ri, int M) {
        queue.clear(); queue.push(0);
        for (int i = 1; i <= M; i++) {
            while (hi[queue.peek()] >= hi[i]) queue.pop();  // 最小单调栈
            le[i] = queue.peek();
            queue.push(i);
        }
        queue.clear(); queue.push(M+1);
        for (int i = M; i > 0; i--) {
            while (hi[queue.peek()] >= hi[i]) queue.pop();  // 最小单调栈
            ri[i] = queue.peek();
            queue.push(i);
        }
        int res = 0;
        for (int i = 1; i <= M; i++) {
            res = Math.max(res, hi[i] * (ri[i]-le[i]-1));
        }
        return res;
    }
}
```

