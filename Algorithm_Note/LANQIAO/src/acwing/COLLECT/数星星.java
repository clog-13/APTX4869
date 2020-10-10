package acwing.COLLECT;

import java.util.Scanner;

public class 数星星 {
    static int[] bit = new int[32010], level = new int[32010];
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        // bit[]: 横坐标等于x的星星数量 的前缀和
        // level[]:某一等级的星星 的数量
        for(int i = 0; i < N; i++) {
            int x = sc.nextInt(), y = sc.nextInt();
            x++;
            level[getSum(x)]++; // x处前有 getSum() 个, 所以等级为 x 的星星数量加一 (!!!星星按 y 坐标增序给出,y 坐标相同的按 x 坐标增序给出)
            update(x);
        }
        for(int i = 0; i < N; i++)
            System.out.println(level[i]);
    }

    private static int lowbit(int x) {
        return x & (-x);
    }

    private static void update(int idx) {
        while(idx <= 32010) {
            bit[idx]++;
            idx += lowbit(idx);
        }
    }

    private static int getSum(int idx) {
        int res = 0;
        for(; idx > 0; idx -= lowbit(idx)) res += bit[idx];
        return res;
    }
}
