#金字塔转换矩阵
现在，我们用一些方块来堆砌一个金字塔。 每个方块用仅包含一个字母的字符串表示。

使用三元组表示金字塔的堆砌规则如下：

对于三元组(A, B, C) ，“C”为顶层方块，方块“A”、“B”分别作为方块“C”下一层的的左、右子块。当且仅当(A, B, C)是被允许的三元组，我们才可以将其堆砌上。

初始时，给定金字塔的基层 bottom，用一个字符串表示。一个允许的三元组列表 allowed，每个三元组用一个长度为 3 的字符串表示。

如果可以由基层一直堆到塔尖就返回 true，否则返回 false。

**示例:**
![](../pic/Pyramid_Transition_Matrix.png)
##模拟+递归
```java
class Solution {
    int[][] data = new int[7][7];	// 记录允许的 三元组
    public boolean pyramidTransition(String bottom, List<String> allowed) {
        for(String str: allowed)	// 用二进制(状态)记录 allowed 在二维数组里
            data[str.charAt(0)-'A'][str.charAt(1)-'A'] |= 1 << (str.charAt(2)-'A');

        int N = bottom.length();
        int[][] T = new int[N][N];	// 模拟金字塔的数组
        int i = 0;
        for(char c: bottom.toCharArray())
            T[N-1][i++] = c - 'A';	// 初始化 底层状态

        return helper(T, N-1, 0);
    }

    private boolean helper(int[][] T, int N, int idx) {
        // 下面两种判定条件都可以
        // if(N == 1 && idx == 1)  当N==1&&idx==1时,意味着下一层已经通过data[T[N][0]][T[N][1]]搭建好了
        if(N == 0 && idx == 0) {
            return true;
        }else if(idx == N) {
            return helper(T, N-1, 0);
        }else {
            int tmp = data[T[N][idx]][T[N][idx+1]];
            for(int i = 0; i < 7; i++) {
                if(((tmp>>i) & 1) != 0) {
                    T[N-1][idx] = i;
                    if(helper(T, N, idx+1)) return true;
                }
            }
        }
        return false;
    }
}
```

##DFS（回溯）
```java
class Solution {
    public boolean pyramidTransition(String bottom, List<String> allowed) {
        return dfs(bottom, allowed, 0, new StringBuilder());
    }

    private boolean dfs(String bottom, List<String> allowed, int index, StringBuilder next) {
        if(bottom.length() == 1) return true;
        if(index >= bottom.length() - 1)
            return dfs(next.toString(), allowed, 0, new StringBuilder());

        String s = bottom.substring(index, index + 2);
        for(int i = 0; i < allowed.size(); i++) {
            if(allowed.get(i).startsWith(s)) {
                next.append(allowed.get(i).charAt(2));
                if(dfs(bottom, allowed, index+1, next)) // 处理后面的基底
                    return true;
                next.deleteCharAt(next.length() - 1);	// 处理多种相同基底的其他情况
            }
        }
        return false;
    }
}
```
