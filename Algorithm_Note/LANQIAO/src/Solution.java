import java.util.*;

class Solution {

    public static void main(String[] args) {
        new Solution().canDistribute(new int[]{1,1,2,3}, new int[]{2,2});
    }

    public boolean canDistribute(int[] nums, int[] quantity) {
        int[] cout = new int[1001];
        for (int n : nums) cout[n]++;
        Arrays.sort(cout);
        int[] couts = Arrays.copyOfRange(cout, cout.length - 50, cout.length);
        Arrays.sort(quantity);
        return backTrack(couts, quantity, quantity.length - 1);

    }

    public boolean backTrack(int[] couts, int[] quantity, int idx) {
        if (idx < 0) return true;
        for (int i = 0; i < couts.length; i++) {
            if (i != 0 && couts[i] == couts[i - 1]) continue; // 剪枝
            if (couts[i] >= quantity[idx]) {
                couts[i] -= quantity[idx];
                if (backTrack(couts, quantity, idx - 1)) return true;
                couts[i] += quantity[idx];
            }
        }
        return false;
    }
}