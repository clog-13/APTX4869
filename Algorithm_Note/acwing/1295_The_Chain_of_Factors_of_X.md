# 1295. X的因子链

输入正整数 X，求 X 的大于 1 的因子组成的满足任意前一项都能整除后一项的严格递增序列的最大长度，以及满足最大长度的序列的个数。

#### 输入格式

输入包含多组数据，每组数据占一行，包含一个正整数表示 X。

#### 输出格式

对于每组数据，输出序列的最大长度以及满足最大长度的序列的个数。每个结果占一行。

#### 数据范围

1≤X≤220

#### 输入样例：

```
2
3
4
10
100
```

#### 输出样例：

```
1 1
1 1
2 1
2 2
4 6
```



## 素数筛+质因子公理

```java
import java.util.*;
class Main {
    int maxN = (1<<20) + 10;
    int[] primes = new int[maxN], minp = new int[maxN];
    boolean[] st = new boolean[maxN];

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        get_primes();

        int[] cout = new int[maxN];
        while (sc.hasNext()) {
            int x = sc.nextInt();
            int idx = 0, cnt = 0;
            while (x>1) {
                int p = minp[x];
                cout[idx] = 0;
                while (x%p==0) {
                    x/=p;
                    cout[idx]++;  // 当前质因子的个数
                    cnt++;  // x 可以分成质因子的数量
                }
                idx++;
            }

            long res = 1;
            for (int i = 1; i <= cnt; i++) res *= i;  // 全排列
            for (int i = 0; i < idx; i++) {
                for (int c = 1; c <= cout[i]; c++) {
                    res /= c;  // 去重
                }
            }　
            System.out.println(cnt+" "+res);
        }
    }

    void get_primes() {
        int idx = 0;
        for (int i = 2; i < maxN; i ++ ) {
            if (!st[i]) {
                primes[idx++] = i;
                minp[i] = i;
            }
            for (int j = 0; primes[j]*i < maxN; j++) {
                st[primes[j] * i] = true;
                minp[primes[j] * i] = primes[j];
                if (i % primes[j] == 0) break;
            }
        }
    }
}
```

