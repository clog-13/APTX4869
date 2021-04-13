# 2068. 整数拼接

给定一个长度为 n 的数组 A1,A2,⋅⋅⋅,An。

你可以从中选出两个数 Ai 和 Aj(i 不等于 j)，然后将 Ai 和 Aj 一前一后拼成一个新的整数。

例如 12 和 345 可以拼成 12345 或 34512。

注意交换 Ai 和 Aj 的顺序总是被视为 2 种拼法，即便是 Ai=Aj 时。

请你计算有多少种拼法满足拼出的整数是 K 的倍数。

#### 输入格式

第一行包含 2 个整数 n 和 K。 第二行包含 n 个整数 A1,A2,⋅⋅⋅,An。

#### 输出格式

一个整数代表答案。

#### 数据范围

1≤n≤10^5^, 1≤K≤10^5^, 1≤Ai≤10^9^

#### 输入样例：

```
4 2
1 2 3 4
```

#### 输出样例：

```
6
```



## 记忆化

```java
import java.util.Scanner;

public class Main {
    static long res = 0, N, K;
    static long[] data = new long[100005];
    static long[][] cout = new long[11][100005];    // cout[a][b]: 乘以(a*10)时，余数为b的 个数

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextLong();
        K = sc.nextLong();

        // (a[i]*pow(10, a[j])%k + a[j]%k)%k = 0
        //  a[i]*pow(10, a[j])%k
        for (int i = 1; i <= N; i++) {
            data[i] = sc.nextLong();
            for (int j = 1; j <= 10; j++) {
                int t = (int) (data[i]*Math.pow(10, j) % K);
                cout[j][t]++;
            }
        }

        for (int i = 1; i <= N; i++) {
            int len = (data[i]+"").length();
            int t1 = (int) (data[i] % K);   // 在后
            int t2 = (int) (data[i]*Math.pow(10,len) % K);  // 在前
            if (t1 == 0) {
                res += cout[len][0];    // 当作后段时，余数为零，找 前段长度len 且余数为零的数量
                if (t2 == 0) res--;     // 不能把同一个树同时放在前后两处(如果t2==0, 上句会把当前数多算一次)
            } else {                    // 当作后段时，余数不为零
                res += cout[len][(int) K-t1];
                if (t1+t2 == K) res--;  // 同理,不能把同一个树同时放在前后两处(t1,t2是用同一个数求出来的)
            }                           // 同时注意理解题目 “交换Ai和Aj的顺序总是被视为2种拼法,即便是Ai=Aj时”
        }

        System.out.println(res);
    }
}
```

