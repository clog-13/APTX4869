package acwing.enum_simulation;

import java.util.*;

public class 连号区间数 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] data = new int[N];
        for(int i = 0; i < N; i++)
            data[i] = sc.nextInt();

        int res = N;
        for(int i = 0; i < N-1; i++) {
            int L = data[i], R = data[i];
            int size = 1;
            for(int j = i+1; j < N; j++) {
                L = Math.min(L, data[j]);
                R = Math.max(R, data[j]);
                size++;
                if(R-L+1 == size) res++;
            }
        }
        System.out.println(res);
    }
}
