# 340. 通信线路

在郊区有 N 座通信基站，P 条 **双向** 电缆，第 i 条电缆连接基站 Ai 和 Bi。

特别地，1 号基站是通信公司的总站，N 号基站位于一座农场中。

现在，农场主希望对通信线路进行升级，其中升级第 i 条电缆需要花费 Li。

电话公司正在举行优惠活动。

农产主可以指定一条从 1 号基站到 N 号基站的路径，并指定路径上不超过 K 条电缆，由电话公司免费提供升级服务。

农场主只需要支付在该路径上剩余的电缆中，升级价格最贵的那条电缆的花费即可。

求至少用多少钱可以完成升级。

#### 输入格式

第 1 行：三个整数 N，P，K。

第 2..P+1 行：第 i+1 行包含三个整数 Ai,Bi,Li。

#### 输出格式

包含一个整数表示最少花费。

若 1 号基站与 N 号基站之间不存在路径，则输出 −1。

#### 数据范围

0≤K<N≤1000, 1≤P≤10000, 1≤Li≤1000000

#### 输入样例：

```
5 7 1
1 2 5
3 1 4
2 4 8
3 2 3
5 2 9
3 4 7
4 5 6
```

#### 输出样例：

```
4
```



## 最短路 + DP

```java
import java.util.*;
import java.io.*;

class Main {
    int N, M, K, idx, maxN = 1010, maxM = 30010, INF = 0x3f3f3f3f;
    int[] info = new int[maxN];
    int[] from = new int[maxM], to = new int[maxM], val = new int[maxM];
    int[][] dist = new int[maxN][maxN];
    boolean[] vis = new boolean[maxN];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]); M = Integer.parseInt(str[1]);
        K = Integer.parseInt(str[2]);

        Arrays.fill(info, -1);
        while (M-- > 0) {
            str = br.readLine().split(" ");
            int a = Integer.parseInt(str[0]), b = Integer.parseInt(str[1]);
            int c = Integer.parseInt(str[2]);
            add(a, b, c); add(b, a, c);
        }

        spfa();

        if (dist[N][K] == INF) System.out.println("-1");
        else System.out.println(dist[N][K]);
    }

    void spfa() {
        for (int[] ff: dist) Arrays.fill(ff, INF);
        dist[1][0] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            vis[cur] = false;
            for (int i = info[cur]; i != -1; i = from[i]) {
                // dist[cur][c-1] 新加入的边免费升级,
                // max(dist[cur][c], val[i]) 新加入的边需要掏钱的,
                // 所以就要和前面走过的边所用的费用进行对比,选取中最大的费用;
                // (升级价格最贵的那条电缆的花费即可), 同时, c越大,dis只会越小
                // min = Math.min(当前边免费, 当前边不免费)
                int t = to[i];
                for (int c = 0; c <= K; c++) {  // 遍历免费电缆状态
                    int min;
                    if (c > 0) min = Math.min(dist[cur][c-1], Math.max(dist[cur][c], val[i]));
                    else min = Math.max(dist[cur][0], val[i]);
                    if (dist[t][c] > min) {
                        dist[t][c] = min;  // dis[i][j]: 到i点, 其中j条免费时 的(最贵一条)最小费用
                        if (!vis[t]) {
                            vis[t] = true;
                            queue.add(t);
                        }
                    }
                }
            }
        }
    }

    void add(int a, int b, int c) {
        from[idx] = info[a];
        to[idx] = b;
        val[idx] = c;
        info[a] = idx++;
    }
}
```



## 二分("迭代加深")

```java
#include <iostream>
#include <cstring>
#include <deque>
#include <algorithm>
using namespace std;

const int N = 1010 , M = 20010;

int e[M] , ne[M] , w[M] , h[N] , idx;
int dist[N];
int n , m , k;
bool st[N];

void add(int a , int b , int c) {
    e[idx] = b , ne[idx] = h[a] , w[idx] = c , h[a] = idx++;
}

bool check(int bound) {
    memset(dist , 0x3f , sizeof dist);
    memset(st , 0 , sizeof st);

    dist[1] = 0;
    deque<int> q;
    q.push_back(1);

    while (q.size()) {
        int t = q.front(); q.pop_front();
        if (st[t]) continue;
        st[t] = true;
        for (int i = h[t] ; ~i ; i = ne[i]) {
            int j = e[i] , x = w[i] > bound;
            if (dist[j] > dist[t] + x) {
                dist[j] = dist[t] + x;
                if (x) q.push_back(j);
                else q.push_front(j);
            }
        }
    }
    return dist[n] <= k;  // 大于 x 的边数 小于等于 k
}

int main() {
    cin >> n >> m >> k;

    memset(h , -1 , sizeof h);
    while (m--) {
        int a , b , c;
        cin >> a >> b >> c;
        add(a , b , c) , add(b , a , c);
    }

    int l = 0 , r = 1e6 + 1;
    while (l < r) {
        int mid = l + r >> 1;
        if(check(mid)) r = mid;  // mid:最高花费
        else l = mid + 1;
    }

    if (r == 1e6 + 1) r = -1;
    cout << r << endl;
    return 0;
}
```



## 分层图 最短路

```java
#include<bits/stdc++.h>
using namespace std;

const int N=1000000+10,M=10000000+10;

int n,p,k,tot=0;
priority_queue<pair<int ,int>> q;
struct node {
    int ver,nex,val;
}po[M];
int head[N],dis[N];
bool v[N];

void add(int x,int y,int z) {
    po[++tot].ver=y,po[tot].val=z;
    po[tot].nex=head[x],head[x]=tot;
}

void dijkstra() {
    memset(dis,0x3f,sizeof(dis));
    dis[1]=0;
    q.push(make_pair(0,1));
    while (q.size()) {
        int x=q.top().second; q.pop();
        if(v[x]) continue;
        v[x]=true;
        for (int i=head[x]; i; i=po[i].nex) {
            int y=po[i].ver,z=max(po[i].val,dis[x]);
            if (dis[y]>z) {
                dis[y]=z;
                q.push(make_pair(-dis[y],y));
            }
        }
    }
}

int main() {
    scanf("%d%d%d",&n,&p,&k);
    for (int i=1,x,y,z; i<=p; i++) {
        scanf("%d%d%d",&x,&y,&z);
        
        add(x,y,z); add(y,x,z);
        for (int j=1; j<=k; j++) {
            add(x+(j-1)*n, y+j*n, 0);  // 向下一层连 0 边
            add(x+j*n,     y+j*n, z);  // 层内联通
            
            add(y+(j-1)*n, x+j*n, 0);
            add(y+j*n,     x+j*n, z);
        }
    }
    
    // 考虑 1 到 N 经过的边小于 k
    for (int i=1; i<=k; i++) add(i*n, (i+1)*n, 0);  // 向自己下一层连一条邻边

    dijkstra();
    if (dis[(k+1)*n]==0x3f3f3f3f) puts("-1");
    else printf("%d",dis[(k+1)*n]);
    return 0;
}
```

