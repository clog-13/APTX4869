# LCP 33. 蓄水

给定 N 个无限容量且初始均空的水缸，每个水缸配有一个水桶用来打水，第 `i` 个水缸配备的水桶容量记作 `bucket[i]`。小扣有以下两种操作：

- 升级水桶：选择任意一个水桶，使其容量增加为 `bucket[i]+1`
- 蓄水：将全部水桶接满水，倒入各自对应的水缸

每个水缸对应最低蓄水量记作 `vat[i]`，返回小扣至少需要多少次操作可以完成所有水缸蓄水要求。

注意：实际蓄水量 **达到或超过** 最低蓄水量，即完成蓄水要求。

**示例：**

> 输入：`bucket = [1,3], vat = [6,8]`
>
> 输出：`4`
>
> 解释：
> 第 1 次操作升级 bucket[0]；
> 第 2 ~ 4 次操作均选择蓄水，即可完成蓄水要求。



## 迭代加深

```java
class Solution {
    public int storeWater(int[] bucket, int[] vat) {
        int N = vat.length, max = -1, min = Integer.MAX_VALUE;
        for (int i = 0; i < N;i++) {
            if (vat[i] > max) max = vat[i];
            if (vat[i] < min) min = vat[i];
        }
        if (max == 0) return 0;

        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= max;i++) {
            int tmp = i;  // 一起倒 i 次水 （不能设成当前所有桶的尺寸，不同桶可能大小不一样）

            for (int j = 0; j < N; j++) {
                int least = vat[j]/i + (vat[j]%i==0 ? 0 : 1);  // 一起倒 i 次水，桶至少多大
                tmp += Math.max(0, least - bucket[j]);  // 升级当前桶的次数
            }
            if (tmp < res) res = tmp;
        }
        return res;
    }
}
```

