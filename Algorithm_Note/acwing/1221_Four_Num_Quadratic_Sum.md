# 1221. 四平方和

四平方和定理，又称为拉格朗日定理：

每个正整数都可以表示为至多 44 个正整数的平方和。

如果把 00 包括进去，就正好可以表示为 44 个数的平方和。

比如：

5=0^2^+0^2^+1^2^+2^2^
7=1^2^+1^2^+1^2^+2^2^

对于一个给定的正整数，可能存在多种平方和的表示法。

要求你对 4 个数排序： 0≤a≤b≤c≤d

并对所有的可能表示法按 a,b,c,d 为联合主键升序排列，最后输出第一个表示法。

#### 输入格式

输入一个正整数 N。

#### 输出格式

输出4个非负整数，按从小到大排序，中间用空格分开。

#### 数据范围

0<N<5∗106

#### 输入样例：

```
5
```

#### 输出样例：

```
0 0 1 2
```



## 二分查找

```java
import java.util.*;

class Node{
    public int sum, c, d;
    public Node(int sum, int c, int d) {
        this.sum = sum;
        this.c = c;
        this.d = d;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        List<Node> arr = new ArrayList<>();
        int idx = 0;
        for (int c = 0; c*c <= N; c++) {
            int cc = c*c;
            for (int d = c; cc+d*d <= N; d++) {
                arr.add(new Node(cc+d*d, c, d));
                idx++;
            }
        }

        arr.sort((t0, t1) -> {
            if(t0.sum != t1.sum) return t0.sum - t1.sum;
            else if(t0.c != t1.c) return t0.c - t1.c;
            else return t0.d - t1.d;
        });

        for (int a = 0; a*a <= N; a++) {
            int aa = a*a;
            for (int b = 0; aa+b*b <= N; b++) {
                int tar =  N - aa - b*b;
                int le = 0, ri = idx;
                while (le < ri) {   // 左边界
                    int mid = (le+ri) >> 1;
                    if (arr.get(mid).sum < tar) {
                        le = mid + 1;
                    } else {
                        ri = mid;
                    }
                }
                if (le>=0 && le<arr.size() && arr.get(le).sum==tar) {
                    System.out.printf("%d %d %d %d", a, b, arr.get(le).c, arr.get(le).d);
                    return;
                }
            }
        }
    }
}
```

