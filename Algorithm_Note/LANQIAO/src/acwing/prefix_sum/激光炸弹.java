package acwing.prefix_sum;

import java.util.Scanner;

public class 激光炸弹 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int R = sc.nextInt();

        int[][] data = new int[5010][5010];
        int[][] pre_sum = new int[5010][5010];
        for(int i = 0; i < N; i++) {    // 先初始化目标数组
            int x = sc.nextInt();
            int y = sc.nextInt();
            data[x+1][y+1] = sc.nextInt();
        }
        int res = 0;
        for(int i = 1; i < 5010; i++) {
            for(int j = 1; j < 5010; j++) {
                pre_sum[i][j] = pre_sum[i-1][j] + pre_sum[i][j-1] - pre_sum[i-1][j-1] + data[i][j];
                if(i >= R && j >= R) {
                    // x1 = i-(R-1);
                    // y1 = i-(R-1);
                    // res = Math.max(res, pre_sum[i][j] - pre_sum[i-(R-1)-1][j] - pre_sum[i][j-(R-1)-1] + pre_sum[i-(R-1)-1][j-(R-1)-1]);
                    res = Math.max(res, pre_sum[i][j] - pre_sum[i-R][j] - pre_sum[i][j-R] + pre_sum[i-R][j-R]);
                }
            }
        }
        System.out.println(res);
    }
}
