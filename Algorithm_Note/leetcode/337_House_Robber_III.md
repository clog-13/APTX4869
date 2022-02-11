# 337. House Robber III

The thief has found himself a new place for his thievery again. There is only one entrance to this area, called `root`.

Besides the `root`, each house has one and only one parent house. After a tour, the smart thief realized that all houses in this place form a binary tree. It will automatically contact the police if **two directly-linked houses were broken into on the same night**.

Given the `root` of the binary tree, return *the maximum amount of money the thief can rob **without alerting the police***.

 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/03/10/rob1-tree.jpg)

```
Input: root = [3,2,3,null,3,null,1]
Output: 7
Explanation: Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
```



## DFS

```java
class Solution {
    public int rob(TreeNode root) {
        if (root == null) return 0;

        int money = root.val;
        if (root.left != null) {
            money += (rob(root.left.left) + rob(root.left.right));
        }

        if (root.right != null) {
            money += (rob(root.right.left) + rob(root.right.right));
        }

        return Math.max(money, rob(root.left) + rob(root.right));
    }
}
```



## DFS + DP

```java
class Solution {
    public int rob(TreeNode root) {
        int[] rootStatus = dfs(root);
        return Math.max(rootStatus[0], rootStatus[1]);
    }

    public int[] dfs(TreeNode node) {
        if (node == null) return new int[]{0, 0};

        int[] le = dfs(node.left), ri = dfs(node.right);
        int cur = node.val + le[1] + ri[1];
        int son = Math.max(le[0], le[1]) + Math.max(ri[0], ri[1]);
        return new int[]{cur, son};
    }
}
```



```java
class Solution {
    public int rob(TreeNode root) {
        int[] res = helper(root);
        return Math.max(res[0], res[1]);
    }
    
    int[] helper(TreeNode node) {
        if (node == null) return new int[]{0,0};
        if (node.left == null && node.right == null) {
            return new int[]{node.val, 0};
        }
        
        int[] le = helper(node.left), ri = helper(node.right);
        
        int[] res = new int[2];  // 0：选当前节点， 1：不选当前节点
        res[0] = Math.max(node.val+le[1]+ri[1], le[0]+ri[0]);
        res[1] = le[0]+ri[0];
        return res;
    }
}
```

