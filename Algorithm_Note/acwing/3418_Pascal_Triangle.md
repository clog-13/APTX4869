# 3418. 杨辉三角形

下面的图形是著名的杨辉三角形：

![QQ截图20210423150438.png](https://cdn.acwing.com/media/article/image/2021/04/23/19_34bec150a4-QQ%E6%88%AA%E5%9B%BE20210423150438.png)

如果我们按从上到下、从左到右的顺序把所有数排成一列，可以得到如下数列：

```
1, 1, 1, 1, 2, 1, 1, 3, 3, 1, 1, 4, 6, 4, 1, ...
```

给定一个正整数 N，请你输出数列中第一次出现 N 是在第几个数？

#### 输入格式

输入一个整数 N。

#### 输出格式

输出一个整数代表答案。

#### 数据范围

对于 20% 的评测用例，1≤N≤10；对于所有评测用例，1≤N≤10^9^。

#### 输入样例：

```
6
```

#### 输出样例：

```
13
```



## 思维（数学）

```java
import java.util.*;
class Main {
    int N;
    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        if (N == 1) System.out.println(1);
        else {
            for (int k = 16; ; k--) {
                if (check(k)) return;
            }            
        }
    }

    boolean check(int k) {
        long le = 2L*k, ri = N;
        while (le < ri) {
            long mid = le+ri>>1;
            if (Cal((int) mid, k) < N) le = mid+1;
            else ri = mid;
        }
        if (Cal((int) ri, k) != N) return false;
        long res = ri*(ri+1)/2 + k+1;
        System.out.println(res);
        return true;
    }

    long Cal(int a, int b) {
        long res = 1;
        for (int i = a, j = 1; j <= b; i--, j++) {
            res = res*i/j;
            if (res > N) return res;
        }
        return res;
    }
}
```

