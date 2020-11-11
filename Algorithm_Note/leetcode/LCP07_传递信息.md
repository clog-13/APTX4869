# LCP 07. 传递信息

小朋友 A 在和 ta 的小伙伴们玩传信息游戏，游戏规则如下：

1. 有 n 名玩家，所有玩家编号分别为 0 ～ n-1，其中小朋友 A 的编号为 0
2. 每个玩家都有固定的若干个可传信息的其他玩家（也可能没有）。传信息的关系是单向的（比如 A 可以向 B 传信息，但 B 不能向 A 传信息）。
3. 每轮信息必须需要传递给另一个人，且信息可重复经过同一个人

给定总玩家数 `n`，以及按 `[玩家编号,对应可传递玩家编号]` 关系组成的二维数组 `relation`。返回信息从小 A (编号 0 ) 经过 `k` 轮传递到编号为 n-1 的小伙伴处的方案数；若不能到达，返回 0。

**示例：**

> 输入：`n = 5, relation = [[0,2],[2,1],[3,4],[2,3],[1,4],[2,0],[0,4]], k = 3`
>
> 输出：`3`
>
> 解释：信息从小 A 编号 0 处开始，经 3 轮传递，到达编号 4。共有 3 种方案，分别是 0->2->0->4， 0->2->1->4， 0->2->3->4。

## DP

```java
class Solution {
    public int numWays(int n, int[][] relation, int k) {
        int[] dp = new int[n];
        dp[0] = 1;
        for (int t = 0; t < k; t++) {
            int[] next = new int[n];
            for (int[] r : relation) {
                next[r[1]] += dp[r[0]];
            }
            dp = next;
        }
        return dp[n-1];
    }
}
```

```java
class Solution {
    public int numWays(int n, int[][] relation, int k) {
        int[][] dp = new int[k+1][n];
        dp[0][0] = 1;
        for (int t = 0; t < k; t++) {
            for (int[] r : relation) {
                dp[t+1][r[1]] += dp[t][r[0]];
            }
        }
        return dp[k][n-1];
    }
}
```



## DFS

```java
class Solution {
    int res = 0;
    Map<Integer, List<Integer>> map = new HashMap<>();
    public int numWays(int n, int[][] relation, int k) {
        for (int[] temp : relation) {
            if (!map.containsKey(temp[0])) map.put(temp[0], new ArrayList<>());
            map.get(temp[0]).add(temp[1]);
        }
        backTracking(0, 0, k, n);
        return res;
    }

    private void backTracking(int cur, int curPerson, int k, int n) {
        if (cur == k) {
            if (curPerson == n-1) res++;
            return;
        }
        if (!map.containsKey(curPerson)) return;
        for (int i : map.get(curPerson)) {
            backTracking(cur + 1, i, k, n);
        }
    }
}
```