import java.util.*;

class Draft {
    static int N, W, cnt = 0;
    static long res = 0;
    static long[] arr;
    static boolean[] vis = new boolean[50];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        W = sc.nextInt();
        N = sc.nextInt();
        arr = new long[N];
        for (int i = 0; i < N; i++) arr[i] = (long) sc.nextInt();
        Arrays.sort(arr);

        for (int i = N-1; i >= 0; i--) {
            if (!vis[i]) {
                vis[i] = true;
                cnt += dfs(i, arr[i]);
                res++;
            }
            if (cnt == N) {
                System.out.println(res);
                return;
            }
        }
    }

    private static int dfs(int start, long sum) {
        int c = 1;
        for (int i = start-1; i >= 0; i--) {
            if (!vis[i] && sum+arr[i] <= W) {
                vis[i] = true;
                sum += arr[i];
                c++;
            }
        }
        return c;
    }
}