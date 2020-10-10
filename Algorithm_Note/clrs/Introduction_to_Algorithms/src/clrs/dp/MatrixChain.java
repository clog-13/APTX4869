package clrs.dp;

public class MatrixChain {
    static int[] p = {30, 35, 15, 5, 10, 20, 25};
    static int[][] dp, srr;

    public static void main(String[] args) {
        MatrixChain(p);
        PrintOptimal(1, 6);
//        System.out.println(MatrixChain(p)[1][6]);
//        for(int[] i : dp){
//            for(int j : i)
//                System.out.printf("%5d, ", j);
//            System.out.println();
//        }


    }

    public static void MatrixChain(int[] p) {
        int N = p.length-1;
        dp = new int[N+1][N+1];
        srr = new int[N][N+1];
        for(int len = 2; len <= N; len++) {
            for(int s = 1; s <= N-len+1; s++) {
                int end = s+len-1;
                dp[s][end] = Integer.MAX_VALUE;
                for(int mid = s; mid <= end-1; mid++) {
                    int min = dp[s][mid] + dp[mid+1][end] + (p[s-1]*p[mid]*p[end]);
                    if(min < dp[s][end]){
                        dp[s][end] = min;
                        srr[s][end] = mid;
                    }
                }
            }
        }
    }

    public static void PrintOptimal(int s, int e) {
        if(s == e) System.out.print("A"+s);
        else{
            System.out.print("(");
            PrintOptimal(s, srr[s][e]);
            PrintOptimal(srr[s][e]+1, e);
            System.out.print(")");
        }
    }

}
