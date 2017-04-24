package JAN17;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class RESERVOI {

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

                char[][] a = new char[n][];

                for (int j = 0; j < n; j++) {
                    a[j] = in.nextLine().toCharArray();
                }

                boolean f = true;

                for (int j = 0; j < n && f; j++) {
                    for (int k = 0; k < m && f; k++) {
                        if (a[j][k] == 'W') {
                            if (j == n - 1 || k == 0 || k == m - 1
                                    || a[j][k - 1] == 'A'
                                    || a[j][k + 1] == 'A'
                                    || a[j + 1][k] == 'A') {
                                f = false;
                            }
                        } else if (a[j][k] == 'B') {
                            if (j != n - 1
                                    && (a[j + 1][k] == 'A'
                                    || a[j + 1][k] == 'W')) {
                                f = false;
                            }
                        }
                    }
                }

                out.println(f ? "yes" : "no");
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
