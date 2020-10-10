import java.io.*;
import java.util.Arrays;

public class LQB_1461 {
    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        in.nextToken();
        int G = (int) in.nval;
        for(int g = 0; g < G; g++) {
            in.nextToken();
            int N = (int) in.nval;
            long[] data = new long[N+1];
            for(int i = 1; i <= N; i++) {
                in.nextToken();
                data[i] = (long) in.nval;
                data[i] += data[i-1];
            }
            long s0 = data[0], sn = data[N];
            if(s0 > sn) {
                long tmp = s0;
                s0 = sn;
                sn = tmp;
            }
            Arrays.sort(data);
            for(int i = 0; i <= N; i++) {
                if(data[i] == s0) {
                    s0 = i;
                    break;
                }
            }
            for(int i = N; i >= 0; i--) {
                if(data[i] == sn) {
                    sn = i;
                    break;
                }
            }
            long[] list = new long[N+1];
            boolean[] vis = new boolean[N+1];
            int le = 0, ri = N;
            for(int i = (int)s0; i >= 0; i-=2) {
                list[le++] = data[i];
                vis[i] = true;
            }
            for(int i = (int)sn; i <= N; i+=2) {
                list[ri--] = data[i];
                vis[i] = true;
            }
            for(int i = 0; i <= N; i++) {
                if(vis[i] == false) {
                    list[le++] = data[i];
                }
            }
            long res = Long.MIN_VALUE;
            for(int i = 1; i <= N; i++)
                res = Math.max(res, Math.abs(list[i]-list[i-1]));
            out.println(res);
            out.flush();
        }
    }
}