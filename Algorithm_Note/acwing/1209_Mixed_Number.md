# 1209. 带分数

100 可以表示为带分数的形式：100=3+69258/714

还可以表示为：100=82+3546/197

注意特征：带分数中，数字 1∼9分别出现且只出现一次（不包含 0）。

类似这样的带分数，100 有 11 种表示法。

#### 输入格式

一个正整数。

#### 输出格式

输出输入数字用数码 1∼9 不重复不遗漏地组成带分数表示的全部种数。

#### 数据范围

1≤N<106

#### 输入样例1：

```
100
```

#### 输出样例：

```
11
```



## DFS

N = a + b/c

N - a = b/c

(N - a)*c = b

```java
import java.util.Scanner;

public class Main {
    int N = 0, res = 0;
    int[] st = new int[10], backup = new int[10];

    public static void main(String[] args) {
        new Main().init();
    }

    void init() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        dfs_a(0);  // 1.
        System.out.println(res);
    }

    void dfs_a(int a) {
        if (a > N) return;
        if (a > 0) dfs_c(a, 0);  // 2. 这里改为dfs_b也可以，保证等式恒等就行
        for (int i = 1; i <= 9; i++) {
            if (st[i] == 0) {
                st[i] = 1;
                dfs_a(a*10+i);
                st[i] = 0;
            }
        }
    }

    void dfs_c(int a, int c) {
        if (check(a, c)) res++;  // 3.
        for (int i = 1; i <= 9; i++) {
            if (st[i] == 0) {
                st[i] = 1;
                dfs_c(a, c*10+i);
                st[i] = 0;
            }
        }
    }

    boolean check(int a, int c) {
        int b = N*c - a*c;
        if(a <= 0 || b <= 0 || c <= 0) return false;
        System.arraycopy(st, 1, backup, 1, 9);
        int tmp;
        while (b > 0) {  // 求出目标b，记录每位情况
            tmp = b%10;
            b /= 10;
            if (backup[tmp] == 1) return false;
            backup[tmp] = 1;
        }

        for (int i = 1; i < st.length; i++) {
            if (backup[i] == 0) return false;
        }
        return true;
    }
}
```

