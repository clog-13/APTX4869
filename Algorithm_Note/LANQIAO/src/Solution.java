import java.io.*;
import java.util.*;

class Solution {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
//    char[] list = {'a','b','c','d','e','f','g'}
    char[][] word;
    boolean[][] st;
    Random rand = new Random();

    public static void main(String[] args) throws IOException {
        new Solution().run();
    }

    void run() throws IOException {
        int[] dx = {-1, 1, 0, 0}, dy = { 0, 0,-1, 1};
//        int T = Integer.parseInt(br.readLine());
        int T = 1;
        while (T-- > 0) {
            char[] arr = br.readLine().toCharArray();
            int N = arr.length;
            word = new char[N][N];
            st = new boolean[N][N];

            int x = N/2, y = N/2, idx = 0, bt = 0;
            word[x][y] = arr[idx++];
            word[x][y] = 'X';
            st[x][y] = true;
            while (idx<N) {
                boolean flag = false;
                do {
                    int op = rand.nextInt(4);
                    int tx = x + dx[op], ty = y + dy[op];
                    if (tx<0 || tx>=N || ty<0 || ty>=N) continue;
                    bt++;
                    if (!st[tx][ty]) {
                        x = tx; y = ty;
                        flag = true;
                    }
                    if (bt > 400) return;
                } while (!flag);
                word[x][y] = arr[idx++];
                st[x][y] = true;
            }
            print();
        }
    }

    void print() {
        fill();
        for (char[] ch: word) {
            for (int j = 0; j < word[0].length; j++) {
                System.out.print(ch[j]);
            }
            System.out.println();
        }
    }

    void fill() {
        for (int i = 0; i < word.length; i++) {
            for (int j = 0; j < word[0].length; j++) {
                if (!st[i][j]) word[i][j] = (char)(rand.nextInt(26)+'a');
            }
        }
    }

    void clear() {

    }
}
