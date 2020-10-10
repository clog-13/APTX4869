package acwing.recursive;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class 递归实现组合型枚举_递归 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        dfs(1, new ArrayList<>(), 0, N, M);
    }

    private static void dfs(int start, List<Integer> list, int vis, int N, int M) {
        if(start + (M-list.size())-1 > N)  return;
//        if(list.size()+1 + N - start < M) return;
        if(list.size() == M) {
            for (int i = 0; i < list.size() - 1; i++)
                System.out.print(list.get(i) + " ");
            System.out.println(list.get(list.size() - 1));
            return;
        }

        for(int i = start; i <= N; i++) {
            if((vis>>(i-1) & 1) == 1) continue;

            list.add(i);
            vis |= 1<<(i-1);

            dfs(i+1, list, vis, N, M);

            vis ^= 1<<(i-1);
            list.remove(list.size()-1);
        }
    }
}
