# 139. 回文子串的最大长度

如果一个字符串正着读和倒着读是一样的，则称它是回文的。

给定一个长度为 N 的字符串 S，求他的最长回文子串的长度是多少。

#### 输入格式

输入将包含最多 30 个测试用例，每个测试用例占一行，以最多 1000000 个小写字符的形式给出。

输入以一个以字符串 `END` 开头的行表示输入终止。

#### 输出格式

对于输入中的每个测试用例，输出测试用例编号和最大回文子串的长度（参考样例格式）。

每个输出占一行。

#### 输入样例：

```
abcbabcbabcba
abacacbaaaab
END
```

#### 输出样例：

```
Case 1: 13
Case 2: 6
```



## Manacher

```java
import java.io.*;

public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    char[] chs, str = new char[22000010];
    int[] pos = new int[22000010];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        int T = 1;
        while (true) {
            chs = br.readLine().toCharArray();
            if (chs[0] == 'E') break;
            bw.write("Case " + (T++) + ": " + manacher() + "\n");
        }
        bw.flush(); bw.close();
    }

    int manacher() {
        int res = -1, len = init(), mx = 0, id = 0;
        for (int i = 1; i < len; i++) {
            if (i < mx) pos[i] = Math.min(pos[id*2-i], mx-i);
            else pos[i] = 1;
            while (str[i+pos[i]]==str[i-pos[i]]) pos[i]++;
            if (pos[i]+i > mx) {
                mx = pos[i] + i;
                id = i;
            }
            res = Math.max(res, pos[i]-1);
        }
        return res;
    }

    int init() {
        str[0] = '@'; str[1] = '#';
        int j = 2;
        for (char ch : chs) {
            str[j++] = ch;
            str[j++] = '#';
        }
        str[j] = '\0';
        return j;
    }
}
```



## Hash

```java
import java.io.*;

public class Main {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    int maxN = (int) (2e6+10), P = 131;
    long[] h1 = new long[maxN], h2 = new long[maxN], p = new long[maxN];
    char[] arr = new char[maxN];

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        int T = 1;
        while (true) {
            String str = "#"+br.readLine();
            int N = str.length();
            for (int i = 0; i < N; i++) arr[i] = str.charAt(i);
            if (arr[1] == 'E') break;
            for (int i = N*2; i > 0; i -= 2) {
                arr[i] = arr[i/2];
                arr[i-1] = '{';
            }
            N *= 2;
            p[0] = 1;
            for (int i = 1, j = N; i <= N; i++, j--) {
                h1[i] = h1[i-1]*P + arr[i];
                h2[i] = h2[i-1]*P + arr[j];
                p[i] = p[i-1]*P;
            }

            int res = 0;
            for (int i = 1; i <= N; i++) {  // 枚举中点
                int le = 0, ri = Math.min(i-1, N-i);
                while (le < ri) {  // 二分半径长度
                    int mid = (le+ri)>>1;  // get(h2, 逆序)
                    if (get(h1, i-mid, i-1) != get(h2, N-(mid+i)+1, N-(i+1)+1)) {
                        ri = mid;
                    } else {
                        le = mid+1;
                    }
                }
                le--;
                if (arr[i-le] != '{') res = Math.max(res, le+1);
                else res = Math.max(res, le);
            }
            bw.write("Case "+(T++)+": "+res+"\n");
        }
        bw.flush(); bw.close();
    }

    long get(long[] h, int le, int ri) {
        return h[ri] - h[le-1] * p[ri-le+1];
    }
}
```

