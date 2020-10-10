package lqb_pastExamPaper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.Stack;

public class Repeat_Program {
    public static void main(String[] args) {
        try{
            System.setIn(new FileInputStream(new File("/home/xiiv/Desktop/Algorithm_Note/LANQIAO/testD.txt")));  // 输入重定向
//            System.setOut(new PrintStream(new File("out.txt")));    // 输出重定向
        }catch (Exception e){
            e.printStackTrace();
        }   // 本地调试用，提交的时候把这个try-catch注释掉，oj会自动重定向输入流。

        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        String[] srr = sc.nextLine().split("");
        int res = Integer.parseInt(srr[srr.length-1]);
        Stack<Integer> repNum = new Stack<>(), coutNum = new Stack<>();
        repNum.push(1); coutNum.push(res);
        int curLayer = 0;
        while (sc.hasNext()) {
            srr = sc.nextLine().split("");
            int k = 0;
            while (srr[k].equals(" ")) k++;

            if (k > curLayer) curLayer = k;
            if (k < curLayer) {
                curLayer = k;
                int tmp = coutNum.pop() * repNum.pop();
                coutNum.push(coutNum.pop() + tmp);
            }
            if (srr[srr.length-1].equals(":")) {    // new REP
                repNum.push(Integer.parseInt(srr[srr.length - 2]));
                coutNum.push(0);
            } else {    // 同一层下为（a+b+c+...） * Ra
                int tmp = coutNum.pop();
                tmp += Integer.parseInt(srr[srr.length - 1]);
                coutNum.push(tmp);
            }
        }

        while (coutNum.size() != 1) {
            int tmp = coutNum.pop() * repNum.pop();
            coutNum.push(coutNum.pop() + tmp);
        }
        System.out.println(coutNum.pop());
    }
}
