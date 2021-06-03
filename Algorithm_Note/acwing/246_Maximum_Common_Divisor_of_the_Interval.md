# 246. 区间最大公约数

给定一个长度为 N 的数列 A，以及 M 条指令，每条指令可能是以下两种之一：

1. `C l r d`，表示把 A[l],A[l+1],…,A[r] 都加上 d。
2. `Q l r`，表示询问 A[l],A[l+1],…,A[r] 的最大公约数(GCD)。

对于每个询问，输出一个整数表示答案。

#### 输入格式

第一行两个整数 N,M。

第二行 N 个整数 A[i]。

接下来 M 行表示 M 条指令，每条指令的格式如题目描述所示。

#### 输出格式

对于每个询问，输出一个整数表示答案。

每个答案占一行。

#### 数据范围

N≤500000,M≤100000, 1≤A[i]≤10^18, |d|≤10^18

#### 输入样例：

```
5 5
1 3 5 7 9
Q 1 5
C 1 5 1
Q 1 5
C 3 3 6
Q 2 4
```

#### 输出样例：

```
1
2
4
```



## DP

```java
import java.io.*;

public class Main {
    int N, M, maxN = 500010;
    long[] arr = new long[maxN], diff = new long[maxN];
    Node[] tr = new Node[4*maxN];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]); M = Integer.parseInt(str[1]);
        str = br.readLine().split(" ");
        for (int i = 1; i <= N; i++) {
            arr[i] = Long.parseLong(str[i-1]);
            diff[i] = arr[i] - arr[i-1];
        }
        build(1, 1, N);

        while (M-- > 0) {
            str = br.readLine().split(" ");
            int le = Integer.parseInt(str[1]), ri = Integer.parseInt(str[2]);

            if (str[0].equals("C")) {
                long n = Long.parseLong(str[3]);
                update(1, le, n);
                if (ri < N) update(1, ri+1, -n);
            } else {
                long preSum = query_dif(1, 1, le);  // 这里 le 是固定的 1
                System.out.println(Math.abs(gcd(preSum, query_gcd(1, le+1, ri))));
            }
        }
    }

    void build(int u, int le, int ri) {
        if (le == ri) tr[u] = new Node(le, ri, diff[le], diff[le]);
        else {
            tr[u] = new Node(le, ri, 0, 0);
            int mid = (le+ri)>>1;
            build(u<<1, le, mid);
            build(u<<1|1, mid+1, ri);
            push_up(u);
        }
    }

    void update(int u, int idx, long val) {
        if (idx > tr[u].ri || idx < tr[u].le) return;
        if (tr[u].le == tr[u].ri) {
            tr[u].dif += val;
            tr[u].gcd += val;
        } else {
            update(u<<1, idx, val);
            update(u<<1|1, idx, val);
            push_up(u);
        }
    }

    long query_dif(int u, int st, int ed) {
        if (st > tr[u].ri || ed < tr[u].le) return 0;
        if (tr[u].le >= st && tr[u].ri <= ed) return tr[u].dif;

        return query_dif(u<<1, st, ed) + query_dif(u<<1|1, st, ed);
    }

    long query_gcd(int u, int st, int ed) {
        if (st > tr[u].ri || ed < tr[u].le) return 0;
        if (tr[u].le >= st && tr[u].ri <= ed) return tr[u].gcd;

        return gcd(query_gcd(u<<1, st, ed), query_gcd(u<<1|1, st, ed));
    }

    void push_up(int u) {
        tr[u].dif = tr[u<<1].dif + tr[u<<1|1].dif;
        tr[u].gcd = gcd(tr[u<<1].gcd, tr[u<<1|1].gcd);
    }

    long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a%b);
    }

    static class Node {
        int le, ri;
        long dif, gcd;
        public Node(int l, int r, long v, long d) {
            le = l; ri = r;
            dif = v; gcd = d;
        }
    }
}
```
