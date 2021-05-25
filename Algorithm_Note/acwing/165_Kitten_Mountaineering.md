# 165. 小猫爬山

翰翰和达达饲养了 N 只小猫，这天，小猫们要去爬山。

经历了千辛万苦，小猫们终于爬上了山顶，但是疲倦的它们再也不想徒步走下山了（呜咕>_<）。

翰翰和达达只好花钱让它们坐索道下山。

索道上的缆车最大承重量为 W，而 N 只小猫的重量分别是 C1、C2……CN。

当然，每辆缆车上的小猫的重量之和不能超过 W。

每租用一辆缆车，翰翰和达达就要付 11 美元，所以他们想知道，最少需要付多少美元才能把这 N 只小猫都运送下山？

#### 输入格式

第 1 行：包含两个用空格隔开的整数，N 和 W。

第 2..N+1 行：每行一个整数，其中第 i+1 行的整数表示第 i 只小猫的重量 Ci。

#### 输出格式

输出一个整数，表示最少需要多少美元，也就是最少需要多少辆缆车。

#### 数据范围

1≤N≤18, 1≤Ci≤W≤10^8

#### 输入样例：

```
5 1996
1
2
1994
12
29
```

#### 输出样例：

```
2
```



## DFS+剪枝

```java
import java.util.*;

public class Main {
    static int N, W, res = Integer.MAX_VALUE;
    static int[] arr, dp = new int[20];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); W = sc.nextInt();
        arr = new int[N+1];
        for (int i = 1; i <= N; i++) arr[i] = sc.nextInt();
        Arrays.sort(arr);

        dfs(N, 0);
        System.out.println(res);
    }

    private static void dfs(int u, int cnt) {
        if (cnt >= res) return;
        if (u == 0) { res = cnt; return; }

        for (int i = 1; i <= cnt; i++) {
            if (dp[i]+arr[u] <= W) {
                dp[i] += arr[u];
                dfs(u-1, cnt);  // 不添新缆车
                dp[i] -= arr[u];
            }
        }

        dp[cnt+1] = arr[u];
        dfs(u-1, cnt+1);  // 不添新缆车
        dp[cnt+1] = 0;
    }
}
```



## 状压DP

```c++
#include <iostream>
#include <cstring>
#include <cstdio>
#include <algorithm>
using namespace std;

int n, w, arr[20], f[1<<20], g[1<<20];  
// f[s] 表示s状态下的最小代价
// g[s] 表示s状态下的当前所有缆车中最大剩余体积

int main() {
    scanf("%d%d", &n, &w);
    for (int i = 0; i < n; i++) scanf("%d", &arr[i]);
    memset(f, 0x3f, sizeof(f));
    f[0] = 0; g[0] = 0; // 没有缆车, 最大剩余容量为零
    for (int state = 1; state < (1<<n); state++) { // 枚举状态
        for (int i = 0; i < n; i++) {  // 枚举猫
            if (!(state&(1<<i))) continue;  // 当前枚举状态不包含当前猫, 跳过
            
            int pre = state-(1<<i);
            if (g[pre]-arr[i] >= 0) {
                f[state] = f[pre];
                g[state] = max(g[state], g[pre]-arr[i]);
            } else {  // 新增一缆车
                f[state] = f[pre] + 1;
                g[state] = max(g[state], w-arr[i]);
            }
        }
    }
    printf("%d\n", f[(1<<n)-1]);
    return 0;
}
```

