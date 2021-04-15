# ST ( Sparse Table ) 算法

ST（Sparse Table）算法是一个非常有名的在线处理RMQ问题的算法，它可以在O(nlogn)时间内进行预处理，然后在O(1)时间内回答每个查询。其思想就是保存以i为起点的某段数据的最小值。

### 预处理数据

预处理，用动态规划（DP）解决。思想接近于二路归并排序过程的分治思想。

预处理的时间是(NlogN)。

1. 既然是DP思想，首先要记录每步的状态。

```
设arr[i]是要求区间最值的数列，dp[i][j]表示从第 i 个数起连续 2^j 个数中的最大值。
```

> 例如：
>
> A数列为：`3 2 4 5 6 8 1 2 9 7`
>
> ```
> dp[1][0]表示第 1 个数起，长度为 2^0=1 的最大值。
> 同理 : dp[1][1] = max(3,2) = 3, dp[1][2]=max(3,2,4,5) = 5，dp[1][3] = max(3,2,4,5,6,8,1,2) = 8;
> ```

1. 状态转移方程
   既然有每步的状态了，开始找状态转移方程。

```
dp[i][j] = max(F[i][j-1], dp[i + 2^(j-1)][j-1]);
```

怎么理解呢？

> 最初始状态：dp\[0\]\[0\] = arr\[0\]; dp\[1\]\[0\] = arr\[1\]; dp\[2\]\[0\] = arr\[2\]，意思是说每单个元素的最大值都是自己（因为只有一个元素）
>
> 下一个状态：dp\[0\]\[1\] = max(dp\[0\]\[0\], dp\[1\]\[0\]) = max(arr\[0\], arr\[1\]);意思是说把F\[0\]\[1\] 分成两段，例如要求得dp\[3\]\[2\] = max(arr[3] , arr[4], arr[5], arr[6]) 其实就是求四个元素的前两个以及后两个的最大值。在求dp\[3\]\[2\]的时候已经知道了dp\[3\]\[1\]和dp\[5\]\[1\]了。

代码如下：

```
void RMQ(int num){
    for (int j = 1; j < 31; j++) {
        for (int i = 0 ; i + (1<<j) -1 <= num; i++) {
        	maxNum[i][j] = max(maxNum[i][j-1], maxNum[i+(1<<(j-1))][j-1]);
			minNum[i][j] = min(minNum[i][j-1], minNum[i+(1<<(j-1))][j-1]);
        }
    }
}
```

### 查询区间

那么查询怎么做呢？毕竟我们存放的都是2次幂个数的最小值。假如查询的区间是奇数个，或不是2次幂个数怎么弄？

其实很简单，就是把头尾分开！

> 假如我们需要查询的区间为(i,j)，那么我们需要找到小于这个闭区间(左边界取i，右边界取j)的最大幂（可以重复，比如查询5，6，7，8，9，我们可以查询5678和6789)

1. 区间的长度为`j - i + 1`
2. 可以取`k=log2(j-i+1)` 区间是 1, 3 就取 `log2(3-1+1) = 1`小于等于区间的最大幂

> dp\[i\]\[k\] 是右边界起点向左找，dp\[ j - 2 ^ k + 1\]\[k\]是左边界作为终点，向右找起点。 一定要用小于等于区间的最大幂是因为要能够两个F加在一起能够覆盖整个区间。



```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] data = new int[N];
        for (int i = 1 ;i <= N; i++)
            data[i] = sc.nextInt();

        int[][] dp = new int[200010][18];
        for (int j = 0; j < 18; j++) {
            for (int i = 1; i+(1<<j)-1 <= N; i++) {
                if (j == 0) dp[i][j] = data[i];
                else dp[i][j] = Math.max(dp[i][j-1], dp[i+(1<<(j-1))][j-1]);
            }
        }

        int M = sc.nextInt();
        while (M-- > 0) {
            int le = sc.nextInt(), ri = sc.nextInt();
            int len = ri - le + 1;
            int k = (int) (Math.log(len) / Math.log(2));	// Math.log(len)/Math.log(2) == log2(len);
            int res = Math.max(dp[le][k], dp[ri-(1<<k)+1][k]);
            System.out.println(res);
        }
    }
}
```

