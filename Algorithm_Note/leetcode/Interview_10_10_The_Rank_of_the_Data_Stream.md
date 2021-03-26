# 面试题 10.10. 数字流的秩

假设你正在读取一串整数。每隔一段时间，你希望能找出数字 x 的秩(小于或等于 x 的值的个数)。请实现数据结构和算法来支持这些操作，也就是说：

实现 `track(int x)` 方法，每读入一个数字都会调用该方法；

实现 `getRankOfNumber(int x)` 方法，返回小于或等于 x 的值的个数。

**示例:**

```
输入:
["StreamRank", "getRankOfNumber", "track", "getRankOfNumber"]
[[], [1], [0], [0]]
输出:
[null,0,null,1]
```



## BST

还可以用 树状数组

```java
class StreamRank {
    Node root;

    public StreamRank() { }

    public void track(int x) {
        if (root == null) {
            root = new Node(x);
            return;
        } else {
            findPlaceToInsert(x, root);
        }
    }

    // 返回小于或等于 x 的值的个数
    public int getRankOfNumber(int x) {
        Node cur = root;
        int res = 0;
        while (true) {
            if (cur == null) break;
            if (x == cur.val) {
                return res + cur.cnt;
            } else if (x > cur.val) {
                res += cur.cnt;
                cur = cur.ri;
            } else if (x < cur.val) {
                cur = cur.le;
            }
        }
        return res;
    }

    private void findPlaceToInsert(int x, Node cur) {
        if (x == cur.val) {
            cur.cnt++;
        } else if (x < cur.val) {
            cur.cnt++;
            if (cur.le == null) {
                cur.le = new Node(x);
            } else {
                findPlaceToInsert(x, cur.le);
            }
        } else if (x > cur.val) {
            if (cur.ri == null) {
                cur.ri = new Node(x);
            } else {
                findPlaceToInsert(x, cur.ri);
            }
        }
    }

    static class Node {
        int val, cnt = 1;
        Node le, ri;

        Node(int x) { val = x; }
    }
}
```

