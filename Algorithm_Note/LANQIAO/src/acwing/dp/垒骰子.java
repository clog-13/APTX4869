package acwing.dp;

import java.util.Arrays;
import java.util.Scanner;

public class 垒骰子 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        int[] data = new int[7];
        Arrays.fill(data, 5);
        for(int i = 0; i < M; i++) {
            data[sc.nextInt()]--;
            data[sc.nextInt()]--;
        }

        int[] res = new int[7];
//        for(int i = 1; i <= 6; i++) {
//            int tmp = data[i];
//            for(int i = )
//
//        }

    }
}
