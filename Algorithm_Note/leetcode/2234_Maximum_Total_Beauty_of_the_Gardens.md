# 2234. Maximum Total Beauty of the Gardens
Alice is a caretaker of n gardens and she wants to plant flowers to maximize the total beauty of all her gardens.

You are given a 0-indexed integer array flowers of size n, where flowers[i] is the number of flowers already planted in the ith garden. Flowers that are already planted cannot be removed. You are then given another integer newFlowers, which is the maximum number of flowers that Alice can additionally plant. You are also given the integers target, full, and partial.

A garden is considered complete if it has at least target flowers. The total beauty of the gardens is then determined as the sum of the following:

The number of complete gardens multiplied by full.
The minimum number of flowers in any of the incomplete gardens multiplied by partial. If there are no incomplete gardens, then this value will be 0.
Return the maximum total beauty that Alice can obtain after planting at most newFlowers flowers.

 

**Example 1:**
```
Input: flowers = [1,3,1,1], newFlowers = 7, target = 6, full = 12, partial = 1
Output: 14
Explanation: Alice can plant
- 2 flowers in the 0th garden
- 3 flowers in the 1st garden
- 1 flower in the 2nd garden
- 1 flower in the 3rd garden
The gardens will then be [3,6,2,2]. She planted a total of 2 + 3 + 1 + 1 = 7 flowers.
There is 1 garden that is complete.
The minimum number of flowers in the incomplete gardens is 2.
Thus, the total beauty is 1 * 12 + 2 * 1 = 12 + 2 = 14.
No other way of planting flowers can obtain a total beauty higher than 14.
```

## Greedy
```go
func maximumBeauty(flowers []int, newFlowers int64, target, full, partial int) int64 {
	sort.Ints(flowers)
	n := len(flowers)
	if flowers[0] >= target { // 剪枝，此时所有花园都是完善的
		return int64(n * full)
	}

    leftFlowers := 0  // 填充后缀后，剩余可以种植的花
	for i, f := range flowers {
		flowers[i] = min(f, target)
		leftFlowers += flowers[i]
	}
	leftFlowers += int(newFlowers) - target*n 

	ans := 0
	for i, x, sumFlowers := 0, 0, 0; i <= n; i++ { // 枚举后缀长度 n-i
		if leftFlowers >= 0 {  // 计算最长前缀的长度
			for ; x < i && flowers[x]*x-sumFlowers <= leftFlowers; x++ {
				sumFlowers += flowers[x] // 注意 x 只增不减，二重循环的时间复杂度为 O(n)
			}
			beauty := (n - i) * full // 计算总美丽值
			if x > 0 {
				beauty += min((leftFlowers+sumFlowers)/x, target-1) * partial
			}
			ans = max(ans, beauty)
		}
		if i < n {
			leftFlowers += target - flowers[i]
		}
	}
	return int64(ans)
}

func min(a, b int) int { if a > b { return b }; return a }
func max(a, b int) int { if a < b { return b }; return a }
```