package practice.extcontest;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class ACM14AM4 {

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

        private long max(int[][] w, int m, int n, int x11, int y11, int x12, int y12, int x21, int y21, int x22, int y22, long s, long ms) {
            while (x11 >= 0 && y11 >= 0
                    && x12 >= 0 && y12 <= n - 1
                    && x21 <= m - 1 && y21 >= 0
                    && x22 <= m - 1 && y22 <= n - 1) {
                s += w[x11][y11] + w[x12][y12] + w[x21][y21] + w[x22][y22];

                if (s > ms) {
                    ms = s;
                }

                x11--;
                y11--;

                x12--;
                y12++;

                x21++;
                y21--;

                x22++;
                y22++;
            }

            return ms;
        }

        public void solve() {
            int t = in.nextInt();

            for (int i = 0; i < t; i++) {
                int m = in.nextInt(),
                        n = in.nextInt();

                int[][] w = new int[m][n];

                for (int j = 0; j < m; j++) {
                    for (int k = 0; k < n; k++) {
                        w[j][k] = in.nextInt();
                    }
                }

                long s, ms = Long.MIN_VALUE;

                for (int j = 0; j < m - 1; j++) {
                    for (int k = 0; k < n - 1; k++) {
                        int x11 = j, y11 = k,
                                x12 = j, y12 = k + 1,
                                x21 = j + 1, y21 = k,
                                x22 = j + 1, y22 = k + 1;

                        s = 0;
                        ms = max(w, m, n, x11, y11, x12, y12, x21, y21, x22, y22, s, ms);
                    }
                }

                for (int j = 1; j < m; j++) {
                    for (int k = 1; k < n; k++) {
                        int x11 = j - 1, y11 = k - 1,
                                x12 = j - 1, y12 = k + 1,
                                x21 = j + 1, y21 = k - 1,
                                x22 = j + 1, y22 = k + 1;

                        s = w[j][k];
                        ms = max(w, m, n, x11, y11, x12, y12, x21, y21, x22, y22, s, ms);
                    }
                }

                out.println(ms);
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
