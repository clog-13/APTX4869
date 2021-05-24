# 1544. Make The String Great

Given a string `s` of lower and upper case English letters.

A good string is a string which doesn't have **two adjacent characters** `s[i]` and `s[i + 1]` where:

- `0 <= i <= s.length - 2`
- `s[i]` is a lower-case letter and `s[i + 1]` is the same letter but in upper-case or **vice-versa**.

To make the string good, you can choose **two adjacent** characters that make the string bad and remove them. You can keep doing this until the string becomes good.

Return *the string* after making it good. The answer is guaranteed to be unique under the given constraints.

**Notice** that an empty string is also good.

 

**Example 1:**

```
Input: s = "leEeetcode"
Output: "leetcode"
Explanation: In the first step, either you choose i = 1 or i = 2, both will result "leEeetcode" to be reduced to "leetcode".
```

**Example 2:**

```
Input: s = "abBAcC"
Output: ""
Explanation: We have many possible scenarios, and all lead to the same answer. For example:
"abBAcC" --> "aAcC" --> "cC" --> ""
"abBAcC" --> "abBA" --> "aA" --> ""
```

## 模拟

```java
class Solution {
    public String makeGood(String s) {
        StringBuffer res = new StringBuffer();
        int resIndex = -1;
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            if (res.length() > 0 && Character.toLowerCase(res.charAt(resIndex)) == Character.toLowerCase(ch) && res.charAt(resIndex) != ch) {
                res.deleteCharAt(resIndex);
                resIndex--;
            } else {
                res.append(ch);
                resIndex++;
            }
        }
        return res.toString();
    }
}
```

```java
class Solution {
    public String makeGood(String s) {
        char[] arr = new char[100];
        int j = 0;
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            if (j == 0) {
                arr[j++] = cur;
                continue;
            }

            int diff = arr[j - 1] - cur;
            if (32 == diff || diff == -32) j--;
            else arr[j++] = cur;
        }

        StringBuffer res = new StringBuffer();
        for (int i = 0; i < j; i++) res.append(arr[i]);
        return res.toString();
    }
}
```

