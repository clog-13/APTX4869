# 1536. 均分纸牌

有N堆纸牌，编号分别为 1,2,…,N。

每堆上有若干张，但纸牌总数必为 N 的倍数。

可以在任一堆上取若干张纸牌，然后移动。

移牌规则为：在编号为 1 的堆上取的纸牌，只能移到编号为 2 的堆上；在编号为 N 的堆上取的纸牌，只能移到编号为 N−1 的堆上；其他堆上取的纸牌，可以移到相邻左边或右边的堆上。

现在要求找出一种移动方法，用最少的移动次数使每堆上纸牌数都一样多。

例如 N=4，4 堆纸牌数分别为：(9,8,17,6)。

移动 3 次可达到目的：

1. 从第三堆取四张牌放入第四堆，各堆纸牌数量变为:(9,8,13,10)。
2. 从第三堆取三张牌放入第二堆，各堆纸牌数量变为:(9,11,10,10)。
3. 从第二堆取一张牌放入第一堆，各堆纸牌数量变为:(10,10,10,10)。

#### 输入格式

第一行包含整数 N。

第二行包含 N 个整数，A1,A2,…,AN 表示各堆的纸牌数量。

#### 输出格式

输出使得所有堆的纸牌数量都相等所需的最少移动次数。

#### 数据范围

1≤N≤100, 1≤Ai≤10000

#### 输入样例：

```
4
9 8 17 6
```

#### 输出样例：

```
3
```



## 贪心+前缀

```java
import java.util.*;
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), sum = 0, res = 0;
        int[] arr = new int[N+1];
        for (int i = 1; i <= N; i++) {
            arr[i] = sc.nextInt();
            sum += arr[i];
        }
        int avg = sum / N;
        for (int i = 1; i <= N; i++) {
            if (arr[i] != avg) {  
                res++;  // i之前的累计状态是不平衡的，最后的方案会从i经过一次
                arr[i+1] += arr[i] - avg;  // 累计状态
            }
        }
        System.out.println(res);
    }
}
```

```c++
#include <iostream>
using namespace std;
const int maxN = 110;
int res, N, arr[maxN], preSum[maxN];

int main() {
    cin >> N;
    for (int i = 1; i <= N ; i++) {
        cin >> arr[i];
        preSum[i] = preSum[i - 1] + arr[i];
    }
    for (int i = 1; i <= N; i++) {
        if (preSum[i] == i*preSum[N] / N) continue;
        res++;
    }
    cout << res;
    return 0;
}
```
