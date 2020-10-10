package template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Bellman_Ford {
    static int N = 510;
    static int M = 10010;
    static int INF = 0x3f3f3f3f;

    static int n, m, k;   // 最多经过k条边
    static int[] dist = new int[N];
    static Node[] list = new Node[M];   // 结构体
    static int[] back = new int[N];     // 备份dist数组

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] str = reader.readLine().split(" ");
        n = Integer.parseInt(str[0]);
        m = Integer.parseInt(str[1]);
        k = Integer.parseInt(str[2]);
        for (int i = 0; i < m; i++) {
            String[] arr = reader.readLine().split(" ");
            int from = Integer.parseInt(arr[0]);
            int to = Integer.parseInt(arr[1]);
            int dis = Integer.parseInt(arr[2]);
            list[i] = new Node(from, to, dis);
        }
        bellman_ford();
    }

    private static void bellman_ford() {
        Arrays.fill(dist, INF);

        dist[1] = 0;
        for(int i = 0; i < k; i++) {
            back = Arrays.copyOf(dist, n+1);
            for(int j = 0; j < m; j++) {
                Node node = list[j];
                int from = node.from;
                int to = node.to;
                int dis = node.dis;
                // 当前从起始点到from的距离加dis 是否能更新 to点
                dist[to] = Math.min(dist[to], back[from] + dis);
            }
        }

        if(dist[n] > INF>>1) System.out.println("impossible");
        else System.out.println(dist[n]);
    }

    private static class Node {
        int from, to, dis;
        public Node(int a, int to, int dis) {
            this.from = a;
            this.to = to;
            this.dis = dis;
        }
    }
}

