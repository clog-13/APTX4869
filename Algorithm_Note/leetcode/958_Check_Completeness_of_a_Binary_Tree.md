# 958. Check Completeness of a Binary Tree

Given the `root` of a binary tree, determine if it is a *complete binary tree*.

In a **[complete binary tree](http://en.wikipedia.org/wiki/Binary_tree#Types_of_binary_trees)**, every level, except possibly the last, is completely filled, and all nodes in the last level are as far left as possible. It can have between `1` and `2h` nodes inclusive at the last level `h`.

 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2018/12/15/complete-binary-tree-1.png)

```
Input: root = [1,2,3,4,5,6]
Output: true
Explanation: Every level before the last is full (ie. levels with node-values {1} and {2, 3}), and all nodes in the last level ({4, 5, 6}) are as far left as possible.
```



## BFS

```java
class Solution {
    public boolean isCompleteTree(TreeNode root) {
        LinkedList<TreeNode> que = new LinkedList<>();
        TreeNode cur;
        que.addLast(root);
        while ((cur = que.removeFirst()) != null) {
            que.addLast(cur.left);
            que.addLast(cur.right);
        }
        while (!que.isEmpty()) {
            if (que.removeLast() != null) {
                return false;
            }
        }
        return true;
    }
}
```

```java
class Solution {
    public boolean isCompleteTree(TreeNode root) {
        if (root == null) return true;
        Queue<TreeNode> que = new LinkedList<>();
        que.add(root);
        TreeNode temp;
        boolean flag = false;
        while (!que.isEmpty()) {
            temp = que.remove();
            if (temp == null){
                flag = true;
                continue;
            }
            if (flag) return false;
            que.add(temp.left);
            que.add(temp.right);
        }
        return true;
    }
}
```



## DFS

```java
class Solution {
    int max, count;
    public boolean isCompleteTree(TreeNode root) {
        cal(root,1);
        return max == count;
    }
    private void cal(TreeNode root,int index){
        if(root==null)return;
        if(index>max){  //按照节点推测
            max=index;
        }
        count++;    //累加计数
        cal(root.left,2*index);
        cal(root.right,2*index+1);
    }
}
```

