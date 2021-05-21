# 1186. Maximum Subarray Sum with One Deletion
Given an array of integers, return the maximum sum for a non-empty subarray (contiguous elements) with at most one element deletion. In other words, you want to choose a subarray and optionally delete one element from it so that there is still at least one element left and the sum of the remaining elements is maximum possible.

Note that the subarray needs to be non-empty after deleting one element.

 

**Example 1:**
```
Input: arr = [1,-2,0,3]
Output: 4
Explanation: Because we can choose [1, -2, 0, 3] and drop -2, thus the subarray [1, 0, 3] becomes the maximum value.
```

## 动态规划

```java
class Solution {
    public int maximumSum(int[] arr) {
        int N = arr.length;
        int[][] f = new int[2][N];
        f[1][0] = arr[0]; f[0][0] = -10010;  // 不可能事件
        for (int i = 1; i < N; i++) {
            // f[1][i]: 前i个中没删过的连续数组最大值
            // f[0][i]: 前i个中已经删除过一次的最大值
            f[1][i] = Math.max(f[1][i-1], 0) + arr[i];  // arr[i]必选
            f[0][i] = Math.max(f[0][i-1]+arr[i], f[1][i-1]);
        }

        int res = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) res = Math.max(res, Math.max(f[0][i], f[1][i]));
        return res;
    }
}
```

