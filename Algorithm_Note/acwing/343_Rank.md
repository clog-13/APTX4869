# 343. 排序

给定 n 个变量和 m 个不等式。其中 n 小于等于 26，变量分别用前 n 的大写英文字母表示。

不等式之间具有传递性，即若 A>B 且 B>C，则 A>C。

请从前往后遍历每对关系，每次遍历时判断：

- 如果能够确定全部关系且无矛盾，则结束循环，输出确定的次序；
- 如果发生矛盾，则结束循环，输出有矛盾；
- 如果循环结束时没有发生上述两种情况，则输出无定解。

#### 输入格式

输入包含多组测试数据。

每组测试数据，第一行包含两个整数 n 和 m。

接下来 m 行，每行包含一个不等式，不等式全部为小于关系。

当输入一行 `0 0` 时，表示输入终止。

#### 输出格式

每组数据输出一个占一行的结果。

结果可能为下列三种之一：

1. 如果可以确定两两之间的关系，则输出 `"Sorted sequence determined after t relations: yyy...y."`,其中`'t'`指迭代次数，`'yyy...y'`是指升序排列的所有变量。
2. 如果有矛盾，则输出： `"Inconsistency found after t relations."`，其中`'t'`指迭代次数。
3. 如果没有矛盾，且不能确定两两之间的关系，则输出 `"Sorted sequence cannot be determined."`。

#### 数据范围

2≤n≤26，变量只可能为大写字母 A∼Z。

#### 输入样例：

```
4 6
A<B
A<C
B<C
C<D
B<D
A<B
3 2
A<B
B<A
26 1
A<Z
0 0
```

#### 输出样例：

```
Sorted sequence determined after 4 relations: ABCD.
Inconsistency found after 2 relations.
Sorted sequence cannot be determined.
```



## Floyd最短路

```java
import java.util.*;

public class Main {
    int N, M, maxN = 26;
    int[][] arr = new int[maxN][maxN], dist = new int[maxN][maxN];
    Node[] level = new Node[maxN];

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            N = sc.nextInt(); M = sc.nextInt();
            if (N == 0 && M == 0) break;
            for (int i = 0; i < N; i++) Arrays.fill(arr[i], 0);
            int type = 0, T = 0; // 0：不确定，1：唯一确定，2：矛盾
            while (M-- > 0) {
                char[] chs = sc.next().toCharArray();
                if (type > 0) continue;
                int a = chs[0] - 'A', b = chs[2] - 'A';
                arr[a][b] = 1;  // 表示 a < b

                floyd();
                type = check();
                T++;
            }

            if (type == 0) System.out.println("Sorted sequence cannot be determined.");
            else if (type == 1) System.out.println("Sorted sequence determined after "+T+" relations: "+getSequence()+".");
            else System.out.println("Inconsistency found after "+T+" relations.");
        }
    }

    void floyd() {
        dist = arr.clone();  // dist[i][j]: i<j
        for (int k = 0; k < N; k ++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (dist[i][k] == 1 && dist[k][j] == 1) {
                        dist[i][j] = 1;
                    }
                }
            }
        }
    }

    int check() {
        for (int i = 0; i < N; i++) if (dist[i][i]==1) return 2;  // contradiction
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if ((dist[i][j]==0 && dist[j][i]==0)) return 0;  // cannot be determined
            }
        }
        return 1;
    }

    String getSequence() {
        for (int i = 0; i < N; i ++) level[i] = new Node((char)('A'+i), 0);
        for (int i = 0; i < N; i ++) {
            for (int j = 0; j < i; j++) {
                if (dist[i][j] == 1) level[j].dis++;  // 拓扑排序
                else level[i].dis++;  // dist[i][j]不为1，对边 一定为1
            }
        }

        Arrays.sort(level, 0, N);
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < N; i++) res.append(level[i].ch);
        return res.toString();
    }

    static class Node implements Comparable<Node> {
        char ch; int dis;
        public Node(char c, int d) {
            ch = c; dis = d;
        }

        @Override
        public int compareTo(Node o) {  // 升序
            return Integer.compare(dis, o.dis);
        }
    }
}
```

