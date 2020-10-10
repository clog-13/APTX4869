package clrs.mor;

import clrs.sorts.InsertionSort;
import clrs.tools.Bools;

import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.stream.DoubleStream;

import static clrs.sorts.QuickSort.Partition;

public class MidAndOrder {

    public static void main(String[] args) {
        int[] nums = Bools.randomArray(1, 100, 100);

//        int[] res = findMaxAndMin(nums);
//        System.out.println(res[0]+", "+res[1]);

//        System.out.println(SecondMin(nums));

//        for (int i = 0; i < 50; i++) {
//            System.out.println(Randomized_Select(nums, 0, nums.length - 1, 24));
//        }

        for (int i = 0; i < 50; i++) {
            System.out.println(Select(nums, 0, nums.length - 1, 53));
        }
    }
    // Ο[ 3(n/2) ]
    public static int[] findMaxAndMin(int[] arr) {
        int max, min;
        int i;
        if(arr.length % 2 == 0) {
            max = Math.max(arr[0], arr[1]);
            min = arr[0] + arr[1] - max;
            i = 2;
        }else {
            max = arr[0];
            min = arr[0];
            i = 1;
        }

        for( ; i < arr.length-1; i++) {
            int tMax = Math.max(arr[i], arr[i+1]);
            int tMin = arr[i] + arr[i+1] - tMax;

            max = Math.max(max, tMax);
            min = Math.min(min, tMin);
        }

        return new int[] {max, min};
    }

    // Ο[ n + lgn - 2]
    public static int SecondMin(int[] arr) {
        int mi = 0;
        for(int i = 1; i < arr.length; i++) {
            if(arr[i] < arr[mi]) mi = i;
        }
        arr[mi] = Integer.MAX_VALUE;

        int len = arr.length;
        int idx = 0;
        while (len > 2) {
            for(int i = 0; i < len; i+=2) {
                if(i < len-1) {
                    arr[idx++] = Math.min(arr[i], arr[i+1]);
                }else arr[idx] = arr[i];
            }
            len = len/2 + len%2;
            idx=0;
        }
        return Math.min(arr[0], arr[1]);
    }

    // Θ[ n ]
    public static int Randomized_Select(int[] arr, int l, int h, int t) {
        if(l == h) return arr[l];

//        int r = new Random().nextInt(h)%(h-l+1) + l;    // 生成 l ~ r 间的随机数
//        Bools.exchange(arr, r, h);
        int tag = Partition(arr, l, h);

        int rank = tag - l + 1;
        if(t == rank) return arr[tag];
        if(t < rank)
            return Randomized_Select(arr, l, tag-1, t);
        else
            return Randomized_Select(arr, tag+1, h, t-rank);

// ------ 循环算法
//        int tag, r;
//        while(l < h) {
//            r = new Random().nextInt(h)%(h-l+1) + l;    // 生成 l ~ r 间的随机数
//            Bools.exchange(arr, r, h);
//            tag = Partition(arr, l, h);
//
//            int rank = tag - l + 1;
//            if(t == rank) return arr[tag];
//            if(t < rank) h = tag-1;
//            else {
//                l = tag+1;
//                t -= rank;
//            }
//        }
//        return arr[l];

    }

    public static int Select(int[] arr, int lo, int hi, int tar) {
        if(lo == hi) return arr[lo];

        int median = FindMedians(arr, lo, hi);
        int tag = Select_Partition(arr, lo, hi, median);

        int k = tag - lo + 1;
        if(k == tar) return arr[tag];
        if(k > tar)
            return Select(arr, lo, tag-1, tar);
        else
            return Select(arr, tag+1, hi, tar-k);
    }

    private static int FindMedians(int[] arr, int le, int ri) {
        int len = ri - le + 1;
        int[] midList = new int[len/5 + 1];

        int sta = 0, end;
        int groups = 0;
        for(int i = le; i < len; i++) {
            if(i % 5 == 0)
                sta = le + i;
            if ((i+1)%5 == 0 || i == len-1) {
                end = le + i;
                InsertionSort.InsertionSort(arr, sta, end);
                int ret = arr[(end-sta)/2 + sta];
                midList[groups++] = ret;
            }
        }

        int p = midList.length/2 - 1 + midList.length%2;
        return Select(midList, 0, midList.length-1, p);
//        return res;
//        InsertionSort.InsertionSort(midList);
//        return midList[p];
    }

    private static int Select_Partition(int[] arr, int lo, int hi, int median) {
        for(int i = lo; i < hi; i++) {
            if(arr[i] == median) {
                Bools.exchange(arr, i, hi);
                break;
            }
        }

        return Partition(arr, lo, hi);
    }
}
