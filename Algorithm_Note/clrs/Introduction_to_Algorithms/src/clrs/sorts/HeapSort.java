package clrs.sorts;

import clrs.tools.Bools;

import java.util.PriorityQueue;

public class HeapSort {

    public static void main(String[] args) {
        PriorityQueue<Integer> pq;
        for(int i = 0; i < 10; i++) {
            int[] nums = Bools.randomArray(1, 50, 15);
            HeapSort(nums);
            Bools.showList(nums);
        }
    }

    public static void HeapSort(int[] nums) {
        int size = nums.length;
        Build_Max_Heap(nums);   // 生成最大堆, 但还不是排序状态
//        for(int n : nums) System.out.print(n+", ");
        for(int i = nums.length-1; i >= 0; i--) {   // 取出一个然后调用 M_H 调整保持最大堆
            Bools.exchange(nums, 0, i);
            Max_Heapify(nums, 0, --size);
        }
    }

    public static void Max_Heapify(int[] nums, int i, int size) {
        int li = Left(i);
        int ri = Right(i);
        int max = i;
        if(li < size && nums[li] > nums[i]) {
            max = li;
        }

        if(ri < size && nums[ri] > nums[max]) {
            max = ri;
        }

        if(max != i) {
            Bools.exchange(nums, max, i);
            Max_Heapify(nums, max, size);
        }
    }

    public static void Build_Max_Heap(int[] nums) {
        for(int i = nums.length/2 - 1; i >= 0; i--)
            Max_Heapify(nums, i, nums.length);
    }

    public static int Parent(int i) {return i/2; }

    public static int Left(int i) {return i*2+1; }

    public static int Right(int i) {return i*2+2; }
}
