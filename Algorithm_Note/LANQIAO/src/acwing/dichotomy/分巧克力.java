package acwing.dichotomy;

import java.util.Scanner;

public class 分巧克力 {
    static int N, K;
    static int[][] arr;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        K = sc.nextInt();

        int le = 1, ri = 0;
        arr = new int[N][2];
        for(int i = 0; i < N; i++) {
            arr[i][0] = sc.nextInt();
            arr[i][1] = sc.nextInt();
            ri = Math.max(ri, Math.max(arr[i][0], arr[i][1]));
        }

        while(le < ri) {
            int mid = (le + ri + 1) >> 1;
            if (check(mid)) {
                le = mid;
            }else {
                ri = mid-1;
            }
        }
        System.out.println(ri);

    }

    private static boolean check(int n) {
        int sum = 0;
        for(int i = 0; i < N; i++) {
            sum += (arr[i][1]/n)*(arr[i][0]/n);
            if(sum > K) return true;
        }
        return false;
    }
}
