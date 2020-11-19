#include <cstring>
#include <iostream>
#include <algorithm>
#include <queue>
#include <vector>

#define x first
#define y second

using namespace std;

typedef pair<int, int> PII;

const int maxN = 25;

struct Node {
    int x, y, dir;
};

int N, M;
int dx[4] = {1, -1, 0, 0}, dy[4] = {0, 0, 1, -1};  // 依次表示下、上、右、左四个方向

char arr[maxN][maxN];   // 存储游戏地图
bool st_box[maxN][maxN][4], st_man[maxN][maxN];   // BFS的判重数组,为了防止BFS遍历相同状态
PII dist[maxN][maxN][4];  // dist[j][k][i]是表示从初始状态到达j,k,i状态所需要的 箱子 最短路程和 人 行走最短路程
int pre_man[maxN][maxN];  // pre_man[x][y]表示人从哪个方向走到(x, y)，如果pre_man[x][y] = i, 那么上一个状态是(x-dx[i], y-dy[i])
Node pre_box[maxN][maxN][4];  // 表示箱子在(x, y)，上一个格子在i方向上的状态，即上一个状态是(x+dx[i], y+dy[i])
vector<int> path[maxN][maxN][4];  // path[j][k][i] 表示人从推(j,k,i)的上一个状态的位置，走到推(j,k,i)这个状态的位置的行走路径


bool check(int x, int y) {
    return x >= 0 && x < N && y >= 0 && y < M && arr[x][y] != '#';
}

// 求人从start走到end，中间不经过box的最短路径，行走序列保存在seq中
int bfs_man(PII start, PII end, PII box, vector<int> &seq) {
    memset(st_man, false, sizeof st_man);
    memset(pre_man, -1, sizeof pre_man);

    queue<PII> queue;
    queue.push(start);
    st_man[start.first][start.second] = true;
    st_man[box.first][box.second] = true;

    while (queue.size()) {
        auto cur = queue.front();
        queue.pop();

        if (cur == end) {
            seq.clear();
            int x = cur.first, y = cur.second;
            while (pre_man[x][y] != -1) {
                int dir = pre_man[x][y] ^ 1;
                seq.push_back(dir);
                x += dx[dir], y += dy[dir];
            }

            return seq.size();
        }

        for (int ii = 0; ii < 4; ii ++ )
        {
            int i = ii ^ 1;
            int x = cur.first, y = cur.second;
            int a = x + dx[i], b = y + dy[i];
            if (check(a, b) && !st_man[a][b])
            {
                st_man[a][b] = true;
                pre_man[a][b] = i;
                queue.push({a, b});
            }
        }
    }

    return -1;
}

bool bfs_box(PII man, PII box, Node &end) {
    memset(st_box, false, sizeof st_box);

    queue<Node> queue;
    for (int i = 0; i < 4; i ++ ) { // 初始化,第一次推动
        int x = box.first, y = box.second;
        int tx = x + dx[i], ty = y + dy[i];
        int px = x - dx[i], py = y - dy[i];
        vector<int> seq;

        if (check(tx, ty) && check(px, py) && bfs_man(man, {tx, ty}, box, seq) != -1) {
            st_box[px][py][i] = true;
            queue.push({px, py, i});
            dist[px][py][i] = {1, seq.size()};
            path[px][py][i] = seq;
            pre_box[px][py][i] = {x, y, -1};
        }
    }

    bool success = false;
    PII man_d = {1e9, 1e9};
    while (queue.size()) {
        auto cur = queue.front(); queue.pop();

        if (arr[cur.x][cur.y] == 'T') {
            success = true;

            if (dist[cur.x][cur.y][cur.dir] < man_d) {
                man_d = dist[cur.x][cur.y][cur.dir];
                end = cur;
            }
        }

        for (int i = 0; i < 4; i++) {
            int tx = cur.x + dx[i], ty = cur.y + dy[i];
            int px = cur.x - dx[i], py = cur.y - dy[i];
            if (check(tx, ty) && check(px, py)) {
                vector<int> seq;
                auto &p = dist[px][py][i];
                int distance = bfs_man({cur.x+dx[cur.dir],cur.y+dy[cur.dir]}, {tx,ty}, {cur.x,cur.y}, seq);
                if (distance != -1) {
                    PII td = {dist[cur.x][cur.y][cur.dir].x+1, dist[cur.x][cur.y][cur.dir].y+distance};
                    if (!st_box[px][py][i]) {
                        st_box[px][py][i] = true;
                        queue.push({px, py, i});
                        path[px][py][i] = seq;
                        pre_box[px][py][i] = cur;
                        p = td;
                    } else if (p > td) {
                        p = td;
                        path[px][py][i] = seq;
                        pre_box[px][py][i] = cur;
                    }
                }
            }
        }
    }

    return success;
}

int main() {
    int T = 1;
    while (cin >> N >> M, N || M) {
        printf("Maze #%d\N", T ++ );
        
        for (int i = 0; i < N; i ++ ) cin >> arr[i];
        PII man, box;
        for (int i = 0; i < N; i ++ ) {
            for (int j = 0; j < M; j ++ ) {
                if (arr[i][j] == 'S') man = {i, j};
                if (arr[i][j] == 'B') box = {i, j};
            }
        }
        
        Node end;
        if (!bfs_box(man, box, end)) puts("Impossible.");
        else {
            char ops[] = "nswe";
            string res;
            while (end.dir != -1) {
                res += ops[end.dir] - 32;
                for (auto dir : path[end.x][end.y][end.dir]) res += ops[dir];
                end = pre_box[end.x][end.y][end.dir];
            }
            reverse(res.begin(), res.end());
            cout<<res<<endl;
        }
        puts("");
    }
    return 0;
}