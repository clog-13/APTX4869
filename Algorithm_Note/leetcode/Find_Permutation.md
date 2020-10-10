# 484. 寻找排列
现在给定一个只由字符 'D' 和 'I' 组成的 秘密签名。'D' 表示两个数字间的递减关系，'I' 表示两个数字间的递增关系。并且 秘密签名 是由一个特定的整数数组生成的，该数组唯一地包含 1 到 n 中所有不同的数字（秘密签名的长度加 1 等于 n）。例如，秘密签名 "DI" 可以由数组 [2,1,3] 或 [3,1,2] 生成，但是不能由数组 [3,2,4] 或 [2,1,3,4] 生成，因为它们都不是合法的能代表 "DI" 秘密签名 的特定串。

现在你的任务是找到具有最小字典序的 [1, 2, ... n] 的排列，使其能代表输入的 秘密签名。

**示例：**
输入： "DI"
输出： [2,1,3]
解释： [2,1,3] 和 [3,1,2] 可以生成秘密签名 "DI"，
但是由于我们要找字典序最小的排列，因此你需要输出 [2,1,3]。

### 贪心
```java
public class Solution {
    public int[] findPermutation(String s) {
        int[] res = new int[s.length()+1];
        for (int i = 0; i < res.length; i++)
            res[i] = i + 1;
        int i = 1;
        while (i <= s.length()) {
            int j = i;
            while (i <= s.length() && s.charAt(i - 1) == 'D')
                i++;
            reverse(res, j - 1, i);
            i++;
        }
        return res;
    }
    public void reverse(int[] a, int start, int end) {
        for (int i = 0; i < (end - start) / 2; i++) {
            int temp = a[i + start];
            a[i + start] = a[end - i - 1];
            a[end - i - 1] = temp;
        }
    }
}
```
### DFS
```java
class Solution {
    String str;
    int N;
    public int[] findPermutation(String s) {
        str = s;
        N = s.length();
        int[] res = new int[s.length() + 1];
        dfs(res, 0);
        return res;
    }

    private void dfs(int[] ret, int idx) {
        if (idx >= N) {
            if (ret[N] == 0) ret[N] = N + 1;
            return;
        }
        int id = idx, dCnt = 0, iCnt = 0;
        while (id < N && str.charAt(id++) == 'D')
            dCnt++;
        while (id < N && str.charAt(id++) == 'I')
            iCnt++;

        int digit = dCnt + idx+1;
        for (int i = 0; i <= dCnt; i++)
            ret[idx++] = digit--;
        digit = idx+1;
        for (int i = 0; i < iCnt-1; i++)	// 注意这里的 iCnt-1, 会在每组 DI 前面留一个 I
            ret[idx++] = digit++;
        dfs(ret, idx);
    }
}
```