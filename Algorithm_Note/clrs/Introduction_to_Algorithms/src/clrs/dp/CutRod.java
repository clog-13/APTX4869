package clrs.dp;

public class CutRod {
    static int[] p = {0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
    static int[] memo, dp, s;

    public static void main(String[] args) {
//        System.out.println(recusion(10));
//        System.out.println(init_memo(40));
        System.out.println(dp(10));
    }

    private static int dp(int N) {
        dp = new int[N+1];
        s = new int[N+1];
        dp[0] = 0;
        for(int i = 1; i <= N; i++) {
            int tMax = Integer.MIN_VALUE;
            for(int j = 1; j <= i; j++){
                tMax = Math.max(tMax, p[j] + dp[i-j]);
//                if(tMax < p[j] + dp[i-j]){
//                    tMax = p[j] + dp[i-j];
//                    s[i] = j;
//                }
            }
            dp[i] = tMax;
        }
        return dp[N];
    }

    private static int init_memo(int N) {
        memo = new int[N+1];
        for(int i = 0; i <= N; i++)
            memo[i] = Integer.MIN_VALUE;
        return memo_recusion(N);
    }

    private static int memo_recusion(int N) {
        if(memo[N] >= 0) return memo[N];
        if(N == 0) return 0;

        int res = Integer.MIN_VALUE;
        for(int i = 1; i <= N; i++)
            res = Math.max(res, p[i] + memo_recusion(N-i));
        return res;
    }

    private static int recusion(int N) {
        if(N == 0) return 0;
        int res = Integer.MIN_VALUE;
        for(int i = 1; i <= N; i++) {
            res = Math.max(res, p[i] + recusion(N-i));
        }
        return res;
    }
}
