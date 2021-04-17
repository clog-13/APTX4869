# 243. 一个简单的整数问题2

给定一个长度为 N 的数列 A，以及 M 条指令，每条指令可能是以下两种之一：

1. `C l r d`，表示把 A[l],A[l+1],…,A[r] 都加上 d。
2. `Q l r`，表示询问数列中第 l∼r 个数的和。

对于每个询问，输出一个整数表示答案。

#### 输入格式

第一行两个整数 N,M。

第二行 N 个整数 A[i]。

接下来 M 行表示 M 条指令，每条指令的格式如题目描述所示。

#### 输出格式

对于每个询问，输出一个整数表示答案。

每个答案占一行。

#### 数据范围

1≤N,M≤105, |d|≤10000, |A[i]|≤10^9^

#### 输入样例：

```
10 5
1 2 3 4 5 6 7 8 9 10
Q 4 4
Q 1 10
Q 2 4
C 3 6 3
Q 2 4
```

#### 输出样例：

```
4
55
9
15
```



## 线段树（MlogN）

```java
import java.io.*;
public class Main {
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
                update_lazy(1, le, ri, n);
            }
        }
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
        if (start <= segs[root].le && segs[root].ri <= end) return segs[root].sum;

        push_down(root);    // 之前懒得更新的值向下传递
        long res = 0;
        res += query(root<<1, start, end);
        res += query((root<<1)|1, start, end);
        return res;
    }

    // 区间修改，start到end位置加上val
    void update_lazy(int root, int start, int end, int val) {
        if (segs[root].ri < start || segs[root].le > end) return;
        if (start <= segs[root].le && segs[root].ri <= end) {   // 如果 该节点包含范围全部需要update
            segs[root].sum += (long) (segs[root].ri - segs[root].le + 1) * val;
            segs[root].tag += val;
        } else {
            push_down(root);    // 向下传递
            update_lazy(root<<1, start, end, val);  // 如果左子节点在需要update的范围内
            update_lazy(root<<1|1, start, end, val);   // 如果右子节点在需要update的范围内

            push_up(root);
        }
    }

    void push_up(int root) {
        segs[root].sum = segs[root<<1].sum + segs[root<<1|1].sum;
    }

    void push_down(int idx) {
        if (segs[idx].tag!=0) {	// 如果update()是修改为，则修改为0时这里有bug
            int mid = (segs[idx].le+segs[idx].ri) >> 1;
            segs[idx<<1].sum += (long) segs[idx].tag * (mid - segs[idx].le + 1);
            segs[idx<<1|1].sum += (long) segs[idx].tag * (segs[idx].ri - mid);

            segs[idx<<1].tag += segs[idx].tag;
            segs[idx<<1|1].tag += segs[idx].tag;

            segs[idx].tag = 0;
        }
    }

    static class Node {
        int le, ri, tag;
        long sum;
        public Node(int l, int r, long s) {
            le = l; ri = r; sum = s; tag = 0;
        }
    }
}
```



## 树状数组(NlogN)

```java
import java.io.*;
public class Main {
    int N, M, maxN = 100010;
    long[] arr = new long[maxN], tr1 = new long[maxN], tr2 = new long[maxN];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]); M = Integer.parseInt(str[1]);
        str = br.readLine().split(" ");
        for (int i = 1; i <= N; i ++) arr[i] = Integer.parseInt(str[i-1]);

        for (int i = 1; i <= N; i ++) {  // init
            long d = arr[i] - arr[i-1];
            update(tr1, i, d);
            update(tr2, i, d*i);
        }
        while (M-- > 0) {
            str = br.readLine().split(" ");
            String op = str[0];
            int le = Integer.parseInt(str[1]), ri = Integer.parseInt(str[2]);
            if (op.equals("C")) {
                int n = Integer.parseInt(str[3]);
                update(tr1, le, n); update(tr1, ri+1, -n);
                update(tr2, le, (long)le*n); update(tr2, ri+1, (long)(ri+1)*-n);
            } else {
                System.out.println(getPreSum(ri) - getPreSum(le-1));
            }
        }
    }

    long getPreSum(int idx) {
        return query(tr1, idx) * (idx + 1) - query(tr2, idx);
    }

    void update(long[] tr, int idx, long n) {
        for (int i = idx; i <= N; i += lowbit(i)) tr[i] += n;
    }

    long query(long[] tr, int idx) {
        long res = 0;
        for (int i = idx; i > 0; i -= lowbit(i)) res += tr[i];
        return res;
    }

    int lowbit(int x) {
        return x & -x;
    }
}
```

.