package acwing.bit_segmentTree;

import java.util.Scanner;

public class 动态求连续区间和 {
    static int N, M;
    static int[] preSum;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        preSum = new int[N+1];
        int[] tmp = new int[N+1];
        for(int i = 1; i <= N; i++) tmp[i] = sc.nextInt();
        for(int i = 1; i <= N; i++) update(i, tmp[i]);

        while(M-- > 0) {
            int k = sc.nextInt();
            int a = sc.nextInt();
            int b = sc.nextInt();

            if(k == 0) System.out.println(getSum(b) - getSum(a-1));
            else update(a, b);
        }
    }

    private static int lowbit(int x) {
        return x & (-x);
    }

    private static void update(int idx, int x) {
        while(idx <= N) {
            preSum[idx] += x;
            idx += lowbit(idx);
        }
    }

    private static int getSum(int idx) {
        int res = 0;
        for(; idx > 0; idx -= lowbit(idx)) res += preSum[idx];
        return res;
    }
}
