# 2875. 超级胶水

小明有 n 颗石子，按顺序摆成一排。

他准备用胶水将这些石子粘在一起。

每颗石子有自己的重量，如果将两颗石子粘在一起，将合并成一颗新的石子，重量是这两颗石子的重量之和。

为了保证石子粘贴牢固，粘贴两颗石子所需要的胶水与两颗石子的重量乘积成正比，本题不考虑物理单位，认为所需要的胶水在数值上等于两颗石子重量的乘积。

每次合并，小明只能合并位置相邻的两颗石子，并将合并出的新石子放在原来的位置。

现在，小明想用最少的胶水将所有石子粘在一起，请帮助小明计算最少需要多少胶水。

#### 输入格式

输入的第一行包含一个整数 n，表示初始时的石子数量。

第二行包含 n 个整数 w1,w2,…,wn，依次表示每颗石子的重量。

#### 输出格式

一个整数表示答案。

#### 数据范围

1≤n≤10^5^, 1≤wi≤1000

#### 输入样例：

```
3
3 4 5
```

#### 输出样例：

```
47
```

## Math

当n=2时：ab
当n=3时：ab+(a+b)c=ac+(a+c)b=bc+(b+c)a=ab+ac+bc
当n=4时：ab+(a+b)c+(a+b+c)d=ac+(a+c)b+(a+b+c)d=.....=ab+ac+ad+bc+bd+cd

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) arr[i] = sc.nextInt();

        long res = 0, pre = 0;
        for (int i = 0; i < N; i++) {
            res += arr[i]*pre;
            pre += arr[i];
        }
        System.out.println(res);
    }
}
```

