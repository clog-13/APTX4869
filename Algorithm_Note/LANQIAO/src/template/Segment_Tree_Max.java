package template;

import java.util.Arrays;

/**
 * 最大（小）值
 */
class Segment_Tree_Max {
    int maxN = 100010;
    int[] arr = new int[maxN];
    Node[] segs = new Node[4*maxN];

    public Segment_Tree_Max(int[] nums) {
        System.arraycopy(nums, 0, arr, 1, nums.length);
        build(1, 1, nums.length+1);
    }

    private void build(int idx, int le, int ri) {
        if (le == ri) {
            segs[idx] = new Node(le, ri, arr[le]);
        } else {
            segs[idx] = new Node(le, ri, 0);

            int mid = le+ri >> 1;
            build(idx<<1, le, mid);
            build(idx<<1 | 1, mid+1, ri);

            cout_up(idx);
        }
    }

    private void cout_up(int idx) {
        segs[idx].max = Math.max(segs[idx<<1].max, segs[idx<<1 | 1].max);
    }

    private int query(int idx, int le, int ri) {
        if (le <= segs[idx].le && segs[idx].ri <= ri) return segs[idx].max;
        int mid = (segs[idx].le + segs[idx].ri) >> 1;
        int res = 0;
        if (le <= mid) res = query(idx<<1, le, ri);
        if (ri > mid) res = Math.max(res, query(idx<<1 | 1, le, ri));
        return res;
    }

    // 修改操作，在idx结点中，tar位置加上val
    private void update(int idx, int tar, int val) {
        if (segs[idx].le == segs[idx].ri) segs[idx].max = val;
        else {
            int mid = (segs[idx].le + segs[idx].ri) >> 1;
            if (tar <= mid) update(idx<<1, tar, val);
            else update(idx<<1 | 1, tar, val);
            cout_up(idx);
        }
    }

    private static class Node {
        int le, ri;
        int max;

        public Node(int l, int r, int s) {
            le = l;
            ri = r;
            max = s;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[7];
        Arrays.fill(nums, 1);
        Segment_Tree_Max stm = new Segment_Tree_Max(nums);
        System.out.println(stm.query(1, 1, 7));
        stm.update(1, 1, 2);
        System.out.println(stm.query(1, 1, 7));
        System.out.println(stm.query(1, 4, 7));
        System.out.println(stm.query(1, 1, 4));
    }
}