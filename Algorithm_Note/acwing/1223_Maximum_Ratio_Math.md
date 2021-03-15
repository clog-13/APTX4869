# 1223. 最大比例

X星球的某个大奖赛设了 M 级奖励。

每个级别的奖金是一个正整数。

并且，相邻的两个级别间的比例是个固定值。

也就是说：所有级别的奖金数构成了一个等比数列。

比如：16,24,36,54，其等比值为：3/2。

现在，我们随机调查了一些获奖者的奖金数。

请你据此推算可能的最大的等比值。

#### 输入格式

第一行为数字 N ，表示接下的一行包含 N 个正整数。

第二行 N 个正整数 Xi，用空格分开，每个整数表示调查到的某人的奖金数额。

#### 输出格式

一个形如 A/B 的分数，要求 A、B 互质，表示可能的最大比例系数。

#### 数据范围

0<N<100
0<Xi<10^12
数据保证一定有解。

#### 输入样例1：

```
3
1250 200 32
```

#### 输出样例1：

```
25/4
```

#### 输入样例2：

```
4
3125 32 32 200
```

#### 输出样例2：

```
5/2
```

#### 输入样例3：

```
3
549755813888 524288 2
```

#### 输出样例3：

```
4/1
```



## GCD + 更相减损术

```java
import java.util.*;

class Main {
    static int maxN = 110;
    static long[] arr;
    static long[] fmq = new long[maxN], fzq = new long[maxN];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        arr = new long[N];
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextLong();
        }
        Arrays.sort(arr);
        int idx = 0;
        for (int i = 1; i < N; i++) {
            if (arr[i] != arr[i-1]) {   // arr[0] 可以换成 arr[i-1]
                long d = gcd(arr[0], arr[i]);  // d>1, 因为d必是 q^x 的形式
                fmq[idx] = arr[i] / d;  // 除去 a0
                fzq[idx] = arr[0] / d;  // 除去 a0
                idx++;
            }
        }

        long rm = fmq[0], rz = fzq[0];
        for (int i = 1; i < idx; i++) {
            rm = gcd_sub(rm, fmq[i]);  // 求fm^x1, fm^x2, ...的最大公约数
            rz = gcd_sub(rz, fzq[i]);  // 求fz^x1, fz^x2, ...的最大公约数
        }
        System.out.println(rm+"/"+rz);
    }

    static long gcd(long a, long b) {
        return b==0 ? a : gcd(b, a%b);
    }

    static long gcd_sub(long a, long b) {
        long ta = a, tb = b;
        if (a < b) {
            ta = b; tb = a;
        }

        if (tb == 1) return ta;
        return gcd_sub(tb, ta/tb);
    }
}
```

