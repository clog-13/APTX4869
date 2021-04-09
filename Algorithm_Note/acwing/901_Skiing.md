# 901. 滑雪

给定一个 R 行 C 列的矩阵，表示一个矩形网格滑雪场。

矩阵中第 i 行第 j 列的点表示滑雪场的第 i 行第 j 列区域的高度。

一个人从滑雪场中的**某个区域内出发**，每次可以向上下左右任意一个方向滑动一个单位距离。

当然，一个人能够滑动到某相邻区域的前提是该区域的高度低于自己目前所在区域的高度。

下面给出一个矩阵作为例子：

```
 1  2  3  4 5

16 17 18 19 6

15 24 25 20 7

14 23 22 21 8

13 12 11 10 9
```

在给定矩阵中，一条可行的滑行轨迹为 24−17−2−1。

在给定矩阵中，最长的滑行轨迹为 25−24−23−…−3−2−1，沿途共经过 25 个区域。

现在给定你一个二维矩阵表示滑雪场各区域的高度，请你找出在该滑雪场中能够完成的最长滑雪轨迹，并输出其长度(可经过最大区域数)。

#### 输入格式

第一行包含两个整数 R 和 C。

接下来 R 行，每行包含 C 个整数，表示完整的二维矩阵。

#### 输出格式

输出一个整数，表示可完成的最长滑雪长度。

#### 数据范围

1≤R,C≤300, 0≤矩阵中整数≤10000

#### 输入样例：

```
5 5
1 2 3 4 5
16 17 18 19 6
15 24 25 20 7
14 23 22 21 8
13 12 11 10 9
```

#### 输出样例：

```
25
```



## 记忆化搜索

```java
import java.util.*;

public class Main {
    int n,m,N = 310;
    int[][] arr = new int[N][N], f = new int[N][N];
    int[] dx = {0,-1,0,1}, dy = {-1,0,1,0};

    public static void main(String[] args) {
        new Main().run();
    }
    
    void run() {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt(); m = sc.nextInt();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                arr[i][j] = sc.nextInt();
            }
        }

        for (int i = 0; i < n; i++) Arrays.fill(f[i], -1);
        int res = 0; 
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res = Math.max(res, dfs(i, j));
            }
        }
        System.out.println(res);        
    }
    
    int dfs(int x,int y) {
        if (f[x][y] != -1) return f[x][y];

        f[x][y] = 1;
        for (int i = 0;i < 4;i ++) {
            int tx = x + dx[i], ty = y + dy[i];
            if (tx < 0 || tx >= n || ty < 0 || ty >= m) continue;
            if (arr[x][y] > arr[tx][ty]) {
                f[x][y] = Math.max(f[x][y], dfs(tx, ty) + 1);
            }
        }
        return f[x][y];
    }
}
```

