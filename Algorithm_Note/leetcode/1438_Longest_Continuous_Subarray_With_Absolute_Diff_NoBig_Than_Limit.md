# 1438. 绝对差不超过限制的最长连续子数组

给你一个整数数组 `nums` ，和一个表示限制的整数 `limit`，请你返回最长连续子数组的长度，该子数组中的任意两个元素之间的绝对差必须小于或者等于 `limit` *。*

如果不存在满足条件的子数组，则返回 `0` 。

 

**示例：**

```
输入：nums = [8,2,4,7], limit = 4
输出：2 
解释：所有子数组如下：
[8] 最大绝对差 |8-8| = 0 <= 4.
[8,2] 最大绝对差 |8-2| = 6 > 4. 
[8,2,4] 最大绝对差 |8-2| = 6 > 4.
[8,2,4,7] 最大绝对差 |8-2| = 6 > 4.
[2] 最大绝对差 |2-2| = 0 <= 4.
[2,4] 最大绝对差 |2-4| = 2 <= 4.
[2,4,7] 最大绝对差 |2-7| = 5 > 4.
[4] 最大绝对差 |4-4| = 0 <= 4.
[4,7] 最大绝对差 |4-7| = 3 <= 4.
[7] 最大绝对差 |7-7| = 0 <= 4. 
因此，满足题意的最长子数组的长度为 2 
```



## 单调队列

```java
class Solution {
    public int longestSubarray(int[] nums, int limit) {
        int[] mx = new int[nums.length], mi = new int[nums.length];
        int le = 0, ri = 0;
        int hhx = 0, ttx = -1, hhi = 0, tti = -1;
        while (ri < nums.length) {
            while (hhx<=ttx && nums[ri]>nums[mx[ttx]]) ttx--;
            while (hhi<=tti && nums[ri]<nums[mi[tti]]) tti--;
            mx[++ttx] = ri; mi[++tti] = ri;
            
            if (nums[mx[hhx]]-nums[mi[hhi]] > limit) {
                if (mx[hhx] <= le) hhx++;
                if (mi[hhi] <= le) hhi++;
                le++;
            }
            ri++;
        }
        return ri-le;
    }
}
```

