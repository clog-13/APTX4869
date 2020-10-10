package acwing.greedy;

import java.util.*;

public class 最大乘积 {
    final static int MOD = 1000000009;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();

        long[] data = new long[N];
        for (int i = 0; i < N; i++) data[i] = sc.nextLong();
        Arrays.sort(data);

        long res = 1;
        int le = 0, ri = N-1;
        long sign = 1;
        if ((K & 1) == 1) {
            res *= data[ri--];
            K--;
            if(res < 0) sign = -1;
        }

        while (K > 0) {
            long x = data[le] * data[le+1];
            long y = data[ri] * data[ri-1];
            if (x*sign > y*sign) {
                res = x % MOD * res % MOD;
                le += 2;
            } else {
                res = y % MOD * res % MOD;
                ri -= 2;
            }
            K -= 2;
        }

        System.out.println(res);
    }
}