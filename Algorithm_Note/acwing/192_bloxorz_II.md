# 192. 立体推箱子2

达达发明了一种立体推箱子游戏。

他发明的游戏里并没有那么多的规则和限制，在他的设定里游戏具有无限的平面空间，并且所有的区域都属于硬地。

终点永远都位于坐标 (0,0) 处的情况下，请你求出从起点到终点所需的最少移动次数是多少。

#### 输入格式

输入包含多组测试用例。

每组测试数据在一行内，格式为 `C x y`，其中 C 为一个字母，x 和 y 是两个整数。

这表示长方体覆盖住了平台上的格子 (x,y)，且其状态为 C。

若 C 为字母 U，表明长方体是竖立的。

若 C 为字母 V，表明长方体与 x 轴平行，且其覆盖的另一个格子为 (x+1,y)。

若 C 为字母 H，表明长方体与 y 轴平行，且其覆盖的另一个格子为 (x,y+1)。

#### 输出格式

对于每个测试用例，输出一个占一行的整数，表示所需的最少移动次数。

#### 数据范围

0≤x,y≤1000000000

#### 输入样例：

```
U 0 0
H 0 0
V 1 0
```

#### 输出样例：

```
0
4
1
```



## 模拟

仔细观察，我们会发现，要达到终点，必须得经过(x,y,0)且x%3=0, y%3=0的点，我们就称这样的点为模三点吧。

所以问题得转化成我们得从当前状态点转移到一个模三点，其次我们得考虑，要从一个模三点如何转移到相邻的一个模三点并且花费的代价最小，显然最小距离最少是2(这里可以画图试一试),归纳一下，从一个模三点转移到任意一个模三点最少代价，其实就是哈密顿距离/3*2，所以问题就转化为了，从当前位置找与他相邻的模三点的代价加上从这个模三点转移到原点的最小代价。

我们只需要一个包含当前方格点在内的6^2的矩阵，就可以把(1e9)^2的矩阵缩小到6^2,这样直接BFS，判断当前点是不是模三点，更新最小值即可

```java
import java.util.*;

public class Main {
    int INF = 2*0x3f3f3f3f;  // 坑
    int[][][] dist = new int[7][7][3];
    int[][][] dir = {  // 下右上左 (0:立 1:横 2:竖)
            {{-2, 0, 2}, {0, 1, 1}, {1, 0, 2}, {0, -2, 1}},
            {{-1, 0, 1}, {0, 2, 0}, {1, 0, 1}, {0, -1, 0}},
            {{-1, 0, 0}, {0, 1, 2}, {2, 0, 0}, {0, -1, 2}}
    };

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String[] str = sc.nextLine().split(" ");
            int st;
            int x = Integer.parseInt(str[1]), y = Integer.parseInt(str[2]);
            if (str[0].equals("U")) st = 0;  // 立
            else if (str[0].equals("H")) st = 1;  // 横
            else st = 2;  // 竖

            Node start = new Node(x%3+3, y%3+3, st);
            x -= x%3; y -= y%3;
            System.out.println(bfs(start, x, y));
        }
    }

    int bfs(Node start, int pastX, int pastY) {
        int res = INF;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(start);
        for (int[][] dd : dist) for (int[] d : dd) Arrays.fill(d, INF);
        dist[start.x][start.y][start.st] = 0;

        while(!queue.isEmpty()) {
            Node cur = queue.poll();

            if (cur.x%3==0 && cur.y%3==0 && cur.st==0) {  // 模三点的最小移动代价就是哈密顿距离/3*2
                int dx = ((cur.x-3)+pastX)/3*2, dy = ((cur.y-3)+pastY)/3*2;
                if (dx<0 || dy<0) continue;
                res = Math.min(res, dist[cur.x][cur.y][0] + dx + dy);
            }
            for (int i = 0; i < 4; i++) {
                int tx = cur.x + dir[cur.st][i][0];
                int ty = cur.y + dir[cur.st][i][1];
                int tt = dir[cur.st][i][2];

                if (outRange(tx, ty)) continue;
                if (tt==1 && outRange(tx, ty+1)) continue;  // 横
                if (tt==2 && outRange(tx+1, ty)) continue;  // 竖

                if (dist[tx][ty][tt] == INF) {  // 未被访问的点
                    dist[tx][ty][tt] = dist[cur.x][cur.y][cur.st]+1;
                    queue.offer(new Node(tx, ty, tt));
                }
            }
        }

        return res;
    }

    boolean outRange(int a, int b) {
        return a < 0 || a > 6 || b < 0 || b > 6;
    }

    static class Node {
        int x, y, st;
        public Node(int xx, int yy, int s) {
            x = xx; y = yy; st = s;
        }
    }
}
```

