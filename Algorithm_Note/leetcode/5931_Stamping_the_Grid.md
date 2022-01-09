# 5931. Stamping the Grid

You are given an `m x n` binary matrix `grid` where each cell is either `0` (empty) or `1` (occupied).

You are then given stamps of size `stampHeight x stampWidth`. We want to fit the stamps such that they follow the given **restrictions** and **requirements**:

1. Cover all the **empty** cells.
2. Do not cover any of the **occupied** cells.
3. We can put as **many** stamps as we want.
4. Stamps can **overlap** with each other.
5. Stamps are not allowed to be **rotated**.
6. Stamps must stay completely **inside** the grid.

Return `true` *if it is possible to fit the stamps while following the given restrictions and requirements. Otherwise, return* `false`.

 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/11/03/ex1.png)

```
Input: grid = [[1,0,0,0],[1,0,0,0],[1,0,0,0],[1,0,0,0],[1,0,0,0]], stampHeight = 4, stampWidth = 3
Output: true
Explanation: We have two overlapping stamps (labeled 1 and 2 in the image) that are able to cover all the empty cells.
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2021/11/03/ex2.png)

```
Input: grid = [[1,0,0,0],[0,1,0,0],[0,0,1,0],[0,0,0,1]], stampHeight = 2, stampWidth = 2 
Output: false 
Explanation: There is no way to fit the stamps onto all the empty cells without the stamps going outside the grid.
```



## PreSum & Diff

```go
func possibleToStamp(grid [][]int, stampHeight, stampWidth int) bool {
	row, col := len(grid), len(grid[0])
	preSum := make([][]int, row+1)
	preSum[0] = make([]int, col+1)
	diff := make([][]int, row+1)
	diff[0] = make([]int, col+1)
	for i, _ := range grid {
		preSum[i+1] = make([]int, col+1)
		diff[i+1] = make([]int, col+1)
        for j, v := range grid[i] { // grid 的二维前缀和
			preSum[i+1][j+1] = preSum[i+1][j] + preSum[i][j+1] - preSum[i][j] + v
		}
		
	}

    // 由于邮票可以互相重叠，我们遵从能放就放邮票的策略，遍历所有的空位，尝试以该空位为左上角放置邮票。
	for i, r := range grid {
		for j, v := range r {
			if v == 0 {
				x, y := i+stampHeight, j+stampWidth  // 注意这是矩形右下角横纵坐标都 +1 后的位置
				if x <= row && y <= col && preSum[x][y]-preSum[x][j]-preSum[i][y]+preSum[i][j] == 0 {
					diff[i][j]++
					diff[i][y]--
					diff[x][j]--
					diff[x][y]++ // 更新二维差分
				}
			}
		}
	}

	// 还原二维差分矩阵对应的计数矩阵，这里用滚动数组实现
    // 如果存在一个空格子的计数值为 00，就表明该空格子没有被邮票覆盖
	cnt, pre := make([]int, col+1), make([]int, col+1)
	for i, r := range grid {
		for j, v := range r {
			cnt[j+1] = cnt[j] + pre[j+1] - pre[j] + diff[i][j]
			if cnt[j+1] <= 0 && v == 0 {
				return false
			}
		}
		cnt, pre = pre, cnt
	}
	return true
}
```

