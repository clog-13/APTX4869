# 242. 一个简单的整数问题

给定长度为 N 的数列 A，然后输入 M 行操作指令。

第一类指令形如 `C l r d`，表示把数列中第 l∼r 个数都加 d。

第二类指令形如 `Q x`，表示询问数列中第 x 个数的值。

对于每个询问，输出一个整数表示答案。

#### 输入格式

第一行包含两个整数 N 和 M。

第二行包含 N 个整数 A[i]。

接下来 M 行表示 M 条指令，每条指令的格式如题目描述所示。

#### 输出格式

对于每个询问，输出一个整数表示答案。

每个答案占一行。

#### 数据范围

1≤N,M≤105, |d|≤10000, |A[i]|≤109|A[i]|≤109

#### 输入样例：

```
10 5
1 2 3 4 5 6 7 8 9 10
Q 4
Q 1
Q 2
C 1 6 3
Q 2
```

#### 输出样例：

```
4
1
2
5
```



## 差分_树状数组

区间修改

单点查询

```java
import java.io.*;

public class Main {
    int N, M, maxN = 100010;
    long[] arr = new long[maxN], trees = new long[maxN];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]); M = Integer.parseInt(str[1]);
        str = br.readLine().split(" ");
        for (int i = 1; i <= N; i ++) arr[i] = Integer.parseInt(str[i-1]);

        for (int i = 1; i <= N; i ++) add(i, arr[i] - arr[i-1]);  // 差分
        while (M-- > 0) {
            str = br.readLine().split(" ");
            String op = str[0];
            if (op.equals("C")) {
                int le = Integer.parseInt(str[1]), ri = Integer.parseInt(str[2]);
                int n = Integer.parseInt(str[3]);
                add(le, n); add(ri+1, -n);  // 差分
            } else {
                int n = Integer.parseInt(str[1]);
                System.out.println(sum(n));
            }
        }
    }

    int lowbit(int x) {
        return x & -x;
    }

    void add(int idx, long n) {
        for (int i = idx; i <= N; i += lowbit(i)) trees[i] += n;
    }

    long sum(int idx) {
        long res = 0;
        for (int i = idx; i >= 1; i -= lowbit(i)) res += trees[i];
        return res;
    }
}
```

.