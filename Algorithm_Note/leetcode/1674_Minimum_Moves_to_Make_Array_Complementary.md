# 1674. 使数组互补的最少操作次数

给你一个长度为 **偶数** `n` 的整数数组 `nums` 和一个整数 `limit` 。每一次操作，你可以将 `nums` 中的任何整数替换为 `1` 到 `limit` 之间的另一个整数。

如果对于所有下标 `i`（**下标从** `0` **开始**），`nums[i] + nums[n - 1 - i]` 都等于同一个数，则数组 `nums` 是 **互补的** 。例如，数组 `[1,2,3,4]` 是互补的，因为对于所有下标 `i` ，`nums[i] + nums[n - 1 - i] = 5` 。

返回使数组 **互补** 的 **最少** 操作次数。

**示例：**

```
输入：nums = [1,2,4,3], limit = 4
输出：1
解释：经过 1 次操作，你可以将数组 nums 变成 [1,2,2,3]（加粗元素是变更的数字）：
nums[0] + nums[3] = 1 + 3 = 4.
nums[1] + nums[2] = 2 + 2 = 4.
nums[2] + nums[1] = 2 + 2 = 4.
nums[3] + nums[0] = 3 + 1 = 4.
对于每个 i ，nums[i] + nums[n-1-i] = 4 ，所以 nums 是互补的。
```

## 差分

```c++
class Solution {
public:
    int minMoves(vector<int>& nums, int limit) {
        
        vector<int> diff(limit * 2 + 2, 0);  // // diff[x] 表示最终互补的数字和为 x，需要的操作数

        int N = nums.size();
        for (int i = 0; i < N / 2; i ++) {
            int A = nums[i], B = nums[N - 1 - i];

            int l = 2, r = 2 * limit;
            diff[l] += 2, diff[r + 1] -= 2;

            l = 1 + min(A, B), r = limit + max(A, B);
            diff[l] -= 1, diff[r + 1] += 1;

            int sum = A + B;
            diff[sum] -= 1; diff[sum + 1] += 1;
        }

        // 依次求和，得到 最终互补的数字和 i 的时候，需要的操作数 tmp
        // 取最小值
        int res = N, tmp = 0;
        for (int i = 2; i <= 2 * limit; i ++) {
            tmp += diff[i];
            if (tmp < res) res = tmp;
        }
        return res;
    }
};
```

