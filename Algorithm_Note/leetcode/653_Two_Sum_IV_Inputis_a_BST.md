# 653. Two Sum IV - Input is a BST

Given the `root` of a Binary Search Tree and a target number `k`, return *`true` if there exist two elements in the BST such that their sum is equal to the given target*.

 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/09/21/sum_tree_1.jpg)

```
Input: root = [5,3,6,2,4,null,7], k = 9
Output: true
```



## HashSet

```go
func findTarget(root *TreeNode, k int) bool {
    set := make(map[int]bool)
    var helper func(root *TreeNode, k int) bool
    helper = func(root *TreeNode, k int) bool {
        if root == nil {
            return false
        }
        if set[root.Val] {
            return true
        } else {
            set[k - root.Val] = true
        }
        return helper(root.Left, k) || helper(root.Right, k)
    }
    return helper(root, k)
}
```



## DFS+HashSet

```go
func findTarget(root *TreeNode, k int) bool {
    var mp = map[int]bool{}
    return find(root, &mp, k)
}

func find(node *TreeNode, mp *map[int]bool, k int) bool {
	if node == nil {
		return false
	}
	if _, ok := (*mp)[k-node.Val]; ok {
		return true
	}
	(*mp)[node.Val] = true
	if node.Left != nil && find(node.Left, mp, k) {
		return true
	}
	if node.Right != nil && find(node.Right, mp, k) {
		return true
	}
	return false
}
```



## BST

```go
var arr []*TreeNode

func findTarget(root *TreeNode, k int) bool {
	if root == nil {
		return false
	}
	arr = make([]*TreeNode, 0)
	inSort(root)
	le, ri := 0, len(arr)-1
	for le < ri {
		sum := arr[le].Val + arr[ri].Val
		if sum == k {
			return true
		}
		if sum > k {
			ri--
		} else {
			le++
		}
	}
	return false
}

func inSort(rt *TreeNode) {
	if rt.Left != nil {
		inSort(rt.Left)
	}
	arr = append(arr, rt)
	if rt.Right != nil {
		inSort(rt.Right)
	}
}
```

