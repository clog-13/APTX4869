# 1371. Find the Longest Substring Containing Vowels in Even Counts
Given the string s, return the size of the longest substring containing each vowel an even number of times. That is, 'a', 'e', 'i', 'o', and 'u' must appear an even number of times.

**Example 1:**
```
Input: s = "eleetminicoworoep"
Output: 13
Explanation: The longest substring is "leetminicowor" which contains two each of the vowels: e, i and o and zero of the vowels: a and u.
```

**Example 2：**
```
Input:
s = "axxxxaxxa"
s = "aaa"
s = "aa"
Output:
8
2
2
```

## 状态压缩 + 下标数组

```java
import java.util.*;

class Solution {
    public int findTheLongestSubstring(String s) {
        int[] pos = new int[1<<5];
        int res = 0, status = 0;
        Arrays.fill(pos, -1);
        pos[0] = 0;     // 初始状态下标
        for (int i = 1; i <= s.length(); i++) {
            char ch = s.charAt(i-1);
            if (ch == 'a') status ^= (1 << 0);
            if (ch == 'e') status ^= (1 << 1);
            if (ch == 'i') status ^= (1 << 2);
            if (ch == 'o') status ^= (1 << 3);
            if (ch == 'u') status ^= (1 << 4);

            if (pos[status] == -1) pos[status] = i;
            else res = Math.max(res, i - pos[status]);
        }
        return res;
    }
}
```

