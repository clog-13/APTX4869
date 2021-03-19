class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[][] m = {{1,2,3,4},{5,6,7,8},{9,10,11,12}};
        sol.spiralOrder(m);
    }

    public int[] spiralOrder(int[][] mat) {
        int row = mat.length, col = mat[0].length;
        int le = 0, ri = col - 1, to = 0, bo = row - 1;
        int[] res = new int[row * col];
        int idx = 0, tar = row * col;
        while (idx < tar) {
            for (int i = le; i <= ri; i++) {
                res[idx++] = mat[to][i]; // left to right.
                if (idx >= tar) break;
            }
            to++;
            for (int i = to; i <= bo; i++) {
                res[idx++] = mat[i][ri]; // top to bottom.
                if (idx >= tar) break;
            }
            ri--;
            for (int i = ri; i >= le; i--) {
                res[idx++] = mat[bo][i]; // right to left.
                if (idx >= tar) break;
            }
            bo--;
            for (int i = bo; i >= to; i--) {
                res[idx++] = mat[i][le]; // bottom to top.
                if (idx >= tar) break;
            }
            le++;
        }
        return res;
    }
}