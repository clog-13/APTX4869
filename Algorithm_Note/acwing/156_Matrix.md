# 156. 矩阵

给定一个 M 行 N 列的 01 矩阵（只包含数字 0 或 1 的矩阵），再执行 Q 次询问，每次询问给出一个 A 行 B 列的 01 矩阵，求该矩阵是否在原矩阵中出现过。

#### 输入格式

第一行四个整数 M,N,A,B。

接下来一个 M 行 N 列的 01 矩阵，数字之间没有空格。

接下来一个整数 Q。

接下来 Q 个 A 行 B 列的 01 矩阵，数字之间没有空格。

#### 输出格式

对于每个询问，输出 1 表示出现过，0 表示没有出现过。

#### 数据范围

A≤100，M,N,B≤1000，Q≤1000

#### 输入样例：

```
3 3 2 2
111
000
111
3
11
00
11
11
00
11
```

#### 输出样例：

```
1
0
1
```



## hash

```java
import java.io.*;
import java.util.*;

public class Main {
    int N, M, A, B, maxN = 1010, P = 131;
    long[][] hash = new long[maxN][maxN];
    long[] power = new long[maxN*maxN];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]); M = Integer.parseInt(str[1]);
        A = Integer.parseInt(str[2]); B = Integer.parseInt(str[3]);

        power[0] = 1;
        for (int i = 1; i <= N*M; i++) power[i] = power[i-1]*P;
        for (int i = 1; i <= N; i++) {  // 读入数据
            char[] arr = br.readLine().trim().toCharArray();  // 计算每行的hash值
            for (int j = 0; j < M; j++) hash[i][j+1] = hash[i][j]*P + arr[j]-'0';
        }

        Set<Long> set = new HashSet<>();
        for (int ri = B; ri <= M; ri++) {  // 列
            long sum = 0;
            int le = ri - B + 1;
            for (int j = 1; j <= N; j++) {  // 行
                sum = sum * power[B] + calc(hash[j], le, ri);  // 用每行hash值累加生成矩阵hash值
                if (j >= A) {
                    sum -= calc(hash[j-A], le, ri) * power[A*B];  // !!! A * B
                    set.add(sum);  // 二维前缀hash值 加入set
                }
            }
        }

        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            long sum = 0;
            for (int i = 0; i < A; i++) {  // 生成 查询矩阵的hash
                char[] arr = br.readLine().trim().toCharArray();
                for (int j = 0; j < B; j++) sum = sum*P + arr[j]-'0';
            }
            if (set.contains(sum)) bw.write("1\n");
            else bw.write("0\n");
        }
        bw.close();
    }

    long calc(long[] h, int le, int ri) {
        return h[ri] - h[le-1] * power[ri-le+1];
    }
}
```

