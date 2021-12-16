# 221. Maximal Square

Given an `m x n` binary `matrix` filled with `0`'s and `1`'s, *find the largest square containing only* `1`'s *and return its area*.

 

**Example 1:**

```
Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
Output: 4
```

**Example 2:**

```
Input: matrix = [["0","1"],["1","0"]]
Output: 1
```



## DP

```go
func maximalSquare(matrix [][]byte) int {
    dp := make([][]int, len(matrix))
    res := 0
    for i := 0; i < len(matrix); i++ {
        dp[i] = make([]int, len(matrix[i]))
        for j := 0; j < len(matrix[i]); j++ {
            dp[i][j] = int(matrix[i][j] - '0')
            if dp[i][j] == 1 {
                res = 1
            }
        }
    }

    for i := 1; i < len(matrix); i++ {
        for j := 1; j < len(matrix[i]); j++ {
            if dp[i][j] == 1 {
                dp[i][j] = min(min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1]) + 1
                if dp[i][j] > res {
                    res = dp[i][j]
                }
            }
        }
    }
    return res * res
}

func min(x, y int) int {
    if x < y {
        return x
    }
    return y
}
```



## Bit Operation

```python
class Solution:
    def getWidth(self, num):
        w = 0
        while num > 0:
            num &= num<<1
            w += 1
        return w
    def maximalSquare(self, matrix: List[List[str]]) -> int:
        nums = [int(''.join(n),base=2) for n in matrix]  # 每一行当作二进制数
        res, n = 0, len(nums)
        for i in range(n):
            tmp = nums[i]
            for j in range(i, n):
                tmp &= nums[j]
                wi = self.getWidth(tmp)
                hi = j-i+1
                res = max(res, min(wi, hi))
        return res*res