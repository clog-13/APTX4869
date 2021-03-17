# 112. 雷达设备

假设海岸是一条无限长的直线，陆地位于海岸的一侧，海洋位于另外一侧。

每个小岛都位于海洋一侧的某个点上。

雷达装置均位于海岸线上，且雷达的监测范围为 d，当小岛与某雷达的距离不超过 d 时，该小岛可以被雷达覆盖。

我们使用笛卡尔坐标系，定义海岸线为 x 轴，海的一侧在 x 轴上方，陆地一侧在 x 轴下方。

现在给出每个小岛的具体坐标以及雷达的检测范围，请你求出能够使所有小岛都被雷达覆盖所需的最小雷达数目。

#### 输入格式

第一行输入两个整数 n 和 d，分别代表小岛数目和雷达检测范围。

接下来 n 行，每行输入两个整数，分别代表小岛的 x，y 轴坐标。

同一行数据之间用空格隔开。

#### 输出格式

输出一个整数，代表所需的最小雷达数目，若没有解决方案则所需数目输出 −1。

#### 数据范围

1≤n≤1000

#### 输入样例：

```
3 2
1 2
-3 1
2 1
```

#### 输出样例：

```
2
```

## 贪心
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long D = sc.nextInt();

        double[][] data = new double[N][2];
        for (int i = 0; i < N; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            if (y > D) {
                System.out.println(-1);
                return;
            }
            double len = Math.sqrt(D*D - y*y);
            data[i][0] = x - len;
            data[i][1] = x + len;
        }

        // 这里不能这么写，要以右边界排序
        // Arrays.sort(data, (r1, r2) -> {
        //   if (r1[0] != r2[0]) return Double.compare(r1[0], r2[0]);
        //   else return Double.compare(r1[1], r2[1]);
        // });
        Arrays.sort(data, (r1, r2) -> {
            return Double.compare(r1[1], r2[1]);
        });

        int res = 1;
        double ri = data[0][1];
        for (int i = 1; i < N; i++) {
            if (data[i][0] > ri) {
                res++;
                ri = data[i][1];
            }
        }
        System.out.println(res);
    }
}
```