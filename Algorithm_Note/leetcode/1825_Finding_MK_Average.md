# 1825. 求出 MK 平均值

给你两个整数 `m` 和 `k` ，以及数据流形式的若干整数。你需要实现一个数据结构，计算这个数据流的 **MK 平均值** 。

**MK 平均值** 按照如下步骤计算：

1. 如果数据流中的整数少于 `m` 个，**MK 平均值** 为 `-1` ，否则将数据流中最后 `m` 个元素拷贝到一个独立的容器中。
2. 从这个容器中删除最小的 `k` 个数和最大的 `k` 个数。
3. 计算剩余元素的平均值，并 **向下取整到最近的整数** 。

请你实现 `MKAverage` 类：

- `MKAverage(int m, int k)` 用一个空的数据流和两个整数 `m` 和 `k` 初始化 **MKAverage** 对象。
- `void addElement(int num)` 往数据流中插入一个新的元素 `num` 。
- `int calculateMKAverage()` 对当前的数据流计算并返回 **MK 平均数** ，结果需 **向下取整到最近的整数** 。

 

**示例：**

```
输入：
["MKAverage", "addElement", "addElement", "calculateMKAverage", "addElement", "calculateMKAverage", "addElement", "addElement", "addElement", "calculateMKAverage"]
[[3, 1], [3], [1], [], [10], [], [5], [5], [5], []]
输出：
[null, null, null, -1, null, 3, null, null, null, 5]

解释：
MKAverage obj = new MKAverage(3, 1); 
obj.addElement(3);        // 当前元素为 [3]
obj.addElement(1);        // 当前元素为 [3,1]
obj.calculateMKAverage(); // 返回 -1 ，因为 m = 3 ，但数据流中只有 2 个元素
obj.addElement(10);       // 当前元素为 [3,1,10]
obj.calculateMKAverage(); // 最后 3 个元素为 [3,1,10]
                          // 删除最小以及最大的 1 个元素后，容器为 [3]
                          // [3] 的平均值等于 3/1 = 3 ，故返回 3
obj.addElement(5);        // 当前元素为 [3,1,10,5]
obj.addElement(5);        // 当前元素为 [3,1,10,5,5]
obj.addElement(5);        // 当前元素为 [3,1,10,5,5,5]
obj.calculateMKAverage(); // 最后 3 个元素为 [5,5,5]
                          // 删除最小以及最大的 1 个元素后，容器为 [5]
                          // [5] 的平均值等于 5/1 = 5 ，故返回 5
```

 

**提示：**

- `3 <= m <= 105`
- `1 <= k*2 < m`
- `1 <= num <= 105`
- `addElement` 与 `calculateMKAverage` 总操作次数不超过 `105` 次。



## 线段树（前缀二分）

```java
class MKAverage {
    int M, K, tot, maxN = 100005;
    Node[] tr = new Node[maxN*4];
    int[] arr = new int[maxN];

    public MKAverage(int m, int k) {
        M = m; K = k;
        build(1, 1, maxN);
    }

    public void addElement(int num) {
        arr[tot++] = num;
        update(1, num, 1);
        if (tot > M) update(1, arr[tot -M-1], -1);
    }

    public int calculateMKAverage() {
        if (tot < M) return -1;
        long sa = query(1, K), sb = query(1, M-K);
        return (int) (sb-sa)/(M-2*K);
    }

    void build(int u, int le, int ri) {
        if (le == ri) tr[u] = new Node(le, ri);
        else {
            tr[u] = new Node(le, ri);
            int mid = le+ri>>1;
            build(u<<1, le, mid);
            build(u<<1|1, mid+1, ri);
        }
    }

    void update(int u, int idx, int delta) {
        if (idx > tr[u].ri || idx < tr[u].le) return;
        tr[u].sum += (long) idx*delta;
        tr[u].cnt += delta;
        if (tr[u].le == tr[u].ri) return;
        update(u<<1, idx, delta);
        update(u<<1|1, idx, delta);
    }

    long query(int u, int size) {
        if (tr[u].cnt <= size) return tr[u].sum;
        if (tr[u].le == tr[u].ri) {  // 多个数相同 ex:sum*2/7 = 7个中的2个
            return tr[u].sum * size / tr[u].cnt;
        }
        if (tr[u<<1].cnt >= size) return query(u<<1, size);
        else return tr[u<<1].sum + query(u<<1|1, size - tr[u<<1].cnt);
    }

    static class Node {
        long sum;
        int le, ri, cnt;
        public Node(int l, int r) {
            le = l; ri = r;
        }
    }
}
```

