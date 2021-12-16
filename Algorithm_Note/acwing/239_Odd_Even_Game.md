# 239. 奇偶游戏

小 A 和小 B 在玩一个游戏。

首先，小 A 写了一个由 0 和 1 组成的序列 S，长度为 N。

然后，小 B 向小 A 提出了 M 个问题。

在每个问题中，小 B 指定两个数 l 和 r，小 A 回答 S[l∼r] 中有奇数个 1 还是偶数个 1。

机智的小 B 发现小 A 有可能在撒谎。

例如，小 A 曾经回答过 S[1∼3] 中有奇数个 1，S[4∼6] 中有偶数个 1，现在又回答 S[1∼6] 中有偶数个 1，显然这是自相矛盾的。

请你帮助小 B 检查这 M 个答案，并指出在至少多少个回答之后可以确定小 A 一定在撒谎。

即求出一个最小的 k，使得 01 序列 S 满足第 1∼k 个回答，但不满足第 1∼k+1 个回答。

#### 输入格式

第一行包含一个整数 N，表示 0101 序列长度。

第二行包含一个整数 M，表示问题数量。

接下来 M 行，每行包含一组问答：两个整数 l 和 r，以及回答 `even` 或 `odd`，用以描述 S[l∼r] 中有偶数个 1 还是奇数个 1。

#### 输出格式

输出一个整数 k，表示 01 序列满足第 1∼k 个回答，但不满足第 1∼k+1 个回答，如果 01 序列满足所有回答，则输出问题总数量。

#### 数据范围

N≤10^9,M≤10000

#### 输入样例：

```
10
5
1 2 even
3 4 odd
5 6 even
1 6 even
7 10 odd
```

#### 输出样例：

```
3
```



## 带权并查集

```java
import java.util.*;
class Main {
    int idx, N, M, maxN = 20010;
    int[] fa = new int[maxN], d = new int[maxN], q = new int[maxN];
    int[][] arr = new int[maxN][3];
    Set<Integer> set = new HashSet<>();

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        N = Integer.parseInt(sc.nextLine());
        M = Integer.parseInt(sc.nextLine());

        for (int i = 1; i <= M; i++) {
            String[] str = sc.nextLine().split(" ");
            int le = Integer.parseInt(str[0]), ri = Integer.parseInt(str[1]);
            arr[i][0] = le-1; arr[i][1] = ri;

            if (str[2].equals("even")) arr[i][2] = 0;
            else arr[i][2] = 1;

            if (!set.contains(le-1)) { q[idx++] = le-1; set.add(le-1); }
            if (!set.contains(ri))   { q[idx++] = ri;   set.add(ri); }
        }

        int[] a = new int[set.size()];
        System.arraycopy(q, 0, a, 0, a.length);
        Arrays.sort(a, 0, set.size());
        for (int i = 1; i <= set.size(); i++) fa[i] = i;

        for (int i = 1; i <= M; i++) {  // 大于等于 k
            int lx = Arrays.binarySearch(a, arr[i][0]);  // k-1或者k的位置
            int rx = Arrays.binarySearch(a, arr[i][1]);    // k的位置(k是必定存在的)
            if (lx < 0) lx = -lx;
            else lx++;
            rx++;

            int pl = find(lx), pr = find(rx);
            if (pl == pr) {
                if ((d[lx]^d[rx]) != arr[i][2]) {
                    System.out.println(i-1);
                    return;
                }
            } else {
                fa[pl] = pr;  // 注意指向
                d[pl] ^= d[lx] ^ d[rx] ^ arr[i][2];
            }
        }
        System.out.println(M);
    }

    int find(int x) {
        if (x != fa[x]) {
            int root = find(fa[x]);
            d[x] ^= d[fa[x]];  // 只有精确相连的才可以传递
            fa[x] = root;
        }
        return fa[x];
    }
}
```

