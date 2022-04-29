# 912. Sort an Array

Given an array of integers `nums`, sort the array in ascending order.

 

**Example 1:**

```
Input: nums = [5,2,3,1]
Output: [1,2,3,5]
```

**Example 2:**

```
Input: nums = [5,1,1,2,0,0]
Output: [0,0,1,1,2,5]
```



## Quick Sort

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



## Heap Sort

```java
class Solution {
    public int[] sortArray(int[] nums) {
        heapSort(nums);
        return nums;
    }

    void heapSort(int[] nums) {
        int len = nums.length - 1;
        for (int i = len / 2; i >= 0; --i) {  // 非叶(but !!!
            maxHeapify(nums, i, len);
        }
        for (int i = len; i >= 1; --i) {  // 逆序轮流
            swap(nums, i, 0);
            len -= 1;
            maxHeapify(nums, 0, len);
        }
    }

    void maxHeapify(int[] nums, int i, int len) {
        while ((i << 1) + 1 <= len) {
            int lson = (i << 1) + 1;
            int rson = (i << 1) + 2;
            int large = i;  // !!!
            if (lson <= len && nums[lson] > nums[large]) {
                large = lson;
            }
            if (rson <= len && nums[rson] > nums[large]) {
                large = rson;
            }

            if (large != i) {
                swap(nums, i, large);
                i = large;
            } else {
                break;  // !!!
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



## Merge Sort

```java
class Solution {
    int[] tmp;

    public int[] sortArray(int[] nums) {
        tmp = new int[nums.length];  // !!!
        mergeSort(nums, 0, nums.length - 1);
        return nums;
    }

    public void mergeSort(int[] nums, int l, int r) {
        if (l >= r) return;  // !!!

        int mid = (l + r) >> 1;
        mergeSort(nums, l, mid);
        mergeSort(nums, mid + 1, r);
        int i = l, j = mid + 1;
        int cnt = 0;
        while (i <= mid && j <= r) {
            if (nums[i] <= nums[j]) {
                tmp[cnt++] = nums[i++];
            } else {
                tmp[cnt++] = nums[j++];
            }
        }
        while (i <= mid) tmp[cnt++] = nums[i++]; // !!!
        while (j <= r) tmp[cnt++] = nums[j++];   // !!!

        for (int k = 0; k < r - l + 1; ++k) {  // !!!
            nums[k + l] = tmp[k];
        }
    }
}
```

