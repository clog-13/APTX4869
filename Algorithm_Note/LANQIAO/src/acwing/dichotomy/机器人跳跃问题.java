package acwing.dichotomy;

import java.util.Scanner;

public class 机器人跳跃问题 {
    static int[] arr;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        arr = new int[N];
        for(int i = 0; i < N; i++)
            arr[i] = sc.nextInt();

        int le = 0, ri = 100000;
        int res = Integer.MAX_VALUE;
        while(le < ri) {
            int mid = (ri+le) >> 1;
            if(!test(mid)) {
                le = mid+1;
            }else {
                ri = mid;
                res = Math.min(res, mid);
            }
        }
        System.out.println(res);
    }

    private static boolean test(int e) {
        for(int i = 0; i < arr.length; i++) {
            if(e > 500000) return true;
            if(e <= arr[i]) e += (e-arr[i]);
            else e -= (arr[i]-e);
            if(e < 0) return false;
        }
        return true;
    }
}