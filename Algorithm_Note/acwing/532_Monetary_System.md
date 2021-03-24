# 532. 货币系统

在货币系统 n=3, a=[2,5,9] 中，金额 1,31,3 就无法被表示出来。 

两个货币系统 (n,a) 和 (m,b) 是等价的，
当且仅当对于任意非负整数 x，它要么均可以被两个货币系统表出，要么不能被其中任何一个表出。 

现在网友们打算简化一下货币系统。

他们希望找到一个货币系统 (m,b)，满足 (m,b) 与原来的货币系统 (n,a) 等价，且 m 尽可能的小。

他们希望你来协助完成这个艰巨的任务：找到最小的 m。

#### 输入格式

输入文件的第一行包含一个整数 T，表示数据的组数。

接下来按照如下格式分别给出 T 组数据。 

每组数据的第一行包含一个正整数 n。

接下来一行包含 n 个由空格隔开的正整数 a[i]。

#### 输出格式

输出文件共有 T 行，对于每组数据，输出一行一个正整数，表示所有与 (n,a) 等价的货币系统 (m,b) 中，最小的 m。

#### 数据范围

1≤n≤100, 1≤a[i]≤25000, 1≤T≤20

#### 输入样例：

```
3
4 
3 19 10 6 
5 
11 29 13 19 17 
3
2 5 9
```

#### 输出样例：

```
2
5
2
```



```java
// 当且仅当一张货币面额可以被其他货币表示时，此货币对该系统能表示的面额没有影响
// 换言之这个货币没有存在的必要，所以将这类的货币去除就可以得到等价的最小数量货币系统。
public class Main {
    static int INF = 0x3f3f3f3f;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- > 0) {
            int N =sc.nextInt();
            int[] arr = new int[N+1];
            int[] dp = new int[25010];
            dp[0] = 1;
            int m = 0;
            for (int i = 1; i <= N; i++) {
                arr[i] = sc.nextInt();
                m = Math.max(m, arr[i]);
            }

            for (int i = 1; i <= N; i++) {
                for (int j = arr[i]; j <= m; j++) {
                    dp[j] += dp[j-arr[i]];
                }
            }

            int res = 0;
            for (int i = 1; i <= N; i++) {
                if(dp[arr[i]] == 1) res++;
            }
            System.out.println(res);
        }
    }
}

```

