#include <bits/stdc++.h>
using namespace std;
const int N=110;
int n,x[N];
bool sum[N];
int dfs(int now,int deep,int last)//当前now深度,deep最大深度,last为上一次的数值,我们要满足数列的单调递增性.
{
    if (x[now]==n)//找到了!!!
        return now;
    if (now>=deep)//如果已经超过深度了
        return 0;
    for(int i=now;i>=1;i--)
        for(int j=i;j>=1;j--)//记得要j<=i 不然会TLE
            if (!sum[x[i]+x[j]] && x[i]+x[j]>=last)//满足条件
            {
                x[now+1]=x[i]+x[j];
                sum[x[i]+x[j]]=true;
                int sm=dfs(now+1,deep,x[i]+x[j]);//下一步
                if (sm)
                    return sm;
                sum[x[i]+x[j]]=false;
                x[now+1]=0;
            } else if (!sum[x[i]+x[j]])//后面的肯定都不可能了,因为单调递增性不满足了.
                break;
    return 0;
}
int main() {
    while(cin>>n && n) {
        x[1]=1;
        x[2]=2;
        int s,k=n;
        if (n>2)
        {
            if (n>20)//高端玄学优化
                k=6;
            else
                k=3;
            for(;k<=10;k++)
            {
                memset(sum,0,sizeof(sum));/记得初始化
                memset(x,0,sizeof(x));
                x[1]=1;
                x[2]=2;
                s=dfs(2,k,3);
                if (s!=0) 
                    break;
            }
        }
        for(int i=1;i<=k;i++)
            cout<<x[i]<<" ";
        cout<<endl;
    }
    return 0;
}