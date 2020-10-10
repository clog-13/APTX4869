public class Tmp {
    public static void main(String[] args) {
        System.out.println(c(26,7));
        System.out.println(c(26,6));
        System.out.println(c(26,5));
    }
    static long c(int n, int m) {
        long temp = 1;
        for (int i = 1; i <= m; ++i)
            temp = temp * (n - m + i) / i;
        return temp;
    }
}
