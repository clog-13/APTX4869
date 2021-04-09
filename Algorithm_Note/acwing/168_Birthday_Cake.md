# 168. 生日蛋糕

7月17日是Mr.W的生日，ACM-THU为此要制作一个体积为Nπ的M层生日蛋糕，每层都是一个圆柱体。

设从下往上数第i层蛋糕是半径为Ri, 高度为Hi的圆柱。

当i < M时，要求Ri > Ri+1且Hi > Hi+1。

由于要在蛋糕上抹奶油，为尽可能节约经费，我们希望蛋糕外表面（最下一层的下底面除外）的面积Q最小。

令Q = Sπ ，请编程对给出的N和M，找出蛋糕的制作方案（适当的Ri和Hi的值），使S最小。

除Q外，以上所有数据皆为正整数 。

#### 输入格式

输入包含两行，第一行为整数N（N <= 10000），表示待制作的蛋糕的体积为Nπ。

第二行为整数M(M <= 20)，表示蛋糕的层数为M。

#### 输出格式

输出仅一行，是一个正整数S（若无解则S = 0）。

#### 数据范围

1≤N≤10000, 1≤M≤20

#### 输入样例：

```
100
2
```

#### 输出样例：

```
68
```



## DFS + 剪枝

数学剪枝推导：

注意：i=1 表示最上层蛋糕（面积体积最小）

搜索是从下往上搜索，所以这里 N-v 表示的是剩余没被搜索的面积体积总和

![](pic\168.jpg)

```java
import java.util.*;

class Main {
    static int N, M, maxN = 30, INF = 0x3f3f3f3f, res = INF;
    static int[] minv = new int[maxN], mins = new int[maxN];
    static int[] H = new int[maxN], R = new int[maxN];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); M = sc.nextInt();
        for (int i = 1; i <= M; i++) {  // 剪枝：预处理最小的体积和表面积
            minv[i] = minv[i-1] + i*i * i;
            mins[i] = mins[i-1] + 2*i * i;
        }

        H[M+1] = R[M+1] = INF;
        dfs(M, 0, 0);   // 剪枝：搜索顺序的优化
        
        if (res == INF) System.out.println(0);
        else System.out.println(res);
    }

    private static void dfs(int dep, int v, int s) {
        if (v + minv[dep] > N) return;  // 剪枝：估计的最小面积比给定的大是不合法的
        if (s + mins[dep] >= res) return;   // 剪枝：估计的最小面积比当前答案大
        if (s + 2*(N-v)/R[dep+1] >= res) return;    // 剪枝：数学剪枝

        if (dep == 0) {
            if (v == N) res = Math.min(res, s);
            return;
        }

        for (int r = Math.min((int)Math.sqrt(N-v), R[dep+1]-1); r >= dep; r--) {    // 剪枝：缩小上边界
            for (int h = Math.min((N-v)/r/r, H[dep+1]-1); h >= dep; h--) {
                R[dep] = r; H[dep] = h;
                int t = dep==M ? r*r : 0;   // 面积(底面积)
                dfs(dep-1, v + r*r*h, s + 2*r*h + t);
            }
        }
    }
}
```

