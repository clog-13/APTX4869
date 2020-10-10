package clrs.structures;

// TODO Simple-Demo
public class BinaryIndexTree {
    int N;
    int[] pre_sum;

    public BinaryIndexTree(int[] arr) {
        N = arr.length;
        pre_sum = new int[N+1];

        for(int i = 0; i < N; i++)
            update(i+1, arr[i]);
    }

    public BinaryIndexTree(int  N) {
        this.N = N;
        pre_sum = new int[N+1];
    }

    /**
     * 返回最低位的 1（的值）
     */
    private int lowbit(int x) {
//        return x & (x ^ (x-1));
        return x & (-x);
    }

    private void update(int idx, int x) {
        while(idx <= N) {
            pre_sum[idx] += x;
            idx += lowbit(idx);
        }
    }

    private int sum(int idx) {
        int res = 0;
        for(; idx > 0; idx -= lowbit(idx))
            res += pre_sum[idx];
        return res;
    }

    private int sum(int s, int e) {
        return sum(e) - sum(s);

    }

    public static void main(String[] args) {
        int[] t = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        BinaryIndexTree bit = new BinaryIndexTree(t);
        for(int i = 0; i < t.length; i++)
            System.out.print(bit.sum(i, i+1) + ", ");
        System.out.println();
        int tmp = 16;
        for(int i = 0; i < t.length; i++)
            bit.update(i+1, tmp--);

        for(int i = 0; i < t.length; i++)
            System.out.print(bit.sum(i, i+1) + ", ");
        System.out.println();
        for(int i = 1; i <= 16; i++)
            System.out.print(bit.lowbit(i)+", ");
        System.out.println();
    }
}
