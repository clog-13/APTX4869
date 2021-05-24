# 45. Jump Game II

Given an array of non-negative integers `nums`, you are initially positioned at the first index of the array.

Each element in the array represents your maximum jump length at that position.

Your goal is to reach the last index in the minimum number of jumps.

You can assume that you can always reach the last index.

 

**Example:**

```
Input: nums = [2,3,1,1,4]
Output: 2
Explanation: The minimum number of jumps to reach the last index is 2. Jump 1 step from index 0 to 1, then 3 steps to the last index.
```



## 贪心

```java
class Solution {
    public int jump(int[] nums) {
        int p = 0, end = 0, res = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            p = Math.max(p, i + nums[i]);
            if (i == end) {
                end = p;
                res++;
            }
        }
        return res;
    }
}
```

```java
class Solution {
    public int jump(int[] nums) {
        if (nums.length==1) return 0;
        int[] nxt = new int[nums.length];
        for (int i = 0; i < nums.length; i++) 
            nxt[i] = Math.max(nxt[i], i+nums[i]);
        for (int i = 1; i < nxt.length; i++) 
            nxt[i] = Math.max(nxt[i], nxt[i-1]);
        int p = 0, res = 0;
        while (p < nums.length-1) {  // 到数组最后一个位置
            p = nxt[p];
            res++;
        }
        return res;
    }
}
```



## 逆向思维

```java
class Solution {
    public int jump(int[] nums) {
        int res = 0, pos = nums.length - 1;
        while (pos > 0) {
            for (int i = 0; i < pos; i++) {
                if (i + nums[i] >= pos) {
                    pos = i;
                    res++;
                    break;
                }
            }
        }
        return res;
    }
}
```

