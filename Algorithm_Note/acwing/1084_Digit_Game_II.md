# 1084. 数字游戏 II

由于科协里最近真的很流行数字游戏。

某人又命名了一种取模数，这种数字必须满足各位数字之和 mod N 为 0。

现在大家又要玩游戏了，指定一个整数闭区间 [a.b]，问这个区间内有多少个取模数。

#### 输入格式

输入包含多组测试数据，每组数据占一行。

每组数据包含三个整数 a,b,N。

#### 输出格式

对于每个测试数据输出一行结果，表示区间内各位数字和 mod N 为 0 的数的个数。

#### 数据范围

1≤a,b≤2^31^−1, 1≤N<100

#### 输入样例：

```
1 19 9
```

#### 输出样例：

```
2
```



## 计数DP

```java
import java.util.*;
class Main {
    int N,maxN = 10;
    int[][] f = new int[maxN][maxN*maxN];

    public static void main(String[] args) {
        new Main().run();
    }
    void run() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int le = sc.nextInt(), ri = sc.nextInt();
            N = sc.nextInt();
            init();
            System.out.println(dp(ri)-dp(le-1));
        }
    }

    int dp(int n) {  //  mod N 为 0 的数的个数
        if (n == 0) return 1;
        List<Integer> list = new ArrayList<>();
        while (n > 0) {
            list.add(n % 10);
            n /= 10;
        }

        int res = 0, pre = 0;
        for (int i = list.size()-1; i >= 0; i--) {
            int cur = list.get(i);
            for (int j = 0; j < cur; j++) {  
                res += f[i][(N*10-pre - j)%N];  // 当前最高位是j, 则res加上 i-1位余数是N-pre-j的 的数量  
            }
            pre = (pre+cur) % N;   // 左边按最高位固定, 右边全为零
            if (pre == 0) res++;
        }
        return res;
    }

    void init() {
        for (int i = 0; i < maxN; i++) Arrays.fill(f[i], 0);
        f[0][0]  = 1;
        for (int j = 0; j <= 9; j++) f[1][j%N] += 1;
        for (int i = 2; i < maxN; i++) {        // 总共i位, 余数为 x 的数量
            for (int j = 0; j <= 9; j++) {      // 当前最高位是j, 加上i-1位的每个情况
                for (int k = 0; k <= N; k++) {  // i-1的范围在[0,N],因为对 N 取模
                    f[i][(j+k)%N] += f[i-1][k];
                }
            }
        }
    }
}
```

