# 302. 包含全部黑色像素的最小矩形
图片在计算机处理中往往是使用二维矩阵来表示的。

假设，这里我们用的是一张黑白的图片，那么 0 代表白色像素，1 代表黑色像素。

其中黑色的像素他们相互连接，也就是说，图片中只会有一片连在一块儿的黑色像素（像素点是水平或竖直方向连接的）。

给出某一个黑色像素点 (x, y) 的位置，你是否可以找出**包含全部黑色像素**的最小矩形（与坐标轴对齐）的面积呢？

**示例:**
输入:
[
  "0010",
  "0110",
  "0100"
]
和 x = 0, y = 2
输出: 6

### 二分查找
```java
public class Solution {
    public int minArea(char[][] image, int x, int y) {
        int row = image.length, col = image[0].length;
        int left = searchColumns(image, 0, y, 0, row, true);
        int right = searchColumns(image, y + 1, col, 0, row, false);
        int top = searchRows(image, 0, x, left, right, true);
        int bottom = searchRows(image, x + 1, row, left, right, false);
        return (right - left) * (bottom - top);
    }
    private int searchColumns(char[][] image, int i, int j, int top, int bottom, boolean whiteToBlack) {
        while (i != j) {
            int k = top, mid = (i + j) >> 1;
            while (k < bottom && image[k][mid] == '0') ++k; // 找到该竖行有'1'为止（或全部找完）
            if (k < bottom == whiteToBlack)
                j = mid;    // 去下标相对小的那边找
            else
                i = mid + 1;// 去下标相对大的那边找
        }
        return i;
    }
    private int searchRows(char[][] image, int i, int j, int left, int right, boolean whiteToBlack) {
        while (i != j) {
            int k = left, mid = (i + j) >> 1;
            while (k < right && image[mid][k] == '0') ++k;
            if (k < right == whiteToBlack)
                j = mid;
            else
                i = mid + 1;
        }
        return i;
    }
}
```

### 搜索
```java
public class Solution {
    private int top, bottom, left, right;
    public int minArea(char[][] image, int x, int y) {
        if(image.length == 0 || image[0].length == 0) return 0;
        top = bottom = x;
        left = right = y;
        dfs(image, x, y);
        return (right - left) * (bottom - top);
    }
    private void dfs(char[][] image, int x, int y){
        if(x < 0 || y < 0 || x >= image.length || y >= image[0].length ||
          image[x][y] == '0')
            return;
        image[x][y] = '0'; // mark visited black pixel as white
        top = Math.min(top, x);
        bottom = Math.max(bottom, x + 1);
        left = Math.min(left, y);
        right = Math.max(right, y + 1);
        dfs(image, x + 1, y);
        dfs(image, x - 1, y);
        dfs(image, x, y - 1);
        dfs(image, x, y + 1);
    }
}
```

### 线性查找
```java
public class Solution {
    public int minArea(char[][] image, int x, int y) {
        int top = x, bottom = x;
        int left = y, right = y;
        for (x = 0; x < image.length; ++x) {
            for (y = 0; y < image[0].length; ++y) {
                if (image[x][y] == '1') {
                    top = Math.min(top, x);
                    bottom = Math.max(bottom, x + 1);
                    left = Math.min(left, y);
                    right = Math.max(right, y + 1);
                }
            }
        }
        return (right - left) * (bottom - top);
    }
}
```