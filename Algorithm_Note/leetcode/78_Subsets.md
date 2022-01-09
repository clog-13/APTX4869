# 78. Subsets

Given an integer array `nums` of **unique** elements, return *all possible subsets (the power set)*.

The solution set **must not** contain duplicate subsets. Return the solution in **any order**.

 

**Example 1:**

```
Input: nums = [1,2,3]
Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
```

**Example 2:**

```
Input: nums = [0]
Output: [[],[0]]
```



## ST Mask

```go
func subsets(nums []int) (res [][]int) {
    for mask := 0; mask < 1<<len(nums); mask++ {
        tmp := []int{}
        for i, v := range nums {
            if mask>>i&1 > 0 {
                tmp = append(tmp, v)
            }
        }
        res = append(res, append([]int(nil), tmp...))
    }
    return
}
```



## BackTrack

```go
func subsets(nums []int) (res [][]int) {
    set := []int{}
    var dfs func(int)
    dfs = func(idx int) {
        if idx == len(nums) {
            res = append(res, append([]int(nil), set...))
            return
        }
        set = append(set, nums[idx])
        dfs(idx + 1) // taken
        set = set[:len(set)-1]
        dfs(idx + 1) // nottaken
    }
    dfs(0)
    return
}
```



```java
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        find(res, new ArrayList<>(), 0, nums);
        return res;
    }
    
    private void find(List<List<Integer>> res,List<Integer> tmp,int index,int[] nums) {
    	if(index == nums.length) {
    		res.add(new ArrayList<Integer>(tmp));
    		return;
    	}
    	tmp.add(nums[index]);
        find(res, tmp, index+1, nums);
    	tmp.remove(tmp.size()-1);    	
    	find(res, tmp, index+1, nums);
    }
}
```

