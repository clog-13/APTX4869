# 294. 翻转游戏 II
你和朋友玩一个叫做「翻转游戏」的游戏，游戏规则：给定一个只有 + 和 - 的字符串。你和朋友轮流将 连续 的两个 "++" 反转成 "--"。 当一方无法进行有效的翻转时便意味着游戏结束，则另一方获胜。

请你写出一个函数来判定起始玩家是否存在必胜的方案。

**示例：**
输入: s = "++++"
输出: true
解析: 起始玩家可将中间的 "++" 翻转变为 "+--+" 从而得胜。

### 记忆化回溯
```java
class Solution {
    Map<String, Boolean> memo = new HashMap<>();
    public boolean canWin(String s) {
        return canWin(s.toCharArray());
    }

    private boolean canWin(char[] c) {
        String s = new String(c);
        if (memo.containsKey(s)) return memo.get(s);
        for (int i = 0; i < c.length-1; i++) {
            boolean flag = true;
            if (c[i] == '+' && c[i+1] == '+') {
                c[i] = '-';
                c[i+1] = '-';
                flag = canWin(c);
                c[i] = '+';
                c[i+1] = '+';
            }
            if (!flag) {
                memo.put(s, true);
                return true;
            }
        }
        memo.put(s, false);
        return false;
    }
}
```
```java
class Solution {
    Map<String, Boolean> memo = new HashMap<>();
    public boolean canWin(String s) {
        if(memo.containsKey(s)) return memo.get(s);
        for (int i = 1; i < s.length(); ++i) {
            if (s.charAt(i) == '+' && s.charAt(i-1) == '+'){
                String ss = s.substring(0, i-1) + "--" + s.substring(i+1);
                if(!canWin(ss)) {
                    memo.put(ss,false);
                    return true;
                }
                memo.put(ss,true);
            }
        }
        return false;
    }
}
```