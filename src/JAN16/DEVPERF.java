package JAN16;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class DEVPERF {

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
                        m = in.nextInt();

                char[][] a = new char[n][m];

                for (int j = 0; j < n; j++) {
                    char[] aj = in.nextLine().toCharArray();

                    a[j] = aj;
                }

                int x1 = Integer.MAX_VALUE,
                        y1 = Integer.MAX_VALUE,
                        x2 = 0,
                        y2 = 0;

                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < m; k++) {
                        if (a[j][k] == '*') {
                            if (k < x1) {
                                x1 = k;
                            }

                            if (k > x2) {
                                x2 = k;
                            }

                            if (j < y1) {
                                y1 = j;
                            }

                            if (j > y2) {
                                y2 = j;
                            }
                        }
                    }
                }

                int s = 0;

                if (x2 - x1 >= 0 && y2 - y1 >= 0) {
                    int xm = (x1 + x2) / 2,
                            ym = (y1 + y2) / 2;

                    s += 1 + Math.max(Math.max(xm - x1, x2 - xm), Math.max(ym - y1, y2 - ym));
                }

                out.println(s);
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
