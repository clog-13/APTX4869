# 1136. 平行课程
已知有 N 门课程，它们以 1 到 N 进行编号。

给你一份课程关系表 relations[i] = [X, Y]，用以表示课程 X 和课程 Y 之间的先修关系：课程 X 必须在课程 Y 之前修完。

假设在一个学期里，你可以学习任何数量的课程，但前提是你已经学习了将要学习的这些课程的所有先修课程。

请你返回学完全部课程所需的最少学期数。

如果没有办法做到学完全部这些课程的话，就返回 -1。

**示例：**
输入：N = 3, relations = [[1,3],[2,3]]
输出：2
解释：
在第一个学期学习课程 1 和 2，在第二个学期学习课程 3。

## 拓扑排序
```java
class Solution {
    public int minimumSemesters(int N, int[][] relations) {
        List<Integer>[] graph = new List[N+1];
        int[] degree = new int[N+1];
        for (int i = 1; i <= N; i++)
            graph[i] = new ArrayList<>();
        for (int[] r : relations) {
            graph[r[1]].add(r[0]);
            degree[r[0]]++;
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++)
            if (degree[i] == 0) queue.add(i);
        if (queue.isEmpty()) return -1;
        int res = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int tmp = queue.poll();
                for (int g : graph[tmp])
                    if (--degree[g] == 0) queue.add(g);
            }
            res++;
        }
        for (int i = 1; i <= N; i++)
            if (degree[i] != 0) return -1;
        return res;
    }
}
```