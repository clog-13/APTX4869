# 1347. Minimum Number of Steps to Make Two Strings Anagram

Given two equal-size strings `s` and `t`. In one step you can choose **any character** of `t` and replace it with **another character**.

Return *the minimum number of steps* to make `t` an anagram of `s`.

An **Anagram** of a string is a string that contains the same characters with a different (or the same) ordering.

 

**Example 1:**

```
Input: s = "bab", t = "aba"
Output: 1
Explanation: Replace the first 'a' in t with b, t = "bba" which is anagram of s.
```

**Example 2:**

```
Input: s = "leetcode", t = "practice"
Output: 5
Explanation: Replace 'p', 'r', 'a', 'i' and 'c' from t with proper characters to make t anagram of s.
```



## HashMap

```c++
class Solution {
public:
    int minSteps(string s, string t) {
        int cout[26] = {0};
        for (char c : s) cout[c-'a']++;
        int ans = 0;
        for (char c : t) {
            if (cout[c-'a'] == 0) {
                ans++;
            } else {
                cout[c-'a']++;
            }
        }
        return ans;
    }
};
```



```java
class Solution {
    public int minSteps(String s, String t) {
        int N = s.length();
        int[] cout_s = new int[26], cout_t = new int[26];
        for (int i = 0; i < N; i++) {
            cout_s[s.charAt(i)-'a']++;
            cout_t[t.charAt(i)-'a']++;
        }

        int ans = 0;
        for (int i = 0; i < 26; i++) {
            ans += Math.abs(cout_t[i]-cout_s[i]);
        }
        return ans/2;
    }
}
```

