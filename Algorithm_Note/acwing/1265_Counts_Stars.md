# 1265. 数星星

天空中有一些星星，这些星星都在不同的位置，每个星星有个坐标。

如果一个星星的左下方（包含正左和正下）有 k 颗星星，就说这颗星星是 k 级的。

![](pic/1265_1.png)

例如，上图中星星 5 是 3 级的（1,2,4 在它左下），星星 3 是 2 级的，星星 2,4 是 1 级的。

例图中有 1 个 0 级，2 个 1 级，1 个 2 级，1 个 3 级的星星。

给定星星的位置，输出各级星星的数目。

换句话说，给定 N 个点，定义每个点的等级是在该点左下方（含正左、正下）的点的数目，试统计每个等级有多少个点。

#### 输入格式

第一行一个整数 N，表示星星的数目；

接下来 N 行给出每颗星星的坐标，坐标用两个整数 x,y 表示；

不会有星星重叠。**星星按 y 坐标增序给出，y 坐标相同的按 x 坐标增序给出。**

#### 输出格式

N 行，每行一个整数，分别是 0 级，1 级，2 级，……，N−1 级的星星的数目。

#### 数据范围

1≤N≤15000, 0≤x,y≤32000

#### 输入样例：

```
5
1 1
5 1
7 1
3 3
5 5
```

#### 输出样例：

```
1
2
1
1
0
```

## 树状数组

```java
import java.util.*;

public class Main {
    static int[] tr = new int[32010], res = new int[32010];
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        for(int i = 0; i < N; i++) {
            int x = sc.nextInt(), y = sc.nextInt();
            x++;
            res[query(x)]++;  // 输入数据是先按y递增,再按x的递增给出，所以当作一维数组处理，先查询再更新
            update(x);
        }

        for(int i = 0; i < N; i++) {
            System.out.println(res[i]);
        }
    }

    private static int lowbit(int x) {
        return x & (-x);
    }

    private static void update(int idx) {
        while(idx <= 32010) {
            tr[idx]++;
            idx += lowbit(idx);
        }
    }

    private static int query(int idx) {
        int res = 0;
        while (idx > 0) {
            res += tr[idx];
            idx -= lowbit(idx);
        }
        return res;
    }
}
```

