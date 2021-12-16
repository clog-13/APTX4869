# 1300. Sum of Mutated Array Closest to Target

Given an integer array `arr` and a target value `target`, return the integer `value` such that when we change all the integers larger than `value` in the given array to be equal to `value`, the sum of the array gets as close as possible (in absolute difference) to `target`.

In case of a tie, return the minimum such integer.

Notice that the answer is not neccesarilly a number from `arr`.

 

**Example 1:**

```
Input: arr = [4,9,3], target = 10
Output: 3
Explanation: When using 3 arr converts to [3, 3, 3] which sums 9 and that's the optimal answer.
```

**Example 2:**

```
Input: arr = [2,3,5], target = 10
Output: 5
```

**Example 3:**

```
Input: arr = [60864,25176,27249,21296,20204], target = 56803
Output: 11361
```



## Simulation

```java
class Solution {
    public int findBestValue(int[] arr, int target) {
        int sum = 0, max = 0;
        for (int i : arr) {
            sum += i;
            max = i>max ? i : max;
        }
        if (sum < target) return max;
        int num = target/arr.length;
        sum = getSum(arr, num);
        while (num <= target) {
           int tmp = getSum(arr, num+1);
           if (tmp >= target) return (tmp-target) < (target-sum) ? num+1 : num;
           sum = tmp;
           num++; 
        }
        return 0;
    }
    int getSum(int[] arr, int val) {
        int sum = 0;
        for (int i : arr) sum += i<val ? i : val;
        return sum;
    }
}
```



## Binary

```java
class Solution {
    public int findBestValue(int[] arr, int tar) {
        Arrays.sort(arr);
        int N = arr.length;
        int[] preSum = new int[N + 1];
        for (int i = 1; i <= N; ++i) preSum[i] = preSum[i-1] + arr[i-1];
        
        int le = 0, ri = arr[N-1], res = -1;
        while (le <= ri) {
            int mid = (le + ri) >> 1;
            int idx = Arrays.binarySearch(arr, mid);
            if (idx < 0) idx = -idx - 1;
            int sum = preSum[idx] + (N-idx)*mid;
            if (sum <= tar) {
                res = mid;
                le = mid + 1;
            } else {
                ri = mid - 1;
            }
        }
        int s = getSum(arr, res), s1 = getSum(arr, res+1);
        return Math.abs(s-tar) <= Math.abs(s1-tar) ? res : res+1;
    }

    public int getSum(int[] arr, int x) {
        int ret = 0;
        for (int num : arr) ret += Math.min(num, x);
        return ret;
    }
}
```

