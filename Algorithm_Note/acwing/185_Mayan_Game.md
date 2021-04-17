# 185. 玛雅游戏

玛雅难题是最近流行起来的一个游戏。

游戏界面是一个 7 行 5 列的棋盘，上面堆放着一些方块，方块不能悬空堆放，即方块必须放在最下面一行，或者放在其他方块之上。

游戏通关是指在规定的步数内消除所有的方块，消除方块的规则如下：

1、每步移动可以且仅可以沿横向（即向左或向右）拖动某一方块一格：当拖动这一方块时，如果拖动后到达的位置（以下称目标位置）也有方块，那么这两个方块将交换位置（参见输入输出样例说明中的图6到图7）；如果目标位置上没有方块，那么被拖动的方块将从原来的竖列中抽出，并从目标位置上掉落（直到不悬空，参见下面图1 和图2 ）；

![游戏1.png.jpg](https://www.acwing.com/media/article/image/2019/01/17/19_6ed6abe419-%E6%B8%B8%E6%88%8F1.png.jpg)

2、任一时刻，如果在一横行或者竖列上有连续三个或者三个以上相同颜色的方块，则它们将立即被消除（参见图1 到图3）。
注意：
a) 如果同时有多组方块满足消除条件，几组方块会同时被消除（例如下面图4 ，三个颜色为1 的方块和三个颜色为 2 的方块会同时被消除，最后剩下一个颜色为 2 的方块）。
b) 当出现行和列都满足消除条件且行列共享某个方块时，行和列上满足消除条件的所有方块会被同时消除（例如下面图5 所示的情形，5 个方块会同时被消除）。

![游戏2.png](https://www.acwing.com/media/article/image/2019/01/17/19_aca31f3e19-%E6%B8%B8%E6%88%8F2.png)

3、方块消除之后，消除位置之上的方块将掉落，掉落后可能会引起新的方块消除。注意：掉落的过程中将不会有方块的消除。

上面图1 到图 3 给出了在棋盘上移动一块方块之后棋盘的变化。

棋盘的左下角方块的坐标为（0, 0），将位于（3, 3）的方块向左移动之后，游戏界面从图 1 变成图 2 所示的状态，此时在一竖列上有连续三块颜色为 4 的方块，满足消除条件，消除连续3 块颜色为4 的方块后，上方的颜色为3 的方块掉落，形成图 3 所示的局面。

#### 输入格式

第一行为一个正整数n ，表示要求游戏通关的步数（这里指的是恰好n步通关）。

接下来的 5 行，描述 7*5 的游戏界面。

每行若干个整数，每两个整数之间用一个空格隔开，每行以一个0 结束，自下向上表示每竖列方块的颜色编号（颜色不多于10种，从1 开始顺序编号，相同数字表示相同颜色）。

输入数据保证初始棋盘中没有可以消除的方块。

#### 输出格式

如果有解决方案，输出 n 行，每行包含 3 个整数x，y，g ，表示一次移动，每两个整数之间用一个空格隔开，其中（x ，y）表示要移动的方块的坐标，g 表示移动的方向，1 表示向右移动，-1表示向左移动。

注意：多组解时，按照 x 为第一关健字，y 为第二关健字，1优先于-1 ，给出一组字典序最小的解。

游戏界面左下角的坐标为（0 ，0）。

如果没有解决方案，输出一行，包含一个整数-1。

#### 数据范围

对于30% 的数据，初始棋盘上的方块都在棋盘的最下面一行； 对于100%的数据，0<n≤5 。

#### 输入样例：

```
3 
1 0 
2 1 0 
2 3 4 0 
3 1 0 
2 4 3 4 0 
```

#### 输出样例：

```
2 1 1 
3 1 1 
3 0 1
```



## DFS + 剪枝

```java
import java.io.*;
import java.util.*;

class Main {
    int N;
    int[][] arr = new int[5][7]; int[][][] backup_arr =  new int[5][5][7];
    int[] cnt = new int[11]; int[][] backup_cnt = new int[5][11];
    Node[] res = new Node[5];
    boolean[][] st = new boolean[5][7];

    public static void main(String[] args) throws IOException {
        new Main().init();
    }

    private void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine().trim());
        for (int i = 0; i < 5; i++) {
            String[] str = br.readLine().trim().split(" ");
            for (int j = 0; j < str.length-1; j++) {
                arr[i][j] = Integer.parseInt(str[j]);
                cnt[arr[i][j]]++; cnt[0]++;
            }
        }

        if (dfs(0)) {
            for (int i = 0; i < N; i++) bw.write(res[i].x+" "+res[i].y+" "+res[i].p+"\n");
        } else {
            bw.write("-1\n");
        }

        bw.flush(); bw.close();
    }

    private boolean dfs(int u) {
        if (u == N) return cnt[0] == 0;
        for (int i = 1; i <= 10; i++) if (cnt[i]==1 || cnt[i]==2) return false;

        copy(arr, backup_arr[u]);
        System.arraycopy(cnt, 0, backup_cnt[u], 0, cnt.length);
        
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 7; y++) {  // 遍历每个点的所有左移右移情况
                if (arr[x][y] > 0) {
                    int tx = x+1;
                    if (tx < 5) {
                        res[u] = new Node(x, y, 1);
                        move(x, y, tx);
                        if (dfs(u+1)) return true;
                        copy(backup_arr[u], arr);
                        System.arraycopy(backup_cnt[u], 0, cnt, 0,  cnt.length);
                    }
                    tx = x-1;
                    if (tx >= 0 && arr[tx][y] == 0) {  // 左移：只有前一位是 0 才计算（没必要重复计算右移的情况）
                        res[u] = new Node(x, y, -1);
                        move(x, y, tx);
                        if (dfs(u+1)) return true;
                        copy(backup_arr[u], arr);
                        System.arraycopy(backup_cnt[u], 0, cnt, 0,  cnt.length);
                    }
                }
            }
        }
        return false;
    }

    private void move(int a, int b, int tx) {
        int tmp = arr[a][b]; arr[a][b] = arr[tx][b]; arr[tx][b] = tmp;

        while (true) {
            boolean flag = false;
            for (int x = 0; x < 5; x++) {   // 讓方塊自由落體
                int z = 0;
                for (int y = 0; y < 7; y++)
                    if (arr[x][y] != 0) arr[x][z++] = arr[x][y];
                while (z < 7) arr[x][z++] = 0;
            }

            for (boolean[] s : st) Arrays.fill(s, false);
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 7; y++) {
                    if (arr[x][y] != 0) {
                        int le = x, ri = x;
                        while (le-1 >= 0 && arr[le-1][y] == arr[x][y]) le--;
                        while (ri+1 < 5  && arr[ri+1][y] == arr[x][y]) ri++;
                        if (ri-le+1 >= 3) { // 竖向超過三
                            flag = true;
                            st[x][y] = true;
                        } else {    // 當前點 竖向找不到再找 横向（减少计算）
                            le = y; ri = y;
                            while (le-1 >= 0 && arr[x][le-1] == arr[x][y]) le--;
                            while (ri+1 < 7  && arr[x][ri+1] == arr[x][y]) ri++;
                            if (ri-le+1 >= 3) { // 横向超過三
                                flag = true;
                                st[x][y] = true;
                            }                           
                        }
                    }
                }
            }

            if (flag) {
                for (int x = 0; x < 5; x++) {
                    for (int y = 0; y < 7; y++) {
                        if (st[x][y]) {
                            cnt[0]--;
                            cnt[arr[x][y]]--;
                            arr[x][y] = 0;
                        }
                    }
                }
            } else break;
        }
    }

    private void copy(int[][] src, int[][] des) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, des[i], 0, src[i].length);
        }
    }
    
    private static class Node {
        int x, y, p;
        public Node (int xx, int yy, int pp) {
            x = xx; y = yy; p = pp;
        }
    }
}
```

