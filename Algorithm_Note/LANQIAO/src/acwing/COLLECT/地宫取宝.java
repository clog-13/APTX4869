package acwing.COLLECT;

import java.util.Scanner;

public class 地宫取宝 {
    static int MOD = 1000000007;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();

        int[][] data = new int[N+1][M+1];
        for(int i = 1; i <= N; i++)
            for(int j = 1; j <= M; j++)
                data[i][j] = sc.nextInt() + 1;


        int[][][][] dp = new int[N+1][M+1][K+1][14];
        dp[1][1][1][data[1][1]] = 1;
        dp[1][1][0][0] = 1;
        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= M; j++) {
                for(int c = 0; c <= K; c++) {
                    for(int v = 0; v <= 13; v++) {      // 不拿

                        int tmp =  dp[i-1][j][c][v];
                        tmp = (tmp+dp[i][j-1][c][v]) % MOD;
                        if(v == data[i][j] && c > 0) {  // 拿
                            for(int s = 0; s < v; s++) {
                                tmp = (tmp+dp[i-1][j] [c-1][s]) % MOD;
                                tmp = (tmp+dp[i][j-1] [c-1][s]) % MOD;
                            }
                        }
                        dp[i][j][c][v] = (tmp+dp[i][j][c][v]) % MOD;
                    }
                }
            }
        }
        int res = 0;
        for(int i = 0; i <= 13; i++)
            res = (res+dp[N][M][K][i]) % MOD;

        System.out.println(res);
    }
}
