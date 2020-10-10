# 1395. 统计作战单位数
 n 名士兵站成一排。每个士兵都有一个 独一无二 的评分 rating 。

每 3 个士兵可以组成一个作战单位，分组规则如下：

从队伍中选出下标分别为 i、j、k 的 3 名士兵，他们的评分分别为 rating[i]、rating[j]、rating[k]
作战单位需满足： rating[i] < rating[j] < rating[k] 或者 rating[i] > rating[j] > rating[k] ，其中  0 <= i < j < k < n
请你返回按上述条件可以组建的作战单位数量。每个士兵都可以是多个作战单位的一部分。

**示例：**
输入：rating = [2,5,3,4,1]
输出：3
解释：我们可以组建三个作战单位 (2,3,4)、(5,4,1)、(5,3,1) 。

### DP, Math
```java
class Solution {
    public int numTeams(int[] rating) {
        int N = rating.length;
        int[] up = new int[N];
        int[] down = new int[N];
        int res = 0;
        for (int i = 1; i < N; i++) {
            for (int j = i-1; j >= 0; j--) {
                if (rating[j] > rating[i]) {
                    res += down[j];
                    down[i]++;
                } else if (rating[j] < rating[i]) {
                    res += up[j];
                    up[i]++;
                }
            }
        }
        return res;
    }
}
```
```java
空间优化
class Solution {
    public int numTeams(int[] rating) {
        int res = 0, N = rating.length;
        for (int i = 0; i < N; i++) {
            int[] le = Cout(0, i, rating[i], rating);
            int[] ri = Cout(i, N, rating[i], rating);
            res += le[0] * ri[1] + le[1] * ri[0];
        }
        return res;
    }

    private int[] Cout(int s, int e, int k, int[] arr) {
        int[] res = new int[2];
        for (int i = s; i < e; i++) {
            if(arr[i] < k) res[0]++;
            if(arr[i] > k) res[1]++;
        }
        return res;
    }
}
```