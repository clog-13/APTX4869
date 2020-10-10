# 1300. 转变数组后最接近目标值的数组和
给你一个整数数组 arr 和一个目标值 target ，请你返回一个整数 value ，使得将数组中所有大于 value 的值变成 value 后，数组的和最接近  target （最接近表示两者之差的绝对值最小）。

如果有多种使得和最接近 target 的方案，请你返回这些整数中的最小值。

请注意，答案不一定是 arr 中的数字。

**示例：**
输入：arr = [4,9,3], target = 10
输出：3
解释：当选择 value 为 3 时，数组会变成 [3, 3, 3]，和为 9 ，这是最接近 target 的方案。

## 数学
```java
class Solution {
    public int findBestValue(int[] arr, int target) {
        int ave = target / arr.length;
        int max = 0;
        for(int a : arr)
            max = Math.round((float)target/arr.length);
        if(max < ave) return ave;

        int pre = Integer.MAX_VALUE;
        for(int i = ave; ; i++) {
            int sum = 0;
            for(int a : arr)
                sum += Math.min(i, a);

            if(Math.abs(sum-target) >= pre)
                return i-1;
            pre = Math.abs(sum-target);
        }
    }
}
```
## 排序+数学
```java
class Solution {
    public int findBestValue(int[] arr, int target) {
        Arrays.sort(arr);
        int N = arr.length;
        int sum = 0; 
        for(int i = 0; i < N; i++) {
            int curAve = (target - sum) / (N - i);
            if (curAve <= arr[i]) {
                double tmp = (double)(target - sum) / (N - i);
                if (tmp - curAve <= 0.5) {
                    return curAve;
                } else {
                    return curAve + 1;
                }
            }
            sum += arr[i];
        }
        return arr[N-1];
    }
}
```