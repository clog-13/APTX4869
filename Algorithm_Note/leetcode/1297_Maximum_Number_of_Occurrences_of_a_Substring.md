# 1297. Maximum Number of Occurrences of a Substring
Given a string s, return the maximum number of ocurrences of any substring under the following rules:

The number of unique characters in the substring must be less than or equal to maxLetters.
The substring size must be between minSize and maxSize inclusive.

**Example 1:**
```
Input: s = "aababcaab", maxLetters = 2, minSize = 3, maxSize = 4
Output: 2
Explanation: Substring "aab" has 2 ocurrences in the original string.
It satisfies the conditions, 2 unique letters and size 3 (between minSize and maxSize).
```

## 字典树

```java
class Solution {
    public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
        int N = s.length();
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) arr[i] = s.charAt(i)-'a';

        TreeNode root = new TreeNode();
        for (int i = 0; i <= N-minSize; i++) {  // 遍历每个字串
            int state = 0, diff = 0;
            TreeNode node = root;
            for (int j = 0; j < minSize; j++) {
                int preSt = state;
                int cur = arr[i+j];
                state |= (1<<cur);
                if (state != preSt) if (++diff > maxLetters) break;
                if (node.childs[cur] == null) node.childs[cur] = new TreeNode();
                node = node.childs[cur];
                if (j == minSize-1) node.cnt++;  // i-j字串数量加一
            }
        }

        return maxFreq(root);
    }

    int maxFreq(TreeNode root) {
        if (root == null) return 0;
        int res = root.cnt;
        if (root.childs == null) return res;
        for (int i = 0; i < 26; i++) {
            res = Math.max(res, maxFreq(root.childs[i]));
        }
        return res;
    }

    private static class TreeNode {
        int cnt;
        TreeNode[] childs = new TreeNode[26];
    }
}
```

## 枚举

```java
import java.util.*;

class Solution {
    public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
        int res = 0;
        Map<String,Integer> map = new HashMap<>();
        for (int i = 0; i <= s.length()-minSize; i++) {
            String sub = s.substring(i, i+minSize);
            map.put(sub, map.getOrDefault(sub, 0) + 1);
        }
        for (String sub : map.keySet()) {
            if (map.get(sub) > res && checkCout(sub, maxLetters)) {
                res = map.get(sub);
            }
        }
        return res;
    }
    
    private boolean checkCout(String s, int maxLetter) {
        Map<Character, Integer> map = new HashMap<>();
        char[] arr = s.toCharArray();
        for (char c : arr) {
            map.put(c, map.getOrDefault(c, 0) + 1);
            if (map.size() > maxLetter) return false;
        }
        return true;
    }
}
```

