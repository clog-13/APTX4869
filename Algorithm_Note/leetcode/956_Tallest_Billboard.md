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
        Map<Integer, Integer> pre = new HashMap<>();
        dict.put(0, 0);
        for (int i: rods) {
            pre.clear(); 
            pre.putAll(dict);
            for (int len: pre.keySet()) {  // getOrDefault(..., 0) 等于两边都不要
                dict.put(len+i, 
                    Math.max(dict.getOrDefault(len+i, 0), pre.get(len) + i));  // len+i：加在左边
                dict.put(len-i, 
                    Math.max(dict.getOrDefault(len-i, 0), pre.get(len)));  // len-i:加在右边等于减左边
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
    Integer[][] memo;  // Integer !!!
    int[] rods;
    public int tallestBillboard(int[] rods) {
        this.rods = rods;
        memo = new Integer[rods.length][10001];
        return (int) dfs(0, 5000);
    }

    public int dfs(int i, int ost) {
        if (i == rods.length) {
            return ost == 5000 ? 0 : NINF;
        } else if (memo[i][ost] != null) {  // null !!!
            return memo[i][ost];
        } else {
            int res = dfs(i+1, ost);  // 丢弃
            res = Math.max(res, dfs(i+1, ost-rods[i]));  // 加在le
            res = Math.max(res, rods[i] + dfs(i+1, ost+rods[i]));  // 加在ri
            memo[i][ost] = res;  // 记录le
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
        Map<Integer, Integer> leMap = make(Arrays.copyOfRange(rods, 0, N/2));
        Map<Integer, Integer> riMap = make(Arrays.copyOfRange(rods, N/2, N));

        int res = 0;
        for (int st: leMap.keySet()) {
            if (riMap.containsKey(-st)) {
                res = Math.max(res, leMap.get(st) + riMap.get(-st));
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
                dp[idx++] = new Point(p.le + v, p.ri);
                dp[idx++] = new Point(p.le, p.ri + v);
            }
        }

        Map<Integer, Integer> res = new HashMap();  // <le-ri的差值， 差值时的le(max)>
        for (int i = 0; i < idx; i++) {
            int le = dp[i].le, ri = dp[i].ri;
            res.put(le-ri, 
                Math.max(res.getOrDefault(le-ri, 0), le));  // !!! max, le
        }
        return res;
    }

    static class Point {
        int le, ri;
        public Point(int xx, int yy) {
            le = xx; ri = yy;
        }
    }
}
```

