package SEPT15;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class BANROB {

    private static InputReader in;
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {
        if (Arrays.asList(args).contains("-local")) {
            try {
                in = new InputReader(new FileInputStream("in.txt"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            in = new InputReader(System.in);
        }

        new Solution().solve();

        out.close();
    }

    private static class Solution {

        public static final double BILLION = 1000000000.0;

        public void solve() {
            int t = in.nextInt();

            for (int i = 0; i < t; i++) {
                int m = in.nextInt();
                double p = in.nextDouble();

                double a, b;

                if (p == 1.0) {
                    if (m % 2 == 0) {
                        a = 0.0;
                        b = BILLION;
                    } else {
                        a = BILLION;
                        b = 0.0;
                    }
                } else if (p == 0.0 || m == 1) {
                    a = BILLION;
                    b = 0.0;
                } else if (m == 2) {
                    a = BILLION - BILLION * p;
                    b = BILLION * p;
                } else {
                    m -= 1;

                    if (m % 2 == 0) {
                        b = Math.pow(p, m + 1) - p
                                - (Math.pow(p, m + 2) - Math.pow(p, 2));
                    } else {
                        b = Math.pow(p, m + 2) - p
                                - (Math.pow(p, m + 1) - Math.pow(p, 2));
                    }

                    b /= Math.pow(p, 2) - 1.0;

                    a = 1.0 - b;

                    a *= BILLION;
                    b *= BILLION;
                }

                out.printf("%f %f\n", a, b);
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

        public String nextLine() {
            if (stringTokenizer != null && stringTokenizer.hasMoreTokens()) {
                return stringTokenizer.nextToken("");
            }

            try {
                return bufferedReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
