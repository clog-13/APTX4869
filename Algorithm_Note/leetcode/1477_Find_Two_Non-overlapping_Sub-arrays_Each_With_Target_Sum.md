# 1477. Find Two Non-overlapping Sub-arrays Each With Target Sum
Given an array of integers arr and an integer target.

You have to find two non-overlapping sub-arrays of arr each with a sum equal target. There can be multiple answers so you have to find an answer where the sum of the lengths of the two sub-arrays is minimum.

Return the minimum sum of the lengths of the two required sub-arrays, or return -1 if you cannot find such two sub-arrays.

**Example:**
```
Input: arr = [3,2,2,4,3], target = 3
Output: 2
Explanation: Only two sub-arrays have sum = 3 ([3] and [3]). The sum of their lengths is 2.
```


## DP + 双指针

```java
class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int sum = 0, le = 0, ri = 0;
        int[] dp = new int[arr.length+1];   // dp[i]: i之前的符合tar的最短长度 或没找到(INF)
        dp[0] = 100002;
        int res = 100002;
        while (ri < arr.length) {
            sum += arr[ri++];
            while (sum > target) sum -= arr[le++];
            if (sum == target) {
                if (ri-le + dp[le] < res) res = ri-le + dp[le];
                dp[ri] = Math.min(dp[ri-1], ri-le);
            } else dp[ri] = dp[ri-1];
        }
        if (res == 100002) return -1;
        return res;
    }
}
```



## DP + 哈希表

```java
import java.util.*;

class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int sum = 0, res = 1000002;
        Map<Integer, Integer> map = new HashMap<>();	// 前缀和的最近下标
        map.put(0, 0);
        int[] dp = new int[arr.length+1];	// dp[i]:i之前的符合tar的最短长度 或之前都没有(INF)
        dp[0] = 1000002;
        for (int i = 1; i <= arr.length; i++) {
            sum += arr[i-1];
            int tmp = sum - target;
            if (map.containsKey(tmp)) {	// 找到了一个
                int le = map.get(tmp);
                int len = i - le;
                res = Math.min(res, dp[le] + len);
                dp[i] = Math.min(dp[i-1], len);
            } else dp[i] = dp[i-1];
            map.put(sum, i);
        }
        return res == 1000002 ? -1 : res;
    }
}
```