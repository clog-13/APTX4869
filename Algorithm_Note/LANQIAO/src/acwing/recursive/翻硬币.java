package acwing.recursive;

import java.util.*;

public class 翻硬币 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String mo = sc.nextLine();
        String st = sc.nextLine();
        int[] demo = new int[mo.length()];
        int[] dest = new int[st.length()];
        for(int i = 0; i < mo.length(); i++) {
            demo[i] = mo.charAt(i) == '*' ? 1 : 0;
            dest[i] = st.charAt(i) == '*' ? 1 : 0;
        }

        int res = 0;
        for(int i = 0; i < mo.length()-1; i++) {
            if(demo[i] != dest[i]) {
                res++;
                demo[i+1] ^= 1;
            }
        }
        System.out.println(res);
    }
}