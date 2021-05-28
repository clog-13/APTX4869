# 174. 推箱子

推箱子游戏相信大家都不陌生，在本题中，你将控制一个人把 1 个箱子到目的地。

给定一张 N 行 M 列的地图，用字符 `.` 表示空地，字符 `#` 表示墙，字符 `S` 表示人的起始位置，字符 `B` 表示箱子的起始位置，字符 `T` 表示箱子的目标位置。

求一种移动方案，使箱子移动的次数最少，在此基础上再让人移动的总步数最少。

方案中使用大写的 `EWSN`（东西南北）表示箱子的移动，使用小写的 `ewsn`（东西南北）表示人的移动。

![推箱子.jpg](https://www.acwing.com/media/article/image/2019/01/16/19_8c8e5b0a19-%E6%8E%A8%E7%AE%B1%E5%AD%90.jpg)

#### 输入格式

输入包含多个测试用例。

对于每个测试用例，第一行包括两个整数 N，M。

接下来 N 行，每行包括 M 个字符，用以描绘整个 N 行 M 列的地图。

当样例为 N=0，M=0 时，表示输入终止，且该样例无需处理。

#### 输出格式

对于每个测试用例，第一行输出 `Maze #`+测试用例的序号。

第二行输入一个字符串，表示推箱子的总体移动过程，若无解，则输出 `Impossible.`。

每个测试用例输出结束后输出一个空行。

若有多条路线满足题目要求，则按照 `N`、`S`、`W`、`E` 的顺序优先选择箱子的移动方向（即先上下推，再左右推）。

在此前提下，再按照 `n`、`s`、`w`、`e` 的顺序优先选择人的移动方向（即先上下动，再左右动）。

#### 数据范围

1≤N,M≤20

#### 输入样例：

```
1 7
SB....T
1 7
SB..#.T
7 11
###########
#T##......#
#.#.#..####
#....B....#
#.######..#
#.....S...#
###########
8 4
....
.##.
.#..
.#..
.#.B
.##S
....
###T
0 0
```

#### 输出样例：

```
Maze #1
EEEEE

Maze #2
Impossible.

Maze #3
eennwwWWWWeeeeeesswwwwwwwnNN

Maze #4
swwwnnnnnneeesssSSS
```



## BFS

```java
import java.io.*;
import java.util.*;

class Main {
    int N, M, maxN = 25;
    char[][] graph = new char[maxN][maxN];
    PII man = null, box = null;
    Node end = new Node(0, 0, 0);

    int[][][][] man_path = new int[maxN][maxN][maxN][];
    PII[][][] dist = new PII[maxN][maxN][4];
    boolean[][][] st_box = new boolean[maxN][maxN][4];
    Node[][][] pre_box = new Node[maxN][maxN][4];
    boolean[][] st_man = new boolean[maxN][maxN];
    int[][] pre_man = new int[maxN][maxN];
    char[] op = {'n','s','w','e'};
    int[] dx = {-1, 1, 0, 0}, dy = {0, 0,-1, 1};
    int[] man_path_tmp = new int[0];

    public static void main(String[] args) throws IOException {
        new Main().init();
    }
    private void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = 1;
        while (true) {
            String[] str = br.readLine().split(" ");
            N = Integer.parseInt(str[0]); M = Integer.parseInt(str[1]);
            if (N==0 || M==0) break;
            for (int i = 0; i < N; i++) graph[i] = br.readLine().toCharArray();

            for (int i = 0; i < N; i++) {
                for (int j = 0;j < M; j++) {
                    if (graph[i][j] == 'S') man = new PII(i, j);
                    if (graph[i][j] == 'B') box = new PII(i, j);
                }
            }

            bw.write("Maze #" + T + "\n"); T++;
            if (!bfs_box(man, box)) bw.write("Impossible.\n");
            else {
                StringBuilder res = new StringBuilder();
                while (end.dir != -1) {
                    res.append((char)(op[end.dir]-32));  // 箱子路径
                    for (int i : man_path[end.x][end.y][end.dir]) res.append(op[i]);  // 人路径
                    end = pre_box[end.x][end.y][end.dir];
                }
                bw.write(res.reverse()+"\n");
            }
            bw.write("\n");
        }

        bw.close();
    }

    private boolean bfs_box(PII man, PII box) {
        for (int i = 0; i < N; i++) for (int j = 0; j < M; j++) Arrays.fill(st_box[i][j], false);

        Queue<Node> queue = new LinkedList<>();
        for (int i = 0; i < 4; i++) {  // 初始化， 枚举推箱子的第一步
            int bx = box.steps+dx[i], by = box.dis+dy[i];
            int mx = box.steps-dx[i], my = box.dis-dy[i];
            if (check(bx, by) && check(mx, my) && bfs_man(man, new PII(mx, my), box) != -1) {
                queue.add(new Node(bx, by, i));
                st_box[bx][by][i] = true;

                man_path[bx][by][i] = man_path_tmp;
                pre_box[bx][by][i] = new Node(box.steps, box.dis, -1);  // 初始化的pre_box.dir 为 -1
                dist[bx][by][i] = new PII(1, man_path_tmp.length);
            }
        }

        boolean success = false;
        PII min_dis = new PII((int)1e9, (int)1e9);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            int x = cur.x, y = cur.y, dir = cur.dir;

            if (graph[x][y] == 'T') {
                success = true;
                if (dist[x][y][dir].compareTo(min_dis) < 0) {
                    min_dis = dist[x][y][dir];
                    end.x = x; end.y = y; end.dir = dir;
                }
                continue;
            }

            for (int i = 0; i < 4; i++) {
                int bx = x+dx[i], by = y+dy[i];
                int mx = x-dx[i], my = y-dy[i];
                if (!check(bx, by) || !check(mx, my)) continue;
                int dis = bfs_man(new PII(x-dx[dir], y-dy[dir]), new PII(mx, my), new PII(x, y));
                if (dis != -1)  {
                    PII tmp = new PII(dist[x][y][dir].steps+1, dist[x][y][dir].dis+man_path_tmp.length);
                    if (!st_box[bx][by][i]) {
                        st_box[bx][by][i] = true;
                        queue.add(new Node(bx, by, i));

                        man_path[bx][by][i] = man_path_tmp;
                        pre_box[bx][by][i] = cur;
                        dist[bx][by][i] = tmp;
                    }
                }
            }
        }
        return success;
    }

    private int bfs_man(PII man, PII to, PII box) {  // man 能否到 to
        for (int i = 0; i < N; i++) Arrays.fill(st_man[i], false);
        for (int i = 0; i < N; i++) Arrays.fill(pre_man[i], -1);

        st_man[man.steps][man.dis] = true;
        st_man[box.steps][box.dis] = true;

        Queue<PII> queue = new LinkedList<>();
        queue.add(man);
        while (!queue.isEmpty()) {
            PII cur = queue.poll();
            int x = cur.steps, y = cur.dis;

            if (cur.compareTo(to) == 0) {  // 找到目标，返回
                List<Integer> list = new ArrayList<>();  // 不确定长度，先用list来储存
                while (pre_man[x][y] != -1) {
                    int dir = pre_man[x][y];
                    list.add(dir);
                    x -= dx[dir]; y -= dy[dir];  // bfs正向搜索时是 +, 这里是 -
                }

                if (list.size() == 0) man_path_tmp = new int[]{};
                else {
                    man_path_tmp = new int[list.size()];
                    for (int i = 0; i < list.size(); i++) man_path_tmp[i] = list.get(i);
                }
                return list.size();
            }

            for (int i = 0; i < 4; i++) {
                int tx = x+dx[i], ty = y+dy[i];
                if (check(tx, ty) && !st_man[tx][ty]) {
                    st_man[tx][ty] = true;
                    pre_man[tx][ty] = i;
                    queue.add(new PII(tx, ty));
                }
            }
        }

        return -1;
    }

    private boolean check(int x, int y) {
        return x>=0 && x<N && y>=0 && y<M && graph[x][y] != '#';
    }

    static class PII implements Comparable<PII> {
        int steps, dis;
        public PII(int s, int d) {
            steps = s; dis = d;
        }

        @Override
        public int compareTo(PII o) {
            return steps==o.steps ? dis-o.dis : steps-o.steps;
        }
    }

    static class Node {
        int x, y, dir;
        public Node(int xx, int yy, int d) {
            x = xx; y = yy; dir = d;
        }
    }
}
```

