package clrs.sorts;

import clrs.tools.Bools;

public class InsertionSort {

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++) {
            int[] nums = Bools.randomArray(1, 50, 20);
            InsertionSort(nums);
            Bools.showList(nums);
        }
    }

    public static void InsertionSort(int[] nums) {
        for(int i = 1; i < nums.length; i++) {
            int tmp = nums[i];
            int j = i-1;
            while (j >= 0 && nums[j] > tmp) {
                nums[j+1] = nums[j];
                j--;
            }
            nums[j+1] = tmp;
        }
    }

    public static void InsertionSort(int[] nums, int lo, int hi) {
        for(int i = lo+1; i < hi; i++) {
            int tmp = nums[i];
            int j = i-1;
            while (j >= lo && nums[j] > tmp) {
                nums[j+1] = nums[j];
                j--;
            }
            nums[j+1] = tmp;
        }
    }

}
