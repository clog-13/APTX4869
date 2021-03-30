# 678. 有效的括号字符串

给定一个只包含三种字符的字符串：`（ `，`）` 和 `*`，写一个函数来检验这个字符串是否为有效字符串。有效字符串具有如下规则：

1. 任何左括号 `(` 必须有相应的右括号 `)`。
2. 任何右括号 `)` 必须有相应的左括号 `(` 。
3. 左括号 `(` 必须在对应的右括号之前 `)`。
4. `*` 可以被视为单个右括号 `)` ，或单个左括号 `(` ，或一个空字符串。
5. 一个空字符串也被视为有效字符串。

**示例:**

```
输入: "()"
输出: True
```



## 贪心

```java
class Solution {
    public boolean checkValidString(String s) {
        int le = 0;  // 没有 被匹配的 '(' 的数量
        int ri = 0;  // 能 匹配 ')'的数量
        for (int i = 0; i < s.length(); i++) {
            char tmp = s.charAt(i);
            if (tmp == '(') { 
                le++;
                ri++;
            }  else if (tmp == ')') { 
                if (le > 0) le--;
                ri--;
            }
            // le减的再多，ri加的再多，不会导致 返回false
            if (tmp == '*') {
                if (le > 0) le--;
                ri++;
            }
            if (ri < 0) return false;
        }
        return le <= 0;
    }
}
```



## 栈

```
class Solution {
public:
    bool checkValidString(string s) {
        stack<int> left,star;
        for(int i=0;i<s.size();i++){
            if(s[i]=='(') left.push(i);
            else if(s[i]=='*') star.push(i);
            else {
                if(left.empty() && star.empty()) return false;
                if(!left.empty()) left.pop();
                else 
                    star.pop();
            }
        }
        while(!left.empty() && !star.empty()){
            if(left.top()>star.top()) return false;
            left.pop();
            star.pop();
        }
        return left.empty();
    }
};
```

 