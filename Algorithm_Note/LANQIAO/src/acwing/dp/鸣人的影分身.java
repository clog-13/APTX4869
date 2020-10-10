package acwing.dp;

import java.util.*;

public class 鸣人的影分身 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- > 0) {
            int M = sc.nextInt();
            int N = sc.nextInt();

            // 当前 j 个数，总和为 i 时的情况数量
            int[][] dp = new int[M+1][N+1];
            dp[0][0] = 1;   // 其余dp[x][0] == 0; 因为不可能出现 0 个数总和为 x 的情况
            for (int i = 0; i <= M; i++) {
                for (int j = 1; j <= N; j++) {
                    dp[i][j] = dp[i][j-1];      // 新增一个分配个数， 新增的值为零
                    if(i >= j) dp[i][j] += dp[i-j][j];
                }
            }

            System.out.println(dp[M][N]);
        }
    }
}