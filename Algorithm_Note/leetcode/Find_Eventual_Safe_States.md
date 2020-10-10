#找到最终的安全状态
在有向图中, 我们从某个节点和每个转向处开始, 沿着图的有向边走。 如果我们到达的节点是终点 (即它没有连出的有向边), 我们停止。

现在, 如果我们最后能走到终点，那么我们的起始节点是最终安全的。 更具体地说, 存在一个自然数 K,  无论选择从哪里开始行走, 我们走了不到 K 步后必能停止在一个终点。

哪些节点最终是安全的？ 结果返回一个有序的数组。

该有向图有 N 个节点，标签为 0, 1, ..., N-1, 其中 N 是 graph 的节点数.  图以以下的形式给出: graph[i] 是节点 j 的一个列表，满足 (i, j) 是图的一条有向边。

示例：
输入：graph = [[1,2],[2,3],[5],[0],[5],[],[]]
输出：[2,4,5,6]
这里是上图的示意图：
![](../pic/Find_Eventual_Safe_States.png)

##拓扑排序（反向图）
```java
class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int N = graph.length;
        List<Set<Integer>> list = new ArrayList<>();
        List<Set<Integer>> rist = new ArrayList<>();
        for (int i = 0; i < N; ++i) {
            list.add(new HashSet());
            rist.add(new HashSet());
        }
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 0; i < N; i++) {
            if(graph[i].length == 0)
                queue.offer(i);
            for(int j : graph[i]) {
                list.get(i).add(j);
                rist.get(j).add(i);
            }
        }
        boolean[] safe = new boolean[N];
        while(!queue.isEmpty()) {
            int n = queue.poll();
            safe[n] = true;		// 只要是入队列的必定是安全的
            for(int i : rist.get(n)) {
                list.get(i).remove(n);
                if(list.get(i).isEmpty()) {
                    queue.offer(i);
                }
            }
        }
        List<Integer> res = new ArrayList<>();
        for(int i = 0; i < N; i++) if(safe[i])
            res.add(i);
        return res;
    }
}
```

##DFS
```java
class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int[] vis = new int[graph.length];
        List<Integer> res = new ArrayList<>();
        for(int i = 0; i < graph.length; i++) {
            if(dfs(i, vis, graph))
                res.add(i);
        }

        return res;
    }

	// vis: 0 = 未访问， 1 = 正在访问dfs中 2 = 安全
    // vis: 0 = none, 1 = ing, 2 = safe
    private boolean dfs(int n, int[] vis, int[][] graph) {
        if(vis[n] > 0)
            return vis[n] == 2;

        vis[n] = 1;
        for(int i : graph[n]) {
            if(vis[i] == 2)
                continue;
            if(vis[i] == 1 || !dfs(i, vis, graph))
                return false;
        }
        vis[n] = 2;
        return true;
    }
}
```