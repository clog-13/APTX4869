# 190. 字串变换

已知有两个字串 A, B 及一组字串变换的规则（至多 6 个规则）:

A1→B1

A2→B2

…

规则的含义为：在 A 中的子串 A1 可以变换为 B1、A2 可以变换为 B2…。

例如：A＝`abcd` B＝`xyz`

变换规则为：

```
abc → xu ud → y y → yz
```

则此时，A 可以经过一系列的变换变为 B，其变换的过程为：

```
abcd → xud → xy → xyz
```

共进行了三次变换，使得 A 变换为 B。

#### 输入格式

输入格式如下：

```
A B
A1 B1
A2 B2
… …
```

第一行是两个给定的字符串 A 和 B。

接下来若干行，每行描述一组字串变换的规则。

所有字符串长度的上限为 20。

#### 输出格式

若在 10 步（包含 10 步）以内能将 A 变换为 B ，则输出最少的变换步数；否则输出 `NO ANSWER!`。

#### 输入样例：

```
abcd xyz
abc xu
ud y
y yz
```

#### 输出样例：

```
3
```



## 迭代加深

```java
import java.util.*;

class Main {
    static String[] arr = new String[6], brr = new String[6];
    static String S, E;
    static int idx;
    static HashMap<String, Integer> map1, map2;

    public static void main (String [] args) {
        Scanner sc = new Scanner(System.in);
        map1 = new HashMap<>(); map2 = new HashMap<>();
        String[] str = sc.nextLine().split(" ");
        S = str[0]; E = str[1];
        while (sc.hasNext()) {
            str = sc.nextLine().split(" ");
            arr[idx] = str[0]; brr[idx] = str[1];
            idx++;
        }
        int res = bfs();
        if (res == 11) System.out.println("NO ANSWER!");
        else System.out.println(res);
    }

    static int bfs () {
        Queue<String> queue1 = new LinkedList<>(), queue2 = new LinkedList<>();
        queue1.offer(S); queue2.offer(E);
        map1.put(S, 0); map2.put(E, 0) ;
        int step = 0 ;
        while (!queue1.isEmpty() && !queue2.isEmpty()) {  // 迭代加深
            int t;
            if (queue1.size() <= queue2.size()) {
                t = extend(queue1, map1, map2, arr, brr);
            } else {
                t = extend(queue2, map2, map1, brr, arr);
            }
            if (t <= 10) return t;
            if (step++ > 10) break;
        }
        return 11;
    }

    static int extend(Queue<String> queue, HashMap<String,Integer> da, HashMap<String,Integer> db, 
                      String[] arr, String[] brr){
        for (int s = 0, size = queue.size(); s < size; s++) {
            String cur = queue.poll();
            for (int i = 0; i < cur.length(); i++) {
                for (int j = 0; j < idx; j++) {
                    if (cur.startsWith(arr[j], i)) {
                        String next = cur.substring(0, i) + brr[j] + cur.substring(i+ arr[j].length());
                        if (da.containsKey(next)) continue;
                        if (db.containsKey(next)) return da.get(cur) + db.get(next) + 1;
                        da.put(next, da.get(cur) + 1) ;
                        queue.offer(next);
                    }
                }
            }
        }
        return 11;
    }
}
```



```java
import java.util.*;

class Main {
    static String[] arr = new String[6], brr = new String[6];
    static String S, E;
    static int idx;
    static HashMap<String, Integer> map1, map2;

    public static void main (String [] args) {
        Scanner sc = new Scanner(System.in);
        map1 = new HashMap<>(); map2 = new HashMap<>();
        S = sc.next(); E = sc.next();
        while (sc.hasNext()) {
            arr[idx] = sc.next(); brr[idx] = sc.next();
            idx++;
        }

        int res = bfs();
        if (res == 11) System.out.println("NO ANSWER!");
        else System.out.println(res);
    }
    
    static int bfs () {
        Queue<String> queue1 = new LinkedList<>(), queue2 = new LinkedList<>();
        queue1.offer(S); queue2.offer(E);
        map1.put(S, 0); map2.put(E, 0) ;
        int step = 0 ;
        while (!queue1.isEmpty() && !queue2.isEmpty()) {  // 迭代加深
            int t = 0;
            if (queue1.size() <= queue2.size()) {
                t = extend(queue1, map1, map2, arr, brr);
            } else {
                t = extend(queue2, map2, map1, brr, arr);
            }
            if (t <= 10) return t;
            if (step++ > 10) break;
        }
        return 11;
    }

    static int extend(Queue<String> queue, HashMap<String,Integer> da, HashMap<String,Integer> db,  
                      String[] arr, String[] brr){
        int curCnt = da.get(queue.peek());   // 只bfs下一层, 为了得到最小解（迭代加深）
        while (!queue.isEmpty() && da.get(queue.peek()) == curCnt) {
            String cur = queue.poll();
            for (int i = 0; i < cur.length(); i++) {
                for (int j = i+1; j <= cur.length(); j++) {
                    String sub = cur.substring(i, j);
                    for (int k = 0; k < idx; k++) {
                        if (arr[k].equals(sub)) {
                            String next = cur.substring(0, i) + brr[k] + cur.substring(j);
                            
                            if (db.containsKey(next)) return da.get(cur) + db.get(next) + 1;
                            if (da.containsKey(next)) continue;
                            da.put(next, da.get(cur) + 1) ;
                            queue.offer(next);
                        }
                    }
                }
            }
        }
        return 11;
    }
}
```

