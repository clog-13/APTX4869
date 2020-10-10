# 1442. 形成两个异或相等数组的三元组数目
给你一个整数数组 arr 。

现需要从数组中取三个下标 i、j 和 k ，其中 (0 <= i < j <= k < arr.length) 。

a 和 b 定义如下：

a = arr[i] ^ arr[i + 1] ^ ... ^ arr[j - 1]
b = arr[j] ^ arr[j + 1] ^ ... ^ arr[k]
注意：^ 表示 按位异或 操作。

请返回能够令 a == b 成立的三元组 (i, j , k) 的数目。

**示例：**
输入：arr = [2,3,1,6,7]
输出：4
解释：满足题意的三元组分别是 (0,1,2), (0,2,2), (2,3,4) 以及 (2,4,4)

## 数学
观察到a = arr[i] ^ arr[i + 1] ^ ... ^ arr[j - 1], b = arr[j] ^ arr[j + 1] ^ ... ^ arr[k],那么根据位运算的规则， 
a\^b = arr[i]^ arr[i + 1] ^ ... ^ arr[k]，即i到k。
此外，根据位运算，**如果a^b\==0,那么a\==b，这是逆否命题，即反推也成立。**
而固定了两端之后，中间的j可以任意取，故有k-i种。每次计算完，如果满足条件，在res里加上k-i即可。
```java
class Solution {
   public int countTriplets(int[] arr) {
        int N = arr.length;
        int res = 0;

        for(int i = 0; i < N - 1; i++) {
            int sum = arr[i];
            for(int k = i+1; k < N; k++) {
                sum ^= arr[k];
                if (sum == 0) res += (k - i);
            }
        }
        return res;
    }
}
```

## 模拟
```java
class Solution {
    public int countTriplets(int[] arr) {
        int N = arr.length;
        int[] pre = new int[N+1];

        for (int i = 1; i <= N; i++)
            pre[i] = pre[i-1] ^ arr[i-1];

        int res = 0;
        for (int len = 1; len < N; len++) {
            for (int i = 0; i+len < N; i++) {
                for (int j = i+1; j <= i+len; j++) {
                    // int a = pre[i-1+1] ^ pre[j-1+1];    // [i, j-1]
                    // int b = pre[j-1+1] ^ pre[i+len+1];  // [j, k(i+len)];
                    int a = pre[i] ^ pre[j];
                    int b = pre[j] ^ pre[i+len+1];
                    if(a == b) res++;
                }
            }
        }
        return res;
    }
}
```