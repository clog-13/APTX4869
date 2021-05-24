# 1588. Sum of All Odd Length Subarrays

Given an array of positive integers `arr`, calculate the sum of all possible odd-length subarrays.

A subarray is a contiguous subsequence of the array.

Return *the sum of all odd-length subarrays of* `arr`.

**Example:**

```
Input: arr = [1,4,2,5,3]
Output: 58
Explanation: The odd-length subarrays of arr and their sums are:
[1] = 1
[4] = 4
[2] = 2
[5] = 5
[3] = 3
[1,4,2] = 7
[4,2,5] = 11
[2,5,3] = 10
[1,4,2,5,3] = 15
If we add all these together we get 1 + 4 + 2 + 5 + 3 + 7 + 11 + 10 + 15 = 58
```

## Math

没有必要求出每种奇数长度数组的情况，只需判断每个元素在奇数长度数组中出现的次数即可

left：对当前元素arr[i]来说，前面有i个元素，想要构成连续数组，可在arr[i]左面连续取0,1,2,...,i个元素，所以共有left=i+1种选择方法

right：arr[i]后有n-i-1个元素，想要构成连续数组，可在arr[i]右面连续取元素，共有right=n-i种选择方法(算上取0个元素)



left_odd, right_odd可选择数：left/2 和 right/2 (左面选奇数个元素，右面选奇数个元素，加上本身后为奇数长度数组)

left_even, right_even可选择数：(left+1)/2 和 (right+1)/2 (左面选偶数个元素，右面选偶数个元素，加上本身后为奇数长度数组)

```java
class Solution {
    public int sumOddLengthSubarrays(int[] arr) {
        int sum = 0, N = arr.length;
        for (int i = 0; i < N; i++) {
            int left = i+1, right = N-i;
            int le_ev = left/2, ri_ev = right/2;  // 偶+1+偶 = 奇
            int le_od = (left+1)/2, ri_od = (right+1)/2;  // 奇+1+奇 = 偶
            sum += arr[i] * (le_ev*ri_ev + le_od*ri_od);
        }
        return sum;
    }
}
```

## 前缀和

```java
class Solution {
    public int sumOddLengthSubarrays(int[] arr) {
        int res = 0, N = arr.length;
        int[] preSum = new int[N+1];
        for (int i = 1; i <= N; i++) preSum[i] = preSum[i-1]+arr[i-1];

        for (int len = 1; len <= N; len+=2) {
            for (int i = len; i <= N; i++) {
                res += preSum[i] - preSum[i-len];
            }
        }

        return res;
    }
}
```
