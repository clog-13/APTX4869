package template;

import java.util.Arrays;
import java.util.Scanner;

public class Dijkstra {
    static int INF = Integer.MAX_VALUE >> 1;
    static int N, M ;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        int[][] graph = new int[N+1][N+1];
        for (int i = 0 ; i <= N ; i ++) Arrays.fill(graph[i], INF);

        for (int i = 0; i < M ; i ++){
            int x = sc.nextInt();
            int y = sc.nextInt();
            int z = sc.nextInt();
            graph[x][y] = Math.min(graph[x][y], z);
        }

        int[] res = dijkstra(graph, 1);

        if (res[N] == INF) System.out.println(-1);
        System.out.println(res[N]);
    }

    public static int[] dijkstra(int[][] graph, int s){
        int[] dist = new int[N+1];
        boolean[] visit = new boolean[N+1];

        Arrays.fill(dist, INF);
        dist[s] = 0;

        for (int i = 1; i <= N ; i++){
            int cur = -1;
            for (int j = 1; j <= N; j++)
                if (!visit[j] && (cur == -1 || dist[cur] > dist[j]))
                    cur = j;
            visit[cur] = true;
            for (int j = 1; j <= N; j++)
                dist[j] = Math.min(dist[j], dist[cur] + graph[cur][j]);
        }
        return dist;
    }
}