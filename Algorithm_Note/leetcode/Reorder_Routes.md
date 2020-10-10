# 1466. 重新规划路线
n 座城市，从 0 到 n-1 编号，其间共有 n-1 条路线。因此，要想在两座不同城市之间旅行只有唯一一条路线可供选择（路线网形成一颗树）。去年，交通运输部决定重新规划路线，以改变交通拥堵的状况。

路线用 connections 表示，其中 connections[i] = [a, b] 表示从城市 a 到 b 的一条有向路线。

今年，城市 0 将会举办一场大型比赛，很多游客都想前往城市 0 。

请你帮助重新规划路线方向，使每个城市都可以访问城市 0 。返回需要变更方向的最小路线数。

题目数据 保证 每个城市在重新规划路线方向后都能到达城市 0 。

**示例：**
输入：n = 6, connections = [[0,1],[1,3],[2,3],[4,0],[4,5]]
输出：3
解释：更改以红色显示的路线的方向，使每个城市都可以到达城市 0 。
![](../pic/Reorder_Routes.png)

## BFS
```java
class Solution {
    public int minReorder(int n, int[][] C) {
        List<Integer>[] graph = new ArrayList[n];
        for(int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for(int i = 0; i < C.length; i++) {
            graph[C[i][0]].add(i);	// C[i][0]节点在 C[i] 处有 链接的信息
        	graph[C[i][1]].add(i);	// C[i][1]节点在 C[i] 处有 链接的信息
        }

        boolean[] vis = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        int res = 0;
        while(!queue.isEmpty()) {
            int cur = queue.poll();
            for(int i : graph[cur]) {   // 遍历无向表 中的节点
                if(vis[i]) continue;
                vis[i] = true;

                int s = C[i][0];
                int o = C[i][1];
                if(cur == s) res++;
                queue.offer((cur == s ? o : s));
            }
        }
        return res;
    }
}
```

## 排序+Set
```java
class Solution {
    public int minReorder(int n, int[][] cons) {
        Arrays.sort(cons, (c1,  c2)-> {
            if(c1[0] != c2[0]) return c1[0] - c2[0];
            else return c1[1] - c2[1];
        });
        Set<Integer> set = new HashSet<>(n);	// set保存被指向的 节点
        set.add(0);

        int res = 0;
        for (int[] r : cons) {
        	if (set.contains(r[0])) {	// 一个节点只能链接两个节点，所以set包含节点又在 r[0] 时 需要变向
                res++;
                set.add(r[1]);
            }else if(set.contains(r[1])) {
                set.add(r[0]);
            }
        }
        return res;
    }
}
```