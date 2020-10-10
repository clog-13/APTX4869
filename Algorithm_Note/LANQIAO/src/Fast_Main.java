import java.io.*;
import java.math.BigInteger;

public class Fast_Main {

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader sc = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);

//        int N = sc.nextInt();
//        out.println(N);

        out.close();
    }

    static class InputReader{
        StreamTokenizer tokenizer;
        public InputReader(InputStream stream){
            tokenizer=new StreamTokenizer(new BufferedReader(new InputStreamReader(stream)));
            tokenizer.ordinaryChars(33,126);
            tokenizer.wordChars(33,126);
        }
        public String next() throws IOException {
            tokenizer.nextToken();
            return tokenizer.sval;
        }
        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }
        public boolean hasNext() throws IOException {
            int res=tokenizer.nextToken();
            tokenizer.pushBack();
            return res!=tokenizer.TT_EOF;
        }

        public double nextDouble() throws NumberFormatException, IOException {
            return Double.parseDouble(next());
        }

        public BigInteger nextBigInteger() throws IOException {
            return new BigInteger(next());
        }
    }
}
