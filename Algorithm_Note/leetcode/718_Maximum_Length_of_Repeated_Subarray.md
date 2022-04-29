# 718. Maximum Length of Repeated Subarray

Given two integer arrays `nums1` and `nums2`, return *the maximum length of a subarray that appears in **both** arrays*.

 

**Example 1:**

```
Input: nums1 = [1,2,3,2,1], nums2 = [3,2,1,4,7]
Output: 3
Explanation: The repeated subarray with maximum length is [3,2,1].
```

**Example 2:**

```
Input: nums1 = [0,0,0,0,0], nums2 = [0,0,0,0,0]
Output: 5
```



## DP

```go
func findLength(A []int, B []int) int {
    N, M := len(A), len(B)
    dp := make([][]int, N+1)
    for i := 0; i < N+1; i++ {
        dp[i] = make([]int, M+1)
    }
    res := 0
	for i := 1; i <= N; i++ {
		for j := 1; j <= M; j++ {
			if A[i-1] == B[j-1] {
				dp[i][j] = dp[i-1][j-1] + 1
			}
			if dp[i][j] > res {
				res = dp[i][j]
			}
		}
	}
    return res
}
```





## Slide Window

```go
func findLength(A []int, B []int) int {
    N, M, res := len(A), len(B), 0
    for i := 0; i < N; i++ {
        ln := min(M, N - i)
        t := maxLength(A, B, i, 0, ln)
        res = max(res, t)
    }
    for i := 0; i < M; i++ {
        ln := min(N, M - i)
        t := maxLength(A, B, 0, i, ln)
        res = max(res, t)        
    }
    return res
}

func maxLength(A, B []int, ostA, ostB, ln int) int {
    res, k := 0, 0
    for i := 0; i < ln; i++ {
        if A[ostA + i] == B[ostB + i] {
            k++
        } else {
            k = 0
        }
        res = max(res, k)
    }
    return res
}

func max(x, y int) int {
    if x > y {
        return x
    }
    return y
}

func min(x, y int) int {
    if x < y {
        return x
    }
    return y
}
```



## Hash

```go
const (
	base, mod = 113, 1000000009
)

func findLength(A []int, B []int) int {
	check := func(N int) bool { // 使用哈希来判断 A 和 B 中存在相同特定长度的子数组
		hashA := 0
		for i := 0; i < N; i++ {
			hashA = (hashA*base + A[i]) % mod
		}
		bucketA := map[int]bool{hashA: true}
		mult := qPow(base, N-1)
		for i := N; i < len(A); i++ {
			hashA = ((hashA-A[i-N]*mult%mod + mod)%mod * base + A[i]) % mod
			bucketA[hashA] = true
		}

		hashB := 0
		for i := 0; i < N; i++ {
			hashB = (hashB*base + B[i]) % mod
		}
		if bucketA[hashB] {
			return true
		}
		for i := N; i < len(B); i++ {
			hashB = ((hashB-B[i-N]*mult%mod + mod)%mod * base + B[i]) % mod
			if bucketA[hashB] {
				return true
			}
		}
		return false
	}

	le, ri := 1, min(len(A), len(B))+1
	for le < ri {
		mid := (le + ri) >> 1
		if check(mid) {
			le = mid + 1
		} else {
			ri = mid
		}
	}
	return le - 1
}

func qPow(base, n int) int {
	res := 1
	for n != 0 {
		if n&1 != 0 {
			res = res * base % mod
		}
		base = base * base % mod
		n >>= 1
	}
	return res
}

func min(x, y int) int {
	if x < y {
		return x
	}
	return y
}
```

