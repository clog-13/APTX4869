# 1655. 分配重复整数

给你一个长度为 `n` 的整数数组 `nums` ，这个数组中至多有 `50` 个不同的值。同时你有 `m` 个顾客的订单 `quantity` ，其中，整数 `quantity[i]` 是第 `i` 位顾客订单的数目。请你判断是否能将 `nums` 中的整数分配给这些顾客，且满足：

- 第 `i` 位顾客 **恰好** 有 `quantity[i]` 个整数。
- 第 `i` 位顾客拿到的整数都是 **相同的** 。
- 每位顾客都满足上述两个要求。

如果你可以分配 `nums` 中的整数满足上面的要求，那么请返回 `true` ，否则返回 `false` 。

 

**示例 1：**

```
输入：nums = [1,2,3,4], quantity = [2]
输出：false
解释：第 0 位顾客没办法得到两个相同的整数。
```

**示例 2：**

```
输入：nums = [1,2,3,3], quantity = [2]
输出：true
解释：第 0 位顾客得到 [3,3] 。整数 [1,2] 都没有被使用。
```



## 状压DP

```java
class Solution {
    public boolean canDistribute(int[] nums, int[] quantity) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) map.put(n, map.getOrDefault(n, 0) + 1);
        int[] cout = new int[map.size()]; int idx = 0;  
        for (Integer value : map.values()) cout[idx++] = value;  // 计数统计（每种数的频次）
        
        int N = cout.length, M = quantity.length;
        int[] sum = new int[1<<M];
        for (int state = 1; state < (1<<M); state++) {  // 顾客组合状态
            for (int i = 0; i < M; i++) {
                if ((state & 1<<i) > 0) {  // sum[state]: state状态下需要的（同一种数）总数
                    sum[state] = sum[state - (1<<i)] + quantity[i];
                    break;  // state状态下的顾客用 同一种数
                }
            }
        }

        boolean[][] dp = new boolean[N][1<<M];  // cnt数组中的前 i 个元素，能否满足顾客的子集合 state 的订单需求。
        for (int i = 0; i < N; i++) dp[i][0] = true;
        for (int i = 0; i < N; i++) {  // 遍历cout
            for (int state = 0; state < (1<<M); state++) {  // 遍历状态
                if (i>0 && dp[i-1][state]) {  // 前 i-1 个元素个能满足, 直接剪枝
                    dp[i][state] = true;
                    continue;
                }
                for (int s = state; s != 0; s = (s-1)&state) { // 这里是遍历只去除一位的每种情况(diff i&-i)
                    int pre = state-s;  // 被去除的一位
                    boolean last = i==0 ? pre==0 : dp[i-1][pre];  // 如果第一个数,pre必须是0(没有pre)
                    boolean need = cout[i] >= sum[s];  // s状态下顾客用同一种数(第i种数)
                    if (last && need) {
                        dp[i][state] = true;
                        break;
                    }
                }
            }
        }

        return dp[N-1][(1<<M)-1];
    }
}
```



## DFS

```java
class Solution {
    public boolean canDistribute(int[] nums, int[] quantity) {
        int[] cout = new int[1001];
        for (int i = 0; i < nums.length; i++) {
            cout[nums[i]]++;
        }
        Arrays.sort(cout);
        int[] couts = Arrays.copyOfRange(cout, cout.length - 50, cout.length);  // ori,from,to
        Arrays.sort(quantity);
        return backTrack(couts, quantity, quantity.length - 1);

    }

    public boolean backTrack(int[] couts, int[] quantity, int idx) {
        if (idx < 0) return true;
        for (int i = 0; i < couts.length; i++) {
            if (i != 0 && couts[i] == couts[i - 1]) continue; // 剪枝
            if (couts[i] >= quantity[idx]) {
                couts[i] -= quantity[idx];
                if (backTrack(couts, quantity, idx - 1)) return true;
                couts[i] += quantity[idx];
            }
        }
        return false;
    }
}
```

