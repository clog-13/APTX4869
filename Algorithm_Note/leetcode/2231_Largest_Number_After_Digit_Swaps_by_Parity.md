# 2231. Largest Number After Digit Swaps by Parity
You are given a positive integer num. You may swap any two digits of num that have the same parity (i.e. both odd digits or both even digits).

Return the largest possible value of num after any number of swaps.

 

**Example 1:**
```
Input: num = 65875
Output: 87655
Explanation: Swap the digit 8 with the digit 6, this results in the number 85675.
Swap the first digit 5 with the digit 7, this results in the number 87655.
Note that there may be other sequences of swaps but it can be shown that 87655 is the largest possible number.
```

# Mono
```go
func largestInteger(num int) int {
	s := []byte(strconv.Itoa(num))
	a := [2][]byte{}
	for _, v := range s {
		a[v&1] = append(a[v&1], v) // 分组
	}
	sort.Slice(a[0], func(i, j int) bool { return a[0][i] > a[0][j] })
	sort.Slice(a[1], func(i, j int) bool { return a[1][i] > a[1][j] }) // 排序

	for i, ch := range s {
		j := ch & 1
		s[i] = a[j][0] // 填回
		a[j] = a[j][1:]
	}
	ans, _ := strconv.Atoi(string(s))
	return ans
}
```