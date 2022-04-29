# 2251. Number of Flowers in Full Bloom
You are given a 0-indexed 2D integer array flowers, where flowers[i] = [starti, endi] means the ith flower will be in full bloom from starti to endi (inclusive). You are also given a 0-indexed integer array persons of size n, where persons[i] is the time that the ith person will arrive to see the flowers.

Return an integer array answer of size n, where answer[i] is the number of flowers that are in full bloom when the ith person arrives.

**Example 1:**
```
Input: flowers = [[1,6],[3,7],[9,12],[4,13]], persons = [2,3,7,11]
Output: [1,2,2,2]
Explanation: The figure above shows the times when the flowers are in full bloom and when the people arrive.
For each person, we return the number of flowers in full bloom during their arrival.
```

## Diff
```go
func fullBloomFlowers(flowers [][]int, persons []int) []int {
	diff := map[int]int{}
	for _, f := range flowers {
		diff[f[0]]++
		diff[f[1]+1]--
	}

	n := len(diff)
	times := make([]int, 0, n)
	for t := range diff {
		times = append(times, t)
	}
	sort.Ints(times)

	for i, p := range persons {
		persons[i] = p<<32 | i // !!!
	}
	sort.Ints(persons)

	ans := make([]int, len(persons))
	i, sum := 0, 0
	for _, p := range persons {
		for ; i < n && times[i] <= p>>32; i++ {
			sum += diff[times[i]] // 累加变化量
		}
		ans[uint32(p)] = sum // !!!
	}
	return ans
}
```

## Binary
```go
func fullBloomFlowers(flowers [][]int, persons []int) []int {
	n := len(flowers)
	starts := make([]int, n)
	ends := make([]int, n)
	for i, f := range flowers {
		starts[i] = f[0]
		ends[i] = f[1]
	}
	sort.Ints(starts)
	sort.Ints(ends)

	ans := make([]int, len(persons))
	for i, p := range persons {
		ans[i] = sort.SearchInts(starts, p+1) - sort.SearchInts(ends, p)
	}
	return ans
}
```