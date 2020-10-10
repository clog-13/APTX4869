package acwing.recursive;

import java.util.*;


// TODO --WA
public class 递归实现组合型枚举_非递归 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        int[] A = new int[N+1];
        int[] points = new int[M+1];
        for(int i = 1; i <= N; i++) {
            if(i < points.length) points[i] = i;
            A[i] = i;
        }

        int base = 0;
        for(int h = 0; h <= N-M; h++) {
            points[0] = base++;  // 找 base 开头的排列
            for(int pi = 1; pi <= M; pi++)
                points[pi] = points[pi-1]+1;

            show(points, A);    // 输出基本状态

            // 把第一个窗口后面的窗口依次移到最后，并打印
            int le = base+M-1, ri = N;  // le：最左边的窗口， ri：目标坐标 (坐标信息储存在points[]里)
            if(le == ri) continue;
            for(int i = 0; i < M-1; i++) {                  // 移动的块数
                for(int j = 0; j < ri-le; j++) {
                    points[M-i]++;
                    show(points, A);
                }
                ri--;
                le--;
            }
        }

//        int[] data = new int[3];
//        for(int i = 1; i <= N; i++) {
//            data[0] = i;
//            for(int j = i+1; j <= N; j++) {
//                data[1] = j;
//                for(int k = j+1; k <= N; k++) {
//                    data[2] = k;
//                    for(int d : data) System.out.print(d+" ");
//                    System.out.println();
//                }
//            }
//        }

    }
    private static void show(int[] p, int[] data) {
        for(int i = 1; i < p.length-1; i++)
            System.out.print(data[p[i]] + " ");
        System.out.println(data[p[p.length-1]]);
    }
}
