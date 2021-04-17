# 1064. 小国王

在 n×n 的棋盘上放 k 个国王，国王可攻击相邻的 8 个格子，求使它们无法互相攻击的方案总数。

#### 输入格式

共一行，包含两个整数 n 和 k。

#### 输出格式

共一行，表示方案总数，若不能够放置则输出0。

#### 数据范围

1≤n≤10, 0≤k≤n^2^

#### 输入样例：

```
3 2
```

#### 输出样例：

```
16
```



## 状态压缩DP

```java
import java.io.*;

class Main {
    long[][][] dp = new long[11][1<<11][105];
    int[] tem = new int[1<<11], num = new int[1<<11];
    int idx = 0;

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        int n = Integer.parseInt(str[0]), k = Integer.parseInt(str[1]);
        dp[0][0][0] = 1;  // dp[i][cur][c]:前i行放好,第i行状态cur,当前放置的国王数为c的情况的方案数
        long res = 0;
        for (int i = 0; i < 1<<n; i++) {  // 初始化,枚举每种状态
            if ((i & i<<1) > 0) continue;  // 相邻,不合法
            tem[idx] = i;
            num[idx++] = get(i);
        }

        for (int i = 1; i <= n; i++) {  // 遍历每行
            for (int cur = 0; cur < idx; cur++) {
                for (int pre = 0; pre < idx; pre++) {  // 攻击范围是左右斜角
                    if ((tem[cur]<<1&tem[pre])==0 && (tem[cur]&tem[pre])==0 && (tem[cur]>>1&tem[pre])==0) {
                        for (int c = 0; c <= k; c++) {
                            if (num[cur]+c <= k) {
                                dp[i][cur][num[cur]+c] += dp[i-1][pre][c];
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < idx; i++) res += dp[n][i][k];
        System.out.println(res);
    }

    int get(int x) {
        int res = 0;
        while (x > 0) {
            int t = x&(-x);
            if (t > 0) res++;
            x -= t;
        }
        return res;
    }
}
```

