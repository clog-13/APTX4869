package acwing.enum_simulation;

import java.util.*;

public class 递增三元组_TLE {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        int[][] data = new int[3][N];
        for(int t = 0; t < 3; t++) for(int i = 0; i < N; i++)
            data[t][i] = sc.nextInt();

        Map<Integer, Integer> secMap = new HashMap<>();     // 第二排的数 小于 第三排所有数 的 数量
        for(int i = 0; i < N; i++) {
            if(secMap.containsKey(data[1][i])) continue;
            int tmp = 0;
            for(int j = 0; j < N; j++)
                if(data[2][j] > data[1][i]) tmp++;
            secMap.put(data[1][i], tmp);
        }

        long res = 0;
        Map<Integer, Long> friMap = new HashMap<>();     // 第一排的数 如果小于 第二排的数，去找（第二排的数 小于 第三排所有数 的 数量）
        for(int i = 0; i < N; i++) {
            if(friMap.containsKey(data[0][i])) {
                res += friMap.get(data[0][i]);
                continue;
            }
            long tmp = 0;
            for(int j = 0; j < N; j++) if(data[1][j] > data[0][i])
                tmp += secMap.get(data[1][j]);

            friMap.put(data[0][i], tmp);
            res += tmp;
        }
        System.out.println(res);
    }
}
