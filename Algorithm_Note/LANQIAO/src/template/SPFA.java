package template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class SPFA {
    static int maxN = 200010;
    static int[] from = new int[maxN];  // 记录 上一个有关 a 的无向边 的 ID
    static int[] to = new int[maxN];    // 记录当前 ID 下 a 的 邻边
    static int[] v = new int[maxN];
    static int[] info = new int[maxN];  // 对每一组无向边，以 a 来记录每组的 ID
    static int[] dist = new int[maxN];
    static boolean[] inQueue = new boolean[maxN];

    static int N, M, idx = 0, INF = 0x3f3f3f3f;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] str = reader.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        M = Integer.parseInt(str[1]);
        Arrays.fill(info, -1);

        while (M-- > 0) {
            str = reader.readLine().split(" ");
            int a = Integer.parseInt(str[0]);
            int b = Integer.parseInt(str[1]);
            int c = Integer.parseInt(str[2]);
            add(a, b, c);
        }

        int res = spfs();
        if (res == INF) System.out.println("impossible");
        else System.out.println(res);
    }

    private static int spfs() {
        Arrays.fill(dist, INF);
        dist[1] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            inQueue[cur] = false;
            for (int i = info[cur]; i != -1; i = from[i]) {
                int t = to[i];
                if (dist[t] > dist[cur] + v[i]) {
                    dist[t] = dist[cur] + v[i];
                    if (!inQueue[t]) {
                        queue.add(t);
                        inQueue[t] = true;
                    }
                }
            }
        }

        return dist[N];
    }

    private static void add(int a, int b, int c) {
        from[idx] = info[a];
        to[idx] = b;
        v[idx] = c;
        info[a] = idx++;
    }
}
