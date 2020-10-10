package acwing.greedy;

import java.util.Scanner;

public class 糖果传递_WA {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] data = new int[N];
        int sum = 0;
        for(int i = 0; i < N; i++) {
            data[i] =  sc.nextInt();
            sum += data[i];
        }

        int ave = sum / N;
        long res = 0;
        outer: for(int i = 0; i < N; i++) {
            if(data[i] == ave) continue;
            int dis = ave - data[i];
            int s = 1;
            while(dis > 0) {
                int led = data[(i-s+N) % N] - ave;
                if(led > 0) {
                    res += s;
                    if(led < dis) {
                        data[i] += led;
                        data[(i-s+N) % N] -= led;
                    }else {
                        data[i] += dis;
                        data[(i-s+N) % N] -= dis;
                        continue outer;
                    }
                }
                int rid = data[(i+s+N) % N] - ave;
                if(rid > 0) {
                    res += s;
                    if(rid < dis) {
                        data[i] += rid;
                        data[(i+s+N) % N] -= rid;
                    }else {
                        data[i] += dis;
                        data[(i+s+N) % N] -= dis;
                        continue outer;
                    }
                }
                s++;
                dis = ave - data[i];
            }
        }

        System.out.println(res);
    }
}
