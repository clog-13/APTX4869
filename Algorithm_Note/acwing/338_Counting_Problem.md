# 338. 计数问题

给定两个整数 a 和 b，求 a 和 b 之间的所有数字中 0∼9 的出现次数。

例如，a=1024，b=1032，则 a 和 b 之间共有 9 个数如下：

```
1024 1025 1026 1027 1028 1029 1030 1031 1032
```

其中 `0` 出现 1010 次，`1` 出现 1010 次，`2` 出现 77 次，`3` 出现 33 次等等…

#### 输入格式

输入包含多组测试数据。

每组测试数据占一行，包含两个整数 a 和 b。

当读入一行为 `0 0` 时，表示输入终止，且该行不作处理。

#### 输出格式

每组数据输出一个结果，每个结果占一行。

每个结果包含十个用空格隔开的数字，第一个数字表示 `0` 出现的次数，第二个数字表示 `1` 出现的次数，以此类推。

#### 数据范围

0<a,b<100000000

#### 输入样例：

```
1 10
44 497
346 542
1199 1748
1496 1403
1004 503
1714 190
1317 854
1976 494
1001 1960
0 0
```

#### 输出样例：

```
1 2 1 1 1 1 1 1 1 1
85 185 185 185 190 96 96 96 95 93
40 40 40 93 136 82 40 40 40 40
115 666 215 215 214 205 205 154 105 106
16 113 19 20 114 20 20 19 19 16
107 105 100 101 101 197 200 200 200 200
413 1133 503 503 503 502 502 417 402 412
196 512 186 104 87 93 97 97 142 196
398 1375 398 398 405 499 499 495 488 471
294 1256 296 296 296 296 287 286 286 247
```



## 计数DP

```java
import java.util.*;
class Main {
    int[] base = new int[10];
    int[][] f = new int[10][10], g = new int[10][10];
    
    public static void main(String[] args) {
        new Main().run();
    }
    
    void run() {
        init();
        
        Scanner sc = new Scanner(System.in);
        while (true) {
            int a = sc.nextInt(), b = sc.nextInt();
            if (a==0 && b==0) return;
            if (a > b) {a = a^b; b = a^b; a = a^b;}
            int[] c1 = dp(a-1);
            int[] c2 = dp(b);
            for (int i = 0; i <= 9; i++) {
                System.out.print((c2[i]-c1[i])+" ");
            }
            System.out.println();
        }
    }
    
    int[] dp(int n) {
        int[] res = new int[10];
        if (n <= 0) return res;
        List<Integer> list = new ArrayList<>();
        while (n > 0) {
            list.add(n%10);
            n /= 10;
        }
        
        // 统计 前n-1个9 的情况(比如1543,统计999)
        for (int k = 0; k <= 9; k++) res[k] = g[list.size()-1][k];
        int[] preCnt = new int[10];  // 记录前缀中各个数字个数
        for (int i = list.size()-1; i >= 0; i--) {  // 统计其余部分(遍历数的每位)
			// 当前位每种数的情况(最左位不能有0, 同时只统计到list.get(i)-1的情况)
            for (int j = i==list.size()-1?1:0; j < list.get(i); j++) {  
                res[j] += base[i];  // (5_000~5_999)
                
                for (int k = 0; k <= 9; k++) {
                    res[k] += preCnt[k] * base[i];  // i之前的每个数也会增加base[i]
                    res[k] += f[i][k];              // i之后的每个数也会增加对应情况
                }
            }
            preCnt[list.get(i)]++;  // 更新i之前的数的计数
        }
        for (int k = 0; k <= 9; k++) res[k] += preCnt[k];  // 统计 n 自己的情况
        return res;
    }
    
    void init() {
        base[0] = 1;
        for (int i = 1; i <= 9; i++) base[i] = 10*base[i-1];
        
        // 从00……0 - 99……9 的各位数字有多少个，其中i为数字个数（包含前导零）
        for (int i = 0; i <= 9; i++) f[1][i] = 1;
        for (int i = 2; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                f[i][j] = base[i-1] + 10*f[i-1][j];  // (5_000~5_999) + (0_f[i-1][j],1_f[i-1][j],...)
            }  // 注意(5_000~5_999)和5_f[i-1][j]不重复,计算的是每个5的次数((5_000~5_999)计算的是最左边的5)
        }
        
        // 从 1 - 99……9 的各位数字有多少个，其中i为数字个数（不包含前导零）
        for (int i = 1; i <= 9; i++) g[1][i] = 1;  // 循环从1开始
        for (int i = 2; i <= 9; i++) {
            g[i][0] = 9*f[i-1][0] + g[i-1][0];  // (1_f[i-1][j],...,9_f[i-1][j]) + 0_g[i-1][j]
            for (int j = 1; j <= 9; j++) {  // (5_000~5_999) + (1_f[i-1][j],...) + 0_g[i-1][j]
                g[i][j] = base[i-1] + 9*f[i-1][j] + g[i-1][j];
            }
        }
    }
}
```



## DP

```java
#include <iostream>
#include <cmath>
#include <algorithm>

using namespace std;

int dgt(int n) {  // 计算 n 有多少位
    int res = 0;
    while (n) res++, n /= 10;
    return res;
}

int cnt(int n, int tar) {  // 统计 tar 出现多少次 
    int res = 0, len = dgt(n);
    for (int i = 1; i <= len; i++) {  // 遍历 n 的每一位
        int base = pow(10, i-1);
        int le = (n/base) / 10, cur = (n/base) % 10, ri = n % base;  // le和ri是第i位左边和右边的整数, cur是第i位的数字
        
        // i位 tar 的数量(xxx tar 00)
        if (tar) res += le * base;  // xxx x xx -> le:xxx base:100 -> (000*100,001*100,...,xxx*100)
        else res += max(le-1,0) * base;  // tar==0, 左边高位不能全为0 -> (       ,001*100,...,xxx*100)

        // i位 tar 的数量(000 tar xx)
        if ((tar < cur) && (tar || le)) res += base;  // ..686 -> ..5_00~..5_99(只要不是最左边,并且tar=0就行)
        else if (tar == cur) res += ri + 1;  // ..586 -> ..5_00~..5_86
    }
    return res;
}

int main() {
    int a, b;
    while (cin >> a >> b , a) {
        if (a > b) swap(a, b);
        a--;
        for (int tar = 0; tar <= 9; ++ tar) cout << cnt(b, tar) - cnt(a, tar) << ' ';
        cout << endl;
    }
    return 0;
}
```

