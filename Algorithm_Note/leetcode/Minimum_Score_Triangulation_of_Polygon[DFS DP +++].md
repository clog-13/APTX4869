#1039.多边形三角剖分的最低得分
给定 N，想象一个凸 N 边多边形，其顶点按顺时针顺序依次标记为 A[0], A[i], ..., A[N-1]。

假设您将多边形剖分为 N-2 个三角形。对于每个三角形，该三角形的值是顶点标记的乘积，三角剖分的分数是进行三角剖分后所有 N-2 个三角形的值之和。

返回多边形进行三角剖分后可以得到的最低分。

**示例：**
输入：[3,7,4,5]
输出：144
解释：有两种三角剖分，可能得分分别为：3×7×5 + 4×5×7 = 245，或 3×4×5 + 3×4×7 = 144。最低分数为 144。
![](../pic/Minimum_Score_Triangulation_of_Polygon03.png)

## 动态规划（区间DP）
区间DP套路，枚举所有的区间和区间内的点，区间内的点和区间左右端点将整个区间又划分为两个已知的子区间。
下图为最基础（最开始）的情况：	
![](../pic/Minimum_Score_Triangulation_of_Polygon01.png)
区间DP表格：
![](../pic/Minimum_Score_Triangulation_of_Polygon02.png)

```java
class Solution {
    public int minScoreTriangulation(int[] A) {
        int N = A.length;
        int[][] dp = new int[N][N];
        for (int len = 2; len < N; len++) {
            for (int le = 0; le < N-len; le++) {
                int ri = le + len;
                dp[le][ri] = Integer.MAX_VALUE;
                for (int k = le + 1; k < ri; k++) {		// 注意： k < ri
                    dp[le][ri] = Math.min(dp[le][ri], dp[le][k] + dp[k][ri] + A[le]*A[k]*A[ri]);
                }
            }
        }
        return dp[0][N - 1];	// 注意返回值，理解表格的意义
    }
}
```
## DFS
```java
class Solution {
    static int[][] memo;
    public int minScoreTriangulation(int[] A) {
        int N = A.length;
        memo = new int[N][N];
        return dfs(0, N-1, A);
    }

    private static int dfs(int le, int ri, int[] A) {
        if(le+1 == ri) return 0;
        int tl = 0, tr = 0;
        int res = Integer.MAX_VALUE;
        for(int i = le+1; i < ri; i++) {
            if(memo[le][i] != 0) tl = memo[le][i];
            else tl = dfs(le, i, A);

            if(memo[i][ri] != 0) tr = memo[i][ri];
            else tr = dfs(i, ri, A);

            res = Math.min(res, tl + tr + (arr[le]*arr[i]*arr[ri]));
        }
        memo[le][ri] = res;
        return res;
    }
}
```
