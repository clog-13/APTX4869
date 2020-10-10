package acwing.enum_simulation;

import java.util.Scanner;

public class 递增三元组 {
    static int M = 100010;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        int[][] data = new int[3][N];
        for(int t = 0; t < 3; t++) for(int i = 0; i < N; i++)
            data[t][i] = sc.nextInt();

        int[] cnt = new int[M];
        int[] pre_sum = new int[M];
        int[] a_sum = new int[N];
        for(int i = 0; i < N; i++) cnt[data[0][i]]++;
        for(int i = 1; i < M; i++) pre_sum[i] = pre_sum[i-1] + cnt[i];
        for(int i = 0; i < N; i++) a_sum[i] = pre_sum[data[1][i] - 1];

        cnt = new int[M];
        pre_sum = new int[M];
        int[] c_sum = new int[N];
        for(int i = 0; i < N; i++) cnt[data[2][i]]++;
        for(int i = 1; i < M; i++) pre_sum[i] = pre_sum[i-1] + cnt[i];
        for(int i = 0; i < N; i++) c_sum[i] = pre_sum[M-1] - pre_sum[data[1][i]];

        long res = 0;
        for(int i = 0; i < N; i++)
            res += (long)a_sum[data[1][i]] * c_sum[data[1][i]];
        System.out.println(res);
    }
}
