# 974. Subarray Sums Divisible by K

Given an array `nums` of integers, return the number of (contiguous, non-empty) subarrays that have a sum divisible by `k`.

 

**Example:**

```
Input: nums = [4,5,0,-2,-3,1], k = 5
Output: 7
Explanation: There are 7 subarrays with a sum divisible by k = 5:
[4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
```

## 计数

```java
class Solution {
    public int subarraysDivByK(int[] A, int K) {
        int[] map = new int[K];
        map[0] = 1;
        int mod = 0, res = 0;
        for (int a : A) {
            mod = (mod + a) % K;
            if (mod < 0) mod += K;
            res += map[mod]++;
        }
        return res;
    }
}
```

