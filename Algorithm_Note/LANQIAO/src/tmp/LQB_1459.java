import java.util.Scanner;

public class LQB_1459 {
    static int[] F = new int[1000010];
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        for (int i = 1; i < F.length; i++)
            F[i] = i;
        for(int i = 0; i < N; i++) {
            int k = sc.nextInt();
            k = find(k);
            System.out.printf("%d ", k);
            F[k] = find(k+1);
        }
    }
    private static int find(int x) {
        if(F[x] == x)
            return x;
        else {
            F[x] = find(F[x]);
            return F[x];
        }
    }
}
