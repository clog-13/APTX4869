# 245. 你能回答这些问题吗

给定长度为 N 的数列 A，以及 M 条指令，每条指令可能是以下两种之一：

1. `1 x y`，查询区间 \[x,y\] 中的**最大连续子段和**。
2. `2 x y`，把 A[x] 改成 y。

对于每个查询指令，输出一个整数表示答案。

#### 输入格式

第一行两个整数 N,M。

第二行 N 个整数 A[i]。

接下来 M 行每行 3 个整数 k,x,y，k=1 表示查询（此时如果 x>y，请交换 x,y），k=2 表示修改。

#### 输出格式

对于每个查询指令输出一个整数表示答案。

每个答案占一行。

#### 数据范围

N≤500000,M≤100000, −1000≤A[i]≤1000

#### 输入样例：

```
5 3
1 2 -3 4 5
1 2 3
2 2 -1
1 3 2
```

#### 输出样例：

```
2
-1
```



## DP

```java
import java.io.*;
public class Main {
    int N, M, maxN = 500005, INF = 0x3f3f3f3f;
    int[] arr = new int[maxN];
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
            int x = Integer.parseInt(str[1]), y = Integer.parseInt(str[2]);

            if (str[0].equals("1")) {
                if (x > y) {x = x^y; y = x^y; x = x^y;}
                System.out.println(query(1, x, y).mx);
            } else {
                update(1, x, y);
            }
        }
    }

    void build(int u, int le, int ri) {
        if (le==ri) segs[u] = new Node(le, ri, arr[le]);
        else {
            segs[u] = new Node(le, ri);

            int mid = (le+ri)>>1;
            build(u<<1, le, mid);
            build(u<<1|1, mid+1, ri);

            push_up(u);
        }
    }

    void update(int u, int idx, int val) {
        if (idx > segs[u].ri || idx < segs[u].le) return;
        if (segs[u].le == segs[u].ri) segs[u].sum = segs[u].lx = segs[u].rx = segs[u].mx = val;
        else {
            update(u<<1, idx, val);    // 如果 需要update的节点在左子节点
            update(u<<1|1, idx, val);  // 否则 需要update的节点在右子节点

            push_up(u);
        }
    }

    Node query(int u, int start, int end) {
        if (start <= segs[u].le && segs[u].ri <= end) return segs[u];

        Node res = new Node(0, 0), nl = new Node(0, 0, -INF), nr = new Node(0, 0, -INF);

        int mid = (segs[u].le + segs[u].ri) >> 1;
        if (start <= mid) nl = query(u<<1, start, end);
        if (mid < end) nr = query(u<<1|1, start, end);

        res.lx = Math.max(nl.lx, nl.sum+nr.lx);
        res.rx = Math.max(nr.rx, nr.sum+nl.rx);
        res.mx = Math.max(Math.max(nl.mx, nr.mx), nl.rx+nr.lx);
        res.sum = Math.max(nl.sum, 0) + Math.max(nr.sum, 0);
        return res;
    }

    void push_up(int u) {
        segs[u].sum = segs[u<<1].sum + segs[u<<1|1].sum;
        segs[u].lx = Math.max(segs[u<<1].lx, segs[u<<1].sum + segs[u<<1|1].lx);
        segs[u].rx = Math.max(segs[u<<1|1].rx, segs[u<<1|1].sum + segs[u<<1].rx);
        segs[u].mx = Math.max(Math.max(segs[u<<1].mx, segs[u<<1|1].mx), segs[u<<1].rx + segs[u<<1|1].lx);
    }

    static class Node {
        int le, ri, sum, lx, rx, mx;
        public Node(int l, int r) {
            le = l; ri = r;
        }
        public Node(int l, int r, int n) {
            le = l; ri = r;
            sum = n; lx = n; rx = n; mx = n;
        }
    }
}
```

