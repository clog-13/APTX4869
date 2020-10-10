package acwing.dp;

import java.util.Scanner;

public class 密码脱落 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String str = sc.nextLine();

        int N = str.length();
        int[][] dp = new int[N][N];
        for (int len = 1; len <= N; len++) {
            for (int le = 0; le+len-1 < N; le++) {
                int ri = le+len-1;
                if (len == 1) dp[le][ri] = 1;
                else {
                    if(str.charAt(le) == str.charAt(ri)) dp[le][ri] = dp[le+1][ri-1] + 2;
                    dp[le][ri] = Math.max(dp[le][ri], Math.max(dp[le+1][ri], dp[le][ri-1]));
                }
            }
        }
        System.out.println(N - dp[0][N-1]);
    }
}
