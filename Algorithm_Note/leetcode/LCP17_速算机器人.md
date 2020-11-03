# LCP 17. 速算机器人

小扣在秋日市集发现了一款速算机器人。店家对机器人说出两个数字（记作 `x` 和 `y`），请小扣说出计算指令：

- `"A"` 运算：使 `x = 2 * x + y`；
- `"B"` 运算：使 `y = 2 * y + x`。

在本次游戏中，店家说出的数字为 `x = 1` 和 `y = 0`，小扣说出的计算指令记作仅由大写字母 `A`、`B` 组成的字符串 `s`，字符串中字符的顺序表示计算顺序，请返回最终 `x` 与 `y` 的和为多少。

**示例：**

> 输入：`s = "AB"`
>
> 输出：`4`
>
> 解释：
> 经过一次 A 运算后，x = 2, y = 0。
> 再经过一次 B 运算，x = 2, y = 2。
> 最终 x 与 y 之和为 4。

## 数学

目标结果是x+y
出现一个"A"，有x+y=(2x+y)+y=2x+2y
出现一个"B"，有x+y=x+(2y+x)=2x+2y
所以每出现一个A/B，都使x+y的值翻倍
因此结果是 1 << s.length()

```java
class Solution {
    public int calculate(String s) {
        return 1 << s.length();
    }
}
```

## 模拟

```java
class Solution {
    public int calculate(String s) {
        int x = 1, y = 0;

        char[] ch = new char[s.length()];
        ch = s.toCharArray();
        for (int i = 0; i < ch.length; i++){
            if ('A' == ch[i]) {
                x = 2*x + y;
            } else if ('B' == ch[i]) {
                y = 2*y + x;
            }
        }
        return x+y;
    }
}
```

