# 2245. Maximum Trailing Zeros in a Cornered Path
You are given a 2D integer array grid of size m x n, where each cell contains a positive integer.

A cornered path is defined as a set of adjacent cells with at most one turn. More specifically, the path should exclusively move either horizontally or vertically up to the turn (if there is one), without returning to a previously visited cell. After the turn, the path will then move exclusively in the alternate direction: move vertically if it moved horizontally, and vice versa, also without returning to a previously visited cell.

The product of a path is defined as the product of all the values in the path.

Return the maximum number of trailing zeros in the product of a cornered path found in grid.

Note:

Horizontal movement means moving in either the left or right direction.
Vertical movement means moving in either the up or down direction.
 

**Example 1:**
```

Input: grid = [[23,17,15,3,20],[8,1,20,27,11],[9,4,6,2,21],[40,9,1,10,6],[22,7,4,5,3]]
Output: 3
Explanation: The grid on the left shows a valid cornered path.
It has a product of 15 * 20 * 6 * 1 * 10 = 18000 which has 3 trailing zeros.
It can be shown that this is the maximum trailing zeros in the product of a cornered path.

The grid in the middle is not a cornered path as it has more than one turn.
The grid on the right is not a cornered path as it requires a return to a previously visited cell.
```

## PreSum + Math
```go
var c25 [1001][2]int

func init() {
	for i := 2; i <= 1000; i++ {  // 预处理：递推算出每个数的因子 2 的个数和因子 5 的个数
		if i%2 == 0 { c25[i][0] = c25[i/2][0] + 1 }
		if i%5 == 0 { c25[i][1] = c25[i/5][1] + 1 }
	}
}

func maxTrailingZeros(grid [][]int) (ans int) {
	m, n := len(grid), len(grid[0])
	s := make([][][2]int, m)
	for i, row := range grid {
		s[i] = make([][2]int, n+1)
		for j, v := range row {
			s[i][j+1][0] = s[i][j][0] + c25[v][0] // 每行的因子 2 的前缀和
			s[i][j+1][1] = s[i][j][1] + c25[v][1] // 每行的因子 5 的前缀和
		}
	}

	for j := 0; j < n; j++ {
		s2, s5 := 0, 0
		for i, row := range grid { // 从上往下，枚举左拐还是右拐
			s2 += c25[row[j]][0]
			s5 += c25[row[j]][1]
			ans = max(ans, max(min(s2+s[i][j][0], s5+s[i][j][1]), 
                               min(s2+s[i][n][0]-s[i][j+1][0], s5+s[i][n][1]-s[i][j+1][1])))
		}
		s2, s5 = 0, 0
		for i := m - 1; i >= 0; i-- { // 从下往上，枚举左拐还是右拐
			s2 += c25[grid[i][j]][0]
			s5 += c25[grid[i][j]][1]
			ans = max(ans, max(min(s2+s[i][j][0], s5+s[i][j][1]),
                               min(s2+s[i][n][0]-s[i][j+1][0], s5+s[i][n][1]-s[i][j+1][1])))
		}
	}
	return
}

func max(a, b int) int { if a < b { return b }; return a }
func min(a, b int) int { if a > b { return b }; return a }
```