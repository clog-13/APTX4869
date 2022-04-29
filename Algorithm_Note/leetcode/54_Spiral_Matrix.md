# 54. Spiral Matrix

Given an `m x n` `matrix`, return *all elements of the* `matrix` *in spiral order*.

 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/11/13/spiral.jpg)

```
Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
Output: [1,2,3,4,8,12,11,10,9,5,6,7]
```



## Mono

```java
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if(matrix.length == 0) return res;
        int up = 0;
        int dn = matrix.length - 1;
        int le = 0;
        int ri = matrix[0].length - 1;
        while(true) {
            for(int i = le; i <= ri; ++i) res.add(matrix[up][i]); //向右移动直到最右
            if(++up > dn) break; //重新设定上边界，若上边界大于下边界，则遍历遍历完成，下同
            for(int i = up; i <= dn; ++i) res.add(matrix[i][ri]); //向下
            if(--ri < le) break; //重新设定有边界
            for(int i = ri; i >= le; --i) res.add(matrix[dn][i]); //向左
            if(--dn < up) break; //重新设定下边界
            for(int i = dn; i >= up; --i) res.add(matrix[i][le]); //向上
            if(++le > ri) break; //重新设定左边界
        }
        return res;
    }
}
```

