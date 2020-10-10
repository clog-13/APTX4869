package acwing.dichotomy;

import java.util.*;

public class 四平方和_哈希表 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        Map<Long, int[]> maps = new HashMap<>();
        for(int i = 0; i*i <= N; i++) {
            for(int j = i; i*i+j*j <= N; j++) {     // int j = i 剪枝
                if(!maps.containsKey((long)i*i+j*j)) {  // 保存第一个结果
                    maps.put((long)i*i+j*j, new int[]{i, j});
                }
            }
        }

        for(int i = 0; i*i <= N; i++) {
            for(int j = 0; i*i+j*j <= N; j++) {
                long t = N - i*i - j*j;
                if(maps.containsKey(t)) {
                    System.out.printf("%d %d %d %d\n", i, j, maps.get(t)[0], maps.get(t)[1]);
                    return;
                }
            }
        }
    }
}
