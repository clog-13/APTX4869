# 527. 单词缩写
给定一个由n个不重复非空字符串组成的数组，你需要按照以下规则为每个单词生成最小的缩写。

初始缩写由起始字母+省略字母的数量+结尾字母组成。
若存在冲突，亦即多于一个单词有同样的缩写，则使用更长的前缀代替首字母，直到从单词到缩写的映射唯一。换而言之，最终的缩写必须只能映射到一个单词。
若缩写并不比原单词更短，则保留原样。

**示例:**
输入: ["like", "god", "internal", "me", "internet", "interval", "intension", "face", "intrusion"]
输出: ["l2e","god","internal","me","i6t","interval","inte4n","f2e","intr4n"]

## 贪心
```java
class Solution {
    public List<String> wordsAbbreviation(List<String> dict) {
        int N = dict.size();
        String[] res = new String[N];
        int[] prefix = new int[N];

        for (int i = 0; i < N; i++)
            res[i] = helper(dict.get(i), 0);

        for (int i = 0; i < N; i++) {
            while (true) {
                List<Integer> deque = new ArrayList<>();
                for (int j = i+1; j < N; j++) {
                    if (res[i].equals(res[j]))
                        deque.add(j);
                }
                if (deque.isEmpty()) break;
                deque.add(i);
                for (int d : deque) 
                    res[d] = helper(dict.get(d), ++prefix[d]);
            }

        }
        return Arrays.asList(res);
    }

    private String helper(String word, int i) {
        int N = word.length();
        if (N - i <= 3) return word;
        return word.substring(0, i+1) + (N - i - 2) + word.charAt(N-1);
    }
}
```

## Trie树
```java
class Solution {
    public List<String> wordsAbbreviation(List<String> dict) {
        Map<String, List<WordIndex>> grasps = new HashMap<>();
        for (int i = 0; i < dict.size(); i++) {
            String tmp = helper(dict.get(i), 0);
            if (!grasps.containsKey(tmp))
                grasps.put(tmp, new ArrayList<>());
            grasps.get(tmp).add(new WordIndex(dict.get(i), i));
        }

        String[] res = new String[dict.size()];

        for (List<WordIndex> grasp : grasps.values()) {
            TrieTree tree = new TrieTree();
            for (WordIndex wi : grasp) {    // 生成树
                TrieTree cur = tree;
                for (char ch : wi.word.substring(1).toCharArray()) {
                    cur.cout++;
                    if (cur.childs[ch-'a'] == null)
                        cur.childs[ch-'a'] = new TrieTree();
                    cur = cur.childs[ch-'a'];
                }
            }

            for (WordIndex wi : grasp) {    // 处理树
                TrieTree cur = tree;
                int i = 1;
                for (char ch : wi.word.substring(1).toCharArray()) {
                    if (cur.cout == 1) break;
                    cur = cur.childs[ch-'a'];
                    i++;
                }
                res[wi.idx] = helper(wi.word, i-1);
            }
        }
        return Arrays.asList(res);
    }

    private String helper(String word, int i) {
        int N = word.length();
        if (N-i <= 3) return word;
        return word.substring(0, i+1) + (N-i-2+"") + word.charAt(N-1);
    }
}

class TrieTree {
    TrieTree[] childs;
    int cout;

    public TrieTree() {
        childs = new TrieTree[26];
        cout = 0;
    }
}

class WordIndex {
    String word;
    int idx;

    public WordIndex(String word, int idx) {
        this.word = word;
        this.idx = idx;
    }
}
```