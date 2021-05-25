# 315. 旅行

爱丽丝和鲍勃想去旅行。

他们每个人制定了一条旅行路线，每条路线包含一个按给定顺序访问的城市列表，一个城市可能会多次出现在同一路线中。

因为他们想要一起去旅行，所以必须在旅行路线上达成一致。

他们两个都不想改变他们的路线上的城市顺序或者在路线上额外添加城市。

因此，他们只能移除各自路线中的一些城市，使得旅行路线达成一致，并且尽可能的长。

该地区共有 26 个城市，用小写字母 `a` 到 `z` 表示。

#### 输入格式

输入包含两行，第一行是爱丽丝的路线城市列表，第二行是鲍勃的路线城市列表。

每个列表由 1 到 80 个小写字母组成，其间没有空格。

#### 输出格式

按升序顺序输出所有满足条件的路线列表。

每个路线列表占一行。

#### 输入样例：

```
abcabcaa
acbacba
```

#### 输出样例：

```
ababa
abaca
abcba
acaba
acaca
acbaa
acbca
```



## DP + DFS

```java
import java.util.*;

public class Main {
    int maxN = 110;
    int[][] f = new int[maxN][maxN];
    char[] c1, c2, path = new char[maxN];

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        String s1 = sc.nextLine(), s2 = sc.nextLine();
        c1 = (" "+s1).toCharArray(); c2 = (" "+s2).toCharArray();
        for (int i = s1.length(); i > 0; i--) {
            for (int j = s2.length(); j > 0; j--) {
                if (c1[i] == c2[j]) {
                    f[i][j] = f[i+1][j+1] + 1;
                } else {
                    f[i][j] = Math.max(f[i+1][j], f[i][j+1]);
                }
            }
        }
        dfs(1, 1, 0, f[1][1]);
    }

    void dfs(int i, int j, int u, int len) {
        if (u >= len) {
            for (int p = 0; p < u; p++) System.out.print(path[p]);
            System.out.println();
            return;
        }

        if (c1[i] == c2[j]) {
            path[u] = c1[i];
            dfs(i + 1, j + 1, u + 1, len);
        } else {
            for (int k = 0; k < 26; k ++ ) {  // 枚举可能的下一个共同字母
                int x = 0, y = 0; // 在s1, s2中，下一个共同字母k出现在哪里
                for (int tx = i; tx < c1.length; tx++) {  // 从i开始
                    if (c1[tx] == (char)('a'+k)) {
                        x = tx;
                        break;
                    }
                }
                for (int ty = j; ty < c2.length; ty++) {  // 从j开始
                    if (c2[ty] == (char)('a'+k)) {
                        y = ty;
                        break;
                    }
                }

                if (x>0 && y>0 && f[x][y] == f[i][j]) {
                    dfs(x, y, u, len);
                }
            }
        }
    }
}
```



