# 299. 裁剪序列

给定一个长度为 N 的序列 A，要求把该序列分成若干段，在满足“每段中所有数的和”不超过 M 的前提下，让“每段中所有数的最大值”之和最小。

试计算这个最小值。

#### 输入格式

第一行包含两个整数 N 和 M。

第二行包含 N 个整数，表示完整的序列 A。

#### 输出格式

输出一个整数，表示结果。

如果结果不存在，则输出 −1。

#### 数据范围

0≤N≤10^5, 0≤M≤1011, 序列A中的数非负，且不超过10^6

#### 输入样例：

```
8 17
2 2 2 8 1 8 2 1
```

#### 输出样例：

```
12
```



## 线段树

```java
import java.util.*;

public class Main {
    int maxN = 100010, INF = 0x3f3f3f3f;
    int[] q = new int[maxN];
    long[] arr = new long[maxN], f = new long[maxN];
    Node[] tr = new Node[4*maxN];


    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); long M =sc.nextLong();
        for (int i = 1; i <= N; i++) {
            arr[i] = sc.nextLong();
            if (arr[i] > M) {
                System.out.println("-1");
                return;
            }
        }
        build(1, 0, N);
        long sum = 0;
        int pos = 1, hh = 0;
        // 线段树的每个叶子节点记录答案,即l==r的一个叶子结点表示在当前i的情况之下
        // f[i]+max(a[i+1], a[j])的值,然后用线段树维护最小值
        // 用pos记录对于i来说最小的j使得a[j]+…+a[i]<=m
        for (int i = 1; i <= N; i++) {
            sum += arr[i];
            while (sum > M) sum -= arr[pos++];

            // q[hh]:前一个最大arr[i]的i
            lazy_update(1, q[hh], q[hh], arr[i]);  // tr[q[hh]] += arr[i]
            while (hh>0 && arr[i] > arr[q[hh]]) {
                lazy_update(1, q[hh-1], q[hh]-1, arr[i]-arr[q[hh]]);  // 更新前一个管理的区间
                hh--;
            }

            q[++hh] = i;

            f[i] = query(1, pos-1, i-1);  // pos: 当前区间左端点
            lazy_update(1, i, i, f[i]);  // tr[i] += f[i]
        }
        System.out.println(f[N]);
    }

    void build(int root, int le, int ri) {
        if (le == ri) tr[root] = new Node(le, ri, 0);  // 做自己
        else {
            tr[root] = new Node(le, ri, 0);  // 人海茫茫，迷茫ing
            int mid = le+ri>>1;
            build(root<<1, le, mid);
            build(root<<1|1, mid+1, ri);
            push_up(root);  // 生活再糟也不要忘记push_up
        }
    }

    long query(int root, int start, int end) {
        if (start > tr[root].ri || end < tr[root].le) return INF;
        if (start <= tr[root].le && tr[root].ri <= end)
            return tr[root].sum;
        push_down(root);  // 自我审视，查缺补漏
        long res = INF;
        res = Math.min(res, query(root<<1, start, end));
        res = Math.min(res, query(root<<1|1, start, end));
        return res;
    }

    void lazy_update(int root, int start, int end, long val) {
        if (start > tr[root].ri || end < tr[root].le) return;  // 我要的你给不起
        if (start <= tr[root].le && tr[root].ri <= end) {  // 大款包养我
            tr[root].sum += val;
            tr[root].tag += val;
        } else {
            push_down(root);    // 自我审视，查缺补漏
            lazy_update(root<<1, start, end, val);
            lazy_update(root<<1|1, start, end, val);
            push_up(root);    // 生活再糟也不要忘记push_up
        }
    }

    void push_up(int root) {
        tr[root].sum = Math.min(tr[root<<1].sum, tr[root<<1|1].sum);
    }

    void push_down(int root) {
        if (tr[root].tag != 0) {
            tr[root<<1].sum += tr[root].tag;
            tr[root<<1|1].sum += tr[root].tag;

            tr[root<<1].tag += tr[root].tag;
            tr[root<<1|1].tag += tr[root].tag;
            tr[root].tag = 0;
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



## 单调队列DP

```java
import java.util.*;

public class Main {
    int maxN = 100010;
    int[] q = new int[maxN];
    long[] arr = new long[maxN], f = new long[maxN];
    PriorityQueue<Long> heap = new PriorityQueue<>();

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); long M =sc.nextLong();
        for (int i = 1; i <= N; i++) {
            arr[i] = sc.nextLong();
            if (arr[i] > M) {
                System.out.println("-1");
                return;
            }
        }

        int hh = 0, tt = 0;
        long sum = 0;
        for (int i = 1, j = 0; i <= N; i++) {
            sum += arr[i];
            while (sum > M) sum -= arr[++j];
            while (hh <= tt && q[hh] <= j) {  // 删除越界情况
                if (hh < tt) heap.remove(f[q[hh]] + arr[q[hh+1]]);
                hh++;
            }

            int tail = tt;
            while (hh <= tt && arr[i] >= arr[q[tt]]) {  // 最大栈
                if (tt != tail) heap.remove(f[q[tt]] + arr[q[tt+1]]);
                tt--;
            }
            if (hh <= tt && tt != tail) heap.remove(f[q[tt]] + arr[q[tt+1]]);

            q[++tt] = i;
            if (hh < tt) heap.add(f[q[tt-1]] + arr[q[tt]]);

            f[i] = f[j] + arr[q[hh]];
            if (!heap.isEmpty()) f[i] = Math.min(f[i], heap.peek());
        }
        System.out.println(f[N]);
    }
}
```

