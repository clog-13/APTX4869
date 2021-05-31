# 30. Substring with Concatenation of All Words

You are given a string `s` and an array of strings `words` of **the same length**. Return all starting indices of substring(s) in `s` that is a concatenation of each word in `words` **exactly once**, **in any order**, and **without any intervening characters**.

You can return the answer in **any order**. 



**Example 1:**

```
Input: s = "barfoothefoobarman", words = ["foo","bar"]
Output: [0,9]
Explanation: Substrings starting at index 0 and 9 are "barfoo" and "foobar" respectively.
The output order does not matter, returning [9,0] is fine too.
```

**Example 2:**

```
Input: s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
Output: []
```



## 剪枝 + 枚举

```java
import java.util.*;

class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        int tarCnt = words.length, wLen = words[0].length();
        HashMap<String, Integer> wordCntMap = new HashMap<>();
        for (String w : words) wordCntMap.put(w, wordCntMap.getOrDefault(w, 0) + 1);

        for (int start = 0; start < wLen; start++) {    // 要以每个起点匹配一次，否则可能会漏
            int curCnt = 0;    // 记录当前字串有多少个单词
            HashMap<String, Integer> curCntMap = new HashMap<>();
            for (int i = start; i <= s.length() - tarCnt*wLen; i += wLen) {    // 每次移动一个单词长度
                while (curCnt < tarCnt) {
                    String cur = s.substring(i + curCnt*wLen, i + (curCnt+1)*wLen);
                    if (wordCntMap.containsKey(cur)) {
                        curCntMap.put(cur, curCntMap.getOrDefault(cur, 0) + 1);
                        // 情况三: 遇到了符合的单词，但是次数超了
                        if (curCntMap.get(cur) > wordCntMap.get(cur)) {
                            int reCnt = 0;
                            while (curCntMap.get(cur) > wordCntMap.get(cur)) {  // 滑动窗口
                                String firstWord = s.substring(i + reCnt*wLen, i + (reCnt+1)*wLen);
                                curCntMap.put(firstWord, curCntMap.get(firstWord)-1);
                                reCnt++;
                            }
                            curCnt -= (reCnt-1);  // 加 1 是因为我们把当前单词加入到了 curCntMap 中
                            i += (reCnt-1)*wLen;
                            break;
                        }
                        curCnt++;
                    } else {    // 情况二: 出现不符合的单词,直接跳到不符合单词后面
                        curCntMap.clear();
                        i += curCnt*wLen;
                        curCnt = 0;
                        break;
                    }
                }

                if (curCnt == tarCnt) {  // 情况一
                    res.add(i);

                    String firstWord = s.substring(i, i + wLen);
                    curCntMap.put(firstWord, curCntMap.get(firstWord) - 1);
                    curCnt--;
                }
            }
        }

        return res;
    }
}
```

