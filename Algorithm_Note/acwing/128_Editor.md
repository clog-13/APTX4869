# 128. 编辑器

你将要实现一个功能强大的整数序列编辑器。

在开始时，序列是空的。

编辑器共有五种指令，如下：

1、`I x`，在光标处插入数值 x。
2、`D`，将光标前面的第一个元素删除，如果前面没有元素，则忽略此操作。
3、`L`，将光标向左移动，跳过一个元素，如果左边没有元素，则忽略此操作。
4、`R`，将光标向右移动，跳过一个元素，如果右边没有元素，则忽略次操作。
5、`Q k`，假设此刻光标之前的序列为 a1,a2,…,an，输出 max1≤i≤kSi，其中 Si=a1+a2+…+ai。

#### 输入格式

第一行包含一个整数 Q，表示指令的总数。

接下来 Q 行，每行一个指令，具体指令格式如题目描述。

#### 输出格式

每一个 `Q k` 指令，输出一个整数作为结果，每个结果占一行。

#### 数据范围

1≤Q≤106,|x|≤103, 1≤k≤n

#### 输入样例：

```
8
I 2
I -1
I 1
Q 3
L
D
R
Q 2
```

#### 输出样例：

```
2
3
```

#### 样例解释

下图包含了对样例的过程描述：

![C464-1004-2.jpg](https://www.acwing.com/media/article/image/2019/01/22/19_e0778ec41d-C464-1004-2.jpg)



## 栈

```java
import java.io.*;

public class Main {
    int lt, rt, maxN = 1000010, INF = 0x3f3f3f3f;
    int[] arr_le = new int[maxN], arr_ri = new int[maxN];
    int[] preSum = new int[maxN], res = new int[maxN];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        res[0] = -INF;
        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            String[] str = br.readLine().split(" ");
            switch (str[0]) {
                case "I":
                    arr_le[++lt] = Integer.parseInt(str[1]);
                    preSum[lt] = preSum[lt - 1] + arr_le[lt];
                    res[lt] = Math.max(preSum[lt], res[lt - 1]);
                    break;
                case "D":
                    if (lt > 0) lt--;
                    break;
                case "L":
                    if (lt > 0) arr_ri[++rt] = arr_le[lt--];
                    break;
                case "R":
                    if (rt > 0) {
                        arr_le[++lt] = arr_ri[rt--];
                        preSum[lt] = preSum[lt - 1] + arr_le[lt];
                        res[lt] = Math.max(res[lt - 1], preSum[lt]);
                    }
                    break;
                case "Q":
                    bw.write(res[Integer.parseInt(str[1])]+"\n");
                    break;
            }
        }
        bw.flush(); bw.close();
    }
}
```



```java
import java.io.*;

public class Main {
    Node cur = new Node(0, 0, Integer.MIN_VALUE, null, null);
    int[] res = new int[1000010];
    int idx = 0;
    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            String[] str = br.readLine().split(" ");
            switch (str[0]) {
                case "I":
                    insert(Integer.parseInt(str[1])); break;
                case "L":
                    moveLeft(); break;
                case "R":
                    moveRight(); break;
                case "D":
                    delete(); break;
                case "Q":
                    bw.write(query(Integer.parseInt(str[1])) + "\n"); break;
            }
        }
        bw.flush(); bw.close();
    }

    void insert(int x) {
        cur.next = new Node(x, cur.preSum+x, Math.max(cur.maxSum, cur.preSum+x), cur, cur.next);
        cur = cur.next;
        res[idx++] = cur.maxSum;
    }

    void moveLeft() {
        if (cur.prev != null) {
            cur = cur.prev;
            idx--;
        }
    }

    void moveRight() {
        if (cur.next != null) {
            cur.next.prev = cur;
            cur = cur.next;
            cur.preSum = cur.prev.preSum + cur.val;
            cur.maxSum = Math.max(cur.preSum, cur.prev.maxSum);
            res[idx++] = cur.maxSum;
        }
    }

    int query(int i) {
        return res[i-1];
    }

    void delete() {
        if (cur.prev != null) {
            Node temp = cur.prev;
            cur.prev.next = cur.next;
            if (cur.next != null) cur.next.prev = cur.prev;
            cur = temp;
            idx--;
        }
    }

    static class Node {
        int val, preSum, maxSum;
        Node prev, next;
        public Node(int v, int pS, int mS, Node p, Node n) {
            val = v; preSum = pS; maxSum = mS;
            prev = p; next = n;
        }
    }
}
```

