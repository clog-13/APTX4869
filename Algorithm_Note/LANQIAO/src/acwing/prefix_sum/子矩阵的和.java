package acwing.prefix_sum;

import java.util.Scanner;

public class 子矩阵的和 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int Q = sc.nextInt();

        int[][] data = new int[N+1][M+1];
        int[][] pre_sum = new int[N+1][M+1];
        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= M; j++) {
                data[i][j] = sc.nextInt();
                pre_sum[i][j] = pre_sum[i-1][j] + pre_sum[i][j-1] - pre_sum[i-1][j-1] + data[i][j];
            }
        }

        for(int i = 0; i < Q; i++) {
            int x1 = sc.nextInt();
            int y1 = sc.nextInt();
            int x2 = sc.nextInt();
            int y2 = sc.nextInt();

            System.out.println(pre_sum[x2][y2] - pre_sum[x1-1][y2] - pre_sum[x2][y1-1] + pre_sum[x1-1][y1-1]);
        }
    }
}
