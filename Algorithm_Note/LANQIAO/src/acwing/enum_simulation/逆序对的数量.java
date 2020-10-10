package acwing.enum_simulation;

import java.util.*;

public class 逆序对的数量 {
    static int[] data;
    static long res = 0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        data = new int[N];
        for(int i = 0; i < N; i++)
            data[i] = sc.nextInt();

        ms(0, N-1);
        System.out.println(res);
    }

    private static void ms(int le, int ri) {
        if(le >= ri) return;
        int mid = (le + ri) >> 1;
        ms(le, mid); ms(mid+1, ri);
        int i = le, j = mid+1, k = 0;
        int[] tmp = new int[ri-le+1];
        while(i <= mid && j <= ri) {
            if(data[i] <= data[j]) {
                tmp[k++] = data[i++];
            }else {
                res += (mid - i + 1);
                tmp[k++] = data[j++];
            }
        }
        while (i <= mid) tmp[k++] = data[i++];
        while (j <= ri)  tmp[k++] = data[j++];

        for (int ii = le, jj = 0; ii <= ri; ii++, jj++) data[ii] = tmp[jj];
    }
}
