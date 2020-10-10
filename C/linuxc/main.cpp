#include"stdio.h"
#include"string.h"
#include"deque"
#include"algorithm"

using namespace std;
#define INF 0x3f3f3f3f
typedef pair<int,int> PII;

const int dir1[4][2] = {{1,1},{-1,-1},{-1,1},{1,-1}};
const int dir2[4][2] = {{1,1},{0,0},{0,1},{1,0}};
int T, R, C;
char Graph[510][510];
int vis[510][510];
int dp[510][510];

int check(int tx, int ty) {
    if(tx >= 0 && tx <= R && ty >= 0 && ty <= C) return 0;
    else return 1;
}

void bfs() {
    deque<PII> queue;
    queue.push_front({0,0});
    dp[0][0] = 0;
    while(!queue.empty()) {
        PII tmp = queue.front(); queue.pop_front();
        int x = tmp.first, y = tmp.second;
        if (vis[x][y]) continue;
        vis[x][y] = 1;
        for (int i = 0; i < 4; i++) {
            int tx = x + dir1[i][0], ty = y + dir1[i][1];
            int a = x + dir2[i][0],b = y + dir2[i][1];
            int time = (Graph[a][b] != (i <= 1 ? '\\' : '/'));
            if(check(tx, ty) || dp[tx][ty] <= dp[x][y] + time) continue;
            dp[tx][ty] = dp[x][y] + time;

            if(time == 0) queue.push_front({tx,ty});
            else queue.push_back({tx,ty});
        }
    }
}

int main() {
    scanf("%d", &T);
    while(T--) {
        scanf("%d%d", &R, &C);
        memset(vis, 0, sizeof(vis));
        for (int i = 0; i <= R; i++)
            for (int j = 0; j <= C; j++)
                dp[i][j] = INF;
        for (int i = 1; i <= R; i++)
            scanf("%s", Graph[i] + 1);  // 每行从下标 1 开始
            
        bfs();

        if(dp[R][C] == INF)
            printf("NO SOLUTION\n");
        else
            printf("%d\n",dp[R][C]);
    }
}