import java.util.*;

class Solution {
    public static void main(String[] args) {
        Solution sol = new Solution();
//        sol.maxValue(6, 1, 10);
//        sol.maxValue(4, 0, 4);
//        sol.maxValue(9, 3, 16);
        sol.maxValue(1, 0, 24);
    }

    public int maxValue(int n, int idx, int maxSum) {
        int le = 0, ri = maxSum+1;
        while (le < ri) {
            int mid = (le+ri)>>1;
            if (check((long)mid, (long)n, (long)idx) <= (long)maxSum) {
                le = mid+1;
            } else {
                ri = mid;
            }
        }
        return le-1;
    }

    long check(long x, long n, long idx) {
        if (x==0) return 0;
        long lc = idx+1, rc = n-idx;
        long ls = 0, rs = 0;
        if (x > lc) {
            ls = lc*(x-lc+1 + x) / 2;
        } else if (x <= lc) {
            ls = x*(1 + x) / 2;
            ls += idx-x+1;
        }

        if (x > rc) {
            rs = rc*(x-rc+1 + x) / 2;
        } else if (x <= rc) {
            rs = x*(1 + x) / 2;
            rs += n-idx-x;
        }
        long sum = ls + rs - x;
        return sum;
    }
}