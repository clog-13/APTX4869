# 247. 亚特兰蒂斯

有几个古希腊书籍中包含了对传说中的亚特兰蒂斯岛的描述。

其中一些甚至包括岛屿部分地图。

但不幸的是，这些地图描述了亚特兰蒂斯的不同区域。

您的朋友 Bill 必须知道地图的总面积。

你自告奋勇写了一个计算这个总面积的程序。

#### 输入格式

输入包含多组测试用例。

对于每组测试用例，第一行包含整数 n，表示总的地图数量。

接下来 n 行，描绘了每张地图，每行包含四个数字 xx1,y1,x2,y2（不一定是整数），(x1,y1) 和 (x2,y2) 分别是地图的左上角位置和右下角位置。

注意，坐标轴 x 轴从上向下延伸，y 轴从左向右延伸。

当输入用例 n=0 时，表示输入终止，该用例无需处理。

#### 输出格式

每组测试用例输出两行。

第一行输出 `Test case #k`，其中 k 是测试用例的编号，从 1 开始。

第二行输出 `Total explored area: a`，其中 a 是总地图面积（即此测试用例中所有矩形的面积并，注意如果一片区域被多个地图包含，则在计算总面积时只计算一次），精确到小数点后两位数。

在每个测试用例后输出一个空行。

#### 数据范围

1≤n≤10000, 0≤x1<x2≤100000, 0≤y1<y2≤100000
注意，本题 nn 的范围上限加强至 10000。

#### 输入样例：

```
2
10 10 20 20
15 15 25 25.5
0
```

#### 输出样例：

```
Test case #1
Total explored area: 180.00 
```

#### 样例解释

样例所示地图覆盖区域如下图所示，两个矩形区域所覆盖的总面积，即为样例的解。

![无标题.png](https://cdn.acwing.com/media/article/image/2019/12/26/19_4acba44c27-%E6%97%A0%E6%A0%87%E9%A2%98.png)



## 线段树

```java
import java.io.*;
import java.util.*;

class Main{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw  = new BufferedWriter(new OutputStreamWriter(System.out));
    int maxN = 100010, N;
    Node[] arr;
    Seg[] tr = new Seg[maxN*8];
    List<Double> list = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        int T = 1;
        while (true){
            N = Integer.parseInt(br.readLine());
            if (N == 0)  break;
            arr = new Node[N*2];
            for (int i = 0, idx = 0; i < N; i++) {
                String[] str = br.readLine().split(" ");
                double x1 = Double.parseDouble(str[0]), y1 = Double.parseDouble(str[1]);
                double x2 = Double.parseDouble(str[2]), y2 = Double.parseDouble(str[3]);
                arr[idx++] = new Node(x1, y1, y2, 1);
                arr[idx++] = new Node(x2, y1, y2, -1);  // 离散化 点
                list.add(y1); list.add(y2);  // 立散化 y下标
            }
            // 排序，去重 实现离散化
            Arrays.sort(arr, (o1, o2) -> (int) (o1.x - o2.x));
            Collections.sort(list);
            list = unique(list);

            build(1, 0, list.size());

            double res = 0;
            for (int i = 0; i < N*2; i++) {
                if (i > 0) res += tr[1].len * (arr[i].x - arr[i-1].x);
                update(1, query(arr[i].y1), query(arr[i].y2) - 1, arr[i].val);
            }

            bw.write("Test case #" + T++ + "\n");
            bw.write("Total explored area: " + String.format("%.2f", res) + "\n");
            bw.write("\n");
        }
        bw.flush();
    }

    void update(int u, int start, int end, int c) {
        if (tr[u].ri < start || tr[u].le > end) return;
        if (start <= tr[u].le && tr[u].ri <= end) {
            tr[u].cnt += c;
        } else {
            update(u<<1, start, end, c);
            update(u<<1|1, start, end, c);
        }
        pushUp(u);
    }

    int query(double target) {
        int le = 0, ri = list.size();
        while (le < ri) {
            int mid = le + ri >> 1;
            if (list.get(mid) < target)  le = mid+1;
            else ri = mid;
        }
        return le;
    }

    void pushUp(int u) {
        if (tr[u].cnt > 0) {  // 如果cnt>0, 则整个区间长度就是len
            // 因为存的是线段(区间), tr[u].r是线段(区间)从r到r+1的左端点, tr[u].r+1是线段(区间)从r到r+1的右端点
            tr[u].len = list.get(tr[u].ri+1) - list.get(tr[u].le); // 记录的是区间内包含线段的完整长度(查询代码需要)
        } else {
            if (tr[u].le != tr[u].ri) {
                tr[u].len = tr[u<<1].len + tr[u<<1|1].len;
            } else {
                tr[u].len = 0;
            }
        }
    }

    void build(int u, int le, int ri){
        tr[u] = new Seg(le, ri, 0, 0);
        if (le == ri) return;
        int mid = le + ri >> 1;
        build(u<<1, le, mid);
        build(u<<1|1, mid + 1, ri);  //不需要pushup,因为cnt和len都是0,pushup后结果一样
    }

    List<Double> unique(List<Double> list){
        List<Double> res = new ArrayList(list.size());
        for (Double t: list) {
            if (res.isEmpty() || !res.get(res.size()-1).equals(t)) res.add(t);
        }
        return res;
    }

    static class Node {
        double x, y1, y2;
        int val;
        public Node(double x, double y1, double y2, int val){
            this.x = x; this.y1 = y1; this.y2 = y2; this.val = val;
        }
    }

    static class Seg {
        int le, ri, cnt;  // 当前区间整个被覆盖的次数
        public double len;  // 不考虑祖先节点cnt的前提下， cnt>0的区间 总长
        public Seg(int l, int r, int c, double ln){
            le = l; ri = r; cnt = c; len = ln;
        }
    }
}
```

