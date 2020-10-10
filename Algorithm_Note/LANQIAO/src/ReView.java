import java.io.*;
import java.util.*;

public class ReView {
    static final String BETA_PATH = "C:/APTX4869/Algorithm_Note/LANQIAO/src/ReView_Beta.txt";
//    static final String BETA_PATH = "/ReView_Beta.txt";
    static final String PATH = BETA_PATH;

    static final String PEEK = "peek";
    static final String UPDATE = "update";
    static final String ADD = "add";
    static final String FIND = "find";  // TODO
    static final String BACKUP = "backup";    // TODO
    static final String REVOKE = "revoke";    // TODO


    public static void main(String[] args) throws IOException {
        init();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.nextLine();
            switch (str) {
                case PEEK :
                    peek();
                    break;
                case UPDATE:
                    modify(0, "");
                    break;
                case ADD:
                    System.out.println("--->");
                    String s = sc.nextLine();
                    modify(1, s);
                    break;
                default:
                    System.out.println("!!!NO FIND COMMAND!!!");
                    break;
            }
        }
    }

    private static void init() {

    }

    private static void modify(int n, String s) throws IOException {
        System.setIn(new FileInputStream(new File(PATH)));  // 输入重定向

        Scanner sc = new Scanner(new BufferedInputStream(System.in));

        Queue<String> queue = new LinkedList<>();
        while (sc.hasNext())
            queue.offer(sc.nextLine());

        FileOutputStream fo = new FileOutputStream(new File(PATH));

        if (n == 0) {
            String cur = queue.poll();
            queue.offer(cur);
        } else if (n == 1) {
            queue.offer(s);
        }


        while (!queue.isEmpty()) {
            String cur = queue.poll();
            cur += "\n";
            byte[] tmp = cur.getBytes();
            fo.write(tmp, 0, tmp.length);
        }

        System.out.println("done!");
        sc.close();
        fo.close();
    }

    private static void peek() throws FileNotFoundException {
        System.setIn(new FileInputStream(new File(PATH)));  // 输入重定向
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        System.out.println(sc.nextLine());

        sc.close();
    }
}