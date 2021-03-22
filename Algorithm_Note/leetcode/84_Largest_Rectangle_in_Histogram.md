# 84. 柱状图中最大的矩形

给定 *n* 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。

求在该柱状图中，能够勾勒出来的矩形的最大面积。



## 单调栈

```java
class Solution {
    public int largestRectangleArea(int[] heights) {
        int N = heights.length;
        if (N == 0) return 0;
        int[] left = new int[N], right = new int[N];
        
        left[0] = - 1;
        for (int i = 1; i < N; i++) {
            int temp = i - 1;
            while (temp >= 0 && heights[temp] >= heights[i]) {
                temp = left[temp];
            }
            left[i] = temp;  // 左边第一根小于 i柱子高度 的柱子 的下标
        }

        right[N-1] = N;
        for (int i = N-2; i >= 0; i--) {
            int temp = i + 1;
            while (temp < N && heights[temp] >= heights[i]) {
                temp = right[temp];
            }
            right[i] = temp;  // 右边第一根小于 i柱子高度 的柱子 的下标
        }

        int maxArea = 0;
        for (int i = 0; i < N; i++) {
            int width = right[i] - left[i] - 1;
            maxArea = Math.max(maxArea, heights[i] * width);
        }
        return maxArea;
    }
}
```

```java
class Solution {
    public int largestRectangleArea(int[] heights) {
        int N = heights.length;
        int[] left = new int[N], right = new int[N];
        Arrays.fill(right, N);
        
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < N; ++i) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                right[stack.pop()] = i;
            }
            left[i] = (stack.isEmpty() ? -1 : stack.peek());
            stack.push(i);
        }
        
        int ans = 0;
        for (int i = 0; i < N; ++i) {
            ans = Math.max(ans, (right[i]-left[i]-1) * heights[i]);
        }
        return ans;
    }
}
```

