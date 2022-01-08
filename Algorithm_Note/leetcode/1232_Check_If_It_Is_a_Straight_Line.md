# 1232. Check If It Is a Straight Line

You are given an array `coordinates`, `coordinates[i] = [x, y]`, where `[x, y]` represents the coordinate of a point. Check if these points make a straight line in the XY plane.

 

 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2019/10/15/untitled-diagram-2.jpg)

```
Input: coordinates = [[1,2],[2,3],[3,4],[4,5],[5,6],[6,7]]
Output: true
```

**Example 2:**

**![img](https://assets.leetcode.com/uploads/2019/10/09/untitled-diagram-1.jpg)**

```
Input: coordinates = [[1,1],[2,2],[3,4],[4,5],[5,6],[7,7]]
Output: false
```



## Linear Algebra

```go
func checkStraightLine(arr [][]int) bool {
    x0, y0 := arr[0][0], arr[0][1]
    xa, ya := arr[1][0] - x0, arr[1][1] - y0
    for i := 2; i < len(arr); i++ {
        xb, yb := arr[i][0] - x0, arr[i][1] - y0
        if xa*yb - ya*xb != 0 {  // 计算二阶行列式
            return false
        }
    }
    return true
}
```



## Math

```go
func checkStraightLine(coordinates [][]int) bool {
    deltaX, deltaY := coordinates[0][0], coordinates[0][1]
    for _, p := range coordinates {
        p[0] -= deltaX
        p[1] -= deltaY
    }
    A, B := coordinates[1][1], coordinates[1][0]
    for _, p := range coordinates[2:] {
        x, y := p[0], p[1]
        if A*x-B*y != 0 {
            return false
        }
    }
    return true
}
```

