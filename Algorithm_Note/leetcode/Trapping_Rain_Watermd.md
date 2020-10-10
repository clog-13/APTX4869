## 题目描述
给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20191218175129255.png)

上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。

**示例:**
输入: [0,1,0,2,1,0,1,3,2,1,2,1]
输出: 6

## 动态编程
```java
class Solution {
    public int trap(int[] height) {
        if(height.length <= 2) return 0;
        
        int maxL = height[0], maxR = 0;
        int[] maxRs = new int[height.length];  
        
        for(int i = height.length - 1; i >= 0; i--) {
            if(height[i] > maxR)
                maxR = height[i];
            maxRs[i] = maxR;
        } 

        int res = 0;
        for(int i = 1; i < height.length; i++) {
            if(height[i] < maxL)
                res += Math.max((Math.min(maxL, maxRs[i]) - height[i]), 0);
            else 
                maxL = height[i];       
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
        int maxL = 0, maxR = 0;    
    
        int res = 0;
        while(le < ri) {
            if(height[le] < height[ri]) {	// 其中一边到达最高点后就不会移动了
                if(height[le] > maxL)
                    maxL = height[le];
                else
                    res += (maxL - height[le]); 
                le++;
            } else {
                if(height[ri] > maxR)
                    maxR = height[ri];
                else 
                    res += (maxR - height[ri]);
                ri--;
            } 
        }
        return res;
    }
}
```

## 单调栈
总体的原则就是
 1. 当前高度小于等于栈顶高度，入栈，指针后移。
 2. 当前高度大于栈顶高度，出栈，计算出当前墙和栈顶的墙之间水的多少，然后计算当前的高度和新栈的高度的关系，重复第 2 步。直到当前墙的高度不大于栈顶高度或者栈空，然后把当前墙入栈，指针后移。

```java
class Solution {
    public int trap(int[] height) {
        int res = 0;
        Stack<Integer> stack = new Stack<>();
        int cur = 0;
        while (cur < height.length) {
            while (!stack.empty() && height[cur] > height[stack.peek()]) {
                int h = height[stack.pop()]; // 取出要出栈的元素
                if (stack.empty()) break;
                
                int dis = cur - stack.peek() - 1; // 两堵墙之前的距离。
                int min = Math.min(height[stack.peek()], height[cur]) - h;
                res += dis * min;
            }
            stack.push(current++); // 当前指向的墙入栈, 并且指针后移
        }
        return res;
    }
}
```

## 按行求和
```java
class Solution {
	public int trap(int[] height) {
        int res = 0, max = getMax(height);	// 找到最大的高度，以便遍历。
        for (int i = 1; i <= max; i++) {
            boolean isStart = false;	// 标记是否开始更新 temp
            int tmp = 0;
            for (int j = 0; j < height.length; j++) {
                if (isStart && height[j] < i)
                    tmp++;

                if (height[j] >= i) {
                    res += tmp;
                    tmp = 0;
                    isStart = true;
                }
            }
        }
        return res;
    }
    
    private int getMax(int[] height) {
            int max = 0;
            for (int i = 0; i < height.length; i++) {
                if (height[i] > max)
                    max = height[i];
            }
            return max;
    }    
}
```