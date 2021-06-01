# 1083. Windy数

Windy 定义了一种 Windy 数：不含前导零且相邻两个数字之差至少为 2 的正整数被称为 Windy 数。

Windy 想知道，在 A 和 B 之间，包括 A 和 B，总共有多少个 Windy 数？

#### 输入格式

共一行，包含两个整数 A 和 B。

#### 输出格式

输出一个整数，表示答案。

#### 数据范围

1≤A≤B≤2×10^9^

#### 输入样例：

```
1 10
```

#### 输出样例：

```
9
```



## 计数DP

```java
import java.util.*;
class Main {
    int maxN = 15;
    int[][] f = new int[maxN][maxN];
    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        init();
        int le = sc.nextInt(), ri = sc.nextInt();
        System.out.println(dp(ri) - dp(le-1));
    }

    int dp(int n) {
        if (n == 0) return 0;  // 不含前置零
        List<Integer> list = new ArrayList<>();
        while (n > 0) {
            list.add(n % 10);
            n /= 10;
        }

        int res = 0, last = -1;  // 初始化last=-1,防止第一位被卡掉
        for (int i = list.size()-1; i >= 0; i--) {
            int cur = list.get(i);
            for (int j = i==list.size()-1?1:0; j < cur; j++) {
                if (Math.abs(j-last) >= 2) res += f[i+1][j];
            }
            if (Math.abs(cur-last) < 2) break;
            last = cur;
            if (i == 0) res++;
        }
        // 计算被前置零耽误的,比如00175对于31579是合法的,但会因为0_1...不合法被耽误
        for (int i = list.size()-1; i >= 0; i--) {  // i从list.size-1开始, 上面是list.size
            for (int j = 1; j <= 9; j++) {  // 从 1 开始
                res += f[i][j];
            }
        }
        return res;
    }

    void init() {
        for (int i = 0; i <= 9; i++) f[1][i] = 1;
        for (int i = 2; i < maxN; i++) {
            for (int j = 0; j <= 9;j++) {
                for (int k = 0; k <= 9; k++) {
                    if (Math.abs(j-k) >= 2) f[i][j] += f[i-1][k];
                }
            }
        }
    }
}
```

