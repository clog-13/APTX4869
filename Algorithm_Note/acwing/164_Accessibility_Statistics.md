# 164. 可达性统计

给定一张N个点M条边的有向无环图，分别统计从每个点出发能够到达的点的数量。

**输入格式**

第一行两个整数N,M，接下来M行每行两个整数x,y，表示从x到y的一条有向边。

**输出格式**

输出共N行，表示每个点能够到达的点的数量。

**数据范围**

1≤N,M≤30000

**输入样例：**

```
10 10
3 8
2 3
2 5
5 9
5 9
2 3
3 9
4 8
2 10
4 9
```

**输出样例：**

```
1
6
3
3
2
1
1
1
1
1
```

## 拓扑排序 + BitSet()

```java
import java.io.*;
import java.util.*;

public class Main {
    int idx, qidx, N, M, maxN = 30010;
    int[] info = new int[maxN], from = new int[maxN], to = new int[maxN];
    int[] qv = new int[maxN], cout = new int[maxN];
    BitSet[] dist = new BitSet[maxN];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]); M = Integer.parseInt(str[1]);

        Arrays.fill(info, -1);
        while (M-- > 0) {
            str = br.readLine().split(" ");
            int a = Integer.parseInt(str[0]), b = Integer.parseInt(str[1]);
            add(b, a);  // 有向边（连一条反向边）
            cout[a]++;  // topsort,从尾部反向搜索
        }

        topsort();

        for (int i = 1; i <= N; i++) {
            dist[i] = new BitSet();
            dist[i].set(i);
        }
        for (int i = 0; i < N; i++) {  // 遍历qv，（从叶子节点向上）
            int cur = qv[i];
            for (int j = info[cur]; j != -1; j = from[j]) {	// 去遍历当前节点所有可达的下层节点
                int t = to[j];
                dist[t].or(dist[cur]);
            }
        }

        for (int i = 1; i <= N; i++) {
            System.out.println(dist[i].cardinality());
        }
    }

    void topsort() {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if (cout[i] == 0) {  // 无环，必有0
                queue.add(i);
                qv[qidx++] = i;
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int i = info[cur]; i != -1; i = from[i]) {
                int t = to[i];
                cout[t]--;
                if (cout[t] == 0) {
                    queue.add(t);
                    qv[qidx++] = t;
                }
            }
        }
    }

    void add(int a, int b) {
        from[idx] = info[a];
        to[idx] = b;
        info[a] = idx++;
    }
}
```

