#include<bits/stdc++.h>

using namespace std;

const int maxN = 60; // 木棒长度不超过 50 ，所以用桶排序空间上也是优的 

int read() {
    static int x; x = 0;
    char ch; ch = getchar();
    while (ch<'0' || ch>'9') ch = getchar();
    while (ch>='0' && ch<='9') {
        x = (x<<1)+(x<<3)+ch-'0';
        ch = getchar();
    }
    return x;
}

int N, sum, len, maxn;
int cout[maxN];

void clear() {
    sum = 0; len = 0; maxn = 0;
    memset(cout, 0, sizeof(cout));
}

// rest 指还需要拼接出几根木棍
// cur 指当前拼接出木棍的长度
// po 是指针指,指向向当前可取最长木棒的下标,也是长度
bool dfs(int rest, int cur, int po) {
    if (rest == 0) return true;
    if (cur == len) return dfs(rest-1, 0, maxn);   // 因为是从小到大枚举，所以一旦有解即为最优
    for (int i = po; i >= 0; i--) { // 剪枝[3]:当前木棍拼接完整，但拼接接下来的木棍失败，立即回溯
        if (cout[i] && cur+i <= len) {   // 要一直枚举到 0 ，不然边界无返回值
            //由于是桶排序，当前长度的木棒不行时就直接跳到下一长度了 
            cout[i]--;
            if (dfs(rest, cur+i, i)) return true;
            cout[i]++;   //还原
            
            if (cur==0 || cur+i==len) return false;
            /*剪枝 [3] : 当前拼接的木棍尝试拼入第一根木棒就失败
              或拼入一根木棒后，木棍恰好拼接完整，但接下来的拼接却失败
              一句话，遇到拼接失败就立即回溯
            */
        }
    }
}

int main() {
    while (1) {
        N = read();
        if (N == 0) break;
        clear();
        for (int i = 1, x; i <= N; i++) {
            x = read();
            if (x > 50) { 
                i--,N--; 
            } else {
                cout[x]++; sum += x;
                maxn = max(maxn, x); // 剪枝[1],[2]:桶排序有效地解决了相同长度的木棒具有等效性问题
            }
        }
        for (int i = maxn; i <= sum; i++) {
            // 剪枝[4]:从小到大枚举（木棍最短为最长的木棒） 
            if (i*2 > sum) {
                printf("%d\n",sum); // 省去一些不必要的枚举
                break; 
            }
            if (sum % i) continue;
            len = i;
            if (dfs(sum/i, 0, maxn)) {
                printf("%d\n", len);
                break;
            }
        }
    }
    return 0;
}