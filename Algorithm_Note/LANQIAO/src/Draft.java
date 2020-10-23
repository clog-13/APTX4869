import java.util.*;

class Draft {
    public static void main(String[] args) {
//        int[][] towers = new int[][][[32,36,27],[17,22,43],[8,11,41],[46,28,7],[22,4,35],[41,8,33],[32,29,4],[44,32,16],[33,20,16],[3,38,35],[17,47,23],[33,0,29],[29,19,6],[4,50,46],[19,47,6],[48,6,41],[20,26,35]];
        int[][] towers = new int[12][12];
        int radius = 2;

        double[][] dist = new double[60][60];
        for (int[] t : towers) {
            for (int i = Math.max(0, t[0]-radius); i <= Math.min(50, t[0]+radius); i++) {
                for (int j = Math.max(0, t[1]-radius); j <= Math.min(50, t[1]+radius); j++) {
//                    double tmp = Math.floor((double) t[2] / (double) (1.0+getDist(t[0], t[1], i, j)));
                    int[] arr1 = {t[0], t[1]};
                    int[] arr2 = {i, j};
                    double tmp = Math.floor((double) t[2] / (double) (1.0+calculateDistance(arr1, arr2)));
                    dist[i][j] += tmp;
                }
            }
        }

        double sum = -1;
        int x = -1, y = -1;
        for (int i = 0; i <= 50; i++) {
            for (int j = 0; j <= 50; j++) {
                if (dist[i][j] > sum) {
                    sum = dist[i][j];
                    x = i;
                    y = j;
                    System.out.println();
                }
            }
        }
        System.out.println(x+","+y);
//        return new int[]{x, y};
    }

    private static int getDist(int x, int y, int xx, int yy) {
        return Math.abs(x-y)+Math.abs(xx-yy);
    }

    private static double calculateDistance(int[] array1, int[] array2) {
        double res = 0.0;
        for(int i=0; i<array1.length; i++)
            res += Math.pow((array1[i]-array2[i]), 2.0);
        return Math.sqrt(res);
    }
}