package acwing.recursive;

import java.util.Scanner;

public class 费解的开关 {
    static String[][] arr;
    public static void main(String[] args) {
        arr = new String[5][5];

        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        arr = new String[5][5];

        for(int i = 0; i < N; i++) {
            sc.nextLine();
            for(int j = 0; j < 5; j++)
                arr[j] = sc.nextLine().split("");
            System.out.println(dfs());
        }
    }

    private static String getRandom(int n) {
        StringBuilder res = new StringBuilder();
        for(int i = 4; i>=0; i--) {
            if((n>>i&1) == 1) res.append("1");
            else res.append("0");
        }
        return res.toString();
    }

    private static int dfs() {
        int res = Integer.MAX_VALUE;
        for(int i = 0; i < 1<<5; i++) {     // 枚举第一行的所有的状态
            String[][] backups = new String[5][5];
            for(int j = 0; j < 5; j++)
                for(int k = 0; k < 5; k++)
                    backups[j][k] = arr[j][k];

            int step = 0;
            for(int j = 0; j < 5; j++) {    // 翻转所枚举的第一行的状态
                if((i>>j & 1) == 1) {
                    step++;
                    turn(0, j);
                }
            }
            for(int j = 1; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    if (arr[j - 1][k].equals("0")) {
                        step++;
                        turn(j, k);
                    }
                }
            }

            boolean flag = true;
            for(int j = 0; j < 5; j++) {
                if(arr[4][j].equals("0")) {
                    flag = false;
                    break;
                }
            }

            for(int j = 0; j < 5; j++)
                for(int k = 0; k < 5; k++)
                    arr[j][k] = backups[j][k];
            if(flag)
                res = Math.min(res, step);
        }
        if(res > 6) return -1;
        return res ;
    }

    private static void turn(int x, int y) {
        int[][] dir = {{0, 0}, {-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for(int i = 0; i < 5; i++) {
            int nx = x+dir[i][0];
            int ny = y+dir[i][1];
            if(nx >= 0 && nx < 5 && ny >= 0 && ny < 5) {
                if(arr[nx][ny].equals("1"))
                    arr[nx][ny] = "0";
                else
                    arr[nx][ny] = "1";
            }
        }
    }

    private static void test() {
        for(int i = 0; i < 1<<5; i++) {
            for(int j = 0; j < 1<<5; j++) {
                for(int k = 0; k < 1<<5; k++) {
                    for(int l = 0; l < 1<<5; l++) {
                        for(int m = 0; m < 1<<5; m++) {
                            arr[0] = getRandom(i).split("");
                            arr[1] = getRandom(j).split("");
                            arr[2] = getRandom(k).split("");
                            arr[3] = getRandom(l).split("");
                            arr[4] = getRandom(m).split("");

                            int tmp = dfs();
                            if(tmp > 6) {
                                for(String[] a : arr) {
                                    for (String s : a)
                                        System.out.print(s);
                                    System.out.println();
                                }
                                System.out.println(tmp+":---------------------------");
                            }
                        }
                    }
                }
            }
        }
    }
}