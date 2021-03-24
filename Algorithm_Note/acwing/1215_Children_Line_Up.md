# 1215. 小朋友排队

n 个小朋友站成一排。

现在要把他们按身高从低到高的顺序排列，但是每次只能交换位置相邻的两个小朋友。

每个小朋友都有一个不高兴的程度。

开始的时候，所有小朋友的不高兴程度都是 0。

如果某个小朋友第一次被要求交换，则他的不高兴程度增加 1，如果第二次要求他交换，则他的不高兴程度增加 2（即不高兴程度为 3），依次类推。当要求某个小朋友第 kk 次交换时，他的不高兴程度增加 kk。

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
import java.util.Arrays;

public class Main {
    static int N, maxN = 1000010;
    static long[] data = new long[maxN];
    static long[] preSum = new long[maxN];
    static long[] cout = new long[maxN];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        String[] str = br.readLine().split(" ");
        for (int i = 1; i <= N; i++) {
            data[i] = Integer.parseInt(str[i-1]);
            data[i]++;
        }

        for (int i = 1; i <= N; i++) {  // 正序找比当前数大的
            update((int) data[i]);  // preSum 存储的是个数
//           cout[i] = query(maxN-1) - query((int) data[i]);
             cout[i] = i - query((int) data[i]); // 当前一共 i 个数，所以query(maxN-1) == i
        }
        Arrays.fill(preSum, 0);
        for (int i = N; i > 0; i--) {   // 倒序找比当前小的
            update((int) data[i]);
            cout[i] += query((int) (data[i]-1));
        }

        long res = 0;
        for (int i = 1; i <= N; i++)
            res += cout[i]*(cout[i]+1) / 2;	// 求和公式
        System.out.println(res);
    }

    private static int lowbit(int n) {
        return n & (-n);
    }

    private static void update(int idx) {
        for (; idx < maxN; idx += lowbit(idx)) preSum[idx] += 1;
    }

    private static long query(int idx) {
        int res = 0;
        for (; idx > 0; idx -= lowbit(idx)) res += preSum[idx];
        return res;
    }
}
```

