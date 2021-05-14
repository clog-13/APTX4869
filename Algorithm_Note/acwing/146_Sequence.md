# 146. 序列

给定 m 个序列，每个包含 n 个非负整数。

现在我们可以从每个序列中选择一个数字以形成具有 m 个整数的序列。

很明显，我们一共可以得到 n^m^ 个这种序列，然后我们可以计算每个序列中的数字之和，并得到 n^m^ 个值。

现在请你求出这些序列和之中最小的 n 个值。

#### 输入格式

第一行输入一个整数 T，代表输入中包含测试用例的数量。

接下来输入 T 组测试用例。

对于每组测试用例，第一行输入两个整数 m 和 n。

接下在 m 行输入 m 个整数序列，数列中的整数均不超过 10000。

#### 输出格式

对于每组测试用例，均以递增顺序输出最小的 n 个序列和，数值之间用空格隔开。

每组输出占一行。

#### 数据范围

0<m≤1000, 0<n≤2000

#### 输入样例：

```
1
2 3
1 2 3
2 2 3
```

#### 输出样例：

```
3 3 4
```



## 堆

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- != 0) {
            int M = sc.nextInt(), N = sc.nextInt();
            int[] arr = new int[N], brr = new int[N];

            for (int i = 0; i < N; i++) arr[i] = sc.nextInt();
            Arrays.sort(arr);
            for (int i = 1; i < M; i++) {
                for (int j = 0; j < N; j++) brr[j] = sc.nextInt();
                arr = merge(arr, brr);
            }

            for (int i = 0; i < N; i++) System.out.printf("%d ", arr[i]);
            System.out.println();
        }
    }

    int[] merge(int[] arr, int[] brr) {
        int N = arr.length;
        int[] res = new int[N];
        Queue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.val));
        for (int i = 0; i < N; i++) queue.add(new Node(arr[0]+brr[i], 0));
        for (int i = 0; i < N ; i++) {
            Node t = queue.poll();
            res[i] = t.val;
            if (t.id+1 < N) queue.add(new Node(t.val - arr[t.id] + arr[t.id+1], t.id+1));
        }
        return res;
    }

    static class Node {
        int val, id;
        public Node(int val, int id) {
            this.val = val; this.id = id;
        }
    }
}
```

