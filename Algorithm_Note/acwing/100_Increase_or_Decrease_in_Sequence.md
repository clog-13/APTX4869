# 100. 增减序列

给定一个长度为 n 的数列 a1,a2,…,an，每次可以选择一个区间 [l,r]，使下标在这个区间内的数都加一或者都减一。

求至少需要多少次操作才能使数列中的所有数都一样，并求出在保证最少次数的前提下，最终得到的数列可能有多少种。

#### 输入格式

第一行输入正整数n。

接下来n行，每行输入一个整数，第i+1行的整数代表ai。

#### 输出格式

第一行输出最少操作次数。

第二行输出最终能得到多少种结果。

#### 数据范围

0<n≤105, 0≤ai<2147483648

#### 输入样例：

```
4
1
1
2
2
```

#### 输出样例：

```
1
2
```

## 差分

考虑 [1,1,1,1,1,1,1,2,3,1,1,1,1,1,1,-1]

```java
import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long[] data = new long[N+1], diff = new long[N+2];
        for (int i = 1; i <= N; i++) {
            data[i] = sc.nextLong();
            diff[i] = data[i] - data[i-1];
        }
        long pos = 0, neg = 0;
        for (int i = 2; i <= N; i++) {
            if (diff[i] > 0) pos += diff[i];
            else neg -= diff[i];
        }
        System.out.println(Math.max(pos, neg));  // pos,neg是累计的差分量（不是某某总和）
        System.out.println(Math.abs(pos-neg)+1);
    }
}
```

.
