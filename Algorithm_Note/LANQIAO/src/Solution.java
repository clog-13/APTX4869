class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
            sol.maxAbsoluteSum(new int[]{1,-3,2,3,-4});
    }

    public int maxAbsoluteSum(int[] nums) {
        int[] arr = new int[nums.length];
        int idx = 0, flag = nums[0]>=0 ? 1 : -1;
        for (int n : nums) {
            if (n*flag < 0 || n<0&&flag>0) {
                flag = flag>0? -1 : 1;
                idx++;
            }
            arr[idx] += n;
        }

        int[] preSum = new int[idx+2];
        for (int i = 1; i <= idx+1; i++) {
            preSum[i] = preSum[i-1] + nums[i-1];
        }
        int le = 0, ri = idx, res = 0;
        while (le <= ri) {
            int sum = preSum[ri+1] - preSum[le];
            res = Math.max(res, Math.abs(sum));
            if (sum >= 0) {
                if (nums[le]*nums[ri] >= 0) {
                    if (nums[le] < nums[ri]) {
                        le++;
                    } else {
                        ri--;
                    }
                } else {
                    if (nums[le] < 0) {
                        le++;
                    } else {
                        ri--;
                    }
                }
            } else {
                if (nums[le]*nums[ri] >= 0) {
                    if (nums[le] < nums[ri]) {
                        ri--;
                    } else {
                        le++;
                    }
                } else {
                    if (nums[le] < 0) {
                        ri--;
                    } else {
                        le++;
                    }
                }
            }
        }
        return res;
    }
}