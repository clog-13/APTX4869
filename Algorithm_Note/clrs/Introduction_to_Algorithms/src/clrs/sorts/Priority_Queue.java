package clrs.sorts;

import clrs.tools.Bools;

import java.util.Random;

public class Priority_Queue {

    public static void main(String[] args) {
        Priority_Queue pq = new Priority_Queue();
        for(int i = 0; i < 15; i++) {
            pq.Max_Heap_Insert(new Random().nextInt(50)+1);
        }
        for(int i = 0; i < 15; i++) {
            System.out.print(pq.Heap_Extract_Max() + ", ");
        }
        System.out.println();
    }

    private int[] nums = new int[100];
    private int size = 0;

    public Priority_Queue() { }

    public int Heap_Max() {
        if(nums.length < 1) return -1;
        return nums[0];
    }

    public int Heap_Extract_Max() {
        if(nums.length < 1) return -1;
        int res = nums[0];
        nums[0] = nums[size];
        size--;
        HeapSort.Max_Heapify(nums, 0, size);
        return res;
    }

    public void Heap_Increase_Key(int i, int key) {
        if(key < nums[i]) return;
        nums[i] = key;
        while(i > 0 && nums[HeapSort.Parent(i)] < nums[i]) {
            Bools.exchange(nums, i, HeapSort.Parent(i));
            i = HeapSort.Parent(i);
        }
    }

    public void Max_Heap_Insert(int key) {
        size++;
        nums[size] = Integer.MIN_VALUE;
        Heap_Increase_Key(size, key);
    }
}
