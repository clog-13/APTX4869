# 442. Find All Duplicates in an Array

Given an integer array `nums` of length `n` where all the integers of `nums` are in the range `[1, n]` and each integer appears **once** or **twice**, return *an array of all the integers that appears **twice***.

You must write an algorithm that runs in `O(n) `time and uses only constant extra space.

 

**Example 1:**

```
Input: nums = [4,3,2,7,8,2,3,1]
Output: [2,3]
```



## Flag

```java
class Solution {
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();
        for (int n : nums) {
            int temp = Math.abs(n);
            if (nums[temp-1] < 0) {
                res.add(temp);
            } else {
                nums[temp-1] *= -1;
            }
        }
        return res; 
    }
}
```



## IQ + Math

```java
class Solution {
    public List<Integer> findDuplicates(int[] nums) {
        int N = nums.length;
        for (int num : nums) {
            int x = (num - 1) % N;
            nums[x] += N;
        }
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            if (nums[i] > 2*N) res.add(i + 1);
        }
        return res;
    }
}