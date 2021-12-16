# 1879. Minimum XOR Sum of Two Arrays

You are given two integer arrays `nums1` and `nums2` of length `n`.

The **XOR sum** of the two integer arrays is `(nums1[0] XOR nums2[0]) + (nums1[1] XOR nums2[1]) + ... + (nums1[n - 1] XOR nums2[n - 1])` (**0-indexed**).

- For example, the **XOR sum** of `[1,2,3]` and `[3,2,1]` is equal to `(1 XOR 3) + (2 XOR 2) + (3 XOR 1) = 2 + 0 + 2 = 4`.

Rearrange the elements of `nums2` such that the resulting **XOR sum** is **minimized**.

Return *the **XOR sum** after the rearrangement*.

 

**Example 1:**

```
Input: nums1 = [1,2], nums2 = [2,3]
Output: 2
Explanation: Rearrange nums2 so that it becomes [3,2].
The XOR sum is (1 XOR 3) + (2 XOR 2) = 2 + 0 = 2.
```

**Example 2:**

```
Input: nums1 = [1,0,3], nums2 = [5,3,4]
Output: 8
Explanation: Rearrange nums2 so that it becomes [5,4,3]. 
The XOR sum is (1 XOR 5) + (0 XOR 4) + (3 XOR 3) = 4 + 4 + 0 = 8.
```



## State DP

```java
class Solution {
    public int minimumXORSum(int[] nums1, int[] nums2) {
        int N = nums1.length;
        int[] f = new int[1<<N];
        Arrays.fill(f, 0x3f3f3f3f);
        f[0] = 0;  // f[st]: nums1中选前cnt个, nums2中选择状态为st !!!
        for (int st = 1; st < 1<<N; st++) {
            int cnt = 0;
            for (int t = st; t > 0; t-=t&-t) cnt++;
            
            for (int i = 0; i < N; i++) {
                if ((st & 1<<i) > 0) {
                    f[st] = Math.min(f[st], f[st-(1<<i)] + (nums1[cnt-1] ^ nums2[i]));
                }
            }
        }
        return f[(1<<N)-1];
    }
}
```

