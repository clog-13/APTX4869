#  Trie_Tree前缀树

Trie tree (前缀树)，包含 `insert`, `search`, 和 `startsWith` 这三个操作。

**示例:**

```
Trie trie = new Trie();

trie.insert("apple");
trie.search("apple");   // 返回 true
trie.search("app");     // 返回 false
trie.startsWith("app"); // 返回 true
trie.insert("app");   
trie.search("app");     // 返回 true
```



## 代码

```java
class Trie {
    Node root = new Node();

    class Node {
        boolean isEnd = false;
        Node[] children = new Node[26];
    }

    public Trie() {}    // 不写会增加运行速度

    public void insert(String word) {
        Node node = find(word, true);
        node.isEnd = true;
    }

    public boolean search(String word) {
        Node node = find(word, false);
        return node != null && node.isEnd;
    }

    public boolean startsWith(String prefix) {
        Node node = find(prefix, false);
        return node != null;
    }

    public Node find(String word, boolean insert) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (node.children[index] == null) {
                if (insert) node.children[index] = new Node();
                else return null;
            }
            node = node.children[index];
        }
        return node;
    }
}
```

