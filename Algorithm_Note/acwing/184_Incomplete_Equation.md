# 184. 虫食算

所谓虫食算，就是原先的算式中有一部分被虫子啃掉了，需要我们根据剩下的数字来判定被啃掉的字母。

来看一个简单的例子：

```
 43#9865#045
+  8468#6633
--------------
 44445506978
```

其中#号代表被虫子啃掉的数字。

根据算式，我们很容易判断：第一行的两个数字分别是5和3，第二行的数字是5。

现在，我们对问题做两个限制：

首先，我们只考虑加法的虫食算。这里的加法是N进制加法，算式中三个数都有N位，允许有前导的0。

其次，虫子把所有的数都啃光了，我们只知道哪些数字是相同的，我们将相同的数字用相同的字母表示，不同的数字用不同的字母表示。

如果这个算式是N进制的，我们就取英文字母表的前N个大写字母来表示这个算式中的0到N-1这N个不同的数字：但是这N个字母并不一定顺序地代表0到N-1。

输入数据保证N个字母分别至少出现一次。

```
   BADC
+  CBDA
----------
   DCCC
```

上面的算式是一个4进制的算式。

很显然，我们只要让ABCD分别代表0123，便可以让这个式子成立了。

你的任务是，对于给定的N进制加法算式，求出N个不同的字母分别代表的数字，使得该加法算式成立。

输入数据保证有且仅有一组解。

#### 输入格式

输入包含4行。

第一行有一个正整数N(N<=26)，后面的3行每行有一个由大写字母组成的字符串，分别代表两个加数以及和。

这3个字符串左右两端都没有空格，并且恰好有N位。

#### 输出格式

输出包含一行。

在这一行中，应当包含唯一的那组解。

解是这样表示的：输出N个数字，分别表示A，B，C……所代表的数字，相邻的两个数字用一个空格隔开，不能有多余的空格。

#### 输入样例：

```
5
ABCED
BDACE
EBBAA
```

#### 输出样例：

```
1 0 3 4 2
```



## DFS +  剪枝 

```java
import java.util.*;
import java.io.*;

public class Main{
    int N, maxN = 30;
    char[][] arr = new char[3][];
    int[] queue = new int[maxN], res = new int[maxN];
    boolean[] st = new boolean[maxN];

    public static void main(String[] args) throws IOException {
        new Main().init();
    }

    void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        for (int i = 0; i < 3; i++) arr[i] = br.readLine().toCharArray();

//        for (int i = 0; i < N; i++) queue[i] = i; // TLE
        for (int i = N-1, idx = 0; i >= 0; i--) {  // 最后一位
            for (int j = 2; j >= 0; j--) {   // 最后一行
                int n = arr[j][i] - 'A';
                if (!st[n]) {
                    st[n] = true;
                    queue[idx++] = n; // 低位的字母在dfs时可以先赋值，提前dfs退出，起剪枝作用
                }
            }
        }

        Arrays.fill(st, false); Arrays.fill(res, -1);
        dfs(0);
        for (int i = 0; i < N; i++) bw.write(res[i] + " ");
        bw.flush(); bw.close();
    }

    private boolean dfs(int u) {
        if (u == N) return true;

        for (int i = 0; i < N; i++) {
            if (!st[i]) {
                st[i] = true;
                res[queue[u]] = i;  // 设queue中第u个字母为 i
                if (check() && dfs(u+1)) return true;   // check在前有剪枝作用
                res[queue[u]] = -1;
                st[i] = false;
            }
        }
        return false;
    }

    boolean check() {  // 根据当前赋值，验证算式(按列从后到前,验证已赋值的是否冲突) 
        for (int i = N-1, carry = 0; i >= 0; i--) { 
            int a = res[arr[0][i]-'A'], b = res[arr[1][i]-'A'], s = res[arr[2][i]-'A'];
            if (a != -1 && b != -1 && s != -1) {  // 
                if (carry == -1) {  // 前面不确定，不能确定是否进位  (tips:java -a%b = -a)
                    if ((a+b)%N != s && (a+b+1)%N != s) return false;  // 当前列不匹配
                    if (i==0 && a+b >= N) return false;  // 最高位还有有进位
                } else {
                    if ((a+b+carry)%N != s) return false;   // 当前列不匹配
                    if (i==0 && a+b+carry >= N) return false;   // 最高位还有进位
                    carry = (a+b+carry) / N;  // 记录进位情况
                }
            } else {  // 不能确定当前列情况和进位
                carry = -1;
            }
        }
        return true;
    }
}
```

