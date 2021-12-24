# 42. 接雨水
给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。

**示例: **

```
输入: [0,1,0,2,1,0,1,3,2,1,2,1]
输出: 6
```



## 前后缀

```java
class Solution {
    public int trap(int[] height) {
        if (height.length <= 2) return 0;
        int maxR = 0, N = height.length;
        int[] maxRs = new int[N];
        for (int i = N-1; i >= 0; i--) {
            if (height[i] > maxR) maxR = height[i];
            maxRs[i] = maxR;
        }

        int maxL = height[0], res = 0;
        for (int i = 1; i < N; i++) {  // traverse row
            if (height[i] < maxL) {
                int s = Math.min(maxL, maxRs[i]) - height[i];
                if (s > 0) res += s;
            } else {  // height[i] > maxL
                maxL = height[i];
            }
        }

        return res;
    }
}
```



## 双指针

```java
class Solution {
    public int trap(int[] height) {
        int le = 0, ri = height.length-1;
        int maxL = 0, maxR = 0, res = 0;
        while (le < ri) {  // 其中一边到达最高点后就不会移动了
            if (height[le] < height[ri]) {	
                if (height[le] > maxL) {
                    maxL = height[le];
                } else {
                    res += (maxL - height[le]); 
                }
                le++;
            } else {  // h[le] >= h[ri]
                if (height[ri] > maxR) {
                    maxR = height[ri];
                } else {
                    res += (maxR - height[ri]);
                }
                ri--;
            } 
        }
        return res;
    }
}
```



## 单调栈	

```java
class Solution {
    public int trap(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int cur = 0, res = 0;
        while (cur < height.length) {
            // 如果当前元素高度 大于 栈顶元素 （非严格单调 递减）
            while (!stack.empty() && height[cur] > height[stack.peek()]) {
                int h = height[stack.pop()];
                if (stack.empty()) break;

                int dis = cur - stack.peek() - 1;  // 两堵墙之前的距离（长度）
                int min = Math.min(height[stack.peek()], height[cur]) - h;
                res += dis * min;
            }
            stack.push(cur++);  // 当前墙入栈，上一步保证栈 非严格单调 递减
        }
        return res;
    }
}
```

