# 1749. Maximum Absolute Sum of Any Subarray

You are given an integer array `nums`. The **absolute sum** of a subarray `[numsl, numsl+1, ..., numsr-1, numsr]` is `abs(numsl + numsl+1 + ... + numsr-1 + numsr)`.

Return *the **maximum** absolute sum of any **(possibly empty)** subarray of* `nums`.

Note that `abs(x)` is defined as follows:

- If `x` is a negative integer, then `abs(x) = -x`.
- If `x` is a non-negative integer, then `abs(x) = x`.

 **Example 1:**

```
Input: nums = [1,-3,2,3,-4]
Output: 5
Explanation: The subarray [2,3] has absolute sum = abs(2+3) = abs(5) = 5.
```

**Example 2:**

```
Input: nums = [2,-5,1,-4,3,-2]
Output: 8
Explanation: The subarray [-5,1,-4] has absolute sum = abs(-5+1-4) = abs(-8) = 8.
```



## DP

```java
class Solution {
    public static int maxAbsoluteSum(int[] nums) {
        int res = 0, max = 0, min = 0;
        for (int n : nums) {
            max = Math.max(max+n, n);
            min = Math.min(min+n, n);

            res = Math.max(res, Math.max(max, Math.abs(min)));
        }
        return res;
    }
}
```



## 前缀和

即 找到最大的 preSum[i] - preSum[j]

因为不考虑正负 所以可以看成 前缀数组 最低点到最高点 或 最高点到最低点

```java
class Solution {
    public static int maxAbsoluteSum(int[] nums) {
        int[] preSum = new int[nums.length+1];
        for (int i = 1; i <= nums.length; i++) {
            preSum[i] = preSum[i-1] + nums[i-1];
        }
        Arrays.sort(preSum);
        return preSum[nums.length] - preSum[0];
    }
}
```

```java
class Solution {
    public int maxAbsoluteSum(int[] nums) {
        int max = 0, min = 0, sum = 0;
        for (int n : nums) {
            sum += n;
            max = Math.max(max, sum);
            min = Math.min(min, sum);
        }
        return max - min;
    }
}
```

