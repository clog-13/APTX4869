# 1798. 你能构造出连续值的最大数目

给你一个长度为 `n` 的整数数组 `coins` ，它代表你拥有的 `n` 个硬币。第 `i` 个硬币的值为 `coins[i]` 。如果你从这些硬币中选出一部分硬币，它们的和为 `x` ，那么称，你可以 **构造** 出 `x` 。

请返回从 `0` 开始（**包括** `0` ），你最多能 **构造** 出多少个连续整数。

你可能有多个相同值的硬币。

 

**示例：**

```
输入：coins = [1,3]
输出：2
解释：你可以得到以下这些值：
- 0：什么都不取 []
- 1：取 [1]
从 0 开始，你可以构造出 2 个连续整数。
```



## 数学

sum代表能构成 [0, sum] 的数，如果 c < sum，则加入 c 就可以构成 [0, sum + c] 的数，否则返回 sum+1

```java
class Solution {
    public int getMaximumConsecutive(int[] coins) {
        Arrays.sort(coins);
        int sum = 0;
        for (int c: coins) {
            if (c <= sum+1) sum += c;
            else break;
        }
        return sum+1;
    }
}
```



## DFS(TLE)

```java
class Solution {
    Set<Integer> set = new TreeSet<> ();
    public int getMaximumConsecutive(int[] coins) {
        dfs(coins, 0, 0);

        set.add(0);
        List<Integer> listAns = new ArrayList<>(set);
        
        
        int tmp = 0, res = 1, i = 0;  // 求以任意数字开始最长序列
        while (i + 1 < listAns.size()) {
            if (listAns.get(i) + 1 == listAns.get(i + 1)) {
                if (tmp == 0) tmp = 1;
                tmp++;
                res = Math.max(tmp, res);
            } else {
                tmp = 0;
            }
            i++;
        }
        return res;
    }
    
    private void dfs(int[] coins, int index, int curSum) {
        if (index >= coins.length) return;
        dfs(coins, index + 1, curSum);
        curSum += coins[index];
        set.add(curSum);
        dfs(coins, index + 1, curSum);
    }
}
```

