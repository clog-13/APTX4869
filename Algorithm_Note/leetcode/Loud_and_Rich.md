# 851. 喧闹和富有
在一组 N 个人（编号为 0, 1, 2, ..., N-1）中，每个人都有不同数目的钱，以及不同程度的安静（quietness）。

为了方便起见，我们将编号为 x 的人简称为 "person x "。

如果能够肯定 person x 比 person y 更有钱的话，我们会说 richer[i] = [x, y] 。注意 richer 可能只是有效观察的一个子集。

另外，如果 person x 的安静程度为 q ，我们会说 quiet[x] = q 。

现在，返回答案 answer ，其中 answer[x] = y 的前提是，在所有拥有的钱不少于 person x 的人中，person y 是最安静的人（也就是安静值 quiet[y] 最小的人）。

**示例：**
输入：richer = [[1,0],[2,1],[3,1],[3,7],[4,3],[5,3],[6,3]], quiet = [3,2,5,4,6,1,7,0]
输出：[5,5,2,5,4,5,6,7]
解释：
answer[0] = 5，
person 5 比 person 3 有更多的钱，person 3 比 person 1 有更多的钱，person 1 比 person 0 有更多的钱。
唯一较为安静（有较低的安静值 quiet[x]）的人是 person 7，
但是目前还不清楚他是否比 person 0 更有钱。

answer[7] = 7，
在所有拥有的钱肯定不少于 person 7 的人中(这可能包括 person 3，4，5，6 以及 7)，
最安静(有较低安静值 quiet[x])的人是 person 7。

## 树形DFS
```java
class Solution {
    ArrayList<Integer>[] graph;
    int[] res, quiet;
    public int[] loudAndRich(int[][] richer, int[] quiet) {
        int N = quiet.length;
        graph = new ArrayList[N];
        res = new int[N];
        this.quiet = quiet;

        for (int i = 0; i < N; i++)
            graph[i] = new ArrayList<>();
        for (int[] r : richer)
            graph[r[1]].add(r[0]);

        Arrays.fill(res, -1);

        for (int i = 0; i < N; i++)
            dfs(i);
        return res;
    }

    private int dfs(int idx) {
        if(res[idx] == -1) {
            res[idx] = idx;
            for (int i : graph[idx]) {
                i = dfs(i);
                if (quiet[i] < quiet[res[idx]]) res[idx] = i;
            }
        }
        return res[idx];
    }
}
```

## 链表节点DFS
```java
class Solution {
    int[] res, quiet;
    Node[] root;
    boolean[] vis;
    public int[] loudAndRich(int[][] richer, int[] quiet) {
        int N = quiet.length;
        this.quiet = quiet;        
        res = new int[N];        
        root = new Node[N];
        vis = new boolean[N];
        
        for (int i = 0; i < N; i++)
            root[i] = new Node(i);
        
        for (int[] r : richer) {
            Node tmp = new Node(r[0]);
            tmp.next = root[r[1]].next;
            root[r[1]].next = tmp;
        }
        
        for (int i = 0; i < N; i++) dfs(i);
        return res;
    }
    
    private int dfs(int idx) {
        if (!vis[idx]) {
            res[idx] = idx;
            for (Node p = root[idx].next; p != null; p = p.next) {
                int tmp = dfs(p.val);
                if (quiet[tmp] < quiet[res[idx]]) {
                    res[idx] = tmp;
                }
            }
        }
        vis[idx] = true;
        return res[idx];
    }
}

class Node {
    int val;
    Node next;
    public Node(int val) {
        this.val = val;
    }
}
```