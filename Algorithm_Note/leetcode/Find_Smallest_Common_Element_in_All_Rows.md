# 1198. 找出所有行中最小公共元素
给你一个矩阵 mat，其中每一行的元素都已经按 递增 顺序排好了。请你帮忙找出在所有这些行中 最小的公共元素。

如果矩阵中没有这样的公共元素，就请返回 -1。

**示例：**
输入：mat = [[1,2,3,4,5],[2,4,5,8,10],[3,5,7,9,11],[1,3,5,7,9]]
输出：5

### 桶计数
```java
class Solution {
    public int smallestCommonElement(int[][] mat) {
        int[] bucket = new int[10010];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (++bucket[mat[i][j]] == mat.length) return mat[i][j];
            }
        }

        return -1;
    }
}
```

### 二分搜索
```java
class Solution {
    public int smallestCommonElement(int[][] mat) {
        int n = mat.length, m = mat[0].length;
        int pos[] = new int[n];
        for (int j = 0; j < m; ++j) {
            boolean found = true;
            for (int i = 1; i < n && found; ++i) {
                pos[i] = Arrays.binarySearch(mat[i], pos[i], m, mat[0][j]);
                if (pos[i] < 0) {
                    found = false;
                    pos[i] = -pos[i] - 1;
                    if (pos[i] >= m) return -1;
                }
            }
            if (found) return mat[0][j];
        }
        return -1;
    }
}
```

### 行位置（多指针）
```java
class Solution {
    public int smallestCommonElement(int[][] mat) {
        int n = mat.length, m = mat[0].length;
        int cout = 0, target = 0;
        int[] pos = new int[n];
        while (true) {
            for (int i = 0; i < n; i++) {
                pos[i] = Arrays.binarySearch(mat[i], pos[i], m, target);
                if (pos[i] < 0) {
                    cout = 1;
                    pos[i] = -pos[i] - 1;
                    if (pos[i] >= m) return -1;
                } else if (++cout == n) {
                    return target;
                }
                target = mat[i][pos[i]];
            }
        }
    }
}
```