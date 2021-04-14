# 244. 谜一样的牛

有 n 头奶牛，已知它们的身高为 1∼n 且各不相同，但不知道每头奶牛的具体身高。

现在这 n 头奶牛站成一列，已知第 i 头牛前面有 Ai 头牛比它低，求每头奶牛的身高。

#### 输入格式

第 1 行：输入整数 n。

第 2..n 行：每行输入一个整数 Ai，第 i 行表示第 i 头牛前面有 Ai 头牛比它低。

（注意：因为第 1 头牛前面没有牛，所以并没有将它列出）

#### 输出格式

输出包含 n 行，每行输出一个整数表示牛的身高。

第 i 行输出第 i 头牛的身高。

#### 数据范围

1≤n≤105

#### 输入样例：

```
5
1
2
1
0
```

#### 输出样例：

```
2
4
5
3
1
```



## 树状数组

```java
import java.io.*;

public class Main {
    int N, maxN = 100010;
    int[] arr = new int[maxN], tr = new int[maxN], res = new int[maxN];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        for (int i = 2; i <= N; i++) arr[i] = Integer.parseInt(br.readLine());

        for (int i = 1; i <= N; i++) update(i, 1);
        for (int i = N; i >= 1; i--) {  // 从后根据“有几个比现在高”的数组遍历
            int k = arr[i] + 1;

            int le = 1, ri = N;  // 二分找到第k小的牛
            while (le < ri) {
                int mid = le+ri >> 1;
                if (query(mid) >= k) ri = mid;  // query到mid有几个,所以k=arr[i] 加 1
                else le = mid + 1;
            }
            res[i] = le;
            update(le, -1);  // 给后面的前缀和减去该数
        }

        for (int i = 1; i <= N; i++) System.out.println(res[i]);
    }

    int lowbit(int x) {
        return x & -x;
    }

    void update(int idx, int n) {
        for (int i = idx; i <= N; i += lowbit(i)) tr[i] += n;
    }

    int query(int idx) {
        int res = 0;
        for (int i = idx; i >= 1; i -= lowbit(i)) res += tr[i];
        return res;
    }
}
```

