package acwing.dp;

import java.util.Scanner;

public class 地宫取宝_BFS_TLE {
    static int res = 0;
    static int N, M, K;
    static int[][] data;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        K = sc.nextInt();

        data = new int[N][M];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                data[i][j] = sc.nextInt();
            }
        }

        bfs(0, 0, 0, -1);
        System.out.println(res);
    }

    private static void bfs(int x, int y, int len, int cur_max) {
        if(len > K) return;
        if(x == N-1 && y == M-1) {
            if(len == K || (data[x][y] > cur_max && len+1 == K))  {
                res++;
            }
            return;
        }

        if(x+1 < N) {
            if(data[x][y] > cur_max)
                bfs(x+1, y, len+1, data[x][y]);
            bfs(x+1, y, len, cur_max);
        }
        if(y+1 < M) {
            if(data[x][y] > cur_max)
                bfs(x, y+1, len+1, data[x][y]);// 向左，
            bfs(x, y+1, len, cur_max);              // 向左，不拿
        }
    }

//    private static int getNum(int a, int b) {
//        if(a == 1 || b == 1) return 1;
//        if(a == 2 && b == 2) return 2;
//        if(a == 2 && b == 3) return 3;
//
//        int res = 0;
//        if(b > 3) res = getNum(a, b-1) + 1;
//
//
//    }
}
