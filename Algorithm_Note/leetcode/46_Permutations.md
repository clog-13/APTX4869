# 46. Permutations

Given an array `nums` of distinct integers, return *all the possible permutations*. You can return the answer in **any order**.

 

**Example 1:**

```
Input: nums = [1,2,3]
Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
```

**Example 2:**

```
Input: nums = [0,1]
Output: [[0,1],[1,0]]
```



## backtrack

```java
class Solution {
    List<List<Integer>> res = new ArrayList<List<Integer>>();

    public List<List<Integer>> permute(int[] nums) {
        List<Integer> list = new ArrayList<Integer>();
        for (int num : nums) list.add(num);

        int N = nums.length;
        helper(list, 0, N);
        return res;
    }

    public void helper(List<Integer> list, int start, int N) {
        if (start == N) res.add(new ArrayList<Integer>(list));
        for (int i = start; i < N; i++) {
            Collections.swap(list, start, i);
            helper(list, start + 1, N);
            Collections.swap(list, start, i);
        }
    }
}
```

```java
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> t = new ArrayList<>();
        find(res, t, nums);
        return res;
    }
    
    void find(List<List<Integer>> res, List<Integer> tmp, int[] nums) {
        if (tmp.size() == nums.length) {
            res.add(new ArrayList<>(tmp));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!tmp.contains(nums[i])) {
                tmp.add(nums[i]);
                find(res, tmp, nums);
                tmp.remove(tmp.size()-1);                
            }
        }
    }
}
```



## simulate

```java
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        List<Integer> tmp = new ArrayList<Integer>();
        tmp.add(nums[0]);
        res.add(tmp);
        for (int i = 1; i < nums.length; i++) {
            List<List<Integer>> nex = new ArrayList<List<Integer>>();
            for (List<Integer> list: res) {
                for (int j = 0; j <= list.size(); j++) {
                    tmp = new ArrayList<Integer>();
                    tmp.addAll(list);
                    tmp.add(j, nums[i]);              
                    nex.add(tmp);
                }   
            }
            res = nex;
        }
        return res;        
    }
}
```

