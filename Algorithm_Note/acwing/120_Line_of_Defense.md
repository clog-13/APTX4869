# 120. 防线

达达学习数学竞赛的时候受尽了同仁们的鄙视，终于有一天......受尽屈辱的达达黑化成为了黑暗英雄怪兽达达。

就如同中二漫画的情节一样，怪兽达达打算毁掉这个世界。

数学竞赛界的精英 lqr 打算阻止怪兽达达的阴谋，于是她集合了一支由数学竞赛选手组成的超级行动队。

由于队员们个个都智商超群，很快，行动队便来到了怪兽达达的黑暗城堡的下方。

但是，同样强大的怪兽达达在城堡周围布置了一条“不可越过”的坚固防线。

防线由很多防具组成，这些防具分成了 N 组。

我们可以认为防线是一维的，那么每一组防具都分布在防线的某一段上，并且同一组防具是等距离排列的。

也就是说，我们可以用三个整数 S， E 和 D 来描述一组防具，即这一组防具布置在防线的 S，S+D，S+2D，…，S+KD(K∈Z，S+KD≤E，S+(K+1)D>E)位置上。

黑化的怪兽达达设计的防线极其精良。

如果防线的某个位置有偶数个防具，那么这个位置就是毫无破绽的(包括这个位置一个防具也没有的情况，因为 0 也是偶数)。

只有有奇数个防具的位置有破绽，但是整条防线上也最多只有一个位置有奇数个防具。

作为行动队的队长，lqr 要找到防线的破绽以策划下一步的行动。

但是，由于防具的数量太多，她实在是不能看出哪里有破绽。

作为 lqr 可以信任的学弟学妹们，你们要帮助她解决这个问题。

#### 输入格式

输入文件的第一行是一个整数 T，表示有 T 组互相独立的测试数据。

每组数据的第一行是一个整数 N。

之后 N 行，每行三个整数 Si，Ei，Di，代表第 i 组防具的三个参数，数据用空格隔开。

#### 输出格式

对于每组测试数据，如果防线没有破绽，即所有的位置都有偶数个防具，输出一行 `"There's no weakness."`(不包含引号) 。

否则在一行内输出两个空格分隔的整数 P 和 C，表示在位置 P 有 C 个防具。当然 C 应该是一个奇数。

#### 数据范围

防具总数不多于108, Si≤Ei, 1≤T≤5, N≤200000, 0≤Si，Ei，Di≤2^31^−1

#### 输入样例：

```
3
2
1 10 1
2 10 1
2
1 10 1 
1 10 1 
4
1 10 1 
4 4 1 
1 5 1 
6 10 1
```

#### 输出样例：

```
1 1
There's no weakness.
4 3
```



## 二分 + 前缀

给定n个等差数列，每个等差数列的起点为s，终点为e，差为d。整个序列中至多有一个位置所占数字是奇数。判断奇数位是否存在。

```java
import java.util.*;
class Main {
    int N, maxN = 200005, INF = 0x3f3f3f3f;
    Node[] arr = new Node[maxN];

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- > 0) {
            N = sc.nextInt();
            int mx = -INF, mi = INF;
            for (int i = 1; i <= N; i++) {   // input
                int s = sc.nextInt(), e = sc.nextInt(), d = sc.nextInt();
                arr[i] = new Node(s, e, d);  // 起点, 终点, 公差
                mi = Math.min(mi, s); mx = Math.max(mx, e);
            }

            if ((getSum(mx) & 1) == 1) {  // 如果存在奇数位，则整个数列的总和必然是奇数，反之
                int le = mi, ri = mx;
                while (le <= ri) {  // 二分, 判断区间总和的奇偶性
                    int mid = le+ri >> 1;
                    if (((getSum(mid)-getSum(le-1)) & 1) == 0) le = mid+1;
                    else ri = mid-1;
                }
                System.out.println(le+" "+(getSum(le)-getSum(le-1)));
            } else {
                System.out.println("There's no weakness.");
            }
        }
    }

    int getSum(int idx) {
        int res = 0;
        for (int i = 1; i <= N; i++) {
            if (arr[i].s <= idx) {  // 区间内[s, e]，当前点的数量
                res += (Math.min(arr[i].e, idx) - arr[i].s) / arr[i].d + 1;
            }
        }
        return res;
    }

    static class Node {
        int s, e, d;
        public Node(int ss, int ee, int dd) {
            s = ss; e = ee; d = dd;
        }
    }
}
```

