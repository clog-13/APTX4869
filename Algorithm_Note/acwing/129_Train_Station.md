# 129. 火车进栈

这里有 n 列火车将要进站再出站，但是，每列火车只有 1 节，那就是车头。

这 n 列火车按 1 到 n 的顺序从东方左转进站，这个车站是南北方向的，它虽然无限长，只可惜是一个死胡同，而且站台只有一条股道，火车只能倒着从西方出去，而且每列火车必须进站，先进后出。

也就是说这个火车站其实就相当于一个栈，每次可以让右侧头火车进栈，或者让栈顶火车出站。

车站示意如图：

```
              出站<——       <——进站
                     |车|
                     |站|
                     |__|
```

现在请你按《字典序》输出前 20 种可能的出栈方案。

#### 输入格式

输入一个整数 n，代表火车数量。

#### 输出格式

按照《字典序》输出前 20 种答案，每行一种，不要空格。

#### 数据范围

1≤n≤20

#### 输入样例：

```
3
```

#### 输出样例：

```
123
132
213
231
321
```



## 栈+DFS

 ```java
import java.io.*;
import java.util.*;

public class Main {
    int cnt, idx, N, stk[] = new int[30];
    List<Integer> list = new ArrayList<>();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        N = Integer.parseInt(br.readLine());
        dfs(1);
        bw.flush(); bw.close();
    }
    
    void dfs(int u) throws IOException {
        if (u > N) {
            if (++cnt > 20) return;
            for (Integer i : list) bw.write(i+"");
            for (int i = idx-1; i >= 0; i--) bw.write(stk[i]+"");  //输出栈中剩下的(后进先出
            bw.write("\n");
            return;
        }
        if (idx > 0) {  // 字典序
            list.add(stk[--idx]);
            dfs(u);  // 出站
            stk[idx++] = list.remove(list.size()-1);
        }
        stk[idx++] = u;
        dfs(u+1);   // 进站
        idx--;
    }
}
 ```



```java
import java.io.*;
import java.util.*;

public class Main {
    int cnt, N, stk[] = new int[30];
    List<Integer> list = new ArrayList<>();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        N = Integer.parseInt(br.readLine());
        dfs(0, 1);
        bw.flush(); bw.close();
    }

    void dfs(int top, int x) throws IOException {
        if (cnt >= 20) return;
        if (x > N && top == 0) {
            cnt++;
            for (Integer i : list) bw.write(i+"");
            bw.write("\n");
            return;
        }
        if (top > 0) {
            int backup = stk[top];
            list.add(stk[top]);
            dfs(top-1, x);  // out
            list.remove(list.size()-1);
            stk[top] = backup;
        }
        if (x <= N) {
            stk[top+1] = x;
            dfs(top+1, x+1);  // in
        }
    }
}
```

