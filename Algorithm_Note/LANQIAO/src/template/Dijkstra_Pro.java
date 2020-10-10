package template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Dijkstra_Pro {
    static int INF = 0x3f3f3f3f, maxN = 1000010;
    static int N, M, idx = 0;

    static int[] info = new int[maxN]; // 按 from节点 来储存当前边的识别 ID
    static int[] from = new int[maxN]; // 按 ID 来储存 当前 from节点的上一个 ID
    static int[] to = new int[maxN];   // 按 ID 来储存 to节点
    static int[] graph = new int[maxN];// 按 ID 来储存 dist

    static int[] dist = new int[maxN]; // 存储1号点到每个点的最短距离
    static boolean[] vis = new boolean[maxN];

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

        System.out.println(dijkstra());
    }

    private static int dijkstra() {
        Arrays.fill(dist, INF);
        dist[1] = 0;
        // 维护当前未在 vis 中标记过且离源点最近的点
        PriorityQueue<PII> queue = new PriorityQueue<>((a, b) -> (a.dis - b.dis));  // 最小堆
        queue.add(new PII(1, 0));
        while(!queue.isEmpty()) {
            PII p = queue.poll();
            int cur = p.id;
            if(vis[cur]) continue;
            vis[cur] = true;

            for(int i = info[cur]; i != -1; i = from[i]) {   // 3、用 cur 更新其他点的距离
                int t = to[i];
                if (dist[t] > dist[cur] + graph[i]) {
                    dist[t] = dist[cur] + graph[i];     // dist[cur] == p.dis;
                    queue.add(new PII(t, dist[t]));
                }
            }
        }
        return dist[N] == INF ? -1 : dist[N];
    }

    private static void add(int a, int b, int c) {
        from[idx] = info[a];
        to[idx] = b;
        graph[idx] = c;
        info[a] = idx++;
    }

     private static class PII {
        int id; // 点编号
        int dis;  // 距离

        public PII(int id, int dis) {
            this.id = id;
            this.dis = dis;
        }
    }
}

