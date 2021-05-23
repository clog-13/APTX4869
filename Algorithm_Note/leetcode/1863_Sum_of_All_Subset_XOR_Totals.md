# 1863. Sum of All Subset XOR Totals

The **XOR total** of an array is defined as the bitwise `XOR` of **all its elements**, or `0` if the array is **empty**.

- For example, the **XOR total** of the array `[2,5,6]` is `2 XOR 5 XOR 6 = 1`.

Given an array `nums`, return *the **sum** of all **XOR totals** for every **subset** of* `nums`. 

**Note:** Subsets with the **same** elements should be counted **multiple** times.

An array `a` is a **subset** of an array `b` if `a` can be obtained from `b` by deleting some (possibly zero) elements of `b`.

 

**Example:**

```
Input: nums = [5,1,6]
Output: 28
Explanation: The 8 subsets of [5,1,6] are:
- The empty subset has an XOR total of 0.
- [5] has an XOR total of 5.
- [1] has an XOR total of 1.
- [6] has an XOR total of 6.
- [5,1] has an XOR total of 5 XOR 1 = 4.
- [5,6] has an XOR total of 5 XOR 6 = 3.
- [1,6] has an XOR total of 1 XOR 6 = 7.
- [5,1,6] has an XOR total of 5 XOR 1 XOR 6 = 2.
0 + 5 + 1 + 6 + 4 + 3 + 7 + 2 = 28
```



## State Compress

```java
class Solution {
    public int subsetXORSum(int[] nums) {
        int res = 0;
        for (int st = 0; st < (1<<nums.length); st++) {
            int tmp = 0;
            for (int i = 0; i < nums.length; i++) {
                if ((st>>i & 1) == 1) tmp ^= nums[i];
            }
            res += tmp;
        }
        return res;
    }
}
```



## BFS

```java
class Solution {
    private int res = 0;

    public int subsetXORSum(int[] nums) {
        dfs(nums, 0, 0);
        return res;
    }

    private void dfs(int[] nums, int idx, int sum) {
        if (idx == nums.length) {
            res += sum;
            return ;
        }

        dfs(nums, idx+1, sum^nums[idx]);  // choose
        dfs(nums, idx+1, sum);  // not choose
    }
}
```



## Computer Science

```
class Solution {
    public int subsetXORSum(int[] nums) {
        int res = 0;
        for (int n : nums) res |= n;
        return res << (nums.length - 1);
    }
}
```