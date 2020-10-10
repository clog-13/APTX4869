import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class U_LQB_1473 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()) {
            int k = Integer.valueOf(sc.nextLine());
            String str = sc.nextLine();
            String[] data = str.split("\\s+|\\.");
//            int[] arr = new int[str.length()];
            List<Integer> alist = new ArrayList<>();
            List<Integer> blist = new ArrayList<>();
            int tmp = 0;
            for(int i = 0; i < data.length; i++) {
                if(data[i].equals("Alice"))
//                    arr[tmp] = 1;
                    alist.add(tmp);
                else if(data[i].equals("Bob"))
//                    arr[tmp] = 2;
                    blist.add(tmp);
                tmp += data[i].length()+1;
            }
//            for(int a : arr) System.out.print(a+"");
            int res = 0;
            for(int a : alist) {
                for(int b : blist) {
                    if(b>a && b <= a+k+5) {
//                        System.out.println(a+","+b);
                        res++;
                    }
                }
            }
            for(int b : blist) {
                for(int a : alist) {
                    if(a>b && a <= b+k+3) {
//                        System.out.println(a+","+b);
                        res++;
                    }
                }
            }


//            for(int i = 0; i < arr.length-3; i++) {
//                if(arr[i] == 1) {
//                    for(int j = i; j < arr.length-3 && j <= i+k+5; j++) {
//                        if(arr[j] == 2) res++;
//                    }
//                }else if(arr[i] == 2) {
//                    for(int j = i; j < arr.length-5 && j <= i+k+3; j++) {
//                        if(arr[j] == 1) res++;
//                    }
//                }
//            }
            System.out.println(res);
        }
    }

}
