import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.Comparator;

public class HDU1007_QuoitDesign {
    static class Node {
        double x, y;
        public Node(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int n = (int) in.nval;
            if (n == 0) break;

            Node[] arr = new Node[n];
            for(int i = 0; i < n; i++) {
                in.nextToken(); double x = in.nval;
                in.nextToken(); double y = in.nval;
                arr[i] = new Node(x, y);
            }
            Arrays.sort(arr, new Comparator<Node>() {
                @Override
                public int compare(Node n1, Node n2) {
                    return n1.y == n2.y ? (n1.x-n2.x > 0 ? 1 : -1) : (n1.y-n2.y>0?1:-1);
                }
            });

            double res = search(arr, 0, n-1);
            out.println(String.format("%.2f", Math.sqrt(res) / 2));
            out.flush();
        }


    }

    private static double search(Node[] arr, int left, int right) {
        int mid = (left+right) / 2;
        double min = Double.MAX_VALUE;

        if(left == right)
            return Double.MAX_VALUE;
        else if(left+1 == right)
            min = (arr[left].x-arr[right].x) * (arr[left].x-arr[right].x)
                    + (arr[left].y-arr[right].y) * (arr[left].y-arr[right].y);
        else
            min = Math.min(search(arr, left, mid), search(arr, mid, right));

        int ll = mid, rr = mid+1;
        while(arr[mid].y-arr[ll].y <= Math.sqrt(min) && ll > left) ll--;
        while(arr[rr].y-arr[mid].y <= Math.sqrt(min) && rr < right) rr++;

        for(int i = ll; i <= rr; i++) {
            for(int j = i+1; j <= rr; j++) {
                double tmp=0;
                if(Math.abs((arr[i].x-arr[j].x)*(arr[i].x-arr[j].x)) > min)
                    continue;
                else {
                    tmp=(arr[i].x-arr[j].x)*(arr[i].x-arr[j].x)
                            +(arr[i].y-arr[j].y)*(arr[i].y-arr[j].y);
                    if(tmp < min) min = tmp;
                }
            }
        }

        return min;
    }
}
