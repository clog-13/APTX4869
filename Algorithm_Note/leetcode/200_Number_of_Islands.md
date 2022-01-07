# 200. Number of Islands

Given an `m x n` 2D binary grid `grid` which represents a map of `'1'`s (land) and `'0'`s (water), return *the number of islands*.

An **island** is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

 **Example 1:**

```
Input: grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
]
Output: 1
```

**Example 2:**

```
Input: grid = [
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]
]
Output: 3
```



## DFS

```go
func numIslands(grid [][]byte) int {
	res := 0
	var dfs func(i, j int)
	dfs = func(i, j int) {
		if i < len(grid) && i >= 0 && j < len(grid[0]) && j >= 0 && grid[i][j] == '1' {
			grid[i][j] = 0
			dfs(i, j+1)
			dfs(i, j-1)
			dfs(i-1, j)
			dfs(i+1, j)
		}
	}

	for i := 0; i < len(grid); i++ {
		for j := 0; j < len(grid[0]); j++ {
			if grid[i][j] == '1' {
				res++
				dfs(i, j)
			}
		}
	}
	return res
}
```



## BFS

```go
func numIslands(grid [][]byte) int {
	dx := []int{0, 1, 0, -1}
	dy := []int{1, 0, -1, 0}
	res, row, col := 0, len(grid), len(grid[0])
	if row == 0 {
		return 0
	}
	que := make([][]int, 0)
	for i := 0; i < row; i++ {
		for j := 0; j < col; j++ {
			if grid[i][j] == '1' {
				res++
				que = append(que, []int{i, j})
				grid[i][j] = '0'
				for len(que) > 0 {
					now := que[0]
					que = que[1:]
					for k := 0; k < 4; k++ {
						tmp := []int{now[0] + dx[k], now[1] + dy[k]}
						if tmp[0] >= 0 && tmp[0] < row && tmp[1] >= 0 && tmp[1] < col && grid[tmp[0]][tmp[1]] == '1' {
							que = append(que, tmp)
							grid[tmp[0]][tmp[1]] = '0'
						}
					}
				}
			}
		}
	}
	return res
}
```



## Union-Find

```go
func numIslands(grid [][]byte) int {
	res, row, col := 0, len(grid), len(grid[0])
	dx := []int{-1, 0, 0, 1}
	dy := []int{0, -1, 1, 0}

	fa := make([]int, row*col+1)
	for i := 0; i <= row*col; i++ {
		fa[i] = i
	}

	for i := 0; i < row; i++ {
		for j := 0; j < col; j++ {
			if grid[i][j] == '0' {
				continue
			}
			for k := 0; k < 4; k++ {
				nx, ny := i + dx[k], j + dy[k]
				if nx >= row || ny >= col || nx < 0 || ny < 0 {
					continue
				} else if grid[nx][ny] == '1' {
					union(fa, i*col+j, nx*col+ny)
				}
			}
		}
	}

	for i := 0; i < row; i++ {
		for j := 0; j < col; j++ {
			// 如果字符为1，且根是自己本身，说明找到一个根，res++
			if grid[i][j] == '1' && find(fa, i*col+j) == i*col+j {
				res++
			}
		}

	}

	return res
}

func find(fa []int, x int) int {
	if fa[x] != x {
    	fa[x] = find(fa, fa[x])
	}
	return fa[x]
}

func union(fa []int, x, y int) {
	x, y = find(fa, x), find(fa, y)
	if x != y {
		fa[x] = y
	}
}
```



```go
type UnionFindSet struct {
	Parents []int // 每个结点的顶级节点
	SetCnt int // 连通分量的个数
}

func (u *UnionFindSet) Init(grid [][]byte) {
	row, col := len(grid), len(grid[0])
	u.Parents = make([]int, row*col)
	for i := 0; i < row; i++ {
		for j := 0; j < col; j++ {
			u.Parents[i*col+j] = i*col+j
			if grid[i][j] == '1' {
				u.SetCnt++
			}
		}
	}
}

func (u *UnionFindSet) Find(node int) int {
	if u.Parents[node] == node {
		return node
	}
	root := u.Find(u.Parents[node])
	u.Parents[node] = root
	return root
}

func (u *UnionFindSet) Union(node1 int, node2 int) {
	root1, root2 := u.Find(node1), u.Find(node2)
	if root1 == root2 {
		return
	}
	if root1 < root2 {
		u.Parents[root1] = root2
	} else {
		u.Parents[root2] = root1
	}
	u.SetCnt--
}
// 心得：并查集是一种搜索算法（针对聚合的）
func numIslands(grid [][]byte) int {
	u := &UnionFindSet{}
	row, col := len(grid), len(grid[0])
	u.Init(grid)

	for i := 0; i < row; i++ {
		for j := 0; j < col; j++ {
			if grid[i][j] == '1' {
				if i-1 >= 0 && grid[i-1][j] == '1' {
					u.Union(i*col+j, (i-1)*col+j)
				}
				if i+1 < row && grid[i+1][j] == '1' {
					u.Union(i*col+j, (i+1)*col+j)
				}
				if j-1 >= 0 && grid[i][j-1] == '1' {
					u.Union(i*col+j, i*col+(j-1))
				}
				if j+1 < col && grid[i][j+1] == '1' {
					u.Union(i*col+j, i*col+(j+1))
				}
				grid[i][j] = '0'  // !!!
			}
		}
	}
	return u.SetCnt
}
```

