package acwing.dichotomy;

import java.util.Scanner;

public class 数的三次方根 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double n = sc.nextDouble();

        double le = -10000.0, ri = 10000.0;
        while(ri-le > 1e-8) {
            double mid = (le + ri) / 2;
            if(mid*mid*mid >= n) ri = mid;
            else le = mid;
        }
        System.out.printf("%.6f", le);
    }
}
