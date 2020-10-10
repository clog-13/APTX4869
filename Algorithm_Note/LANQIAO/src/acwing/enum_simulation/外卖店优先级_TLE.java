package acwing.enum_simulation;

import java.util.*;

public class 外卖店优先级_TLE {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();       // 店家 数
        int M = sc.nextInt();       // 消息 数 （时间 ：　店家）
        int T = sc.nextInt();       // 时间

        Map<Integer, Integer>[] data = new HashMap[T+1];
        for(int i = 0; i <= T; i++) data[i] = new HashMap<>();
        for(int i = 0; i < M; i++) {
            int t = sc.nextInt();
            int id = sc.nextInt();
            data[t].put(id, data[t].getOrDefault(id, 0) + 2);
        }

        int res = 0;
        int[] tmp = new int[N+1];
        boolean[] cur = new boolean[N+1];
        for(int i = 1; i <= T; i++) {        // 遍历时间
            for(int j = 1; j <= N; j++) {    // 遍历所有店家
                if(data[i].containsKey(j)) tmp[j] += data[i].get(j);
                else tmp[j] = Math.max(0, tmp[j]-1);
                if(!cur[j] && tmp[j] > 5) {
                    cur[j] = true;
                    res++;
                }
                if(cur[j] && tmp[j] <= 3) {
                    cur[j] = false;
                    res--;
                }
            }
        }

//        for(int i = 1; i <= N; i++) if(cur[i]) res++;
        System.out.println(res);
    }
}
