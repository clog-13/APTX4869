package acwing.dp;

import java.util.Scanner;

public class 摘花生 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- > 0) {
            int R = sc.nextInt();
            int C = sc.nextInt();
            int[][] data = new int[R+1][C+1];
            for(int i = 1; i <= R; i++) for(int j = 1; j <= C; j++)
                data[i][j] = sc.nextInt();

            for(int i = 1; i <= R; i++) {
                for(int j = 1; j <= C; j++) {
                    data[i][j] += Math.max(data[i-1][j], data[i][j-1]);
                }
            }
            System.out.println(data[R][C]);
        }
    }
}
