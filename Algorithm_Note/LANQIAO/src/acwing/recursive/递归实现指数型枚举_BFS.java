package acwing.recursive;

import java.util.Scanner;

public class 递归实现指数型枚举_BFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        dfs(1, new StringBuilder(), N);
    }

    private static void dfs(int start, StringBuilder sb, int N) {
        if(start > N) return;
        for(int i = start; i <= N; i++) {
            sb.append(i);
            System.out.println(sb.toString());
            sb.append(" ");
            dfs(i+1, sb, N);
            sb.delete(sb.length()-2, sb.length());
        }
    }
}
