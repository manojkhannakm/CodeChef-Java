import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class CHEFCODE {

    private static InputReader in;
    private static PrintWriter out;

    public static void main(String[] args) {
        List<String> list = Arrays.asList(args);

        if (!list.contains("-in")) {
            in = new InputReader(System.in);
        } else {
            try {
                in = new InputReader(new FileInputStream("in.txt"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        if (!list.contains("-out")) {
            out = new PrintWriter(System.out, true);
        } else {
            try {
                out = new PrintWriter(new FileOutputStream("out.txt"), true);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        long t = System.currentTimeMillis();

        new Solution().solve(false);

        if (list.contains("-debug")) {
            out.println("");
            out.println("Run time: " + ((System.currentTimeMillis() - t) / 1000.0f) + "s");
        }

        in.close();
        out.close();
    }

    private static class Solution {

        private static final int MOD = 1000000007;

        public Solution() {
        }

        private void solve(int t) {
            int n = in.nextInt();
            long k = in.nextLong();

            long[] a = in.nextLongArray(n);

            BigInteger m = BigInteger.ZERO;

            for (int i = 1; i < 1 << n; i++) {
                BigInteger p = BigInteger.ONE;

                boolean b = true;

                for (int j = 0; j < n; j++) {
                    if ((i >> j & 1) == 1) {
                        p = p.multiply(BigInteger.valueOf(a[j]));

                        if (p.compareTo(BigInteger.valueOf(k)) >= 0) {
                            b = false;
                            break;
                        }
                    }
                }

                if (b) {
                    m = m.add(BigInteger.ONE);
                }
            }

            out.println(m);
        }

        public void solve(boolean f) {
            int t = f ? in.nextInt() : 1;

            for (int i = 1; i <= t; i++) {
                solve(i);
            }
        }

    }

    @SuppressWarnings("UnusedDeclaration")
    private static class InputReader {

        private BufferedReader bufferedReader;
        private StringTokenizer stringTokenizer;

        public InputReader(InputStream inputStream) {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        }

        public String next() {
            while (stringTokenizer == null || !stringTokenizer.hasMoreTokens()) {
                try {
                    stringTokenizer = new StringTokenizer(bufferedReader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            return stringTokenizer.nextToken();
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
                a[i] = in.nextInt();
            }

            return a;
        }

        public long[] nextLongArray(int n) {
            long[] a = new long[n];

            for (int i = 0; i < n; i++) {
                a[i] = in.nextLong();
            }

            return a;
        }

        public float[] nextFloatArray(int n) {
            float[] a = new float[n];

            for (int i = 0; i < n; i++) {
                a[i] = in.nextFloat();
            }

            return a;
        }

        public double[] nextDoubleArray(int n) {
            double[] a = new double[n];

            for (int i = 0; i < n; i++) {
                a[i] = in.nextDouble();
            }

            return a;
        }

        public String nextLine() {
            stringTokenizer = null;

            try {
                String line;

                do {
                    line = bufferedReader.readLine();
                } while (line != null && line.isEmpty());

                if (line == null) {
                    throw new NullPointerException();
                }

                return line;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
