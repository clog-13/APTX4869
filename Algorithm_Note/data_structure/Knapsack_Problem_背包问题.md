# Knapsack Problem 背包问题



## 5. 多重背包问题 II（二进制优化）

有 N 种物品和一个容量是 V 的背包。

第 i 种物品最多有 si 件，每件体积是 vi，价值是 wi。

求解将哪些物品装入背包，可使物品体积总和不超过背包容量，且价值总和最大。
输出最大价值。

**输入格式**

第一行两个整数，N，V，用空格隔开，分别表示物品种数和背包容积。

接下来有 N 行，每行三个整数 vi,wi,si，用空格隔开，分别表示第 i 种物品的体积、价值和数量。

**输出格式**

输出一个整数，表示最大价值。

**数据范围**

0<N≤1000, 0<V≤2000, 0<vi,wi,si≤2000

**输入样例**

```
4 5
1 2 3
2 4 1
3 4 3
4 5 2
```

**输出样例：**

```
10
```

```java
import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), S = sc.nextInt();
        int[] wgts = new int[200002], vals = new int[200002];
        int idx = 0;
        for (int s = 0; s < N;s++) {
            int size = sc.nextInt();
            int val = sc.nextInt();
            int cout = sc.nextInt();
            
            // 这里比较直观的取了二进制的每个1,(1, 10, 100, 1000, ...)
            for (int k = 1; k < cout; k <<= 1) {
                wgts[idx] = k*size;
                vals[idx] = k*val;
                cout -= k;
                idx++;
            }
            // cout:[0, 上面前n-1个数的和]
            // 可以通过二进制的“图像性质”理解，或者记住 cout <= k
            if (cout > 0) {
                wgts[idx] = cout*size;
                vals[idx] = cout*val;
                idx++;
            }
        }
        
        // 01背包模板
        int[] dp = new int[V+1];
        int res  = 0;
        for (int i =0; i < idx; i++) {  // 注意这里是 idx
            for (int s = S; s >= wgts[i]; s--) {
                dp[s] = Math.max(dp[s], dp[s-wgts[i]]+vals[i]);
                res = Math.max(res, dp[s]);
            }
        }
        // 朴素完全背包代码 O(n^3), 二进制优化(n^2*logS)
        // for (int s = S; s >= size; s--) {  
        //     for (int c = 0; c<=cout && c*size<=s; c++) {
        //         dp[s] = Math.max(dp[s], dp[s - c*size] + c*val);
        //     }
        // }
        System.out.println(res);
    }
}
```



## 6. 多重背包问题 III（单调队列优化）

有 N 种物品和一个容量是 V 的背包。

第 i 种物品最多有 si 件，每件体积是 vi，价值是 wi。

求解将哪些物品装入背包，可使物品体积总和不超过背包容量，且价值总和最大。
输出最大价值。

**输入格式**

第一行两个整数，N，V (0<N≤1000，0<V≤20000)，用空格隔开，分别表示物品种数和背包容积。

接下来有 N 行，每行三个整数 vi,wi,si，用空格隔开，分别表示第 i 种物品的体积、价值和数量。

**输出格式**

输出一个整数，表示最大价值。

**数据范围**

0<N≤1000, 0<V≤20000, 0<vi,wi,si≤20000

**输入样例**

```
4 5
1 2 3
2 4 1
3 4 3
4 5 2
```

**输出样例：**

```
10
```



**思路**

```
我们令 dp[j] 表示容量为j的情况下，获得的最大价值
那么，针对每一类物品 i ，我们都更新一下 dp[m] --> dp[0] 的值，最后 dp[m] 就是一个全局最优值

dp[m] = max(dp[m], dp[m-v] + w, dp[m-2*v] + 2*w, dp[m-3*v] + 3*w, ...)

接下来，我们把 dp[0] --> dp[m] 写成下面这种形式
dp[0], dp[v],   dp[2*v],   dp[3*v],   ... , dp[k*v]
dp[1], dp[v+1], dp[2*v+1], dp[3*v+1], ... , dp[k*v+1]
dp[2], dp[v+2], dp[2*v+2], dp[3*v+2], ... , dp[k*v+2]
...
dp[j], dp[v+j], dp[2*v+j], dp[3*v+j], ... , dp[k*v+j]
```
```
dp[k*v+j] 只依赖于 { dp[j], dp[v+j], dp[2*v+j], dp[3*v+j], ... , dp[k*v+j] }

所以，我们可以得到
dp[j]    =     dp[j]
dp[j+v]  = max(dp[j] +  w,  dp[j+v])
dp[j+2v] = max(dp[j] + 2w,  dp[j+v] +  w, dp[j+2v])
dp[j+3v] = max(dp[j] + 3w,  dp[j+v] + 2w, dp[j+2v] + w, dp[j+3v])
...
但是，这个队列中前面的数，每次都会增加一个 w ，所以我们需要做一些转换
```

```
dp[j]    =     dp[j]
dp[j+v]  = max(dp[j], dp[j+v] - w) + w
dp[j+2v] = max(dp[j], dp[j+v] - w, dp[j+2v] - 2w) + 2w
dp[j+3v] = max(dp[j], dp[j+v] - w, dp[j+2v] - 2w, dp[j+3v] - 3w) + 3w
...
这样，每次入队的值是 dp[j+k*v] - k*w
```

**代码**

```java
import java.util.*;
class Main {
    static int N, M, maxN = 200010;
    static int[] dp = new int[maxN];
    static Node[] queue = new Node[maxN];
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); M = sc.nextInt();
        
        for (int i = 0; i <= M; i++) queue[i] = new Node(0, 0);
        for (int i = 0; i < N; i++) {
            int size = sc.nextInt(), val = sc.nextInt(), cnt = sc.nextInt();
            
            for (int j = 0; j < size; j++) {  // mod
                int head = 0, tail = 0, stop = (M-j)/size;
                for (int k = 0; k <= stop; k++) {  // 当前物品 选几个 的方案
                    
                    int t = dp[j + k*size] - k*val;  // 最大的值放在head
                    while (tail > head && t >= queue[tail-1].val) tail--;  
                    queue[tail].pos = k; queue[tail++].val = t;
                    
                    if (queue[head].pos < k-cnt) head++;  // 当前物品最多 cnt 个
                    dp[j + k*size] = queue[head].val + k*val;
                }
            }
        }
        System.out.println(dp[M]);
    }
    static class Node {
        int val,pos;
        public Node(int p, int v){
            pos = p; val = v;
        }
    }
}
```



## 532. 货币系统

在货币系统 n=3, a=[2,5,9] 中，金额 1,31,3 就无法被表示出来。 

两个货币系统 (n,a) 和 (m,b) 是等价的，
当且仅当对于任意非负整数 x，它要么均可以被两个货币系统表出，要么不能被其中任何一个表出。 

现在网友们打算简化一下货币系统。

他们希望找到一个货币系统 (m,b)，满足 (m,b) 与原来的货币系统 (n,a) 等价，且 m 尽可能的小。

他们希望你来协助完成这个艰巨的任务：找到最小的 m。

**输入格式**

输入文件的第一行包含一个整数 T，表示数据的组数。

接下来按照如下格式分别给出 T 组数据。 

每组数据的第一行包含一个正整数 n。

接下来一行包含 n 个由空格隔开的正整数 a[i]。

**输出格式**

输出文件共有 T 行，对于每组数据，输出一行一个正整数，表示所有与 (n,a) 等价的货币系统 (m,b) 中，最小的 m。

**数据范围**

1≤n≤100, 1≤a[i]≤25000, 1≤T≤20

**输入样例：**

```
3
4 
3 19 10 6 
5 
11 29 13 19 17 
3
2 5 9
```

**输出样例：**

```
2
5
2
```

```java
// 当且仅当一张货币面额可以被其他货币表示时，此货币对该系统能表示的面额没有影响
// 换言之这个货币没有存在的必要，所以将这类的货币去除就可以得到等价的最小数量货币系统。
public class Main {
    static int INF = 0x3f3f3f3f;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- > 0) {
            int N =sc.nextInt();
            int[] arr = new int[N+1];
            int[] dp = new int[25010];
            dp[0] = 1;
            int m = 0;
            for (int i = 1; i <= N; i++) {
                arr[i] = sc.nextInt();
                m = Math.max(m, arr[i]);
            }

            for (int i = 1; i <= N; i++) {
                for (int j = arr[i]; j <= m; j++) {
                    dp[j] += dp[j-arr[i]];
                }
            }

            int res = 0;
            for (int i = 1; i <= N; i++) {
                if(dp[arr[i]] == 1) res++;
            }
            System.out.println(res);
        }
    }
}

```





## 8. 二维费用的背包问题

有 N 件物品和一个容量是 V 的背包，背包能承受的最大重量是 M。

每件物品只能用一次。体积是 vi，重量是 mi，价值是 wi。

求解将哪些物品装入背包，可使物品总体积不超过背包容量，总重量不超过背包可承受的最大重量，且价值总和最大。

输出最大价值。

**输入格式**

第一行两个整数，N，V,MN，V,M，用空格隔开，分别表示物品件数、背包容积和背包可承受的最大重量。

接下来有 NN 行，每行三个整数 vi,mi,wivi,mi,wi，用空格隔开，分别表示第 ii 件物品的体积、重量和价值。

**输出格式**
输出一个整数，表示最大价值。

**数据范围**
0<N≤1000, 0<V,M≤100, 0<vi,mi≤100, 0<wi≤1000

**输入样例**

```
4 5 6
1 2 3
2 4 4
3 4 5
4 5 6
```

**输出样例：**

```
8
```

```java
import java.util.*;
class Main {
    public static void main(String[] args) {
        Scanner sc=  new Scanner(System.in);;
        int N = sc.nextInt(), S = sc.nextInt(), M = sc.nextInt();
        
        int[][] dp = new int[S+1][M+1];
        for (int i = 0; i < N; i++) {
            int size = sc.nextInt(), wei = sc.nextInt(), val = sc.nextInt();
            for (int s = S; s >= size; s--) {
                for (int m = M; m >= wei; m--) {
                    dp[s][m] = Math.max(dp[s][m], dp[s-size][m-wei]+val);
                }
            }
        }
        System.out.println(dp[S][M]);
    }
}
```



## 1022. 宠物小精灵之收服

对于每一个野生小精灵而言，小智可能需要使用很多个精灵球才能收服它，而在收服过程中，野生小精灵也会对皮卡丘造成一定的伤害（从而减少皮卡丘的体力）。

当皮卡丘的体力小于等于0时，小智就必须结束狩猎（因为他需要给皮卡丘疗伤），而使得皮卡丘体力小于等于0的野生小精灵也不会被小智收服。

当小智的精灵球用完时，狩猎也宣告结束。

我们假设小智遇到野生小精灵时有两个选择：收服它，或者离开它。

如果小智选择了收服，那么一定会扔出能够收服该小精灵的精灵球，而皮卡丘也一定会受到相应的伤害；如果选择离开它，那么小智不会损失精灵球，皮卡丘也不会损失体力。

小智的目标有两个：主要目标是收服尽可能多的野生小精灵；如果可以收服的小精灵数量一样，小智希望皮卡丘受到的伤害越小（剩余体力越大），因为他们还要继续冒险。

**输入格式**

输入数据的第一行包含三个整数：N，M，K，分别代表小智的精灵球数量、皮卡丘初始的体力值、野生小精灵的数量。

之后的K行，每一行代表一个野生小精灵，包括两个整数：收服该小精灵需要的精灵球的数量，以及收服过程中对皮卡丘造成的伤害。

**输出格式**

输出为一行，包含两个整数：C，R，分别表示最多收服C个小精灵，以及收服C个小精灵时皮卡丘的剩余体力值最多为R。

**数据范围**

0<N≤1000,0<M≤500,0<K≤100

**输入样例：**

```
10 100 5
7 10
2 40
2 50
1 20
4 20
```

**输出样例：**

```
3 30
```

 ```java
import java.util.*;
class Main {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        int C = sc.nextInt(), S = sc.nextInt(), N = sc.nextInt();
        int[][] dp = new int[C+1][S+1];
        for (int i = 0; i < N; i++) {
            int cnt = sc.nextInt(), size = sc.nextInt();
            for (int c = C; c >= cnt; c--) {
                for (int s = S-1; s >= size; s--) {
                    dp[c][s] = Math.max(dp[c][s], dp[c-cnt][s-size]+1);
                }
            }
        }
        
        int k = S-1;
        while (k > 0 && dp[C][k-1] == dp[C][S-1]) k--;
        System.out.println(dp[C][S-1] + " "+ (S-k));
    }
}
 ```



## 734. 能量石

岩石怪物杜达生活在魔法森林中，他在午餐时收集了 N 块能量石准备开吃。

由于他的嘴很小，所以一次只能吃一块能量石。

能量石很硬，吃完需要花不少时间。

吃完第 i 块能量石需要花费的时间为 Si 秒。

杜达靠吃能量石来获取能量。

不同的能量石包含的能量可能不同。

此外，能量石会随着时间流逝逐渐失去能量。

第 i 块能量石最初包含 Ei 单位的能量，并且每秒将失去 Li 单位的能量。

当杜达开始吃一块能量石时，他就会立即获得该能量石所含的全部能量（无论实际吃完该石头需要多少时间）。

能量石中包含的能量最多降低至 0。

请问杜达通过吃能量石可以获得的最大能量是多少？

**输入格式**

第一行包含整数 T，表示共有 T 组测试数据。

每组数据第一行包含整数 N，表示能量石的数量。

接下来 N 行，每行包含三个整数 Si,Ei,Li。

**输出格式**

每组数据输出一个结果，每个结果占一行。

结果表示为 `Case #x: y`，其中 x 是组别编号（从 11 开始），y 是可以获得的最大能量值。

**数据范围**

1≤T≤10, 1≤N≤100, 1≤Si≤100, 1≤Ei≤105, 0≤Li≤105

**输入样例：**

```
3
4
20 10 1
5 30 5
100 30 1
5 80 60
3
10 4 1000
10 3 1000
10 8 1000
2
12 300 50
5 200 0
```

**输出样例：**

```
Case #1: 105
Case #2: 8
Case #3: 500
```

**思路**

交换后两个宝石的贡献总和不会变得更大，即（假设之前的总时间为t ）：

Ei−t∗Li+Ej−(t+Si)∗Lj >= Ej−t∗Lj+Ei−(t+Sj)∗Li

整理后：

Si∗Lj<=Sj∗Li

```java
import java.util.*;
class Main {
    static int maxN = 105, maxS = 10005;
    static Node[] arr = new Node[maxN];
    static int[] dp = new int[maxS];
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for (int t = 1; t <= T; t++) {
            int N = sc.nextInt(), sum = 0;
            for (int i = 1; i <= N; i++) {
                int s = sc.nextInt();
                int e = sc.nextInt();
                int l = sc.nextInt();
                sum += s;
                arr[i] = new Node(s, e, l);
            }
            
            // 升序
            // 必须排序，因为不仅要考虑 选哪些，还要考虑 选的顺序。
            // 普通 01背包 用作比较的值是 “所选组合的 总和”， 
            // 而这题用作比较的值是 “所选组合的 吃法最大值”，需要排序后才能确定。
            // 假设 i, j 吃法比 j, i 吃法 能得到最大值，则必须是满足 Si*Lj <= Sj*Li
            // 同时因为等式恒等性， 若Si*Lj <= Sj*Li，则 i, j的吃法值 必定大于 j, i 的吃法值
            Arrays.sort(arr, 1, N+1, (v1, v2) -> (v1.s*v2.l - v2.s*v1.l));

            Arrays.fill(dp, 0xcfcfcfcf);  // 0xcf == -0x3f
            dp[0] = 0;  // dp[j]: j时刻的最大值
            for (int i = 1; i <= N; i++) {  // 循环已排序的每个宝石
                for (int j = sum; j >= arr[i].s; j--) {  // 循环时刻，最大为吃所有宝石的总和
                    dp[j] = Math.max(dp[j], dp[j-arr[i].s] + arr[i].e-(j-arr[i].s)*arr[i].l);
                }
            }
            
            int res = 0;
            for (int i = 1; i <= sum; i++) res = Math.max(res, dp[i]);
            System.out.println("Case #"+t+": "+res);
        }
    }
    
    static class Node {
        int s, e, l;
        public Node(int ss, int ee, int ll) {
            s = ss; e = ee; l = ll;
        }
    }
}
```

