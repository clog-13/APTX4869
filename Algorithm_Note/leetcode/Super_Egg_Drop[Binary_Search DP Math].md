#887.鸡蛋掉落
##题目描述
你将获得 K 个鸡蛋，并可以使用一栋从 1 到 N  共有 N 层楼的建筑。

每个蛋的功能都是一样的，如果一个蛋碎了，你就不能再把它掉下去。

你知道存在楼层 F ，满足 0 <= F <= N 任何从高于 F 的楼层落下的鸡蛋都会碎，从 F 楼层或比它低的楼层落下的鸡蛋都不会破。

每次移动，你可以取一个鸡蛋（如果你有完整的鸡蛋）并把它从任一楼层 X 扔下（满足 1 <= X <= N）。

你的目标是确切地知道 F 的值是多少。

无论 F 的初始值如何，你确定 F 的值的最小移动次数是多少？

**示例：**
输入：K = 1, N = 2
输出：2
解释：
鸡蛋从 1 楼掉落。如果它碎了，我们肯定知道 F = 0 。
否则，鸡蛋从 2 楼掉落。如果它碎了，我们肯定知道 F = 1 。
如果它没碎，那么我们肯定知道 F = 2 。
因此，在最坏的情况下我们需要移动 2 次以确定 F 是多少。

##动态规划+二分查找
我们可以考虑使用动态规划来做这道题，状态可以表示成 (K, N)，其中 K 为鸡蛋数，N 为楼层数。当我们从第 X 楼扔鸡蛋的时候：

- 如果鸡蛋不碎，那么状态变成 (K, N−X)，即我们鸡蛋的数目不变，但答案只可能在上方的 N-X 层楼了。也就是说，我们把原问题缩小成了一个规模为 (K, N-X) 的子问题；
- 如果鸡蛋碎了，那么状态变成 (K-1, X-1)，即我们少了一个鸡蛋，但我们知道答案只可能在第 X 楼下方的 X-1 层楼中了。也就是说，我们把原问题缩小成了一个规模为 (K-1, X-1) 的子问题。

这样一来，我们定义 dp(K, N) 为在状态 (K, N) 下最少需要的步数。根据以上分析我们可以列出状态转移方程：
![](../pic/Super_Egg_Drop01.png)

```java
class Solution {
	public int superEggDrop(int K, int N) {
    	return dp(K, N);
    }

    public int dp(int K, int N) {
        if(N==0) return 0;
        if(K==1) return N;
        int res = Integer.MAX_VALUE;
        for(int i = 1; i <= N; i++)
            res = Math.min(res, Math.max(dp(K-1, i-1),dp(K, N-i))+1);
        return res;
    }
}
```
**复杂度分析**
- 时间复杂度：O(K × N × N)。
- 空间复杂度：O(K × N)。

```java
二分查找优化
class Solution {
	int T;
    public int superEggDrop(int K, int N) {
    	T = N;
        return dp(K, N);
    }

    Map<Integer, Integer> memo = new HashMap();
    public int dp(int K, int N) {
        if (!memo.containsKey(N * T + K)) {
            int res;
            if (N == 0)
                res = 0;
            else if (K == 1)
                res = N;
            else {
                int le = 1, ri = N;
                while (le + 1 < ri) {
                    int mid = (le + ri) / 2;
                    int t1 = dp(K-1, mid-1);  // 计算x楼下的数据
                    int t2 = dp(K, N-mid);    // 计算x楼上的数据

                    if (t1 < t2)
                        le = mid;
                    else if (t1 > t2)
                        ri = mid;
                    else
                        le = ri = mid;
                }

                res = 1 + Math.min(Math.max(dp(K-1, le-1), dp(K, N-le)),
                				   Math.max(dp(K-1, ri-1), dp(K, N-ri)));
            }
            memo.put(N * T + K, res);
        }
        return memo.get(N * T + K);
    }
}
```
**复杂度分析**
- 时间复杂度：O(K × N × N)。 
优化：O(K × N × logN)。我们需要计算 O(K × N) 个状态，每个状态计算时需要 O(logN) 的时间进行二分搜索。
- 空间复杂度：O(K × N)。我们需要 O(K × N) 的空间存储每个状态的解。

##优化动态规划（数学法）
反过来想这个问题：如果我们可以做 T 次操作，而且有 K 个鸡蛋，那么我们能找到答案的最高的 N 是多少？我们设 f(T, K) 为在上述条件下的 N。如果我们求出了所有的 f(T, K)，那么只需要找出最小的满足 f(T, K)≥N 的 T。

那么我们如何求出 f(T, K) 呢？我们还是使用动态规划。因为我们需要找出最高的 N，因此我们不必思考到底在哪里扔这个鸡蛋，我们只需要扔出一个鸡蛋，看看到底发生了什么：

- 如果鸡蛋没有碎，那么对应的是 f(T - 1, K)，也就是说在这一层的上方可以有 f(T - 1, K) 层；
- 如果鸡蛋碎了，那么对应的是 f(T - 1, K - 1)，也就是说在这一层的下方可以有 f(T - 1， K - 1) 层。

因此我们就可以写出状态转移方程：xie

![](../pic/Super_Egg_Drop01.png)

边界条件为：当 T≥1 的时候 f(T, 1) = T，当 K≥1 时，f(1, K) = 1。

那么问题来了：T 最大可以达到多少？由于我们在进行动态规划时，T 在题目中并没有给出，那么我们需要进行到动态规划的哪一步呢？可以发现，操作次数是一定不会超过楼层数的，因此 T≤N，我们只要算出在 f(N, K) 内的所有 f 值即可。

```java
class Solution {
	public int superEggDrop(int K, int N) {
    	int[][] dp = new int[N+1][K+1];		// 储存 丢m次，j个鸡蛋 能测出的层数
        int m = 0;
        while(dp[m][K] < N) {		// 超过N层楼，得到答案跳出循环
        	m++;
            for(int j = 1; j <= K; j++)
            	// 如果鸡蛋没碎，对应的是dp(m-1, j)
				// 如果鸡蛋碎了，对应的是dp(m-1, j-1)
                // 如果用自顶向下的思维去看就是 我想要知道我丢了这次后还需要多少次才能知道答案
                // 那就是 这次碎了（dp[m-1][j-1]，然后找楼下） + 这次没碎（dp[m-1][j]， 然后找楼上）
            	dp[m][j] = dp[m-1][j] + dp[m-1][j-1] + 1;
        }
        return m;
    }
}
```
Tips:观察 dp[1][j] 和 dp[m][1] 两个边界条件
```java
空间优化
class Solution {
    public int superEggDrop(int K, int N) {
        int[] dp = new int[K+1];
        int res = 0;
        while(dp[K] < N) {
            res++;
            for(int i = K; i > 0; i--)
                dp[i] = dp[i-1] + dp[i] + 1;
        }
        return res;
    }
}
```

不过不能用 T次和N楼（在dp[][] N层楼时用 min() 求最小dp的值的下标） 去求，最主要的原因应该是边界条件无法写。

**复杂度分析**
- 时间复杂度：O(K × N)。事实上，更准确的时间复杂度应当为 O(K × T)，我们不加证明地给出 N = O(T^K)，因此有 O(K × T)=O(K × N^-K)。
- 空间复杂度：O(K × N) 
优化：O(K)

##决策单调性（？？？）
我们固定 K，随着 N 的增加，对于状态转移方程中 dp(K-1, X-1) 这一项，它的值是不变的，因为它和 NNN 无关。而对于状态转移方程中 dp(K, N-X) 这一项，随着 N 的增加，它的值也会增加。在方法一中，我们知道 dp(K-1, X-1) 随着 X 单调递增，而 dp(K, N-X) 随着 X 单调递减，那么当 N 增加时，dp(K, N-X) 对应的函数折线图在每个整数点上都是增加的。

我们可以想象一条斜率为负的直线和一条斜率为正的直线，当斜率为负的直线（类比 dp(K, N-X)）向上平移（类比 N 的增加）时，它和斜率为正的直线（类比 dp(K-1, X-1)）的交点会一直向右移动。

因此当我们固定 K 时，随着 N 的增加，dp(K, N) 对应的最优解的坐标单调递增，这样一来每个 dp(K, N) 的均摊时间复杂度为 O(1)。

```java
class Solution {
    public int superEggDrop(int K, int N) {
        int[] dp = new int[N+1];
        for (int i = 0; i <= N; i++)
            dp[i] = i;

        for (int k = 2; k <= K; k++) {
            int[] dp2 = new int[N+1];
            int x = 1;
            for (int n = 1; n <= N; n++) {
                // Increase our optimal x while we can make our answer better.
                while (x < n && Math.max(dp[x-1], dp2[n-x]) >
                				Math.max(dp[x]  , dp2[n-x-1]))
                    x++;

                dp2[n] = 1 + Math.max(dp[x-1], dp2[n-x]);
            }
            dp = dp2;
        }
        return dp[N];
    }
}
```
**复杂度分析**
- 时间复杂度：O(K × N)。我们需要计算 O(K × N) 个状态，同时对于每个 K，最优解指针只会从 0 到 N 走一次，复杂度也是 O(K × N)。因此总体复杂度为 O(K × N)。
- 空间复杂度： O(N)。因为 dp 每一层的解只依赖于上一层的解，因此我们每次只保留一层的解，需要的空间复杂度为 O(N)。