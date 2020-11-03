import java.util.*;

class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
        sol.mostVisited(2, new int[]{2,1,2,1,2,1,2,1,2});
    }


    public List<Integer> mostVisited(int n, int[] rounds) {
        List<Integer> res = new ArrayList<Integer>();
        int[] cout = new int[110];
        int max = 0;

        outer:
        for (int i = 0; i < rounds.length-1; i++) {
            for (int j = rounds[i]; ; j++) {
                while (j > n) j -= n;
                if (j == rounds[i+1]) continue outer;

                cout[j]++;
                max = Math.max(max, cout[j]);
            }
        }
        cout[rounds[rounds.length-1]]++;
        max = Math.max(max, cout[rounds[rounds.length-1]]);

        for (int i = 0; i < cout.length; i++) {
            if (cout[i] == max) {
                res.add(i);
            }
        }
        return res;
    }
}