package acwing.greedy;

import java.util.*;

public class 后缀表达式_sample {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int add = sc.nextInt();
        int sub = sc.nextInt();
        int total = add + sub + 1;
        int[] data = new int[total];
        for (int i = 0; i < total; i++) data[i] = sc.nextInt();

        long res = 0;
        if (sub == 0) {     // 如果没有 负号
            for(int i = 0; i < total; i++) res += data[i];
        } else {
            Arrays.sort(data);

            // 有至少一个 负数 不能用 负号 变成 正数
            res = data[total-1]-data[0];        // 这里一定要这么写（而且很巧妙）
            for(int i = 1; i < total-1; i++) res += Math.abs(data[i]);
        }
        System.out.println(res);
    }
}
