package acwing.greedy;

import java.util.*;

public class 货仓选址 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        if(N == 1) {
            System.out.println(sc.nextInt());
            return;
        }

        int[] data = new int[N];
        for (int i = 0; i < N; i++)
            data[i] = sc.nextInt();

        Arrays.sort(data);
        int res = 0;
        for(int i = 1; i < N; i++)
            res += data[i] - data[0];   // res初始表示的是"累加状态"
        for (int idx = data[0]+1; idx <= data[N-1]; idx++) {   // 仓库选址下标
            int ti = 0, le = 0;     // 从data数组第一个依次查看， le表示现在选址左边的仓库数
            for (; data[ti] < idx; ti++)    // 当前下标左右的仓库数
                le++;
            res = Math.min(res, res - (N-le) + le);
        }
        System.out.println(res);
    }
}
