import java.util.*;
class Solution {
    public static void main(String[] args) {
        Solution sol = new Solution();
//        for (int i = 0; i <= 10000_0000; i++) {
//            int d = i - sol.helper(i);

//        }


//        System.out.println(Integer.MIN_VALUE);
        int[] ar = new int[34];
        ar[0] = 43;
        ar[1] = 3;
        ar[2] = 4;
        Arrays.sort(ar, 0,3);
        System.out.println();
    }

    public int countNicePairs(int[] nums) {
        int[] rev = new int[nums.length];
        long[] diff = new long[nums.length];

        for (int i = 0; i < nums.length; i++) {
            rev[i] = helper(nums[i]);
            diff[i] = (long)nums[i] - (long)rev[i];
            // n_diff[i] = (long)num[i] - (long)res[i];
        }

        Map<Long, Integer> map = new HashMap<>();
        int res = 0;
        for (int i = nums.length-1; i >= 0; i--) {
            int tarCnt = map.getOrDefault(diff[i], 0);
            res = (res+tarCnt) % 10000_00009;
            map.put(diff[i], tarCnt+1);
        }
        return res;
    }

    int helper(int n) {
        int res = 0;
        while (n>0) {
            res = res*10+n%10;
            n/=10;
        }
        return res;
    }
}