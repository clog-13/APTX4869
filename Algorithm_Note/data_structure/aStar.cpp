#include <iostream>
#include <cstring>
#include <queue>
#include <unordered_map>
#include <algorithm>

using namespace std;
typedef pair<int, string> PIS;

unordered_map<string, int> dist;
unordered_map<string, pair<string, char>> pre;
priority_queue<PIS, vector<PIS>, greater<PIS>> heap;
string ed = "12345678x";
int dx[4] = {-1, 0, 1, 0};
int dy[4] = { 0, 1, 0,-1};
char dir[] = "urdl";

int getDist(string state) {   // 求估值函数,这里是曼哈顿距离
    int res = 0;
    for(int u = 0; u < 9; u++) {
        if(state[u] != 'x') {
            int c = state[u] - '1';
            res += abs(c/3-u/3) + abs(c%3-u%3);
        }
    }
    return res;
}

string bfs(string start) {
    heap.push({getDist(start) , start});
    dist[start] = 0;

    while (heap.size()) {
        auto cur = heap.top();
        heap.pop();

        string state = cur.second;
        if(state == ed) break;  // 如果到达终点就break
        int step = dist[state]; // 记录到达state的实际距离

        int k = state.find('x');// 确定当前 'x' 的位置
        int x = k / 3 , y = k % 3;

        string backup = state;  // 因为在下面state会变，所以留一个备份
        for (int i = 0; i < 4; i++) {
            int tx = x + dx[i], ty = y + dy[i];
            if (tx >= 0 && tx < 3 && ty >= 0 && ty < 3) {
                swap(state[x*3+y], state[tx*3+ty]);

                if (!dist.count(state) || dist[state] > step + 1) {
                    dist[state] = step + 1;
                    pre[state] = {backup, dir[i]};
                    heap.push({dist[state] + getDist(state), state});
                }
                swap(state[x * 3 + y], state[tx * 3 + ty]);   // 因为要多次交换，所以要恢复现场
            }
        }

    }

    string res;
    while (ed != start) {
        res += pre[ed].second;
        ed = pre[ed].first;
    }
    reverse(res.begin() , res.end());
    return res;
}

int main() {
    string init;
    char c;
    for (int i = 0; i < 9; i++) {
        cin >> c;
        init += c;
    }

    int cnt = 0;
    for(int i = 0; i < 9; i++) {
        if (init[i] == 'x') continue;
        for(int j = i+1; j < 8; j++) {
            if (init[j] == 'x') continue;
            if(init[i] > init[j]) cnt++;             
        }
    }

    if(cnt % 2) puts("unsolvable");
    else cout<<bfs(init)<<endl;

    return 0;
}