# 1248. Count Number of Nice Subarrays

Given an array of integers `nums` and an integer `k`. A continuous subarray is called **nice** if there are `k` odd numbers on it.

Return *the number of **nice** sub-arrays*.

 

**Example 1:**

```
Input: nums = [1,1,2,1,1], k = 3
Output: 2
Explanation: The only sub-arrays with 3 odd numbers are [1,1,2,1] and [1,2,1,1].
```

**Example 2:**

```
Input: nums = [2,2,2,1,2,2,1,2,2,2], k = 2
Output: 16
```

## 前缀和 + 差分

```java
class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        int N = nums.length;
        int[] cout = new int[N+1]; cout[0] = 1;
        int cnt = 0, res = 0;
        for (int n : nums) {
            cnt += n & 1;  // 奇数数量（一直增加）
            cout[cnt] += 1;  // 想想cnt没增加的情况
            res += cnt>=k ? cout[cnt-k] : 0;  // 同上
        }
        return res;
    }
}
```

## 数学

```java
class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        int N = nums.length, res = 0, idx = 1;
        int[] odd_idx = new int[N + 2];
        odd_idx[0] = -1;
        for (int i = 0; i < N; ++i) {
            if ((nums[i] & 1) == 1) odd_idx[idx++] = i;
        }
        odd_idx[idx] = N;
        for (int i = 1; i + k <= idx; i++) {  // 选择方案数相乘
            res += (odd_idx[i] - odd_idx[i-1]) * (odd_idx[i+k] - odd_idx[i+k-1]);
        }
        return res;
    }
}
```

## 前缀和 + 遍历

```java
class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        int N = nums.length;
        int[] preOdd = new int[N+1];
        for (int i = 1; i <= N; i++) {
            if (nums[i-1]%2 == 1) preOdd[i] = preOdd[i-1] + 1;
            else preOdd[i] = preOdd[i-1];
        }


        int res = 0, preCnt = 0;
        for (int i = 0; i <= N; i++) {
            if (preOdd[N]-preOdd[i] < k) break;
            if (preCnt != 0 && nums[i-1]%2 == 0) {  // 当前奇数项
                res += preCnt;  // nice_sub 累计一次前面的偶数余项
                continue;
            }

            int cnt = 0;
            for (int ri = i+k; ri <= N; ri++) {  // 可能偶数项，可能奇数项
                int mk = preOdd[ri]-preOdd[i];
                if (mk > k) break;
                if (mk == k) cnt++;  
            }

            res += cnt;  // nice_sub 累计一次后面的偶数余项
            preCnt = cnt;
        }
        return res;
    }
}
```