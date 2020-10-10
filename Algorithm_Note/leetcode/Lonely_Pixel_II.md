# 533. 孤独像素 II
给定一幅由黑色像素和白色像素组成的图像， 与一个正整数N, 找到位于某行 R 和某列 C 中且符合下列规则的黑色像素的数量:

- 行R 和列C都==恰好==包括N个黑色像素。
- 列C中所有黑色像素所在的行必须和行完全相同。


图像由一个由‘B’和‘W’组成二维字符数组表示, ‘B’和‘W’分别代表黑色像素和白色像素。

示例:

输入:[
['W', **'B'**, 'W', **'B'**, 'B', 'W'],
['W', **'B'**, 'W', **'B'**, 'B', 'W'],
['W', **'B'**, 'W', **'B'**, 'B', 'W'],
['W', 'W', 'B', 'W', 'B', 'W']]

N = 3
输出: 6
解析: 所有粗体的'B'都是我们所求的像素(第1列和第3列的所有'B').

以R = 0行和C = 1列的'B'为例:
规则 1，R = 0行和C = 1列都恰好有N = 3个黑色像素. 
规则 2，在C = 1列的黑色像素分别位于0，1和2行。它们都和R = 0行完全相同。

## 散列表
```java
class Solution {
    public int findBlackPixel(char[][] picture, int N) {
        int[][] rGrid = new int[picture.length][N + 1];     // 行位置
        int[][] cGrid = new int[picture[0].length][N + 1];  // 列位置
        for (int rid = 0; rid < picture.length; rid++) {
            for (int cid = 0; cid < picture[0].length; cid++) {
                if (picture[rid][cid] == 'B') { // 记录每行每列 B 的位置（记录该行该列为 B 的下标）
                    if (++rGrid[rid][0] <= N) rGrid[rid][rGrid[rid][0]] = cid;
                    if (++cGrid[cid][0] <= N) cGrid[cid][cGrid[cid][0]] = rid;
                }
            }
        }

        int res = 0;
        for (int rid = 0; rid < picture.length; rid++) {    // 遍历列
//==========找到符合条件的 行
            if (rGrid[rid][0] == N) {   // rGrid[rid][0]代表这一行有 N 个 B
                int colIdx = -1;
//==============找到 该行 符合条件的 列
                for (int i = 1; i <= N; i++) {
                    int each = rGrid[rid][i];
                    if (cGrid[each][0] == N) {
                        colIdx = i; // 找到满足条件的一个列 （下面再去判断相同列）
                        break;
                    }
                }
                if (colIdx == -1) continue;
                int col = rGrid[rid][colIdx];

//==============判断 该列 是否和其他 行 的 列 相同
//根据规则2，我们需要判断每行是否相互相等，所以要遍历 部分行（rGrid[each][i]），部分行怎么选择，选择那些目标列里为B的行（each），目标列下标（col）
                boolean same = true;
                for (int i = 1; i <= N && same; i++) {
                    for (int j = 1; j <= N && same; j++) {
                        int each = cGrid[col][i];
                        if (rGrid[each][j] != rGrid[rid][j]) {
                            same = false;
                        }
                    }
                }

                if (same) {
                    for (int i = 1; i <= N; i++) {
                        if (cGrid[rGrid[rid][i]][0] == N)
                            res += N;
                        rGrid[cGrid[col][i]][0] = 0;
                    }
                }
            }
        }

        return res;
    }
}
```