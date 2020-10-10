package codeforces.dp;

import java.util.Scanner;

public class MaximumSumOnEvenPositions {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            int[] data = new int[N];
            long res = 0;
            for (int i = 0; i < N; i++) {
                data[i] = sc.nextInt();
                if((i & 1) == 0) res += data[i];
            }
            
            long max = 0, s1 = 0, s2 = 0;
            for (int i = 1; i < N; i += 2) {
                s1 = Math.max(0, data[i] - data[i-1] + s1);
                max = Math.max(max, s1);
            }
            for (int i = 2; i < N; i += 2) {
                s2 = Math.max(0, data[i-1] - data[i] + s2);
                max = Math.max(max, s2);
            }
            System.out.println(res+max);
        }
    }
}