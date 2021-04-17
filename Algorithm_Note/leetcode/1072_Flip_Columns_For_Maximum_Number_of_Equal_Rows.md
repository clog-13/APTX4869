# 1072. 按列翻转得到最大值等行数
给定由若干 0 和 1 组成的矩阵 `matrix`，从中选出任意数量的列并翻转其上的 **每个** 单元格。翻转后，单元格的值从 0 变成 1，或者从 1 变为 0 。

返回经过一些翻转后，行上所有值都相等的最大行数。

**示例：**

```
输入：
[[0,0,0]
,[0,0,1]
,[1,1,0]]
输出：2
解释：翻转前两列的值之后，后两行由相等的值组成。
```



## 分治

物以類聚

high-low+1就是相同類型的行的數量，相同類型的行最後可以同時把當前行變成相等數字

這裏的相同類型指 相較第一個數（也可以是其他錨點）的 同或異 的狀態

```java
class Solution {
    public int maxEqualRowsAfterFlips(int[][] matrix) {
        int N = matrix.length;
        int[] rows = new int[N];
        for (int i = 0; i < N; i++) rows[i] = i;
        return radixSort(matrix, rows, 0, N - 1, 1);
    }

    public int radixSort(int[][] arr, int[] rows, int low, int high, int col) {
        if (low > high) return 0;
        if (col == arr[0].length) return high-low+1;

        int tl = low, th = high;
        while (tl <= th) {
            while (tl<=th && arr[rows[tl]][col] == arr[rows[tl]][0]) tl++;
            while (tl<=th && arr[rows[th]][col] != arr[rows[th]][0]) th--;
            if (tl > th) break;

            int temp = rows[tl]; rows[tl] = rows[th]; rows[th] = temp;  // 当前col列前状态相同的交换到一起
        }

        return Math.max(
                radixSort(arr, rows, low, th, col+1),
                radixSort(arr, rows, tl, high, col+1)
        );
    }
}
```



## Hash表

```java
class Solution {
    public int maxEqualRowsAfterFlips(int[][] matrix) {
        Map<String, Integer> map = new HashMap<>();
        boolean firstZero = false;
        int res = 0;
        for (int i = 0, len = matrix.length; i < len; i++) {
            if (matrix[i][0] == 0) firstZero = true;
            else firstZero = false;

            StringBuilder tmp = new StringBuilder();
            for (int j = 0; j < matrix[i].length; j++) {
                if (firstZero) tmp.append(matrix[i][j]);
                else tmp.append((matrix[i][j] ^ 1));
            }
            String str = tmp.toString();
            map.put(str, map.getOrDefault(str, 0) + 1);
            res = Math.max(res, map.get(str));
        }
        return res;
    }
}
```

