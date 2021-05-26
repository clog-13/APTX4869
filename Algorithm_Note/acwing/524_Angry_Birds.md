# 524. 愤怒的小鸟

有一架弹弓位于 (0,0) 处，每次 Kiana 可以用它向第一象限发射一只红色的小鸟， 小鸟们的飞行轨迹均为形如 y=ax2+bx 的曲线，其中 a,b 是 Kiana 指定的参数，且必须满足 a<0。

当小鸟落回地面（即 x 轴）时，它就会瞬间消失。

在游戏的某个关卡里，平面的第一象限中有 n 只绿色的小猪，其中第 ii 只小猪所在的坐标为 (xi,yi)。 

如果某只小鸟的飞行轨迹经过了 (xi, yi)，那么第 i 只小猪就会被消灭掉，同时小鸟将会沿着原先的轨迹继续飞行； 

如果一只小鸟的飞行轨迹没有经过 (xi, yi)，那么这只小鸟飞行的全过程就不会对第 i 只小猪产生任何影响。 

例如，若两只小猪分别位于 (1,3) 和 (3,3)，Kiana 可以选择发射一只飞行轨迹为 y=−x2+4x 的小鸟，这样两只小猪就会被这只小鸟一起消灭。 

而这个游戏的目的，就是通过发射小鸟消灭所有的小猪。 

这款神奇游戏的每个关卡对 Kiana 来说都很难，所以 Kiana 还输入了一些神秘的指令，使得自己能更轻松地完成这个这个游戏。   

这些指令将在输入格式中详述。 

假设这款游戏一共有 T 个关卡，现在 Kiana 想知道，对于每一个关卡，至少需要发射多少只小鸟才能消灭所有的小猪。  



#### 输入格式

第一行包含一个正整数 T，表示游戏的关卡总数。

下面依次输入这 T 个关卡的信息。

每个关卡第一行包含两个非负整数 n,m，分别表示该关卡中的小猪数量和 Kiana 输入的神秘指令类型**(无用)**。

接下来的 n 行中，第 i 行包含两个正实数 (xi,yi)，表示第 i 只小猪坐标为 (xi,yi)，数据保证同一个关卡中不存在两只坐标完全相同的小猪。

保证 1≤n≤18，0≤m≤2，0<xi,yi<10，输入中的实数均保留到小数点后两位。

#### 输出格式

对每个关卡依次输出一行答案。

输出的每一行包含一个正整数，表示相应的关卡中，消灭所有小猪最少需要的小鸟数量。

#### 输入样例：

```
2
2 0
1.00 3.00
3.00 3.00
5 2
1.00 5.00
2.00 8.00
3.00 9.00
4.00 8.00
5.00 5.00
```

#### 输出样例：

```
1
1
```



## 状压DP+线性DP

```java
import java.util.*;

public class Main {
    int N, M, maxN = 18, maxM = 1 << 18;
    Node[] arr = new Node[maxN];
    int[][] path = new int[maxN][maxN];
    double esp = 1e-8;
    int[] f = new int[maxM];

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T -- > 0) {
            N = sc.nextInt(); M = sc.nextInt();
            for (int i = 0; i < N; i ++) {
                double x = sc.nextDouble(), y = sc.nextDouble();
                arr[i] = new Node(x, y);
            }

            for (int i = 0; i < N; i++) Arrays.fill(path[i], 0);
            for (int i = 0; i < N; i++) {
                path[i][i] = 1 << i;  // path[i][j]表示i号点和j号点对应的抛物线的状态(肯定包含i,j)
                for (int j = 0; j < N; j++) {
                    double x1 = arr[i].x, y1 = arr[i].y;
                    double x2 = arr[j].x, y2 = arr[j].y;
                    if (cmp(x1, x2) == 0) continue;

                    // 求方程中的 a系数 和 b系数
                    double a = (y1 / x1 - y2 / x2) / (x1 - x2);
                    double b = y1 / x1 - a * x1;
                    if (cmp(a, 0) >= 0) continue;

                    int state = 0;
                    for (int k = 0; k < N; k++) {  // 判断哪些点在该抛物线上
                        double x = arr[k].x, y = arr[k].y;
                        if (cmp(a*x*x + b*x, y) == 0) state |= 1<<k;
                    }
                    path[i][j] = state;  
                }
            }

            Arrays.fill(f, 0x3f3f3f3f);
            f[0] = 0;  // f[st] 表示达到 st 状态 的点需要多少条抛物线
            for (int st = 0; st < 1<<N; st++) {
                int x = 0;  
                for (int i = 0; i < N; i++) {  // 找到当前状态的 任意一个未覆盖的点
                    if ((st>>i & 1) == 0) {
                        x = i;
                        break;
                    }
                }
                // 遍历所有与 x 有关的抛弧线(加一条抛弧线)， 或者已经包含x点和当前状态的最小花费
                for (int i = 0; i < N; i++) {  // 枚举所有与x号点相关的抛弧线(再加一只鸟-抛弧线)
                    f[st | path[x][i]] = Math.min(f[st | path[x][i]], f[st] + 1);
                }
            }
            System.out.println(f[(1<<N) - 1]);
        }
    }

    int cmp(double a, double b) {
        if (Math.abs(a-b) < esp) return 0;
        if (a > b) return 1;
        return -1;
    }

    static class Node {
        double x, y;
        
        public Node(double xx, double yy) {
            x = xx; y = yy;
        }
    }
}
```

