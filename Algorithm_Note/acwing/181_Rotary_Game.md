# 181. 回转游戏

如下图所示，有一个 `#` 形的棋盘，上面有 1,2,3 三种数字各 8 个。

给定 8 种操作，分别为图中的 A∼H。

这些操作会按照图中字母和箭头所指明的方向，把一条长为 7 的序列循环移动 1 个单位。

例如下图最左边的 `#` 形棋盘执行操作 A 后，会变为下图中间的 `#` 形棋盘，再执行操作 C 后会变成下图最右边的 `#` 形棋盘。

给定一个初始状态，请使用最少的操作次数，使 `#` 形棋盘最中间的 8 个格子里的数字相同。

![2286_1.jpg](https://www.acwing.com/media/article/image/2019/01/23/19_4ec33e321e-2286_1.jpg)

#### 输入格式

输入包含多组测试用例。

每个测试用例占一行，包含 24 个数字，表示将初始棋盘中的每一个位置的数字，按整体从上到下，同行从左到右的顺序依次列出。

输入样例中的第一个测试用例，对应上图最左边棋盘的初始状态。

当输入只包含一个 0 的行时，表示输入终止。

#### 输出格式

每个测试用例输出占两行。

第一行包含所有移动步骤，每步移动用大写字母 A∼H 中的一个表示，字母之间没有空格，如果不需要移动则输出 `No moves needed`。

第二行包含一个整数，表示移动完成后，中间 8 个格子里的数字。

如果有多种方案，则输出字典序最小的解决方案。

#### 输入样例：

```
1 1 1 1 3 2 3 2 3 1 3 2 2 3 1 2 2 2 3 1 2 1 3 3
1 1 1 1 1 1 1 1 2 2 2 2 2 2 2 2 3 3 3 3 3 3 3 3
0
```

#### 输出样例：

```
AC
2
DDHH
2
```



## IDA*

```java
import java.io.*;
import java.util.*;

/**
 *        0,   1,
 *        2,   3,
 *  4, 5, 6, 7,8, 9,10
 *       11,  ,12,
 * 13,14,15,16,17,18,19
 *       20,  ,21,
 *       22,  ,23,
 */

public class Main {
    final int N = 24;
    int[] arr = new int[N], res = new int[N*N];
    int[][] op = {
            { 0, 2, 6,11,15,20,22},
            { 1, 3, 8,12,17,21,23},
            {10, 9, 8, 7, 6, 5, 4},
            {19,18,17,16,15,14,13},
            {23,21,17,12, 8, 3, 1},
            {22,20,15,11, 6, 2, 0},
            {13,14,15,16,17,18,19},
            { 4, 5, 6, 7, 8, 9,10}
    };
    int[] backOp = { 5, 4, 7, 6, 1, 0, 3, 2};
    int[] center = { 6, 7, 8,11,12,15,16,17};

    public static void main(String[] args) throws IOException {
        new Main().init();
    }

    private void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String[] str = br.readLine().split(" ");
            if (str.length != 24) return;
            for (int i = 0; i < N; i++) arr[i] = Integer.parseInt(str[i]);

            int depth = 0;
            while (!dfs(0, depth, -1)) depth++;

            if (depth == 0) System.out.print("n moves needed");
            else {
                for (int i = 0; i < depth; i++) {
                    System.out.print((char)(res[i]+'A'));
                }

            }
            System.out.println("\n"+arr[center[0]]);
        }
    }

    private boolean dfs(int u, int maxDepth, int pre) {
        if (check()) return true;
        if (u + f() > maxDepth) return false;  // 可行性剪枝


        for (int i = 0; i < 8; i++) {
            if (backOp[i] == pre) continue;
            drag(i);
            res[u] = i;
            if (dfs(u+1, maxDepth, i)) return true;
            drag(backOp[i]);
        }
        return false;
    }

    private int f() {
        int[] cout = new int[4];
        for (int c : center) cout[arr[c]]++;
        int maxNum = 0;
        for (int c : cout) maxNum = Math.max(maxNum, c);
        return 8-maxNum;
    }

    private void drag(int n) {
        int tmp = arr[op[n][0]];
        for (int i = 1; i < 7; i++) {
            arr[op[n][i-1]] = arr[op[n][i]];
        }
        arr[op[n][6]] = tmp;
    }

    private boolean check() {
        for (int i = 1; i < center.length; i++) {
            if (arr[center[i]] != arr[center[i-1]]) return false;
        }
        return true;
    }
}
```

