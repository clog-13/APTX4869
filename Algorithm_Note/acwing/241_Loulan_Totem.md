#  241. 楼兰图腾

西部 314 打算研究这幅壁画中包含着多少个图腾。

如果三个点 (i,yi),(j,yj),(k,yk) 满足 1≤i<j<k≤n 且 yi>yj,yj<yk，则称这三个点构成 `V` 图腾;

如果三个点 (i,yi),(j,yj),(k,yk) 满足 1≤i<j<k≤n 且 yi<yj,yj>yk，则称这三个点构成 `∧` 图腾;

西部 314 想知道，这 n 个点中两个部落图腾的数目。

因此，你需要编写一个程序来求出 `V` 的个数和 `∧` 的个数。

#### 输入格式

第一行一个数 n。

第二行是 n 个数，分别代表 y1，y2,…,yn。

#### 输出格式

两个数，中间用空格隔开，依次为 `V` 的个数和 `∧` 的个数。

#### 数据范围

对于所有数据，n≤200000，且输出答案不会超过 int64。 y1∼yn 是 1 到 n 的一个排列。

#### 输入样例：

```
5
1 5 3 2 4
```

#### 输出样例：

```
3 4
```



## 树状数组

```java
import java.util.*;
class Main {
    int N, maxN = 2000010;
    int[] ls = new int[maxN], hs = new int[maxN];
    int[] arr = new int[maxN], trees = new int[maxN];

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        for (int i = 1; i <= N; i++) arr[i] = sc.nextInt();

        for (int i = 1; i <= N; i++) {
            int t = arr[i];
            ls[i] = query(t-1);
            hs[i] = query(N)-query(t);
            update(t, 1);
        }

        Arrays.fill(trees, 0);
        long resA = 0, resV = 0;
        for (int i = N; i >= 1; i--) {
            int t = arr[i];
            resV += (query(N)-query(t)) * (long)hs[i];
            resA += query(t-1) * (long)ls[i];
            update(t, 1);
        }
        System.out.println(resV+" "+resA);
    }


    void update(int idx, int n) {
        for (int i = idx; i <= N; i += lowbit(i)) trees[i] += n;
    }

    int query(int idx) {
        int res = 0;
        for (int i = idx; i > 0; i -= lowbit(i)) res += trees[i];
        return res;
    }

    int lowbit(int x) {
        return x & -x;
    }
}
```

.