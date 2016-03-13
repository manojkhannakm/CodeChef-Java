package practice.easy;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class FRNDMTNG {

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

        public void solve() {
            int t = in.nextInt();

            for (int i = 0; i < t; i++) {
                double T1 = in.nextDouble(),
                        T2 = in.nextDouble(),
                        t1 = in.nextDouble(),
                        t2 = in.nextDouble();

                if (T1 < T2) {
                    double tmp = T1;
                    T1 = T2;
                    T2 = tmp;

                    tmp = t1;
                    t1 = t2;
                    t2 = tmp;
                }

                double a = T1 * T2;

                double at1 = 0.0;
                if (t1 < T2) {
                    double b1 = T2 - t1;
                    at1 = 0.5 * b1 * b1;
                }

                double at2 = 0.0;
                if (t2 < T1) {
                    double b2 = Math.min(T1 - t2, T2);
                    at2 = 0.5 * b2 * b2;

                    if (T1 - t2 > T2) {
                        at2 += T2 * (T1 - t2 - T2);
                    }
                }

                out.format("%.6f\n", (a - at1 - at2) / a);
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
