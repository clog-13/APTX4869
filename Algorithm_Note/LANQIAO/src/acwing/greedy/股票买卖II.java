package acwing.greedy;

import java.util.Scanner;

public class 股票买卖II {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] data = new int[N];
        for(int i = 0; i < N; i++)
            data[i] = sc.nextInt();

//        int res = 0;
//        for(int i = 1; i < N; i++) {
//            int tmp = data[i] - data[i-1];
//            if(tmp > 0) res += tmp;
//        }
//        System.out.println(res);

        int min = data[0], pre = data[0];
        int res = 0;
        for(int i = 1; i < N; i++) {
            if(data[i] < pre) {
                res += (pre - min);
                min = data[i];
            }else if(data[i] < min) {
                min = data[i];
            }

            pre = data[i];
        }
        res += (pre-min);
        System.out.println(res);
    }


}
