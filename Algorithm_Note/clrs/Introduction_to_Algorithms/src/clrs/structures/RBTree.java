package clrs.structures;

import sun.security.x509.RDN;

public class RBTree<T extends Comparable<T>> {
    private RBNode<T> root;
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public class RBNode<T extends Comparable<T>> {
        boolean color;
        T key;
        RBNode<T> left;
        RBNode<T> right;
        RBNode<T> parent;

        public RBNode(T key, boolean color, RBNode<T> parent, RBNode<T> left, RBNode<T> right) {
            this.key = key;
            this.color = color;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public T getKey() { return key; }

        public String toString() { return "" + key + (this.color == RED ? "R" : "B"); }
    }

    public RBTree() {
        root = null;
    }

    private void leftRotate(RBNode<T> node) {
        RBNode<T> son = node.left;
        node.left = son.right;
        if(son.right != null)
            son.right.parent = node.left;

        son.parent = node.parent;
        if(node.parent == null)
            this.root = son;
        else if(node == node.parent.left)
            son.parent.left = son;
        else
            son.parent.right = son;

        son.right = node;
        node.parent = son;
    }

    private void rightRotate(RBNode<T> node) {
        RBNode<T> son = node.right;
        node.right = son.left;
        if(son.left != null)
            son.left.parent = node;

        son.parent = node.parent;
        if(node.parent == null)
            this.root = son;
        else if(node == node.parent.right)
            son.parent.right = son;
        else
            son.parent.left = son;

        son.left = node;
        node.parent = son;
    }

    public void insert(T key) {
        RBNode<T> node = new RBNode<T>(key, RED, null, null, null);
        if(node != null)
            insert(node);
    }

    private void insert(RBNode<T> node) {
        RBNode<T> tPar = null;
        RBNode<T> tmp = this.root;
        while(tmp != null) {
            tPar = tmp;
            int cmp = node.key.compareTo(tmp.key);
            if(cmp < 0) {
                tmp = tmp.left;
            }else {
                tmp = tmp.right;
            }
        }

        node.parent = tPar;
        if(tPar == null)
            this.root = node;
        else if(node.key.compareTo(tPar.key) < 0)
            tPar.left = node;
        else
            tPar.right = node;

        // new的时候已经完成了node颜色,子树的初始化
        fixUp(node);
    }

    private void fixUp(RBNode<T> node) {
        RBNode<T> par, grand;
        while ((par = node.parent) != null && isRed(par)) {
            grand = node.parent.parent;

            if(par == grand.left) {
                RBNode<T> uncle = grand.right;

                // case1: 叔叔节点也是红色
                if(uncle != null && isRed(uncle)) {
                    uncle.color = BLACK;
                    par.color = BLACK;
                    grand.color = RED;

                    node = grand;
                    continue;
                }

                // case2: 叔叔节点是黑色，且当前节点是右子节点 (这里可以 先判断左也可以先判断右, 不过最好先判断一个"相反"的方向)
                if(node == par.right) {
                    node = par;
                    leftRotate(node);
                }

                // case3: 叔叔节点是黑色，且当前节点是左子节点
                par.color = BLACK;
                grand.color = RED;
                rightRotate(grand);
            }else {
                RBNode<T> uncle = grand.left;
                if(uncle != null && isRed(uncle)) {
                    uncle.color = BLACK;
                    par.color = BLACK;
                    grand.color = RED;

                    node = grand;
                    continue;
                }

                if(node == par.left) {
                    node = par;
                    rightRotate(node);
                }

                par.color = BLACK;
                grand.color = RED;
                leftRotate(grand);
            }
        }

        root.color = BLACK;
    }

    private void replace(RBNode<T> old, RBNode<T> key) {
        RBNode<T> replace = search(root, old.key);
        RBNode<T> node = search(root, key.key);

        if(replace.parent == null)
            root = node;
        else if(replace == replace.right.left)
            replace.parent.left = node;
        else
            replace.parent.right = node;
        node.parent = replace.parent;

    }

    public void remove(T key) {
        RBNode<T> node;
        if((node = search(root, key)) != null)
            remove(node);
    }

    private void remove(RBNode<T> node) {
        RBNode<T> child = null, parent = null;
        boolean color = false;

        //1. 被删除的节点“左右子节点都不为空”的情况

        if((node.left != null) && (node.right != null)) {
            //  1). 获取“后继节点”
            RBNode<T> replace = node;
            replace = replace.right;
            while(replace.left != null)
                replace = replace.left;

            //  2). 处理“后继节点”和“被删除节点 的父节点”之间的关系
            if(node.parent == null)
                this.root = replace;
            else {
                if(node == node.parent.left)
                    node.parent.left = replace;
                else
                    node.parent.right = replace;
            }

            //  3). 处理“后继节点 的子节点”和“被删除节点 的子节点”之间的关系
            child = replace.right;  // 后继节点肯定不存在左子节点
            parent = replace.parent;
            color = replace.color;  // 保存后继节点的颜色
            if(parent == node)      // 后继节点是被删除节点的子节点
                parent = replace;
            else {
                if(child != null)
                    child.parent = parent;
                parent.left = child;

                replace.right = node.right;
                node.right.parent = replace;
            }
            replace.parent = node.parent;
            replace.color = node.color; //保持原来位置的颜色

            replace.left = node.left;
            node.left.parent = replace;

        }
        if(color == BLACK) { //4. 如果移走的后继节点颜色是黑色，重新修整红黑树
            removeFixUp(child, parent);//将后继节点的child和parent传进去
        }
    }

    //node表示待修正的节点，即后继节点的子节点（因为后继节点被挪到删除节点的位置去了）
    private void removeFixUp(RBNode<T> node, RBNode<T> parent) {
        RBNode<T> other;

        while((node == null || isBlack(node)) && (node != this.root)) {
            if(parent.left == node) { //node是左子节点，下面else与这里的刚好相反
                other = parent.right; //node的兄弟节点
                if(isRed(other)) { //case1: node的兄弟节点other是红色的
                    other.parent.color = BLACK;
                    parent.color = RED;
                    leftRotate(parent);
                    other = parent.right;
                }

                //case2: node的兄弟节点other是黑色的，且other的两个子节点也都是黑色的
                if((other.left == null || isBlack(other.left)) &&
                        (other.right == null || isBlack(other.right))) {
                    other.color = RED;
                    node = parent;
                    parent = node.parent;
                } else {
                    //case3: node的兄弟节点other是黑色的，且other的左子节点是红色，右子节点是黑色
                    if(other.right == null || isBlack(other.right)) {
                        other.left.color = BLACK;
                        other.color = RED;
                        rightRotate(other);
                        other = parent.right;
                    }

                    //case4: node的兄弟节点other是黑色的，且other的右子节点是红色，左子节点任意颜色
                    other.color = parent.color;
                    parent.color = BLACK;
                    other.right.color = BLACK;
                    leftRotate(parent);
                    node = this.root;
                    break;
                }
            } else { //与上面的对称
                other = parent.left;

                if (isRed(other)) {
                    // Case 1: node的兄弟other是红色的
                    other.color = BLACK;
                    parent.color = RED;
                    rightRotate(parent);
                    other = parent.left;
                }

                if ((other.left==null || isBlack(other.left)) &&
                        (other.right==null || isBlack(other.right))) {
                    // Case 2: node的兄弟other是黑色，且other的俩个子节点都是黑色的
                    other.color = RED;
                    node = parent;
                    parent =  node.parent;
                    parent = node.parent;
                } else {

                    if (other.left==null || isBlack(other.left)) {
                        // Case 3: node的兄弟other是黑色的，并且other的左子节点是红色，右子节点为黑色。
                        other.right.color = BLACK;
                        other.color = RED;
                        leftRotate(other);
                        other = parent.left;
                    }

                    // Case 4: node的兄弟other是黑色的；并且other的左子节点是红色的，右子节点任意颜色
                    other.color = parent.color;
                    parent.color = BLACK;
                    other.left.color = BLACK;
                    rightRotate(parent);
                    node = this.root;
                    break;
                }
            }
        }
        if (node!=null)
            node.color = BLACK;
    }

    private RBNode<T> search(RBNode<T> x, T key) {
        if(x == null)
            return x;

        int cmp = key.compareTo(x.key);

        if(cmp < 0)
            return search(x.left, key);
        else if(cmp > 0)
            return search(x.right, key);
        else
            return x;
    }

    /********* 查找节点x的后继节点,即大于节点x的最小节点 ***********/
    public RBNode<T> successor(RBNode<T> x) {
        //如果x有右子节点，那么后继节点为“以右子节点为根的子树的最小节点”
        if(x.right != null)
            return minNode(x.right);
        //如果x没有右子节点，会出现以下两种情况：
        //1. x是其父节点的左子节点，则x的后继节点为它的父节点
        //2. x是其父节点的右子节点，则先查找x的父节点p，然后对p再次进行这两个条件的判断
        RBNode<T> p = x.parent;
        while((p != null) && (x == p.right)) { //对应情况2
            x = p;
            p = x.parent;
        }
        return p; //对应情况1

    }

    /********* 查找节点x的前驱节点，即小于节点x的最大节点 ************/
    public RBNode<T> predecessor(RBNode<T> x) {
        //如果x有左子节点，那么前驱结点为“左子节点为根的子树的最大节点”
        if(x.left != null)
            return maxNode(x.left);
        //如果x没有左子节点，会出现以下两种情况：
        //1. x是其父节点的右子节点，则x的前驱节点是它的父节点
        //2. x是其父节点的左子节点，则先查找x的父节点p，然后对p再次进行这两个条件的判断
        RBNode<T> p = x.parent;
        while((p != null) && (x == p.left)) { //对应情况2
            x = p;
            p = x.parent;
        }
        return p; //对应情况1
    }

    public T minValue() {
        RBNode<T> node = minNode(root);
        if(node != null)
            return node.key;
        return null;
    }

    private RBNode<T> minNode(RBNode<T> tree) {
        if(tree == null)
            return null;
        while(tree.left != null) {
            tree = tree.left;
        }
        return tree;
    }

    public T maxValue() {
        RBNode<T> node = maxNode(root);
        if(node != null)
            return node.key;
        return null;
    }

    private RBNode<T> maxNode(RBNode<T> tree) {
        if(tree == null)
            return null;
        while(tree.right != null)
            tree = tree.right;
        return tree;
    }

    public boolean isRed(RBNode<T> node) {
        return (node != null) && (node.color == RED);
    }

    public boolean isBlack(RBNode<T> node) {
        return !isRed(node);
    }
}