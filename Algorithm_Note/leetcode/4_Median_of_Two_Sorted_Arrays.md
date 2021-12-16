# 4. Median of Two Sorted Arrays

Given two sorted arrays `nums1` and `nums2` of size `m` and `n` respectively, return **the median** of the two sorted arrays.

The overall run time complexity should be `O(log (m+n))`.

 

**Example 1:**

```
Input: nums1 = [1,3], nums2 = [2]
Output: 2.00000
Explanation: merged array = [1,2,3] and median is 2.
```

**Example 2:**

```
Input: nums1 = [], nums2 = [1]
Output: 1.00000
```



## Binary

```java
class Solution {
  public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int le = (m+n+1)/2, ri = (m+n+2)/2;  // m+n 为奇数的话， (m+n+1) / 2 和 (m+n+2) / 2 的值相等
        return (findKth(nums1, 0, nums2, 0, le) + findKth(nums1, 0, nums2, 0, ri)) / 2.0;
    }

    // i: nums1的起始位置 j: nums2的起始位置
    public int findKth(int[] nums1, int i, int[] nums2, int j, int k) {
        if (i >= nums1.length) return nums2[j+k-1];  // nums1为空数组
        if (j >= nums2.length) return nums1[i+k-1];  // nums2为空数组
        if (k == 1) return Math.min(nums1[i], nums2[j]);
        
        int midVal1 = (i+k/2-1 < nums1.length) ? nums1[i+k/2-1] : Integer.MAX_VALUE;
        int midVal2 = (j+k/2-1 < nums2.length) ? nums2[j+k/2-1] : Integer.MAX_VALUE;
        if (midVal1 < midVal2) {
            return findKth(nums1, i+k/2, nums2, j , k - k/2);
        } else {
            return findKth(nums1, i, nums2, j+k/2 , k - k/2);
        }        
    }
}
```



## Math

```c++
class Solution {
public:
    double findMedianSortedArrays(vector<int>& nums1, vector<int>& nums2) {
        if (nums1.size() > nums2.size()) return findMedianSortedArrays(nums2, nums1);
        
        int m = nums1.size(), n = nums2.size();
        int le = 0, ri = m, m1 = 0, m2 = 0;

        // 1.L_Len == R_Len 2.L_MAX <= R_MIN
        while (le <= ri) {  
            int i = (le+ri)/2, j = (m+n+1)/2 - i;
            int nums_im1 = (i==0 ? INT_MIN : nums1[i-1]);  // nums1[i-1]
            int nums_jm1 = (j==0 ? INT_MIN : nums2[j-1]);  // nums2[j-1]
            int nums_i = (i==m ? INT_MAX : nums1[i]);  // nums1[i]
            int nums_j = (j==n ? INT_MAX : nums2[j]);  // nums2[j]

            if (nums_im1 <= nums_j) {
                m1 = max(nums_im1, nums_jm1);
                m2 = min(nums_i, nums_j);
                le = i + 1;
            } else {
                ri = i - 1;
            }
        }

        return (m+n)%2==0 ? (m1+m2)/2.0 : m1;
    }
};
```





