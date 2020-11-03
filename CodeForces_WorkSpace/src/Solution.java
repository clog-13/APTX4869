class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] arr = {37,69,3,74,46};
        int[][] pieces = {{37,69,3,74,46}};
        sol.canFormArray(arr, pieces);
    }

    public boolean canFormArray(int[] arr, int[][] pieces) {
        boolean[] vis = new boolean[pieces.length];
        outer: for (int i = 0; i < arr.length; ) {
            for (int j = 0; j < pieces.length; j++) {
                if (vis[j]) continue;
                if (pieces[j][0] == arr[i]) {
                    vis[j] = true;
                    for (int k = 0; k < pieces[j].length; k++) {
                        if (arr[i+k] != pieces[j][k]) return false;
                    }
                    i += pieces.length;
                    continue outer;
                }
            }
            return false;
        }
        return true;
    }
}