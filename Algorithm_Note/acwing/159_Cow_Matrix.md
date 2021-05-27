# 159. 奶牛矩阵

每天早上，农夫约翰的奶牛们被挤奶的时候，都会站成一个 R 行 C 列的方阵。

现在在每个奶牛的身上标注表示其品种的大写字母，则所有奶牛共同构成了一个 R 行 C 列的字符矩阵。

现在给定由所有奶牛构成的矩阵，求它的最小覆盖子矩阵的面积是多少。

如果一个子矩阵无限复制扩张之后得到的矩阵能包含原来的矩阵，则称该子矩阵为覆盖子矩阵。

#### 输入格式

第 1 行：输入两个用空格隔开的整数，R 和 C。

第 2..R+1 行：描绘由奶牛构成的 R 行 C 列的矩阵，每行 C 个字符，字符之间没有空格。

#### 输出格式

输出最小覆盖子矩阵的面积。（每个字符的面积为 1）

#### 数据范围

1≤R≤10000, 1≤C≤75

#### 输入样例：

```
2 5
ABABA
ABABA
```

#### 输出样例：

```
2
```

#### 提示

样例中给出的矩阵的最小覆盖子矩阵为 AB(非精确覆盖)，面积为 2。



## KMP

```java
import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int R = sc.nextInt(), C = sc.nextInt(); sc.nextLine();
        char[][] arr = new char[R+1][];

        for (int i = 0; i < R; i++) arr[i] = sc.nextLine().toCharArray();

        int width = -1;
        for (int j = 1; j <= C; j++) {  // 枚举宽度
            int i = 0;
            for ( ; i < R; i++) {
                if (notLoop(arr[i], j)) break;
            }
            if (i == R) {  // 当前长度所取 对所有列 都符合 循环
                width = j; break;
            }
        }

       
        String[] p = new String[R+1];
        for (int i = 0; i < R; i++) p[i+1] = new String(arr[i], 0, width);
        
        int[] pmt = new int[R+1];
        int pos = 2, pre = 0;
        for ( ; pos <= R; pos++) {
            while (pre != 0 && !p[pos].equals(p[pre+1])) pre = pmt[pre];
            if (p[pos].equals(p[pre+1])) pre++;
            pmt[pos] = pre;
        }

        int res = (R - pmt[R]) * width;  // pmt[R]前后缀最长相同长度 （循环的最小分片）
        System.out.println(res);
    }

    static boolean notLoop(char[] chs, int j) {
        for (int i = 0; i < chs.length; i++) {
            if (chs[i] != chs[i%j]) return true;
        }
        return false;
    }
}
```

