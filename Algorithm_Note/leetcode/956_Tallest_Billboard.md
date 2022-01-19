# 956. Tallest Billboard

You are installing a billboard and want it to have the largest height. The billboard will have two steel supports, one on each side. Each steel support must be an equal height.

You are given a collection of `rods` that can be welded together. For example, if you have rods of lengths `1`, `2`, and `3`, you can weld them together to make a support of length `6`.

Return *the largest possible height of your billboard installation*. If you cannot support the billboard, return `0`.

 

**Example 1:**

```
Input: rods = [1,2,3,6]
Output: 6
Explanation: We have two disjoint subsets {1,2,3} and {6}, which have the same sum = 6.
```

**Example 2:**

```
Input: rods = [1,2,3,4,5,6]
Output: 10
Explanation: We have two disjoint subsets {2,3,5} and {4,6}, which have the same sum = 10.
```

**Example 3:**

```
Input: rods = [1,2]
Output: 0
Explanation: The billboard cannot be supported, so we return 0.
```

 

**Constraints:**

- `1 <= rods.length <= 20`
- `1 <= rods[i] <= 1000`
- `sum(rods[i]) <= 5000`



## DP

```java
class Solution {
    public int tallestBillboard(int[] rods) {
        Map<Integer, Integer> dict = new HashMap<>();
        Map<Integer, Integer> temp = new HashMap<>();
        dict.put(0, 0);
        for (int i : rods) {
            temp.clear(); 
            temp.putAll(dict);
            for (Integer len : temp.keySet()) {
                dict.put(len+i, Math.max(
                    dict.getOrDefault(len+i, 0), temp.get(len) + i));
                dict.put(len-i, Math.max(
                    dict.getOrDefault(len-i, 0), temp.get(len)));
            }
        }
        return dict.get(0);
    }
}
```



## DFS

```java
class Solution {
    int NINF = Integer.MIN_VALUE / 3;
    Integer[][] memo;  // !!!
    public int tallestBillboard(int[] rods) {
        memo = new Integer[rods.length][10001];
        return (int) dfs(rods, 0, 5000);
    }

    public int dfs(int[] rods, int i, int s) {
        if (i == rods.length) {
            return s == 5000 ? 0 : NINF;
        } else if (memo[i][s] != null) {  // !!!
            return memo[i][s];
        } else {
            int res = dfs(rods, i+1, s);  // 0
            res = Math.max(res, dfs(rods, i+1, s-rods[i]));  // -1
            res = Math.max(res, rods[i] + dfs(rods, i+1, s+rods[i])); // 1
            memo[i][s] = res;
            return res;
        }
    }
}
```



## ST & DP

```java
class Solution {
    public int tallestBillboard(int[] rods) {
        int N = rods.length;
        Map<Integer, Integer> le_map = make(Arrays.copyOfRange(rods, 0, N/2));
        Map<Integer, Integer> ri_map = make(Arrays.copyOfRange(rods, N/2, N));

        // 我们的目标是将两个状态合并，使得 delta 之和为 0
        // score 是所有正数之和，我们希望获得最高的 score
        // 对于每个 delta 我们只会记录具有最高 score 的状态
        int res = 0;
        for (int st: le_map.keySet()) {
            if (ri_map.containsKey(-st)) {
                res = Math.max(res, le_map.get(st) + ri_map.get(-st));
            }
        }
        return res;
    }

    Map<Integer, Integer> make(int[] arr) {
        Point[] dp = new Point[60000];
        int idx = 0;
        dp[idx++] = new Point(0, 0);
        for (int v: arr) {
            int len = idx;
            for (int i = 0; i < len; i++) {
                Point p = dp[i];
                dp[idx++] = new Point(p.x + v, p.y);
                dp[idx++] = new Point(p.x, p.y + v);
            }
        }

        Map<Integer, Integer> res = new HashMap();
        for (int i = 0; i < idx; ++i) {
            int a = dp[i].x, b = dp[i].y;
            res.put(a-b, Math.max(res.getOrDefault(a-b, 0), a));
        }
        return res;
    }

    static class Point {
        int x, y;
        public Point(int xx, int yy) {
            x = xx; y = yy;
        }
    }
}
```

