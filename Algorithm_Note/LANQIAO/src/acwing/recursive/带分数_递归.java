package acwing.recursive;

import java.util.Scanner;

public class 带分数_递归 {
    static int N = 0, res = 0;
    static int[] used = new int[10];
    static int[] backups = new int[10];
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        a_dfs(0);
        System.out.println(res);
    }

    private static void a_dfs(int a) {
        if(a >= N) return;
        if(a > 0) c_dfs(a, 0);
        for(int i = 1; i <= 9; i++) {
            if(used[i] == 0) {
                used[i] = 1;
                a_dfs(a*10+i);
                used[i] = 0;
            }
        }
    }

    private static void c_dfs(int a, int c) {
        if(check(a, c)) res++;

        for(int i = 1; i <= 9; i++) {
            if(used[i] == 0) {
                used[i] = 1;
                c_dfs(a, c*10+i);
                used[i] = 0;
            }
        }
    }

    private static boolean check(int a, int c) {
        int b =  c*N - c*a;
        if(a <= 0 || b <= 0 || c <= 0) return false;
        System.arraycopy(used, 0, backups, 0, used.length);

        int tmp;
        while(b > 0) {
            tmp = b%10;
            if(tmp == 0) return false;
            b /= 10;
            if(backups[tmp] == 1) return false;
            backups[tmp] = 1;
        }

        for(int i = 1; i < used.length; i++)
            if(backups[i] == 0) return false;

        return true;
    }
}
