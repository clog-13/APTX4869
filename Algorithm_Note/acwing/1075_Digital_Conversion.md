# 1075. 数字转换

如果一个数 x 的约数之和 y（不包括他本身）比他本身小，那么 x 可以变成 y，y 也可以变成 x。

例如，4 可以变为 3，1 可以变为 7。

限定所有数字变换在不超过 n 的正整数范围内进行，求不断进行数字变换且不出现重复数字的最多变换步数。

#### 输入格式

输入一个正整数 n。

#### 输出格式

输出不断进行数字变换且不出现重复数字的最多变换步数。

#### 数据范围

1≤n≤50000

#### 输入样例：

```
7
```

#### 输出样例：

```
3
```

#### 样例解释

一种方案为：4→3→1→7。



## Math + DFS

```java
import java.util.*;

public class Main {
    static int idx, res, N, maxN = 100010;
    static int[] info = new int[maxN], from = new int[maxN], to = new int[maxN];
    static int[] sum = new int[maxN];
    static boolean[] st = new boolean[maxN];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();

        // O(n^2) 朴素写法, TLE
        // for (int i = 2; i <= N; i++) {
        //     for (int j = 1; j < i; j++) {
        //         if (i%j==0) sum[i] += j;  // 这步判断比较浪费时间
        //     }
        // }

        // O(n*logn) 调和级数O(logn) = n + n / 2 + n / 3 + ... + n/n = lnn + c
        for (int i = 1; i <= N; i++) {  // 通过筛法求出1到N的所有约数之和
            for (int j = 2; j*i <= N; j++) {  // 保证正确性, 这里从2开始
                sum[i * j] += i;  // sum[i+j]: i*j的约数之和, 为了防止重复, 这里只加 i
            }
        }

        Arrays.fill(info, -1);
        for (int i = 1; i <= N; i++) {  // 1 -> 7 和 1 -> 3 最后一边作为来边,一边作为去边
            if (sum[i] < i) add(sum[i], i);  // 和 -> 数
        }

        for (int i = 1; i <= N; i++) if (!st[i]) dfs(i);  // dfs(i) 找以i为根的树中的最长路径
        // dfs(1);  // dfs 1 就行了,可以理解为最长的必包含1 (假设最长不包含1, 也可以连一条向1的边)
        System.out.println(res);
    }

    static int dfs(int u) {
        st[u] = true;
        int d1 = 0, d2 = 0;
        for (int i = info[u]; i != -1; i = from[i]) {
            int t = to[i];
            int d = dfs(t) + 1;  // 找到u点往下走的一个最大长度

            if (d > d1) {
                d2 = d1; d1 = d;
            } else if (d > d2) {
                d2 = d;
            }
        }
        res = Math.max(res, d1 + d2);
        return d1;
    }

    static void add(int a, int b) {
        from[idx] = info[a];
        to[idx] = b;
        info[a] = idx++;
    }
}
```

