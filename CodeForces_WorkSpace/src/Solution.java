import java.util.*;

class Solution {
    public static void main(String[] args) {
        double dd = 15.0;
        System.out.println(dd/60.0);
    }

    public int minOperations(int[] nums) {
        int res = 0, max = 0;
        for (int num : nums) {
            max = Math.max(max, num);
            res += Integer.bitCount(num);   // 二进制有几个 1
        }
        res += Integer.toBinaryString(max).length() - 1;
        return res;
    }
}