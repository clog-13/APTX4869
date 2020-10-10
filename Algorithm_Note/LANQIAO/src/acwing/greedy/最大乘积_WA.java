package acwing.greedy;

import java.util.*;

public class 最大乘积_WA {
    static int MOD = 1000000009;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        long[] pos = new long[N];
        long[] neg = new long[N];
        int p = 0, n = 0;

        for(int i = 0; i < N; i++) {
            long tmp = sc.nextLong();
            if(tmp > 0) {
                pos[p++] = tmp;
            }else {
                neg[n++] = -tmp;
            }
        }

        Arrays.sort(pos, 0, p);
        Arrays.sort(neg, 0 ,n);
        p--; n--;
        long res = 1, i = 0;

        if(p <= 0) {
            if(K % 2 == 0) {
                for(; i < K; i++) {
                    res = (res * neg[(int) (n-i)]) % MOD;
                }
                System.out.println(res);
            }else {
                for(; i < K; i++) {
                    res = (res * neg[(int) i]) % MOD;
                }
                System.out.println(-res);
            }
            return;
        }

        if(K % 2 != 0) {
            i++;
            res *= pos[p];
        }
        for(; i < K; i += 2) {
            if(p-1 >= 0 && n-1 >= 0) {
                if(pos[p] * pos[p-1] > neg[n] * neg[n-1]) {
                    res = (res * pos[p]) % MOD;
                    res = (res * pos[p-1]) % MOD;
                    p -= 2;
                }else {
                    res = (res * neg[n]) % MOD;
                    res = (res * neg[n-1]) % MOD;
                    n -= 2;
                }
            }else if(p-1 >= 0){
                res = (res * pos[p]) % MOD;
                res = (res * pos[p-1]) % MOD;
                p -= 2;
            }else {
                res = (res * neg[n]) % MOD;
                res = (res * neg[n-1]) % MOD;
                n -= 2;
            }
        }
        System.out.println(res);
    }
}
