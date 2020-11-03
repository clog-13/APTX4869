import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[3];
        for (int i = 0; i < N; i++) {
            String[] str = br.readLine().split(" ");
            arr[0] += Integer.parseInt(str[0]);
            arr[1] += Integer.parseInt(str[1]);
            arr[2] += Integer.parseInt(str[2]);
        }
        boolean flag = true;
        for (int a : arr) if (a != 0) flag = false;
        if (flag) System.out.println("YES");
        else System.out.println("NO");
    }
}