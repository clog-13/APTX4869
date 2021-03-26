# 136. 只出现一次的数字

给定一个**非空**整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。

**说明：**

你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？

**示例:**

```
输入: [4,1,2,1,2]
输出: 4
```



## 异或运算

```java
class Solution {
    public int[] singleNumbers(int[] nums) {
        int x = 0;
        for (int val : nums) x ^= val;

        int flag = x & (-x), a = 0;
        for (int val : nums) {
            if ((flag & val) != 0) {
                a ^= val;
            }
        }
        return new int[] {a, x ^ a};
    }
}
```



## 二分

```java
class Solution {
    public int[] singleNumbers(int[] nums) {
        int sum = 0, min = Integer.MAX_VALUE, max = Integer.MIN_VALUE, zeroCount = 0;
        for (int num: nums) {
            if (num == 0) zeroCount += 1;
            min = Math.min(min, num);
            max = Math.max(max, num);
            sum ^= num;
        }
        
        if (zeroCount == 1) {  // 需要特判一下某个数是0的情况。
            return new int[]{sum, 0};
        }
        int lo = min, hi = max;
        while (lo <= hi) {
            int mid = (lo + hi) >> 1;  // 根据 lo 的正负性来判断二分位置怎么写，防止越界
            int loSum = 0, hiSum = 0;
            for (int num: nums) {
                if (num <= mid) {
                    loSum ^= num;
                } else {
                    hiSum ^= num;
                }
            }
            if (loSum != 0 && hiSum != 0) {  // 两个都不为0，说明 p 和 q 分别落到2个数组里了
                return new int[] {loSum, hiSum};
            }
            if (loSum == 0) {  // 说明 p 和 q 都比 mid 大，所以比 mid 小的数的异或和变为0了
                lo = mid + 1;
            } else {  // 说明 p 和 q 都不超过 mid
                hi = mid - 1;
            }
        }
        return null;
    }
}
```

