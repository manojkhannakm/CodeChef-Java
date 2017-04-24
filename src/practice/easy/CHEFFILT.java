package practice.easy;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class CHEFFILT {

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

        private static final int MOD = 1000000007;

        private void matrixGaussEliminate(int[][] a, int m, int n, int o) {
            for (int i = 0, j = 0; j < n - 1; j++) {
                int p = -1,
                        apj = 0;

                for (int k = i; k < m; k++) {
                    int akj = Math.abs(a[k][j]);

                    if (akj > apj) {
                        p = k;
                        apj = akj;
                    }
                }

                if (p == -1) {
                    continue;
                }

                if (p != i) {
                    for (int k = 0; k < n; k++) {
                        int t = a[i][k];
                        a[i][k] = a[p][k];
                        a[p][k] = t;
                    }
                }

                for (int k = i + 1; k < m; k++) {
                    int f = 0;

                    while ((a[k][j] + a[i][j] * f) % o != 0) {
                        f++;
                    }

                    for (int l = j; l < n; l++) {
                        a[k][l] = (a[k][l] + a[i][l] * f) % o;
                    }
                }

                i++;
            }
        }

        private int matrixRank(int[][] a, int m, int n, int o) {
            matrixGaussEliminate(a, m, n, o);

            int r = 0;

            for (int i = 0, j = 0; i < m; i++) {
                for (; j < n - 1; j++) {
                    if (a[i][j] != 0) {
                        r++;
                        break;
                    }
                }

                if (j == n - 1 && a[i][j] != 0) {
                    return -1;
                }
            }

            return r;
        }

        public void solve() {
            int t = in.nextInt();

            for (int i = 0; i < t; i++) {
                String s = in.nextLine();

                int n = in.nextInt();

                String[] f = new String[n];

                for (int j = 0; j < n; j++) {
                    f[j] = in.nextLine();
                }

                int m = 10;

                int[][] a = new int[m][n + 1];

                for (int j = 0; j < m; j++) {
                    for (int k = 0; k < n; k++) {
                        a[j][k] = f[k].charAt(j) == '-' ? 0 : 1;
                    }

                    a[j][n] = s.charAt(j) == 'b' ? 0 : 1;
                }

                int r = matrixRank(a, m, n + 1, 2),
                        c = 0;

                if (r != -1) {
                    c = 1;

                    for (int j = 0; j < n - r; j++) {
                        c = c * 2 % MOD;
                    }
                }

                out.println(c);
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
