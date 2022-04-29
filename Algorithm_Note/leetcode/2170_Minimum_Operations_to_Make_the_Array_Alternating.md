# 2169. Minimum Operations to Make the Array Alternating

You are given a **0-indexed** array `nums` consisting of `n` positive integers.

The array `nums` is called **alternating** if:

- `nums[i - 2] == nums[i]`, where `2 <= i <= n - 1`.
- `nums[i - 1] != nums[i]`, where `1 <= i <= n - 1`.

In one **operation**, you can choose an index `i` and **change** `nums[i]` into **any** positive integer.

Return *the **minimum number of operations** required to make the array alternating*.

 

**Example 1:**

```
Input: nums = [3,1,3,2,4,3]
Output: 3
Explanation:
One way to make the array alternating is by converting it to [3,1,3,1,3,1].
The number of operations required in this case is 3.
It can be proven that it is not possible to make the array alternating in less than 3 operations. 
```

**Example 2:**

```
Input: nums = [1,2,2,2,2]
Output: 2
Explanation:
One way to make the array alternating is by converting it to [1,2,1,2,1].
The number of operations required in this case is 2.
Note that the array cannot be converted to [2,2,2,2,2] because in this case nums[0] == nums[1] which violates the conditions of an alternating array.
```



## Map + Greedy

```go
func minimumOperations(nums []int) int {
	cnt := [2]map[int]int{{},{}} // !!!
	for i, num := range nums {
		cnt[i&1][num]++
	}
	a0 := getMaxCnt2(cnt[0])
	a1 := getMaxCnt2(cnt[1])
	if a0[0].num != a1[0].num {
		return len(nums) - a0[0].cnt - a1[0].cnt // 不相等时，保留出现次数最多的两个
	}
	return len(nums) - max(a0[0].cnt+a1[1].cnt, a0[1].cnt+a1[0].cnt)
}

func getMaxCnt2(cnt map[int]int) []pair {
	arr := make([]pair, 0, max(len(cnt), 2)) // !!!
	for num, c := range cnt {
		arr = append(arr, pair{num, c})
	}
	sort.Slice(arr, func(i, j int) bool { return arr[i].cnt > arr[j].cnt })
	return arr[:2] // 不足两个时，用 pair{0, 0} 填充
}

type pair struct{ num, cnt int }

func max(a, b int) int {
	if b > a {
		return b
	}
	return a
}
```

