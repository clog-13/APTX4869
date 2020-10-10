import java.io.*;
import java.util.Arrays;

class Main_1226 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static int maxN = 10010, N;
    static Node[] data = new Node[maxN * 2];
    static Seg[] segs = new Seg[maxN * 4];

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        int idx = 0;
        for (int i = 0; i < N; i++) {
            String[] s = br.readLine().split(" ");
            int x1 = Integer.parseInt(s[0]);
            int y1 = Integer.parseInt(s[1]);
            int x2 = Integer.parseInt(s[2]);
            int y2 = Integer.parseInt(s[3]);

            data[idx++] = new Node(x1, y1, y2, 1);
            data[idx++] = new Node(x2, y1, y2, -1);
        }
        Arrays.sort(data, 0, idx);

        build(1, 0, 10000);
        int res = 0;
        for (int i = 0; i < idx; i++) {
            if (i > 0) res += segs[1].len * (data[i].x - data[i-1].x);
            update(1, data[i].y1, data[i].y2 - 1, data[i].k);
        }

        bw.write(res + "\n");
        bw.close();
    }

    public static void build(int idx, int le, int ri) {
        segs[idx] = new Seg(le, ri);
        if (le == ri) return;
        int mid = le + ri >> 1;
        build(idx << 1, le, mid);
        build(idx << 1 | 1, mid + 1, ri);
    }

    public static void update(int idx, int le, int ri, int k) {
        if (segs[idx].le >= le && segs[idx].ri <= ri)
            segs[idx].cnt += k;
        else {
            int mid = segs[idx].le + segs[idx].ri >> 1;
            if (le <= mid) update(idx << 1, le, ri, k);
            if (ri > mid) update(idx<<1| 1, le, ri, k);
        }
        pushup(idx);
    }

    public static void pushup(int x) {
        if (segs[x].cnt > 0) segs[x].len = segs[x].ri - segs[x].le + 1;
        else if (segs[x].le == segs[x].ri) segs[x].len = 0;
        else if (segs[x].cnt == 0) segs[x].len = segs[x << 1].len + segs[x << 1 | 1].len;
    }

    private static class Seg {
        int le, ri;
        int cnt;    // 当前区间内被覆盖的次数
        int len;    // 当前区间内被覆盖的长度

        public Seg(int l, int r) {
            le = l; ri = r;
        }
    }

    private static class Node implements Comparable<Node> {
        int x, y1, y2, k;

        public Node(int x, int y1, int y2, int k) {
            this.x = x;
            this.y1 = y1;
            this.y2 = y2;
            this.k = k;
        }

        @Override
        public int compareTo(Node Node) { // 按横坐标升序排列
            return this.x - Node.x;
        }
    }
}