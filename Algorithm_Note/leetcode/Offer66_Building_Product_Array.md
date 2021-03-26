# 剑指 Offer 66. 构建乘积数组

给定一个数组 `A[0,1,…,n-1]`，请构建一个数组 `B[0,1,…,n-1]`，其中 `B[i]` 的值是数组 `A` 中除了下标 `i` 以外的元素的积, 即 `B[i]=A[0]×A[1]×…×A[i-1]×A[i+1]×…×A[n-1]`。不能使用除法。

 

**示例:**

```
输入: [1,2,3,4,5]
输出: [120,60,40,30,24]
```



## 线性代数

```
0, 1, 1, 1
1, 0, 1, 1
1, 1, 0, 1
1, 1, 1, 0
```

```java
class Solution {
    public int[] constructArr(int[] a) {
    int N = a.length;
    int[] res = new int[N];
    for (int i = 0, pre = 1; i < N; i++) {  // 从左往右累乘
        res[i] = pre;
        pre *= a[i];
    }
    for (int i = N-1, pre = 1; i >= 0; i--) {  // 从右往左累乘
        res[i] *= pre;
        pre *= a[i];
    }
    return res;
    }
}
```

