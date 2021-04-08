# 1425. 带限制的子序列和

给你一个整数数组 `nums` 和一个整数 `k` ，请你返回 **非空** 子序列元素和的最大值，子序列需要满足：子序列中每两个 **相邻** 的整数 `nums[i]` 和 `nums[j]` ，它们在原数组中的下标 `i` 和 `j` 满足 `i < j` 且 `j - i <= k` 。

数组的子序列定义为：将数组中的若干个数字删除（可以删除 0 个数字），剩下的数字按照原本的顺序排布。

 

**示例：**

```
输入：nums = [10,2,-10,5,20], k = 2
输出：37
解释：子序列为 [10, 2, 5, 20] 。
```



## 单调队列

```java
class Solution {
    public int constrainedSubsetSum(int[] nums, int k) {
        int N = nums.length;
        int[] f = new int[N], q = new int[N];
        int res = nums[0], hh = 0, tt = 0;
        f[0] = nums[0];
        for (int i = 1; i < N; i++) {
            if (hh<=tt && i > q[hh]+k) hh++;
            f[i] = Math.max(f[q[hh]], 0) + nums[i];
            while (hh<=tt && f[i] > f[q[tt]]) tt--;
            q[++tt] = i;
            res = Math.max(res, f[i]);
        }
        return res;
    }
}
```

