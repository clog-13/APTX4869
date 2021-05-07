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
                if (x > y) {
                    x = x^y; y = x^y; x = x^y;
                }
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

        Node nl = new Node(0, 0, -INF), nr = new Node(0, 0, -INF), res = new Node(0, 0);
        res.sum = 0;

        int mid = (segs[u].le + segs[u].ri) >> 1;
        if (start <= mid) {
            nl = query(u<<1, start, end);
            res.sum += nl.sum;
        }
        if (mid < end) {
            nr = query(u<<1|1, start, end);
            res.sum = nr.sum;
        }

        res.mx = Math.max(Math.max(nl.mx, nr.mx), nl.rx+nr.lx);
        res.lx = Math.max(nl.lx, nl.sum+nr.lx);
        res.rx = Math.max(nr.rx, nr.sum+nl.rx);

        if (start > mid) res.lx = Math.max(res.lx, nr.lx);
        if (end <= mid) res.rx = Math.max(res.rx, nl.rx);
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

