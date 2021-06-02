# 260. Single Number III

Given an integer array `nums`, in which exactly two elements appear only once and all the other elements appear exactly twice. Find the two elements that appear only once. You can return the answer in **any order**.

You must write an algorithm that runs in linear runtime complexity and uses only constant extra space.

**Example:**

```
Input: nums = [1,2,1,3,2,5]
Output: [3,5]
Explanation:  [5, 3] is also a valid answer.
```



**注意：**

1. 结果输出的顺序并不重要，对于上面的例子， `[5, 3]` 也是正确答案。

2. 你的算法应该具有线性时间复杂度。你能否仅使用常数空间复杂度来实现？

   

## Bit Manipulation

- 首先计算 bitmask ^= x，则 bitmask 不会保留出现两次数字的值，因为相同数字的异或值为 0。

  但是 bitmask 会保留只出现一次的两个数字（x 和 y）之间的差异。

- 我们不能直接从 bitmask 中提取 x 和 y 吗。但是我们可以用 bitmask 作为标记来分离 x 和 y。

  我们通过 bitmask & (-bitmask) 保留 bitmask 最右边的 1，这个 1 要么来自 x，要么来自 y。

- 当我们找到了 `x`，那么 `y = bitmask^x`。

 ```java
class Solution {
    public int[] singleNumber(int[] nums) {
        int bitmask = 0;
        for (int n : nums) bitmask ^= n;

        int diff = bitmask & (-bitmask);

        int x = 0;
        for (int n : nums) if ((n & diff) != 0) x ^= n;

        return new int[]{x, bitmask^x};
    }
}
 ```

