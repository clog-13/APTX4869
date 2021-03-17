import java.util.*;

class Solution {
    int row, col, res = 0;
    int[] dx = {0, 0,-1, 1}, dy = {-1, 1, 0, 0};
    char[][] grid;

    public static void main(String[] args) {
        Solution sol = new Solution();
        char[][] g = {{'#','.','.','#','T','#','#','#','#'},
 {'#','.','.','#','.','#','.','.','#'},
 {'#','.','.','#','.','#','B','.','#'},
 {'#','.','.','.','.','.','.','.','#'},
 {'#','.','.','.','.','#','.','S','#'},
 {'#','.','.','#','.','#','#','#','#'}};
        System.out.println(sol.minPushBox(g));
    }
    
    
    public int minPushBox(char[][] grid) {
        this.grid = grid;
        row = grid.length; col =grid[0].length;

        Node man = new Node(0, 0), box = new Node(0, 0);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 'S') man = new Node(i, j);
                if (grid[i][j] == 'B') box = new Node(i, j);
            }
        }

        if (bfs_box(man, box)) return res;
        else return -1;
    }

    boolean bfs_box(Node man, Node box) {
        boolean[][] st = new boolean[row][col];
        st[box.x][box.y] = true;
        Queue<Node> queue = new LinkedList<>();
        box.px = man.x; box.py = man.y;
        queue.add(box);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int s = 0; s < size; s++) {
                Node cur = queue.poll();
                if (grid[cur.x][cur.y] == 'T') return true;
                for (int d = 0; d < 4; d++) {
                    int bx = cur.x+dx[d], by = cur.y+dy[d];
                    int mx = cur.x-dx[d], my = cur.y-dy[d];
//                    boolean f1 = check(bx, by);
//                    boolean f2 = check(mx, my);
//                    boolean f3 = !st[bx][by];
//                    boolean f4 = bfs_man(new Node(cur.px, cur.py), new Node(mx, my), cur);
                    if (check(bx, by) && check(mx, my)
                            && bfs_man(new Node(cur.px, cur.py), new Node(mx, my), cur)) {
                        st[bx][by] =true;
                        queue.add(new Node(bx, by, cur.x, cur.y));
                    }
                }
            }
            res++;
        }
        return false;
    }

    boolean bfs_man(Node man, Node to, Node box) {
        boolean[][] st = new boolean[row][col];
        st[man.x][man.y] = true;
        st[box.x][box.y] = true;
        Queue<Node> queue = new LinkedList<>();
        queue.add(man);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            if (cur.x==to.x && cur.y==to.y) return true;
            for (int d = 0; d < 4; d++) {
                int tx = cur.x+dx[d], ty = cur.y+dy[d];
                if (check(tx, ty) && !st[tx][ty]) {
                    st[tx][ty] = true;
                    queue.add(new Node(tx, ty));
                }
            }
        }
        return false;
    }

    boolean check(int x, int y) {
        return x>=0 && x<row && y>=0 && y<col && grid[x][y]!='#';
    }

    static class Node{
        int x, y, px, py;
        Node(int xx, int yy) {
            x = xx; y = yy; px = 0; py = 0;
        }
        Node(int xx, int yy, int pxx, int pyy) {
            x = xx; y = yy; px = pxx; py = pyy;
        }
    }
}