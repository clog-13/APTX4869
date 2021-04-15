# 154. 滑动窗口

给定一个大小为 n≤106 的数组。

有一个大小为 k 的滑动窗口，它从数组的最左边移动到最右边。

你只能在窗口中看到 k 个数字。

每次滑动窗口向右移动一个位置。

以下是一个例子：

该数组为 `[1 3 -1 -3 5 3 6 7]`，k 为 3。

| 窗口位置            | 最小值 | 最大值 |
| :------------------ | :----- | :----- |
| [1 3 -1] -3 5 3 6 7 | -1     | 3      |
| 1 [3 -1 -3] 5 3 6 7 | -3     | 3      |
| 1 3 [-1 -3 5] 3 6 7 | -3     | 5      |
| 1 3 -1 [-3 5 3] 6 7 | -3     | 5      |
| 1 3 -1 -3 [5 3 6] 7 | 3      | 6      |
| 1 3 -1 -3 5 [3 6 7] | 3      | 7      |

你的任务是确定滑动窗口位于每个位置时，窗口中的最大值和最小值。

#### 输入格式

输入包含两行。

第一行包含两个整数 n 和 k，分别代表数组长度和滑动窗口的长度。

第二行有 n 个整数，代表数组的具体数值。

同行数据之间用空格隔开。

#### 输出格式

输出包含两个。

第一行输出，从左至右，每个位置滑动窗口中的最小值。

第二行输出，从左至右，每个位置滑动窗口中的最大值。

#### 输入样例：

```
8 3
1 3 -1 -3 5 3 6 7
```

#### 输出样例：

```
-1 -3 -3 -3 3 3
3 3 5 5 6 7
```



## 单调队列

```java
import java.io.*;

class Main {
    int maxN = 1000010;
    int[] arr = new int[maxN], q = new int[maxN];
    
    public static void main(String[] args) throws IOException {
        new Main().run();
    }
    
    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        String[] str = br.readLine().split(" ");
        int N = Integer.parseInt(str[0]), K = Integer.parseInt(str[1]);
        
        int hh = 0, tt = -1;
        str = br.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(str[i]);
            if (i >= q[hh]+K) hh++;
            while (hh <= tt && arr[i] <= arr[q[tt]]) tt--;  // 递增
            q[++tt] = i;
            if (i+1 >= K) bw.write(arr[q[hh]] + " "); 
        }
        bw.write("\n");
        
        hh = 0; tt = -1;
        for (int i = 0; i < N; i++) {
            if (i >= q[hh]+K) hh++;
            while (hh <= tt && arr[i] >= arr[q[tt]]) tt--;  // 递减
            q[++tt] = i;
            if (i+1 >= K) bw.write(arr[q[hh]] + " "); 
        }
        bw.flush();
    }
}
```

