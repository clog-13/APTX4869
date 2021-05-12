# 150. 括号画家

达达是一名漫画家，她有一个奇特的爱好，就是在纸上画括号。

这一天，刚刚起床的达达画了一排括号序列，其中包含小括号 `( )`、中括号 `[ ]` 和大括号 `{ }`，总长度为 N。

这排随意绘制的括号序列显得杂乱无章，于是达达定义了什么样的括号序列是美观的：

1. 空的括号序列是美观的；
2. 若括号序列 AA 是美观的，则括号序列 (A)(A)、\[A\]\[A\]、{A}{A} 也是美观的；
3. 若括号序列 A、B 都是美观的，则括号序列 AB 也是美观的。

例如 `[(){}]()` 是美观的括号序列，而`)({)[}](` 则不是。

现在达达想在她绘制的括号序列中，找出其中连续的一段，满足这段子序列是美观的，并且长度尽量大。

你能帮帮她吗？

#### 输入格式

输入一行由括号组成的字符串。

#### 输出格式

输出一个整数，表示最长的美观的子段的长度。

#### 数据范围

字符串长度不超过 10^5^。

#### 输入样例：

```
({({(({()}})}{())})})[){{{([)()((()]]}])[{)]}{[}{)
```

#### 输出样例：

```
4
```



## DP

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        char[] s = (" "+sc.nextLine()).toCharArray();
        int res = 0;
        int[] f = new int[s.length+1];
        for (int i = 2; i < s.length; i++) {
            if (s[i]=='(' || s[i]=='[' || s[i]=='{') continue;
            if ((s[i]==')' && s[i-1-f[i-1]]=='(') || (s[i]==']' && s[i-1-f[i-1]]=='[')
                    || (s[i]=='}' && s[i-1-f[i-1]]=='{')) {
                f[i] = f[i-1]+2 + (i-2-f[i-1]>0 ? f[i-2-f[i-1]] : 0);
                res = Math.max(res,f[i]);
            }
        }
        System.out.println(res);
    }
}
```



## 栈

```java
#include <iostream>
#include <stack>

using namespace std;

const int N = 100010;

int main() {
    string str;
    cin >> str;
    stack<int> stk;

    int res = 0;
    for (int i = 0; i < str.size(); i ++ ) {
        char c = str[i];
        if (c == ')' && stk.size() && str[stk.top()] == '(') stk.pop();
        else if (c == ']' && stk.size() && str[stk.top()] == '[') stk.pop();
        else if (c == '}' && stk.size() && str[stk.top()] == '{') stk.pop();
        else stk.push(i);

        if (stk.size()) res = max(res, i - stk.top());
        else res = max(res, i + 1);
    }

    cout << res << endl;
    return 0;
}
```

