# 378. Kth Smallest Element in a Sorted Matrix

Given an `n x n` `matrix` where each of the rows and columns are sorted in ascending order, return *the* `kth` *smallest element in the matrix*.

Note that it is the `kth` smallest element **in the sorted order**, not the `kth` **distinct** element.

 

**Example:**
```
Input: matrix = [[1,5,9],[10,11,13],[12,13,15]], k = 8
Output: 13
Explanation: The elements in the matrix are [1,5,9,10,11,12,13,13,15], and the 8th smallest number is 13
```


## 二分

```java
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int row = matrix.length, col = matrix[0].length;
        int le = matrix[0][0], ri = matrix[row-1][col-1];
        while (le < ri) {  // 二分，“迭代加深”
            int mid = le+ri>>1, cnt = 0;  // 统计比mid等小的数量
            int i = row-1, j = 0;  // 左下角
            while (i >= 0 && j < col) {  // 右上角
                if (matrix[i][j] <= mid) {
                    cnt += i+1;  // 行高
                    j++;  // 向右一列
                } else i--;  // 向上一行
            }
            if (cnt < k) le = mid+1;
            else ri = mid;  // 左边界, mid-cnt >= k
        }
        return le;
    }
}
```

