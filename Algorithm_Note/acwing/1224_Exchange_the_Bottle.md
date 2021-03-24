# 1224. 交换瓶子

有 N 个瓶子，编号 1∼N，放在架子上。

比如有 5 个瓶子：

```
2 1 3 5 4
```

要求每次拿起 2 个瓶子，交换它们的位置。

经过若干次后，使得瓶子的序号为：

```
1 2 3 4 5
```

对于这么简单的情况，显然，至少需要交换 2 次就可以复位。

如果瓶子更多呢？你可以通过编程来解决。

#### 输入格式

第一行包含一个整数 N，表示瓶子数量。

第二行包含 N 个整数，表示瓶子目前的排列状况。

#### 输出格式

输出一个正整数，表示至少交换多少次，才能完成排序。

#### 数据范围

1≤N≤10000

#### 输入样例：

```
5
3 1 2 5 4
```

#### 输出样例：

```
3
```



```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] data = new int[N+1];
        for (int i = 1; i <= N; i++) {
            data[i] = sc.nextInt();
        }

        int cout = 0;
        boolean[] st = new boolean[N+1];
        for (int i = 1; i <= N; i++) {
            if (!st[i]) {
                cout++;  // 联通环数量
                while (!st[i]) {
                    st[i] = true;
                    i = data[i];
                }
            }
        }
        System.out.println(N - cout);
    }
}
```



```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] data = new int[N+1];
        for (int i = 1; i <= N; i++) {
            data[i] = sc.nextInt();
        }

        int res = 0;
        boolean[] st = new boolean[N+1];
        for (int i = 1; i <= N; i++) {
            if (!st[i]) res--;
            while (!st[i]) {
                res++;  // 交换次数
                st[i] = true;
                i = data[i];
            }
        }
        System.out.println(res);
    }
}
```

