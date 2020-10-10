import java.io.PrintWriter;
import java.util.PriorityQueue;

class Draft {
    public static void main(String[] args) {
        int lval = 0xfedcba98 << 32;
        int rval = 0xfedcba98 >> 36;
        int uval = 0xfedcba98 >> 40;
        System.out.printf("%x\n", lval);
        System.out.printf("%x\n", rval);
        System.out.printf("%x\n", uval);

        char c = 'B';
        int n = 10;
        switch (c) {
            case 'A': n+=2;
            case 'B': n+=4;
            case 'C': n+=5; break;
            default: n++;
        }
        System.out.println(n);

        PriorityQueue<Integer> pq=  new PriorityQueue<>((a, b) -> (b-a));
        pq.add(1);
        pq.add(0);
        System.out.println(pq.poll());
    }
}