# 295. 清理班次



农民约翰正在指挥他的 N 头牛进行清理工作。

他将一天划分为了 T 个班次（1∼T）。

每头牛都只能在一天中的某一个时间段内进行不间断的工作。

你需要帮助约翰排列出一个合理的奶牛的清理班次，使得每个班次都有奶牛在进行清理，而且动用的奶牛数量可以尽可能的少。

#### 输入格式

第 1 行：两个空格隔开的整数 N 和 T。

第 2..N+1 行：第 i+1 行包含两个整数，分别表示第 i 头牛可以进行工作的开始时间和结束时间。

#### 输出格式

输出一个整数，表示在每个班次都有奶牛清理的情况下，所需的奶牛最小数量。

如果无法做到每个班次都有奶牛清理，则输出 −1。

#### 数据范围

1≤N≤25000, 1≤T≤10^6^

#### 输入样例：

```
3 10
1 7
3 6
6 10
```

#### 输出样例：

```
2
```



## 贪心

```java
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        int N = Integer.parseInt(str[0]), T = Integer.parseInt(str[1]);
        int[] nxt = new int[T+1];
        for (int i = 0; i < N; i++) {
            str = br.readLine().split(" ");
            int le = Integer.parseInt(str[0]), ri = Integer.parseInt(str[1]);
            nxt[le] = Math.max(nxt[le], ri);  // nxt[le]:覆盖到ri的最长区间
        }
        for (int i = 1; i <= T; i++) {  // 一个区间内部点指向最大ri边界
            nxt[i] = Math.max(nxt[i], nxt[i-1]);
        }
        int p = 1, res = 0;
        while (p <= T && p <= nxt[p]) {
            p = nxt[p]+1;
            res++;
        }
        if (p <= T) System.out.println(-1);
        else System.out.println(res);
    }
}
```





## 线段树

```java
import java.io.*;
import java.util.*;

public class Main {
    int INF = 0x3f3f3f3f, maxN = 1000010;
    Pair[] arr;
    Node[] tr = new Node[4*maxN];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        int N = Integer.parseInt(str[0]), T = Integer.parseInt(str[1]);
        arr = new Pair[N];
        for (int i = 0; i < N; i++) {
            str = br.readLine().split(" ");
            int le = Integer.parseInt(str[0]), ri = Integer.parseInt(str[1]);
            arr[i] = new Pair(le, ri);
        }
        Arrays.sort(arr, (a, b) -> (a.ri - b.ri));  // ri升序

        build(1, 0, T);
        update(1, 0, 0);  // f[0]:至少0之前位置全部覆盖最小花费
        for (int i = 0; i < N; i++) {
            int le = arr[i].le, ri = arr[i].ri;
            int val = query(1, le-1, ri-1) + 1;  // f[le-1]~f[ri-1]:区间最小值
            update(1, ri, val);
        }

        int res = query(1, T, T);  // 单点查询
        if (res == INF) System.out.println(-1);
        else System.out.println(res);
    }

    void build(int u, int le, int ri) {
        if (le == ri) tr[u] = new Node(le, ri, INF);  // 做自己
        else {
            tr[u] = new Node(le, ri, INF);  // 人海茫茫，迷茫ing
            int mid = le+ri>>1;
            build(u<<1, le, mid);
            build(u<<1|1, mid+1, ri);
            push_up(u);  // 生活再糟也不要忘记push_up
        }
    }

    int query(int u, int start, int end) {
        if (start > tr[u].ri || end < tr[u].le) return INF;
        if (start <= tr[u].le && tr[u].ri <= end)
            return tr[u].val;
        int res = INF;
        res = Math.min(res, query(u<<1, start, end));
        res = Math.min(res, query(u<<1|1, start, end));
        return res;
    }

    void update(int u, int idx, int val) {
        if (idx > tr[u].ri || idx < tr[u].le) return;  // 我要的你给不起
        if (tr[u].le == tr[u].ri) {
            tr[u].val = Math.min(tr[u].val, val);  // 目标节点
        } else {
            update(u<<1, idx, val);    // 如果 需要update的节点在左子节点
            update(u<<1|1, idx, val);  // 否则 需要update的节点在右子节点
            push_up(u);    // 生活再糟也不要忘记push_up
        }
    }

    void push_up(int u) {
        tr[u].val = Math.min(tr[u<<1].val, tr[u<<1|1].val);
    }

    static class Node {
        int le, ri, val, tag;
        public Node(int l, int r, int s) {
            le = l; ri = r; val = s; tag = 0;
        }
    }

    static class Pair {
        int le, ri;

        public Pair(int l, int r) {
            le = l; ri = r;
        }
    }
}
```

