# 289. 环路运输

在一条环形公路旁均匀地分布着 N 座仓库，编号为 1∼N，编号为 i 的仓库与编号为 j 的仓库之间的距离定义为 dist(i,j)=min(|i−j|,N−|i−j|)，也就是逆时针或顺时针从 i 到 j 中较近的一种。

每座仓库都存有货物，其中编号为 i 的仓库库存量为 Ai。

在 i 和 j 两座仓库之间运送货物需要的代价为 Ai+Aj+dist(i,j)。

求在哪两座仓库之间运送货物需要的代价最大。

#### 输入格式

第一行包含一个整数 N。

第二行包含 N 个整数 A1∼AN。

#### 输出格式

输出一个整数，表示最大代价。

#### 数据范围

1≤N≤10^6, 1≤Ai≤10^7

#### 输入样例：

```
5
1 8 6 2 5
```

#### 输出样例：

```
15
```



## DP

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), maxN = 2000010;
        int[] arr = new int[maxN], q = new int[maxN];
        for (int i = 1; i <= N; i++) {
            arr[i] = sc.nextInt(); 
            arr[i+N] = arr[i];
        }
        int res = 0, hh = 0, tt = -1, len = N/2;
        for (int i = 1; i <= N*2; i++) {
            if (hh <= tt && q[hh]+len < i) hh++;  // 限定区间在 N 内
            res = Math.max(res, i+arr[i]+arr[q[hh]]-q[hh]);  // arr[i]-i => arr[q[hh]]-a[hh]
            while (hh <= tt && arr[i]-i >= arr[q[tt]]-q[tt]) tt--;  // 最大栈
            q[++tt] = i;
        }
        System.out.println(res);
    }
}
```

