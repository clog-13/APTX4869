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

N≤500000, M≤100000, −1000≤A[i]≤1000

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
    int N, M, maxN = 500010, INF = 0x3f3f3f3f;
    int[] arr = new int[maxN];
    Node[] tr = new Node[4*maxN];

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
        if (le==ri) tr[u] = new Node(le, ri, arr[le]);
        else {
            tr[u] = new Node(le, ri, 0);
            int mid = (le+ri)>>1;
            build(u<<1, le, mid);
            build(u<<1|1, mid+1, ri);
            push_up(u);
        }
    }

    void update(int u, int idx, int val) {
        if (idx > tr[u].ri || idx < tr[u].le) return;
        if (tr[u].le == tr[u].ri) tr[u].sum = tr[u].lx = tr[u].rx = tr[u].mx = val;
        else {
            update(u<<1, idx, val);    // 如果 需要update的节点在左子节点
            update(u<<1|1, idx, val);  // 否则 需要update的节点在右子节点
            push_up(u);
        }
    }

    Node query(int u, int start, int end) {
        if (start <= tr[u].le && tr[u].ri <= end) return tr[u];

        Node res = new Node(0, 0, 0);
        Node LE = new Node(0, 0, -INF), RI = new Node(0, 0, -INF);

        int mid = (tr[u].le + tr[u].ri) >> 1;
        if (start <= mid) LE = query(u<<1, start, end);
        if (mid < end) RI = query(u<<1|1, start, end);

        res.lx = Math.max(LE.lx, LE.sum+RI.lx);
        res.rx = Math.max(RI.rx, RI.sum+LE.rx);
        res.mx = Math.max(Math.max(LE.mx, RI.mx), LE.rx+RI.lx);  // 区间内的值
        res.sum = Math.max(LE.sum, 0) + Math.max(RI.sum, 0);  // 区间内全选的情况
        return res;
    }

    void push_up(int u) {
        Node LE = tr[u<<1], RI = tr[u<<1|1];
        tr[u].sum = LE.sum + RI.sum;
        tr[u].lx = Math.max(LE.lx, LE.sum + RI.lx);
        tr[u].rx = Math.max(RI.rx, RI.sum + LE.rx);
        tr[u].mx = Math.max(Math.max(LE.mx, RI.mx), LE.rx + RI.lx);
    }

    static class Node {
        int le, ri, sum, lx, rx, mx;
        public Node(int l, int r, int n) {
            le = l; ri = r;
            sum = n; lx = n; rx = n; mx = n;
        }
    }
}
```

