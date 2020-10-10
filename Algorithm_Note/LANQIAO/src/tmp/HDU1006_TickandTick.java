import java.util.Scanner;

/**
 * Tick and Tick
 * http://acm.hdu.edu.cn/showproblem.php?pid=1006
 *
 * 时针一小时转30度，一分钟转0.5度。分针一小时转360度，一分钟转6度。秒针一分钟转360度。
 * 所以每一秒内 时针转动1/120度；分针转动1/10度；秒针转6度。
 *
 * 三个指针走的角速度：
 * 秒针速度S = 6°/s，分针速度M = (1/10)°/s，时针速度H = (1/120)°/s
 * 这三个指针两两之间的相对速度差为：
 * 秒时相差S_H = (719/120)°/s，秒分相差S_M = (59/10)°/s，分时相差M_H = (120/11)°/s
 * 相差一度需要的时间为：
 * 秒时相差SH = (120/719)s/度，秒分相差SM = (10/59)s/度，分时相差MH = (11/120)s/度
 */

public class HDU1006_TickandTick {
    public static void main(String args[]) {
        Scanner s = new Scanner(System.in);
        while(true) {
            double D = s.nextDouble();
            if(D==-1) break;

            // 分 时
            double mhmin[] = new double[11];
            double mhmax[] = new double[11];//这里存储的不是角度，而是时间
            for(int i=0;i<11;i++){
                //360*i+D为俩指针之间最小相差角度，相差一度需要的时间为120/11，所以*120/11转换为相差这么多度需要的时间。
                mhmin[i] = (360*i+D)*120/11;
                mhmax[i] = (360*i+360-D)*120/11;
            }
            // 秒 分(59*12=708)
            double smmin[] = new double[708];
            double smmax[] = new double[708];
            for(int i=0;i<708;i++){
                smmin[i]=(360*i+D)/5.9;
                smmax[i]=(360*i+360-D)/5.9;
            }
            // 秒 时(708+11=719)
            double shmin[] = new double[719];
            double shmax[] = new double[719];
            for(int i=0;i<719;i++){
                shmin[i] = (360*i+D)*120/719;
                shmax[i] = (360*i+360-D)*120/719;
            }
            double countTime=0;
            for(int i = 0; i < 11; i++){
                for(int j = 0; j < 708; j++){
                    if(smmin[j]<mhmax[i] && mhmin[i]<smmax[j]){
                        for(int k = 0; k < 719; k++){
                            if(shmin[k]<smmax[j] && shmin[k]<mhmax[i]
                                    && smmin[j]<shmax[k] && mhmin[i]<shmax[k]){
                                double min = Max(shmin[k],smmin[j],mhmin[i]);
                                double max = Min(shmax[k],smmax[j],mhmax[i]);
                                countTime += (max-min);
                            }
                        }
                    }
                }
            }
            double percent=countTime/432;
            System.out.printf("%.3f", percent);
            System.out.println();
        }
    }

    private static double Min(double d, double e, double f) {
        double min=0;
        min=d>e?e:d;
        min=min>f?f:min;
        return min;
    }

    private static double Max(double d, double e, double f) {
        double max=0;
        max=d>e?d:e;
        max=max>f?max:f;
        return max;
    }

}