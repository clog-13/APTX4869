/*
 * @lc app=leetcode.cn id=1 lang=java
 *
 * [1] 两数之和
 */

// @lc code=start
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int r1 = -1, r2 = -1;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target-nums[i])) {
                r1 = map.get(target-nums[i]);
                r2 = i;
                return new int[]{r1, r2};
            } else {
                map.put(nums[i], i);
            }
        }
        return null;
    }
}
// @lc code=end

