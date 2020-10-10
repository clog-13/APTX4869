# 411. 最短特异单词缩写
字符串 "word" 包含以下这些缩写形式：

["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
给一个目标字符串和一个字符串字典，为目标字符串找一个 最短 长度的缩写字符串，同时这个缩写字符串不是字典中其他字符串的缩写形式。

缩写形式中每一个 数字 或者字母都被视为长度为 1 。比方说，缩写形式 "a32bc" 的长度为 4 而不是 5 。

**注意:**
如果像第二个示例一样有多个有效答案，你可以返回它们中的任意一个。

**示例:**
"apple", ["blade"] -> "a4" (因为 "5" 或者 "4e" 同时也是 "blade" 的缩写形式，所以它们是无效的缩写)

"apple", ["plain", "amber", "blade"] -> "1p3" (其他有效的缩写形式还包括 "ap3", "a3e", "2p2", "3le", "3l1")。

### 模拟 + 位运算
```java
class Solution {
    public String minAbbreviation(String target, String[] dictionary) {
        int N = target.length(), M = 1<<N;
        List<Integer> filter = new ArrayList<>();
        for (String str : dictionary) {
            if (str.length() != N) continue;
            int fil = 0, cur = M>>1;
            for (int i = 0; i < str.length(); i++) {
                if (target.charAt(i) != str.charAt(i)) fil |= cur;  // 记录差异点
                cur >>= 1;
            }
            filter.add(fil);
        }
        if (filter.isEmpty()) return String.valueOf(target.length());

        int minMask = M-1, minLen = N;
        for (int mask = 1; mask < M; mask++) {  // 1 为字母， 0 为缩写部分
            int len = getLen(mask, N);
            if (len >= minLen) continue;
            boolean confese = false;
            for (int fil : filter)
                if ((fil & mask) == 0) { confese = true; break; }  // 差异点全部被缩写了
            if (confese) continue;
            minMask = mask; minLen = len;
        }

        StringBuilder res = new StringBuilder();
        int cur = M>>1, cnt = 0;
        for (int i = 0; i < N; i++) {
            if ((cur & minMask) != 0) {
                if (cnt != 0) res.append(cnt);
                res.append(target.charAt(i));
                cnt = 0;
            } else {
                cnt++;
            }
            cur >>= 1;
        }
        if (cnt != 0) res.append(cnt);
        return res.toString();
    }

    private int getLen(int mask, int N) {
        int res = 0, cnt = 0;
        while (N-- > 0) {
            if ((mask & 1) != 0) {  // 1 为字母
                res++; cnt = 0;
            } else {
                cnt++;              // 0 为缩写部分
            }
            mask >>= 1;
        }
        if (cnt != 0) res++;
        return res;
    }
}
```