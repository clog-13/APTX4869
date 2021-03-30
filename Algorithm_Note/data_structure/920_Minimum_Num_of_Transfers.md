# 920. 最优乘车

H 城是一个旅游胜地，每年都有成千上万的人前来观光。

为方便游客，巴士公司在各个旅游景点及宾馆，饭店等地都设置了巴士站并开通了一些单程巴士线路。

每条单程巴士线路从某个巴士站出发，依次途经若干个巴士站，最终到达终点巴士站。

一名旅客最近到 H 城旅游，他很想去 S 公园游玩，但如果从他所在的饭店没有一路巴士可以直接到达 S 公园，则他可能要先乘某一路巴士坐几站，再下来换乘同一站台的另一路巴士，这样换乘几次后到达 S 公园。

现在用整数 1,2,…N 给 H 城的所有的巴士站编号，约定这名旅客所在饭店的巴士站编号为 1，S 公园巴士站的编号为 N。

写一个程序，帮助这名旅客寻找一个最优乘车方案，使他在从饭店乘车到 S 公园的过程中换乘的次数最少。

#### 输入格式

第一行有两个数字 M 和 N，表示开通了 M 条单程巴士线路，总共有 N 个车站。

从第二行到第 M+1 行依次给出了第 1 条到第 M 条巴士线路的信息，其中第 i+1 行给出的是第 i 条巴士线路的信息，从左至右按运行顺序依次给出了该线路上的所有站号，相邻两个站号之间用一个空格隔开。

#### 输出格式

共一行，如果无法乘巴士从饭店到达 S 公园，则输出 `NO`，否则输出最少换乘次数，换乘次数为 0 表示不需换车即可到达。

#### 数据范围

1≤M≤100, 1≤N≤500

#### 输入样例：

```
3 7
6 7
4 7 3 6
2 1 3 5
```

#### 输出样例：

```
2
```



## BFS

```java
import java.io.*;
import java.util.*;

class Main {
    static int maxN = 510;
    static int N, M;
    
    static boolean[][] graph = new boolean[maxN][maxN];
    static int[] dist=  new int[maxN];
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        M = Integer.parseInt(str[0]);
        N = Integer.parseInt(str[1]);
        
        while (M-- > 0) {
            str = br.readLine().split(" ");
            for (int i = 0; i < str.length; i++) {
                for (int j = i+1; j < str.length; j++) {  // 公交线路里的边是有向边
                    graph[Integer.parseInt(str[i])][Integer.parseInt(str[j])] = true;
                    // graph[Integer.parseInt(str[j])][Integer.parseInt(str[i])] = true;
                }
            }
        }
        
        bfs();
        
        if (dist[N] == -1) System.out.println("NO");
        else System.out.println(dist[N] - 1);
    }
    
    private static void bfs() {
        Arrays.fill(dist, -1);
        dist[1] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int i = 1; i <= N; i++) {
                if (!graph[cur][i]) continue;  // a到b需要换乘 （M - cur可达i的线路数）  次
                if (dist[i] != -1) continue;
                dist[i] = dist[cur] + 1; 
                queue.add(i);
            }
        }
    }
}
```



## DFS

```java
#include <iostream>
#include <algorithm>
#include <queue>
#include <cstring>
#include <utility>
using namespace std ;
typedef pair<int,int> PII ;
const int N=510 ;
int m,n ;
bool st[N][N] ;
int stops[N][N] ;
vector<PII> transfer[N] ;
/*
1.dfs传入参数pos和bus,表示公交车的编号与这辆公交车的站数
2.如果到了终点则返回
3.决策1：如果没有到最后一站则继续坐这辆公交车；决策2：判断这一站是否为换乘站，如果是则换乘其他公交车

4.取决策1, 决策2的最小值，返回函数
*/
int dfs(int pos,int bus) {                       //1
    st[pos][bus]=true ;
    if(stops[pos][bus]==n) return 0 ;            //2
    int res=2e9 ;
    if(stops[pos+1][bus]) res=dfs(pos+1,bus) ;   //3-1
    for(auto t:transfer[stops[pos][bus]]) {      //3-2
        int fir=t.first ;
        int sec=t.second ;
        if(st[fir][sec]) continue ;
        res=min(res,dfs(fir,sec)+1) ;
    }
    return res ;                                 //4
}
int main() {
    scanf("%d%d",&m,&n) ;
    string str ;
    getline(cin,str) ;
    PII res ;
    for(int i=1;i<=m;i++){
        int cnt=1 ;
        string str ;
        getline(cin,str) ;
        for(auto ch:str) {
            if(ch==' ') {
                transfer[stops[cnt][i]].push_back({cnt,i}) ;  // transfer[n]:n站的bus信息
                if(stops[cnt][i]==1) res={cnt,i} ;
                cnt++ ;
            }
            else stops[cnt][i]=stops[cnt][i]*10+ch-'0';  // i路公交 第cnt站
        }
    }
    
    int ans=dfs(res.first, res.second) ;
    
    if(ans==2e9) puts("NO") ;
    else printf("%d",ans) ;
    return 0 ;
}
```

