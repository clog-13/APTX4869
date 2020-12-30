import java.io.*;
import java.util.*;

public class Draft {
    public static void main(String[] args) {
        try{
            System.setIn(new FileInputStream("in.txt"));  // 输入重定向
            System.setOut(new PrintStream(new File("out.txt")));    // 输出重定向
            Scanner sc = new Scanner(new BufferedInputStream(System.in));
            while (sc.hasNext()) {
                System.out.println(sc.nextLine());
            }
        }catch (Exception e){
            e.printStackTrace();
        }   // 本地调试用，提交的时候把这个try-catch注释掉，oj会自动重定向输入流。
    }
}
