# 327. 玉米田

农夫约翰的土地由 M×N 个小方格组成，现在他要在土地里种植玉米。

非常遗憾，部分土地是不育的，无法种植。

而且，相邻的土地不能同时种植玉米，也就是说种植玉米的所有方格之间都不会有公共边缘。

现在给定土地的大小，请你求出共有多少种种植方法。

土地上什么都不种也算一种方法。

#### 输入格式

第 1 行包含两个整数 M 和 N。

第 2..M+1 行：每行包含 N 个整数 0 或 1，用来描述整个土地的状况，1 表示该块土地肥沃，0 表示该块土地不育。

#### 输出格式

输出总种植方法对 108 取模后的值。

#### 数据范围

1≤M,N≤12

#### 输入样例：

```
2 3
1 1 1
0 1 0
```

#### 输出样例：

```
9
```



## 状压DP

```java
import java.util.*;
class Main {
    int N, M, maxN = 14, mod = 10000_0000;
    int arr[] = new int[maxN], dp[][] = new int[maxN][1<<maxN];
    List<Integer> state = new ArrayList<>();
    List<Integer>[] head = new List[1<<maxN];
    
    public static void main(String[] args) {
        new Main().run();
    }
    
    void run() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); M = sc.nextInt();
        
        for (int i = 1; i <= N; i++) {  
            int st = 0;
            for (int j = 0; j < M; j++) {
                int x = sc.nextInt();
                if (x==0) st |= 1<<j;  // 非法土地 位为 1
            }
            arr[i] = st;
        }
        
        for (int i = 0; i < 1<<M; i++) {  // 筛选掉 有相邻土地 的状态
            if ((i & i<<1) == 0) state.add(i);
        }
        
        for (int i = 0; i < state.size(); i++) {  // 把可以上下相邻的状态归类
            head[i] = new ArrayList<>();
            int cur = state.get(i);
            for (int j = 0; j < state.size(); j++) {
                if ((cur&state.get(j)) == 0) head[i].add(j);
            }
        }
        
        dp[0][0] = 1;
        for (int i = 1; i <= N; i++) {  // 每一行
            for (int j = 0; j < state.size(); j++) { // 遍历所有 合法行状态
                if ((state.get(j) & arr[i]) == 0) {  // 如果状态可以在当前行
                    for (int pre : head[j]) {
                        dp[i][j] = (dp[i][j]+dp[i-1][pre])%mod;
                    }
                }
            }
        }

        int res = 0;
        for (int i = 0; i < 1<<M; i++) res = (res + dp[N][i]) % mod;
        System.out.println(res);
    }
}
```

