# 1192. 奖金

由于无敌的凡凡在2005年世界英俊帅气男总决选中胜出，Yali Company总经理Mr.Z心情好，决定给每位员工发奖金。

公司决定以每个人本年在公司的贡献为标准来计算他们得到奖金的多少。

于是Mr.Z下令召开 mm 方会谈。

每位参加会谈的代表提出了自己的意见：“我认为员工 aa 的奖金应该比 bb 高！”

Mr.Z决定要找出一种奖金方案，满足各位代表的意见，且同时使得总奖金数最少。

每位员工奖金最少为100元，且必须是整数。

#### 输入格式

第一行包含整数 n,mn,m，分别表示公司内员工数以及参会代表数。

接下来 mm 行，每行 22 个整数 a,ba,b，表示某个代表认为第 aa 号员工奖金应该比第 bb 号员工高。

#### 输出格式

若无法找到合理方案，则输出“Poor Xed”；

否则输出一个数表示最少总奖金。

#### 数据范围

1≤n≤100001≤n≤10000,
1≤m≤200001≤m≤20000

#### 输入样例：

```
2 1
1 2
```

#### 输出样例：

```
201
```

## 拓扑排序

```java
import java.io.*;
import java.util.*;

public class Main {
    static int N, M, maxN = 20010;
    static int idx, qidx;
    static int[] info = new int[maxN], from = new int[maxN], to = new int[maxN];
    static int[] cout = new int[maxN], dist = new int[maxN];
    static int[] qv = new int[maxN];
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        M = Integer.parseInt(str[1]);

        Arrays.fill(info, -1);
        while (M-- > 0) {
            str = br.readLine().split(" ");
            int a = Integer.parseInt(str[0]);
            int b = Integer.parseInt(str[1]);
            add(b, a);
            cout[a]++;
        }

        if (!topsort()) System.out.println("Poor Xed");
        else {
            Arrays.fill(dist, 100);
            for (int i = 0; i < N; i++) {
                int cur = qv[i];
                for (int j = info[cur]; j != -1; j = from[j]) {
                    int t = to[j];
                    dist[t] = Math.max(dist[t], dist[cur]+1);
                }
            }
            
            int res = 0;
            for (int i = 1; i <= N; i++) res += dist[i];
            System.out.println(res);
        }
    }

    static boolean topsort() {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if (cout[i] == 0) {
                queue.add(i);
                qv[qidx++] = i;
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int i = info[cur]; i != -1; i = from[i]) {
                int t = to[i];
                if (--cout[t] == 0) {
                    queue.add(t);
                    qv[qidx++] = t;
                }
            }
        }
        return qidx == N;
    }

    static void add(int a, int b) {
        from[idx]  = info[a];
        to[idx] = b;
        info[a] = idx++;
    }
}
```

