#1052. 爱生气的书店老板
今天，书店老板有一家店打算试营业 customers.length 分钟。每分钟都有一些顾客（customers[i]）会进入书店，所有这些顾客都会在那一分钟结束后离开。

在某些时候，书店老板会生气。 如果书店老板在第 i 分钟生气，那么 grumpy[i] = 1，否则 grumpy[i] = 0。 当书店老板生气时，那一分钟的顾客就会不满意，不生气则他们是满意的。

书店老板知道一个秘密技巧，能抑制自己的情绪，可以让自己连续 X 分钟不生气，但却只能使用一次。

请你返回这一天营业下来，最多有多少客户能够感到满意的数量。

**示例：**
输入：customers = [1,0,1,2,1,1,7,5], grumpy = [0,1,0,1,0,1,0,1], X = 3
输出：16
解释：
书店老板在最后 3 分钟保持冷静。
感到满意的最大客户数量 = 1 + 1 + 1 + 1 + 7 + 5 = 16.

##滑动窗口
```java
class Solution {
    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        int N = grumpy.length;
        int[] record = new int[N-X];
        int max = 0, res = 0;

        if(X == N) {
            for(int i : customers) res += i;
            return res;
        }

        for(int i = 0; i <= N-X; i++) {     // 计算 技能最大收益
            int tmp = 0;
            for(int j = i; j < i+X; j++) {
                if(grumpy[j] == 1)
                    tmp += customers[j];
            }
            max = Math.max(max, tmp);
        }

        for(int i = 0; i < N; i++)          // 计算 正常情况的res
            if(grumpy[i] == 0) res += customers[i];
        return res+max;
    }
}
```
把if(grumpy[i] == 1) {...} 的状态融合到一个数组(grumpy)
```java
优化代码
class Solution {
    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        int res = 0;
        for(int i = 0; i < grumpy.length; i++) {
            if(grumpy[i] == 1)
                grumpy[i] = customers[i];   // *** 让后面的逻辑代码更简洁，也解决了一些边界例子的问题
            else
                res += customers[i];
        }

        int tmp = 0;
        for(int i = 0; i < X; i++)
            tmp += grumpy[i];
        int max = tmp;
        for(int i = X; i < grumpy.length; i++) {
            tmp -= grumpy[i-X];
            tmp += grumpy[i];
            max = Math.max(max, tmp);
        }

        return res + max;
    }
}
```
**复杂度分析**
- 时间复杂度：O(N)
- 空间复杂度：O(N)