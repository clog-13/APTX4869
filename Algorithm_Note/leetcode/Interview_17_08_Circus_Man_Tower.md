# 面试题 17.08. 马戏团人塔

有个马戏团正在设计叠罗汉的表演节目，一个人要站在另一人的肩膀上。出于实际和美观的考虑，在上面的人要比下面的人矮一点且轻一点。已知马戏团每个人的身高和体重，请编写代码计算叠罗汉最多能叠几个人。

**示例：**

```
输入：height = [65,70,56,75,60,68] weight = [100,150,90,190,95,110]
输出：6
解释：从上往下数，叠罗汉最多能叠 6 层：(56,90), (60,95), (65,100), (68,110), (70,150), (75,190)
```



## DP（二分优化）（LIS模型）

```java
class Solution {
    public int bestSeqAtIndex(int[] height, int[] weight) {
        int N = height.length;
        int[][] person = new int[N][2];
        for (int i = 0; i < N; i++)
            person[i] = new int[]{height[i], weight[i]};
        
        Arrays.sort(person, (a, b) -> a[0]==b[0] ? b[1]-a[1] : a[0]-b[0]);
        
        int[] dp = new int[N];
        int res = 0;
        for (int[] pair : person) {  // 身高升序排列，找体重的最大升序子串
            int i = Arrays.binarySearch(dp, 0, res, pair[1]);
            if (i < 0) i = -(i + 1);
            
            dp[i] = pair[1];
            
            if (i == res) res++;
        }
        return res;
    }
}
```