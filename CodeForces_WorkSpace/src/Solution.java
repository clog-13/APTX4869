import java.util.*;

public class Solution {
    public int eatenApples(int[] apples, int[] days) {
        PriorityQueue<Apple> pq = new PriorityQueue<>(Comparator.comparingInt(x -> x.valid));
        int res = 0;
        int N = apples.length;
        for (int i = 1; i <= N; i++) {
            pq.add(new Apple(apples[i-1], i + days[i-1]));
            while (!pq.isEmpty()) {
                Apple cur = pq.poll();
                if (cur.remain == 0 || cur.valid <= i) continue;
                res++;
                cur.remain--;
                pq.add(cur);
                break;
            }
        }

        for (int i = N + 1; !pq.isEmpty(); ) {
            while (!pq.isEmpty()) {
                Apple cur = pq.poll();
                if (cur.remain == 0 || cur.valid <= i) continue;
                int used = Math.min(cur.remain, cur.valid-i);
                i += used;
                res += used;
                break;
            }
        }
        return res;
    }
}

class Apple {
    int remain, valid;

    public Apple(int remain, int valid) {
        this.remain = remain;
        this.valid = valid;
    }
}