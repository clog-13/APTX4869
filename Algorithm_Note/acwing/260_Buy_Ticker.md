# 260. 买票

达达在买回家的火车票，因为正值春运，售票处排起了长队。

因为晚上室内光线很暗，所以很多人趁机插队。

现在给每个人赋予一个整数作为编号，告诉你每一个排队的人的编号，和他进入队列时的具体位置。

请你确定最终的队列顺序。

#### 输入格式

输入可能包含多组测试用例。

对于每组测试用例，第一行包含整数 N，表示排队的总人数。

接下来 N 行，每行两个整数 Pi,Vi，第 i 行数据表示第 i 个人进入队列时的位置以及他的个人编号。

一个人的 Pi 值具体表示为当该人员进入队列时，他前面的人数。

例如，如果一个人插到了队首，则其 Pi 值为 0，如果插到了第三个位置（第二个人后面），则其 Pi 值为 2。

#### 输出格式

每个测试用例，输出一行包含 N 个整数（表示每个人的编号）的结果，表示最终的人员队列顺序。

每个结果占一行，同行数据之间空格隔开。

#### 数据范围

1≤N≤200000, 0≤Vi≤32767, 0≤Pi≤i−1

#### 输入样例：

```
4
0 77
1 51
1 33
2 69
4
0 20523
1 19243
1 3890
0 31492
```

#### 输出样例：

```
77 33 69 51
31492 20523 3890 19243
```



## BITree

```java
import java.util.*;

class Main {
    int N, maxN = 200005;
    int[] tr = new int[maxN], res = new int[maxN];
    Node[] arr = new Node[maxN];

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            N = sc.nextInt();
            Arrays.fill(tr, 0);
            for (int i = 1; i <= N; i++) {
                if (i > 1) update(i, 1);
                arr[i] = new Node(sc.nextInt(), sc.nextInt());  // (pos+1, id);
            }
            // tr[i]:i位置的人的pos应该为 tr[i]
            for (int i = N; i > 0; i--) {  // 倒序遍历, 这样不会影响到arr前面的数据
                int le = 1, ri = N;
                while (le < ri) {
                    int mid = le+ri >> 1;  // query(mid):mid位置前面有多少人
                    if (query(mid) < arr[i].pos) le = mid+1;  // 左边界
                    else ri = mid;
                }

                res[le] = arr[i].id;
                update(le, -1);
            }
            for (int i = 1; i <= N; i++) System.out.print(res[i]+" ");
            System.out.println();
        }
    }

    void update(int idx, int val) {
        for ( ; idx <= N; idx += lowbit(idx)) tr[idx] += val;
    }

    int query(int idx) {
        int res = 0;
        for ( ; idx > 0; idx -= lowbit(idx)) res += tr[idx];
        return res;
    }

    int lowbit(int x) {
        return x&-x;
    }

    static class Node {
        int pos, id;
        public Node(int p, int i) {
            pos = p; id = i;
        }
    }
}
```

