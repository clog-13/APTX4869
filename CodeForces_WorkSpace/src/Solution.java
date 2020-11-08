import java.net.Socket;
import java.util.*;

class Solution {

    public static void main(String[] args) {
        String t = "1.0.1.";
        String[] str = t.split("\\.", -1);
        String[] str1 = t.split("\\.");

        System.out.println();

        Solution sol = new Solution();
        int[] arr = {497978859,167261111,483575207,591815159};  // 373219333
        System.out.println(sol.maxProfit(arr, 836556809));
    }

    public int maxProfit(int[] arr, int orders) {
        int N = arr.length, MOD = 1000000007;

        Arrays.sort(arr);
        long res = 0;
        while (true) {
            int le = N-1, ri = N-1;
            while (le > 0 && arr[le-1] == arr[ri]) le--;    // 宽
            int w = ri-le+1;
            int nex = le == 0 ? 0 : arr[le-1];
            int h = arr[le]-nex;

            if (orders <= w*h) {
                int th = orders/w;

                long t = (arr[ri]%MOD-th+1)%MOD;
                long tmp = (th*(t+arr[ri]%MOD)%MOD) % MOD;
                res = (res + ((tmp/2)%MOD * w)%MOD) % MOD;
                orders -= w*th;
                if (orders != 0) for (int j = le; j <= ri; j++) arr[j] -= th;
                for (int i = 0; i < orders; i++) res = (res + arr[ri]) % MOD;
                break;
            } else {
                long t = (nex+1+arr[ri])%MOD;
                long tmp = (h*t) % MOD;
                res = (res + ((tmp/2)%MOD * w)%MOD) % MOD;
                orders -= w*h;
                for (int i = le; i <= ri; i++) arr[i] = nex;
            }
        }

        return (int)res%MOD;
    }
}