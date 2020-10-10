package tmp;

public class FastTools {

    public static void main(String[] args) {
        System.out.println(fast_pow(2, 5));
    }

    static int fast_pow(int a, int n) {
        int res = 1;
        while(n > 0) {
            if (n%2 == 1)
                res *= a;
            a *= a;
            n >>= 1;
        }
        return res;
    }

    static long fast_mul(long a, long b, long n) {
        long res = 0;
        while(b > 0) {
            long t = b&1;
            if(t == 1) res = (res+a) % n;
            a = (a+a)%n;
            b>>=1;
        }
        return res;
    }
}
