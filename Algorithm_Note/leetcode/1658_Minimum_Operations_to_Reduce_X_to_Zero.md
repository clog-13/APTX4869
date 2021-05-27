# 1658. Minimum Operations to Reduce X to Zero

You are given an integer array `nums` and an integer `x`. In one operation, you can either remove the leftmost or the rightmost element from the array `nums` and subtract its value from `x`. Note that this **modifies** the array for future operations.

Return the **minimum number** of operations to reduce `x` to **exactly** `0` if it's possible, otherwise, return `-1`.

 

**Example:**

```
Input: nums = [3,2,20,1,1,3], x = 10
Output: 5
Explanation: The optimal solution is to remove the last three elements and the first two elements (5 operations in total) to reduce x to zero.
```

## Sliding Window

```java
class Solution {
    public int minOperations(int[] nums, int x) {
        int N = nums.length, sum = 0;
        for (int n : nums) sum += n;
        int tar = sum-x;
        if (tar < 0) return -1;
        int cur = 0, max = -1, le = 0, ri = 0;
        while (ri < N) {
            cur += nums[ri];
            while (cur > tar) cur -= nums[le++];
            if (cur==tar) max = Math.max(max, ri-le+1);
            ri++;
        }
        return max == -1 ? -1 : N-max;
    }
}
```

```java
class Solution {
    public int minOperations(int[] nums, int x) {
        int sum = Arrays.stream(nums).sum();
        int max = -1, tar = sum-x, cur = 0, le = 0, ri = 0;
        while (le < nums.length) {
            if (ri < nums.length) cur += nums[ri++];
            while (cur>tar && le<nums.length) cur -= nums[le++];
            if (cur == tar) max = Math.max(max, ri - le);
            if (ri == nums.length) le++;
        }
        return max == -1 ? -1 : nums.length-max;
    }
}
```

## PreSum + Hash

```java
class Solution {
    public int minOperations(int[] nums, int x) {
        int sum = 0, tar, res = Integer.MIN_VALUE, preSum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) sum += num;
        tar = sum - x;
        map.put(0, 0);
        for (int i = 1; i < nums.length+1; i++) {
            preSum += nums[i-1];
            if (!map.containsKey(preSum)) map.put(preSum, i);
            if (map.containsKey(preSum - tar)) {
                res = Math.max(res, i - map.get(preSum - tar));
            } 
        }
        return res == Integer.MIN_VALUE ? -1 : nums.length-res;
    }
}
```

