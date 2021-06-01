# 171. 送礼物

达达帮翰翰给女生送礼物，翰翰一共准备了 N 个礼物，其中第 i 个礼物的重量是 G[i]。

达达的力气很大，他一次可以搬动重量之和不超过 W 的任意多个物品。

达达希望一次搬掉尽量重的一些物品，请你告诉达达在他的力气范围内一次性能搬动的最大重量是多少。

#### 输入格式

第一行两个整数，分别代表 W 和 N。

以后 N 行，每行一个正整数表示 G[i]。

#### 输出格式

仅一个整数，表示达达在他的力气范围内一次性能搬动的最大重量。

#### 数据范围

1≤N≤46, 1≤W,G[i]≤2^31^−1

#### 输入样例：

```
20 5
7
5
4
18
1
```

#### 输出样例：

```
19
```



## 双向搜索+剪枝

```java
import java.io.*;
import java.util.*;

class Main{
    int N, W, res = 0;
    int[] arr = new int[50];
    Set<Integer> set = new HashSet<>();
    List<Integer> setList;

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        N = Integer.parseInt(str[1]); W = Integer.parseInt(str[0]);
        for (int i = 0; i < N; i++) arr[i] = Integer.parseInt(br.readLine());
        Arrays.sort(arr, 0, N);

        int mid = N / 2;  // mid取值可以左右适当调整
        dfs_ri(N-1, 0, mid);
        setList = new ArrayList(set);
        Collections.sort(setList);

        dfs_le(mid, 0);
        System.out.println(res);
    }

    void dfs_ri(int idx, int sum, int mid) {
        set.add(sum);   // 打表, 去重
        if (idx <= mid) return;
        if ((long) sum+arr[idx] <= W) dfs_ri(idx-1, sum+arr[idx], mid);  // add
        dfs_ri(idx-1, sum, mid);  // not add
    }

    void dfs_le(int idx, int sum) {
        if (idx < 0) {  // 左边dfs搜索完，左边总和，去匹配右边搜索结果
            int le = 0, ri = setList.size();  // 找到小于等于target的最大值
            int tar = W-sum;
            while (le < ri) {  // 二分
                int mid = le + ri >> 1;
                if (setList.get(mid) <= tar) {  // 右边界
                    le = mid+1;
                } else if (setList.get(mid) > tar) {
                    ri = mid;
                }
            }
            res = Math.max(res, sum+setList.get(le-1));
            return;
        }

        if ((long) sum+arr[idx] <= W) dfs_le(idx-1, sum+arr[idx]);
        dfs_le(idx-1, sum);
    }
}
```

