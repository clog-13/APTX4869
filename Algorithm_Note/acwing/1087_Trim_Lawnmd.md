# 1087. 修剪草坪

FJ 的草坪非常脏乱，因此，FJ 只能够让他的奶牛来完成这项工作。

FJ 有 N 只排成一排的奶牛，编号为 1 到 N。

每只奶牛的效率是不同的，奶牛 i 的效率为 Ei。

编号相邻的奶牛们很熟悉，如果 FJ 安排超过 K 只编号连续的奶牛，那么这些奶牛就会罢工去开派对。

因此，现在 FJ 需要你的帮助，找到最合理的安排方案并计算 FJ 可以得到的最大效率。

注意，方案需满足不能包含超过 K 只编号连续的奶牛。

#### 输入格式

第一行：空格隔开的两个整数 N 和 K；

第二到 N+1 行：第 i+1 行有一个整数 Ei。

#### 输出格式

共一行，包含一个数值，表示 FJ 可以得到的最大的效率值。

#### 数据范围

1≤N≤10^5^, 0≤Ei≤10^9^

#### 输入样例：

```
5 2
1
2
3
4
5
```

#### 输出样例：

```
12
```

#### 样例解释

FJ 有 5 只奶牛，效率分别为 1、2、3、4、5。

FJ 希望选取的奶牛效率总和最大，但是他不能选取超过 2 只连续的奶牛。

因此可以选择第三只以外的其他奶牛，总的效率为 1 + 2 + 4 + 5 = 12。



## 单调队列

转换成连续M+1头牛里，至少有一只没选，求最小的 没选总值

```java
import java.util.*;
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), M = sc.nextInt();
        int[] arr = new int[N+1], q = new int[N+1];
        long sum = 0, res = Long.MAX_VALUE;
        long[] f = new long[N+1];
        
        for (int i = 1; i <= N; i++) {
            arr[i] = sc.nextInt();
            sum += arr[i];
        }
        
        int hh = 0, tt = -1;
        for (int i = 0; i <= N; i++) {
            if (hh<=tt && i > q[hh]+M+1) hh++;  // 窗口大小M+1
            f[i] = arr[i] + f[q[hh]];  // 当前不选 + 前面的合理方案(前M+1内)
            while (hh<=tt && f[i] < f[q[tt]]) tt--;
            q[++tt] = i;
            
            if (i >= N-M) res = Math.min(res, f[i]);  // 需要到最后一只可以不选的牛才更新答案
        }
        
        System.out.println(sum-res);
    }
}
```

