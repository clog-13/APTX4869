import java.util.*;

class Solution {
    public static void main(String[] args) {
        new Solution().avoidFlood(new int[]{1,0,2,3,0,1,2});
    }

    public int[] avoidFlood(int[] rains) {
        int N = rains.length, cnt = 0;
        int[] res = new int[N];
        Arrays.fill(res, -1);
        Deque<Integer> sunDay = new ArrayDeque<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < N; i++) {
            if (rains[i] > 0) {
                if (map.containsKey(rains[i])) {
                    if (sunDay.size() > 0) {
                        int t = sunDay.pollFirst();
                        if (t < map.get(rains[i])) return new int[]{};
                        else {
                            res[t] = rains[i];
                            map.put(rains[i], i);
                        }
                    } else {
                        return new int[]{};
                    }
                } else {
                    map.put(rains[i], i);
                }
            } else {
                sunDay.addLast(i);
                res[i] = 1;
            }
        }
        return res;
    }
}