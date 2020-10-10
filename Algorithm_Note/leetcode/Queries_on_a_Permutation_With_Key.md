# 1409. 查询带键的排列
给你一个待查数组 queries ，数组中的元素为 1 到 m 之间的正整数。 请你根据以下规则处理所有待查项 queries[i]（从 i=0 到 i=queries.length-1）：

一开始，排列 P=[1,2,3,...,m]。
对于当前的 i ，请你找出待查项 queries[i] 在排列 P 中的位置（下标从 0 开始），然后将其从原位置移动到排列 P 的起始位置（即下标为 0 处）。注意， queries[i] 在 P 中的位置就是 queries[i] 的查询结果。
请你以数组形式返回待查数组  queries 的查询结果。

**示例：**
输入：queries = [3,1,2,1], m = 5
输出：[2,1,2,1]

解释：待查数组 queries 处理如下：
对于 i=0: queries[i]=3, P=[1,2,3,4,5], 3 在 P 中的位置是 2，接着我们把 3 移动到 P 的起始位置，得到 P=[3,1,2,4,5]
对于 i=1: queries[i]=1, P=[3,1,2,4,5], 1 在 P 中的位置是 1，接着我们把 1 移动到 P 的起始位置，得到 P=[1,3,2,4,5]
对于 i=2: queries[i]=2, P=[1,3,2,4,5], 2 在 P 中的位置是 2，接着我们把 2 移动到 P 的起始位置，得到 P=[2,1,3,4,5]
对于 i=3: queries[i]=1, P=[2,1,3,4,5], 1 在 P 中的位置是 1，接着我们把 1 移动到 P 的起始位置，得到 P=[1,2,3,4,5]

因此，返回的结果数组为 [2,1,2,1] 。

## 树状数组
```java
class Solution {
    int[] tree;

    private int lowbit(int x) {
        return x & (-x);
    }

    private void update(int idx, int x) {
        while (idx < tree.length) {
            tree[idx] += x;
            idx += lowbit(idx);
        }
    }

    private int getSum(int idx) {
        int res = 0;
        while (idx > 0) {
            res += tree[idx];
            idx -= lowbit(idx);
        }
        return res;
    }

	// 这里有个很精髓的地方， queries的长度代表需要移到头部的数量
    public int[] processQueries(int[] queries, int m) {
        int N = queries.length;

        // 树状数组，数组从1-i的区间和getSum(i)代表在i前面有多少个数（包括自身）
        // 所以一个数对应到P中的真实索引就是getSum(i)-1
        tree = new int[N + m + 1];
        int[] idxData = new int[m+1];	// 存储 常规数组 在 树状数组 中相应的下标
        for (int i = 1; i <= m; i++) {	// 初始化
            idxData[i] = i+N;
            update(i+N, 1);
        }

        int[] res = new int[N];
        for (int i = 0; i < N; i++) {
            int tmp = idxData[queries[i]];
            res[i] = getSum(tmp) - 1;

            update(tmp, -1);
            idxData[queries[i]] = N - i;
            update(N - i, 1);	// 这是前缀和数组，上面update(tmp,-1)被修改的地方就抵消了
        }
        return res;
    }
}
```

## 桶计数
```java
class Solution {
    public int[] processQueries(int[] queries, int m) {
        int[] cout = new int[m];
        for (int i = 0; i < m; i++)
            cout[i] = i;

        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int cur = queries[i]-1;
            res[i] = cout[cur];
            for(int j = 0; j < m; j++)	// 更新被查询的数前面的数的下标（+1）
                if(cout[j] < res[i]) cout[j]++;
            cout[cur] = 0;	// 被查询的数下标更新为零
        }
        return res;
    }
}
```