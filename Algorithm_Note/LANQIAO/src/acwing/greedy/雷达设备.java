package acwing.greedy;

import java.util.*;

public class 雷达设备 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long D = sc.nextInt();

        double[][] data = new double[N][2];
        for (int i = 0; i < N; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            if(y > D) {
                System.out.println(-1);
                return;
            }
            double len = Math.sqrt(D*D - y*y);
            data[i][0] = x - len;
            data[i][1] = x + len;
        }

        Arrays.sort(data, (r1, r2) -> {
            if(r1[1] != r2[1]) return Double.compare(r1[1], r2[1]);     // 这里要注意精度判断问题
            else return Double.compare(r1[0], r2[1]);
        });

        int res = 1;
        double ri = data[0][1];
        for(int i = 1; i < N; i++) {
            if(data[i][0] > ri) {
                res++;
                ri = data[i][1];
            }
        }
        System.out.println(res);
    }
}
