# 1207. 大臣的旅费

王国修建了大量的快速路，用于连接首都和王国内的各大城市。任何一个大城市都能从首都直接或者通过其他大城市间接到达。

同时，如果不重复经过大城市，从首都到达每个大城市的方案都是唯一的。

J是T国重要大臣，他巡查于各大城市之间，体察民情。

所以，从一个城市马不停蹄地到另一个城市成了J最常做的事情。

他有一个钱袋，用于存放往来城市间的路费。

聪明的J发现，如果不在某个城市停下来修整，在连续行进过程中，他所花的路费与他已走过的距离有关，在走第x千米到第x+1千米这一千米中（x是整数），他花费的路费是x+10这么多。也就是说走1千米花费11，走2千米要花费23。

J大臣想知道：他从某一个城市出发，中间不休息，到达另一个城市，所有可能花费的路费中最多是多少呢？

#### 输入格式

输入的第一行包含一个整数 n，表示包括首都在内的T王国的城市数。

城市从 1 开始依次编号，1 号城市为首都。

接下来 n−1 行，描述T国的高速路（T国的高速路一定是 n−1 条）。

每行三个整数 Pi,Qi,Di，表示城市 Pi 和城市 Qi 之间有一条**双向**高速路，长度为 Di 千米。

#### 输出格式

输出一个整数，表示大臣J最多花费的路费是多少。

#### 数据范围

1≤n≤105, 1≤Pi,Qi≤n, 1≤Di≤1000

#### 输入样例：

```
5 
1  2  2 
1  3  1 
2  4  5 
2  5  4 
```

#### 输出样例：

```
135
```



## 树形DP

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static int maxN = 100010, maxM = maxN * 2;
    static int[] info = new int[maxN];
    static int[] from = new int[maxM], to = new int[maxM],  val = new int[maxM];
    static int idx = 0, res = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());
        Arrays.fill(info,-1);
        for (int i = 0; i < N-1; i++) {
            String[] str = br.readLine().split(" ");
            int a = Integer.parseInt(str[0]);
            int b = Integer.parseInt(str[1]);
            int c = Integer.parseInt(str[2]);
            add(a, b, c); add(b, a, c);
        }
        
        dfs(1, -1);
        System.out.println(res * 11 + ((long)(res - 1) * res ) / 2);
    }
    
    //找到u点往下走的最大长度
    static int dfs(int u, int father) {
        int d1 = 0;//最大值
        int d2 = 0;//次大值
        for (int i = info[u];i != -1;i = from[i]) {
            int t = to[i];
            if (t == father) continue;
            int d = dfs(t, u) + val[i];

            if (d > d1) {
                d2 = d1; 
                d1 = d;
            } else if (d > d2) {
                d2 = d;
            }
        }
        res = Math.max(res, d1 + d2);
        return d1;
    }
    
    static void add(int a,int b,int c) {
        from[idx] = info[a];
        to[idx] = b;
        val[idx] = c;
        info[a] = idx ++;
    }
}
```





## DFS, BFS

```java
import java.util.Arrays;
import java.util.Scanner;

public class Main{
    static int N = 100010,M = 200010;
    static int[] h = new int[N];
    static int[] e = new int[M];
    static int[] ne = new int[M];
    static int[] w = new int[M];
    static int[] dist = new int[N];
    static int idx = 0;
    static void add(int a,int b,int c) {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx ++;
    }
    static void dfs(int u,int father,int distance) {
        dist[u] = distance;  // 联通图是树
        for(int i = h[u];i != -1;i = ne[i]) {
            int j = e[i];
            if(j != father)
                dfs(j,u,distance + w[i]);
        }

    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        Arrays.fill(h,-1);
        for(int i = 0;i < n - 1;i ++) {
            int a = scan.nextInt();
            int b = scan.nextInt();
            int c = scan.nextInt();
            add(a,b,c);
            add(b,a,c);
        }
        //找到任意点x找到距离最远的点y
        dfs(1,-1,0);

        int u = 1;
        for(int i = 2;i <= n;i ++)
            if(dist[i] > dist[u])
                u = i;
        //找到离y最远的点的距离
        dfs(u,-1,0);
        int maxv = dist[1];
        for(int i = 2;i <= n;i ++) {
            if(dist[i] > maxv)
                maxv = dist[i];
        }

        System.out.println(maxv * 10 + ((long)(maxv + 1) * maxv ) / 2);
    }
}
```

```java
public class Main{
    static int N = 100010,M = 200010;
    static int[] h = new int[N];
    static int[] e = new int[M];
    static int[] ne = new int[M];
    static int[] w = new int[M];
    static int[] dist = new int[N];
    static boolean[] st = new boolean[N];//判断该点是否被遍历过
    static int idx = 0;
    static void add(int a,int b,int c)
    {
        e[idx] = b;
        w[idx] = c;
        ne[idx] = h[a];
        h[a] = idx ++;
    }
    static void bfs(int u)
    {
        Queue<Integer> q = new LinkedList<Integer>();
        Arrays.fill(st, false);
        q.add(u);
        dist[u] = 0;
        st[u] = true;
        while(!q.isEmpty())
        {
            int t = q.poll();
            for(int i = h[t];i != -1;i = ne[i])
            {
                int j = e[i];
                if(st[j]) continue;
                dist[j] = dist[t] + w[i];
                st[j] = true;
                q.add(j);
            }
        }
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        Arrays.fill(h,-1);
        for(int i = 0;i < n - 1;i ++)
        {
            int a = scan.nextInt();
            int b = scan.nextInt();
            int c = scan.nextInt();
            add(a,b,c);
            add(b,a,c);
        }
        //找到任意点x找到距离最远的点
        bfs(1);
        int u = 1;
        for(int i = 2;i <= n;i ++)
            if(dist[i] > dist[u])
                u = i;
        //找到离y最远的点的距离
        bfs(u);
        int maxv = dist[1];
        for(int i = 2;i <= n;i ++)
        {
            if(dist[i] > maxv)
                maxv = dist[i];
        }

        System.out.println(maxv * 10 + ((long)(maxv + 1) * maxv ) / 2);

    }
}
```

