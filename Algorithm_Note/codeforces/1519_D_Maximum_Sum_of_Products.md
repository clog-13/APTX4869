# D. Maximum Sum of Products

time limit per test:2 seconds / memory limit per test/256 megabytes

You are given two integer arrays aa and bb of length nn.

You can reverse **at most one** subarray (continuous subsegment) of the array aa.

Your task is to reverse such a subarray that the sum ∑i=1nai⋅bi∑i=1nai⋅bi is **maximized**.

## Input

The first line contains one integer nn (1≤n≤50001≤n≤5000).

The second line contains nn integers a1,a2,…,ana1,a2,…,an (1≤ai≤1071≤ai≤107).

The third line contains nn integers b1,b2,…,bnb1,b2,…,bn (1≤bi≤1071≤bi≤107).

## Output

Print single integer — maximum possible sum after reversing **at most one** subarray (continuous subsegment) of aa.

## Examples

**input**

```
5
2 3 2 1 3
1 3 2 4 2
```

**output**

```
29
```

**Note**

In the example, you can reverse the subarray \[4,5\]. Then a=[2,3,2,3,1] and 2⋅1+3⋅3+2⋅2+3⋅4+1⋅2=29.

In the second example, you don't need to use the reverse operation. 13⋅2+37⋅4=174.

In the third example, you can reverse the subarray \[3,5\]. Then a=[1,8,3,6,7,6] and 1⋅5+8⋅9+3⋅6+6⋅8+7⋅8+6⋅6=235.



## Brute Force

```java
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(br.readLine());
        String[] str1 = br.readLine().split(" "), str2 = br.readLine().split(" ");
        int[] arr = new int[N], brr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(str1[i]); brr[i] = Integer.parseInt(str2[i]);
        }
        long[] preFix = new long[N+1];
        for (int i = 0; i < N; i++) {
            preFix[i+1] += preFix[i] + (long) arr[i] * brr[i];
        }
        long res = preFix[N];
        for (int c = 0; c < N; c++) {  // reverse center
            long cur = (long) arr[c] * brr[c];
            for (int le = c-1, ri = c+1; le>=0 && ri<N; le--, ri++) {  // odd segment
                cur += (long) arr[le] * brr[ri];
                cur += (long) arr[ri] * brr[le];
                res = Math.max(res, cur + preFix[le] + (preFix[N] - preFix[ri+1]));
            }
            cur = 0;
            for (int le = c  , ri = c+1; le>=0 && ri<N; le--, ri++) {  // even segment
                cur += (long) arr[le] * brr[ri];
                cur += (long) arr[ri] * brr[le];
                res = Math.max(res, cur + preFix[le] + (preFix[N] - preFix[ri+1]));
            }
        }
        bw.write(res+"\n");
        bw.flush(); bw.close();
    }
}
```

