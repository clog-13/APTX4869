package acwing.dp;

import java.util.Arrays;
import java.util.Scanner;

public class 糖果 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();

        // 前 i 个数里面， 选取总和的余数为 j 的方案数
        // 1. 不包含第i个数   2. 包含第i个数
        // dp[i][j] = Math.max(dp[i-1][j], dp[i-1][(j-v+K)%K] + v);
        int[][] dp = new int[N+1][K];
        for(int[] d : dp) Arrays.fill(d, Integer.MIN_VALUE);
        dp[0][0] = 0;
        for (int i = 1; i <= N; i++) {
            int v = sc.nextInt();
            for (int j = 0; j < K; j++) {
//                dp[i][j] = dp[i - 1][j];
//                dp[i][j] = Math.max(dp[i][j], dp[i - 1][mod(j - v,k)] + v);
                dp[i][j] = Math.max(dp[i-1][j], dp[i-1][((j-v%K)+K) % K] + v);
            }
        }
        System.out.println(dp[N][0]);
    }
}
