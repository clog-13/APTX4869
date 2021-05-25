# 1605. Find Valid Matrix Given Row and Column Sums

You are given two arrays `rowSum` and `colSum` of non-negative integers where `rowSum[i]` is the sum of the elements in the `ith` row and `colSum[j]` is the sum of the elements of the `jth` column of a 2D matrix. In other words, you do not know the elements of the matrix, but you do know the sums of each row and column.

Find any matrix of **non-negative** integers of size `rowSum.length x colSum.length` that satisfies the `rowSum` and `colSum` requirements.

Return *a 2D array representing **any** matrix that fulfills the requirements*. It's guaranteed that **at least one** matrix that fulfills the requirements exists.

 

**Example:**

```
Input: rowSum = [3,8], colSum = [4,7]
Output: [[3,0],
         [1,7]]
Explanation:
0th row: 3 + 0 = 3 == rowSum[0]
1st row: 1 + 7 = 8 == rowSum[1]
0th column: 3 + 1 = 4 == colSum[0]
1st column: 0 + 7 = 7 == colSum[1]
The row and column sums match, and all matrix elements are non-negative.
Another possible matrix is: [[1,2],
                             [3,5]]
```

## 贪心

题目是保证矩阵成立的（从解题思维来说）

```java
class Solution {
    public int[][] restoreMatrix(int[] rowSum, int[] colSum) {
        int N = rowSum.length, M = colSum.length;
        int[][] res = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                res[i][j] = Math.min(rowSum[i], colSum[j]);
                rowSum[i] -= res[i][j];
                colSum[j] -= res[i][j];
            }
        }
        return res;
    }
}
```

下面代码效率更高，也更能理解代码思想：

```java
class Solution {
    public int[][] restoreMatrix(int[] rowSum, int[] colSum) {
        int N = rowSum.length, M = colSum.length;
        int[][] res = new int[N][M];
        int i = 0, j = 0;
        while(i < N && j < M) {
            res[i][j] = Math.min(rowSum[i], colSum[j]);

            if (rowSum[i] > colSum[j]) {    	// 往后都为 0
                rowSum[i] -= colSum[j++];
            } else if (colSum[j] > rowSum[i]) { // 往后都为 0
                colSum[j] -= rowSum[i++];
            } else {
                i++; j++;
            }
        }
        return res;
    }
}
```
