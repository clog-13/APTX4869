#947. 移除最多的同行或同列石头

在二维坐标系中给定若干个石头stones。

对于一个石头i来说，当存在另一个石头j与它处于同一行或同一列时，我们才能将i移除掉。我们最多能移除多少个石头？

##并查集
```java
class Solution {
    public int removeStones(int[][] stones) {
        int N = stones.length;
        DSU dsu = new DSU(20000);

        for (int[] stone: stones)
            dsu.union(stone[0], stone[1] + 10000);  // union(x, y);

        Set<Integer> sets = new HashSet();
        for (int[] stone: stones)           // 过滤不能删的 x
            sets.add(dsu.find(stone[0]));   // 过滤不能删的 y

        return N - sets.size();     // set.sizes() 代表 不能删的 石头
    }
}

class DSU {
    int[] parent;
    public DSU(int N) {
        parent = new int[N];
        for (int i = 0; i < N; ++i)
            parent[i] = i;
    }
    public int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);    // when parent[x] == x END
        return parent[x];
    }
    public void union(int x, int y) {
        parent[find(x)] = find(y);
    }
}
```
**复杂度分析**

- 时间复杂度： O(N × logN)，其中 N 是 stones 的大小。

- 空间复杂度： O(N)。

##DFS(???)
```java
class Solution {
    public static int removeStones(int[][] stones) {
        int N = stones.length;

        // graph[i][0] = the length of the 'list' graph[i][1:]
        int[][] graph = new int[N][N];
        for(int i = 0; i < N; i++)
            for(int j = i+1; j < N; j++)
                if(stones[i][0]==stones[j][0] || stones[i][1]==stones[j][1]) {
                    graph[i][++graph[i][0]] = j;
                    graph[j][++graph[j][0]] = i;
                }

        for(int[] g : graph) {
            for(int i : g)
                System.out.print(i+",");
            System.out.println();
        }

        int ans = 0;
        boolean[] seen = new boolean[N];
        for (int i = 0; i < N; i++) if (!seen[i]) {
            Stack<Integer> stack = new Stack();
            stack.push(i);
            seen[i] = true;
            ans--;
            while (!stack.isEmpty()) {
                int node = stack.pop();
                ans++;
                for (int k = 1; k <= graph[node][0]; ++k) {
                    int nei = graph[node][k];
                    if (!seen[nei]) {
                        stack.push(nei);
                        seen[nei] = true;
                    }
                }
            }
        }
        return ans;
    }
}
```
**复杂度分析**

- 时间复杂度： O(N^2)，其中 N 是石头的数量。

- 空间复杂度： O(N^2)。
