package acwing.greedy;

import java.util.*;

public class 灵能传输 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        while (T-- > 0) {
            int N = sc.nextInt();
            long[] pre_sum = new long[N+1];
            for(int i = 0; i < N; i++)
                pre_sum[i+1] = pre_sum[i] + sc.nextInt();;

            long s0 = pre_sum[0];
            long sn = pre_sum[N];
            if (s0 > sn) {
                long tmp = s0; s0 = sn; sn = tmp;
            }

            Arrays.sort(pre_sum);
            for(int i = 0; i <= N; i++){
                if(pre_sum[i] == s0){
                    s0 = i;
                    break;
                }
            }
            for(int i = 0; i <= N; i++){
                if(pre_sum[i] == sn){
                    sn = i;
                    break;
                }
            }

            long[] arr = new long[N+1];
            boolean[] vis = new boolean[N+1];
            int le = 0, ri = N;
            for(int i = (int)s0; i >= 0; i -= 2) {
                arr[le++] = pre_sum[i];
                vis[i] = true;
            }
            for(int i = (int)sn; i <= N; i += 2) {
                arr[ri--] = pre_sum[i];
                vis[i] = true;
            }

            for(int i = 0; i <= N; i++){
                if(!vis[i]){
                    arr[le++] = pre_sum[i];
                }
            }

            long res = 0;
            for(int i = 1; i <= N; i++)
                res = Math.max(res, Math.abs(arr[i] - arr[i-1]));
            System.out.println(res);
        }
    }
}