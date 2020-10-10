package clrs.sorts;

import clrs.tools.Bools;

public class CountingSort {

    public static void main(String[] args) {
        for(int i = 0; i < 100; i++) {
            int[] nums = Bools.randomArray(1, 100, 15);
            int[] res = CountingSort(nums);
            Bools.showList(res);
        }
    }

    public static int[] CountingSort(int[] nums) {
        int max = Integer.MIN_VALUE;
        for(int i : nums)
            max = Math.max(max, i);

        int[] res = new int[nums.length];
        int[] cs = new int[max+1];
        for(int i : nums)
            cs[i]++;
        for(int i = 1; i < cs.length; i++)
            cs[i] += cs[i-1];

//        for(int i = nums.length-1; i >= 0; i--) {
//            res[cs[nums[i]]-1] = nums[i];
//            cs[nums[i]]--;
//        }
        for(int i : nums) {
            res[--cs[i]] = i;
//            res[cs[i] - 1] = i;
//            cs[i]--;
        }

        return res;
    }
}
