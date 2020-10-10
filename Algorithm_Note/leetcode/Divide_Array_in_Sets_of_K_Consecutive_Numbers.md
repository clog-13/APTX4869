# 1296. 划分数组为连续数字的集合
给你一个整数数组 nums 和一个正整数 k，请你判断是否可以把这个数组划分成一些由 k 个连续数字组成的集合。
如果可以，请返回 True；否则，返回 False。

**示例：**
输入：nums = [1,2,3,3,4,4,5,6], k = 4
输出：true
解释：数组可以分成 [1,2,3,4] 和 [3,4,5,6]。

## 数学
```java
class Solution {
    public boolean isPossibleDivide(int[] nums, int k) {
        if (nums.length % k != 0) return false;

        int[] temp = new int[k];
        for (int n : nums)
            temp[n % k]++;
        for (int i = 1; i < k; i++)	// 从 1 开始, 0 的应该被忽略
            if (temp[i] != temp[i - 1]) return false;
        return true;
    }
}
```

## 贪心
```java
class Solution {
    public boolean isPossibleDivide(int[] nums, int k) {
        int max = 0, min = Integer.MAX_VALUE;
        for (int n : nums) {
            max = Math.max(max, n);
            min = Math.min(min, n);
        }
        int[] bucket = new int[max+1];
        for (int n : nums)
            bucket[n]++;

        for (int s = min; s <= max; ) {
            if (s + k-1 > max) return false;
            int t = bucket[s];
            int next = s+1;
            for (int i = s+1; i < s+k; i++) {
                if (bucket[i] < t) return false;
                bucket[i] -= t;
                if(bucket[i] == 0) next = i+1;
            }
            s = next;
        }
        return true;
    }
}
```

## 模拟
```java
class Solution {
    public boolean isPossibleDivide(int[] nums, int k) {
        if (nums.length % k != 0) return false;

		PriorityQueue<Integer> minHeap = new PriorityQueue<>(nums.length);
        for (int num : nums)
            minHeap.offer(num);

        while (!minHeap.isEmpty()) {
            Integer top = minHeap.poll();

            for (int i = 1; i < k; i++) {
                if (!minHeap.remove(top + i)) return false;
            }
        }
        return true;
    }
}
```