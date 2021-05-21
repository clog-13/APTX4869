# 1177. Can Make Palindrome from Substring


Given a string s, we make queries on substrings of s.

For each query queries[i] = [left, right, k], we may rearrange the substring s[left], ..., s[right], and then choose up to k of them to replace with any lowercase English letter. 

If the substring is possible to be a palindrome string after the operations above, the result of the query is true. Otherwise, the result is false.

Return an array answer[], where answer[i] is the result of the i-th query queries[i].

Note that: Each letter is counted individually for replacement so if for example s[left..right] = "aaa", and k = 2, we can only replace two of the letters.  (Also, note that the initial string s is never modified by any query.)

 

**Example :**

```
Input: s = "abcda", queries = [[3,3,0],[1,2,0],[0,3,1],[0,3,2],[0,4,1]]
Output: [true,false,false,true,true]
Explanation:
queries[0] : substring = "d", is palidrome.
queries[1] : substring = "bc", is not palidrome.
queries[2] : substring = "abcd", is not palidrome after replacing only 1 character.
queries[3] : substring = "abcd", could be changed to "abba" which is palidrome. Also this can be changed to "baab" first rearrange it "bacd" then replace "cd" with "ab".
queries[4] : substring = "abcda", could be changed to "abcba" which is palidrome.

```


##  状态前缀和

品味一下 状态前缀和 的正确性

```java
import java.util.*;

class Solution {
    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
        int[] preSt = new int[s.length()];
        int tmp = 0;
        for (int i = 0; i < s.length(); ++i) {
            tmp ^= (1 << (s.charAt(i) - 'a'));
            preSt[i] = tmp;
        }

        List<Boolean> res = new ArrayList<>();
        for (int[] q : queries) {
            int leSt = q[0]>0 ? preSt[q[0] - 1] : 0;
            int st = leSt ^ (preSt[q[1]]);
            int cnt = 0;
            while (st != 0) {
                cnt++;
                st -= (st&-st);  // st &= (st - 1); 
            }
            res.add(cnt>>1 <= q[2]);
        }
        return res;
    }
}
```
