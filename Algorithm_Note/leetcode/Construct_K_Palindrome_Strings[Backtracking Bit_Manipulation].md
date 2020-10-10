#1400. 构造 K 个回文字符串
给你一个字符串 s 和一个整数 k 。请你用 s 字符串中 所有字符 构造 k 个非空 回文串 。

如果你可以用 s 中所有字符构造 k 个回文字符串，那么请你返回 True ，否则返回 False 。

**示例：**
输入：s = "annabelle", k = 2
输出：true
解释：可以用 s 中所有字符构造 2 个回文字符串。
一些可行的构造方案包括："anna" + "elble"，"anbna" + "elle"，"anellena" + "b"

##归纳法
由于我们需要根据给定的字符串 s 构造出 k 个非空的回文串，那么一种容易想到的步骤是：

1.求出字符串 s 最少可以构造的回文串个数 odd；

2.求出字符串 s 最多可以构造的回文串个数 s 的长度；

3.找出满足要求的范围，并判断 k 是否在其中。

那么我们如何分析步骤 1 呢？我们需要考虑回文串的性质：回文串分为两类，第一类是长度为奇数，回文中心为一个字符，例如 abcba，abacaba 等；第二类是长度为偶数，回文中心为两个相同的字符，例如 abccba，abaccaba 等。我们可以发现，对于第一类回文串，只有一种字符出现了奇数次，其余所有字符都出现了偶数次；而对于第二类回文串，所有字符都出现了偶数次。

因此，如果 s 中有 p 个字符出现了奇数次，q 个字符出现了偶数次，那么 s 最少可以构造的回文串个数就为 p，这是因为每一种出现了奇数次的字符都必须放在不同的回文串中。==特别地，如果 p为0，那么最少构造的回文串个数为 1。==

```java
class Solution {
    public boolean canConstruct(String s, int k) {
        int[] arr = new int[26];
        for(int i = 0; i < s.length(); i++)
            arr[s.charAt(i)-'a']++;

        int odd = 0;
        for(int i : arr)
            if(i%2 != 0) odd++;
        odd = Math.max(odd, 1);

        return k >= odd && k <= s.length();
    }
}
```
```java
异或优化
class Solution {
    public boolean canConstruct(String s, int k) {
        if(s.length() < k)
            return false;
        else if(s.length() == k)
            return true;
        else if(k >= 26)	// 可以把每个相同字母组合为一个回文字符串，最多26个
            return true;

        int status = 0;
        for (char c : s.toCharArray())
            status ^= 1 << (c - 'a');

        return Integer.bitCount(status) <= k;	// bitCount: 二进制下为 1 的位的个数
    }
}
```
**复杂度分析**

- 时间复杂度：O(K)

- 空间复杂度：O(1)