package acwing.COLLECT;

import java.util.*;

public class 飞行员兄弟_枚举 {
    static int[][] arr = new int[4][4];
    static int[][] backups = new int[4][4];
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        for(int i = 0; i < 4; i++) {
            String tmp = sc.nextLine();
            for(int j = 0; j < 4; j++)
                arr[i][j] = (tmp.charAt(j) == '+' ? 0 : 1);
        }

        List<int[]> res = new ArrayList<>();
        for(int i = 0; i < 1<<16; i++) {

            for(int j = 0; j < 4; j++) for(int k = 0; k < 4; k++)   // 备份
                    backups[j][k] = arr[j][k];

            List<int[]> tmp = new ArrayList<>();
            for(int j = 0; j < 4; j++) for(int k = 0; k < 4; k++)
                if((i >> (j*4+k) & 1) == 1) {
                    tmp.add(new int[]{j, k});
                    turn_all(j, k);
                }

            boolean has_closed = false;
            for(int j = 0; j < 4; j++) for(int k = 0; k < 4; k++)
                if(arr[j][k] == 0)
                    has_closed = true;

            if(!has_closed)
                if(res.isEmpty() || res.size() > tmp.size()) res = new ArrayList<>(tmp);

            for(int j = 0; j < 4; j++) for(int k = 0; k < 4; k++)   // 还原备份
                arr[j][k] = backups[j][k];

        }

        System.out.println(res.size());
        for(int i = 0; i < res.size(); i++)
            System.out.println((res.get(i)[0]+1)+" "+(res.get(i)[1]+1));
    }

    private static void turn_all(int x, int y) {
        for(int i = 0; i < 4; i++) {
            turn_one(i, y);
            turn_one(x, i);
        }
        turn_one(x, y);
    }

    private static void turn_one(int x, int y) {
        if(arr[x][y] == 1) arr[x][y] = 0;
        else arr[x][y] = 1;
    }
}
