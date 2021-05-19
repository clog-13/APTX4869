# B. AGAGA XOOORRR

Baby Ehab is known for his love for a certain operation. He has an array a of length n, and he decided to keep doing the following operation on it:

- he picks 2 adjacent elements; he then removes them and places a single integer in their place: their [bitwise XOR](https://en.wikipedia.org/wiki/Bitwise_operation#XOR). Note that the length of the array decreases by one.

Now he asks you if he can make all elements of the array equal. Since babies like to make your life harder, he requires that you leave at least 2 elements remaining.

**Input**

The first line contains an integer t (1≤t≤15) — the number of test cases you need to solve.

The first line of each test case contains an integers n (2≤n≤2000) — the number of elements in the array a.

The second line contains nn space-separated integers a1, a2, ……, an (0≤ai<230) — the elements of the array a.

**Output**

If Baby Ehab can make all elements equal while leaving at least 2 elements standing, print "YES". Otherwise, print "NO".

**Example**

**input**

```
2
3
0 2 2
4
2 3 1 10
```

**output**

```
YES
NO
```

**Note**

In the first sample, he can remove the first 2 elements, 0 and 2, and replace them by 0⊕2=2. The array will be \[2,2\], so all the elements are equal.

In the second sample, there's no way to make all the elements equal.

## Prefix

```java
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine());
            int[] pre = new int[N+1];
            String[] str = br.readLine().split(" ");
            for (int i = 1; i <= N; i++) {
                pre[i] = Integer.parseInt(str[i-1]) ^ pre[i-1];
            }
            boolean res = pre[N] == 0;
            for (int i = 1; i < N; i++) {
                if (res) break;
                for (int j = i+1; j < N; j++) {
                    res |= pre[N]==(pre[i]^pre[j]) && pre[N]==(pre[j]^pre[N]);
                }
            }
            if (res) bw.write("YES\n");
            else bw.write("NO\n");
        }
        bw.flush(); bw.close();
    }
}
```

```java
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine());
            String[] str = br.readLine().split(" ");
            int xr = 0;
            for (int i = 0; i < N; i++) xr ^= Integer.parseInt(str[i]);

            if (xr == 0) bw.write("YES\n");
            else {
                int t = 0, cnt = 0;
                for (int i = 0; i < N; i++) {
                    t ^= Integer.parseInt(str[i]);
                    if (t == xr) {
                        t = 0;
                        cnt++;
                    }
                }
                if (cnt >= 3) bw.write("YES\n");
                else bw.write("NO\n");
            }
        }
        bw.flush(); bw.close();
    }
}
```

