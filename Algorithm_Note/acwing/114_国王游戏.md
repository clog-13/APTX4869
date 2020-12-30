# 114. 国王游戏

恰逢 H 国国庆,国王邀请 n 位大臣来玩一个有奖游戏。

首先,他让每个大臣在左、右手上面分别写下一个整数,国王自己也在左、右手上各写一个整数。

然后,让这 n 位大臣排成一排,国王站在队伍的最前面。

排好队后,所有的大臣都会获得国王奖赏的若干金币,每位大臣获得的金币数分别是:

排在该大臣前面的所有人的左手上的数的乘积除以他自己右手上的数,然后向下取整得到的结果。

国王不希望某一个大臣获得特别多的奖赏,所以他想请你帮他重新安排一下队伍的顺序,使得获得奖赏最多的大臣,所获奖赏尽可能的少。

注意,国王的位置始终在队伍的最前面。

#### 输入格式

第一行包含一个整数 n,表示大臣的人数。

第二行包含两个整数 a 和 b,之间用一个空格隔开,分别表示国王左手和右手上的整数。

接下来 n 行,每行包含两个整数 a 和 b,之间用一个空格隔开,分别表示每个大臣左手和右手上的整数。

#### 输出格式

输出只有一行,包含一个整数,表示重新排列后的队伍中获奖赏最多的大臣所获得的金币数。

#### 数据范围

1≤n≤1000
0<a,b<10000

#### 输入样例：

```
3
1 1
2 3
7 4
4 6
```

#### 输出样例：

```
2
```

## 贪心 + 数学
![](pic\114.png)
```java
import java.math.BigInteger;
import java.util.*;

public class Main {
    int N;
    PII[] arr = new PII[1010];

    public static void main(String[] args) {
        new Main().init();
    }

    void init() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();

        for (int i = 0; i <= N; i++) {
            int le = sc.nextInt(); int ri = sc.nextInt();
            arr[i] = new PII(le, ri);
        }
        Arrays.sort(arr, 1, N+1);

        BigInteger res = BigInteger.ZERO;
        BigInteger pre = BigInteger.valueOf(arr[0].le);
        for (int i = 1; i <= N; i++) {
            res = res.max(pre.divide(BigInteger.valueOf(arr[i].ri)));
            pre = pre.multiply(BigInteger.valueOf(arr[i].le));
        }
        System.out.println(res);
    }

    static class PII implements Comparable<PII> {
        int le, ri, sum;

        public PII(int l, int r) {
            le = l; ri = r;
            sum = le*ri;
        }

        @Override
        public int compareTo(PII pii) {
            if (this.sum == pii.sum) return this.ri - pii.ri;
            else return this.sum - pii.sum;
        }
    }
}
```