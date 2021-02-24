import java.util.*;

class Solution {
    public void minOperations(int[] target, int[] arr) {
        Segment_Tree_Max st = new Segment_Tree_Max();
        st.build(1, 1, target.length);
    }

    static class Segment_Tree_Max {
        int maxN = 100010;
        Node[] segs = new Node[4*maxN];

        void build(int root, int le, int ri) {
            segs[root] = new Node(le, ri, 0);
            if (le == ri) return;
            int mid = (le+ri) >> 1;
            build(root<<1, le, mid);
            build(root<<1, mid+1, ri);
        }

        void update(int root, int tar, int val) {
            int le = segs[root].le, ri = segs[root].ri;
            if (le == ri && le == root) {
                segs[root].max = Math.max(segs[root].max, val);
            } else {
                int mid = (le+ri) >> 1;
                if (tar <= mid) update(root<<1, tar, val);
                else update(root<<1|1, tar, val);
                segs[root].max = Math.max(segs[root<<1].max, segs[root<<1|1].max);
            }
        }



//        void update(int i, int l, int r, int pos, int val) {
//            if(l == r && l == pos) {
//                data[i] = max(data[i], val);
//            }
//            else {
//                int m = (l + r) >> 1;
//                if(pos <= m) update(lson, l, m, pos, val);
//                else update(rson, m + 1, r, pos, val);
//                data[i] = max(data[lson], data[rson]);
//            }
//        }
//        int query(int i, int l, int r, int li, int ri) {
//            if(li > ri) {
//                return 0;
//            }
//            if(li <= l && r <= ri) {
//                return data[i];
//            }
//            else {
//                int m = (l + r) >> 1;
//                if(ri <= m) return query(lson, l, m, li, ri);
//                else if(li > m) return query(rson, m + 1, r, li, ri);
//                else {
//                    int lans = query(lson, l, m, li, ri);
//                    int rans = query(rson, m + 1, r, li, ri);
//                    return max(lans, rans);
//                }
//            }
//        }


        private static class Node {
            int le, ri, max, tag;
            public Node(int l, int r, int s) {
                le = l; ri = r; max = s; tag = 0;
            }
        }
    }
}