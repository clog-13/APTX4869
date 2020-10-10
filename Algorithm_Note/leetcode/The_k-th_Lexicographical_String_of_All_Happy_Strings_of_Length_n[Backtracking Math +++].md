#1415. 长度为 n 的开心字符串中字典序第 k 小的字符串
一个 「开心字符串」定义为：

仅包含小写字母 ['a', 'b', 'c'].
对所有在 1 到 s.length - 1 之间的 i ，满足 s[i] != s[i + 1] （字符串的下标从 1 开始）。
比方说，字符串 "abc"，"ac"，"b" 和 "abcbabcbcb" 都是开心字符串，但是 "aa"，"baa" 和 "ababbc" 都不是开心字符串。

给你两个整数 n 和 k ，你需要将长度为 n 的所有开心字符串按字典序排序。

请你返回排序后的第 k 个开心字符串，如果长度为 n 的开心字符串少于 k 个，那么请你返回 空字符串 。

**示例：**
输入：n = 3, k = 9
输出："cab"
解释：长度为 3 的开心字符串总共有 12 个 ["aba", "abc", "aca", "acb", "bab", "bac", "bca", "bcb", "cab", "cac", "cba", "cbc"] 。第 9 个字符串为 "cab"
##DFS（回溯思想）
```java
class Solution {
    char[] num = {'a','b','c'};
    List<String> listres = new ArrayList<>();
    public String getHappyString(int n, int k) {
        dfs("", n);
        return k > listres.size() ? "" : listres.get(k-1);
    }

    private void dfs(String s, int n){
        int len = s.length();
        if(len == n){
            listres.add(s);
            return;
        }

        for(int i = 0; i < num.length; i ++){
            if(s.equals("") || s.charAt(len-1) != num[i]){
                dfs(s + num[i], n);
            }
        }
    }
}
```
```java
优化
class Solution {
    int N = 0;
    public String getHappyString(int n, int k) {
        char[] res = new char[n];
        for (int j = 0; j < 3; j++) {   // 处理 n 为 1 的情况
            res[0] = (char)(j+'a');
            talgo(res, k, 1);
            if (N == k) return new String(res);
        }
        return "";
    }

    private void talgo(char[] res, int k, int i) {
        if (i == res.length) {
            N++;
            return;
        }

        for (int j = 0; j < 3; j++) {
            char x = (char)(j+'a');
            if (res[i-1] != x) {
                res[i] = x;
                talgo(res, num, k, i+1);
                if (N == k) return;
            }
        }
    }
}
```
##数学(???)
211:2×2^2 + 2×2^1 + 2×2^0 = 11
![](../pic/The_k-th_Lexicographical_String_of_All_Happy_Strings_of_Length_n.png)
```java
class Solution {
    public String getHappyString(int n, int k) {
        int base = (int)Math.pow(2, n-1);
        StringBuilder res = new StringBuilder();
        k--;
        int idx = k/base;
        if(idx > 2) return res.toString();

        if(idx == 0) res.append("a");
        else if(idx == 1) res.append("b");
        else if(idx == 2) res.append("c");

        while(--n > 0) {
            k %= base;
            base >>= 1;
            idx = k/base;
            if(idx == 0) {		// 当前正常情况下为 a， 前一个如果为 a 则为 b
                if(res.charAt(res.length()-1) == 'a') res.append("b");
                else res.append("a");
            }else {				// 这里把 elif(idx==1) 和 elif(idx==2) 写到了一起
                if(res.charAt(res.length()-1) == 'c') res.append("b");
                else res.append("c");
            }
        }
        return res.toString();
    }
}
```