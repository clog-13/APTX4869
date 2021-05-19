# 1215. 小朋友排队

n 个小朋友站成一排。

现在要把他们按身高从低到高的顺序排列，但是每次只能交换位置相邻的两个小朋友。

每个小朋友都有一个不高兴的程度。

开始的时候，所有小朋友的不高兴程度都是 0。

如果某个小朋友第一次被要求交换，则他的不高兴程度增加 1，如果第二次要求他交换，则他的不高兴程度增加 2（即不高兴程度为 3），依次类推。当要求某个小朋友第 k 次交换时，他的不高兴程度增加 k。

请问，要让所有小朋友按从低到高排队，他们的不高兴程度之和最小是多少。

如果有两个小朋友身高一样，则他们谁站在谁前面是没有关系的。

#### 输入格式

输入的第一行包含一个整数 n，表示小朋友的个数。

第二行包含 n 个整数 H1,H2,…,Hn，分别表示每个小朋友的身高。

#### 输出格式

输出一行，包含一个整数，表示小朋友的不高兴程度和的最小值。

#### 数据范围

1≤n≤100000, 0≤Hi≤1000000

#### 输入样例：

```
3
3 2 1
```

#### 输出样例：

```
9
```



## 树状数组

```java
import java.io.*;
import java.util.*;

public class Main {
    int N, maxN = 1000010;
    int[] arr = new int[maxN], cout = new int[maxN];
    long[] preSum = new long[maxN];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        String[] str = br.readLine().split(" ");
        for (int i = 1; i <= N; i++) arr[i] = Integer.parseInt(str[i-1]) + 1;

        for (int i = 1; i <= N; i++) {  // 正序 找比当前数 大的 （前面大的要换的后面去）
            update(arr[i]);  // arr[i]身高， preSum存储的是身高区间的个数
            cout[i] = i - query(arr[i]);  // 当前一共 i 个人，所以query(maxN-1) == i
        }
        Arrays.fill(preSum, 0);
        for (int i = N; i >= 1; i--) {  // 倒序 找比当前数 小的 （后面小的要换到前面去）
            update(arr[i]);
            cout[i] += query((arr[i]-1));
        }

        long res = 0;
        for (int i = 1; i <= N; i++) res += (long) cout[i]*(cout[i]+1) / 2;  // 求和公式
        System.out.println(res);
    }

    int lowbit(int x) {
        return x&-x;
    }

    void update(int idx) {
        for (; idx < maxN; idx += lowbit(idx)) preSum[idx] += 1;
    }

    int query(int idx) {
        int res = 0;
        for (; idx > 0; idx -= lowbit(idx)) res += preSum[idx];
        return res;
    }
}
```

