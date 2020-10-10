#72. 编辑距离
给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。

你可以对一个单词进行如下三种操作：
- 插入一个字符
- 删除一个字符
- 替换一个字符

**示例：**
输入：word1 = "intention", word2 = "execution"
输出：5
解释：
intention -> inention (删除 't')
inention -> enention (将 'i' 替换为 'e')
enention -> exention (将 'n' 替换为 'x')
exention -> exection (将 'n' 替换为 'c')
exection -> execution (插入 'u')

##动态规划
我们可以发现，如果我们有单词 A 和单词 B：

- 对单词 A 删除一个字符和对单词 B 插入一个字符是等价的。例如当单词 A 为 doge，单词 B 为 dog 时，我们既可以删除单词 A 的最后一个字符 e，得到相同的 dog，也可以在单词 B 末尾添加一个字符 e，得到相同的 doge；

- 同理，对单词 B 删除一个字符和对单词 A 插入一个字符也是等价的；

- 对单词 A 替换一个字符和对单词 B 替换一个字符是等价的。例如当单词 A 为 bat，单词 B 为 cat 时，我们修改单词 A 的第一个字母 b -> c，和修改单词 B 的第一个字母 c -> b 是等价的。

这样以来，本质不同的操作实际上可以简化成三种：

- 在单词 A 中插入一个字符；

- 在单词 B 中插入一个字符；
（也可看做： 在单词 A 中删除一个字符 然后可以理解用一个*null*暂时代替）
A：“a b c *null* d e”
B：“a b c *null* d e”

- 修改单词 A 的一个字符。

我们用 D[i][j] 表示 A 的前 i 个字母和 B 的前 j 个字母之间的编辑距离：

- D[i][j-1] 为 A 的前 i 个字符和 B 的前 j - 1 个字符编辑距离的子问题。即对于 B 的第 j 个字符，我们在 A 的末尾添加了一个相同的字符，那么 D[i][j] 最小可以为 D[i][j-1] + 1；

- D[i-1][j] 为 A 的前 i - 1 个字符和 B 的前 j 个字符编辑距离的子问题。即对于 A 的第 i 个字符，我们在 B 的末尾添加了一个相同的字符，那么 D[i][j] 最小可以为 D[i-1][j] + 1；

- D[i-1][j-1] 为 A 前 i - 1 个字符和 B 的前 j - 1 个字符编辑距离的子问题。即对于 B 的第 j 个字符，我们修改 A 的第 i 个字符使它们相同，那么 D[i][j] 最小可以为 D[i-1][j-1] + 1。特别地，如果 A 的第 i 个字符和 B 的第 j 个字符原本就相同，那么我们实际上不需要进行修改操作。在这种情况下，D[i][j] 最小可以为 D[i-1][j-1]。


```java
自底向上
class Solution {
    public int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        int[][] dis = new int[len1+1][len2+1];

        for(int i = 1; i <= len1; i++)
            dis[i][0] = i;
        for(int i = 1; i <= len2; i++)
            dis[0][i] = i;

        for(int i = 1; i <= len1; i++) {
            for(int j = 1; j <= len2; j++) {
                if(word1.charAt(i-1) == word2.charAt(j-1))
                    dis[i][j] = dis[i-1][j-1];
                else
                    dis[i][j] = 1 + Math.min(Math.min(dis[i-1][j], dis[i][j-1]), dis[i-1][j-1]);
            }
        }
        return dis[len1][len2];
    }
}
```
**复杂度分析**
- 时间复杂度 ：O(mn)，其中 m 为 word1 的长度，n 为 word2 的长度。
- 空间复杂度 ：O(mn)，我们需要大小为 O(mn) 的 D 数组来记录状态值。

```java
自顶向下
class Solution {
	public int minDistance(String word1, String word2) {
    	return dp(word1.length(), word2.length);
    }
    private int dp(int i, int j) {
        if(i == 0) return j;
        if(j == 0) return i;

        if(A.charAt(i-1) == B.charAt(j-1))
            return dp(i-1, j-1);
        else
            return 1+Math.min(dp(i-1, j), Math.min(dp(i, j-1), dp(i-1, j-1)));
    }
}
```
```java
自顶向下 + 记忆化
class Solution {
    int[][] memo;
    String word1;
    String word2;
    public int minDistance(String word1, String word2) {
        memo = new int[word1.length()][word2.length()];
        this.word1 = word1;
        this.word2 = word2;
        return dp(word1.length() - 1, word2.length() - 1);
    }

    public int dp(int i, int j) {
        if (i == -1) return j+1;	// 为了记忆化memo[0][j]
        if (j == -1) return i+1;	// 为了记忆化memo[i][0]
        if (memo[i][j] > 0)
            return memo[i][j];

        int res = 0;
        if (word1.charAt(i) == word2.charAt(j))
            res = dp(i - 1, j - 1);
        else
			res = 1 + Math.min(dp(i-1, j), Math.min(dp(i, j-1), dp(i-1, j-1)));

        memo[i][j] = res;
        return res;
    }
}
```