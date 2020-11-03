# LCP 18. 早餐组合

小扣在秋日市集选择了一家早餐摊位，一维整型数组 `staple` 中记录了每种主食的价格，一维整型数组 `drinks` 中记录了每种饮料的价格。小扣的计划选择一份主食和一款饮料，且花费不超过 `x` 元。请返回小扣共有多少种购买方案。

注意：答案需要以 `1e9 + 7 (1000000007)` 为底取模，如：计算初始结果为：`1000000008`，请返回 `1`

**示例：**

> 输入：`staple = [10,20,5], drinks = [5,5,2], x = 15`
>
> 输出：`6`
>
> 解释：小扣有 6 种购买方案，所选主食与所选饮料在数组中对应的下标分别是：
> 第 1 种方案：staple[0] + drinks[0] = 10 + 5 = 15；
> 第 2 种方案：staple[0] + drinks[1] = 10 + 5 = 15；
> 第 3 种方案：staple[0] + drinks[2] = 10 + 2 = 12；
> 第 4 种方案：staple[2] + drinks[0] = 5 + 5 = 10；
> 第 5 种方案：staple[2] + drinks[1] = 5 + 5 = 10；
> 第 6 种方案：staple[2] + drinks[2] = 5 + 2 = 7。



## 双指针

```java
class Solution {
    public int breakfastNumber(int[] staple, int[] drinks, int x) {
        Arrays.sort(staple); Arrays.sort(drinks);
        long res = 0;
        int ri = drinks.length-1;
        for (int le = 0; le < staple.length; le++) {
            if (ri < 0) break;
            while (ri >= 0 && staple[le]+drinks[ri] > x) ri--;
            res += (ri+1);
        }
        return (int) (res % (1000000007));
    }
}
```



## 前缀和

```java
class Solution {
    public int breakfastNumber(int[] staple, int[] drinks, int x) {
            long res = 0;
            int[] preSum = new int[x+1];
            for (int s : staple) 
                if (s < x) preSum[s] += 1;

            for (int i = 2; i <= x; i++)
                preSum[i] += preSum[i-1];

            for (int d : drinks) {
                int tar = x - d;
                if (tar < 0) continue;
                res += preSum[tar];
            }

            return (int) (res % (1000000007));
        }
}
```

