# 542. 01 Matrix

Given an `m x n` binary matrix `mat`, return *the distance of the nearest* `0` *for each cell*.

The distance between two adjacent cells is `1`.

 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/04/24/01-1-grid.jpg)

```
Input: mat = [[0,0,0],[0,1,0],[0,0,0]]
Output: [[0,0,0],[0,1,0],[0,0,0]]
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2021/04/24/01-2-grid.jpg)

```
Input: mat = [[0,0,0],[0,1,0],[1,1,1]]
Output: [[0,0,0],[0,1,0],[1,2,1]]
```



## 2-Loops

```go
func updateMatrix(matrix [][]int) [][]int {
	row, col := len(matrix), len(matrix[0])
	res := make([][]int, row)
	for i := 0; i < row; i++ {
		res[i] = make([]int, col)
		for j := 0; j < col; j++ {
			if matrix[i][j] == 1 {
				res[i][j] = 10000
				if i != 0 {
					res[i][j] = min(res[i][j], res[i-1][j]+1)
				}
				if j != 0 {
					res[i][j] = min(res[i][j], res[i][j-1]+1)
				}
			} else {
				res[i][j] = 0
			}
		}
	}
	for i := row - 1; i >= 0; i-- {
		for j := col - 1; j >= 0; j-- {
			if res[i][j] > 0 {
				if i < row-1 {
					res[i][j] = min(res[i][j], res[i+1][j]+1)
				}
				if j < col-1 {
					res[i][j] = min(res[i][j], res[i][j+1]+1)
				}
			}
		}
	}
	return res
}
func min(a, b int) int {
	if a <= b {
		return a
	}
	return b
}
```



## BFS

```java
func updateMatrix(mat [][]int) [][]int {
	row, col := len(mat), len(mat[0])
	que := make([][2]int, 0, row*col)
	res := make([][]int, row)
	for i := 0; i < row; i++ {
		res[i] = make([]int, col)
		for j := 0; j < col; j++ {
			if mat[i][j] == 0 {
				que = append(que, [2]int{i, j})
			} else {
				res[i][j] = -1 // mat[i][j] == 1
			}
		}
	}
	dir := [4][2]int{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}
	for len(que) > 0 {
		item := que[0]
		que = que[1:]
		val := res[item[0]][item[1]]
		for i := 0; i < len(dir); i++ {
			x, y := item[0]+dir[i][0], item[1]+dir[i][1]
			if x >= 0 && x < row && y >= 0 && y < col {
				if res[x][y] == -1 {
					res[x][y] = val + 1
					que = append(que, [2]int{x, y}) // !!!
				}
			}
		}
		
	}
	return res
}
```

