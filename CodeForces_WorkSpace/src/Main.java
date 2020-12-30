import java.util.*;

public class Main {

    public static void main(String[] args) {
        new Main().init();
    }

    void init() {
        Scanner sc = new Scanner(System.in);

        List<Integer>[] list = new List[14];
        for (int i = 0; i <= 13; i++) list[i] = new ArrayList<>();

        for (int i = 1; i <= 13; i++) {
            String[] str = sc.nextLine().split("\\s+");
            for (String t : str) list[i].add(change(t.charAt(0)));
        }

        int[] cout = new int[14];
        for (int i = 0; i < 4; i++) {
            int t = list[13].get(i);

            while (t != 13) {
                cout[t]++;
                int num = list[t].get(list[t].size()-1);
                list[t].remove(list[t].size()-1);
                t = num;
            }
        }

        int res = 0;
        for (int i = 1; i <= 13; i++) if (cout[i] == 4) res++;
        System.out.println(res);
    }

    private static Integer change(char ch) {
        if (ch=='A')  return 1;
        else if (ch>='2' && ch<='9') return ch-'0';
        else if (ch=='0') return 10;
        else if (ch=='J') return 11;
        else if (ch=='Q') return 12;
        else return 13;
    }
}