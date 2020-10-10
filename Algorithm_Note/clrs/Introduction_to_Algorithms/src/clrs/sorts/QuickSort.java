package clrs.sorts;

import clrs.tools.*;

import java.util.Random;

public class QuickSort {

    public static void main(String[] args) {
        for(int i = 0; i < 100; i++) {
            int[] nums = Bools.randomArray(1, 100, 20);
//            QuickSort(nums, 0, nums.length-1, true);
            Tail_Recursive_Quicksort(nums, 0, nums.length-1);
            Bools.showList(nums);

//            int[] ints = new int[nums.length*2];
//            for (int j = 0; j < nums.length; j++) {
//                int k = j*2;
//                ints[k] = nums[j];
//                ints[k+1] = nums[j];
//            }
//            QuickSort(ints, 0, ints.length-1, true);
//            Bools.showList(ints);
        }
        System.out.println("Done!");
    }

    public static void QuickSort(int[] nums, int l, int h, boolean rand) {
        if(h-l < 7) {
            InsertionSort.InsertionSort(nums);
            return;
        }
        if(l < h) {
            if(rand) {
                int r = new Random().nextInt(h)%(h-l+1) + l;    // 生成 l ~ r 间的随机数
                Bools.exchange(nums, r, h);
            }
            int tag = Partition(nums, l, h);
            QuickSort(nums, l, tag-1, rand);
            QuickSort(nums, tag+1, h, rand);

            // HP的解法相当于一开始就把中间值交换, 返回的数组和P的不同在于 P数组的中间值在“中间”， 而HP不在，以为一开始就交换了
//            int tag = Hoare_Partition(nums, l, h);
//            QuickSort(nums, l, tag, rand);        // tag不需要 -1
//            QuickSort(nums, tag+1, h, rand);

//            int[] tag = Partition_7_2(nums, l, h);
//            QuickSort(nums, l, tag[0]-1, rand);
//            QuickSort(nums, tag[1]+1, h, rand);
        }
    }

    public static void Tail_Recursive_Quicksort(int[] nums, int l, int h) {
        while (l < h) {
            int tag = Partition(nums, l, h);
            Tail_Recursive_Quicksort(nums, l, tag-1);
            l = tag+1;
        }

        // 栈深度 Θ(lgn) , 时间复杂度 Θ(nlgn)
        while(l < h) {
            int tag = Partition(nums, l, h);
            if(tag < (h-l)/2) {
                Tail_Recursive_Quicksort(nums, l, tag-1);
                l = tag+1;
            }else {
                Tail_Recursive_Quicksort(nums, tag+1, h);
                h = tag-1;
            }
        }
    }

    public static int Partition(int[] nums, int l, int h) {
        int index = l-1;
        int mid = nums[h];
        for(int i = l; i < h; i++) {
            if(nums[i] <= mid) {        // 不重复数组可以用 <
                index++;
                Bools.exchange(nums, index, i);
            }
        }
        Bools.exchange(nums, index+1, h);
        return index+1;
    }

    public static int[] Partition_7_2(int[] nums, int l, int h) {
        int m = nums[l];
        int pre = l-1;
        int end = l;
        for(int i = l+1; i <= h; i++) {
            if(nums[i] < m) {
                pre++;
                end++;
                Bools.exchange(nums, i, pre);
                Bools.exchange(nums, i, end);
            }else if(nums[i] == m) {
                end++;
                Bools.exchange(nums, i, end);
            }
        }
        return new int[] {pre+1, end};
    }

    public static int Hoare_Partition(int[] nums, int l, int h) {
        int tmp = nums[l];
        int i = l-1;
        int j = h+1;
        while(true) {
            do {
                --j;
            }while (nums[j] > tmp);
            do {
                ++i;
            }while (nums[i] < tmp);

            if(i < j)
                Bools.exchange(nums, i, j);
            else
                return j;
        }
    }
}
