## WA
```java
WA: [[1,1,1],[0,1,0],[0,1,1],[1,1,1],[1,1,1],[1,1,1]]

class Solution {
    public int largest1BorderedSquare(int[][] grid) {
        Pair[][] arr = new Pair[grid.length+1][grid[0].length+1];

        for (int i = 1; i <= grid.length; i++) {
            for (int j = 1; j <= grid[0].length; j++) {
                // 如果在 arr 边界，或在 grid 前一个处为 0
                int r = 0, c = 0;
                if(arr[i-1][j] == null || grid[i-2][j-1] == 0 || grid[i-1][j-1] == 0)
                    r = grid[i-1][j-1];
                else
                    r = arr[i-1][j].row + grid[i-1][j-1];

                if(arr[i][j-1] == null || grid[i-1][j-2] == 0 || grid[i-1][j-1] == 0)
                    c = grid[i-1][j-1];
                else
                    c = arr[i][j-1].col + grid[i-1][j-1];

                arr[i][j] = new Pair(r, c);
            }
        }

        int res = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j < arr[0].length; j++) {
                if(arr[i][j].row == 0 || arr[i][j].col == 0) continue;

                int tr = 0, tc = 0;
                if(arr[i][j].row <= arr[i][j - arr[i][j].col + 1].row)
                    tr = arr[i][j].row;
                if(arr[i][j].col <= arr[i - arr[i][j].row + 1][j].col)
                    tc = arr[i][j].col;

                // int min = Math.min(arr[i][j].row, arr[i][j].col);
                int min = Math.min(tr, tc);
                res = Math.max(res, min * min);
            }
        }
        return res;
    }
}

class Pair {
    int row, col;
    public Pair(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
```