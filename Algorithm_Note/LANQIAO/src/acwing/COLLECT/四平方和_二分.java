package acwing.COLLECT;

import java.util.*;

/**
 * https://www.acwing.com/problem/content/1223/
 * 四平方和定理，又称为拉格朗日定理：
 *
 * 每个正整数都可以表示为至多 4 个正整数的平方和。
 *
 * 如果把 0 包括进去，就正好可以表示为 4 个数的平方和。
 *
 * 比如：
 * 5=0^2+0^2+1^2+2^2
 * 7=1^2+1^2+1^2+2^2
 * 对于一个给定的正整数，可能存在多种平方和的表示法。
 *
 * 要求你对 4 个数排序：
 * 0≤a≤b≤c≤d
 * 并对所有的可能表示法按 a,b,c,d 为联合主键升序排列，最后输出第一个表示法。
 *
 * 输入格式：
 * 输入一个正整数 N。
 *
 * 输出格式：
 * 输出4个非负整数，按从小到大排序，中间用空格分开。
 *
 * 数据范围：
 * 0<N<5∗10^6
 *
 * 输入样例：
 * 5
 * 输出样例：
 * 0 0 1 2
 */
public class 四平方和_二分 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        List<Node> res = new ArrayList<>();
        int idx = 0;
        for (int c = 0; c*c <= N; c++) {
            int cc = c*c;
            for (int d = c; cc+d*d <= N; d++) {
                res.add(new Node(cc+d*d, c, d));
                idx++;
            }
        }

        res.sort((t0, t1) -> {
            if(t0.sum != t1.sum) return t0.sum - t1.sum;
            else if(t0.c != t1.c) return t0.c - t1.c;
            else return t0.d - t1.d;
        });

        for (int a = 0; a*a <= N; a++) {
            int aa = a*a;
            for (int b = 0; aa+b*b <= N; b++) {
                int tar =  N - aa - b*b;
                int le = 0, ri = idx-1;
                while (le < ri) {
                    int mid = (le+ri) >> 1;
                    if (res.get(mid).sum < tar)
                        le = mid + 1;
                    else
                        ri = mid;
                }
                if (res.get(le).sum == tar) {
                    System.out.printf("%d %d %d %d", a, b, res.get(le).c, res.get(le).d);
                    return;
                }
            }
        }
    }
}

class Node{
    public int sum, c, d;
    public Node (int sum, int c, int d) {
        this.sum = sum;
        this.c = c;
        this.d = d;
    }
}

