# 248. 窗内的星星

在一个天空中有很多星星（看作平面直角坐标系），已知每颗星星的坐标和亮度（都是整数）。

求用宽为 W、高为 H 的矩形窗口（W,H 为正整数）能圈住的星星的亮度总和最大是多少。（矩形边界上的星星不算）

#### 输入格式

输入包含多组测试用例。

每个用例的第一行包含 3 个整数：n，W，H，表示星星的数量，矩形窗口的宽和高。

然后是 n 行，每行有 3 个整数：x，y，c，表示每个星星的位置 (x，y) 和亮度。

没有两颗星星在同一点上。

#### 输出格式

每个测试用例输出一个亮度总和最大值。

每个结果占一行。

#### 数据范围

1≤n≤10000, 1≤W,H≤1000000, 0≤x,y<2^31^

#### 输入样例：

```
3 5 4
1 2 3
2 3 2
6 3 1
3 5 4
1 2 3
2 3 2
5 3 1
```

#### 输出样例：

```
5
6
```



## 线段树

```java
import java.util.*;

public class Main {
    int N, W, H, maxN = 20005;
    List<Long> list; List<Node> nodes;
    Seg[] tr = new Seg[maxN << 2];

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            N = sc.nextInt(); W = sc.nextInt(); H = sc.nextInt();
            nodes = new ArrayList<>(); list = new ArrayList<>();
            for (int i = 0; i < N; i++) {  // 读入数据
                long x = sc.nextLong(), y = sc.nextLong(), z = sc.nextLong();
                list.add(y); list.add(y+H-1);  // 离散化 y下标
                nodes.add(new Node(x  , y, y+H-1, z,  1));
                nodes.add(new Node(x+W, y, y+H-1, z, -1));
            }
            list = new ArrayList<>(new TreeSet<>(list));  // 去重
            nodes.sort((Node a, Node b) -> {  // x坐标排序
                if (a.x != b.x) return Long.compare(a.x, b.x);
                else return Long.compare(a.k, b.k);
            });

            build(1, 1, list.size());

            long res = 0;
            for (Node n: nodes) {
                update(1, getIdx(n.y1), getIdx(n.y2), n.z * n.k);
                res = Math.max(res, tr[1].sum);
            }
            System.out.println(res);
        }
    }

    void build(int u, long le, long ri) {
        tr[u] = new Seg(le, ri);
        if (le >= ri) return;
        long mid = le + ri >> 1;
        build(u<<1, le, mid);
        build(u<<1|1, mid+1, ri);
    }

    void update(int u, long st, long ed, long val) {
        if (tr[u].ri < st || tr[u].le > ed) return;  // start和end始终相差 H
        if (st <= tr[u].le && tr[u].ri <= ed) {  // 当前节点 区间长度小于 H，且在s-e范围内
            tr[u].flag += val;
            tr[u].sum += val;
        } else {
            pushDown(u);
            update(u<<1, st, ed, val);
            update(u<<1|1, st, ed, val);
            // 当前节点 区间长度大于 H 或 不在s-e范围内
            tr[u].sum = Math.max(tr[u<<1].sum, tr[u<<1|1].sum);
        }
    }

    int getIdx(long tar) {
        int le = 0, ri = list.size();
        while (le < ri) {
            int mid = le+ri >> 1;
            if (list.get(mid) < tar) le = mid+1;
            else ri = mid;
        }
        return ri + 1;
    }

    void pushDown(int u) {
        if (tr[u].flag == 0) return;

        long t = tr[u].flag;
        tr[u<<1].sum += t;
        tr[u<<1|1].sum += t;

        tr[u<<1].flag += t;
        tr[u<<1|1].flag += t;
        tr[u].flag = 0;
    }

    static class Node {
        long x, y1, y2, z;
        int k;
        public Node(long xx, long y1, long y2, long z, int k) {
            x = xx; this.y1 = y1; this.y2 = y2; this.z = z; this.k = k;
        }
    }

    static class Seg {
        long le, ri, sum, flag;
        public Seg(long l, long r) {
            le = l; ri = r;
        }
    }
}
```

