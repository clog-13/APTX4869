import java.util.*;

class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.countVowelStrings(33));
    }

    public int countVowelStrings(int n) {
        int[][] dp = new int[n+1][5];
        for (int i = 0; i < 5; i++) dp[1][i] = 1;
        for (int t = 2; t <= n; t++) {
            for (int i = 0; i < 5; i++) {
                for (int j = i; j < 5; j++) {
                    dp[t][i] += dp[t-1][j];
                }
            }
        }
        int res = 0;
        for (int i = 0; i < 5; i++) res += dp[n][i];
        return res;
    }
}