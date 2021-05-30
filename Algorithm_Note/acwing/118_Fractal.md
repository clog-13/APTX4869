## 118. 分形



分形，具有以非整数维形式充填空间的形态特征。

通常被定义为“一个粗糙或零碎的几何形状，可以分成数个部分，且每一部分都（至少近似地）是整体缩小后的形状”，即具有自相似的性质。

现在，定义“盒子分形”如下：

一级盒子分形：

```
   X
```

二级盒子分形：

```
   X X
    X
   X X
```

如果用 B(n−1) 代表第 n−1 级盒子分形，那么第 n 级盒子分形即为：

```
  B(n - 1)        B(n - 1)

          B(n - 1)

  B(n - 1)        B(n - 1)
```

你的任务是绘制一个 n 级的盒子分形。

#### 输入格式

输入包含几个测试用例。

输入的每一行包含一个不大于 7 的正整数 n，代表要输出的盒子分形的等级。

输入的最后一行为 −1，代表输入结束。

#### 输出格式

对于每个测试用例，使用 `X` 符号输出对应等级的盒子分形。

请注意 `X` 是一个大写字母。

每个测试用例后输出一个独立一行的短划线。

#### 输入样例：

```
1
2
3
4
-1
```

#### 输出样例

```
X
-
X X
 X
X X
-
X X   X X
 X     X
X X   X X
   X X
    X
   X X
X X   X X
 X     X
X X   X X
-
X X   X X         X X   X X
 X     X           X     X
X X   X X         X X   X X
   X X               X X
    X                 X
   X X               X X
X X   X X         X X   X X
 X     X           X     X
X X   X X         X X   X X
         X X   X X
          X     X
         X X   X X
            X X
             X
            X X
         X X   X X
          X     X
         X X   X X
X X   X X         X X   X X
 X     X           X     X
X X   X X         X X   X X
   X X               X X
    X                 X
   X X               X X
X X   X X         X X   X X
 X     X           X     X
X X   X X         X X   X X
-
```

## 递归

```java
import java.util.*;
import java.io.*;

public class Main {
    static int[][] arr = new int[1010][1010];

    public static void main(String[] args) throws IOException {
        arr[0][0] = 1;
        dfs(7);
        Scanner sc = new Scanner(System.in);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        while (true) {
            int N = sc.nextInt(), len = 1;
            if (N == -1) break;
            while (--N > 0) len *= 3;
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    if (arr[i][j] == 1) bw.write("X");
                    else bw.write(" ");
                } bw.write("\n");
            } bw.write("-"+"\n");
        } bw.close();
    }

    static void dfs(int n) {
        if (n == 1) return;
        dfs(n-1);

        int len = 1;
        for (int i = 0; i < n-2; i++) len *= 3;
        int[] dx = {0, 1, 2, 2}, dy = {2, 1, 0, 2};  // 右上，中间，左下，右下
        for (int k = 0; k < 4; k++) {  // 复制四份
            for (int i = 0; i < len; i++) {  // src, srcPos, dest, destPos, len
                System.arraycopy(arr[i], 0, arr[i + dx[k]*len], dy[k]*len, len);
            }
        }
    }
}
```

