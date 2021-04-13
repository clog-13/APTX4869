# 102. 最佳牛围栏

农夫约翰的农场由 N 块田地组成，每块地里都有一定数量的牛，其数量不会少于 1 头，也不会超过 2000 头。

约翰希望用围栏将一部分连续的田地围起来，并使得围起来的区域内每块地包含的牛的数量的平均值达到最大。

围起区域内至少需要包含 F 块地，其中 F 会在输入中给出。

在给定条件下，计算围起区域内每块地 **包含的牛的数量** 的 **平均值可能的最大值** 是多少。

#### 输入格式

第一行输入整数 N 和 F，数据间用空格隔开。

接下来 N 行，每行输入一个整数，第 i+1 行输入的整数代表第 i 片区域内**包含的牛的数目**。

#### 输出格式

输出一个整数，表示平均值的最大值乘以 1000 再 向下取整 之后得到的结果。

#### 数据范围

1≤N≤100000, 1≤F≤N

#### 输入样例：

```
10 6
6 
4
2
10
3
8
5
9
4
1
```

#### 输出样例：

```
6500
```



## 二分+前缀

```java
import java.util.*;

public class Main {
    int[] arr = new int[100010];
    double[] preAve = new double[100010];
    int N, F;

    public static void main(String[] args) {
        new Main().init();

    }

    void init() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); F = sc.nextInt();
        arr = new int[N + 1];
        for (int i = 1; i <= N; i++) arr[i] = sc.nextInt();

        double le = 0.0, ri = 2000, eps = 0.00001;
        while (ri-le > eps) {
            double mid = (le+ri) / 2.0;
            if (check(mid)) le = mid;
            else ri = mid;
        }

        System.out.println((int)(le * 1000));
    }

    boolean check(double avg) {
        for (int i = 1; i <= N; i++) preAve[i] = preAve[i-1] + arr[i] - avg;

        double min = 0;
        for (int i = 0; i+F <= N; i++) {
            min = Math.min(min, preAve[i]);
            if (preAve[i+F] >= min) return true;
        }
        return false;
    }
}
```

