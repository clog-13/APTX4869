# 线段树（Segmen Tree）

![](pic\segment_tree01.png)

```java
/**
 * 求和
 */
class Main {
    int maxN = 100010;
    int[] arr = new int[maxN];
    Node[] segs = new Node[4*maxN];

    public Main(int[] nums) {
        System.arraycopy(nums, 0, arr, 1, nums.length);
        build(1, 1, nums.length+1);
    }

    private void build(int root, int le, int ri) {
        if (le == ri) {
            segs[root] = new Node(le, ri, arr[le]);
        } else {
            segs[root] = new Node(le, ri, 0);

            int mid = le+ri >> 1;
            build(root<<1, le, mid);
            build(root<<1 | 1, mid+1, ri);

            cout_up(root);
        }
    }

    private void cout_up(int idx) {
        segs[idx].sum = segs[idx<<1].sum + segs[idx<<1 | 1].sum;
    }
    
    // 查询操作，le到ri之间的和，查询时要自己输入root(1)的值
    private int query(int root, int le, int ri) {
        if (le <= segs[root].le && segs[root].ri <= ri) return segs[root].sum;
        int mid = (segs[root].le + segs[root].ri) >> 1;
        int res = 0;
        if (le <= mid) res = query(root<<1, le, ri);
        if (ri > mid) res += query((root<<1) | 1, le, ri);
        return res;
    }

    // 修改操作，tar位置加上val，修改时要自己输入root(1)的值
    private void update(int root, int tar, int val) {
        if (segs[root].le == segs[root].ri) segs[root].sum += val;
        else {
            int mid = (segs[root].le + segs[root].ri) >> 1;
            if (tar <= mid) update(root<<1, tar, val);
            else update(root<<1 | 1, tar, val);
            cout_up(root);
        }
    }

    private static class Node {
        int le, ri, sum;
        public Node(int l, int r, int s) {
            le = l; ri = r; sum = s;
        }
    }
}
```

```java
/**
 * 最大（小）值
 */
class Segment_Tree_Max {
    int maxN = 100010;
    int[] arr = new int[maxN];
    Node[] segs = new Node[4*maxN];

    public Segment_Tree_Max(int[] nums) {
        System.arraycopy(nums, 0, arr, 1, nums.length);
        build(1, 1, nums.length+1);
    }

    private void build(int root, int le, int ri) {
        if (le == ri) {
            segs[root] = new Node(le, ri, arr[le]);
        } else {
            segs[root] = new Node(le, ri, 0);

            int mid = le+ri >> 1;
            build(root<<1, le, mid);
            build(root<<1 | 1, mid+1, ri);

            push_up(root);
        }
    }

    // 查询操作，le到ri之间的和，查询时要自己输入root(1)的值
    private int query(int le, int ri) { return query(1, le, ri); }
    private int query(int root, int le, int ri) {
        if (le <= segs[root].le && segs[root].ri <= ri) return segs[root].max;

        int mid = (segs[root].le + segs[root].ri) >> 1;
        int res = 0;
        if (le <= mid) res = query(root<<1, le, ri);
        if (ri > mid) res = Math.max(res, query(root<<1 | 1, le, ri));
        return res;
    }

    // 修改操作，在idx结点中，tar位置 加val
    private void update(int tar, int val) { update(1, tar, val); }
    private void update(int root, int tar, int val) {
        if (segs[root].le == segs[root].ri) {
            segs[root].max += val;
        } else {
            int mid = (segs[root].le + segs[root].ri) >> 1;
            if (tar <= mid) update(root<<1, tar, val);
            else update(root<<1 | 1, tar, val);

            push_up(root);
        }
    }

    // 修改操作，在idx结点中，tar位置更新为val
    private void lazy_update(int le, int ri, int val) { lazy_update(1, le, ri, val); }
    private void lazy_update(int root, int le, int ri, int val) {
        if (segs[root].le <= le && ri <= segs[root].ri) {
            segs[root].max += val;
            segs[root].tag += val;
        } else {
            push_down(root);

            int mid = (segs[root].le + segs[root].ri) >> 1;
            if (segs[root].le <= mid) lazy_update(root<<1, le, ri, val);
            if (mid < segs[root].ri) lazy_update(root<<1 | 1, le, ri, val);

            push_up(root);
        }
    }

    private void push_up(int root) {
        segs[root].max = Math.max(segs[root<<1].max, segs[root<<1 | 1].max);
    }

    private void push_down(int root) {
        if (segs[root].tag > 0) {
            segs[root<<1].tag += segs[root].tag;
            segs[root<<1 | 1].tag += segs[root].tag;

            segs[root<<1].max += segs[root].tag;
            segs[root<<1 | 1].max += segs[root].tag;
            segs[root].tag = 0;
        }
    }

    private static class Node {
        int le, ri, max, tag;
        public Node(int l, int r, int s) {
            le = l; ri = r; max = s; tag = 0;
        }
    }
}
```



**什么情况下，无法使用线段树？**
如果我们删除或者增加区间中的元素，那么区间的大小将发生变化，此时是无法使用线段树解决这种问题的。