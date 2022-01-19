# 912. Sort an Array

Given an array of integers `nums`, sort the array in ascending order.

 

**Example 1:**

```
Input: nums = [5,2,3,1]
Output: [1,2,3,5]
```



## QuickSort

```java
class Solution {
    public int[] sortArray(int[] nums) {
        sort(nums, 0, nums.length - 1);
        return nums;
    }

    void sort(int[] nums, int le, int ri) {
        if (le < ri) {
            int pos = partition(nums, le, ri);
            sort(nums, le, pos - 1);
            sort(nums, pos + 1, ri);
        }
    }

    int partition(int[] nums, int le, int ri) {
        int i = new Random().nextInt(ri - le + 1) + le;
        swap(nums, ri, i);  // random
        
        int pivot = nums[ri];  // ri
        i = le - 1;
        for (int j = le; j <= ri-1; j++) {
            if (nums[j] <= pivot) {
                swap(nums, ++i, j);
            }
        }

        swap(nums, i + 1, ri);
        return i + 1;
    }

    void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```



## HeapSort

```java
class Solution {
    public int[] sortArray(int[] nums) {
        heapSort(nums);
        return nums;
    }

    void heapSort(int[] nums) {
        int len = nums.length - 1;
        for (int i = len / 2; i >= 0; i--) {  // 非叶向上
            sift(nums, i, len);
        }
        for (int i = len; i > 0; i--) {  // 叶向上
            swap(nums, i, 0);
            len -= 1;
            sift(nums, 0, len);
        }
    }

    void sift(int[] nums, int i, int len) {
        for ( ; (i<<1)+1 <= len; ) {
            int large = i, lson = (i<<1)+1, rson = (i<<1)+2;
            if (lson <= len && nums[lson] > nums[i]) {
                large = lson;
            }
            if (rson <= len && nums[rson] > nums[large]) {
                large = rson;
            }
            if (large != i) {
                swap(nums, i, large);
                i = large;
            } else {
                break;
            }
        }
    }

    void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```



## MergeSort

```java
class Solution {
    int[] tmp;

    public int[] sortArray(int[] nums) {
        tmp = new int[nums.length];
        mergeSort(nums, 0, nums.length - 1);
        return nums;
    }

    public void mergeSort(int[] nums, int le, int ri) {
        if (le >= ri) return;
        int mi = (le + ri) >> 1;
        mergeSort(nums, le, mi);
        mergeSort(nums, mi + 1, ri);
        
        int cnt = 0, i = le, j = mi + 1;
        while (i <= mi && j <= ri) {
            if (nums[i] <= nums[j]) {
                tmp[cnt++] = nums[i++];
            } else {
                tmp[cnt++] = nums[j++];
            }
        }
        while (i <= mi) tmp[cnt++] = nums[i++];
        while (j <= ri) tmp[cnt++] = nums[j++];
        for (int k = 0; k < ri - le + 1; ++k) {
            nums[le + k] = tmp[k];
        }
    }
}
```

