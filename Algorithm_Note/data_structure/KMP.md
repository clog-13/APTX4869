# KMP

**Kmp算法**
判定 字符串P 是否是 字符串S 的字串，并求出 字符串P 在 字符串S 中首次出现的位置

N-pmt[N]: 循环节最小分片（第一个）

```java
class KMP {
    int[] pmt = new int[100010];

    int kmp(char[] s, char[] p) {
        kmp_init(p);
        int pos = 0, pre = 0;
        while (pos < s.length && pre < p.length) {
            if (pre == -1 || s[pos] == p[pre]) {
                pos++; pre++;
            } else {
                pre = pmt[pre];  // j位的下一个下标
            }
        }

        if (pre == p.length) return pos-p.length;
        else return -1;
    }
    
	// 部分匹配表，部分匹配值（前缀后缀的最长共有元素长度， Partial Match Table）
    void kmp_init(char[] p) {  // 下标从 0 开始的数组写法
        pmt[0] = -1;
        int pos = 0, pre = -1;
        while (pos < p.length) {
            if (pre == -1 || p[pos] == p[pre]) {  // p[pos](主部)后缀, p[pre]（匹部）前缀
                pmt[++pos] = ++pre;  // 如果，pos+1不匹配，比较pre+1的位置(这个暂时的pre就是pmt)
            } else {
                pre = pmt[pre];  // 前缀位置的pmt长度
            }
        }
    }
    
    void kmp_init(char[] p) {  // 下标从 1 开始的数组写法
        for (int pos = 2, pre = 0; pos < p.length; pos++) {
            while (pre > 0 && p[pos] != p[pre+1]) pre = pmt[pre];
            if (p[pos] == p[pre+1]) pre++;
            pmt[pos] = pre;
        }
    }
}
```



## 例题：831. KMP字符串

给定一个模式串 S，以及一个模板串 P，所有字符串中只包含大小写英文字母以及阿拉伯数字。

模板串 P 在模式串 S 中多次作为子串出现。

求出模板串 P 在模式串 S 中所有出现的位置的起始下标。

**输入格式**

第一行输入整数 N，表示字符串 P 的长度。 第二行输入字符串 P。

第三行输入整数 M，表示字符串 S 的长度。 第四行输入字符串 S。

**输出格式**

共一行，输出所有出现位置的起始下标（下标从 0 开始计数），整数之间用空格隔开。

**数据范围**

1≤N≤10^5, 1≤M≤10^6

**输入样例：**

```
3
aba
5
ababa
```

**输出样例：**

```
0 2
```



```java
import java.io.*;

class Main {
    int[] pmt = new int[1000010];
    char[] p, s;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        br.readLine(); p = br.readLine().toCharArray();
        br.readLine(); s = br.readLine().toCharArray();

        kmp();

        bw.close();
    }

    void kmp() throws IOException {
        kmp_init();
        int pos = 0, pre = 0;
        while (pos < s.length && pre < p.length) {
            if (pre == -1 || s[pos] == p[pre]) {
                pos++; pre++;
            } else {
                pre = pmt[pre];  // j位的下一个下标
            }
            if (pre == p.length) {
                bw.write((pos-p.length) + " ");
                pre = pmt[pre];
            }
        }
    }

    // 部分匹配表，部分匹配值（前缀后缀的最长共有元素长度， Partial Match Table）
    void kmp_init() {  // 下标从 0 开始的数组写法
        pmt[0] = -1;
        int pos = 0, pre = -1;
        while (pos < p.length) {
            if (pre == -1 || p[pos] == p[pre]) {  // p[pos](主部)后缀, p[pre]（匹部）前缀
                pmt[++pos] = ++pre;  // 如果，pos+1不匹配，比较pre+1的位置(这个暂时的pre就是pmt)
            } else {
                pre = pmt[pre];  // 前缀位置的pmt长度
            }
        }
    }
}
```



## 例题：141. 周期

一个字符串的前缀是从第一个字符开始的连续若干个字符，例如 `abaab` 共有 5 个前缀，分别是 `a`，`ab`，`aba`，`abaa`，`abaab`。

我们希望知道一个 N 位字符串 S 的前缀是否具有循环节。

换言之，对于每一个从头开始的长度为 i（i>1）的前缀，是否由重复出现的子串 A 组成，即 AAA…A （A 重复出现 K 次,K>1）。

如果存在，请找出最短的循环节对应的 K 值（也就是这个前缀串的所有可能重复节中，最大的 K 值）。

**输入格式**

输入包括多组测试数据，每组测试数据包括两行。

第一行输入字符串 S 的长度 N。

第二行输入字符串 S。

输入数据以只包括一个 0 的行作为结尾。

**输出格式**

对于每组测试数据，第一行输出 `Test case #` 和测试数据的编号。

接下来的每一行，输出具有循环节的前缀的长度 i 和其对应 K，中间用一个空格隔开。

前缀长度需要升序排列。

在每组测试数据的最后输出一个空行。

**数据范围**

2≤N≤1000000

**输入样例：**

```
3
aaa
4
abcd
12
aabaabaabaab
0
```

**输出样例：**

```
Test case #1
2 2
3 3

Test case #2

Test case #3
2 2
6 2
9 3
12 4
```

```java
import java.io.*;

public class Main {
    int N;
    int[] pmt = new int[1000002];
    char[] arr;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        new Main().run();}

    void run() throws IOException {
        int T = 1;
        while (true) {
            N = Integer.parseInt(br.readLine());
            if (N == 0) break;
            arr = (" "+br.readLine()).toCharArray();
            bw.write("Test case #"+(T++)+"\n");
            kmp();
        }
        bw.close();
    }

    void kmp() throws IOException {
        kmp_init();

        for (int i = 2; i <= N; i++) {
            int t = i - pmt[i];
            if (i > t && i%t == 0) {  // pmt[i] > i/2 && t (循环分节)
                bw.write(i+" "+(i/t)+"\n");
            }
        }
        bw.write("\n");
    }

    void kmp_init() {
        for (int i = 2, j = 0; i <= N; i++) {
            while (j > 0 && arr[i] != arr[j+1]) j = pmt[j];
            if (arr[i] == arr[j+1]) j++;
            pmt[i] = j;
        }
    }
}
```

