import java.io.*;
import java.util.*;

class Solution {
    static int N, maxN = 1510, idx = 0;
    static int[] from = new int[maxN], to = new int[maxN], info = new int[maxN], val = new int[maxN];
    static boolean[][] st = new boolean[maxN][maxN];
    static boolean[] has_fa = new boolean[maxN];
    static int[][] dp = new int[maxN][2];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine().trim());

        Arrays.fill(info, -1);
        for (int i = 0; i < N; i++) {
            String[] str = br.readLine().split(" ");
            int a = Integer.parseInt(str[0]);
            val[a] = Integer.parseInt(str[1]);
            for (int j = 0; j < Integer.parseInt(str[2]); j++) {

                int b = Integer.parseInt(str[j+3]);
                if (st[a][b] || st[b][a]) continue;
                add(a, b);
                st[a][b] = true;
                has_fa[b] = true;
            }
        }

        int root = 1;
        while(has_fa[root]) root++;
        dfs(root);  // 第一个没有父节点的点

        if (Math.min(dp[root][0], dp[root][1]) == 0) {
            System.out.println(Math.max(dp[root][0], dp[root][1]));
        } else {
            System.out.println(Math.min(dp[root][0], dp[root][1]));
        }
    }

    private static void dfs(int u) {
        dp[u][1] = val[u]; dp[u][0] = 0;
        for(int i = info[u]; i != -1; i = from[i]) {
            int t = to[i];
            dfs(t);
            dp[u][0] += dp[t][1];
            dp[u][1] += Math.min(dp[t][0], dp[t][1]);
        }
    }

    private static void add(int a, int b) {
        from[idx] = info[a];
        to[idx] = b;
        info[a] = idx++;
    }
}