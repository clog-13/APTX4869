import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class LQB_1455 {
    static int N = 30, M = 50;
    static char[][] maze;
    static int[][] lens;
    static int[][] DIR = {{1, 0},{0, -1},{0, 1},{-1, 0}};   // U, R, l, D
    static char[] c = {'D','L','R','U'};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Queue<Integer> queue = new LinkedList<>();
        maze = new char[N][M];
        lens = new int[N][M];


        for(int i = 0; i < N; i++) {
            String string = scanner.next();
            for(int j=0;j<M;j++) {
                maze[i][j] = string.charAt(j);
            }
        }

        queue.add((N-1)*M + M-1);
        while(!queue.isEmpty()) {
            int tmp = queue.poll();
            for(int i = 0; i < 4; i++) {
                int xx = tmp/M + DIR[i][0];
                int yy = tmp%M + DIR[i][1];
                if(xx<0||xx>=N || yy<0||yy>=M || maze[xx][yy] == '1' || lens[xx][yy] != 0) continue;

                queue.add(xx*M + yy);
                lens[xx][yy] = lens[tmp/M][tmp%M] + 1;
                if(xx==0 && yy==0) break;
            }
        }
        lens[N-1][M-1] = 0;

        StringBuilder record = new StringBuilder();
        int x = 0,y = 0;
        while(x!=N-1 || y!=M-1) {
            for(int i = 0; i < 4; i++) {
                int xx = x + DIR[i][0];
                int yy = y + DIR[i][1];
                if(xx<0||xx>= N || yy<0||yy>= M || maze[xx][yy]=='1') continue;
                if(lens[x][y]-1 == lens[xx][yy]) {
                    x = xx; y = yy;
                    record.append(c[i]);
                    break;
                }
            }
        }

        String strand = "DDDDRRURRRRRRDRRRRDDDLDDRDDDDDDDDDDDDRDDRRRURRUURRDDDDRDRRRRRRDRRURRDDDRRRRUURUUUUUUULULLUUUURRRRUULLLUUUULLUUULUURRURRURURRRDDRRRRRDDRRDDLLLDDRRDDRDDLDDDLLDDLLLDLDDDLDDRRRRRRRRRDDDDDDRR";
        System.out.println(record.toString().equals(strand));
        System.out.println(record.toString());
    }
}