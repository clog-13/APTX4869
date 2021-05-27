# 1642. Furthest Building You Can Reach

You are given an integer array `heights` representing the heights of buildings, some `bricks`, and some `ladders`.

You start your journey from building `0` and move to the next building by possibly using bricks or ladders.

While moving from building `i` to building `i+1` (**0-indexed**),

- If the current building's height is **greater than or equal** to the next building's height, you do **not** need a ladder or bricks.
- If the current building's height is **less than** the next building's height, you can either use **one ladder** or `(h[i+1] - h[i])` **bricks**.

*Return the furthest building index (0-indexed) you can reach if you use the given ladders and bricks optimally.*

 

**Example:**

```
Input: heights = [4,2,7,6,9,14,12], bricks = 5, ladders = 1
Output: 4
Explanation: Starting at building 0, you can follow these steps:
- Go to building 1 without using ladders nor bricks since 4 >= 2.
- Go to building 2 using 5 bricks. You must use either bricks or ladders because 2 < 7.
- Go to building 3 without using ladders nor bricks since 7 >= 6.
- Go to building 4 using your only ladder. You must use either bricks or ladders because 6 < 9.
It is impossible to go beyond building 4 because you do not have any more bricks or ladders.
```

## 贪心 + 优先队列

优先用梯子,梯子不够就把最短的梯子换成砖头,砖头不够就返回

```java
class Solution {
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int i = 0;
        while (i < heights.length-1) {
            if (heights[i] >= heights[i+1]) i++;
            else {
                heap.add(heights[i+1] - heights[i]);
                if (heap.size() > ladders) bricks -= heap.poll();
                if (bricks < 0) return i;
                i++;
            }
        }
        return i;
    }
}
```

## DFS(TLE)

```java
class Solution {
    int res = 0;
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        dfs(heights, 0, bricks, ladders);
        return res;
    }
    private void dfs(int[] heights, int cur, int bricks, int ladders) {
        if (res == heights.length - 1) return;  // 剪枝
        if (cur == heights.length - 1 || (heights[cur+1]-heights[cur] > bricks && ladders==0)) {
            res = Math.max(res, cur);
            return;
        }

        if (heights[cur] >= heights[cur+1]) dfs(heights, cur + 1, bricks, ladders);
        else {
            if (bricks >= heights[cur+1] - heights[cur])  // 砖
                dfs(heights, cur + 1, bricks + heights[cur] - heights[cur + 1], ladders);
            if (ladders >= 1)  // 梯子
                dfs(heights, cur + 1, bricks, ladders - 1);
        }
    }
}
```
