package acwing.COLLECT;

import java.util.*;

public class 糖果传递 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        long[] data = new long[N];
        long sum = 0;
        for (int i = 0; i < N; i++) {
            data[i] = sc.nextInt();
            sum += data[i];
        }

        long ave = sum / N;
        long[] c = new long[N];
        c[N-1] = 0 + ave - data[N-1];
        for (int n = N-2; n > 0; n--)
            c[n] = c[n+1] + ave - data[n];

        Arrays.sort(c);
        long res = 0;
        for(int i = 0; i < N; i++)
            res += Math.abs(c[i] - c[N/2]);

        System.out.println(res);
    }
}
