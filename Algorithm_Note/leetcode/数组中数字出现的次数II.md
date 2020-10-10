# LeetCode刷题笔记 137. 只出现一次的数字 II
给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现了三次。找出那个只出现了一次的元素。

说明：
算法应该具有线性时间复杂度。 可以不使用额外空间来实现吗？

**示例:**
输入: [0,1,0,1,0,1,99]
输出: 99

## 状态机
```java
class Solution {
    public int singleNumber(int[] nums) {
        int one = 0, two = 0, three;	// 最后要返回 one， 因为其他数字
        for (int i : nums) {
        	two |= (one & i);	// 把出现两次的数 推到 two（如果一个数之前已经出现了两次，one中此数的状态在之前会被清除， 这时就不会再 推到 two，不过 推到 也没事）
            one ^= i;			// 一次的数状态记录（非第一次出现的会被清除，所以要先记录 two）
            three = (one & two)；// 出现三次的就是已经出现了 2 + 1 次的数

			// 初略理解： 那些在one，two中保存的状态如果在three中也出现了， & ~three 会导致这种状态清零
            // 即 把出现三次的数丢弃
            one &= ~three;		// 更新完 three 后反过来根据 three 更新 one 和 two
            two &= ~three;		// 调换one，two顺序不影响结果
        }
        return one;

        // int ones = 0, twos = 0;
        // for (int i : nums) {
        //     ones = (ones ^ i) & ~twos;
        //     twos = (twos ^ i) & ~ones;
        // }
        // return ones;
    }
}
```
## 利用Integer性质， 桶计数
```java
class Solution {
    public int singleNumber(int[] nums) {
        int res = 0;
        for (int i = 0; i < 32; i++) {
            int mask = 1 << i;  // 1, 10, 100, 1000, ...
            int cnt = 0;
            for (int j = 0; j < nums.length; j++) {
                if ((nums[j] & mask) != 0) {
                    cnt++;
                }
            }
            if (cnt % 3 != 0) {
                res |= mask;
            }
        }
        return res;
    }
}

```