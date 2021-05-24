# 线段树（Segmen Tree）

![](pic\segment_tree01.png)

## 求和
```java
import java.io.*;
class Main {
    int N, M, maxN = 100010;
    long[] arr = new long[maxN];
    Node[] segs = new Node[4*maxN];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]); M = Integer.parseInt(str[1]);
        str = br.readLine().split(" ");
        for (int i = 1; i <= N; i++) arr[i] = Integer.parseInt(str[i-1]);
        build(1, 1, N);

        while (M-- > 0) {
            str = br.readLine().split(" ");
            int le = Integer.parseInt(str[1]), ri = Integer.parseInt(str[2]);
            if (str[0].equals("Q")) {
                System.out.println(query(1, le, ri));
            } else {
                int n = Integer.parseInt(str[3]);
                lazy_update(1, le, ri, n);
            }
        }
    }

    void build(int root, int le, int ri) {
        if (le == ri) segs[root] = new Node(le, ri, arr[le]);  // 做自己
        else {
            segs[root] = new Node(le, ri, 0);  // 人海茫茫，迷茫ing
            int mid = le+ri>>1;
            build(root<<1, le, mid);
            build(root<<1|1, mid+1, ri);
            push_up(root);  // 生活再糟也不要忘记push_up
        }
    }

    long query(int root, int start, int end) {
        if (start > segs[root].ri || end < segs[root].le) return 0;
        if (start <= segs[root].le && segs[root].ri <= end)
            return segs[root].sum;
        push_down(root);  // 自我审视，查缺补漏
        long res = 0;
        res += query(root<<1, start, end);
        res += query(root<<1|1, start, end);
        return res;
    }

    void update(int root, int idx, int val) {
        if (idx > segs[root].ri || idx < segs[root].le) return;  // 我要的你给不起
        if (segs[root].le == segs[root].ri) segs[root].sum += val;  // 目标节点
        else {
            update(root<<1, idx, val);    // 如果 需要update的节点在左子节点
            update(root<<1|1, idx, val);  // 否则 需要update的节点在右子节点
            push_up(root);    // 生活再糟也不要忘记push_up
        }
    }

    void lazy_update(int root, int start, int end, int val) {
        if (start > segs[root].ri || end < segs[root].le) return;  // 我要的你给不起
        if (start <= segs[root].le && segs[root].ri <= end) {  // 大款包养我
            segs[root].sum += (long) (segs[root].ri-segs[root].le+1) * val;  // 能力多大，责任多大
            segs[root].tag += val;
        } else {
            push_down(root);    // 自我审视，查缺补漏
            lazy_update(root<<1, start, end, val);
            lazy_update(root<<1|1, start, end, val);
            push_up(root);    // 生活再糟也不要忘记push_up
        }
    }

    void push_up(int root) {
        segs[root].sum = segs[root<<1].sum + segs[root<<1|1].sum;
    }

    void push_down(int root) {
        if (segs[root].tag != 0) {
            int mid = segs[root].le+segs[root].ri >> 1;
            segs[root<<1].sum += segs[root].tag * (mid - segs[root].le+1);  // 能力多大，责任多大
            segs[root<<1|1].sum += segs[root].tag * (segs[root].ri - mid);  // 能力多大，责任多大

            segs[root<<1].tag += segs[root].tag;  // 生命不息，传承不止
            segs[root<<1|1].tag += segs[root].tag;
            segs[root].tag = 0;
        }
    }

    static class Node {
        int le, ri;
        long sum, tag;
        public Node(int l, int r, long s) {
            le = l; ri = r; sum = s; tag = 0;
        }
    }
}
```

## 最值

```java
class Segment_tree {
    int maxN = 100010;
    int[] arr = new int[maxN];
    Node[] segs = new Node[4*maxN];

    public Segment_tree(int[] nums) {
        System.arraycopy(nums, 0, arr, 1, nums.length);
        build(1, 1, nums.length+1);
    }

    void build(int root, int le, int ri) {
        if (le == ri) {
            segs[root] = new Node(le, ri, arr[le]);
        } else {
            segs[root] = new Node(le, ri, 0);

            int mid = le+ri >> 1;
            build(root<<1, le, mid);
            build(root<<1|1, mid+1, ri);

            push_up(root);
        }
    }

    // 查询操作，start到end之间的和
    long query(int root, int start, int end) {
        if (start > segs[root].ri || end < segs[root].le) return 0;
        if (start <= segs[root].le && segs[root].ri <= end) return segs[root].max;

        push_down(root);    // 之前懒得更新的值向下传递
        long res = 0;
        res = Math.max(res, query(root<<1, start, end));
        res = Math.max(res, query((root<<1)|1, start, end));
        return res;
    }

    void update(int root, int idx, int val) {
        if (segs[root].le == segs[root].ri) segs[root].max = Math.max(segs[root].max, val);  // 目标节点
        else {
            int mid = (segs[root].le + segs[root].ri) >> 1;
            if (idx <= mid) update(root<<1, idx, val);  // 如果 需要update的节点在左子节点
            else update(root<<1|1, idx, val);   // 否则 需要update的节点在右子节点

            push_up(root);
        }
    }
    
    // 区间修改，start到end位置加上val
    void update_lazy(int root, int start, int end, int val) {
        if (segs[root].ri < start || segs[root].le > end) return;
        if (start <= segs[root].le && segs[root].ri <= end) {   // 如果 该节点包含范围全部需要update
            segs[root].max = Math.max(segs[root].max, val);
            segs[root].tag += val;
        } else {
            push_down(root);    // 向下传递
            update_lazy(root<<1, start, end, val);  // 如果左子节点在需要update的范围内
            update_lazy(root<<1|1, start, end, val);   // 如果右子节点在需要update的范围内

            push_up(root);
        }
    }

    void push_up(int root) {
        long sMax = Math.max(segs[root<<1].max, segs[root<<1|1].max);
        segs[root].max = Math.max(segs[root].max, sMax);
    }

    void push_down(int idx) {
        if (segs[idx].tag!=0) {	 // 如果update()是修改为，则修改为0时这里有bug
            segs[idx<<1].max = Math.max(segs[idx<<1].max, segs[idx].tag);
            segs[idx<<1|1].max = Math.max(segs[idx<<1|1].max, segs[idx].tag);

            segs[idx<<1].tag = Math.max(segs[idx<<1].tag, segs[idx].tag);
            segs[idx<<1|1].tag = Math.max(segs[idx<<1|1].tag, segs[idx].tag);

            segs[idx].tag = 0;
        }
    }

    static class Node {
        int le, ri, tag;
        long max;
        public Node(int l, int r, int s) {
            le = l; ri = r; max = s; tag = 0;
        }
    }
}
```



**什么情况下，无法使用线段树？**
如果我们删除或者增加区间中的元素，那么区间的大小将发生变化，此时是无法使用线段树解决这种问题的。



## C版本

```c
/**
 * node: 区间结点号    le:该node的区间左边界   ri: 该node的区间右边界     
 * pos: 查询区间的点  start: 查询区间的左边界 end: 查询区间的右边界 
 * */ 
  
/**
 * 线段树:求和或最值 
 * 区间更新,区间查询(lazy标记表示本节点的信息已经根据标记更新过了，但是本节点的子节点仍需要进行更新。)
 * lazy初始为0,区间加上k给该区间管理的结点的lazy加k,push_down给子节点加(ri-le+1)*k
 * 
 * lson 2*node
 * rson 2*node+1
 * [le,ri]
 * [le,mid] [mid+1,ri] 其中mid为(le+ri)/2
 * */ 

#define lson (node<<1)
#define rson ((node<<1) | 1)
#define mid ((le+ri) >> 1)
#define maxn 100010

int segTree[maxn*4];
int lazy[maxn];
int arr[maxn];

void build(int node, int le, int ri) {  // 建树 
    lazy[node] = 0;
    if (le == ri) { // le==end表示管理的是结点 
        // scanf("%d", &segTree[node]);    // 按照顺序输入结点，由于建树类似于树的先根遍历，所以建立的线段树的叶子结点从左到右的值就是输入的顺序 
        segTree[node] = arr[le];    // 用于任意顺序输入,先将输入存入a数组,下标从1开始，begin = ri = index 
        return; // 输入完成后要return，否则会继续访问左右孩子，可能越界
    }
    build(lson, le, mid);
    build(rson, mid+1, ri);
    pushUp(node);
}

void pushUp(int node) {
    segTree[node] = segTree[lson] + segTree[rson];
}

void pushDown(int node, int le, int ri) {   // pushDown自顶向下更新lazy数组和给结点加上lazy数组的值 
    if (!lazy[node]) return;    // lazy[node]为0直接return 
    segTree[lson] += (mid-le+1) * lazy[node];
    segTree[rson] += (ri-mid) * lazy[node]; 
    lazy[lson] += lazy[node]; 
    lazy[rson] += lazy[node];   // 给左右孩子传递lazy,是+=不是=，因为孩子节点可能被多次延迟标记又没有向下传递 
    lazy[node] = 0; // 把父节点的lazy置为0 
}

void update(int node, int le, int ri, int pos, int k) { // 单点更新 
    if (pos < le || pos > ri) return;   // 管理的区间不包含pos,直接return 
    if (le == ri) segTree[node] += k;
    else {
        update(lson, le, mid, pos, k);
        update(rson, mid+1, ri, pos, k);
        pushUp(node);
    }
}

void update(int node, int le, int ri, int start, int end, int k){  // 区间更新 
    if (start > ri || end < le) return;    // 结点和更新区间没有公共点 
    if (start <= le && end >= ri) {    // 更新区间包含结点 
        segTree[node] += (ri-le+1) * k;
        lazy[node] += k;
        return;
    }
    pushDown(node, le, ri);
    update(lson, le, mid, start, end, k);
    update(rson, mid+1, ri, start, end, k);
    pushUp(node);
}

int query(int node, int le, int ri, int start, int end) {  //区间查询 
    if (start > ri || end < le) return 0;  // 查询结点和区间没有公共点 
    if (start <= le && end >= ri) return segTree[node];    // 查询区间包含查询结点 
    
    pushDown(node, le, ri);
    int res = 0;
    res += query(lson, le, mid, start, end);
    res += query(rson, mid+1, ri, start, end);
    return res;
}
```