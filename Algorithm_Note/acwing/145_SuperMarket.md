# 145. 超市

超市里有 N 件商品，每件商品都有利润 pi 和过期时间 di，每天只能卖一件商品，过期商品不能再卖。

求合理安排每天卖的商品的情况下，可以得到的最大收益是多少。

#### 输入格式

输入包含多组测试用例。

每组测试用例，以输入整数 N 开始，接下来输入 N 对 pi 和 di，分别代表第 i 件商品的利润和过期时间。

在输入中，数据之间可以自由穿插任意个空格或空行，输入至文件结尾时终止输入，保证数据正确。

#### 输出格式

对于每组产品，输出一个该组的最大收益值。

每个结果占一行。

#### 数据范围

0≤N≤10000, 1≤pi,di≤10000, 最多有 14 组测试样例

#### 输入样例：

```
4  50 2  10 1   20 2   30 1

7  20 1   2 1   10 3  100 2   8 2
   5 20  50 10
```

#### 输出样例：

```
80
185
```



## Greedy

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int N = sc.nextInt();
            Attribute[] arr = new Attribute[N];
            for (int i = 0; i < N; i++) {  // 读入数据
                int val = sc.nextInt(), time = sc.nextInt();
                arr[i] = new Attribute(val, time);
            }
            Arrays.sort(arr, (a, b) -> (a.endTime - b.endTime));  // 过期日期升序
            PriorityQueue<Integer> heap = new PriorityQueue<>();  // 价格最小堆
            for (int i = 0; i < N; i++) {
                heap.add(arr[i].val);  // size：售卖天数，代码保证局部最优性
                // 即前i物品中不管如何最多只能其中卖endTime件，除去之中价格最小的
                if (arr[i].endTime < heap.size()) heap.poll();
            }
            int res = 0;
            while (!heap.isEmpty()) res += heap.poll();
            System.out.println(res);
        }
    }

    static class Attribute {
        int val, endTime;

        public Attribute(int v, int t) {
            val = v; endTime = t;
        }
    }
}
```

