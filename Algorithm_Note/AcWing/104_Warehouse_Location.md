# 104. 货仓选址

在一条数轴上有 N 家商店，它们的坐标分别为 A1∼AN。

现在需要在数轴上建立一家货仓，每天清晨，从货仓到每家商店都要运送一车商品。

为了提高效率，求把货仓建在何处，可以使得货仓到每家商店的距离之和最小。

#### 输入格式

第一行输入整数 N。

第二行 N 个整数 A1∼AN。

#### 输出格式

输出一个整数，表示距离之和的最小值。

#### 数据范围

1≤N≤100000, 0≤Ai≤40000

#### 输入样例：

```
4
6 2 9 1
```

#### 输出样例：

```
12
```



## 前缀

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        if (N == 1) {
            System.out.println(sc.nextInt());
            return;
        }

        int[] arr = new int[N];
        for (int i = 0; i < N; i++) arr[i] = sc.nextInt();
        Arrays.sort(arr);
        
        int res = 0;
        for (int i = 1; i < N; i++) res += arr[i] - arr[0];  // 初始化答案
        for (int idx = arr[0]+1; idx <= arr[N-1]; idx++) {   // 仓库选址下标
            int le = 0;     // le表示现在选址左边的仓库数
            for (int ti = 0; arr[ti] < idx; ti++) le++;  // 当前下标左右的仓库数
            res = Math.min(res, res - (N-le) + le);
        }
        System.out.println(res);
    }
}
```



## 数学

```
|x-a|+|x-b| >= |a-b|  // 可以从现实模型理解（绝对值表示数轴上两点的距离）
```

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) arr[i] = sc.nextInt();
        Arrays.sort(arr);

        int res = 0, mid = N>>1;
        for (int i = 0; i < N; i++) res += Math.abs((arr[mid] - arr[i]));
        System.out.println(res);
    }
}
```

