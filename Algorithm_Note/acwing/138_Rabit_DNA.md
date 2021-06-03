# 138. 兔子与兔子

很久很久以前，森林里住着一群兔子。

有一天，兔子们想要研究自己的 DNA 序列。

我们首先选取一个好长好长的 DNA 序列（小兔子是外星生物，DNA 序列可能包含 26 个小写英文字母）。

然后我们每次选择两个区间，询问如果用两个区间里的 DNA 序列分别生产出来两只兔子，这两个兔子是否一模一样。

注意两个兔子一模一样只可能是他们的 DNA 序列一模一样。

#### 输入格式

第一行输入一个 DNA 字符串 S。

第二行一个数字 m，表示 m 次询问。

接下来 m 行，每行四个数字 l1,r1,l2,r2，分别表示此次询问的两个区间，注意字符串的位置从 1 开始编号。

#### 输出格式

对于每次询问，输出一行表示结果。

如果两只兔子完全相同输出 `Yes`，否则输出 `No`（注意大小写）。

#### 数据范围

1≤length(S), m≤1000000

#### 输入样例：

```
aabbaabb
3
1 3 5 7
1 3 6 8
1 2 1 2
```

#### 输出样例：

```
Yes
No
Yes
```



## Hash

```java
import java.io.*;
import java.util.*;

public class Main{
    long base = 131L, mod = Long.MAX_VALUE;
    List<Long> power = new ArrayList<>(), hash = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        char[] chs = br.readLine().toCharArray();
        power.add(1L);
        hash.add(0L);
        for (int i = 0 ; i < chs.length; i++) {
            hash.add((hash.get(i)*base%mod + (chs[i]-'a'+1))%mod);
            power.add(power.get(i)*base%mod);
        }

        int N = Integer.parseInt(br.readLine());
        for (int i = 0 ; i < N ; i++) {
            String[] str = br.readLine().split(" ");
            int l1 = Integer.parseInt(str[0]), r1 = Integer.parseInt(str[1]);
            int l2 = Integer.parseInt(str[2]), r2 = Integer.parseInt(str[3]);
            if (check(l1, r1, l2, r2)) bw.write("Yes\n");
            else bw.write("No\n");
        }
        bw.flush(); bw.close();
    }

    boolean check(int l1, int r1, int l2, int r2) {
        long h1 = hash.get(r1) - hash.get(l1-1) * power.get(r1-l1+1)%mod;
        long h2 = hash.get(r2) - hash.get(l2-1) * power.get(r2-l2+1)%mod;
        return h1 == h2;
    }
}
```

