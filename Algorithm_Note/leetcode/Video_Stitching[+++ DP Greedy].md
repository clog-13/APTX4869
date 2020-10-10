#1024. 视频拼接
你将会获得一系列视频片段，这些片段来自于一项持续时长为 T 秒的体育赛事。这些片段可能有所重叠，也可能长度不一。

视频片段 clips[i] 都用区间进行表示：开始于 clips[i][0] 并于 clips[i][1] 结束。我们甚至可以对这些片段自由地再剪辑，例如片段 [0, 7] 可以剪切成 [0, 1] + [1, 3] + [3, 7] 三部分。

我们需要将这些片段进行再剪辑，并将剪辑后的内容拼接成覆盖整个运动过程的片段（[0, T]）。返回所需片段的最小数目，如果无法完成该任务，则返回 -1 。

**示例：**
输入：clips = [[0,2],[4,6],[8,10],[1,9],[1,5],[5,9]], T = 10
输出：3
解释：
我们选中 [0,2], [8,10], [1,9] 这三个片段。
然后，按下面的方案重制比赛片段：
将 [1,9] 再剪辑为 [1,2] + [2,8] + [8,9] 。
现在我们手上有 [0,2] + [2,8] + [8,10]，而这些涵盖了整场比赛 [0, 10]。

##贪心算法
```java
class Solution {
    public int videoStitching(int[][] clips, int T) {
        Comparator<int[]> c = new Comparator<>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0] == o2[0])
                    return o1[1] - o2[1];
                else
                    return o1[0] - o2[0];
            }
        };

        PriorityQueue<int[]> pq = new PriorityQueue<>(c);
        for(int[] i : clips) 
            pq.add(i);

        int res = 0, next = 0;
        while(next < T && !pq.isEmpty() && next>=pq.peek()[0]) {
            int cur = next;
            res++;
            while(!pq.isEmpty() && cur >= pq.peek()[0])
                next = Math.max(next, pq.poll()[1]);
        }

        return next < T ? -1 : res;
    }
}
```

##动态规划
```java
class Solution {
    public int videoStitching(int[][] clips, int T) {
        int[] dp = new int[T+1];
        Arrays.fill(dp, T+1);	// 只要大于 T 就行
		dp[0] = 0;		// T=0的时候，片段为0
        for(int i = 0; i <= T; i++)		// 计算到 T 需要的片段数
            for(int j = 0; j < clips.length; j++)	// 遍历 clips，枚举答案
                if(i >= clips[j][0] && i <= clips[j][1])
                    dp[i] = Math.min(dp[i], dp[clips[j][0]] + 1);

       return dp[T] == T+1 ? -1 : dp[T];	// T+1对应第二行的T+1
    }
}
```