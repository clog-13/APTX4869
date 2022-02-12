# 剑指 Offer 64. 求1+2+…+n
求 1+2+...+n ，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。

**示例：**
输入: n = 3
输出: 6
```c
typedef unsigned int (*fun)(unsigned int);

unsigned int sumNums_finally(unsigned int n) {
	return 0;
}
unsigned int sumNums(unsigned int n) {
	fun f[2] = {sumNums_finally, sumNums};	// 函数数组
	return n + f[!!n](n - 1);
}
```
```c
int sumNums(int n){
    n && (n += sumNums(n-1));	// &&有短路性质
    return n;
}
```
# 面试题 16.01. 交换数字
编写一个函数，不用临时变量，直接交换numbers = [a, b]中a与b的值。

**示例：**
输入: numbers = [1,2]
输出: [2,1]
```c
int* swapNumbers(int* nums, int numbersSize, int* returnSize){
    nums[0] = nums[0]+nums[1];
    nums[1] = nums[0]-nums[1];
    nums[0] = nums[0]-nums[1];
    *returnSize = numbersSize;
    return nums;
}
```
```java
class Solution {
    public int[] swapNumbers(int[] nums) {
        nums[0] = nums[0] - nums[1];
        nums[1] = nums[1] + nums[0];
        nums[0] = nums[1] - nums[0];
        return nums;
    }
}
```
```java
class Solution {
    public int[] swapNumbers(int[] nums) {
        nums[0] ^= nums[1];
        nums[1] ^= nums[0];
        nums[0] ^= nums[1];
        return nums;
    }
}
```
# 915. 分割数组
给定一个数组 A，将其划分为两个不相交（没有公共元素）的连续子数组 left 和 right， 使得：

left 中的每个元素都小于或等于 right 中的每个元素。
left 和 right 都是非空的。
left 要尽可能小。
在完成这样的分组后返回 left 的长度。可以保证存在这样的划分方法。

**示例：**
输入：[5,0,3,8,6]
输出：3
解释：left = [5,0,3]，right = [8,6]

**提示：**
可以保证至少有一种方法能够按题目所描述的那样对 A 进行划分

```cpp
int partitionDisjoint(int* A, int ASize) {
    int index = 0;
    int cur = A[0], max = cur;
    for (int i = 1; i < ASize; i++) {
        if (A[i] < cur) {   // left 要要尽可能小
            index = i;      // 当有更小的左边的值时更新
            cur = max;
        }

        if (A[i] > max) max = A[i];
    }
    return index + 1;
}
```
```cpp
int partitionDisjoint(int* A, int ASize) {
    int max[30000], min[30000];
    int temp = 0x80000000;	// Integer.MIN_VALUE
    for (int i = 0; i < ASize; i++) {		// 前 i 个数中最大的值
        temp = temp > A[i] ? temp : A[i];
        max[i] = temp;
    }
    temp = 0x7fffffff;		// Integer.MAX_VALUE
    for (int i = ASize - 1; i >= 0; i--) {	// 后 i 个数中最小的值
        min[i] = temp;		// 这里和上面顺序不同，因为抽象的原因, [i]包括前i个的min，但不包括后i个的max
        temp = temp < A[i] ? temp : A[i];
    }
    for (int i = 0; i < ASize; ++i)
        if (max[i] <= min[i]) return i + 1;

	return -1;
}
```

# 面试题 17.04. 消失的数字
数组nums包含从0到n的所有整数，但其中缺了一个。请编写代码找出那个缺失的整数。你有办法在O(n)时间内完成吗？

**示例：**
输入：[3,0,1]
输出：2

```cpp
细品下面两种相似的写法
int missingNumber_1(int* nums, int numsSize){
    int missNum = 0;
    for(int i = 1; i <= numsSize; i++){
        missNum ^= nums[i-1];
        missNum ^= i;
    }
    return missNum;
}
int missingNumber_2(int* nums, int numsSize){
    int missNum = numsSize;
    for(int i = 0; i < numsSize; i++){
        missNum ^= nums[i];
        missNum ^= i;
    }
    return missNum;
}
```

# 1331. 数组序号转换
给你一个整数数组 arr ，请你将数组中的每个元素替换为它们排序后的序号。

序号代表了一个元素有多大。序号编号的规则如下：

序号从 1 开始编号。
一个元素越大，那么序号越大。如果两个元素相等，那么它们的序号相同。
每个数字的序号都应该尽可能地小。

**示例：**
输入：arr = [40,10,20,30]
输出：[4,1,2,3]
解释：40 是最大的元素。 10 是最小的元素。 20 是第二小的数字。 30 是第三小的数字。
```java
class Solution {
    public int[] arrayRankTransform(int[] arr) {
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for (int n : arr) {
            max = Math.max(max, n);
            min = Math.min(min, n);
        }

        int[] cout = new int[max-min+1];
        for (int n : arr) cout[n-min] = 1;

        int idx = 1;
        for(int i = 0; i < cout.length; i++)
            if(cout[i] != 0) cout[i] = idx++;

        // 生成下标的另一种方法
        // int[] pre = new int[cout.length+1];
        // for (int i = 1; i < pre.length; i++)
        //     pre[i] = pre[i-1] + cout[i-1];

        int[] res = new int[arr.length];
        for(int i = 0; i < res.length; i++) {
            res[i] = cout[arr[i] - min];
            // res[i] = pre[arr[i] - min] + 1;
        }

        return res;
    }
}
```

# 758. 字符串中的加粗单词
给定一个关键词集合 words 和一个字符串 S，将所有 S 中出现的关键词加粗。所有在标签 <b> 和 </b> 中的字母都会加粗。

返回的字符串需要使用尽可能少的标签，当然标签应形成有效的组合。

例如，给定 words = ["ab", "bc"] 和 S = "aabcd"，需要返回 "a<b>abc</b>d"。注意返回 "a<b>a<b>b</b>c</b>d" 会使用更多的标签，因此是错误的。

```java
字典树
class Solution {
    public String boldWords(String[] words, String S) {
        TrieTree root = new TrieTree();
        for (String word : words) { // 初始化字典树
            char[] w = word.toCharArray();
            TrieTree node = root;
            for (char c : w) {
                if (node.next[c-'a'] == null)
                    node.next[c-'a'] = new TrieTree();
                node = node.next[c-'a'];
            }
            node.end = true;
        }

        int maxEnd = -1;
        char[] ss = S.toCharArray();
        boolean isOpen = false;
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < S.length(); i++) {
            TrieTree node = root;
            int j = i;
            while (j < S.length() && node.next[ss[j]-'a'] != null) {
                node = node.next[ss[j]-'a'];
                j++;

                if (node.end) {
                    if (!isOpen) {
                        res.append("<b>");
                        isOpen = true;
                    }
                    maxEnd = Math.max(maxEnd, j);
                }
            }
            if (i == maxEnd) {
                res.append("</b>");
                isOpen = false;
            }
            res.append(ss[i]);
        }
        if (isOpen) res.append("</b>");
        return res.toString();
    }

    class TrieTree {
        boolean end;
        TrieTree[] next = new TrieTree[26]; 
    }
}
```
```java
染色表
class Solution {
    public String boldWords(String[] words, String S) {
        boolean[] isBold = new boolean[S.length()];
        for (String word : words) {
            int n = S.indexOf(word, 0);
            while (n != -1) {
                for (int i = n; i < n + word.length(); i++) isBold[i] = true;
                n = S.indexOf(word, n + 1);
            }
        }
        StringBuilder sb = new StringBuilder();
        if (isBold[0]) sb.append("<b>");
        for (int i = 0; i < isBold.length; i++) {
            sb.append(S.charAt(i));
            if (i == isBold.length - 1) {
                if (isBold[i]) sb.append("</b>");
                break;
            }
            if (isBold[i] && !isBold[i + 1]) sb.append("</b>");
            if (!isBold[i] && isBold[i + 1]) sb.append("<b>");
        }
        return sb.toString();
    }
}
// 用时更长
//class Solution {
//     public String boldWords(String[] words, String S) {
//         int[] color = new int[S.length()];
//         for (String str : words) {
//             for (int i = 0; i <= S.length()-str.length(); i++) {
//                 if (str.equals(S.substring(i, i+str.length()))) {
//                     for (int j = i; j < i+str.length(); j++) {
//                         color[j] = 1;
//                     }
//                 }
//             }
//         }
//         char[] arr = S.toCharArray();
//         StringBuilder res = new StringBuilder();
//         for (int i = 0; i < S.length(); i++) {
//             if (color[i] == 1) {
//                 res.append("<b>");
//                 while (i < S.length() && color[i] == 1)
//                     res.append(arr[i++]);
//                 res.append("</b>");
//             }
//             if (i < S.length())
//                 res.append(arr[i]);
//         }
//         return res.toString();
//     }
// }
```

# 1360. 日期之间隔几天
请你编写一个程序来计算两个日期之间隔了多少天。

日期以字符串形式给出，格式为 YYYY-MM-DD，如示例所示。

**示例：**
输入：date1 = "2019-06-29", date2 = "2019-06-30"
输出：1

```cpp
int isLeap(int year) {
    return (year%4==0 && year%100!=0) || year%400==0;
}

int getDate (char *date) {
    int y, m, d, res = 0;
    sscanf(date, "%d-%d-%d", &y, &m, &d);
    int arr[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    for (int i = 1970; i < y; i++) {
        if (isLeap(i)) res += 366;
        else res += 365;
    }
    for (int i = 1; i < m; i++) {
        res += arr[i];
        if (i==2 && isLeap(y)) res++;
    }
    return res + d;
}

#define intfabs(x) (x < 0 ? -(x) : x)
int daysBetweenDates(char * date1, char * date2){
    return intfabs(getDate(date1) - getDate(date2));
}
```

# 276. 栅栏涂色
有 k 种颜色的涂料和一个包含 n 个栅栏柱的栅栏，每个栅栏柱可以用其中一种颜色进行上色。

你需要给所有栅栏柱上色，并且保证其中相邻的栅栏柱 最多连续两个 颜色相同。然后，返回所有有效涂色的方案数。

注意:
n 和 k 均为非负的整数。

**示例:**
输入: n = 3，k = 2
输出: 6

## DP
```cpp
int numWays(int n, int k){
    if (n == 0 || k == 0) return 0;
    if (n == 1) return k;   // 不能写成 if (n) ，n 大于 0 都为 True
    int dp[n+1];
    dp[1] = k;
    dp[2] = k*k;

    // 1)第i个栏栅与第i-1个栏栅颜色不同，则dp[i] = dp[i-1] * (k-1)
    // 2)第i个栏栅与第i-1个栏栅颜色相同，即 i 与 i-2 颜色不同，则dp[i] = dp[i-2] * (k-1)

    // 【注】如何理解k-1？
    // 在第一种情况中，表示去掉i-1的颜色；在第二种情况中，表示去掉i-2的颜色
    for (int i = 3; i <= n; i++)
        dp[i] = dp[i-1]*(k-1) + dp[i-2]*(k-1);
    return dp[n];
}
```

# 751. IP 到 CIDR
给定一个起始 IP 地址 ip 和一个我们需要包含的 IP 的数量 n，返回用列表（最小可能的长度）表示的 CIDR块的范围。 

CIDR 块是包含 IP 的字符串，后接斜杠和固定长度。例如：“123.45.67.89/20”。固定长度 “20” 表示在特定的范围中公共前缀位的长度。

**示例：**
输入：ip = "255.0.0.7", n = 10
输出：["255.0.0.7/32","255.0.0.8/29","255.0.0.16/32"]
解释：
转换为二进制时，初始IP地址如下所示（为清晰起见添加了空格）：
255.0.0.7 -> 11111111 00000000 00000000 00000111
地址 "255.0.0.7/32" 表示与给定地址有相同的 32 位前缀的所有地址，
在这里只有这一个地址。

地址 "255.0.0.8/29" 表示与给定地址有相同的 29 位前缀的所有地址：
255.0.0.8 -> 11111111 00000000 00000000 00001000
有相同的 29 位前缀的地址如下：
11111111 00000000 00000000 00001000
11111111 00000000 00000000 00001001
11111111 00000000 00000000 00001010
11111111 00000000 00000000 00001011
11111111 00000000 00000000 00001100
11111111 00000000 00000000 00001101
11111111 00000000 00000000 00001110
11111111 00000000 00000000 00001111

地址 "255.0.0.16/32" 表示与给定地址有相同的 32 位前缀的所有地址，
这里只有 11111111 00000000 00000000 00010000。

总之，答案指定了从 255.0.0.7 开始的 10 个 IP 的范围。

有一些其他的表示方法，例如：
["255.0.0.7/32","255.0.0.8/30", "255.0.0.12/30", "255.0.0.16/32"],
但是我们的答案是最短可能的答案。

另外请注意以 "255.0.0.7/30" 开始的表示不正确，
因为其包括了 255.0.0.4 = 11111111 00000000 00000000 00000100 这样的地址，
超出了需要表示的范围。

```java
class Solution {
    public List<String> ipToCIDR(String ip, int n) {
        long start = ipToLong(ip);

        List<String> res = new ArrayList();
        while (n > 0) {
            // mask 越大， 包含范围越小（mask 指位数）， 这里就是找比较小的那个范围
            int mask = Math.max(32 - bitLength(Long.lowestOneBit(start)),   // 最右边 1 的权总
            					32 - bitLength(n));
            mask++;
            res.add(longToIP(start) + "/" + mask);
			int range = 1 << (32 - mask);   // range 要为 2 的幂级数
            start += range;
            n -= range;
        }
        return res;
    }
    private long ipToLong(String ip) {
        long res = 0;
        for (String x: ip.split("\\."))
            res = 256 * res + Integer.valueOf(x);
        return res;
    }
    private String longToIP(long x) {
        return String.format("%s.%s.%s.%s",
            x >> 24, (x >> 16) & 0xff, (x >> 8) & 0xff, x & 0xff); // 先 >> 再 % 的， % 的作用就是取低x位
    }
    private int bitLength(long x) {
        if (x == 0) return 1;
        int res = 0;
        while (x > 0) {
            x >>= 1;
            res++;
        }
        return res;
    }
}
```

