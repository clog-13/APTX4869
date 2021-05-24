# 719. Find K-th Smallest Pair Distance

Given an integer array, return the k-th smallest **distance** among all the pairs. The distance of a pair (A, B) is defined as the absolute difference between A and B.

**Example:**

```
Input:
nums = [1,3,1]
k = 1
Output: 0 
Explanation:
Here are all the pairs:
(1,3) -> 2
(1,1) -> 0
(3,1) -> 2
Then the 1st smallest distance pair is (1,1), and its distance is 0.
```



## 二分 + 双指针

```java
class Solution {
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        int lo = 0, hi = nums[nums.length - 1] - nums[0];
        while (lo < hi) {
            int mi = lo+hi>>1, cout = 0;
            for (int le = 0, ri = 0; ri < nums.length; ri++) {
                while (nums[ri] - nums[le] > mi) le++;
                cout += ri-le;
            }
            if (cout < k) lo = mi + 1;  // cout: number of pairs with distance <= mi
            else hi = mi;  // left point
        }
        return lo;
    }
}
```



## 前缀和 + 双指针

```java
class Solution {
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        int N = nums.length, width = 2*nums[N-1];
        int[] multi = new int[N];
        for (int i = 1; i < N; i++) {
            if (nums[i] == nums[i-1]) multi[i] = multi[i-1]+1;
        }

        int[] prefix = new int[width];
        int left = 0;
        for (int i = 0; i < width; i++) {
            while (left < N && nums[left] <= i) left++;
            prefix[i] = left - 1;
        }

        int lo = 0, hi = nums[N-1];
        while (lo < hi) {
            int cout = 0, mid = lo+hi>>1;
            for (int i = 0; i < N; i++) {
                cout += prefix[nums[i] + mid] - prefix[nums[i]] + multi[i];
            }
            if (cout < k) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}
```

