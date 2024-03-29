# 319. 灯泡开关

**初始时有 *n* 个灯泡关闭**。 第 1 轮，你打开所有的灯泡。 第 2 轮，每两个灯泡你关闭一次。 第 3 轮，每三个灯泡切换一次开关（如果关闭则开启，如果开启则关闭）。第 *i* 轮，每 *i* 个灯泡切换一次开关。 对于第 *n* 轮，你只切换最后一个灯泡的开关。 **找出 *n* 轮后有多少个亮着的灯泡。**

**示例:**

```
输入: 3
输出: 1 
解释: 
初始时, 灯泡状态 [关闭, 关闭, 关闭].
第一轮后, 灯泡状态 [开启, 开启, 开启].
第二轮后, 灯泡状态 [开启, 关闭, 开启].
第三轮后, 灯泡状态 [开启, 关闭, 关闭]. 

你应该返回 1，因为只有一个灯泡还亮着。
```

## 数学 + 脑筋急转弯

不严谨的说就是 '2 关闭的'会被 '4' 打开，'3 关闭的'会被 '9' 打开...

所以答案就是n的算术平方根下取整，

例如 

n= 9,  res=3

n=10, res=3

n=11, res=3

...

n=16, res=4（'4 关闭的'第16个被16打开） 

(想象整个过程分为三个阶段

1. 第 1 轮全部打开。
2. （小鬼关灯）较小的数会关闭一些灯。
3. （大鬼开灯）较大的数会打开其对应的方根'关闭的灯'的一些数（其实只有一个）。

```java
class Solution {
    public int bulbSwitch(int n) {
        return (int)Math.sqrt(n);
    }
}
```



## 模拟

```java
class Solution {
    public int bulbSwitch(int n) {
        int res = 0;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = 1;

        for (int i = 2; i <= n; i++) {
            for (int j = i-1; j < n; j += i) {
                arr[j] = ((j+1)%i == 0) ? arr[j]^1 : arr[j];
            }
        }

        for (int i = 0; i < n; i++) if (arr[i] == 1) res++;
        return res;
    }
}
```



# 672. 灯泡开关 Ⅱ

现有一个房间，墙上挂有 `n` 只已经打开的灯泡和 4 个按钮。在进行了 `m` 次未知操作后，你需要返回这 `n` 只灯泡可能有多少种不同的状态。

假设这 `n` 只灯泡被编号为 [1, 2, 3 ..., n]，这 4 个按钮的功能如下：

1. 将所有灯泡的状态反转（即开变为关，关变为开）
2. 将编号为偶数的灯泡的状态反转
3. 将编号为奇数的灯泡的状态反转
4. 将编号为 `3k+1` 的灯泡的状态反转（k = 0, 1, 2, ...)

**示例:**

```
输入: n = 3, m = 1.
输出: 4
说明: 状态为: [关, 开, 关], [开, 关, 开], [关, 关, 关], [关, 开, 开].
```



## 数学+模拟

```java
class Solution {
    public int flipLights(int n, int m) {
        Set<Integer> set = new HashSet();
        n = Math.min(n, 6);
        int s = 6-n;
        for (int cand = 0; cand < 16; cand++) {  // 16 --> 10000, 这里是模拟排列组合
            int b_cnt = Integer.bitCount(cand);
            if (b_cnt%2 == m%2 && b_cnt <= m) {
                int st = 0;  // 判断模拟排列组合变化
                if (((cand>>0) & 1) > 0) st ^= 0b111111 >> s;
                if (((cand>>1) & 1) > 0) st ^= 0b010101 >> s;
                if (((cand>>2) & 1) > 0) st ^= 0b101010 >> s;
                if (((cand>>3) & 1) > 0) st ^= 0b100100 >> s;
                set.add(st);
            }
        }
        return set.size();
    }
}
```



# 1375. 灯泡开关 III

房间中有 `n` 枚灯泡，编号从 `1` 到 `n`，自左向右排成一排。最初，所有的灯都是关着的。

在 *k* 时刻（ *k* 的取值范围是 `0` 到 `n - 1`），我们打开 `light[k]` 这个灯。

灯的颜色要想 **变成蓝色** 就必须同时满足下面两个条件：

- 灯处于打开状态。
- 排在它之前（左侧）的所有灯也都处于打开状态。

请返回能够让 **所有开着的** 灯都 **变成蓝色** 的时刻 **数目 。**

**示例：**

```
输入：light = [2,1,3,5,4]
输出：3
解释：所有开着的灯都变蓝的时刻分别是 1，2 和 4 。
```



## 模拟

```java
class Solution {
    public int numTimesAllBlue(int[] light) {
        int cout = 0, idx = 0, res = 0;
        for (int i = 0; i < light.length; i++) {
            if (light[i] > idx) idx = light[i];
            cout++;
            if (cout == idx) res++;
        }
        return res;
    }
}
```



# 1529. 灯泡开关 IV

房间中有 `n` 个灯泡，编号从 `0` 到 `n-1` ，自左向右排成一行。最开始的时候，所有的灯泡都是 **关** 着的。

请你设法使得灯泡的开关状态和 `target` 描述的状态一致，其中 `target[i]` 等于 `1` 第 `i` 个灯泡是开着的，等于 `0` 意味着第 `i` 个灯是关着的。

有一个开关可以用于翻转灯泡的状态，翻转操作定义如下：

- 选择当前配置下的任意一个灯泡（下标为 `i` ）
- 翻转下标从 `i` 到 `n-1` 的每个灯泡

翻转时，如果灯泡的状态为 `0` 就变为 `1`，为 `1` 就变为 `0` 。

返回达成 `target` 描述的状态所需的 **最少** 翻转次数。

 

**示例：**

```
输入：target = "10111"
输出：3
解释：初始配置 "00000".
从第 3 个灯泡（下标为 2）开始翻转 "00000" -> "00111"
从第 1 个灯泡（下标为 0）开始翻转 "00111" -> "11000"
从第 2 个灯泡（下标为 1）开始翻转 "11000" -> "10111"
至少需要翻转 3 次才能达成 target 描述的状态
```



## 位运算技巧 + 模拟

```java
class Solution {
    public static int minFlips(String target) {
        int res = 0;
        for (char c : target.toCharArray())
            res += (c-'0') ^ (res&1);  // st ^ 第奇/偶次翻转
        return res;
    }
}
```

```java
class Solution {
    public int minFlips(String target) {
        int res = 0, flag = 0;
        char[] arr= target.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (flag == 0) {
                if (arr[i] == '1') {
                    res++; flag = 1;
                }
            } else {
                if (arr[i] == '0') {
                    res++; flag = 0;
                }
            }
        }
        return res;
    }
}
```

