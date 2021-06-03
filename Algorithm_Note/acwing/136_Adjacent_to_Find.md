# 136. 邻值查找

给定一个长度为 n 的序列 A，A 中的数各不相同。

对于 A 中的每一个数 Ai，求： min|Ai−Aj| (1≤j<i)

以及令上式取到最小值的 j（记为 Pi）。若最小值点不唯一，则选择使 Aj 较小的那个。

#### 输入格式

第一行输入整数 n，代表序列长度。

第二行输入 n 个整数 A1…An ,代表序列的具体数值，数值之间用空格隔开。

#### 输出格式

输出共 n−1 行，每行输出两个整数，数值之间用空格隔开。

分别表示当 i 取 2∼n 时，对应的 min|Ai−Aj|  (1≤j<i)和 Pi 的值。

#### 数据范围

n≤10^5,|Ai|≤10^9

#### 输入样例：

```
3
1 5 3
```

#### 输出样例：

```
4 1
2 1
```



## 邻接表

```java
import java.util.*;
import java.io.*;

class Main {
    int N, maxN = 100010;
    int[] les = new int[maxN], ris = new int[maxN], map = new int[maxN];
    Node[] arr = new Node[maxN], res = new Node[maxN];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(br.readLine());
        String[] str = br.readLine().split(" ");
        for (int i = 1; i <= N; i++) {  // 读入数据
            arr[i] = new Node(Integer.parseInt(str[i-1]), i);  // (值，下标)
        }
        Arrays.sort(arr, 1, N+1, (a, b) -> (int) (a.val - b.val));  // 值，升序排序
        for (int i = 1; i <= N; i++) {
            map[arr[i].id] = i;  // map[id] = i;
            les[i] = i-1; ris[i] = i+1;
        }
        arr[0] = new Node((long) (-2e9-1), -1); arr[N+1] = new Node((long) (2e9+1), -1);

        for (int i = N; i > 1; i--) {  // 倒序遍历 原始数组 每个数 （倒序，因为1<j<i）
            int px = map[i], le = les[px], ri = ris[px];  // px:排序后每个数新的下标
            long lv = Math.abs(arr[px].val - arr[le].val);
            long rv = Math.abs(arr[ri].val - arr[px].val);

            if (lv <= rv) res[i] = new Node(lv, arr[le].id);  // 比较出最小值（Aj最小）
            else res[i] = new Node(rv, arr[ri].id);

            les[ri] = le; ris[le] = ri;  // 删除节点，因为是倒序，当前点不能用作后续判断
        }

        for (int i = 2; i <= N; i++) bw.write(res[i].val+" "+ res[i].id +"\n");
        bw.flush(); bw.close();
    }

    static class Node {
        long val;
        int id;
        public Node(long v, int i) {
            val = v; id = i;
        }
    }
}
```



## TreeMap

```java
import java.util.*;
import java.io.*;

class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        int N = Integer.parseInt(br.readLine());
        String[] str = br.readLine().split(" ");
        int[] arr = new int[N + 1];
        for (int i = 1; i <= N; ++i) arr[i] = Integer.parseInt(str[i-1]);

        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(arr[1], 1);
        for (int i = 2; i <= N; ++i) {
            Integer le = map.floorKey(arr[i]), ri = map.ceilingKey(arr[i]);
            if (le == null) {
                bw.write((ri-arr[i])+" "+map.get(ri)+"\n");
            } else if (ri == null) {
                bw.write((arr[i]-le)+" "+map.get(le)+"\n");
            } else {
                if (arr[i]-le <= ri-arr[i]) {
                    bw.write((arr[i]-le)+" "+map.get(le)+"\n");
                } else {
                    bw.write((ri-arr[i])+" "+map.get(ri)+"\n");
                }
            }
            map.put(arr[i], i);
        }
        bw.flush(); bw.close();
    }
}
```

