# 479. 加分二叉树

设一个n个节点的二叉树tree的中序遍历为（1,2,3,…,n），其中数字1,2,3,…,n为节点编号。

每个节点都有一个分数（均为正整数），记第i个节点的分数为di，tree及它的每个子树都有一个加分，任一棵子树subtree（也包含tree本身）的加分计算方法如下：     

subtree的左子树的加分 × subtree的右子树的加分 ＋ subtree的根的分数 

若某个子树为空，规定其加分为1。叶子的加分就是叶节点本身的分数，不考虑它的空子树。

试求一棵符合中序遍历为（1,2,3,…,n）且加分最高的二叉树tree。

要求输出： 

（1）tree的最高加分 

（2）tree的前序遍历

#### 输入格式

第1行：一个整数n，为节点个数。 

第2行：n个用空格隔开的整数，为每个节点的分数（0<分数<100）。

#### 输出格式

第1行：一个整数，为最高加分（结果不会超过4,000,000,000）。     

第2行：n个用空格隔开的整数，为该树的前序遍历。如果存在多种方案，则输出字典序最小的方案。

#### 数据范围

n<30

#### 输入样例：

```
5
5 7 1 2 10
```

#### 输出样例：

```
145
3 1 2 4 5
```



## DP

题目要求目标树符合中序遍历为（1,2,3,…,n），

中序可以看作把树扁平化后的数组(换句话说，数组间元素的相对位置等于 相同相对位置 树的中序遍历)

![](pic\479.png)

中序遍历：8, 4, 2, 5, 1, 6, 3, 7

所以用区间DP模板就可以了，最后递归输出前序

注：不同的二叉树可能生成同一个序列，也就是同一个序列，按中序遍历可以生成多个不同的二叉树

```java
import java.io.*;

public class Main {
    static int N, maxN = 35;
    static int[] arr = new int[maxN];
    static int[][] dp = new int[maxN][maxN], root = new int[maxN][maxN];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        String[] str = br.readLine().split(" ");
        for (int i = 1; i <= N; i++) arr[i] = Integer.parseInt(str[i-1]);

        for (int len = 1; len <= N; len++) {
            for (int le = 1; le+len-1 <= N; le++) {
                int ri = le+len-1;
                for (int mid = le; mid <= ri; mid++) {
                    int ls = mid==le ? 1 : dp[le][mid-1];
                    int rs = mid==ri ? 1 : dp[mid+1][ri];
                    int sum = ls * rs + arr[mid];
                    if (le == ri) sum = arr[mid];
                    if (dp[le][ri] < sum) {
                        dp[le][ri] = sum;
                        root[le][ri] = mid;
                    }
                }
            }
        }

        System.out.println(dp[1][N]);
        dfs(1, N);
    }

    private static void dfs(int le, int ri) {
        if (le > ri) return;
        int mid = root[le][ri];
        System.out.printf("%d ", mid);
        dfs(le, mid-1);
        dfs(mid+1, ri);
    }
}
```

