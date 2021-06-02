# 284. 金字塔

虽然探索金字塔是极其老套的剧情，但是有一队探险家还是到了某金字塔脚下。

经过多年的研究，科学家对这座金字塔的内部结构已经有所了解。

首先，金字塔由若干房间组成，房间之间连有通道。

如果把房间看作节点，通道看作边的话，整个金字塔呈现一个有根树结构，节点的子树之间有序，金字塔有唯一的一个入口通向树根。

并且，每个房间的墙壁都涂有若干种颜色的一种。

探险队员打算进一步了解金字塔的结构，为此，他们使用了一种特殊设计的机器人。

这种机器人会从入口进入金字塔，之后对金字塔进行深度优先遍历。

机器人每进入一个房间（无论是第一次进入还是返回），都会记录这个房间的颜色。

最后，机器人会从入口退出金字塔。

显然，机器人会访问每个房间至少一次，并且穿越每条通道恰好两次（两个方向各一次）， 然后，机器人会得到一个颜色序列。

但是，探险队员发现这个颜色序列并不能唯一确定金字塔的结构。

现在他们想请你帮助他们计算，对于一个给定的颜色序列，有多少种可能的结构会得到这个序列。

因为结果可能会非常大，你只需要输出答案对10^9^ 取模之后的值。

#### 输入格式

输入仅一行，包含一个字符串 S，长度不超过 300，表示机器人得到的颜色序列。

#### 输出格式

输出一个整数表示答案。

#### 输入样例：

```
ABABABA
```

#### 输出样例：

```
5
```



## DP

```java
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] str = br.readLine().split("");
        int N = str.length, mod = 10000_00000;
        if (N%2==0) System.out.println(0);
        else {
            int[][] f = new int[310][310];
            for (int len = 1; len <= N; len+=2) {
                for (int le = 0; le+len-1 < N; le++) {
                    int ri = le+len-1;
                    if (len==1) f[le][ri] = 1;
                    else if (str[le].equals(str[ri])) {  // 否则不谈
                        for (int k = le; k < ri; k+=2) {  // k 为当前区间的根节点
                            if (str[k].equals(str[ri])) {  // 否则不谈
                                f[le][ri] = (int) ((f[le][ri] + (long)f[le][k]*f[k+1][ri-1])%mod);
                            }
                        }
                    }
                }
            }
            System.out.println(f[0][N-1]);
        }

        bw.flush(); bw.close();
    }
}
```

