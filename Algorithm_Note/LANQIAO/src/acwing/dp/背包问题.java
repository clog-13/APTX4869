package acwing.dp;

import java.util.Scanner;

public class 背包问题 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        int[] dp = new int[M+1];
        for(int step = 0; step < N; step++) {
            int size = sc.nextInt();
            int value = sc.nextInt();
            for(int i = M; i >= size; i--) {
                dp[i] = Math.max(dp[i], dp[i - size] + value);
            }
        }
        System.out.println(dp[M]);
    }
}

