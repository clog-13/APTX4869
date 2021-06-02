# 平衡树

```java
import java.util.*;

class Main{
    int maxN = 10000010, INF = 0x3f3f3f3f;
    Node root = null;

    public static void main(String[] args){
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        build();
        while (T-- > 0) {
            int opt = sc.nextInt(), x = sc.nextInt();
            if (opt==1) root = insert(root, x);  // 插入数值 x
            if (opt==2) root = remove(root, x);  // 删除数值 x(若有多个相同的数，只删除一个)
			// 查询数值 x 的排名(若有多个相同的数，输出最小的排名)
            if (opt==3) System.out.println(getRangByKey(root, x));  
            if (opt==4) System.out.println(getKeyByRang(root, x+1));  // 查询排名为 x 的数值
            if (opt==5) System.out.println(getPre(root, x));  // 求数值 x 的前驱(前驱定义为小于 x 的最大的数)
            if (opt==6) System.out.println(getNext(root, x));  // 求数值 x 的后继(后继定义为大于 x 的最小的数)
        }
    }

    // 插入数值 x
    Node insert(Node u, int x) {
        if (u == null) return new Node(x, getRand());
        if (u.val == x) {
            u.cnt++;
        } else if (x < u.val) {
            u.le = insert(u.le, x);
            if (u.le.rand > u.rand) u = riRotate(u);  // 左大右旋
        } else if (x > u.val) {
            u.ri = insert(u.ri, x);
            if (u.ri.rand > u.rand) u = leRotate(u);  // 右大左旋
        }
        update(u);
        return u;
    }

    // 删除数值 x(若有多个相同的数，只删除一个)
    Node remove(Node u, int x) {
        if (u == null) return null;
        if (x == u.val) {
            if (u.cnt > 1) u.cnt--;
            else {
                if (u.le == null && u.ri == null) {  // 唯一删除代码
                    return null;
                } else if (u.ri == null) {  // 右无右旋
                    u = riRotate(u);  // 可能还会进行多次旋转
                    u.ri = remove(u.ri, x);
                } else {
                    u = leRotate(u);  // 可能还会进行多次旋转
                    u.le = remove(u.le, x);
                }
            }
        } else if (x < u.val) {
            u.le = remove(u.le, x);
        } else if (x > u.val) {
            u.ri = remove(u.ri, x);
        }

        update(u);
        return u;
    }

    // 查询数值 x 的排名(若有多个相同的数，输出最小的排名)
    int getRangByKey(Node u, int x) {
        if (u == null) return 0;

        int lsize = u.le==null ? 0 : u.le.size;
        if (x < u.val) return getRangByKey(u.le, x);
        if (x > u.val) return lsize + u.cnt + getRangByKey(u.ri, x);
        return lsize;
    }

    // 查询排名为 rank 的数值
    int getKeyByRang(Node u, int rank) {
        if (u == null) return INF;
        int lsize = u.le==null ? 0 : u.le.size;

        if (lsize >= rank) return getKeyByRang(u.le, rank);
        if (lsize+u.cnt >= rank) return u.val;  // l<r && r<=l+c
        return getKeyByRang(u.ri, rank - lsize - u.cnt);
    }

    // 求数值 x 的前驱数值(前驱定义为小于 x 的最大值)
    int getPre(Node u, int x) {
        if (u == null) return -INF;
        if (u.val >= x) return getPre(u.le, x);
        else return Math.max(u.val, getPre(u.ri, x));
    }

    // 求数值 x 的后继数值(后继定义为大于 x 的最小值)
    int getNext(Node u, int x) {
        if (u == null) return INF;
        if (u.val <= x) return getNext(u.ri, x);
        else return Math.min(u.val, getNext(u.le, x));
    }

    Node riRotate(Node node) {
        Node tmp = node.le;
        node.le = tmp.ri;
        tmp.ri = node;

        update(tmp.ri);  // == pushup(node)
        update(tmp);
        return tmp;
    }

    Node leRotate(Node node) {
        Node tmp = node.ri;
        node.ri = tmp.le;
        tmp.le = node;

        update(tmp.le);  // == pushup(node)
        update(tmp);
        return tmp;
    }

    void update(Node u) {
        u.size = u.cnt;
        if (u.le != null) u.size += u.le.size;
        if (u.ri != null) u.size += u.ri.size;
    }

    void build() {
        root = new Node(-INF, getRand());
        root.ri = new Node(INF, getRand());
        update(root);
    }

    int getRand() {
        return new Random().nextInt(2*maxN);
    }

    static class Node {
        int val, rand, cnt, size;
        Node le, ri;

        public Node(int v, int r) {
            val = v; rand = r;
            cnt = size = 1;
        }
    }
}
```

