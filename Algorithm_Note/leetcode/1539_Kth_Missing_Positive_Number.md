# 1539. 第 k 个缺失的正整数

给你一个 **严格升序排列** 的正整数数组 `arr` 和一个整数 `k` 。

请你找到这个数组里第 `k` 个缺失的正整数。

 

**示例 1：**

```
输入：arr = [2,3,4,7,11], k = 5
输出：9
解释：缺失的正整数包括 [1,5,6,8,9,10,12,13,...] 。第 5 个缺失的正整数为 9 。
```

**示例 2：**

```
输入：arr = [1,2,3,4], k = 2
输出：6
解释：缺失的正整数包括 [5,6,7,...] 。第 2 个缺失的正整数为 6 。
```



## 模拟

```java
class Solution {
    public int findKthPositive(int[] arr, int k) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] <= k) k++;
        }
        return k;
    }
}
```





## 二分

```java
class Solution {
    // 一个不缺失元素的序列，会有arr[i]=i+1这样的关系,
    // 而在缺失元素之后，会有arr[i]>i+1，简单移项可得 arr[i]-i-1 > 0
    // 缺失一个的时候，相差1，两个则相差2，以此类推，缺失越多，两者差距越大
    // 我们要找第k个缺失的，换言之，只要arr[i]-i-1 == k,我们便找到了题目要找的数字。
    public int findKthPositive(int[] arr, int k) {
        int le = 0, ri = arr.length, mid = 0;
        while (le < ri) {
            mid = le + (ri-le)/2;
            if (arr[mid]-mid >= k+1) {
                ri = mid;
            } else {
                le = mid + 1;
            }
        }
        return k + le;
    }
}
```

