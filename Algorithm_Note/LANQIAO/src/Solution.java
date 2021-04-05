import java.util.PriorityQueue;
import java.util.Queue;

class Solution {
    public static void main(String[] args) {
        Solution sol = new Solution();
//        System.out.println(sol.orchestraLayout(10000_0000,50000_000, 5000_000));

        System.out.println(sol.orchestraLayout(7,2,2));
        System.out.println(sol.orchestraLayout(7,2,3));
        System.out.println(sol.orchestraLayout(7,2,4));
        System.out.println(sol.orchestraLayout(7,3,4));

        System.out.println(sol.orchestraLayout(7,4,4));


        System.out.println(sol.orchestraLayout(7,4,3));
        System.out.println(sol.orchestraLayout(7,4,2));
        System.out.println(sol.orchestraLayout(7,3,2));

        System.out.println(sol.orchestraLayout(7,3,3));



    }

    public int orchestraLayout(int num, int xPos, int yPos) {
        if (num==1) return 1;
        if (num==2) {
            if (xPos==0) {
                if (yPos==0) return 1;
                else return 2;
            } else {
                if (yPos==0) return 4;
                else return 3;
            }
        }

        int mi = Math.min(Math.min(yPos, num-1-yPos), Math.min(xPos, num-1-xPos));

        int T = mi, len = num;
        long cnt = (4L *len-4)*T + (long) T *(T-1)/2*(-8);
        len -= 2*T;

        long s = cnt%9+1;

        if (xPos == mi) {  //上
            int tx = mi, ty = mi;

            int d = yPos-ty;
            s += d;
            if (s%9==0) return 9;
            else return (int) (s%9);
        } else if (xPos == num-1-mi) {  // 下
            int tx = num-1-mi, ty = num-1-mi;
            s += 2*len-2;

            int d = ty-yPos;
            s += d;
            if (s%9==0) return 9;
            else return (int) (s%9);

//            for ( ; ty>=mi; ty--) {
//                s++;
//                if (tx == xPos && ty == yPos) {
//                    if (s%9==0) return 9;
//                    else return (int) (s%9);
//                }
//            }
        } else {  // 左右
            if (yPos == num-1-mi) {  // 左
                s += len-1;
                int tx = mi, ty = num-1-mi;

                int d = xPos-tx;
                s += d;
                if (s%9==0) return 9;
                else return (int) (s%9);

//                for ( ; tx<=num-1-mi; tx++) {
//                    s++;
//                    if (tx == xPos && ty == yPos) {
//                        if (s%9==0) return 9;
//                        else return (int) (s%9);
//                    }
//                }
            } else {  // 右
                int tx = num-1-mi, ty = mi;
                s+=3*len-3;

                int d = tx-xPos;
                s += d;
                if (s%9==0) return 9;
                else return (int) (s%9);

//                for ( ; tx>=mi; tx--) {
//                    s++;
//                    if (tx == xPos && ty == yPos) {
//                        if (s%9==0) return 9;
//                        else return (int) (s%9);
//                    }
//                }
            }
        }

    }
}