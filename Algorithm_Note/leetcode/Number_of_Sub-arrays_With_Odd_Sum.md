# 5457. 和为奇数的子数组数目

给你一个整数数组 arr 。请你返回和为 奇数 的子数组数目。

由于答案可能会很大，请你将结果对 10^9 + 7 取余后返回。


**示例 1：**
输入：arr = [1,3,5]
输出：4
解释：所有的子数组为 [[1],[1,3],[1,3,5],[3],[3,5],[5]] 。
所有子数组的和为 [1,4,9,3,8,5].
奇数和包括 [1,9,3,5] ，所以答案为 4 。

**示例 2：**
输入：arr = [2,4,6]
输出：0
解释：所有子数组为 [[2],[2,4],[2,4,6],[4],[4,6],[6]] 。
所有子数组和为 [2,6,12,4,10,6] 。
所有子数组和都是偶数，所以答案为 0 。

**示例 3：**
输入：arr = [100,100,99,99]
输出：4

## 数学
细品
```java
class Solution {
    int MOD = 1000000007;
    public int numOfSubarrays(int[] arr) {
        int res = 0, sum = 0;
        int odd = 0, even = 1;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            int tmp = (sum&1) == 0 ? odd : even;
            res = (res + tmp) % MOD;
            if ((sum&1) == 0) even++;
            else odd++;
        }
        return res % MOD;
    }
}
```