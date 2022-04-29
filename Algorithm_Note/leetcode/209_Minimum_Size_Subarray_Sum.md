# 209. Minimum Size Subarray Sum

Given an array of positive integers `nums` and a positive integer `target`, return the minimal length of a **contiguous subarray** `[numsl, numsl+1, ..., numsr-1, numsr]` of which the sum is greater than or equal to `target`. If there is no such subarray, return `0` instead.

 

**Example 1:**

```
Input: target = 7, nums = [2,3,1,2,4,3]
Output: 2
Explanation: The subarray [4,3] has the minimal length under the problem constraint.
```

**Example 2:**

```
Input: target = 4, nums = [1,4,4]
Output: 1
```

**Example 3:**

```
Input: target = 11, nums = [1,1,1,1,1,1,1,1]
Output: 0
```



## PreSum + BinarySearch

```java
class Solution {
    public int minSubArrayLen(int s, int[] nums) {
        int N = nums.length;
        if (N == 0) return 0;
        int res = Integer.MAX_VALUE;
        int[] preSum = new int[N + 1]; 
        for (int i = 1; i <= N; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }
        for (int i = 1; i <= N; i++) {
            int tar = s + preSum[i - 1];
            int idx = Arrays.binarySearch(preSum, tar);
            if (idx < 0) idx = -idx - 1;
            if (idx <= N) {
                res = Math.min(res, idx - (i - 1));
            }
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }
}
```



## 滑动窗口

```java
class Solution {
    public int minSubArrayLen(int s, int[] nums) {
        int N = nums.length;
        if (N == 0) return 0;
        int res = Integer.MAX_VALUE, start = 0, end = 0, sum = 0;
        while (end < N) {
            sum += nums[end];
            while (sum >= s) {
                res = Math.min(res, end - start + 1);
                sum -= nums[start];
                start++;
            }
            end++;
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }
}
```

