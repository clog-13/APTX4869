# Difference差分

## 一维差分

```java
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), M = sc.nextInt();
        int[] data = new int[N+1], diff = new int[N+2];
        
        for (int i = 1; i <= N; i++) {
            data[i] = sc.nextInt();
            diff[i] = data[i] - data[i-1];
        }
        
        while (M-- > 0) {  // update
            int le = sc.nextInt(), ri = sc.nextInt();
            int c = sc.nextInt();
            diff[le] += c;
            diff[ri+1] -= c;
        }
        
        for (int i = 1; i <= N; i++) {
            diff[i] += diff[i-1];
            System.out.print(diff[i]+" ");
        }
    }
}
```



## 二维差分

```java
class Main {
    public static void main(String[] args) {
        int[][] arr = new int[1010][1010], diff = new int[1010][1010];
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), M = sc.nextInt(), Q = sc.nextInt();
        
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                arr[i][j] = sc.nextInt();
                update(diff, i, j, i, j, arr[i][j]);
            }
        }

        while (Q-- > 0) {
            int x1 = sc.nextInt(), y1 = sc.nextInt();
            int x2 = sc.nextInt(), y2 = sc.nextInt();
            int c = sc.nextInt();
            
            update(diff, x1, y1, x2, y2, c);
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= M; j++) {
                diff[i][j] += diff[i][j-1] + diff[i-1][j] - diff[i-1][j-1];
                System.out.print(diff[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void update(int[][] diff, int x1, int y1, int x2, int y2, int k) {
        diff[x1][y1] += k;
        
        diff[x2+1][y1] -= k;
        diff[x1][y2+1] -= k;
        
        diff[x2+1][y2+1] += k;
    }
}
```



## 三维差分

![](pic\difference.jpg)

```java
import java.util.Scanner;

class Main {
    static int A, B, C, m, maxN = 2000010;
    static long[] data = new long[maxN], diff = new long[maxN];
    static int[][] dir = new int[][]{
            {0, 0, 0, 1},

            {0, 0, 1,-1},
            {0, 1, 0,-1},
            {1, 0, 0,-1},

            {0, 1, 1, 1},
            {1, 0, 1, 1},
            {1, 1, 0, 1},

            {1, 1, 1,-1},
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        A = sc.nextInt(); B = sc.nextInt(); C = sc.nextInt();
        m = sc.nextInt();

        // 录入信息
        for (int i = 1; i <= A; i++) {
            for (int j = 1; j <= B; j++) {
                for (int k = 1; k <= C; k++) {
                    data[get(i, j, k)] = sc.nextInt();
                }
            }
        }

        // 三维差分数组
        for (int i = 1; i <= A; i++) {
            for (int j = 1; j <= B; j++) {
                for (int k = 1; k <= C; k++) {
                    for (int u = 0; u < 8; u++) {
                        int x = i - dir[u][0];
                        int y = j - dir[u][1];
                        int z = k - dir[u][2];
                        int t = dir[u][3];
                        diff[get(i, j, k)] += data[get(x, y, z)] * t;
                    }
                }
            }
        }

        // update
    }

    static void update(int x1, int y1, int z1, int x2, int y2, int z2, int t) {
        backup[get(x1, y1, z1)] += t;

        backup[get(x2+1, y1, z1)] -= t;
        backup[get(x1, y2+1, z1)] -= t;
        backup[get(x1, y1, z2+1)] -= t;

        backup[get(x1, y2+1, z2+1)] += t;
        backup[get(x2+1, y1, z2+1)] += t;
        backup[get(x2+1, y2+1, z1)] += t;

        backup[get(x2+1, y2+1, z2+1)] -= t;
    }


    static int get(int i, int j, int k) {
        return (i * B + j) * C + k;
    }
}

```

