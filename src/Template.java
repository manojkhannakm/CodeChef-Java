import java.io.*;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class Template {

    private static InputReader in;
    private static PrintWriter out;

    public static void main(String[] args) {
        String input = null, output = null;
        boolean debug = false;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-i":
                    input = args[i + 1];
                    break;
                case "-o":
                    output = args[i + 1];
                    break;
                case "-d":
                    debug = true;
                    break;
            }
        }

        if (input == null) {
            in = new InputReader(System.in);
        } else {
            try {
                in = new InputReader(new FileInputStream(input));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        if (output == null) {
            out = new PrintWriter(System.out, true);
        } else {
            try {
                out = new PrintWriter(new FileOutputStream(output), true);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        long time = System.currentTimeMillis();

        new Solution().solve();

        if (debug) {
            out.println("");
            out.println("Run time: " + ((System.currentTimeMillis() - time) / 1000.0f) + "s");
        }

        in.close();
        out.close();
    }

    private static class Solution {

        private static final boolean SINGLE_TEST = true;

        private static final int MOD = 1000000007;

        public Solution() {

        }

        private void solve(int t) {

        }

        public void solve() {
            int n = SINGLE_TEST ? 1 : in.nextInt();

            for (int i = 1; i <= n; i++) {
                solve(i);
            }
        }

    }

    private static class InputReader {

        private final BufferedReader bufferedReader;

        private StringTokenizer stringTokenizer;

        public InputReader(InputStream inputStream) {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        }

        private String readLine() {
            String line;

            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (line == null) {
                throw new NullPointerException();
            }

            return line;
        }

        public String next() {
            while (stringTokenizer == null || !stringTokenizer.hasMoreTokens()) {
                stringTokenizer = new StringTokenizer(readLine());
            }

            return stringTokenizer.nextToken();
        }

        public String nextLine() {
            stringTokenizer = null;

            return readLine();
        }

        public char nextChar() {
            return next().charAt(0);
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public float nextFloat() {
            return Float.parseFloat(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public int[] nextIntArray(int n) {
            int[] a = new int[n];

            for (int i = 0; i < n; i++) {
                a[i] = nextInt();
            }

            return a;
        }

        public long[] nextLongArray(int n) {
            long[] a = new long[n];

            for (int i = 0; i < n; i++) {
                a[i] = nextLong();
            }

            return a;
        }

        public float[] nextFloatArray(int n) {
            float[] a = new float[n];

            for (int i = 0; i < n; i++) {
                a[i] = nextFloat();
            }

            return a;
        }

        public double[] nextDoubleArray(int n) {
            double[] a = new double[n];

            for (int i = 0; i < n; i++) {
                a[i] = nextDouble();
            }

            return a;
        }

        public void close() {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
