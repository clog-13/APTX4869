# 169. 数独2

请你将一个 16×16 的数独填写完整，使得每行、每列、每个 4×4 十六宫格内字母 A∼P 均恰好出现一次。

保证每个输入只有唯一解决方案。

![数独2.jpg](https://www.acwing.com/media/article/image/2019/01/16/19_cabce58018-%E6%95%B0%E7%8B%AC2.jpg)

#### 输入格式

输入包含多组测试用例。

每组测试用例包括 16 行，每行一组字符串，共 16 个字符串。

第 i 个字符串表示数独的第 i 行。

字符串包含字符可能为字母 A∼P 或 `-`（表示等待填充）。

测试用例之间用单个空行分隔，输入至文件结尾处终止。

#### 输出格式

对于每个测试用例，均要求保持与输入相同的格式，将填充完成后的数独输出。

每个测试用例输出结束后，输出一个空行。

#### 输入样例：

```
--A----C-----O-I
-J--A-B-P-CGF-H-
--D--F-I-E----P-
-G-EL-H----M-J--
----E----C--G---
-I--K-GA-B---E-J
D-GP--J-F----A--
-E---C-B--DP--O-
E--F-M--D--L-K-A
-C--------O-I-L-
H-P-C--F-A--B---
---G-OD---J----H
K---J----H-A-P-L
--B--P--E--K--A-
-H--B--K--FI-C--
--F---C--D--H-N-
```

#### 输出样例：

```
FPAHMJECNLBDKOGI
OJMIANBDPKCGFLHE
LNDKGFOIJEAHMBPC
BGCELKHPOFIMAJDN
MFHBELPOACKJGNID
CILNKDGAHBMOPEFJ
DOGPIHJMFNLECAKB
JEKAFCNBGIDPLHOM
EBOFPMIJDGHLNKCA
NCJDHBAEKMOFIGLP
HMPLCGKFIAENBDJO
AKIGNODLBPJCEFMH
KDEMJIFNCHGAOPBL
GLBCDPMHEONKJIAF
PHNOBALKMJFIDCEG
IAFJOECGLDPBHMNK
```



## DFS+剪枝

```java
import java.util.Scanner;

class Main {
    int N = 16;
    int[] ones = new int[1<<N], num_log = new int[1<<N];
    int[][] state = new int[N][N];
    int[][][] backup_state = new int[N*N+1][N][N], backup_stateTmp = new int[N*N+1][N][N];
    char[][] arr = new char[N][N];
    char[][][] backup_arr = new char[N*N+1][N][N];

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        for (int i = 0; i < N; i++) num_log[1<<i] = i;
        for (int i = 0; i < (1<<N); i++) {
            for (int j = i; j > 0; j -= lowbit(j)) ones[i]++;
        }

        Scanner sc = new Scanner(System.in);
        next: while (sc.hasNext()) {
            for (int i = 0; i < N; i++) {
                String str = sc.nextLine().trim();
                if (str.length() == 0) continue next;
                arr[i] = str.toCharArray();
            }

            init();
            int cnt = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (arr[i][j] != '-') draw(i, j, arr[i][j]-'A');
                    else cnt++;
                }
            }

            dfs(cnt);

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) System.out.print(arr[i][j]);
                System.out.println();
            }
            System.out.println();
        }
    }

    private boolean dfs(int cnt) {
        if (cnt == 0) return true;

        int bknt = cnt;
        copy(state, backup_state[bknt]);
        copy(arr, backup_arr[bknt]);

        // case 1:对于每个空格,如果不能填任何一个字母,则返回;如果只能填一个字母,那么直接填上
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (arr[i][j] == '-') {
                    if (state[i][j] == 0) {  // 如果不能填任何一个字母，则返回false
                        copy(backup_state[bknt], state);
                        copy(backup_arr[bknt], arr);
                        return false;
                    }
                    if (ones[state[i][j]] == 1) {  // 如果只能填一个字母，那么直接填上
                        draw(i, j, num_log[state[i][j]]);
                        cnt--;
                    }
                }
            }
        }

        // case 2:对于每一行,如果某个字母不能出现在任何一个位置,则返回;如果某个字母只有一个位置可以填,则直接填上
        for (int i = 0; i < N; i++) {  // 枚举所有行, state[i][j]:第i行 j列的格子状态
            int sor = 0, sand = (1<<N)-1, drawn = 0;
            for (int j = 0; j < N; j++) {  // 枚举当前行所有格子
                sand &= ~(sor & state[i][j]);  // sand用来找某个字母只有当前位置可以填(其他位置都不能填)
                sor |= state[i][j];  // sor存的是这一行里每个格子备选方案的并集
                if (arr[i][j] != '-') drawn |= state[i][j];  // 当前行中已经填了的
            }

            if (sor != (1<<N)-1) {  // 当前行有字母不能填（最后填不完）
                copy(backup_state[bknt], state);
                copy(backup_arr[bknt], arr);
                return false;
            }

            for (int tmp = sand; tmp > 0; tmp -= lowbit(tmp)) {
                int t = lowbit(tmp);
                if ((drawn & t) > 0) continue;
                for (int k = 0; k < N; k++) {
                    if ((state[i][k] & t) != 0) {
                        draw(i, k, num_log[t]);
                        cnt--;
                    }
                }
            }
        }

        // case 3: 同上, i,j互换
        for (int i = 0; i < N; i++) {
            int sor = 0, sand = (1<<N)-1, drawn = 0;
            for (int j = 0; j < N; j++) {
                sand &= ~(sor & state[j][i]);
                sor |= state[j][i];
                if (arr[j][i] != '-') drawn |= state[j][i];
            }

            if (sor != (1<<N)-1) {
                copy(backup_state[bknt], state);
                copy(backup_arr[bknt], arr);
                return false;
            }

            for (int j = sand; j > 0; j -= lowbit(j)) {
                int t = lowbit(j);
                if ((drawn & t) == 0) {
                    for (int k = 0; k < N; k++) {
                        if ((state[k][i] & t) != 0) {
                            draw(k, i, num_log[t]);
                            cnt--;
                        }
                    }
                }
            }
        }

        // case 4:同上,对于格子内
        for (int i = 0; i < N; i++) {
            int sor = 0, sand = (1<<N)-1, drawn = 0;
            for (int j = 0; j < N; j++) {
                int sx = i/4*4, sy = i%4*4;
                int dx = j/4,   dy = j%4;
                int s = state[sx+dx][sy+dy];
                sand &= ~(sor & s);
                sor |= s;
                if (arr[sx+dx][sy+dy] != '-') drawn |= s;
            }

            if (sor != (1<<N)-1) {
                copy(backup_state[bknt], state);
                copy(backup_arr[bknt], arr);
                return false;
            }

            for (int j = sand; j > 0; j -= lowbit(j)) {
                int t = lowbit(j);
                if ((drawn & t) == 0) {
                    for (int k = 0; k < N; k++) {
                        int sx = i/4*4, sy = i%4*4;
                        int dx = k/4,   dy = k%4;
                        if ((state[sx+dx][sy+dy] & t) != 0) {
                            draw(sx+dx, sy+dy, num_log[t]);
                            cnt--;
                        }
                    }
                }
            }
        }

        // case 5:已经填完,提前返回
        if (cnt == 0) return true;

        // case 6:选择备选方案最少的
        int x = -1, y = -1, minOpts = 20;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (arr[i][j] == '-' && ones[state[i][j]] < minOpts) {
                    minOpts = ones[state[i][j]];
                    x = i; y = j;
                }
            }
        }

        copy(state, backup_stateTmp[bknt]);
        for (int i = state[x][y]; i > 0; i -= lowbit(i)) {
            copy(backup_stateTmp[bknt], state);
            draw(x, y, num_log[lowbit(i)]);
            if (dfs(cnt-1)) return true;
        }

        copy(backup_state[bknt], state);
        copy(backup_arr[bknt], arr);
        return false;
    }

    private void draw(int x, int y, int c) {
        arr[x][y] = (char) (c+'A');
        for (int i = 0; i < N; i++) {
            state[x][i] &= ~(1 << c);
            state[i][y] &= ~(1 << c);
        }

        int sx = x/4*4, sy = y/4*4;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                state[sx+i][sy+j] &= ~(1 << c);
            }
        }

        state[x][y] = 1<<c;
    }

    private void init() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                state[i][j] = (1 << N) - 1;
            }
        }
    }

    private int lowbit(int n) {
        return n & (-n);
    }

    private void copy(int[][] src, int[][] backup) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, backup[i], 0, src[0].length);
        }
    }
    private void copy(char[][] src, char[][] backup) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, backup[i], 0, src[0].length);
        }
    }
}

```

