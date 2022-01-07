# 918. Maximum Sum Circular Subarray

Given a **circular integer array** `nums` of length `n`, return *the maximum possible sum of a non-empty **subarray** of* `nums`.

A **circular array** means the end of the array connects to the beginning of the array. Formally, the next element of `nums[i]` is `nums[(i + 1) % n]` and the previous element of `nums[i]` is `nums[(i - 1 + n) % n]`.

A **subarray** may only include each element of the fixed buffer `nums` at most once. Formally, for a subarray `nums[i], nums[i + 1], ..., nums[j]`, there does not exist `i <= k1`, `k2 <= j` with `k1 % n == k2 % n`.

 

**Example 1:**

```
Input: nums = [1,-2,3,-2]
Output: 3
Explanation: Subarray [3] has maximum sum 3.
```

**Example 2:**

```
Input: nums = [5,-3,5]
Output: 10
Explanation: Subarray [5,5] has maximum sum 5 + 5 = 10.
```



 ## Adjacency Array

```java
class Solution {
    public int maxSubarraySumCircular(int[] A) {
        int res = A[0], cur = A[0], N = A.length;
        for (int i = 1; i < N; i++) {
            cur = A[i] + Math.max(cur, 0);
            res = Math.max(res, cur);
        }

        int[] riSum = new int[N];
        riSum[N-1] = A[N-1];
        for (int i = N-2; i >= 0; i--) {
            riSum[i] = riSum[i+1] + A[i];
        }
        int[] maxRi = new int[N];
        maxRi[N-1] = A[N-1];
        for (int i = N-2; i >= 0; i--) {
            maxRi[i] = Math.max(maxRi[i+1], riSum[i]);
        }
        int leSum = 0;
        for (int i = 0; i < N-2; i++) {
            leSum += A[i];
            res = Math.max(res, leSum + maxRi[i+2]);
        }

        return res;
    }
}
```



## PreSum & MonoStack

```java
class Solution {
    public int maxSubarraySumCircular(int[] A) {
        int N = A.length;
        int[] preSum = new int[2*N+1];
        for (int i = 0; i < 2*N; i++) preSum[i+1] = preSum[i] + A[i%N];

        int res = A[0];
        Deque<Integer> deq = new ArrayDeque();
        deq.offer(0);
        for (int j = 1; j <= 2*N; j++) {
            if (deq.peekFirst() < j-N) deq.pollFirst();

            res = Math.max(res, preSum[j] - preSum[deq.peekFirst()]);
            while (!deq.isEmpty() && preSum[j] <= preSum[deq.peekLast()]) {
                deq.pollLast();
            }
            deq.offerLast(j);
        }
        return res;
    }
}
```



## Kadane(Minimum)

```java
class Solution {
    public int maxSubarraySumCircular(int[] nums) {
        int cur = nums[0], mx = cur, sum = cur, mi = 0;
        for (int i = 1; i < nums.length; i++) {
            cur = nums[i] + Math.max(cur, 0);
            mx = Math.max(cur, mx);
            sum += nums[i];
        }
        cur = nums[0];
        for (int i = 1; i < nums.length-1; i++) {
            cur = nums[i] + Math.min(cur, 0);
            mi = Math.min(cur, mi);
        }
        return Math.max(sum-mi, mx);
    }
}
```



## Kadane(Sign Reverse)

```java
class Solution {
    public int maxSubarraySumCircular(int[] A) {
        long S = 0;
        for (int x: A) S += x;
        
        long res1 = kadane(A, 0, A.length-1, 1);
        // 如果选择了 B 的完整数组，双区间子段为空
        long res2 = S + kadane(A, 1, A.length-1, -1);
        long res3 = S + kadane(A, 0, A.length-2, -1);
        return (int)Math.max(res1, Math.max(res2, res3));
    }

    public int kadane(int[] A, int i, int j, int sign) {
        int res = Integer.MIN_VALUE, cur = Integer.MIN_VALUE;
        for (int k = i; k <= j; ++k) {
            cur = sign * A[k] + Math.max(cur, 0);
            res = Math.max(res, cur);
        }
        return res;
    }
}
```

