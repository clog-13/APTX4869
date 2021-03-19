# LCIS 最长子序列问题

## 896. 最长上升子序列 II

给定一个长度为 N 的数列，求数值严格单调递增的子序列的长度最长是多少。

#### 输入格式

第一行包含整数 N。

第二行包含 N 个整数，表示完整序列。

#### 输出格式

输出一个整数，表示最大长度。

#### 数据范围

1≤N≤100000， −109≤数列中的数≤109

#### 输入样例：

```
7
3 1 2 1 8 5 6
```

#### 输出样例：

```
4
```

```java
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] data = new int[N];
        for (int i = 0; i < N; i++) data[i] = sc.nextInt();
        
        int[] dp = new int[N+1];
        int maxL = 1;
        dp[maxL] = data[0];
        for (int i = 1; i < N; i++) {
            if (data[i] > dp[maxL]) {
                dp[++maxL] = data[i];
            } else {  // 构建具体的最长上升序列
                binarySet(1, maxL, dp, data[i]);
            }
        }
        
        System.out.println(maxL);
    }
    
    // [1, 6, 8, 3, 4, 5]
    // 1, 6, 8 => 1, 3, 8
    // 这样变化后,更利于后面的数, 组成更长的上升序列
    private static void binarySet(int le, int ri, int[] dp, int x) {
        if (le == ri) {
            dp[le] = x;
            return;
        }
        
        int mid = (le + ri) >> 1;
        if (dp[mid] > x) binarySet(le, mid, dp, x);  // 这里不用在意是左边界还是右边界
        else binarySet(mid+1, ri, dp, x);
    }
}
```



## 272. 最长公共上升子序列

熊大妈的奶牛在小沐沐的熏陶下开始研究信息题目。

小沐沐先让奶牛研究了最长上升子序列，再让他们研究了最长公共子序列，现在又让他们研究最长公共上升子序列了。

小沐沐说，对于两个数列 A 和 B，如果它们都包含一段位置不一定连续的数，且数值是严格递增的，那么称这一段数是两个数列的公共上升子序列，而所有的公共上升子序列中最长的就是最长公共上升子序列了。

奶牛半懂不懂，小沐沐要你来告诉奶牛什么是最长公共上升子序列。

不过，只要告诉奶牛它的长度就可以了。

数列 A 和 B 的长度均不超过 3000。

#### 输入格式

第一行包含一个整数 N，表示数列 A，B 的长度。

第二行包含 N 个整数，表示数列 A。

第三行包含 N 个整数，表示数列 B。

#### 输出格式

输出一个整数，表示最长公共上升子序列的长度。

#### 数据范围

1≤N≤3000,序列中的数字均不超过 2^31^−1。

#### 输入样例：

```
4
2 2 1 3
2 1 2 3
```

#### 输出样例：

```
2
```

```java
class Main {
    static int maxN = 3010;
    static int[] arr = new int[maxN], brr = new int[maxN];
    static int[][] dp = new int[maxN][maxN];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        for (int i = 1; i <= N; i++) arr[i] = sc.nextInt();
        for (int i = 1; i <= N; i++) brr[i] = sc.nextInt();

        for (int i = 1; i <= N; i++) {
            int max = 1;  // a里以b[i]结尾的最长(公共上升子序列)长度 + 1(当前a[i])
            for (int j = 1; j <= N; j++) {
                dp[i][j] = dp[i-1][j];  
                if (brr[j] < arr[i]) max = Math.max(max, dp[i-1][j] + 1);
                if (brr[j]== arr[i]) dp[i][j] = max; 
            }
        }
        // 最长上升子序列 的二维数组DP写法
        // for (int i = 1; i <= N; i++) {
        //     int max = 1;
        //     for (int j = 1; j <= N; j++) {
        //         dp[i][j] = dp[i-1][j];
        //         if (arr[j] < arr[i]) max = Math.max(max, dp[i-1][j] + 1);
        //         if (j <= i) dp[i][i] = max;  // LCIS 和 LIS 的不同处
        //     }
        // }

        int res = 0;
        for (int i = 1; i <= N; i++) res = Math.max(res, dp[N][i]);
        System.out.println(res);
    }
}
```



## 1012. 友好城市

Palmia国有一条横贯东西的大河，河有笔直的南北两岸，岸上各有位置各不相同的N个城市。

北岸的每个城市有且仅有一个友好城市在南岸，而且不同城市的友好城市不相同。

每对友好城市都向政府申请在河上开辟一条直线航道连接两个城市，但是由于河上雾太大，政府决定避免任意两条航道交叉，以避免事故。

编程帮助政府做出一些批准和拒绝申请的决定，使得在保证任意两条航线不相交的情况下，被批准的申请尽量多。

#### 输入格式

第1行，一个整数N，表示城市数。

第2行到第n+1行，每行两个整数，中间用1个空格隔开，分别表示南岸和北岸的一对友好城市的坐标。

#### 输出格式

仅一行，输出一个整数，表示政府所能批准的最多申请数。

#### 数据范围

1≤N≤5000, 0≤xi≤10000

#### 输入样例：

```
7
22 4
2 6
10 3
15 12
9 8
17 17
4 2
```

#### 输出样例：

```
4
```

```java
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[][] data = new int[N][2];
        for (int i = 0; i < N; i++){
            data[i][0] = sc.nextInt();
            data[i][1] = sc.nextInt();
        }
        // 一边保持有序递增，找另一边的LCS 就能满足题目的要求
        Arrays.sort(data, (a, b) -> (a[0] - b[0]));
        
        int res = 0;
        int[] dp = new int[N];
        for (int i = 0; i < N; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (data[j][1] < data[i][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            res = Math.max(res, dp[i]);
        }
        
        System.out.println(res);
    }
}
```



## 902. 最短编辑距离

给定两个字符串 A 和 B，现在要将 A 经过若干操作变为 B，可进行的操作有：

1. 删除–将字符串 A 中的某个字符删除。
2. 插入–在字符串 A 的某个位置插入某个字符。
3. 替换–将字符串 A 中的某个字符替换为另一个字符。

现在请你求出，将 A 变为 B 至少需要进行多少次操作。

#### 输入格式

第一行包含整数 n，表示字符串 A 的长度。

第二行包含一个长度为 n 的字符串 A。

第三行包含整数 m，表示字符串 B 的长度。

第四行包含一个长度为 m 的字符串 B。

字符串中均只包含大写字母。

#### 输出格式

输出一个整数，表示最少操作次数。

#### 数据范围

1≤n,m≤1000

#### 输入样例：

```
10 
AGTCTGACGC
11 
AGTAAGTAGGC
```

#### 输出样例：

```
4
```

```java
class Main {
    static int maxN = 1010;
    static char[] arr = new char[maxN], brr = new char[maxN];
    static int[][] dp = new int[maxN][maxN];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        sc.nextLine();
        char[] str1 = sc.nextLine().toCharArray();
        int N = str1.length;
        System.arraycopy(str1, 0, arr, 1, N);
        sc.nextLine();
        char[] str2 = sc.nextLine().toCharArray();
        int M = str2.length;
        System.arraycopy(str2, 0, brr, 1, M);

        for (int i = 0; i <= N; i++) dp[i][0] = i;
        for (int i = 0; i <= M; i++) dp[0][i] = i;

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + 1;
                // 这里因为题目定义原因（“修改操作”），所以代码和LCS不同
                dp[i][j] = Math.min(dp[i][j], dp[i-1][j-1] + (arr[i] == brr[j] ? 0 : 1));
            }
        }
        System.out.println(dp[N][M]);
    }
}

```



## 1010. 拦截导弹

某国为了防御敌国的导弹袭击，发展出一种导弹拦截系统。

但是这种导弹拦截系统有一个缺陷：虽然它的第一发炮弹能够到达任意的高度，但是以后每一发炮弹都不能高于前一发的高度。

某天，雷达捕捉到敌国的导弹来袭。

由于该系统还在试用阶段，所以只有一套系统，因此有可能不能拦截所有的导弹。

输入导弹依次飞来的高度（雷达给出的高度数据是不大于30000的正整数，导弹数不超过1000），计算这套系统最多能拦截多少导弹，如果要拦截所有导弹最少要配备多少套这种导弹拦截系统。

#### 输入格式

共一行，输入导弹依次飞来的高度。

#### 输出格式

第一行包含一个整数，表示最多能拦截的导弹数。

第二行包含一个整数，表示要拦截所有导弹最少要配备的系统数。

#### 数据范围

雷达给出的高度数据是不大于 30000 的正整数，导弹数不超过 1000。

#### 输入样例：

```
389 207 155 300 299 170 158 65
```

#### 输出样例：

```
6
2
```



数组中 严格上升的子序列就是 解决问题的次数 的原因是：

可以想象 严格LIS 中任意两个数 无法放到同一个 LDS 中去解决，所以至少需要 严格LIS长度 个 LDS去解决

```java
class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");

        int[] dp01 = new int[1000];
        int[] dp02 = new int[1000];
        int res = 1, cout = 1;
        for (int i = 0; i < str.length; i++) {
            dp01[i] = 1; dp02[i] = 1;
            for (int j = 0; j < i; j++) {
                if (Integer.parseInt(str[i]) <= Integer.parseInt(str[j])) dp01[i] = Math.max(dp01[i], dp01[j]+1);
                else dp02[i] = Math.max(dp02[i], dp02[j]+1);
            }
            res = Math.max(res, dp01[i]);
            cout = Math.max(cout, dp02[i]);
        }
        System.out.println(res);
        System.out.println(cout);
    }
}
```

```java
class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");

        // dp01：最长下降子序列(非严格)
        // dp02：最长上升子序列(严格)
        int[] dp01 = new int[1010], dp02 = new int[1010];
        int idx01 = 1, idx02 = 1;
        dp01[1] = Integer.parseInt(str[0]);
        dp02[1] = Integer.parseInt(str[0]);
        for (int i = 1; i < str.length; i++) {
            int cur = Integer.parseInt(str[i]);
            if (cur <= dp01[idx01]) {
                dp01[++idx01] = cur;
            } else {
                DecBinary(1, idx01, dp01, cur);
            }
            
            if (cur > dp02[idx02]) {
                dp02[++idx02] = cur;
            } else {
                IncBinary(1, idx02, dp02, cur);
            }
        }
        System.out.println(idx01);
        System.out.println(idx02);
    }
    
    static void IncBinary(int le, int ri, int[] dp, int x) {
        if (le == ri) {
            dp[le] = x;
            return;
        }
        
        int mid = (le+ri)>>1;
        if (dp[mid] > x) IncBinary(le, mid, dp, x);  // diff
        else IncBinary(mid+1, ri, dp, x);
    }
    
    static void DecBinary(int le, int ri, int[] dp, int x) {
        if (le == ri) {
            dp[le] = x;
            return;
        }
        int mid = (le+ri)>>1;
        if (dp[mid] < x) DecBinary(le, mid, dp, x);  // diff
        else DecBinary(mid+1, ri, dp, x);
    }
}
```

