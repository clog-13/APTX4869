# 410. Split Array Largest Sum

Given an array `nums` which consists of non-negative integers and an integer `m`, you can split the array into `m` non-empty continuous subarrays.

Write an algorithm to minimize the largest sum among these `m` subarrays.

 

**Example 1:**

```
Input: nums = [7,2,5,10,8], m = 2
Output: 18
Explanation:
There are four ways to split nums into two subarrays.
The best way is to split it into [7,2,5] and [10,8],
where the largest sum among the two subarrays is only 18.
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

```go
func splitArray(nums []int, m int) int {
    N := len(nums)
    dp := make([][]int, N+1)
    preSum := make([]int, N+1)
    for i := 0; i < len(dp); i++ {
        dp[i] = make([]int, m+1)
        for j := 0; j < m+1; j++ {
            dp[i][j] = math.MaxInt32
        }
    }

    for i := 0; i < N; i++ {
        preSum[i+1] = preSum[i] + nums[i]
    }
    dp[0][0] = 0 // dp[i][j] 表示将数组的前 i 个数分割为 j 段所能得到的最小值。
    for i := 1; i <= N; i++ {
        for j := 1; j <= min(i, m); j++ {
            for k := 0; k < i; k++ {
                dp[i][j] = min(dp[i][j], max(dp[k][j - 1], preSum[i] - preSum[k]))
            }
        }
    }
    return dp[N][m]
}

func min(x, y int) int {
    if x < y {
        return x
    }
    return y
}

func max(x, y int) int {
    if x > y {
        return x
    }
    return y
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



```go
func splitArray(nums []int, m int) int {
    le, ri := 0, 0
    for i := 0; i < len(nums); i++ {
        ri += nums[i]
        if le < nums[i] {
            le = nums[i]
        }
    }
    for le < ri {
        mid := (ri - le) / 2 + le
        if check(nums, mid, m) {
            ri = mid
        } else {
            le = mid + 1
        }
    }
    return le
}

func check(nums []int, x, m int) bool {
    sum, cnt := 0, 1
    for i := 0; i < len(nums); i++ {
        if sum + nums[i] > x {
            cnt++
            sum = nums[i]
        } else {
            sum += nums[i]
        }
    }
    return cnt <= m
}
```



