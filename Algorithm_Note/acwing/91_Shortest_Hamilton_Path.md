# 91. 最短Hamilton路径

给定一张 n 个点的带权无向图，点从 0∼n−1 标号，求起点 0 到终点 n−1 的最短 Hamilton 路径。

Hamilton 路径的定义是从 0 到 n−1 不重不漏地经过每个点恰好一次。

#### 输入格式

第一行输入整数 n。

接下来 n 行每行 n 个整数，其中第 i 行第 j 个整数表示点 i 到 j 的距离（记为 a[i,j]）。

对于任意的 x,y,z，数据保证 a[x,x]=0，a[x,y]=a[y,x] 并且 a[x,y]+a[y,z]≥a[x,z]。（权非负）

#### 输出格式

输出一个整数，表示最短 Hamilton 路径的长度。

#### 数据范围

1≤n≤20, 0≤a[i,j]≤1070≤a[i,j]≤107

#### 输入样例：

```
5
0 2 4 5 1
2 0 6 5 3
4 6 0 8 3
5 5 8 0 5
1 3 3 5 0
```

#### 输出样例：

```
18
```



## 状压DP

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[][] dp = new int[1<<N][N], arr = new int[N][N];
        int[] logs = new int[1<<N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) arr[i][j] = sc.nextInt();
        }
        for (int i = 0; i < 1<<N; i++) Arrays.fill(dp[i], 0x3f3f3f3f);
        for (int i = 0; i < N; i++) logs[1<<i] = i;

        // dp[i][j]表示当前所有走过点的集合i(状态)，最后移动到j (i是{0,1,4}，j是1，那么i = 10011)
        dp[1][0] = 0;  // 1:只走了0的集合  0:起点
        for (int st = 0; st < 1<<N; st++) {  // 枚举所有状态
            for (int j = st; j > 0; j -= j&-j) { // 从当前状态减去 e
                int e = logs[j&-j];
                for (int k = st-(1<<e); k > 0; k -= k&-k) {  // 从剩余节点中找 到e的最小值
                    int m = logs[k&-k];  // st中最后到达的是e，遍历所有 st-e 的所有状态的转移代价
                    dp[st][e] = Math.min(dp[st][e], dp[st-(1<<e)][m] + arr[m][e]);
                }
            }
        }
        System.out.println(dp[(1<<N)-1][N-1]);
    }
}
```

