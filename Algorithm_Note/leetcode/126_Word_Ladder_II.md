# 126. Word Ladder II
A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:

Every adjacent pair of words differs by a single letter.
Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
sk == endWord
Given two words, beginWord and endWord, and a dictionary wordList, return all the shortest transformation sequences from beginWord to endWord, or an empty list if no such sequence exists. Each sequence should be returned as a list of the words [beginWord, s1, s2, ..., sk].

 

**Example 1:**
```
Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
Output: [["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
Explanation: There are 2 shortest transformation sequences:
"hit" -> "hot" -> "dot" -> "dog" -> "cog"
"hit" -> "hot" -> "lot" -> "log" -> "cog"
```

**Example 2:**
```
Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
Output: []
Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.
```

## 双向BFS

```java
import java.util.*;

public class Solution {
    List<List<String>> res = new ArrayList<>();
    Map<String, List<String>> mapTree = new HashMap<>();

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordList.contains(endWord)) return res;

        Set<String> begin = new HashSet<>(), end = new HashSet<>();
        begin.add(beginWord); end.add(endWord);
        List<String> track = new ArrayList<>();
        track.add(beginWord);
        if (buildTree(wordSet, begin, end, true)) {
            dfs(beginWord, endWord, track);
        }
        return res;
    }

    boolean buildTree(Set<String> wordSet, Set<String> begin, Set<String> end, boolean isFront) {
        if (begin.size() == 0) return false;
        if (begin.size() > end.size()) return buildTree(wordSet, end, begin, !isFront);

        wordSet.removeAll(begin);  // 防止形成环
        boolean isMeet = false;
        Set<String> next = new HashSet<>();
        for (String word : begin) {  // 遍历可选分支
            char[] chars = word.toCharArray();
            for (char i = 0; i < chars.length; i++) {  // 字母的每一位
                char backup = chars[i];
                for (char ch = 'a'; ch <= 'z'; ch++) {  // 每一位的每种变法
                    chars[i] = ch;
                    String tmp = String.valueOf(chars);
                    if (wordSet.contains(tmp)) {  // 可以变
                        next.add(tmp);  // 暂存下一层
                        String key = isFront ? word : tmp;
                        String nex = isFront ? tmp : word;
                        if (end.contains(tmp)) isMeet = true;  // 碰头
                        if (!mapTree.containsKey(key)) mapTree.put(key, new ArrayList<>());
                        mapTree.get(key).add(nex);  // 记录路径
                    }
                }
                chars[i] = backup;
            }
        }
        if (isMeet) return true;
        return buildTree(wordSet, next, end, isFront);
    }

    void dfs(String beginWord, String endWord, List<String> track) {
        if (beginWord.equals(endWord)) {
            res.add(new ArrayList<>(track));
            return;
        }
        if (mapTree.containsKey(beginWord)) {
            for (String word : mapTree.get(beginWord)) {
                track.add(word);
                dfs(word, endWord, track);
                track.remove(track.size()-1);
            }
        }
    }
}
```

## BFS

```java
import java.util.*;

class Solution {
    private static final int INF = 0x3f3f3f3f;
    private final Map<String, Integer> wordId = new HashMap<>(); // 单词到id的映射
    private final ArrayList<String> idWord = new ArrayList<>();  // id到单词的映射

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        int id = 0; // 将wordList所有单词加入wordId中(相同的只保留一个),并为每一个单词分配一个id
        for (String word : wordList) {
            if (!wordId.containsKey(word)) {
                wordId.put(word, id++);
                idWord.add(word);
            }
        }
        // 若endWord不在wordList中 则无解
        if (!wordId.containsKey(endWord)) return new ArrayList<>(); 
        if (!wordId.containsKey(beginWord)) {   // 把beginWord也加入wordId中
            wordId.put(beginWord, id++);
            idWord.add(beginWord);
        }

        // 初始化存边用的数组
        // 图的边
        ArrayList<Integer>[] edges = new ArrayList[idWord.size()];
        for (int i = 0; i < idWord.size(); i++) edges[i] = new ArrayList<>();

        // 添加边
        for (int i = 0; i < idWord.size(); i++) {
            for (int j = i + 1; j < idWord.size(); j++) {
                // 若两者可以通过转换得到 则在它们间建一条无向边
                if (transformCheck(idWord.get(i), idWord.get(j))) {
                    edges[i].add(j);
                    edges[j].add(i);
                }
            }
        }

        int destID = wordId.get(endWord); // 目的ID
        List<List<String>> res = new ArrayList<>(); // 存答案
        int[] cost = new int[id]; // 到每个点的代价
        for (int i = 0; i < id; i++) cost[i] = INF; // 每个点的代价初始化为无穷大

        // 将起点加入队列 并将其cost设为0
        Queue<ArrayList<Integer>> queue = new LinkedList<>();
        ArrayList<Integer> tmpBegin = new ArrayList<>();
        tmpBegin.add(wordId.get(beginWord));
        queue.add(tmpBegin);
        cost[wordId.get(beginWord)] = 0;

        // 开始广度优先搜索
        while (!queue.isEmpty()) {  // dijk
            ArrayList<Integer> cur = queue.poll();
            int last = cur.get(cur.size() - 1); // 最近访问的点
            if (last == destID) { // 若该点为终点则将其存入答案res中
                ArrayList<String> tmp = new ArrayList<>();
                for (int index : cur)
                    tmp.add(idWord.get(index)); // 转换为对应的word
                res.add(tmp);
            } else { // 该点不为终点 继续搜索
                for (int i = 0; i < edges[last].size(); i++) {
                    int to = edges[last].get(i);
                    // 把代价相同的不同路径全部保留下来
                    if (cost[to] >=cost[last] + 1) {
                        cost[to] = cost[last] + 1;
                        // 把to加入路径中
                        ArrayList<Integer> tmp = new ArrayList<>(cur); tmp.add(to);
                        queue.add(tmp); // 把这个路径加入队列
                    }
                }
            }
        }
        return res;
    }

    // 两个字符串是否可以通过改变一个字母后相等
    boolean transformCheck(String str1, String str2) {
        int diff = 0;
        for (int i = 0; i < str1.length() && diff < 2; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                diff++;
            }
        }
        return diff == 1;
    }
}
```

