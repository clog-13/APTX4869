# 1081. 度的数量

求给定区间 [X,Y] 中满足下列条件的整数个数：这个数恰好等于 K 个互不相等的 B 的整数次幂之和。

例如，设 X=15,Y=20,K=2,B=2，则有且仅有下列三个数满足题意：

17=2^4^+2^0^
18=2^4^+2^1^
20=2^4^+2^2^

#### 输入格式

第一行包含两个整数 X 和 Y，接下来两行包含整数 K 和 B。

#### 输出格式

只包含一个整数，表示满足条件的数的个数。

#### 数据范围

1≤X≤Y≤2^31^−1, 1≤K≤20, 2≤B≤10

#### 输入样例：

```
15 20
2
2
```

#### 输出样例：

```
3
```



## 数位DP

关于进制转换:

``` 
2进制:  20 = 10100 => 1*(2^4) + 0*(2^3) + 1*(2^2) + 0*(2^1) + 0*(2^1);
3进制:  20 = 202 => 2*(3^2) + 0*(3^1) + 2*(3^0)
10进制: 20 = 20 => 2*(10^1) + 0*(10^0)
题目要求 "...B的整数次幂之和", 所以只有某个数在B进制表示下每一位都是1或0的才可以(这样才纯, 即整数次幂)
3^n意思为n个3相乘, 乘一个非3的数就不纯了
同时根据进制的转换原理，B进制下的数的每位范围在[0, B-1]之间，想要纯只能是1或0
```



```java
import java.util.*;
class Main {
    int K, B, maxN = 35;
    int[][] f = new int[maxN][maxN];
    
    public static void main(String[] args) {
        new Main().run();
    }
    
    void run() {
        init();
        Scanner sc = new Scanner(System.in);
        int le = sc.nextInt(), ri = sc.nextInt();
        K = sc.nextInt(); B = sc.nextInt();

        System.out.println(dp(ri) - dp(le-1));
    }
    
    int dp(int n) {  // n的B进制中, 小等于 n 并且恰好有K位是 1 的个数
        if (n==0) return 0;
        List<Integer> list = new ArrayList<>();
        while (n>0) {
            list.add(n%B);
            n/=B;
        }
        int res = 0, last = 0;
        for (int i = list.size()-1; i >= 0; i--) {
            int x = list.get(i);
            if (x > 0) {
                // 当前这一位是0(并且固定了last个1,因为前面不知道取了后面还能不能取,这时候把前面补上)
                res += f[i][K-last];  
                // 当前这一位是 1
                if (x == 1) {  
                    // last++;
                    if (++last > K) break;
                } else {  // 1000如果当前点取了,后面的数不一定能取1, 
                    // 2000如果当前点取了, 后面还是可以取任意多个 1 (1_111,...,1_000,都是小于2000的, 都是合法的)
                    if (K-last-1 >= 0) res += f[i][K-last-1];  // 所以直接用 组合数 求了就可以返回了
                    break;  // 为了防止重复计算, 直接break
                }
            }
            if (i==0 && last==K) res++;
        }
        return res;
    }
    
    void init() {
        for (int i = 0; i < maxN; i++) {
            for (int j = 0; j <= i; j++) {
                if (j==0) f[i][j] = 1;
                else f[i][j] = f[i-1][j-1] + f[i-1][j];
            }
        }
    }
}
```

