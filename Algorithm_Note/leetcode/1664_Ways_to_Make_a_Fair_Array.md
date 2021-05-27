# 1664. Ways to Make a Fair Array

You are given an integer array `nums`. You can choose **exactly one** index (**0-indexed**) and remove the element. Notice that the index of the elements may change after the removal.

For example, if `nums = [6,1,7,4,1]`:

- Choosing to remove index `1` results in `nums = [6,7,4,1]`.
- Choosing to remove index `2` results in `nums = [6,1,4,1]`.
- Choosing to remove index `4` results in `nums = [6,1,7,4]`.

An array is **fair** if the sum of the odd-indexed values equals the sum of the even-indexed values.

Return the ***number** of indices that you could choose such that after the removal,* `nums` *is **fair**.*

 

**Example 1:**

```
Input: nums = [2,1,6,4]
Output: 1
Explanation:
Remove index 0: [1,6,4] -> Even sum: 1 + 4 = 5. Odd sum: 6. Not fair.
Remove index 1: [2,6,4] -> Even sum: 2 + 4 = 6. Odd sum: 6. Fair.
Remove index 2: [2,1,4] -> Even sum: 2 + 4 = 6. Odd sum: 1. Not fair.
Remove index 3: [2,1,6] -> Even sum: 2 + 6 = 8. Odd sum: 1. Not fair.
There is 1 index that you can remove to make nums fair.
```

**Example 2:**

```
Input: nums = [1,1,1]
Output: 3
Explanation: You can remove any index and the remaining array is fair.
```

## PreDiff

dp[i−1]表示索引i左边部分奇偶元素差值

dp[n] - dp[i] 表示索引 i 右边部分奇偶元素差值

```java
class Solution {
    public int waysToMakeFair(int[] nums) {
        int res = 0, N = nums.length;
        int[] preFix = new int[N+1];

        for (int i = 0; i < N; i++) {
            preFix[i+1] = preFix[i] + (i%2==1 ? nums[i] : -nums[i]);
        }
        for (int i = 1; i <= N; i++) {
            if (preFix[i-1] == preFix[N] - preFix[i]) res++;
        }
        return res;
    }
}
```



## PreSum

```java
class Solution {
    public int waysToMakeFair(int[] nums) {
        int sumOdd=0, sumEve=0, leOdd=0, leEve=0, riOdd=0, riEve=0, res=0;
        for (int i = 0; i < nums.length; i++) {
            if (i%2 == 0) sumEve += nums[i];
            else sumOdd += nums[i];
        }
        for (int i = 0; i < nums.length; i++) {
            if (i%2 == 0) leEve += nums[i];
            else leOdd += nums[i];

            riEve = sumOdd - leOdd; 
            riOdd = sumEve - leEve;
            if (i%2 == 0) riEve -= nums[i];
            else riOdd -= nums[i];

            if (leEve+riEve == leOdd+riOdd) res++;
        }
        return res;
    }
}
```

