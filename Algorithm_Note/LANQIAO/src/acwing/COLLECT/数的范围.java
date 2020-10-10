package acwing.COLLECT;

import java.util.Scanner;

public class 数的范围 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int T = sc.nextInt();

        int[] arr = new int[N];
        for(int i = 0; i < N; i++)
             arr[i] = sc.nextInt();

        for(int ti = 0; ti < T; ti++) {
            int s = sc.nextInt();
            int le = 0, ri = arr.length-1;

            // 求左端点，返回le
            while(le < ri) {
                int mid = (le+ri) >> 1;
                if(arr[mid] < s)
                    le = mid+1;
                else
                    ri = mid;
            }

            if(arr[le] == s) {
                System.out.print(le+" ");
                ri = arr.length-1;

                // 求右端点，返回ri
                while(le < ri) {
                    int mid = (le+ri+1) >> 1;
                    if(arr[mid] <= s)
                        le = mid;
                    else
                        ri = mid-1;
                }
                System.out.println(ri);
            }else System.out.println((-1) + " " + (-1));
        }
    }
}