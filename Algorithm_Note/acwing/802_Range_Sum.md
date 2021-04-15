# 802. 区间和

假定有一个无限长的数轴，数轴上每个坐标上的数都是 0。

现在，我们首先进行 n 次操作，每次操作将某一位置 x 上的数加 c。

接下来，进行 m 次询问，每个询问包含两个整数 l 和 r，你需要求出在区间 [l,r] 之间的所有数的和。

#### 输入格式

第一行包含两个整数 n 和 m。

接下来 n 行，每行包含两个整数 x 和 c。

再接下来 m 行，每行包含两个整数 l 和 r。

#### 输出格式

共 m 行，每行输出一个询问中所求的区间内数字和。

#### 数据范围

−10^9^≤x≤10^9^,  1≤n,m≤10^5^,  −10^9^≤l≤r≤10^9^,  −10000≤c≤10000

#### 输入样例：

```
3 3
1 2
3 6
7 5
1 3
4 6
7 8
```

#### 输出样例：

```
8
0
5
```



## 离散化

```java
import java.util.*;
public class Main {
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), M = sc.nextInt(), maxN = 300010;  // N + 2M <= 300000
        int[] preSum = new int[maxN];

        List<Integer> list = new ArrayList<>();  // 将所有的使用到的数存在list中, x, le, ri(离散化)
        Set<Integer> set = new HashSet<>();
        List<Pairs> updates = new ArrayList<>(), querys = new ArrayList<>();

        for (int i = 0; i < N; i++) {  // 记录修改
            int idx = sc.nextInt(), val = sc.nextInt();
            updates.add(new Pairs(idx, val));
            if (!set.contains(idx)) { set.add(idx); list.add(idx); }
        }
        for (int i = 0; i < M; i++) {  // 记录查询
            int le = sc.nextInt(), ri = sc.nextInt();
            querys.add(new Pairs(le, ri));
            if (!set.contains(le)) { set.add(le); list.add(le); }
            if (!set.contains(ri)) { set.add(ri); list.add(ri); }
        }

        Collections.sort(list);

        for (Pairs item: updates) {
            int idx = find(item.fi, list);
            preSum[idx] += item.se;
        }

        for (int i = 1; i <= list.size(); i++) preSum[i] += preSum[i-1];

        for (Pairs item: querys) {
            int le = find(item.fi, list), ri = find(item.se, list);
            System.out.println(preSum[ri] - preSum[le-1]);
        }
    }

    static int find(int idx, List<Integer> list) {
        int le = 0, ri = list.size()-1;
        while (le <= ri) {
            int mid = le + ri >> 1;
            if (list.get(mid) == idx) {
                return mid + 1;  // +1, 因为要考虑到前缀和
            } else if (list.get(mid) < idx) {
                le = mid + 1;
            } else if (list.get(mid) > idx) {
                ri = mid - 1;
            }
        }
        return -1;  // 因为要考虑到前缀和
    }
}

class Pairs {
    int fi, se;
    public Pairs(int f, int s) {
        fi = f; se = s;
    }
}
```
