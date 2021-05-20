# 852. spfa判断负环

个 n 个点 m 条边的有向图，图中可能存在重边和自环， **边权可能为负数**。

请你判断图中是否存在**负权回路**。

#### 输入格式

第一行包含整数 n 和 m。

接下来 m 行每行包含三个整数 x,y,z，表示存在一条从点 x 到点 y 的有向边，边长为 z。

#### 输出格式

如果图中**存在**负权回路，则输出 `Yes`，否则输出 `No`。

#### 数据范围

1≤n≤2000, 1≤m≤10000, 图中涉及边长绝对值均不超过 10000。

#### 输入样例：

```
3 3
1 2 -1
2 3 4
3 1 -4
```

#### 输出样例：

```
Yes
```



## SPFA（虚拟源点）

```java
import java.util.*;
class Main {
    int N, M, idx, maxN = 2010, maxM = 10010, INF = 0x3f3f3f3f;
    int[] info = new int[maxN], dist = new int[maxN], cout = new int[maxN];
    int[] from = new int[maxM], to = new int[maxM], val = new int[maxM];
    boolean[] vis = new boolean[maxN];

    public static void main(String[] args) {
        new Main().init();
    }

    void init() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); M = sc.nextInt();

        Arrays.fill(info, -1);
        while (M-- > 0) {
            int a = sc.nextInt(), b = sc.nextInt(), c = sc.nextInt();
            add(a, b, c);
        }

        if (spfa()) System.out.println("No");
        else System.out.println("Yes");
    }

    boolean spfa() {
        Queue<Integer> queue = new LinkedList<>();
        // 为了遍历到所有的点, 设有一个超级虚拟源点, dist[ALL] = 0 (虚拟源点权值为0)
        for (int i = 1; i <= N; i++) queue.add(i);  

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            vis[cur] = false;

            for (int i = info[cur]; i != -1; i = from[i]) {
                int t = to[i];
                if (dist[t] > dist[cur]+val[i]) {
                    dist[t] = dist[cur]+val[i];
                    cout[t] = cout[cur]+1;
                    // 从虚拟源点到t至少经过n条边时，表示一定有点是重复使用(总共N个点)
                    if (cout[t] >= N) return false;
                    if (!vis[t]) {
                        vis[t] = true;
                        queue.add(t);
                    }
                }
            }
        }
        return true;
    }

    void add(int a, int b, int c) {
        from[idx] = info[a];
        to[idx] = b;
        val[idx] = c;
        info[a] = idx++;
    }
}
```

