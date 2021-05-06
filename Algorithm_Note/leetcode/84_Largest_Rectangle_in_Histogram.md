# 84. 柱状图中最大的矩形

给定 *n* 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。

求在该柱状图中，能够勾勒出来的矩形的最大面积。



## 单调栈

```java
class Solution {
    public int largestRectangleArea(int[] arr) {
        int N = arr.length; if (N == 0) return 0;
        int[] left = new int[N], right = new int[N];

        left[0] = - 1;
        for (int i = 1; i < N; i++) {
            int le = i - 1;
            while (le >= 0 && arr[le] >= arr[i]) {
                le = left[le];
            }
            left[i] = le; // 左边第一根小于 i柱子高度 的柱子 的下标
        }

        right[N-1] = N;
        for (int i = N-2; i >= 0; i--) {
            int ri = i + 1;
            while (ri < N && arr[ri] >= arr[i]) {
                ri = right[ri];
            }
            right[i] = ri;    // 右边第一根小于 i 位置的柱子
        }

        int maxArea = 0;
        for (int i = 0; i < N; i++) {
            maxArea = Math.max(maxArea, arr[i] * (right[i]-left[i]-1));
        }
        return maxArea;
    }
}
```



```java
import java.io.*;
import java.util.*;

public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        while (true) {
            String[] str = br.readLine().split(" ");
            if (Integer.parseInt(str[0])==0) break;
            long[] arr = new long[str.length-1];
            for (int i = 1; i < str.length; i++) {
                arr[i-1] = Long.parseLong(str[i]);
            }
            bw.write(largestRectangleArea(arr)+"\n");
        }
        bw.flush(); bw.close();
    }

    public long largestRectangleArea(long[] arr) {
        int N = arr.length;
        long[] left = new long[N], right = new long[N];  // 下标可以取的最左或最右 的下标
        Arrays.fill(right, N);

        Stack<Integer> stack = new Stack<>();  // stack储存的是下标
        for (int i = 0; i < N; ++i) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {  // 当栈顶大等于cur
                right[stack.peek()] = i;  // peek()的最右取到 cur
                stack.pop();
            }
            left[i] = (stack.isEmpty() ? -1 : stack.peek());  // i的最左取到peek()，le和ri是相通的
            stack.push(i);
        }

        long res = 0;
        for (int i = 0; i < N; ++i) {
            res = Math.max(res, (right[i] - left[i] - 1) * arr[i]);
        }
        return res;
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

