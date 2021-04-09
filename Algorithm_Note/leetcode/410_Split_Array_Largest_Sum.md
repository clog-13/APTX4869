# 410. 分割数组的最大值

给定一个非负整数数组 `nums` 和一个整数 `m` ，你需要将这个数组分成 `m` 个非空的连续子数组。

设计一个算法使得这 `m` 个子数组各自和的最大值最小。

 

**示例：**

```
输入：nums = [7,2,5,10,8], m = 2
输出：18
解释：
一共有四种方法将 nums 分割为 2 个子数组。 其中最好的方式是将其分为 [7,2,5] 和 [10,8] 。
因为此时这两个子数组各自的和的最大值为18，在所有情况中最小。
```



## DP

```java
class Solution {
    public int splitArray(int[] nums, int m) {
        int N = nums.length;
        int preSum[] = new int[N+1], dp[][] = new int[N+1][m+1];
        for (int i = 1; i <= N; i++) preSum[i] = preSum[i-1] + nums[i-1];

        for (int[] d : dp) Arrays.fill(d, Integer.MAX_VALUE);
        dp[0][0] = 0;  // 前 i 个数分割为 j 段所能得到的最大连续子数组和的最小值
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= Math.min(i, m); j++) {
                for (int k = 0; k < i; k++) {
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[k][j-1], preSum[i]-preSum[k]));
                }
            }
        }
        return dp[N][m];
    }
}
```



## 二分＋贪心

```java
class Solution {
    public int splitArray(int[] nums, int m) {
        int le = 0, ri = 0;
        for (int n : nums) {
            ri += n;
            if (le < n) le = n;
        }
        // ri++;  // 严谨的话应该ri++
        while (le < ri) {
            int mid = le+ri >> 1;  // 求最小可行返回值
            if (check(nums, mid, m)) {  // 左边界
                ri = mid;
            } else {
                le = mid + 1;
            }
        }
        return le;
    }

    public boolean check(int[] nums, int x, int m) {
        int sum = 0, cnt = 1;  // cnt:当前每组最大值下的组数
        for (int n : nums) {
            if (sum + n > x) {  // 每组不超过 x
                cnt++;
                sum = n;
            } else {
                sum += n;
            }
        }
        return cnt <= m;
    }
}
```



