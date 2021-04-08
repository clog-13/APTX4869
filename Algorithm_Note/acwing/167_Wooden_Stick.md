# 167. 木棒

乔治拿来一组等长的木棒，将它们随机地砍断，使得每一节木棍的长度都不超过50个长度单位。

然后他又想把这些木棍恢复到为裁截前的状态，但忘记了初始时有多少木棒以及木棒的初始长度。

请你设计一个程序，帮助乔治计算木棒的可能最小长度。

每一节木棍的长度都用大于零的整数表示。

#### 输入格式

输入包含多组数据，每组数据包括两行。

第一行是一个不超过64的整数，表示砍断之后共有多少节木棍。

第二行是截断以后，所得到的各节木棍的长度。

在最后一组数据之后，是一个零。

#### 输出格式

为每组数据，分别输出原始木棒的可能最小长度，每组数据占一行。

#### 数据范围

数据保证每一节木棍的长度均不大于50。

#### 输入样例：

```
9
5 2 1 5 2 1 5 2 1
4
1 2 3 4
0
```

#### 输出样例：

```
6
5
```

## DFS剪枝

```java
import java.util.*;

public class Main {
    static int N, sum, tar;
    static int[] arr;
    static boolean[] vis = new boolean[70];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            N = sc.nextInt(); if (N == 0) break;
            arr = new int[N];
            Arrays.fill(vis, false);

            sum = 0;
            for (int i = 0; i < N; i++) {
                arr[i] = sc.nextInt();
                sum += arr[i];
            }
            Arrays.sort(arr);

            tar = 1;
            while (true) {
                if (sum%tar == 0 && dfs(0, 0, N-1)) {  // 第0根木棒, 当前拼接长度是0, 第0根开始
                    System.out.println(tar);
                    break;
                }
                tar++;
            }
        }
    }

    private static boolean dfs(int cnt, int curLen, int start) {  // 第0根木棒, 当前拼接长度是0, 第0根开始
        if (cnt * tar == sum) return true;
        if (curLen == tar) return dfs(cnt+1, 0, N-1);

        for (int i = start; i >= 0; i--) {  // 剪枝, i从start开始枚举
            if (vis[i]) continue;
            if (curLen+arr[i] > tar) continue;  // 剪枝, 可行性剪枝

            vis[i] = true;  // 剪枝， 数组排序后，搜索顺序剪枝（先大后小）
            if (dfs(cnt, curLen+arr[i], i-1)) return true;  // 枚举当前木棒的小棍拼接方案
            vis[i] = false;

            // 剪枝, 如果是木棒的第一根小棍失败了，则一定失败（因为数组排序的，按顺序枚举，而每根小棍必须用到）
            if (curLen == 0) return false;    
            if (curLen+arr[i] == tar) return false; // 剪枝, 因为当前tar其他木棒不行

            int j = i;  // 剪枝, 如果当前小棍加入后失败了，则略过后面一样长度的小棍（传递性）
            while (j >= 0 && arr[i] == arr[j]) j--;  
            i = j+1;
        }
        return false;
    }
}
```

## 桶排序+剪枝

```java
#include<bits/stdc++.h>
using namespace std;

const int maxN = 60; // 木棒长度不超过 50 ，所以用桶排序空间上也是优的 
int N, sum, len, maxn;
int cout[maxN];

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
                printf("%d\n",sum); // 只能全部拼成一根
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
```

