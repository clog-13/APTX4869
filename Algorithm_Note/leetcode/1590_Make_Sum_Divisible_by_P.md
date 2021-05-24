# 1590. Make Sum Divisible by P

Given an array of positive integers `nums`, remove the **smallest** subarray (possibly **empty**) such that the **sum** of the remaining elements is divisible by `p`. It is **not** allowed to remove the whole array.

Return *the length of the smallest subarray that you need to remove, or* `-1` *if it's impossible*.

A **subarray** is defined as a contiguous block of elements in the array.

 

**Example:**

```
Input: nums = [3,1,4,2], p = 6
Output: 1
Explanation: The sum of the elements in nums is 10, which is not divisible by 6. We can remove the subarray [4], and the sum of the remaining elements is 6, which is divisible by 6.
```

## 前缀 + HashMap

假设 nums 的和除以 P，余数是 mod，

如果 mod == 0，答案就是 0。

**如果 mod != 0，答案变成了找原数组中的最短连续子数组，使得其数字和除以 P，余数也是 mod。**

```java
class Solution {
    public int minSubarray(int[] nums, int p) {
        int mod = 0, N = nums.length;
        for (int n : nums) mod = (mod + n) % p;
        if (mod == 0) return 0;

        Map<Integer, Integer> map = new HashMap<>(); 
        map.put(0, -1);  // mod - 0 = mod (cur - tar = mod)
        int res = N, curMod = 0;
        for (int i = 0; i < N; i++) {
            curMod = (curMod + nums[i]) % p;  // curMod - tarMod = mod (这里运算省略除余)
            int tarMod = (curMod - mod + p) % p;  // 等于mod的这段 就是要去除的
            if (map.containsKey(tarMod)) {  // 找到一段，对 P 的余数是 tarMod
                res = Math.min(res, i - map.get(tarMod));
                if (res == 1) return res;
            }
            map.put(curMod, i);
        }
        return res==N ? -1 : res;
    }
}
```

