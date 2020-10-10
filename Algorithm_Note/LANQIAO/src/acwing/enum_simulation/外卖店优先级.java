package acwing.enum_simulation;

import java.util.*;

class Pair {
    int t, id;
    public Pair(int t, int id) {
        this.t = t;
        this.id = id;
    }
}
// 1,  9, 1, 6, 0, 6, 2, 5, 3, 0, 2, 4, 0, 1, 0, 0, 0, 1, 5, 0,
// 2, 10, 2, 8, 2, 7, 2, 7, 4, 2, 2, 4, 2, 2, 2, 2, 2, 2, 6, 8,
public class 外卖店优先级 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int T = sc.nextInt();

        Pair[] data = new Pair[M];
        for(int i = 0; i < M; i++)
            data[i] = new Pair(sc.nextInt(), sc.nextInt());
        Arrays.sort(data, (p1, p2) -> {
            if(p1.t != p2.t) return p1.t - p2.t;
            else return p1.id - p2.id;
        });

        int[] last = new int[N+1];
        int[] score = new int[N+1];
        boolean[] status = new boolean[N+1];
        for(int i = 0; i < M; ) {
            int j = i;
            while(j < M && data[i].t == data[j].t && data[i].id == data[j].id) j++;
            int t = data[i].t, id = data[i].id, cnt = j - i;
            i = j;

            score[id] -= (t - last[id] - 1);
            if(score[id] < 0) score[id] = 0;

            if(score[id] <= 3) status[id] = false;
            score[id] += cnt*2;
            if(score[id] > 5) status[id] = true;
            last[id] = t;
        }

        for(int i = 1; i <= N; i++) {
            System.out.print(score[i]+", ");
            if(last[i] < T) {
                score[i] -= T - last[i];
                if(score[i] <= 3) status[i] = false;
            }
        }
        int res = 0;
        for(int i = 1; i <= N; i++)
            if(status[i]) res++;
        System.out.println("\n"+res);
    }
}