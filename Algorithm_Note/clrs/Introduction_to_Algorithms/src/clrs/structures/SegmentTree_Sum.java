package clrs.structures;

class Node {
    int le, ri, sum, lazy;
    public Node() {
        le = 0; ri = 0; sum = 0; lazy = 0;
    }
}

// TODO
public class SegmentTree_Sum {
    int[] arr;
    Node[] tr;
    public SegmentTree_Sum(int[] arr) {
        this.arr = arr;
    }

    private void pushup(int k) {
        tr[k].sum = tr[k >> 1].sum + tr[k >> 1 | 1].sum;
    }

    private void builde(int k, int le, int ri) {
        tr[k].le = le;
        tr[k].ri = ri;
        if(le == ri) {
            tr[k].sum = arr[k];
            return;
        }
        int mid = (le + ri) >> 1;
        builde(k<<1, le, mid);
        builde(k<<1|1, mid+1, ri);
        pushup(k);
    }

    /**
     * @param k 下标
     * @param le 左边界
     * @param ri 右边界
     * @param x 所有值 + x
     */
    private void modifySegment(int k, int le, int ri, int x) {
        if(tr[k].le == le && tr[k].ri == ri) {
            tr[k].sum += (ri-le+1) * x;     // 更新区间的sum
            tr[k].lazy += x;                // 添加lazy标记
            return;
        }

        int mid = (tr[k].le + tr[k].ri) / 2;
        if(ri <= mid)       // 如果被修改区间完全在左区间
            modifySegment(k << 1, le, ri, x);
        else if(le >= mid)  // 如果被修改区间完全在右区间
            modifySegment(k<<1|1, le, ri ,x);
        else {  // 否则就把区间分解为两块，分别递归
            modifySegment(k<<1, le, mid, x);
            modifySegment(k<<1|1, mid+1, ri, x);
        }
        pushup(k);
    }

    void pushdown(int k) {  // 将点k的懒惰标记下传
        if(tr[k].le == tr[k].ri) {
            tr[k].lazy = 0;
            return;
        }
        int lk = k << 1, rk = k << 1 | 1;
        //如果节点k已经是叶节点了，没有子节点，那么标记就不用下传，直接删除就可以了
        tr[k*2].sum += (tr[k*2].ri - tr[k*2].le+1) * tr[k].lazy;
        tr[k*2+1].sum += (tr[k*2+1].ri - tr[k*2+1].le+1) * tr[k].lazy;
        //给k的子节点重新赋值
        tr[k*2].lazy += tr[k].lazy;
        tr[k*2+1].lazy += tr[k].lazy;
        //下传点k的标记
        tr[k].lazy = 0;//记得清空点k的标记
    }
}
