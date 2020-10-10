# 1043. 分隔数组以得到最大和

给出整数数组 A，将该数组分隔为长度最多为 K 的几个（连续）子数组。分隔完成后，每个子数组的中的值都会变为该子数组中的最大值。

返回给定数组完成分隔后的最大和。

**示例：**
输入：A = [1,15,7,9,2,5,10], K = 3
输出：84
解释：A 变为 [15,15,15,9,10,10,10]

## DP
```java
class Solution {
    public int maxSumAfterPartitioning(int[] A, int K) {
        int N = A.length;
        int[] dp = new int[N+1];
        for (int i = 1; i <= N; i++) {
            int j = i - 1, max = 0;
            while (j >= 0 && (i-j) <= K) {
                max = Math.max(max, A[j]);
                dp[i] = Math.max(dp[i], dp[j]+max*(i-j));
                j--;
            }
        }
        return dp[N];
    }
}
```
```java
时间优化（对于K特别大的情况）
class Solution {
    public int maxSumAfterPartitioning(int[] A, int K) {
        int max = -1, N = A.length;
        int[] dp = new int[N];
        for (int i = 0; i < K; i++) {
            if (A[i] > max) max = A[i]; 
            dp[i] = max * (i+1);
        }
        for (int i = K; i < N; i++) {
            max = 0;
            for(int j = 0; j < K; j++) {
                max = Math.max(max, A[i-j]);
                dp[i] = Math.max(dp[i], dp[i-j-1] + (j+1)*max);
            }
        }
        return dp[N-1];
    }
}
```