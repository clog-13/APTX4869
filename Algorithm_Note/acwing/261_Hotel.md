# 261. 旅馆

一家旅馆共有 N 个房间，这 N 个房间是连成一排的，标号为 1∼N。

现在有很多旅客以组为单位前来入住，每组旅客的数量可以用 Di 来表示。

旅店的业务分为两种，入住和退房：

1. 旅客入住时，第 i 组旅客需要根据他们的人数 Di，给他们安排 Di 个连续的房间，并且房间号要尽可能的小。如果房间不够，则无法安排。
2. 旅客退房时，第 i 组旅客的账单将包含两个参数 Xi 和 Di，你需要将房间号 Xi 到 Xi+Di−1 之间的房间全部清空。

现在你需要帮助该旅馆处理 M 单业务。

旅馆最初是空的。

#### 输入数据

第一行输入两个用空格隔开的整数 N 和 M。

接下来 M 行将描述 M 单业务：

“1 Di”表示这单业务为入住业务。

“2 Xi Di”表示这单业务为退房业务。

#### 输出数据

每个入住业务输出一个整数，表示要安排的房间序列中的第一个房间的号码。

如果没办法安排，则输出 0。

每个输出占一行。

#### 数据范围

1≤Di≤N≤50000, 1≤M<50000

#### 输入样例：

```
10 6
1 3
1 3
1 3
1 3
2 5 5
1 6
```

#### 输出样例：

```
1
4
7
0
5
```



## 线段树

```java
import java.io.*;
class Main {
    int N, M;
    Node[] tr = new Node[4*50010];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]); M = Integer.parseInt(str[1]);

        build(1, 1, N);

        while (M-- > 0) {
            str = br.readLine().split(" ");
            if (str[0].equals("1")) {
                int len = Integer.parseInt(str[1]);
                int idx = query(1, len);
                System.out.println(idx);
                if (idx > 0) lazy_update(1, idx, idx+len-1, 1);
            } else {
                int idx = Integer.parseInt(str[1]), len = Integer.parseInt(str[2]);
                lazy_update(1, idx, idx+len-1, 2);
            }
        }
    }

    void build(int root, int le, int ri) {
        if (le == ri) tr[root] = new Node(le, ri, 1);  // 做自己
        else {
            tr[root] = new Node(le, ri, 0);  // 人海茫茫，迷茫ing
            int mid = le+ri>>1;
            build(root<<1, le, mid);
            build(root<<1|1, mid+1, ri);
            push_up(root);  // 生活再糟也不要忘记push_up
        }
    }

    int query(int u, int len) {
        if (tr[u].d < len) return 0;

        push_down(u);
        if (tr[u<<1].d >= len) return query(u<<1, len);  // 左节点完全满足，递归
        if (tr[u<<1].rd + tr[u<<1|1].ld >= len) {  // 左边界+右节点左边部分 （左节点不完全满足）
            return tr[u<<1].ri - tr[u<<1].rd + 1;
        }
        if (tr[u<<1|1].d >= len) return query(u<<1|1, len);  // 右节点，递归
        return 0;
    }

    void lazy_update(int u, int start, int end, int val) {
        if (start > tr[u].ri || end < tr[u].le) return;
        if (start <= tr[u].le && tr[u].ri <= end) {
            if (val == 2) {
                tr[u].d = tr[u].ld = tr[u].rd = tr[u].ri-tr[u].le+1;
            } else {
                tr[u].d = tr[u].ld = tr[u].rd = 0;
            }
            tr[u].tag = val;
        } else {
            push_down(u);
            lazy_update(u << 1, start, end, val);
            lazy_update(u << 1 | 1, start, end, val);
            push_up(u);
        }
    }

    void push_up(int u) {
        Node LE = tr[u<<1], RI = tr[u<<1|1];
        tr[u].ld = LE.ld;  // 左节点左边必然时u的左边
        if (LE.d == LE.ri-LE.le+1) tr[u].ld = LE.d + RI.ld;  // 左节点完全覆盖
        tr[u].rd = RI.rd;  // 右节点右边必然时u的右边
        if (RI.d == RI.ri-RI.le+1) tr[u].rd = RI.d + LE.rd;  // 右节点完全覆盖
        tr[u].d = Math.max(LE.rd + RI.ld, Math.max(LE.d, RI.d));  // 取最长连续长度
    }

    void push_down(int u) {
        if (tr[u].tag == 0) return;
        Node LE = tr[u<<1], RI = tr[u<<1|1];
        if (tr[u].tag == 1) {  // 入住(push_down必然是整个区间需要修改)
            LE.d = LE.ld = LE.rd = RI.d = RI.ld = RI.rd = 0;
        } else if (tr[u].tag == 2) {  // 退房
            LE.d = LE.ld = LE.rd = LE.ri-LE.le+1;
            RI.d = RI.ld = RI.rd = RI.ri-RI.le+1;
        }
        LE.tag = RI.tag = tr[u].tag;
        tr[u].tag = 0;  // 完成使命，tag归零
        tr[u<<1] = LE; tr[u<<1|1] = RI;
    }

    static class Node {
        int le, ri, d, ld, rd, tag;
        public Node(int l, int r, int n) {
            le = l; ri = r; d = n; ld = n; rd = n;
        }
    }
}
```

