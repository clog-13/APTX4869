# 95. 费解的开关

你玩过“拉灯”游戏吗？25盏灯排成一个5x5的方形。每一个灯都有一个开关，游戏者可以改变它的状态。每一步，游戏者可以改变某一个灯的状态。游戏者改变一个灯的状态会产生连锁反应：和这个灯上下左右相邻的灯也要相应地改变其状态。

我们用数字“1”表示一盏开着的灯，用数字“0”表示关着的灯。下面这种状态

```
10111
01101
10111
10000
11011
```

在改变了最左上角的灯的状态后将变成：

```
01111
11101
10111
10000
11011
```

再改变它正中间的灯后状态将变成：

```
01111
11001
11001
10100
11011
```

给定一些游戏的初始状态，编写程序判断游戏者是否可能在6步以内使所有的灯都变亮。

#### 输入格式

第一行输入正整数n，代表数据中共有n个待解决的游戏初始状态。

以下若干行数据分为n组，每组数据有5行，每行5个字符。每组数据描述了一个游戏的初始状态。各组数据间用一个空行分隔。

#### 输出格式

一共输出n行数据，每行有一个小于等于6的整数，它表示对于输入数据中对应的游戏状态最少需要几步才能使所有灯变亮。

对于某一个游戏初始状态，若6步以内无法使所有灯变亮，则输出“-1”。

#### 数据范围

0<n≤500

#### 输入样例：

```
3
00111
01011
10001
11010
11100

11101
11101
11110
11111
11111

01111
11111
11111
11111
11111
```

输出样例：

```
3
2
-1
```



## 枚举

```java
import java.io.*;

public class Main {
    char[][] arr = new char[5][5];

    public static void main(String[] args) throws IOException {
        new Main().init();
    }

    void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        while (N-- > 0) {
            for (int j = 0; j < 5; j++) arr[j] = br.readLine().toCharArray();
            System.out.println(dfs());
            br.readLine();
        }
    }

    int dfs() {
        int res = Integer.MAX_VALUE;
        for (int st = 0; st < 1<<5; st++) {     // 枚举所有初始化操作
            char[][] backups = new char[5][5];  // 然后从第一排开始对每个必须操作的地方操作，最后判断
            for (int i = 0; i < 5; i++) System.arraycopy(arr[i], 0, backups[i], 0, 5);

            int step = 0;
            for (int i = 0; i < 5; i++) {    // 翻转当前枚举的状态的情況
                if ((st>>i & 1) == 1) {
                    step++;
                    turn(0, i);
                }
            }
            for (int i = 1; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (arr[i-1][j] == '0') {
                        step++;
                        turn(i, j);
                    }
                }
            }

            boolean flag = true;  // 判断最终状态
            for (int j = 0; j < 5; j++) {
                if (arr[4][j] == '0') {
                    flag = false;
                    break;
                }
            }

            if (flag) res = Math.min(res, step);
            for (int j = 0; j < 5; j++) System.arraycopy(backups[j], 0, arr[j], 0, 5);
        }
        if (res > 6) return -1;
        return res;
    }

    void turn(int x, int y) {
        int[][] dir = {{0, 0}, {-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int i = 0; i < 5; i++) {
            int tx = x+dir[i][0], ty = y+dir[i][1];
            if (tx >= 0 && tx < 5 && ty >= 0 && ty < 5) {
                if (arr[tx][ty] == '1') arr[tx][ty] = '0';
                else arr[tx][ty] = '1';
            }
        }
    }
}
```

.