class Solution {
    public static void main(String[] args) {
        new Solution().longestSubarray(new int[]{7,6,5,4,3,8,1}, 2);
    }

    public int longestSubarray(int[] nums, int limit) {
        int[] maxq = new int[nums.length], minq = new int[nums.length];
        int hh1 = 0, tt1 = -1, hh2 = 0, tt2 = -1;
        int le = 0,ri = 0;
        while (ri < nums.length) {
            while (hh1 <= tt1 && nums[ri] > nums[maxq[tt1]]) tt1--;  // 递减(最大在前)
            while (hh2 <= tt2 && nums[ri] < nums[minq[tt2]]) tt2--;  // 递增(最小在前)

            maxq[++tt1] = ri; minq[++tt2] = ri;

            if (nums[maxq[hh1]]-nums[minq[hh2]] > limit){
                while (le >= maxq[hh1]) hh1++;
                while (le >= minq[hh2]) hh2++;
                le++;
            }

            ri++;
        }
        return ri - le;
    }
}