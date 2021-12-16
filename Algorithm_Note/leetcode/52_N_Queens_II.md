# 52. N-Queens II

The **n-queens** puzzle is the problem of placing `n` queens on an `n x n` chessboard such that no two queens attack each other.

Given an integer `n`, return *the number of distinct solutions to the **n-queens puzzle***.

 

**Example:**

![img](https://assets.leetcode.com/uploads/2020/11/13/queens.jpg)

```
Input: n = 4
Output: 2
Explanation: There are two distinct solutions to the 4-queens puzzle as shown.
```



## DFS + BIT OP

```c++
class Solution {
public:
    int totalNQueens(int n) {
        return solve(0, 0, 0, 0, n);
    }

    int solve(int row, int cols, int diag1, int diag2, int n) {
        if (row == n) return 1;
        else {
            int res = 0;
            int able = (~(cols|diag1|diag2)) & ((1<<n)-1);
            while (able != 0) {
                int pos = able&-able;
                able -= able&-able;
                res += solve(row+1, cols|pos, (diag1|pos)<<1, (diag2|pos)>>1, n);
            }
            return res;
        }
    }
};
```

