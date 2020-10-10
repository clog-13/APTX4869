package acwing.recursive;

import java.util.Scanner;

public class 递归实现指数型枚举_递归 {
    static int[] data = new int[20];
    static int N;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        for(int i = 1; i <= N; i++)
            recursive(1, 1, i);
    }



    private static void recursive(int start, int pos, int end) {
        if(pos > end) {
            for(int i = 1; i <= end-1; i++)
                System.out.print(data[i] + " ");
            System.out.println(data[end]);
            return;
        }

        for(int i = start; i <= N; i++) {
            data[pos] = i;
            recursive(i+1, pos+1, end);
        }
    }
}
