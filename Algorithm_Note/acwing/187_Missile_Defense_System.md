# 187. 导弹防御系统

为了对抗附近恶意国家的威胁，R 国更新了他们的导弹防御系统。

一套防御系统的导弹拦截高度要么一直 **严格单调** 上升要么一直 **严格单调** 下降。

例如，一套系统先后拦截了高度为 3 和高度为 4 的两发导弹，那么接下来该系统就只能拦截高度大于 4 的导弹。

给定即将袭来的一系列导弹的高度，请你求出至少需要多少套防御系统，就可以将它们全部击落。

#### 输入格式

输入包含多组测试用例。

对于每个测试用例，第一行包含整数 n，表示来袭导弹数量。

第二行包含 n 个不同的整数，表示每个导弹的高度。

当输入测试用例 n=0 时，表示输入终止，且该用例无需处理。

#### 输出格式

对于每个测试用例，输出一个占据一行的整数，表示所需的防御系统数量。

#### 数据范围

1≤n≤50

#### 输入样例：

```
5
3 5 2 4 1
0 
```

#### 输出样例：

```
2
```

#### 样例解释

对于给出样例，最少需要两套防御系统。

一套击落高度为 3,4 的导弹，另一套击落高度为 5,2,1 的导弹。



## 迭代加深 + DFS

```java
import java.util.*;

class Main {
    int N, maxN = 55;
    int[] arr = new int[maxN];
    // up[i] = 第 i 个上升序列的最后一个数
    int[] up = new int[maxN], dn = new int[maxN];

    public static void main(String[] args) {
        new Main().init();
    }

    private void init() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            N = sc.nextInt();
            if (N == 0) break;

            for (int i = 0; i < N; i++) arr[i] = sc.nextInt();

            int depth = 1;
            while (!dfs(0, 0, 0, depth)) depth++;
            System.out.println(depth);
        }
    }

    private boolean dfs(int u, int su, int sd, int depth) {
        if (su+sd > depth) return false;
        if (u == N) return true;

        boolean flag = false;
        for (int i = 1; i <= su; i++) {
            if (arr[u] > up[i]) {
                flag = true;
                int bk = up[i];
                up[i] = arr[u];  // 上升防御系统
                if (dfs(u+1, su, sd, depth)) return true;
                up[i] = bk;
                break;
            }
        }
        if (!flag) {
            up[su+1] = arr[u];  // 新增上升防御系统
            if (dfs(u+1, su+1, sd, depth)) return true;
        }

        flag = false;
        for (int i = 1; i <= sd; i++) {
            if (arr[u] < dn[i]) {
                flag = true;
                int bk = dn[i];
                dn[i] = arr[u];  // 下降防御系统
                if (dfs(u+1, su, sd, depth)) return true;
                dn[i] = bk;
                break;
            }
        }
        if (!flag) {
            dn[sd+1] = arr[u];  // 新增下降防御系统
            if (dfs(u+1, su, sd+1, depth)) return true;
        }

        return false;
    }
}
```



## DFS

```c++
#include<iostream>
using namespace std;
const int N = 55;
int a[N], ans, up[N], down[N], n;
void dfs(int u, int d, int t) {  // u表示上升的系统个数，d表示下降的系统个数,t表示第t个数
    if (u + d >= ans) return;
    if (t == n) {  // 全部防御完
        if (u+d < ans) ans = u+d;
        return;
    }
    int i;
    for (i = 1; i <= u; i++)  // 找到第一个末尾数小于a[t]的导弹系统
        if (up[i] < a[t]) break;
    int temp = up[i];
    up[i] = a[t];
    dfs(max(u, i), d, t + 1);
    up[i] = temp;
    
    for (i = 1; i <= d; i++)  // 找到第一个末尾数大于a[t]的导弹系统
        if (down[i] > a[t]) break;
    temp = down[i];
    down[i] = a[t];
    dfs(u, max(d, i), t + 1);
    down[i] = temp;
}

int main(){
    while (scanf("%d", &n) != EOF && n != 0) {
        ans = 100;
        for (int i = 0; i < n; i++) cin >> a[i];
        dfs(0, 0, 0);
        printf("%d\n", ans);
    }
    return 0;
}
```



## 迭代加深 + DFS + 二分

```java
#include <iostream>
#include <algorithm>
#include <cstring>

using namespace std;

const int N = 55;

int n, up[N], down[N], a[N];

bool dfs(int depth, int u, int su, int sd) {
    if (su + sd > depth) return false;
    if (u == n) return true;

    if (!su || up[su] >= a[u]) {
        up[su + 1] = a[u];
        if (dfs(depth, u + 1, su + 1, sd)) return true;
    } else {
        int l = 1, r = su;
        while (l < r) {  // 坐标
            int mid = (l + r) >> 1;
            if (up[mid] < a[u]) r = mid;
            else l = mid + 1;
        }
        int t = up[l];
        up[l] = a[u];
        if (dfs(depth, u + 1, su, sd)) return true;
        up[l] = t;
    }

    if (!sd || down[sd] <= a[u]) {
        down[sd + 1] = a[u];
        if (dfs(depth, u + 1, su, sd + 1)) return true;
    } else {
        int l = 1, r = sd;
        while (l < r) {
            int mid = (l + r) >> 1;
            if (down[mid] > a[u]) r = mid;
            else l = mid + 1;
        }
        int t = down[l];
        down[l] = a[u];
        if (dfs(depth, u+1, su, sd)) return true;
        down[l] = t;
    }

    return false;
}

int main() {
    while (cin >> n, n) {
        for (int i = 0; i < n; i ++) cin >> a[i];

        int depth = 0;
        while (!dfs(depth, 0, 0, 0)) depth++; 
        cout << depth << endl;
    }
    return 0;
}
```

