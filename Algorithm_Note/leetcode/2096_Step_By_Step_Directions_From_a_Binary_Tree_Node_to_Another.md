# 2096. Step-By-Step Directions From a Binary Tree Node to Another

You are given the `root` of a **binary tree** with `n` nodes. Each node is uniquely assigned a value from `1` to `n`. You are also given an integer `startValue` representing the value of the start node `s`, and a different integer `destValue` representing the value of the destination node `t`.

Find the **shortest path** starting from node `s` and ending at node `t`. Generate step-by-step directions of such path as a string consisting of only the **uppercase** letters `'L'`, `'R'`, and `'U'`. Each letter indicates a specific direction:

- `'L'` means to go from a node to its **left child** node.
- `'R'` means to go from a node to its **right child** node.
- `'U'` means to go from a node to its **parent** node.

Return *the step-by-step directions of the **shortest path** from node* `s` *to node* `t`.

 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/11/15/eg1.png)

```
Input: root = [5,1,2,3,null,6,4], startValue = 3, destValue = 6
Output: "UURL"
Explanation: The shortest path is: 3 → 1 → 5 → 2 → 6.
```



## Brute-Force

```go
func getDirections(root *TreeNode, startValue int, destValue int) string {
    que := []*TreeNode{nil}
    parents := map[*TreeNode]*TreeNode{}

    var dfs func(node, pa *TreeNode)
    dfs = func(node, pa *TreeNode) {
        if node == nil {return}
        parents[node] = pa
        if node.Val == startValue {que[0] = node}
        dfs(node.Left, node)
        dfs(node.Right, node)
    }
    dfs(root, nil)

    res := []byte{}
    vis := map[*TreeNode]bool{nil: true, que[0]: true}  // !!!
    type pair struct {
        fromNode *TreeNode
        dir byte
    }
    path := map[*TreeNode]pair{}
    for len(que) > 0 {
        node := que[0]
        que = que[1:]  // !!!
        if node.Val == destValue {
            for ; path[node].fromNode != nil; node = path[node].fromNode {
                res = append(res, path[node].dir)
            }
            break
        }
        if !vis[node.Left] {
            vis[node.Left] = true
            path[node.Left] = pair{node, 'L'}
            que = append(que, node.Left)
        }
        if !vis[node.Right] {
            vis[node.Right] = true
            path[node.Right] = pair{node, 'R'}
            que = append(que, node.Right)
        }
        if !vis[parents[node]] {
            vis[parents[node]] = true
            path[parents[node]] = pair{node, 'U'}
            que = append(que, parents[node])
        }
    }

    for i, n := 0, len(res); i < len(res)/2; i++ {
        res[i], res[n-i-1] = res[n-i-1], res[i]
    } 
    return string(res)
}
```



## LCA

```go
func getDirections(root *TreeNode, startValue, destValue int) string {
	var path []byte
	var dfs func(*TreeNode, int) bool
	dfs = func(node *TreeNode, tar int) bool {
		if node == nil {return false}
        if node.Val == tar {return true}
		
        path = append(path, 'L')
		if dfs(node.Left, tar) {return true}
		path[len(path)-1] = 'R'
		if dfs(node.Right, tar) {return true}
		path = path[:len(path)-1]
		return false
	}
    dfs(root, startValue)
    toStart := path

    path = nil
	dfs(root, destValue)
	toDest := path

	for len(toStart)>0 && len(toDest)>0 && toStart[0]==toDest[0] {
		toStart = toStart[1:]
		toDest = toDest[1:]
	}

	return strings.Repeat("U", len(toStart)) + string(toDest)
}
```

