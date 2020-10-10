# 1504. 统计全 1 子矩形
给你一个只包含 0 和 1 的 rows * columns 矩阵 mat ，请你返回有多少个 子矩形 的元素全部都是 1 。

**示例：**
输入：
mat = [
[1,0,1],
[1,1,0],
[1,1,0]]
输出：13
解释：
有 6 个 1x1 的矩形。
有 2 个 1x2 的矩形。
有 3 个 2x1 的矩形。
有 1 个 2x2 的矩形。
有 1 个 3x1 的矩形。
矩形数目总共 = 6 + 2 + 3 + 1 + 1 = 13 。

## DP
起始的二维数组
![](../pic/Count_Submatrices_With_All_Ones01.png)
对其使用“按位与”进行压缩
![](../pic/Count_Submatrices_With_All_Ones02.png)
第二次统计的结果, 这里我们可以发现第二次的统计是2xn的，既包含了2X1的情况也包含了2x2的情况...
![](../pic/Count_Submatrices_With_All_Ones03.png)
```java
class Solution {
    public int numSubmat(int[][] mat) {
        int row = mat.length, col = mat[0].length;
        int res = 0;
        for (int s = 0; s < row; s++) {
            // 统计
            for (int i = s; i < row; i++) {
                int cur = 0;
                for (int j = 0; j < col; j++) {
                    if(mat[i][j] == 0) cur = 0;
                    else cur++;
                    res += cur;
                }
            }
			// 压缩
            for (int i = row-1; i > s; i--) {
                for (int j = 0; j < col; j++) {
                    mat[i][j] = mat[i][j] & mat[i-1][j];
                }
            }
        }
        return res;
    }
}
```

## 暴力
```java
class Solution {
    public int numSubmat(int[][] mat) {
        int row = mat.length, col = mat[0].length; 
        int[][] pre_sum = new int[row+1][col+1];
        int res = 0;
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                pre_sum[i][j] = mat[i-1][j-1] + pre_sum[i-1][j] + pre_sum[i][j-1] - pre_sum[i-1][j-1];
            }
        }

        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                for (int ii = i; ii <= row; ii++) {
                    for (int jj = j; jj <= col; jj++) {
                        if(pre_sum[ii][jj]-pre_sum[ii-i][jj]-pre_sum[ii][jj-j]+pre_sum[ii-i][jj-j] == i*j)
                        	res++;
                    }
                }
            }
        }
        return res;
    }
}
```