# 286. 选课

学校实行学分制。

每门的必修课都有固定的学分，同时还必须获得相应的选修课程学分。

学校开设了 N 门的选修课程，每个学生可选课程的数量 M 是给定的。

学生选修了这 M 门课并考核通过就能获得相应的学分。

在选修课程中，有些课程可以直接选修，有些课程需要一定的基础知识，必须在选了其他的一些课程的基础上才能选修。

例如《Windows程序设计》必须在选修了《Windows操作基础》之后才能选修。

我们称《Windows操作基础》是《Windows程序设计》的先修课。

每门课的直接先修课最多只有一门。

两门课可能存在相同的先修课。

你的任务是为自己确定一个选课方案，使得你能得到的学分最多，并且必须满足先修条件。

假定课程之间不存在时间上的冲突。

#### 输入格式

输入文件的第一行包括两个整数 N、M（中间用一个空格隔开）其中 1≤N≤300,1≤M≤N。

接下来 N 行每行代表一门课，课号依次为 1，2，…，N。

每行有两个数（用一个空格隔开），第一个数为这门课先修课的课号（若不存在先修课则该项为 0），第二个数为这门课的学分。

学分是不超过 10 的正整数。

#### 输出格式

输出一个整数，表示学分总数。

#### 输入样例：

```
7 4
2 2
0 1
0 4
2 1
7 1
7 6
2 2
```

#### 输出样例：

```
13
```



## DP

```java
import java.io.*;
import java.util.*;

public class Main {
    int N, M, idx, maxN = 310;
    int[] info = new int[maxN], from = new int[maxN], to = new int[maxN];
    int[] arr = new int[maxN];
    int[][] f = new int[maxN][maxN];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); M = sc.nextInt();

        Arrays.fill(info, -1);
        for (int i = 1; i <= N; i++) {
            int fa = sc.nextInt(); arr[i] = sc.nextInt();
            add(fa, i);
        }

        dfs(0);
        System.out.println(f[0][M+1]);
    }

    void dfs(int u) {
        for (int i = info[u]; i != -1; i = from[i]) {
            int t = to[i];
            dfs(t);
            for (int j = M; j >= 0; j--) {  // 循环体积
                for (int k = j; k >= 1; k--) {  // 循环体积选择方案
                    f[u][j] = Math.max(f[u][j], f[u][j-k] + f[t][k]);
                }
            }
        }
        for (int i = M+1; i > 0; i--) {  // 叶子节点会直接运行这里(注意循环倒序)
            f[u][i] = f[u][i-1] + arr[u];  // f[u][i]:u为根的子树中 选了i门(不包含第i门)的 最高分
        }
    }

    void add(int a, int b) {
        from[idx] = info[a];
        to[idx] = b;
        info[a] = idx++;
    }
}
```

