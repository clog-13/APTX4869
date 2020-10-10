package acwing.dp;

import java.util.Scanner;

public class 石子问题 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        int[] pre_sum = new int[N];
        for(int i = 1; i < N; i++)
            pre_sum[i] = sc.nextInt() + pre_sum[i-1];

        int[][] dp = new int[N+1][N+1];
        for(int len = 2; len <= N; len++) {
            for(int i = 1; i+len-1 <= N; i++) {
                int e = i+len-1;
                dp[i][e] = Integer.MAX_VALUE;
                int sum = pre_sum[e] - pre_sum[i-1];
                for(int m = i; m < e; m++) {
                    dp[i][e] = Math.min(dp[i][m], dp[m+1][e]) + sum;
                }
            }
        }
        System.out.println(dp[1][N]);
    }
}
