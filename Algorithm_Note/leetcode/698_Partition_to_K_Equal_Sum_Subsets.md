# 698. Partition to K Equal Sum Subsets

Given an integer array `nums` and an integer `k`, return `true` if it is possible to divide this array into `k` non-empty subsets whose sums are all equal.

 

**Example 1:**

```
Input: nums = [4,3,2,3,5,2,1], k = 4
Output: true
Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
```

**Example 2:**

```
Input: nums = [1,2,3,4], k = 3
Output: false
```



## DFS

```go
package main

func canPartitionKSubsets(nums []int, k int) bool {
	sum := 0
	for _, v := range nums {
		sum += v
	}
	if sum%k != 0 {
		return false
	}

	st := make([]bool, len(nums))
	tar := sum / k
	return backtrack(k, 0, nums, 0, st, tar)
}

func backtrack(k int, sum int, nums []int, start int, st []bool, tar int) bool {
	if k == 0 {
		return true
	}
	if sum == tar {
		return backtrack(k-1, 0, nums, 0, st, tar)
	}

	for i := start; i < len(nums); i++ {
		if st[i] {
			continue
		}
		if sum+nums[i] > tar {
			continue
		}

		st[i] = true
		if backtrack(k, sum+nums[i], nums, i+1, st, tar) {
			return true
		}
		st[i] = false
	}
	return false
}
```

