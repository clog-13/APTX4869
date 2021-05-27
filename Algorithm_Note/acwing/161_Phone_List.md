# 161. 电话列表

给出一个电话列表，如果列表中存在其中一个号码是另一个号码的前缀这一情况，那么就称这个电话列表是不兼容的。

假设电话列表如下：

- `Emergency 911`
- `Alice 97 625 999`
- `Bob 91 12 54 26`

在此例中，报警电话号码（`911`）为 Bob 电话号码（`91 12 54 26`）的前缀，所以该列表不兼容。

#### 输入格式

第一行输入整数 t，表示测试用例数量。

对于每个测试用例，第一行输入整数 n，表示电话号码数量。

接下来 n 行，每行输入一个电话号码，号码内数字之间无空格，电话号码不超过 10 位。

#### 输出格式

对于每个测试用例，如果电话列表兼容，则输出 `YES`。

否则，输出 `NO`。

#### 数据范围

1≤t≤40, 1≤n≤10000

#### 输入样例：

```
2
3
911
97625999
91125426
5
113
12340
123440
12345
98346
```

#### 输出样例：

```
NO
YES
```

## 字典树

```java
import java.util.*;

public class Main{
    int N, tot, res, maxN = 400010;
    int[][] tr = new int[maxN][10];
    int[] end = new int[maxN];

    public static void main(String[] args){
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- > 0) {
            init();

            N = sc.nextInt();
            sc.nextLine();

            for (int i = 0; i < N; i++) {
                char[] chs = sc.nextLine().toCharArray();
                insert(chs);
            }
            if (res == 1) System.out.println("NO");
            else System.out.println("YES");
        }
    }

    void insert(char[] chs) {
        int u = 1;
        for (char c : chs) {
            int ch = c - '0';
            if (tr[u][ch] == 0) tr[u][ch] = ++tot;
            u = tr[u][ch];
            if (end[u] != 0) res = 1;  // 插入过程中已存在前缀字符串
        }
        end[u]++;

        
        for(int i = 0; i < 10; i++) {  // 插入字符串是某个已存在字符串的前缀
            if (tr[u][i] != 0) res = 1;
        }
    }

    void init() {
        tot = 1; res = 0;
        for (int i = 0; i < maxN; i++) Arrays.fill(tr[i], 0);
        Arrays.fill(end, 0);
    }
}
```

