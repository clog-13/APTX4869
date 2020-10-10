package acwing.dp;

import java.util.HashMap;
import java.util.Scanner;

public class 括号配对 {
    static final HashMap<Character, Character> map = new HashMap<>();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        map.put('[', ']');
        map.put(']', '[');
        map.put('(', ')');
        map.put(')', '(');

        String str = sc.nextLine();
        int N = str.length();
        int[][] dp = new int[N][N];

        for (int len = 1; len <= N; len++) {
            for (int le = 0; le + len - 1 < N; le++) {
                int ri = le + len - 1;
                if (len == 1) dp[le][ri] = 0;
                else {
                    if(map.get(str.charAt(le)) == str.charAt(ri)) dp[le][ri] = dp[le+1][ri-1] + 2;
                    dp[le][ri] = Math.max(dp[le][ri], Math.max(dp[le+1][ri], dp[le][ri-1]));
                }
            }
        }

//        for(int[] d : dp)
//            for(int i : d)
//                System.out.print(i+", ");
//            System.out.println();

        System.out.println(N - dp[0][N-1]);
    }
}
