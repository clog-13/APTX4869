# 416. Partition Equal Subset Sum

Given a **non-empty** array `nums` containing **only positive integers**, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.

 

**Example 1:**

```
Input: nums = [1,5,11,5]
Output: true
Explanation: The array can be partitioned as [1, 5, 5] and [11].
```

**Example 2:**

```
Input: nums = [1,2,3,5]
Output: false
Explanation: The array cannot be partitioned into equal sum subsets.
```



## 01DP

```go
func canPartition(nums []int) bool {
	sum, max := 0, 0
	for _, v := range nums {
		sum += v
		if v > max {
			max = v
		}
	}
	tar := sum / 2
	if sum%2 != 0 || max > tar {
		return false // 剪枝
	}

	dp := make([]bool, tar+1)
	dp[0] = true
	for i := 0; i < len(nums); i++ {
		v := nums[i]
		for j := tar; j >= v; j-- { // 01
			dp[j] = dp[j] || dp[j-v]
		}
	}
	return dp[tar]
}
```



## DFS

```go
func canPartition(nums []int) bool {
	sum := 0
	for _, num := range nums {
		sum += num
	}
	if sum%2 == 1 {
		return false
	}
	sort.Slice(nums, func(x, y int) bool { // sort.Ints(nums)
		return nums[x] < nums[y]
	})
	tar := sum / 2
	memo := make([][]int, len(nums))
	for i := 0; i < len(nums); i++ {
		memo[i] = make([]int, tar+1)
	}
	var dfs func(i, tar int) bool
	dfs = func(i, tar int) bool { // !!! memo[i][tar] == 1
		if i >= len(nums) || nums[i] > tar || memo[i][tar] == 1 {
			return false
		}
		if tar == nums[i] {
			return true
		}
		st := dfs(i+1, tar-nums[i]) || dfs(i+1, tar)
		if !st {
			memo[i][tar] = 1
		}
		return st
	}
	return dfs(0, tar)
}
```

