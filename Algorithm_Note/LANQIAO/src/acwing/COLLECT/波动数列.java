package acwing.COLLECT;

import java.util.Scanner;


// TODO understand?
public class 波动数列 {
    static int MOD = 100000007;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), S = sc.nextInt();
        int A = sc.nextInt(), B = sc.nextInt();

        int[][] dp = new int[N][1010];
        dp[0][0] = 1;
        for(int i = 1; i < N; i++) {
            for(int j = 1; j < N; j++) {
//                dp[i][j] = (dp[i-1][getMOD(j - A*(N-i), N)] + dp[i-1][getMOD(j + B*(N-i), N)]) % MOD;
                dp[i][j] = (dp[i-1][getMOD(j - A*i, N)] + dp[i-1][getMOD(j + B*i, N)]) % MOD;
            }
        }
        System.out.println(dp[N-1][getMOD(S, N)]);
    }

    private static int getMOD(int a, int b) {
        return (a % b + b) % b;
    }
}
