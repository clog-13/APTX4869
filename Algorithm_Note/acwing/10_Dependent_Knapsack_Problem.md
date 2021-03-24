# 10. 有依赖的背包问题

有 N 个物品和一个容量是 V 的背包。

物品之间具有依赖关系，且依赖关系组成一棵树的形状。如果选择一个物品，则必须选择它的父节点。

如果选择物品5，则必须选择物品1和2。这是因为2是5的父节点，1是2的父节点。

每件物品的编号是 i，体积是 vi，价值是 wi，依赖的父节点编号是 pi。物品的下标范围是 1…Na。

求解将哪些物品装入背包，可使物品总体积不超过背包容量，且总价值最大。

输出最大价值。

#### 输入格式

第一行有两个整数 N，V，用空格隔开，分别表示物品个数和背包容量。

接下来有 N 行数据，每行数据表示一个物品。
第 i 行有三个整数 vi,wi,pi，用空格隔开，分别表示物品的体积、价值和依赖的物品编号。
如果 pi=−1，表示根节点。 **数据保证所有物品构成一棵树。**

#### 输出格式

输出一个整数，表示最大价值。

#### 数据范围

1≤N,V≤100, 1≤vi,wi≤100

父节点编号范围：

- 内部结点：1≤pi≤N;
- 根节点：pi=−1;

#### 输入样例

```
5 7
2 3 -1
2 2 1
3 5 1
4 7 2
3 6 2
```

#### 输出样例：

```
11
```

 ```java
import java.util.*;

class Main {
    static int idx = 0, N, S, maxN = 110;
    static int[] vals = new int[maxN], size = new int[maxN];
    static int[][] dp = new int[maxN][maxN];
    static int[] info = new int[maxN], from = new int[maxN], to = new int[maxN];
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); S = sc.nextInt();
        
        int root = 0;
        Arrays.fill(info, -1);
        for (int i = 1; i <= N; i++) {
            size[i] = sc.nextInt(); vals[i] = sc.nextInt();
            int p  = sc.nextInt();
            if (p == -1) root = i;
            else add(p, i);
        }
        
        dfs(root);  // dp[i][j]: i节点，容积 j 时最大价值(且当前节点默认选择)
        System.out.println(dp[root][S]);
    }
    
    static void dfs(int u) {
        for (int i = info[u]; i != -1; i = from[i]) {  // 遍历当前节点的子节点
            int son = to[i];
            dfs(son);  // 先递归到叶子节点dp，然后再dp父节点
            // 遍历背包的容积,当前节点我们默认选择
            // 我们每一次都默认选择当前结点，因为到最后根节点是必选的(除非一个都不选)
            for (int j = S-size[u]; j >= 0; j--) {  // 01
                for (int k = j; k >= 0; k--) {  // 子节点
                    dp[u][j] = Math.max(dp[u][j], dp[u][j-k] + dp[son][k]);
                }
            }
        }
        // 加上刚刚默认选择的父节点价值, 这段代码位置很难移动
        for (int i = S; i >= size[u]; i--) {
            dp[u][i] = dp[u][i-size[u]] + vals[u];
        }
        // 因为当前节点我们默认选择
        // 所以如果背包容积不如当前物品的体积大,那就不能选择当前结点及其子节点,因此为零 
        for (int i = size[u]-1; i >= 0; i--) {
            dp[u][i] = 0;
        }
    }
    
    static void add(int a, int b) {
        from[idx] = info[a];
        to[idx] = b;
        info[a] = idx++;
    }
}
 ```

```java
import java.util.*;

class Main {
    static int idx = 0, N, S, maxN = 110;
    static int[] vals = new int[maxN], size = new int[maxN];
    static int[][] dp = new int[maxN][maxN];
    static List<Integer>[] list = new List[maxN];
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); S = sc.nextInt();
        
        for (int i = 1; i <= N; i++) list[i] = new ArrayList<>();
        int root = 0;
        for (int i = 1; i <= N; i++) {
            size[i] = sc.nextInt(); vals[i] = sc.nextInt();
            int p = sc.nextInt();
            if (p == -1) root = i;
            else list[p].add(i);
        }

        dfs(root);
        System.out.println(dp[root][S]);
    }
    
    static void dfs(int u) {
        for (int i = size[u]; i <= S; i++) dp[u][i] = vals[u];  // 节点 u 必须选
        
        for (int i = 0; i < list[u].size(); i++) {
            int t = list[u].get(i);
            dfs(t);
            for (int s = S; s >= size[u]; s--) {  // 01背包
                for (int k = 0; k <= s-size[u]; k++) {  // 可选的子节点
                    dp[u][s] = Math.max(dp[u][s], dp[u][s-k]+dp[t][k]);
                }
            }
        }
    }
}
```

