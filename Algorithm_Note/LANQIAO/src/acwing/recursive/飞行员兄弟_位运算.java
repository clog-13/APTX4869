package acwing.recursive;

import java.util.*;

public class 飞行员兄弟_位运算 {
    static int[][] change = new int[4][4];
    public static void main(String[] args) {
        for(int i = 0; i < 4; i++) {        // 初始化 翻转辅助表
            for(int j = 0; j < 4; j++) {
                for(int k = 0; k < 4; k++) {
                    change[i][j] += (1 << (i*4+k)) + (1 << (k*4+j));
                }
                change[i][j] -= (1 << (i*4+j));
            }
        }

        Scanner sc = new Scanner(System.in);
        int state = 0;
        for(int i = 0; i < 4; i++) {
            String tmp = sc.nextLine();
            for(int j = 0; j < 4; j++)
                if(tmp.charAt(j) == '+') state += 1 << (i*4+j);
        }

        List<int[]> res = new ArrayList<>();
        for(int op = 0; op < 1<<16; op++) {     // 枚举
            int cur = state;
            List<int[]> tmp = new ArrayList<>();
            for(int i = 0; i < 4; i++) {       // 翻转不要求顺序
                for(int j = 0; j < 4; j++) {
                    if((op>>(i*4+j) & 1) == 1) {
                        cur ^= change[i][j];
                        tmp.add(new int[]{i, j});
                    }
                }
            }
            if(cur == 0 && (res.isEmpty() || res.size() > tmp.size())) res = new ArrayList<>(tmp);
        }
        System.out.println(res.size());
        for(int i = 0; i < res.size(); i++)
            System.out.println((res.get(i)[0]+1)+" "+(res.get(i)[1]+1));
    }
}
