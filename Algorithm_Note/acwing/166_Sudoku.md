# 166. 数独

数独是一种传统益智游戏，你需要把一个 9×9 的数独补充完整，使得图中每行、每列、每个 3×3 的九宫格内数字 1∼9 均恰好出现一次。

请编写一个程序填写数独。

#### 输入格式

输入包含多组测试用例。

每个测试用例占一行，包含 81 个字符，代表数独的 81 个格内数据（顺序总体由上到下，同行由左到右）。

每个字符都是一个数字（1−9）或一个 `.`（表示尚未填充）。

您可以假设输入中的每个谜题都只有一个解决方案。

文件结尾处为包含单词 `end` 的单行，表示输入结束。

#### 输出格式

每个测试用例，输出一行数据，代表填充完全后的数独。

#### 输入样例：

```
4.....8.5.3..........7......2.....6.....8.4......1.......6.3.7.5..2.....1.4......
......52..8.4......3...9...5.1...6..2..7........3.....6...1..........7.4.......3.
end
```

#### 输出样例：

```
417369825632158947958724316825437169791586432346912758289643571573291684164875293
416837529982465371735129468571298643293746185864351297647913852359682714128574936
```



## DFS

```java
import java.io.*;
import java.util.*;

public class Main {
    char[] str;
    int[] ones = new int[1 << 9];
    int[] row = new int[9], col = new int[9];
    int[][] cell = new int[3][3];
    Map<Integer, Integer> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for (int state = 0; state < 1<<9; state++) {    // 预处理 每种状态中有多少个1
            for (int i = 0; i < 9; i++) {
                ones[state] += state>>i & 1;
            }
        }
        for (int i = 0; i < 9; i++) map.put(1<<i, i);  // 预处理每种选择进二进制

        while (true) {
            String tmp = br.readLine();
            if (tmp.equals("end")) break;
            str = tmp.toCharArray();

            init();
            int count = 81;
            for (int i = 0; i < 9; i++) {  // 绘制当前九宫格
                for (int j = 0; j < 9; j++) {
                    if (str[i*9+j] != '.') {
                        draw(i, j, str[i*9+j]-'1', true);
                        count--;
                    }
                }
            }

            dfs(count);
            bw.write(new String(str) + "\n");
        }
        bw.flush();
    }

    boolean dfs(int cnt) {
        if (cnt == 0) return true;
        
        int min = 10, x = -1, y = -1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (str[i*9+j] == '.') {  // 状态中 1 表示可选
                    int options = ones[row[i] & col[j] & cell[i/3][j/3]];
                    if (options < min) {
                        min = options;  // 剪枝, 选择分支最少的格子
                        x = i; y = j;
                    }
                }
            }
        }

        int state = row[x] & col[y] & cell[x/3][y/3];
        for (int i = state; i != 0; i -= lowbit(i)) {
            int n = map.get(lowbit(i));
            draw(x, y, n, true);
            if (dfs(cnt - 1)) return true;
            draw(x, y, n, false);
        }

        return false;
    }

    // 若isSet为true 则填进n数，否则恢复现场
    void draw(int x, int y, int n, boolean isSet) {
        if (isSet) {
            str[x*9+y] = (char) (n+'1');
        } else {
            str[x*9+y] = '.';
        }

        int dv = isSet ? 1<<n : -(1<<n);

        row[x] -= dv; col[y] -= dv;
        cell[x / 3][y / 3] -= dv;
    }
    
    void init() {  // 初始化二进制全是1，表示均可以选择
        Arrays.fill(row, (1<<9)-1);
        Arrays.fill(col, (1<<9)-1);
        for (int i = 0; i < 3; i++) Arrays.fill(cell[i], (1<<9)-1);
    }

    int lowbit(int x) {
        return x & -x;
    }
}
```

