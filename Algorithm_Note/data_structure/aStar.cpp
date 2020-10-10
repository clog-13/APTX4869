#include <bits/stdc++.h>

using namespace std;

struct edge {   // edge代表边的结构体
    int e, v;
};

struct node {   // A*内每个节点的node结构体
    long long st,l,f;
    bool operator<(const node&a) const {    // 重载小于号规定优先队列的排序为l+f(p)(也就是当前的总长度与估价函数值的和)
        return a.l+a.f < l+f;
    }
};

vector<edge> G[1005],GG[1005];  // 定义两个存图的vector,G是正向的,GG是逆向的.因为是有向图所以逆向SPFA需要用逆向存图的GG
const int inf = 0x3f3f3f3f;  // SPFA用的inf
int main() {
    long long dis[1005], bk[1005];   // dis代表SPFA的单源最短路的记录,bk是SPFA过程中的辅助数组
    int n, m;
    scanf("%d%d", &n, &m);
    while (m--) {
        int s, e, v;
        scanf("%d%d%d", &s, &e, &v);
        G[s].push_back((edge){e, v});//模拟邻接表存图
        GG[e].push_back((edge){s, v});
    }
    int st, ed, k;
    scanf("%d%d%d", &st, &ed, &k);

    // 逆向SPFA
    for (int i = 1; i <= n; i++) dis[i] = i == ed ? 0 : inf;
    queue<int> q;
    q.push(ed);
    while (q.size()) {
        int cur = q.front(); 
        q.pop();
        bk[cur] = 0;
        for (int i = 0; i < GG[cur].size(); i++) {
            if (dis[GG[cur][i].e] > dis[cur]+GG[cur][i].v) {
                dis[GG[cur][i].e] = dis[cur]+GG[cur][i].v;
                if (!bk[GG[cur][i].e]) {
                    bk[GG[cur][i].e] = 1;
                    q.push(GG[cur][i].e);
                }
            }
        }
    }
    
    for (int i = 0; i <= n; i++) printf("%d\n", dis[i]);

    // aStar
    if (dis[st] == inf) return printf("-1"), 0;
    priority_queue<node> Q;
    Q.push((node){st, 0, 0});
    int cntt = 0;
    if (ed == st) k++;
    while (Q.size()) {
        node p = Q.top();
        Q.pop();
        if (p.st == ed) {
          cntt++;
          if (cntt == k) return printf("%lld", p.l), 0;
        }
        for (int i = 0; i < G[p.st].size(); i++) {
            Q.push((node){G[p.st][i].e, G[p.st][i].v+p.l, dis[G[p.st][i].e]});
        }
    }
    printf("-1");
}