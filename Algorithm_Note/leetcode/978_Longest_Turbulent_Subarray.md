# 978. 最长湍流子数组

当 `A` 的子数组 `A[i], A[i+1], ..., A[j]` 满足下列条件时，我们称其为*湍流子数组*：

- 若 `i <= k < j`，当 `k` 为奇数时， `A[k] > A[k+1]`，且当 `k` 为偶数时，`A[k] < A[k+1]`；
- **或** 若 `i <= k < j`，当 `k` 为偶数时，`A[k] > A[k+1]` ，且当 `k` 为奇数时， `A[k] < A[k+1]`。

也就是说，如果比较符号在子数组中的每个相邻元素对之间翻转，则该子数组是湍流子数组。

返回 `A` 的最大湍流子数组的**长度**。

 

**示例：**

```
输入：[9,4,2,10,7,8,8,1,9]
输出：5
解释：(A[1] > A[2] < A[3] > A[4] < A[5])
```



## 滑动窗口（双指针）

```java
class Solution {
    public int maxTurbulenceSize(int[] arr) {
        int res = 1, le = 0, ri = 0;
        while (ri < arr.length-1) {
            if (le == ri) {
                if (arr[le] == arr[le+1]) le++;
                ri++;
            } else {
                if (arr[ri-1]>arr[ri] && arr[ri]<arr[ri+1]) {
                    ri++;
                } else if (arr[ri-1]<arr[ri] && arr[ri]>arr[ri+1]) {
                    ri++;
                } else {
                    le = ri;
                }
            }
            res=Math.max(res, ri-le+1);
        }
        return res;
    }
}
```



## DP

```java
class Solution {
    public int maxTurbulenceSize(int[] arr) {
        if (arr.length==1) return 1; 
        int[][] dp = new int[2][arr.length+1];
        int a = 1, b= 1;

        int res = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i-1]) {
                a += 1; b = 1;
                res = Math.max(res, a);
            } else if (arr[i] > arr[i-1]) {
                a = 1; b += 1;
                res = Math.max(res, b);
            } else {
                a = 1; b = 1;
            }
            int tmp = a;
            a = b;
            b = tmp;
        }
        
        return res;
    }
}
```

