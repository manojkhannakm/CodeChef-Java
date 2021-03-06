package AUG15;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class WOUT {

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
                int n = in.nextInt(),
                        h = in.nextInt();

                int[] a = new int[n + 10];

                for (int j = 0; j < n; j++) {
                    int li = in.nextInt(),
                            hi = in.nextInt();

                    a[li]++;
                    a[hi + 1]--;
                }

                for (int j = 1; j < n; j++) {
                    a[j] += a[j - 1];
                }

                long c = 0;

                for (int j = 0; j < h; j++) {
                    c += a[j];
                }

                long mc = c;

                for (int j = 1; j <= n - h; j++) {
                    c = c - a[j - 1] + a[j + h - 1];

                    if (c > mc) {
                        mc = c;
                    }

                    if (c == (long) n * h) {
                        break;
                    }
                }

                out.println((long) n * h - mc);
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
