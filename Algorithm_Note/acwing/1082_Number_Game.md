# 1082. 数字游戏

科协里最近很流行数字游戏。

某人命名了一种不降数，这种数字必须满足从左到右各位数字呈非下降关系，如 123，446。

现在大家决定玩一个游戏，指定一个整数闭区间 [a,b]，问这个区间内有多少个不降数。

#### 输入格式

输入包含多组测试数据。

每组数据占一行，包含两个整数 a 和 b。

#### 输出格式

每行给出一组测试数据的答案，即 [a,b] 之间有多少不降数。

#### 数据范围

1≤a≤b≤2^31^−1

#### 输入样例：

```
1 9
1 19
```

#### 输出样例：

```
9
18
```



## 计数DP

```java
import java.util.*;
class Main {
    int maxN = 15;
    int[][] f = new int[maxN][maxN];

    public static void main(String[] args) {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);

        init();
        while (sc.hasNext()) {
            int a = sc.nextInt(), b = sc.nextInt();
            System.out.println(dp(b)-dp(a-1));
        }
    }
    
    int dp(int n) { 
        if(n==0) return 1;
        List<Integer> list = new ArrayList<>();
        while (n > 0) {
            list.add(n%10);
            n/=10;
        }    

        int res=0, last=0;  // 保存上一位的值
        for (int i = list.size()-1; i >= 0; i--) {
            int cur = list.get(i);

            if (last > cur) break;
            for (int j = last; j < cur; j++) {  // j < cur, 因为在 [0,cur-1] 这个范围才可以为所欲为
                res += f[i+1][j];
            }
            if (i == 0) res++;  // 全部枚举完了也同样构成一种方案
            last = cur;
        }

        return res;
    }

    void init() {  // f[i][j]: i位数且最高位为j的不降序的方案数
        for (int i = 0; i <= 9; i++) f[1][i]=1;
        for (int i = 2; i < maxN; i++) {
            for (int j = 0; j <= 9; j++) {
                for (int k = j; k <= 9; k++) {  // j=5, k=6_xxx,7_xxx,8_xxx
                    f[i][j] += f[i-1][k];
                }
            }
        }
    }
}
```

