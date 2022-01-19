# 113. Path Sum II

Given the `root` of a binary tree and an integer `targetSum`, return *all **root-to-leaf** paths where the sum of the node values in the path equals* `targetSum`*. Each path should be returned as a list of the node **values**, not node references*.

A **root-to-leaf** path is a path starting from the root and ending at any leaf node. A **leaf** is a node with no children.

 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/01/18/pathsumii1.jpg)

```
Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
Output: [[5,4,11,2],[5,8,4,5]]
Explanation: There are two paths whose sum equals targetSum:
5 + 4 + 11 + 2 = 22
5 + 8 + 4 + 5 = 22
```



## DFS

```go
func pathSum(root *TreeNode, targetSum int) (res [][]int) {
    path := []int{}
    var dfs func(*TreeNode, int)
    dfs = func(node *TreeNode, tar int) {
        if node == nil {
            return
        }
        tar -= node.Val
        path = append(path, node.Val)
        defer func() { path = path[:len(path)-1] }()  // !!!
        if node.Left == nil && node.Right == nil && tar == 0 {
            res = append(res, append([]int(nil), path...))
            return
        }
        dfs(node.Left, tar)
        dfs(node.Right, tar)
    }
    dfs(root, targetSum)
    return
}
```

```java
class Solution {
    List<List<Integer>> res = new LinkedList<List<Integer>>();
    Deque<Integer> path = new LinkedList<Integer>();

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        dfs(root, targetSum);
        return res;
    }

    void dfs(TreeNode root, int tar) {
        if (root == null) {
            return;
        }
        path.offerLast(root.val);
        tar -= root.val;
        if (root.left == null && root.right == null && tar == 0) {
            res.add(new LinkedList<Integer>(path));
        }
        dfs(root.left, tar);
        dfs(root.right, tar);
        path.pollLast();
    }
}
```



## BFS

```go
type pair struct {
    node *TreeNode
    tar int
}

func pathSum(root *TreeNode, targetSum int) (res [][]int) {
    if root == nil {
        return
    }

    parent := map[*TreeNode]*TreeNode{}
    getPath := func(node *TreeNode) (path []int) {
        for ; node != nil; node = parent[node] {
            path = append(path, node.Val)
        }
        for i, j := 0, len(path)-1; i < j; i++ {
            path[i], path[j] = path[j], path[i]
            j--
        }
        return
    }

    que := []pair{{root, targetSum}}
    for len(que) > 0 {
        cur := que[0]
        que = que[1:]
        node := cur.node
        tar := cur.tar - node.Val
        if node.Left == nil && node.Right == nil {
            if tar == 0 {
                res = append(res, getPath(node))
            }
        } else {
            if node.Left != nil {
                parent[node.Left] = node
                que = append(que, pair{node.Left, tar})
            }
            if node.Right != nil {
                parent[node.Right] = node
                que = append(que, pair{node.Right, tar})
            }
        }
    }
    return
}
```

```java
class Solution {
    List<List<Integer>> res = new LinkedList<List<Integer>>();
    Map<TreeNode, TreeNode> map = new HashMap<TreeNode, TreeNode>();

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return res;
        }

        Queue<TreeNode> que = new LinkedList<TreeNode>();
        Queue<Integer> queVal = new LinkedList<Integer>();
        que.offer(root);
        queVal.offer(0);

        while (!que.isEmpty()) {
            TreeNode node = que.poll();
            int val = queVal.poll() + node.val;

            if (node.left == null && node.right == null) {
                if (val == targetSum) {
                    getPath(node);
                }
            } else {
                if (node.left != null) {
                    map.put(node.left, node);
                    que.offer(node.left);
                    queVal.offer(val);
                }
                if (node.right != null) {
                    map.put(node.right, node);
                    que.offer(node.right);
                    queVal.offer(val);
                }
            }
        }
        return res;
    }

    void getPath(TreeNode node) {
        List<Integer> tmp = new LinkedList<Integer>();
        while (node != null) {
            tmp.add(node.val);
            node = map.get(node);
        }
        Collections.reverse(tmp);
        res.add(new LinkedList<Integer>(tmp));
    }
}
```

