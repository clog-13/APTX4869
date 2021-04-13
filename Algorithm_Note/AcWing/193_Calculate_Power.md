# 193. 算乘方的牛

约翰的奶牛希望能够非常快速地计算一个数字的整数幂 P 是多少，这需要你的帮助。

在它们计算得到最终结果的过程中只能保留两个工作变量用于中间结果。

第一个工作变量初始化为 x，第二个工作变量初始化为 1。

奶牛可以将任意一对工作变量相乘或相除（可以是一个工作变量与自己相乘或相除），并将结果储存在任意一个工作变量中，但是所有结果都只能储存为整数。

举个例子，如果它们想要得到 x 的 31 次方，则得到这一结果的一种执行方法如下所示：

```
                                         工作变量1  工作变量2

                                  开始 :   x        1

     工作变量1与本身相乘，结果置于工作变量2:   x        x^2

     工作变量2与本身相乘，结果置于工作变量2:   x        x^4

     工作变量2与本身相乘，结果置于工作变量2:   x        x^8

     工作变量2与本身相乘，结果置于工作变量2:   x        x^16

     工作变量2与本身相乘，结果置于工作变量2:   x        x^32

  工作变量2除以工作变量1，结果置于工作变量2：  x        x^31
```

因此，x 的 31 次方经过六个操作就可得到。

现在给出你希望求得的具体次幂数，请你计算至少需要多少个操作才能得到。

#### 输入格式

输入包含一个整数 P，表示具体次幂数。

#### 输出格式

输出包含一个整数，表示所需最少操作数。

#### 数据范围

1≤P≤20000

#### 输入样例：

```
31
```

#### 输出样例：

```
6
```



## DFS

```java
import java.util.*;

class Main {
    int N;
    public static void main(String[] args) {
        new Main().init();
    }

    void init() {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        int depth = 0;
        while (!dfs(1, 0, 0, depth)) depth++;
        System.out.println(depth);
    }

    boolean dfs(int a, int b, int u, int depth) {
        if (a == N) return true;
        if (u == depth || (a<<(depth-u)) < N) return false;  // 可行性剪枝
        if (N % gcd(a, b) != 0) return false;  // 设gcd(a,b)=d，那么不管之后怎么操作，得到的次数一定会是d的倍数

        int na, nb;
        na = a+a; nb = a;
        if (nb>0 && dfs(na, nb, u+1, depth)) return true;
        na = a+a; nb = b;
        if (nb>0 && dfs(na, nb, u+1, depth)) return true;

        na = a+b; nb = a;
        if (nb>0 && dfs(na, nb, u+1, depth)) return true;
        na = a+b; nb = b;
        if (nb>0 && dfs(na, nb, u+1, depth)) return true;

        na = Math.max(b+b, a); nb = Math.min(b+b, a);
        if (nb>0 && dfs(na, nb, u+1, depth)) return true;
        na = Math.max(b+b, b); nb = Math.min(b+b, b);
        if (nb>0 && dfs(na, nb, u+1, depth)) return true;

        na = Math.max(a-b, a); nb = Math.min(a-b, a);
        if (nb>0 && dfs(na, nb, u+1, depth)) return true;
        na = Math.max(a-b, b); nb = Math.min(a-b, b);
        if (nb>0 && dfs(na, nb, u+1, depth)) return true;

        return false;
    }

    int gcd(int a, int b) {
        return b==0 ? a : gcd(b, a%b);
    }
}
```

