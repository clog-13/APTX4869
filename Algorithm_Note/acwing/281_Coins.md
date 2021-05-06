# 281. 硬币

给定 N 种硬币，其中第 i 种硬币的面值为 Ai，共有 Ci 个。

从中选出若干个硬币，把面值相加，若结果为 S，则称“面值 S 能被拼成”。

求 1∼M 之间能被拼成的面值有多少个。

#### 输入格式

输入包含多组测试用例。

每组测试用例第一行包含两个整数 N 和 M。

第二行包含 2N 个整数，分别表示 A1,A2,…,AN 和 C1,C2,…,CN。

当输入用例 N=0，M=0 时，表示输入终止，且该用例无需处理。

#### 输出格式

每组用例输出一个结果，每个结果占一行。

#### 数据范围

1≤N≤100, 1≤M≤105 1≤Ai≤1051≤Ai≤105, 1≤Ci≤1000

#### 输入用例：

```
3 10
1 2 4 2 1 1
2 5
1 4 2 1
0 0
```

#### 输出用例：

```
8
4
```



## DP（背包）

```java
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int[] size = new int[110], cout = new int[110];
        int[] f = new int[100010], g = new int[100010];
        while (true) {
            String[] str = br.readLine().split(" ");
            int N = Integer.parseInt(str[0]), M = Integer.parseInt(str[1]);
            if (N==0 && M==0) break;
            str = br.readLine().split(" ");

            for (int i = 0; i < str.length/2; i++) {
                size[i+1] = Integer.parseInt(str[i]);
                cout[i+1] = Integer.parseInt(str[str.length/2 + i]);
            }

            Arrays.fill(f, 0);
            f[0] = 1;  // f[i]=1: i可以被凑出来
            for (int i = 1; i <= N; i++) {  // 每种硬币
                Arrays.fill(g, 0);  // g[j]:当前硬币 在j体积时的 硬币数
                for (int j = size[i]; j <= M; j++) {  // 遍历每种体积
                    if (f[j]==0 && f[j-size[i]]>0 && g[j-size[i]] < cout[i]) {
                        g[j] = g[j-size[i]] + 1;
                        f[j] = 1;
                    }
                }
            }
            int res = 0;
            for (int i = 1; i <= M; i++) res += f[i];
            System.out.println(res);
        }

        bw.flush(); bw.close();
    }
}
```

