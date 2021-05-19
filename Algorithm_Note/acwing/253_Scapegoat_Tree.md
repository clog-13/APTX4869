# 253. 普通平衡树

您需要写一种数据结构（可参考题目标题），来维护一些数，其中需要提供以下操作：

1. 插入数值 x。
2. 删除数值 x(若有多个相同的数，应只删除一个)。
3. 查询数值 x 的排名(若有多个相同的数，应输出最小的排名)。
4. 查询排名为 x 的数值。
5. 求数值 x 的前驱(前驱定义为小于 x 的最大的数)。
6. 求数值 x 的后继(后继定义为大于 x 的最小的数)。



#### 输入格式

第一行为 n，表示操作的个数。

接下来 n 行每行有两个数 opt 和 x，opt 表示操作的序号(1≤opt≤6)。

#### 输出格式

对于操作 3,4,5,6 每行输出一个数，表示对应答案。

#### 数据范围

1≤n≤100000,所有数均在 −107 到 107 内。

#### 输入样例：

```
8
1 10
1 20
1 30
3 20
4 2
2 10
5 25
6 -1
```

#### 输出样例：

```
2
20
20
20
```



## Scapegoat Tree

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
            if (opt==2) root = remove(root, x);  // 删除数值 x(若有多个相同的数，应只删除一个)
            if (opt==3) System.out.println(getRangByKey(root, x) - 1);  // 查询数值 x 的排名(若有多个相同的数，应输出最小的排名)
            if (opt==4) System.out.println(getKeyByRang(root, x+1));  // 查询排名为 x 的数值
            if (opt==5) System.out.println(getPre(root, x));  // 求数值 x 的前驱(前驱定义为小于 x 的最大的数)
            if (opt==6) System.out.println(getNext(root, x));  // 求数值 x 的后继(后继定义为大于 x 的最小的数)
        }
    }

    // 插入数值 x
    Node insert(Node u, int x) {
        if (u == null) return new Node(x, getRand());
        if (u.val == x) u.cnt++;
        if (x < u.val) {
            u.le = insert(u.le, x);
            if (u.le.rand > u.rand) u = riRotate(u);  // 左大右旋
        } else if (x > u.val) {
            u.ri = insert(u.ri, x);
            if (u.ri.rand > u.rand) u = leRotate(u);  // 右大左旋
        }

        pushup(u);
        return u;
    }

    // 删除数值 x(若有多个相同的数，应只删除一个)
    Node remove(Node u, int x) {
        if (u == null) return null;

        if (x == u.val) {
            if (u.cnt > 1) u.cnt--;
            else {
                if (u.le == null && u.ri == null) {
                    return null;
                } else if (u.le == null) {  // 左无左旋
                    u = leRotate(u);
                    u.le = remove(u.le, x); // 左旋删左
                } else if (u.ri == null) {  // 右无右旋
                    u = riRotate(u);
                    u.ri = remove(u.ri, x); // 右旋删右
                } else {
                    if (u.le.val > u.ri.val) {  // 左大右旋
                        u = riRotate(u);
                        u.ri = remove(u.ri, x); // 左旋删左
                    } else {                    // 右大左旋
                        u = leRotate(u);
                        u.le = remove(u.le, x); // 右旋删右
                    }
                }
            }
        } else if (x < u.val) {
            u.le = remove(u.le, x);
        } else if (x > u.val) {
            u.ri = remove(u.ri, x);
        }

        pushup(u);
        return u;
    }

    // 查询数值 x 的排名(若有多个相同的数，应输出最小的排名)
    int getRangByKey(Node u, int x) {
        if (u == null) return 0;
        int lsize = u.le==null ? 0 : u.le.size;
        if (x < u.val) return getRangByKey(u.le, x);
        if (x > u.val) return lsize + u.cnt + getRangByKey(u.ri, x);
        return lsize + 1;
    }

    // 查询排名为 rank 的数值
    int getKeyByRang(Node u, int rank) {
        if (u == null) return INF;
        int lsize = u.le==null ? 0 : u.le.size;

        if (lsize >= rank) return getKeyByRang(u.le, rank);
        if (lsize+u.cnt >= rank) return u.val;
        return getKeyByRang(u.ri, rank - lsize - u.cnt);
    }

    // 求数值 x 的前驱(前驱定义为小于 x 的最大的数)
    int getPre(Node u, int x) {
        if (u == null) return -INF;
        if (u.val >= x) return getPre(u.le, x);
        else return Math.max(u.val, getPre(u.ri, x));
    }

    // 求数值 x 的后继(后继定义为大于 x 的最小的数)
    int getNext(Node u, int x) {
        if (u == null) return INF;
        if (u.val <= x) return getNext(u.ri, x);
        else return Math.min(u.val, getNext(u.le, x));
    }

    Node riRotate(Node node) {
        Node tmp = node.le;
        node.le = tmp.ri;
        tmp.ri = node;

        pushup(tmp.ri);  // == pushup(node)
        pushup(tmp);
        return tmp;
    }

    Node leRotate(Node node) {
        Node tmp = node.ri;
        node.ri = tmp.le;
        tmp.le = node;

        pushup(tmp.le);  // == pushup(node)
        pushup(tmp);
        return tmp;
    }

    void pushup(Node u) {
        int lsize = 0, rsize = 0;
        if (u.le != null) lsize = u.le.size;
        if (u.ri != null) rsize = u.ri.size;
        u.size = u.cnt + lsize + rsize;
    }

    void build() {
        root = new Node(-INF, getRand());
        root.ri = new Node(INF, getRand());
        pushup(root);
        if (root.rand < root.ri.rand) root = leRotate(root);
    }

    int getRand() {
        return new Random().nextInt(2*maxN);
    }

    static class Node{
        int val, rand, cnt, size;
        Node le, ri;

        public Node(int v, int r){
            val = v; rand = r;
            cnt = size = 1;
        }
    }
}
```

