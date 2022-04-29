# 2250. Count Number of Rectangles Containing Each Point
You are given a 2D integer array rectangles where rectangles[i] = [li, hi] indicates that ith rectangle has a length of li and a height of hi. You are also given a 2D integer array points where points[j] = [xj, yj] is a point with coordinates (xj, yj).

The ith rectangle has its bottom-left corner point at the coordinates (0, 0) and its top-right corner point at (li, hi).

Return an integer array count of length points.length where count[j] is the number of rectangles that contain the jth point.

The ith rectangle contains the jth point if 0 <= xj <= li and 0 <= yj <= hi. Note that points that lie on the edges of a rectangle are also considered to be contained by that rectangle.

 

**Example 1:**
```
Input: rectangles = [[1,2],[2,3],[2,5]], points = [[2,1],[1,4]]
Output: [2,1]
Explanation: 
The first rectangle contains no points.
The second rectangle contains only the point (2, 1).
The third rectangle contains the points (2, 1) and (1, 4).
The number of rectangles that contain the point (2, 1) is 2.
The number of rectangles that contain the point (1, 4) is 1.
Therefore, we return [2, 1].
```

## Sort
```go
func countRectangles(rectangles [][]int, points [][]int) []int {
    for i := range points {
		points[i] = append(points[i], i)
	}
	
    sort.Slice(points, func(i, j int) bool { return points[i][1] > points[j][1] })
    sort.Slice(rectangles, func(i, j int) bool { return rectangles[i][1] > rectangles[j][1] })
    
	ans := make([]int, len(points))
	i, N, arr := 0, len(rectangles), []int{}
	for _, p := range points {
		start := i
		for ; i < N && rectangles[i][1] >= p[1]; i++ {
			arr = append(arr, rectangles[i][0])
		}
		if start != i { // 只有在 arr 插入了新元素时才排序
			sort.Ints(arr)
		}
		ans[p[2]] = i - sort.SearchInts(arr, p[0])
	}
	return ans
}
```

## Count
```go
func countRectangles(rectangles [][]int, points [][]int) []int {
	for i := range points {
		points[i] = append(points[i], i)
	}
	sort.Slice(points, func(i, j int) bool { return points[i][0] > points[j][0] })
	sort.Slice(rectangles, func(i, j int) bool { return rectangles[i][0] > rectangles[j][0] })

	ans := make([]int, len(points))
	i, cnt := 0, [101]int{}
	for _, p := range points {
		for ; i < len(rectangles) && rectangles[i][0] >= p[0]; i++ {
			cnt[rectangles[i][1]]++
		}
		for _, c := range cnt[p[1]:] {
			ans[p[2]] += c
		}
	}
	return ans
}
```

## Count + Binary
```go
func countRectangles(rectangles [][]int, points [][]int) []int {
	for i := range points {
		points[i] = append(points[i], i)
	}
	sort.Slice(points, func(i, j int) bool { return points[i][0] > points[j][0] })
	sort.Slice(rectangles, func(i, j int) bool { return rectangles[i][0] > rectangles[j][0] })

	ans := make([]int, len(points))
	i, cnt := 0, [101]int{}
	for _, p := range points {
		for ; i < len(rectangles) && rectangles[i][0] >= p[0]; i++ {
			cnt[rectangles[i][1]]++
		}
		for _, c := range cnt[p[1]:] {
			ans[p[2]] += c
		}
	}
	return ans
}
```