# 高精度

## 高精度加法

给定两个正整数，计算它们的和。

**输入格式**

共两行，每行包含一个整数。

**输出格式**

共一行，包含所求的和。

**数据范围**

1≤整数长度≤100000

**输入样例：**

```
12
23
```

**输出样例：**

```
35
```

**code**

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String a = scan.nextLine(), b = scan.nextLine();

        List<Integer> A = new ArrayList<>(), B = new ArrayList<>();
        for (int i = a.length()-1; i >= 0; i--) A.add(a.charAt(i) - '0');  // 低位放在前面
        for (int i = b.length()-1; i >= 0; i--) B.add(b.charAt(i) - '0');

        List<Integer> res = add(A, B);
        for (int i = res.size()-1; i >= 0; i--) System.out.print((res.get(i)));
    }

    static List<Integer> add(List<Integer> A, List<Integer> B) {
        if (A.size() < B.size()) return add(B, A);
        int t = 0;
        for (int i = 0; i < A.size(); i++) {
            t += A.get(i);
            if (i < B.size()) t += B.get(i);
            A.set(i, t % 10);
            t /= 10;
        }
        if (t != 0) A.add(t);
        return A;
    }
}
```



## 高精度减法

给定两个正整数，计算它们的差，计算结果可能为负数。

**输入格式**

共两行，每行包含一个整数。

**输出格式**

共一行，包含所求的差。

**数据范围**

1≤整数长度≤105

**输入样例：**

```
32
11
```

**输出样例：**

```
21
```



**code**

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String a = sc.next(), b = sc.next();

        List<Integer> A = new ArrayList<>(), B = new ArrayList<>();
        for (int i = a.length()-1; i >= 0; i--) A.add(a.charAt(i) - '0');  // 低位在前面
        for (int i = b.length()-1; i >= 0; i--) B.add(b.charAt(i) - '0');
        if(!cmp(A, B)) System.out.print("-");  // 若该数为负数,添上负号
        List<Integer> res = sub(A, B);
        for (int i = res.size()-1; i >= 0; i--) System.out.print((res.get(i)));
    }

    public static List<Integer> sub(List<Integer> A,List<Integer> B) {
        if(!cmp(A, B)) return sub(B, A);
        for (int i = 0, t = 0; i < A.size(); i++) {
            t = A.get(i) - t;
            if (i < B.size()) t -= B.get(i);
            A.set(i, (t + 10) % 10);
            t = t<0 ? 1 : 0;
        }
        while (A.size()>1 && A.get(A.size()-1)==0) A.remove(A.size()-1);  // 去掉前置零
        return A;
    }

    public static boolean cmp(List<Integer> A ,List<Integer> B) {
        if (A.size() != B.size()) return A.size() > B.size();
        for (int i = A.size()-1; i >= 0; i--) {
            if (A.get(i) != B.get(i)) {
                return A.get(i) > B.get(i);
            }
        }
        return true;
    }
}
```



## 高精度乘法

给定两个正整数 A 和 B，请你计算 A×B 的值。

#### 输入格式

共两行，第一行包含整数 A，第二行包含整数 B。

#### 输出格式

共一行，包含 A×B 的值。

#### 数据范围

1≤A的长度≤100000, 0≤B≤10000

#### 输入样例：

```
2
3
```

#### 输出样例：

```
6
```

**Code**

```java
import java.util.*;
class Main {
    public static void main(String[] args) {
        new Main().run();
    }
    
    void run() {
        Scanner sc = new Scanner(System.in);
        String a = sc.nextLine();
        int B = sc.nextInt();
        if (B==0) {
            System.out.println(0);
            return;
        }
        List<Integer> A = new ArrayList<>();
        for (int i = a.length()-1; i >= 0; i--) A.add(a.charAt(i)-'0');
        List<Integer> res = mul(A, B);
        for (int i = res.size()-1; i >= 0; i--) System.out.print(A.get(i));
    }
    
    List<Integer> mul(List<Integer> A, int B) {
        int t = 0;
        for (int i = 0; i < A.size(); i++) {
            t += A.get(i)*B;
            A.set(i, t%10);
            t /= 10;
        }
        while (t != 0) {
            A.add(t%10);
            t/=10;
        }
        return A;
    }
}
```





## 高精度除法

给定两个非负整数 A，B，请你计算 A/B 的商和余数。

**输入格式**

共两行，第一行包含整数 A，第二行包含整数 B。

**输出格式**

共两行，第一行输出所求的商，第二行输出所求余数。

**数据范围**

1≤A的长度≤100000, 1≤B≤10000, B 一定不为 0

**输入样例：**

```
7
2
```

**输出样例：**

```
3
1
```

**code**

```java
import java.util.*;

public class Main {
    int t = 0;
    public static void main(String[] args) {
        new Main().run();
    }
    
    void run() {
        Scanner sc = new Scanner(System.in);
        String a = sc.next();
        int B = sc.nextInt();
        List<Integer> A = new ArrayList<Integer>();
        for (int i = a.length()-1; i >= 0; i--) A.add(a.charAt(i)-'0');
        List<Integer> res = div(A, B);

        for (int i = res.size()-1; i >= 0; i--) System.out.print((res.get(i)));
        System.out.println("\n"+t);
    }
            
    List<Integer> div(List<Integer> A, int B) {  // 从高位往低位除
        for (int i = A.size()-1; i >= 0; i--) {
            t = t * 10 + A.get(i);
            A.set(i, t / B);
            t %= B;
        }
        while (A.size()>1 && A.get(A.size()-1)==0) A.remove(A.size()-1);
        return A;
    }
}
```

