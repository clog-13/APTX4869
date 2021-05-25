# 316. 减操作

给定一个整数数组 a1,a2,…,an。

定义数组第 i 位上的减操作：把 ai 和 ai+1 换成 ai−ai+1。

用 con(a,i) 表示减操作，可以表示为：

con(a,i)=[a1,a2,…,ai−1,ai−ai+1,ai+2,…,an]

长度为 n 的数组，经过 n−1 次减操作后，就可以得到一个整数 t。

例如数组 [12,10,4,3,5] 经过如下操作可得到整数 4：

con([12,10,4,3,5],2)=[12,6,3,5]

con([12,6,3,5],3)=[12,6,−2]

con([12,6,−2],2)=[12,8]

con([12,8],1)=[4]

现在给定数组以及目标整数，求完整操作过程。

#### 输入格式

第 1 行包含两个整数 n 和 t。

第 2..n+1 行：第 i 行包含数组中的第 i 个整数 ai。

#### 输出格式

输出共 n−1 行，每行包含一个整数，第 i 行的整数表示第 i 次减操作的操作位置。

#### 数据范围

1≤n≤100, −10000≤t≤10000, 1≤ai≤100

#### 输入样例：

```
5 4
12
10
4
3
5
```

#### 输出样例：

```
2
3
2
1
```



## DP

```java
import java.util.*;

public class Main {
    int N, T, cnt, offset = 10000, maxN = 105;
    int[] arr = new int[maxN], res = new int[maxN];
    int[][] f = new int[maxN][2*offset+10];

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); T = sc.nextInt();
        for (int i = 1; i <= N; i++) arr[i] = sc.nextInt();
        f[1][arr[1] + offset] = 1;          // a[1]必然是加上
        f[2][arr[1]-arr[2] + offset] = -1;  // a[2]必然是减去
        for (int i = 3; i <= N; i++) {  // 遍历数组
            for (int j = 0; j <= 20000; j++) {  // 遍历可能取值
                if (f[i - 1][j] != 0) {  // 可转移
                    f[i][j + arr[i]] = 1;  // +号
                    f[i][j - arr[i]] = -1; // -号
                }
            }
        }

        int tmp = T + offset;
        for (int i = N; i >= 2; i--) {  // 回溯走路径,确定+,-号
            res[i] = f[i][tmp];
            if (res[i] == 1) tmp -= arr[i];
            else if (res[i] == -1) tmp += arr[i];
        }
        for (int i = 2; i <= N; i++) {
            if (res[i] == 1) {  // 加操作（先减后面转变(有且仅有一次)为加）
                System.out.println(i - cnt - 1);
                cnt++;
            }
        }
        for (int i = 2; i <= N; i++) {  // 加操作
            if (res[i] != 1) System.out.println(1);
        }
    }
}

```

