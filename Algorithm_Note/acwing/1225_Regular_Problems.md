# 1225. 正则问题

考虑一种简单的正则表达式：

只由 x ( ) | 组成的正则表达式。

小明想求出这个正则表达式能接受的最长字符串的长度。

例如 ((xx|xxx)x|(x|xx))xx 能接受的最长字符串是： xxxxxx，长度是6。

#### 输入格式

一个由x()|组成的正则表达式。

#### 输出格式

输出所给正则表达式能接受的最长字符串的长度。

#### 数据范围

输入长度不超过100，保证合法。

#### 输入样例：

```
((xx|xxx)x|(x|xx))xx 
```

#### 输出样例：

```
6
```

## 递归

```java
import java.util.*;
class Main {
    int idx;
    char[] arr;
    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        arr = sc.nextLine().trim().toCharArray();
        System.out.println(dfs());
    }

    int dfs() {
        int res = 0;
        while (idx < arr.length) {
            if (arr[idx] == '(') {  // 处理 (......)
                idx++;  // 跳过 '('
                res += dfs();
                idx++;  // 跳过 ')'，统一由上层维护 "(...)" 的下标
            } else if (arr[idx] == ')') {  // '(a|b)' ) 是 b 的返回条件也是 a 和 ( 和 | 层 的返回条件
                return res;  // 这里 不能idx++ 要留着去判断其他层的是否返回（它还可能是其他层的返回条件）    
            } else if (arr[idx] == '|') {
                idx++;  // 跳过 '|'
                res = Math.max(res, dfs());
            } else if (arr[idx] == 'x') {
                res++;
                idx++;
            }
        }
        return res;
    }
}
```

