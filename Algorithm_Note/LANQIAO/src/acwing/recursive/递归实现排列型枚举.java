package acwing.recursive;

import java.util.*;

public class 递归实现排列型枚举 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        dfs(new ArrayList<>(), 0, N);
    }

    private static void dfs(List<Integer> list, int vis, int N) {
        if(list.size() == N) {
            for(int i = 0; i < list.size()-1; i++)
                System.out.print(list.get(i)+" ");
            System.out.println(list.get(list.size()-1));
            return;
        }

        for(int i = 1; i <= N; i++) {
            if((vis>>(i-1) & 1) == 1) continue;
            list.add(i);
            vis |= 1<<(i-1);
            dfs(list, vis, N);
            list.remove(list.size()-1);
            vis ^= 1<<(i-1);
        }
    }

}
