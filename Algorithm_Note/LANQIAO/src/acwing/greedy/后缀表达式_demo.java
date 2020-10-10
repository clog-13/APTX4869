package acwing.greedy;

import java.util.*;

public class 后缀表达式_demo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()) {
            long N = sc.nextInt();
            long M = sc.nextInt();
            long total = N+M+1;
            long Z = 0, res = 0;
            long[] data = new long[(int) total];
            for(int i = 0; i < total; i++) {
                data[i] = sc.nextInt();
                res += data[i];
                if(data[i] < 0) Z++;
            }
            Arrays.sort(data);

            if(M > 0) {     // 有 负号
                if(Z > 0) { // 有 负数
                    if(Z == total) {    // 全部是负数，可以把 total-1个数转为正 (-a) - ((-b) + (-c) + ...) - (-e) - (-f) - ...
                        for(int i = 0; i < total-1; i++)
                            res -= data[i]*2;
                    }else {             // 可以把负数全部转为正， 同上 a - ((-b) + (-c) + ...) - (-e) - ... - (-f) + g + ... + h
                        for(int i = 0; i < Z; i++)
                            res -= data[i]*2;
                    }
                }else {     // 没有 负数， 但是有 负号（则减去最小正数就行， a+...+c-(d-...-f)）
                    res -= data[0]*2;
                }
            }

            System.out.println(res);
        }
    }
}