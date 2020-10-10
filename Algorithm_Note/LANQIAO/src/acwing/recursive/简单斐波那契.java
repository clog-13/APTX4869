package acwing.recursive;

import java.util.Scanner;

public class 简单斐波那契 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int cur = 0, before = 1;
        for(int i = 0; i < N; i++) {
            System.out.print(cur+" ");
            cur += before;
            before = cur - before;
        }
    }
}
