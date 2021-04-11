# 最强对手矩阵

#### 题目描述 

这一天你来到了蓝桥杯的考场，你发现考场是一个N*M的矩阵。

因为你的群友很多，你知道考场内每个人有多强，并且把实力换算成了数值。(因为有的人太弱了,所以可能出现实力值是负数的可能)

你想知道考场内实力总和最大的**矩阵区域**的实力和是多少。**（注意:区域是按照矩形划分的）**

#### 输入描述:

```
第一行两个整数 N M  (1≤N×M≤2e5)

第二到N+1行是一个N*M的矩阵代表考场内的情况(−200<=实力值<=200)
```

#### 输出描述:

```
请输出考场内实力总和最大的矩阵区域的实力和是多少
```



**示例**

```
3 2
8 9
10 11
-4 11
```

**输出**

```
45
```



##  前缀和

矩形不只是正方形, 注意本题 “用小的 n 做 n2 复杂度”的优化思想

```java
import java.io.*;
public class Main {
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	String[] str = br.readLine().split(" ");
    	int N = Integer.parseInt(str[0]), M = Integer.parseInt(str[1]);
        
        if (N <= M) {
            long[][] preSum = new long[N+1][M+1];
            for (int i = 1; i <= N; i++) {
                String[] arr = br.readLine().split(" ");
                for (int j = 1; j <= M; j++) {
                    int n = Integer.parseInt(arr[j-1]);
                    preSum[i][j] = preSum[i-1][j] + n;
                }
            }

            long res = Long.MIN_VALUE;
            for (int top = 1; top <= N; top++) {
                for (int btm = top; btm <= N; btm++) {
                    long tmp = 0;
                    for (int k = 1; k <= M; k++) {
                        tmp = Math.max(tmp, 0) + preSum[btm][k] - preSum[top-1][k];
                        res = Math.max(res, tmp);
                    }
                }
            }
            System.out.println(res);
        } else {
            long[][] preSum = new long[N+1][M+1];
            for (int i = 1; i <= N; i++) {
                String[] arr = br.readLine().split(" ");
                for (int j = 1; j <= M; j++) {
                    preSum[i][j] = preSum[i][j-1] + Integer.parseInt(arr[j-1]);
                }
            }
            
            long res = Long.MIN_VALUE;
            for (int le = 1; le <= M; le++) {
                for (int ri = le; ri <= M; ri++) {
                    long tmp = 0;
                    for (int k = 1; k <= N; k++) {
                        tmp = Math.max(tmp, 0) + preSum[k][ri] - preSum[k][le-1];
                        res = Math.max(res, tmp);
                    }
                }
            }
            System.out.println(res);
        }
    }
}
```

