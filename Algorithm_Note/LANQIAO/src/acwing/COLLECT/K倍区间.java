package acwing.COLLECT;

import java.util.*;

/**
 * https://www.acwing.com/problem/content/1232/
 * 给定一个长度为 N 的数列，A1,A2,…AN，如果其中一段连续的子序列 Ai,Ai+1,…Aj 之和是 K 的倍数，我们就称这个区间 [i,j] 是 K 倍区间。
 *
 * 你能求出数列中总共有多少个 K 倍区间吗？
 *
 * 输入格式：
 * 第一行包含两个整数 N 和 K。
 *
 * 以下 N 行每行包含一个整数 Ai。
 *
 * 输出格式：
 * 输出一个整数，代表 K 倍区间的数目。
 *
 * 数据范围：
 * 1≤N,K≤100000,
 * 1≤Ai≤100000
 *
 * 输入样例：
 * 5 2
 * 1
 * 2
 * 3
 * 4
 * 5
 * 输出样例：
 * 6
 */
public class K倍区间 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        long[] pre_sum = new long[100010];
        long[] cout = new long[100010];
        for (int i = 1; i <= N; i++)
            pre_sum[i] = pre_sum[i-1] + (long)sc.nextInt();

        long res = 0;
        cout[0] = 1;
        for (int i = 1; i <= N; i++) {
            res += cout[(int) (pre_sum[i] % K)];    // 再次得到 pre % N == 某数时，说明期间有 序列 % N == 0;
            cout[(int) (pre_sum[i] % K)]++;
        }
        System.out.println(res);
    }
}