# 182. 破坏正方形

下图左侧显示了一个用 24 根火柴棍构成的完整 3×3 网格。

所有火柴的长度都是 1。

您可以在网格中找到许多不同大小的正方形。

在左图所示的网格中，有 9 个边长为 1 的正方形，4 个边长为 2 的正方形和 1 个边长为 3 的正方形。

组成完整网格的每一根火柴都有唯一编号，该编号从上到下，从左到右，从 1 开始按顺序分配。

如果你将一些火柴棍从完整网格中取出，形成一个不完整的网格，则一部分正方形将被破坏。

右图为移除编号 12,17 和 2 的三个火柴棍后的不完整的 3×3 网格。

这次移除破坏了 5 个边长为 1 的正方形，3 个边长为 2 的正方形和 1 个边长为 3 的正方形。

此时，网格不具有边长为 3 的正方形，但仍然具有 4 个边长为 1 的正方形和 1 个边长为 2 的正方形。

![火柴图.jpg](https://www.acwing.com/media/article/image/2019/01/16/19_2af90edc19-%E7%81%AB%E6%9F%B4%E5%9B%BE.jpg)

现在给定一个（完整或不完整）的 n×n（nn 不大于 55）网格，求至少再去掉多少根火柴棒，可以使得网格内不再含有任何尺寸的正方形。

#### 输入格式

输入包含 T 组测试用例。

测试用例的数量 T 在输入文件的第一行中给出。

每个测试用例由两行组成：

第一行包含一个整数 n，表示网格的规模大小。

第二行以非负整数 k 开头，表示所给网格相较完整的 n×n 网格所缺少的火柴杆数量，后跟 k 个整数表示所有缺少的火柴杆的具体编号。

注意，如果 k 等于零，则表示输入网格是完整的 n×n 网格。

#### 输出格式

每个测试用例输出一个结果，表示破坏所有正方形，所需的去掉火柴棒的最小数量。 每个结果占一行。

#### 输入样例：

```
2
2
0
3
3 12 17 23
```

#### 输出样例：

```
3
3
```



## 迭代加深

```java
import java.io.*;
import java.util.*;

class Main {
    int T, N, M, maxN = 100;
    List<Integer>[] square = new List[maxN];
    String[] arr;
    boolean[] st = new boolean[maxN];

    public static void main(String[] args) throws IOException {
        new Main().init();
    }

    private void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine().trim());
        while (T-- > 0) {
            N = Integer.parseInt(br.readLine().trim());

            M = 0;
            for (int i = 0; i < maxN; i++) square[i] = new ArrayList<>();
            for (int n = 1; n <= N; n++) {
                for (int row = 0; row+n-1 < N; row++) { // 相同大小正方形 行 起始位置的偏移
                    for (int col = 1; col+n-1 <= N; col++) { // 相同大小正方形 列 起始位置的偏移
                        for (int offset = 0; offset < n; offset++) { // 当前正方形内 每个边里 的偏移
                            int d = 2 * N + 1;
                            square[M].add( row   *d     + col + offset);    // 上
                            square[M].add((row+n)*d     + col + offset);    // 下
                            square[M].add( row   *d+N   + col + offset*d);  // 左
                            square[M].add( row   *d+N+n + col + offset*d);  // 右
                        }
                        M++;
                    }
                }
            }

            Arrays.fill(st, false);
            arr = br.readLine().split(" ");
            for (int i = 1; i < arr.length; i++) {
                st[Integer.parseInt(arr[i])] = true;
            }
            
            int depth = 0;
            while (!dfs(depth)) depth++;
            System.out.println(depth);
        }
    }

    private boolean dfs(int depth) {
        if (f() > depth) return false;
        for (int i = 0; i < M; i++) {
            if (check_complete(square[i])) {
                for (int s : square[i]) {
                    st[s] = true;
                    if (dfs(depth-1)) return true;
                    st[s] = false;
                }
                return false;
            }
        }
        return true;
    }

    private int f() {
        boolean[] backup_st = new boolean[maxN];
        System.arraycopy(st, 0, backup_st, 0, st.length);
        int res = 0;
        for (int i = 0; i < M; i++) {
            if (check_complete(square[i])) {
                res++;
                for (int s : square[i]) st[s] = true;
            }
        }
        System.arraycopy(backup_st, 0, st, 0, st.length);
        return res;
    }

    private boolean check_complete(List<Integer> seq) {
        for (int s : seq) if (st[s]) return false;
        return true;
    }
}
```

