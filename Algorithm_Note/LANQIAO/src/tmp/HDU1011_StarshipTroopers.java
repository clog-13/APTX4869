import java.util.ArrayList;
import java.util.Scanner;

public class HDU1011_StarshipTroopers {
    static Scanner sc;
    static int n, m;
    static int[][] arr, dp;
    static ArrayList<Integer>[] graph;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        while(true) {
            n = sc.nextInt();
            m = sc.nextInt();
            if(n == -1) break;
            createGraph();

            if(m == 0) System.out.println(0);
            else {
                dp = new int[n+1][m+1];     // n:出发房间 m:人数下最大值
                bfs(1, -1);
                System.out.println(dp[1][m]);
            }

        }
    }

    private static void bfs(int cur, int from) {
        int tNum = (arr[cur][0]+19) / 20;
        for(int i = tNum; i <= m; i++) {
            dp[cur][i] = arr[cur][1];
        }

        for(int i = 0; i < graph[cur].size(); i++) {
            int s = graph[cur].get(i);
            if(s == from) continue;
            bfs(s, cur);
            for(int j = m; j > tNum; j--) {
                for(int k = 1; k <= j-tNum; k++) {
                    dp[cur][j] = Math.max(dp[cur][j], dp[s][k] + dp[cur][j-k]);
                }
            }
        }
    }

    private static void createGraph() {
        arr = new int[n+1][2];
        graph = new ArrayList[n+1];
        for(int i = 1; i <= n; i++) {
            arr[i][0] = sc.nextInt();
            arr[i][1] = sc.nextInt();
            graph[i] = new ArrayList<>();
        }

        for(int i = 1; i < n; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            graph[a].add(b);
            graph[b].add(a);
        }
    }
}