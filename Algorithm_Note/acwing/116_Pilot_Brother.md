# 116. 飞行员兄弟

“飞行员兄弟”这个游戏，需要玩家顺利的打开一个拥有 16 个把手的冰箱。

已知每个把手可以处于以下两种状态之一：打开或关闭。

只有当所有把手都打开时，冰箱才会打开。

把手可以表示为一个 4×4 的矩阵，您可以改变任何一个位置 [i,j][i,j] 上把手的状态。

但是，这也会使得第 i 行和第 j 列上的所有把手的状态也随着改变。

请你求出打开冰箱所需的切换把手的次数最小值是多少。

#### 输入格式

输入一共包含四行，每行包含四个把手的初始状态。

符号 `+` 表示把手处于闭合状态，而符号 `-` 表示把手处于打开状态。

至少一个手柄的初始状态是关闭的。

#### 输出格式

第一行输出一个整数 N，表示所需的最小切换把手次数。

接下来 N 行描述切换顺序，每行输出两个整数，代表被切换状态的把手的行号和列号，数字之间用空格隔开。

**注意**：如果存在多种打开冰箱的方式，则按照优先级整体从上到下，同行从左到右打开。

#### 数据范围

1≤i,j≤4

#### 输入样例：

```
-+--
----
----
-+--
```

#### 输出样例：

```
6
1 1
1 3
1 4
4 1
4 3
4 4
```



## 枚举

```java
import java.util.*;

public class Main {
    static int[][] change = new int[4][4];
    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {        // 初始化 翻转辅助表
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    change[i][j] |= (1 << (i*4+k)) | (1 << (k*4+j));  // 横向位置 | 竖向位置
                }
            }
        }

        Scanner sc = new Scanner(System.in);
        int state = 0;
        for (int i = 0; i < 4; i++) {
            String tmp = sc.nextLine();
            for (int j = 0; j < 4; j++) {
                if(tmp.charAt(j) == '+') state |= 1<<(i*4+j);
            }
        }

        List<int[]> res = new ArrayList<>();
        for (int op = 0; op < 1<<16; op++) {  // 枚举
            int cur = state;
            List<int[]> tmp = new ArrayList<>();
            for (int i = 0; i < 4; i++) {     // 翻转不要求顺序
                for (int j = 0; j < 4; j++) {
                    if ((op>>(i*4+j) & 1) == 1) {
                        cur ^= change[i][j];
                        tmp.add(new int[]{i, j});
                    }
                }
            }

            if (cur == 0 && (res.isEmpty() || tmp.size() < res.size())) {
                res = new ArrayList<>(tmp);
            }
        }

        System.out.println(res.size());
        for (int[] r : res) System.out.println((r[0]+1) + " " + (r[1]+1));
    }
}
```

