package acwing.enum_simulation;

import java.util.*;


/**
 * 时间要优化掉，不能一一遍历 TODO
 */

public class 外卖店优先级_WA_TLE {
    //n家店，m条信息，t时刻结束
    static int n,m,t;
    static Map<Integer, ArrayList<Integer>> map = new TreeMap<>();
    static int result = 0;

    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        n = sc.nextInt();
        m = sc.nextInt();
        t = sc.nextInt();
        //添加数据，按照店铺id号进行分类
        for(int i = 1; i <= m; i++) {
            int time = sc.nextInt();
            int id = sc.nextInt();
            if (map.containsKey(id)) {
                map.get(id).add(time);
            }else {
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(time);
                map.put(id, temp);
            }
        }

        ArrayList<Map.Entry<Integer, ArrayList<Integer>>> list = new ArrayList<>(map.entrySet());
        for(int i = 0; i < list.size(); i++) {  // 遍历每家店
            ArrayList<Integer> arrayList = list.get(i).getValue();  // 遍历店的所有订单

            int num = 0;
            boolean flag = false;
            int[] count = new int[t + 1];       // 记录某一时刻的订单数
            for(int j = 0; j < arrayList.size(); j++)
                count[arrayList.get(j)]++;

            for(int j = 1; j <= t; j++) {       // 按时间遍历
                if(count[j] == 0) {
                    if(num > 0) num--;
                    if(num <= 3) flag = false;
                }else {
                    num += count[j] * 2;
                    if(num > 5) flag = true;
                }
            }

            /**
             * WA Result:订单要小于等于 3 才会被删除 优先队列
             */
//            if(num > 5) result++;
            if(num > 3 && flag) result++;
        }
        System.out.println(result);
    }
}