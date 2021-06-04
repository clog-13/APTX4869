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
            // 查询排名为 x 的数值
            if (opt==4) System.out.println(getKeyByRang(root, x+1));
            // 求数值 x 的前驱(前驱定义为小于 x 的最大的数)
            if (opt==5) System.out.println(getPre(root, x));  
            // 求数值 x 的后继(后继定义为大于 x 的最小的数)
            if (opt==6) System.out.println(getNext(root, x));  
        }
    }

    // 插入数值 x
    Node insert(Node u, int x) {
        if (u == null) return new Node(x, getRand());
        
        if (u.val == x) {
            u.cnt++;
        } else if (x < u.val) {
            u.le = insert(u.le, x);
            if (u.le.rand > u.rand) u = leRotate(u);  // rand左大左旋
        } else if (x > u.val) {
            u.ri = insert(u.ri, x);
            if (u.ri.rand > u.rand) u = riRotate(u);  // rand右大右旋
        }
        
        push_up(u);  // !!!
        return u;
    }

    // 删除数值 x(若有多个相同的数，只删除一个)
    Node remove(Node u, int x) {
        if (u == null) return null;
        
        if (x == u.val) {
            if (u.cnt > 1) u.cnt--;
            else {  // x==u.val && u.cnt==1
                if (u.le == null && u.ri == null) {  // !!! 唯一删除代码
                    return null;
                } else if (u.ri == null) {  // 右无左旋 （优先判断右边，保证结构正确性）
                    u = leRotate(u);  // 可能还会进行多次旋转
                    u.ri = remove(u.ri, x);  // ri == u
                } else {  // ri节点比le节点，要把ri节点摇上来
                    u = riRotate(u);  // 可能还会进行多次旋转
                    u.le = remove(u.le, x);  // le == u
                }
            }
        } else if (x < u.val) {
            u.le = remove(u.le, x);
        } else if (x > u.val) {
            u.ri = remove(u.ri, x);
        }

        push_up(u);  // !!!
        return u;
    }

    // 查询数值 x 的排名(若有多个相同的数，输出最小的排名)
    int getRangByKey(Node u, int x) {
        if (u == null) return 0;

        int lsize = u.le==null ? 0 : u.le.size;
        if (x < u.val) return getRangByKey(u.le, x);
        if (x == u.val) return lsize;
        return lsize + u.cnt + getRangByKey(u.ri, x);
    }

    // 查询排名为 rank 的数值
    int getKeyByRang(Node u, int rank) {
        if (u == null) return INF;
        int lsize = u.le==null ? 0 : u.le.size;

        if (lsize >= rank) return getKeyByRang(u.le, rank);
        if (lsize+u.cnt >= rank) return u.val;  // l < r <= l+c
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

    Node leRotate(Node node) {
        Node tmp = node.le;
        node.le = tmp.ri;
        tmp.ri = node;

        push_up(tmp.ri);  // update(node)，必须先更新
        push_up(tmp);
        return tmp;   // !!!
    }

    Node riRotate(Node node) {
        Node tmp = node.ri;
        node.ri = tmp.le;
        tmp.le = node;

        push_up(tmp.le);  // update(node)，必须先更新
        push_up(tmp);
        return tmp;  // !!!
    }

    void push_up(Node u) {
        u.size = u.cnt;  // size：包括子节点的权重， cnt：自身的权重
        if (u.le != null) u.size += u.le.size;
        if (u.ri != null) u.size += u.ri.size;
    }

    void build() {
        root = new Node(INF, getRand());
        root.le = new Node(-INF, getRand());
        push_up(root);
    }

    int getRand() {
        return new Random().nextInt(2*maxN);
    }

    static class Node {
        int val, rand, cnt, size;
        Node le, ri;

        public Node(int v, int r) {
            val = v; rand = r;
            cnt = size = 1;  // !!! 创建时默认为 1 
        }
    }
}
```

