# 109. 天才ACM

给定一个整数 M，对于任意一个整数集合 S，定义“校验值”如下:

从集合 S 中取出 M 对数(即 2×M 个数，不能重复使用集合中的数，如果 S 中的整数不够 M 对，则取到不能取为止)，使得“每对数的差的平方”之和最大，这个最大值就称为集合 S 的“校验值”。

现在给定一个长度为 N 的数列 A 以及一个整数 T。

我们要把 A 分成**若干段**，使得每一段的“校验值”都不超过 T。

求 **最少需要分成几段**。

#### 输入格式

第一行输入整数 K，代表有 K 组测试数据。

对于每组测试数据，第一行包含三个整数 N,M,T 。

第二行包含 N 个整数，表示数列A1,A2…AN。

#### 输出格式

对于每组测试数据，输出其答案，每个答案占一行。

#### 数据范围

1≤K≤12,  1≤N,M≤500000, 0≤T≤1018, 0≤Ai≤220

#### 输入样例：

```
2
5 1 49
8 2 1 7 9
5 1 64
8 2 1 7 9
```

#### 输出样例：

```
2
1
```



## 倍增

```java
import java.util.*;

public class Main{
    int N, M, maxN = 500010;
    long T;
    long[] arr = new long[maxN];

    public static void main(String[] args){
        new Main().init();
    }

    void init() {
        Scanner sc = new Scanner(System.in);
        int K = sc.nextInt();
        while (K-- > 0) {
            N = sc.nextInt(); M = sc.nextInt(); T = sc.nextLong();
            for (int i = 0; i < N; i++) arr[i] = sc.nextLong();

            int le = 0, ri = 0, res = 0;
            while (ri < N) {
                int len = 1;
                while (len > 0) {
                    if (ri+len<=N && check(le, ri+len)) {
                        ri += len;
                        len <<= 1;
                    } else {
                        len >>= 1;
                    }
                }
                res++;
                le = ri;
            }

            System.out.println(res);
        }
    }

    boolean check(int le, int ri) {
        int t = 0;
        long[] tmp = new long[ri-le];
        for (int i = le; i < ri; i++) tmp[t++] = arr[i];
        Arrays.sort(tmp);
        long sum = 0;
        for (int i = 0; i < M && i < t; i++, t--) {
            long l = tmp[t-1] - tmp[i];
            sum += l * l;
        }

        return sum <= T;
    }
}
```

