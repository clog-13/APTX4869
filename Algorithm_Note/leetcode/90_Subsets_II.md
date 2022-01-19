# 90. Subsets II

Given an integer array `nums` that may contain duplicates, return *all possible subsets (the power set)*.

The solution set **must not** contain duplicate subsets. Return the solution in **any order**.

 

**Example 1:**

```
Input: nums = [1,2,2]
Output: [[],[1],[1,2],[1,2,2],[2],[2,2]]
```



## ST Mask

```
func subsetsWithDup(nums []int) (res [][]int) {
    sort.Ints(nums) 
outer:
    for mask := 0; mask < 1<<len(nums); mask++ {
        t := []int{}
        for i, v := range nums {
            if mask>>i&1 > 0 {
                if i > 0 && mask>>(i-1)&1 == 0 && v == nums[i-1] {
                    continue outer
                }
                t = append(t, v)
            }
        }
        res = append(res, append([]int(nil), t...))
    }
    return
}
```



## BackTrack

```go
func subsetsWithDup(nums []int) (res [][]int) {
    sort.Ints(nums)
    t := []int{}
    var dfs func(bool, int)
    dfs = func(choosePre bool, cur int) {
        if cur == len(nums) {
            res = append(res, append([]int(nil), t...))
            return
        }
        dfs(false, cur+1)
        if !choosePre && cur > 0 && nums[cur] == nums[cur-1] {
            return
        }
        
        t = append(t, nums[cur])
        dfs(true, cur+1)
        t = t[:len(t)-1]
    }
    
    dfs(false, 0)
    return
}
```

