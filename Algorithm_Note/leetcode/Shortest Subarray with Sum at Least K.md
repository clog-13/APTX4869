# 862. 和至少为 K 的最短子数组
返回 A 的最短的非空连续子数组的长度，该子数组的和至少为 K 。

如果没有和至少为 K 的非空子数组，返回 -1 。

**示例：**
输入：A = [2,-1,2], K = 3
输出：3

## 前缀和（区间DP）
```java
TLE
class Solution {
    public int shortestSubarray(int[] A, int K) {
        int N = A.length;
        int[] pre_sum = new int[N+1];
        for(int i = 1; i <= N; i++) {
            if(A[i-1] >= K) return 1;
            pre_sum[i] = pre_sum[i-1] + A[i-1];
        }

        int res = 50010;
        for(int len = 1; len <= N; len++)
            for(int i = 1; i+len-1 <= N; i++)
                for(int j = i+len-1; j <= N; j++) {
                    int tmp = pre_sum[j] - pre_sum[i-1];
                    if(tmp >= K) res = Math.min(res, j-i+1);
                }
        return res == 50010 ? -1 : res;
    }
}
```

## 滑动窗口
```java
class Solution {
    public int shortestSubarray(int[] A, int K) {
        int sum = 0, begin = 0, res = -1;
        for(int i = 0; i < A.length; i++) { // 从零开始, 处理长度为一的数组情况
            if(A[i] >= K) return 1;
            sum += A[i];
            if(sum <= 0) {
                sum = 0;
                begin = i+1;
                continue;
            }

            for(int t = i-1; A[t+1] < 0; t--) {
                A[t] += A[t+1];
                A[t+1] = 0;
            }
            if(sum >= K) {
                while(sum - A[begin] >= K)
                    sum -= A[begin++];
                int len = i - begin + 1;
                if(res < 0 || res > len)
                    res = len;
            }
        }
        return res;
    }
}
```

## 滑动窗口（前缀和）
```java
class Solution {
    public int shortestSubarray(int[] A, int K) {
        int N = A.length;
        long[] pre_sum = new long[N+1];
        for(int i = 1; i <= N; i++)
            pre_sum[i] = pre_sum[i-1] + (long)A[i-1];

        int res = N+1;
        Deque<Integer> queue = new LinkedList<>();
        for(int i = 0; i < N+1; i++) {		// 注意 i < N+1
            while(!queue.isEmpty() && pre_sum[i] <= pre_sum[queue.getLast()])
                queue.removeLast();
            while(!queue.isEmpty() && pre_sum[i] >= pre_sum[queue.getFirst()] + K)
                res = Math.min(res, i - queue.removeFirst());
            queue.addLast(i);
        }
        return res == N+1 ? -1 : res;
    }
}
```