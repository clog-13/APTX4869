import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class LQB_1460 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();
        Set<Integer> set = new HashSet<>();
        int[] data = new int[101];
        int[] dp = new int[1<<20];
        Arrays.fill(dp, 1000);

        // data:罐子的二进制状态
        // dp:某种组合状态
        for(int i = 0; i < N; i++) {
            int tmp = 0;
            for(int j = 0; j < K; j++) {
                int x = sc.nextInt();
                set.add(x);
                tmp = tmp | (1<<(x-1));
            }
            data[i] = tmp;
            dp[tmp] = 1;
        }

        if(set.size() < M) System.out.println(-1);
        else {
            int E = (1<<M)-1;
            for(int i = 1; i <= N; i++) {       // data, 用每个罐子去匹配
                for(int j = 0; j <= E; j++) {   // dp
                    if(dp[j] != 1000) {
                        int tmp = data[i] | j;
                        dp[tmp] = Math.min(dp[tmp], dp[j]+1);
                    }
                }
            }
            System.out.println(dp[E]);
        }
    }
}