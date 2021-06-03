# 3491. 完全平方数

一个整数 a 是一个完全平方数，是指它是某一个整数的平方，即存在一个整数 b，使得 a=b^2。

给定一个正整数 n，请找到最小的正整数 x，使得它们的乘积是一个完全平方数。

#### 输入格式

输入一行包含一个正整数 n。

#### 输出格式

输出找到的最小的正整数 x。

#### 数据范围

对于 30% 的评测用例，1≤n≤1000，答案不超过 1000。
对于 60% 的评测用例，1≤n≤10^8^，答案不超过 10^8^。
对于所有评测用例，1≤n≤10^12^，答案不超过 10^12^。

#### 输入样例：

```
12
```

#### 输出样例：

```
3
```



## Math

一个数如果时完全平方数，则所有质因子必定是偶次方

求N的每个质因子的平方次数，非偶的补全

```java
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long N = sc.nextLong(), res = 1;
        for (long i = 2; i*i <= N; i++) {  // 遍历质因子
            if (N % i == 0) {  // 判断是否为质因子
                int cnt = 0;
                while (N % i == 0) {  // 计算质因子次数
                    cnt++;
                    N /= i;  // N 在变少
                }
                if ((cnt&1) == 1) res *= i;
            }            
        }
        if (N > 1) res *= N;
        System.out.println(res);
    }
}
```

