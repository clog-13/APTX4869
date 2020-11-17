import java.util.Scanner;

class Main {
    int N = 16;
    int[] ones = new int[1<<N], cnt_log = new int[1<<N];
    int[][] state = new int[N][N];
    int[][][] backup_state = new int[N*N+1][N][N], backup_stateTmp = new int[N*N+1][N][N];
    char[][] arr = new char[N][N];
    char[][][] backup_arr = new char[N*N+1][N][N];

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        for (int i = 0; i < N; i++) cnt_log[1<<i] = i;
        for (int i = 0; i < (1<<N); i++) {
            for (int j = i; j > 0; j -= lowbit(j)) ones[i]++;
        }

        Scanner sc = new Scanner(System.in);
        next: while (sc.hasNext()) {
            for (int i = 0; i < N; i++) {
                String str = sc.nextLine().trim();
                if (str.length() == 0) continue next;
                arr[i] = str.toCharArray();
            }
            init();

            int cnt = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (arr[i][j] != '-') draw(i, j, arr[i][j]-'A');
                    else cnt++;
                }
            }

            dfs(cnt);

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) System.out.print(arr[i][j]);
                System.out.println();
            }
            System.out.println();
        }
    }

    private boolean dfs(int cnt) {
        if (cnt == 0) return true;

        int bknt = cnt;
        copy(state, backup_state[bknt]);
        copy(arr, backup_arr[bknt]);

        // case 1
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (arr[i][j] == '-') {
                    if (state[i][j] == 0) {
                        copy(backup_state[bknt], state);
                        copy(backup_arr[bknt], arr);
                        return false;
                    }
                    if (ones[state[i][j]] == 1) {
                        draw(i, j, cnt_log[state[i][j]]);
                        cnt--;
                    }
                }
            }
        }

        // case 2
        for (int i = 0; i < N; i++) {
            int sor = 0, sand = (1<<N)-1, drawn = 0;
            for (int j = 0; j < N; j++) {
                sand &= ~(sor & state[i][j]);
                sor |= state[i][j];
                if (arr[i][j] != '-') drawn |= state[i][j];
            }

            if (sor != (1<<N)-1) {
                copy(backup_state[bknt], state);
                copy(backup_arr[bknt], arr);
                return false;
            }

            for (int j = sand; j > 0; j -= lowbit(j)) {
                int t = lowbit(j);
                if ((drawn & t) == 0) {
                    for (int k = 0; k < N; k++) {
                        if ((state[i][k] & t) != 0) {
                            draw(i, k, cnt_log[t]);
                            cnt--;
                        }
                    }
                }
            }
        }

        // case 3
        for (int i = 0; i < N; i++) {
            int sor = 0, sand = (1<<N)-1, drawn = 0;
            for (int j = 0; j < N; j++) {
                sand &= ~(sor & state[j][i]);
                sor |= state[j][i];
                if (arr[j][i] != '-') drawn |= state[j][i];
            }

            if (sor != (1<<N)-1) {
                copy(backup_state[bknt], state);
                copy(backup_arr[bknt], arr);
                return false;
            }

            for (int j = sand; j > 0; j -= lowbit(j)) {
                int t = lowbit(j);
                if ((drawn & t) == 0) {
                    for (int k = 0; k < N; k++) {
                        if ((state[k][i] & t) != 0) {
                            draw(k, i, cnt_log[t]);
                            cnt--;
                        }
                    }
                }
            }
        }

        // case 4
        for (int i = 0; i < N; i++) {
            int sor = 0, sand = (1<<N)-1, drawn = 0;
            for (int j = 0; j < N; j++) {
                int sx = i/4*4, sy = i%4*4;
                int dx = j/4,   dy = j%4;
                int s = state[sx+dx][sy+dy];
                sand &= ~(sor & s);
                sor |= s;
                if (arr[sx+dx][sy+dy] != '-') drawn |= s;
            }

            if (sor != (1<<N)-1) {
                copy(backup_state[bknt], state);
                copy(backup_arr[bknt], arr);
                return false;
            }

            for (int j = sand; j > 0; j -= lowbit(j)) {
                int t = lowbit(j);
                if ((drawn & t) == 0) {
                    for (int k = 0; k < N; k++) {
                        int sx = i/4*4, sy = i%4*4;
                        int dx = k/4,   dy = k%4;
                        if ((state[sx+dx][sy+dy] & t) != 0) {
                            draw(sx+dx, sy+dy, cnt_log[t]);
                            cnt--;
                        }
                    }
                }
            }
        }

        // case 5
        if (cnt == 0) return true;

        // case 6
        int x = -1, y = -1, opts = 20;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (arr[i][j] == '-' && ones[state[i][j]] < opts) {
                    opts = ones[state[i][j]];
                    x = i; y = j;
                }
            }
        }

        copy(state, backup_stateTmp[bknt]);
        for (int i = state[x][y]; i > 0; i -= lowbit(i)) {
            copy(backup_stateTmp[bknt], state);
            draw(x, y, cnt_log[lowbit(i)]);
            if (dfs(cnt-1)) return true;
        }

        copy(backup_state[bknt], state);
        copy(backup_arr[bknt], arr);
        return false;
    }

    private void draw(int x, int y, int c) {
        arr[x][y] = (char) (c+'A');
        for (int i = 0; i < N; i++) {
            state[x][i] &= ~(1 << c);
            state[i][y] &= ~(1 << c);
        }

        int sx = x/4*4, sy = y/4*4;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                state[sx+i][sy+j] &= ~(1 << c);
            }
        }

        state[x][y] = 1<<c;
    }

    private void init() {
        for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) {
            state[i][j] = (1<<N)-1;
        }
    }

    private int lowbit(int n) {
        return n & (-n);
    }

    private void copy(int[][] src, int[][] backup) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, backup[i], 0, src[0].length);
        }
    }
    private void copy(char[][] src, char[][] backup) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, backup[i], 0, src[0].length);
        }
    }
}
