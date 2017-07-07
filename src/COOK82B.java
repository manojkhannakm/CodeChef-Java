import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

/**
 * @author Manoj Khanna
 */

class COOK82B {

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

            int[] a = in.nextIntArray(n);

            long x = 1;

            for (int i = 0; i < n; i++) {
                x = x * a[i] % MOD;
            }

            PollardRho.factor(BigInteger.valueOf(x));

            boolean b = true;
            int f = -1;

            for (Map.Entry<Integer, Integer> entry : PollardRho.m.entrySet()) {
                if (f == -1) {
                    f = entry.getValue();
                } else {
                    if (f != entry.getValue()) {
                        b = false;
                        break;
                    }
                }
            }

            if (b) {
                out.println("justdoit");
                return;
            }

            long y = 1;

            for (Map.Entry<Integer, Integer> entry : PollardRho.m.entrySet()) {
                Integer p = entry.getKey(),
                        q = entry.getValue();

                if (q < n + 1) {
                    for (int i = 0; i < (n + 1) - q; i++) {
                        y = y * p % MOD;
                    }
                } else if (q > n + 1) {
                    for (int i = 0; i < q - (n + 1); i++) {
                        y = y * p % MOD;
                    }
                }
            }

            out.println(y);
        }

        public void solve(boolean f) {
            int t = f ? in.nextInt() : 1;

            for (int i = 1; i <= t; i++) {
                solve(i);
            }
        }

        public static class PollardRho {

            private final static BigInteger ZERO = new BigInteger("0");
            private final static BigInteger ONE = new BigInteger("1");
            private final static BigInteger TWO = new BigInteger("2");
            private final static SecureRandom random = new SecureRandom();

            static HashMap<Integer, Integer> m = new HashMap<>();

            public static BigInteger rho(BigInteger N) {

                BigInteger divisor;
                BigInteger c = new BigInteger(N.bitLength(), random);
                BigInteger x = new BigInteger(N.bitLength(), random);
                BigInteger xx = x;

                if (N.mod(TWO).compareTo(ZERO) == 0) return TWO;

                do {
                    x = x.multiply(x).mod(N).add(c).mod(N);
                    xx = xx.multiply(xx).mod(N).add(c).mod(N);
                    xx = xx.multiply(xx).mod(N).add(c).mod(N);
                    divisor = x.subtract(xx).gcd(N);
                } while ((divisor.compareTo(ONE)) == 0);

                return divisor;
            }

            public static void factor(BigInteger N) {

                if (N.compareTo(ONE) == 0) return;

                if (N.isProbablePrime(20)) {
                    int n = N.intValue();

                    Integer i = m.get(n);

                    if (i == null) {
                        i = 0;
                    }

                    i++;

                    m.put(n, i);

                    return;
                }

                BigInteger divisor = rho(N);
                factor(divisor);
                factor(N.divide(divisor));
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
