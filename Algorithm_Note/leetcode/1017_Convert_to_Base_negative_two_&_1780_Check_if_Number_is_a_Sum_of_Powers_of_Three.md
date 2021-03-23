# 1017. 负二进制转换

给出数字 `N`，返回由若干 `"0"` 和 `"1"`组成的字符串，该字符串为 `N` 的**负二进制（`base -2`）**表示。

除非字符串就是 `"0"`，否则返回的字符串中不能含有前导零。 

**示例：**

```
输入：2
输出："110"
解释：(-2) ^ 2 + (-2) ^ 1 = 2
```



## 计算机思维

(-2)^5^ + (-2)^4^ + (0)^3^ + (-2)^2^ + (-2)^1^ + (-2)^0^ = (-32) + 16 + 0 + 4 +(-2) + 1 = -13

```java
public class Solution {
    public String baseNeg2(int N) {
        StringBuilder res = new StringBuilder(40);

        while (N != 0) {
            int mod = N % (-2);
            N /= -2;
            
            if (mod == -1) {
                res.append(1);
                N++;    // 修正N
            } else {
                res.append(mod);
            }
        }
        return res.length() == 0 ? "0" : res.reverse().toString();
    }
}
```



# 1780. 判断一个数字是否可以表示成三的幂的和

给你一个整数 `n` ，如果你可以将 `n` 表示成若干个不同的三的幂之和，请你返回 `true` ，否则请返回 `false` 。

对于一个整数 `y` ，如果存在整数 `x` 满足 `y == 3x` ，我们称这个整数 `y` 是三的幂。

**示例：**

```
输入：n = 91
输出：true
解释：91 = 30 + 32 + 34
```



## 计算机思维

```java
class Solution {
    public boolean checkPowersOfThree(int n) {
        while (n > 0) {
            if (n % 3 == 2) return false;
            n /= 3;
        }
        return true;
    }
}
```

.