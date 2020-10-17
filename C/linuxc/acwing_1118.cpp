#include<iostream>
#include<cstring>
using namespace std;

const int maxN = 10;
int N, res = maxN;//由于是求最小值，这里初始化为最大值
int arr[maxN];//n个整数

int dp[maxN][maxN];//分组
int st[maxN];

int gcd(int a, int b) {
    return b ? gcd(b, a%b) : a;
}

bool check(int g[], int x, int gc) {
    for (int i = 0; i < gc; i++) {
        if (gcd(g[i], x) > 1) return false;
    }
    return true;
}

// 当前要放在哪个分组；要放在该分组的第几个位置；从哪个位置开始选择元素【组合套路(定一个遍历顺序)】；当前已分组完毕的元素个数
void dfs(int gr, int gc, int start, int cnt) {
    
    if (cnt == N) {
        res = gr; // 因为是贪心，所以 gr 先到的是最小的
        return;
    }
    bool flag = true; 
    if (gr >= res) return; // 剪枝 + 防止死循环

    for (int i = start; i < N; i++) {  // 从start开始找，是否有元素不能放到gr组中
        if (!st[i] && check(dp[gr], arr[i], gc)) {
            dp[gr][gc] = arr[i];
            st[i] = true;
            dfs(gr, gc+1, i+1, cnt+1);
            st[i] = false;
            flag = false;            
        }
    }
    // 新开一个分组

    // 由于dfs每层之间确定了顺序，所以期间是会有元素被漏掉的，【比如一开始你找的一串序列(1)是1,2,3,4 但是第二次(2)是1,3,4 很显然此时
    // (2)还有一个3没有得到分组，需要从start=0开始再把它找出来！  因此这样做仿佛有点浪费时间呢！！】

    // 因此当所有元素都不能放进当前分组的时候 或者 当start=n-1了但是元素没有全部分组完毕时，要重新从start=0开始找，并且一定要有st数组！！！不然会把一个元素重复的分组！
    if (flag) dfs(gr+1, 0, 0, cnt);

}

int main() {
    cin>>N;
    for (int i = 0; i < N; i++) cin>>arr[i];

    // 为什么一开始gr从1开始但是分组只开了10个呢？
    // 首先这样的话可以直接通过gr就得到当前分了多少组；其次由于res初始化即为10，因此当打算开第10个分组时，会被弹回，数组不会越界
    dfs(1, 0, 0, 0);

    cout<<res;
    return 0;
}