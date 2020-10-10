package acwing.dp;

import java.util.Scanner;

public class 最长上升子序列 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] data = new int[N];
        for(int i = 0; i < N; i++)
            data[i] = sc.nextInt();

        int res = 0;
        int[] dp = new int[N];
        for(int i = 0; i < N; i++) {
            dp[i] = 1;
            for(int j = 0; j < i; j++)
                if(data[i] > data[j])
                    dp[i] = Math.max(dp[i], dp[j]+1);

            res = Math.max(res, dp[i]);
        }
        System.out.println(res);
    }
}
