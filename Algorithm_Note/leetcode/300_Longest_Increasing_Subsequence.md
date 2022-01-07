# 300. Longest Increasing Subsequence

Given an integer array `nums`, return the length of the longest strictly increasing subsequence.

A **subsequence** is a sequence that can be derived from an array by deleting some or no elements without changing the order of the remaining elements. For example, `[3,6,2,7]` is a subsequence of the array `[0,3,1,6,2,2,7]`.

**Example:**

```
Input: nums = [10,9,2,5,3,7,101,18]
Output: 4
Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
```



## 二分查找

通过dp数组构建一个动态的最长子序列，中途使用Arrays.binarySearch()，它的用法如下：
```java
import java.util.Arrays;

public class ArraysBinarySearch {
	public static void main(String[] args) {
		int[] list = {13, 1, 3, 5, 8, 10, 2};
		//             0, 1, 2, 3, 4,  5, 6 

		System.out.println(Arrays.binarySearch(list, 2, 5, 5));		//  3
		System.out.println(Arrays.binarySearch(list, 2, 5, 1));		// -3
		System.out.println(Arrays.binarySearch(list, 2, 5, 4));		// -4
		System.out.println(Arrays.binarySearch(list, 2, 5, 6));		// -5
		System.out.println(Arrays.binarySearch(list, 2, 5, 7));		// -5
		System.out.println("-------");
		System.out.println(Arrays.binarySearch(list, 2, 4,  12));	// -5
		System.out.println(Arrays.binarySearch(list, 2, 4, -12));	// -3
		System.out.println(Arrays.binarySearch(list, 2, 4, 2));		// -3
		System.out.println(Arrays.binarySearch(list, 2, 4, 13));	// -5
	}
}
>输出结果：
3
-3
-4
-5
-5
-------
-5
-3
-3
-5
```
过程类似插入排序，不过会把之前位置的数替代掉。
下面第二段进一步优化，因为如果当前遍历到数为之前数最大，就直接插入dp数组最后。
```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        int res = 0;
        for (int num: nums) {
            int idx = Arrays.binarySearch(dp, 0, res, num);
            if (idx < 0) idx = -idx-1;
            dp[idx] = num;
            if (idx == res) res++;
        }
        return res;
    }
}
```
**优化**

```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) return 0;

        int len = 1;
        int[] dp = new int[nums.length];
        dp[len] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > dp[len]) {     // 这里可以写成if-else，但这么写逻辑更清晰  
                dp[++len] = nums[i];
                continue;
            }
            binarySearch(dp, 0, len, nums[i]);
        }

        return len+1;	// len是求出的最长上升子序列在dp中的下标，所以要加一
    }

    private void binarySearch(int[] dp, int start, int end, int target) {
        if(start == end) {
            dp[end] = target;
            return;
        }

        int mid = start + (end-start)/2;
        if(dp[mid] < target) {
            binarySearch(dp, mid+1, end, target);
        } else {
            binarySearch(dp, start, mid, target);
        }
    }
}
```



## DP

时间复杂度：O(n^2) 

通过 前面子序列的最长长度得出 现子序列的最长长度

```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int res = 1;
        for(int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            for(int j = 0; j < i; j++) {
                if(nums[j] < nums[i]) dp[i] = Math.max(dp[j]+1, dp[i]); 
            }
            res = Math.max(res, dp[i]);
        }

        return res;
    }
}
```



## 记忆化递归

针对两种情况（taken，nottaken）从前往后递归。
```java
public class Solution {
    public int lengthOfLIS(int[] nums) {
        int memo[][] = new int[nums.length + 1][nums.length];
        for (int[] l : memo) Arrays.fill(l, -1);

        return lengthofLIS(nums, -1, 0, memo);
    }
    
    public int lengthofLIS(int[] nums, int pre, int cur, int[][] memo) {
        if (cur == nums.length) return 0;
        if (memo[pre+1][cur] >= 0) return memo[pre+1][cur]; 

        int taken = 0;
        if (pre < 0 || nums[cur] > nums[pre])   // 选取 当前字符构成最终的上升子序列
            taken = 1 + lengthofLIS(nums, cur, cur+1, memo);
        int nottaken = lengthofLIS(nums, pre, cur+1, memo);  // 不选 （即使符合前面的子序列上升）
        memo[pre+1][cur] = Math.max(taken, nottaken);
        return memo[pre+1][cur];
    }
}
```
