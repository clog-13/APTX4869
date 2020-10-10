package acwing.prefix_sum;

import java.util.Scanner;

/**
 * https://www.acwing.com/problem/content/797/
 */
public class 前缀和 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] data = new int[N+1];
        int[] pre_sum = new int[N+1];
        for(int i = 1; i <= N; i++) {
            data[i] = sc.nextInt();
            pre_sum[i] = pre_sum[i-1]+data[i];
        }

        for(int i = 1; i <= M; i++) {
            int le = sc.nextInt();
            int ri = sc.nextInt();
            System.out.println(pre_sum[ri]-pre_sum[le-1]);
        }
    }
}
