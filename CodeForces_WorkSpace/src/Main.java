import java.util.*;
import java.io.*;

class Main {
    static int N, idx = 0, maxN = 1510;
    static int[] from = new int[maxN], to = new int[maxN], info = new int[maxN];
    static boolean[] hasPa = new boolean[maxN];
    static int[][] dp = new int[maxN][3];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String str = br.readLine();
            if (str == null) break;
            N = Integer.parseInt(str);

            Arrays.fill(hasPa, false);
            Arrays.fill(info, -1);
            idx = 0;
            while (N-- > 0) {
                String[] arr = br.readLine().split(" ");
                int a = Integer.parseInt(arr[0].substring(0, arr[0].indexOf(':')));
                for (int i = 1; i < arr.length; i++) {
                    int b = Integer.parseInt(arr[i]);
                    add(a, b);
                    hasPa[b] = true;
                }
            }

            int root = 0;
            while (hasPa[root]) root++;
            dfs(root);
            System.out.println(Math.min(dp[root][1], dp[root][2]));
        }
    }

    private static void dfs(int u) {
        dp[u][1] = 1;
        int sum = 0;
        for(int i = info[u]; i != -1; i = from[i]) {
            int t = to[i];
            dfs(t);

            dp[u][0] += Math.min(dp[t][1], dp[t][2]);
            dp[u][1] += Math.min(dp[t][0], Math.min(dp[t][1], dp[t][2]));
            sum += Math.min(dp[t][1], dp[t][2]);
        }

        dp[u][2] = 0x3f3f3f3f;
        for(int i = info[u]; i != -1; i = from[i]) {
            int t = to[i];
            dp[u][2] = Math.min(dp[u][2], dp[t][1] + sum - Math.min(dp[t][1], dp[t][2]));
        }
    }

    // private static void dfs(int u) {
    //     dp[1][u] = 1; dp[0][u] = 0;
    //     for (int i = info[u]; i != -1; i = from[i]) {
    //         int t = to[i];
    //         dfs(t);
    //         dp[0][u] += dp[1][t];
    //         dp[1][u] += Math.min(dp[1][t], dp[0][t]);
    //     }
    // }

    private static void add(int a, int b) {
        from[idx] = info[a];
        to[idx] = b;
        info[a] = idx++;
    }
}