import java.io.*;
import java.math.BigInteger;
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

        private void matrixGuassElimination(int[][] a, int[] b) {
            int r = a.length,
                    c = a[0].length,
                    p = -1;

            for (int i = 0; i < c; i++) {
                int q = -1;

                for (int j = p + 1; j < r; j++) {
                    if (a[j][i] == 1) {
                        q = j;
                        break;
                    }
                }

                if (q == -1) {
                    continue;
                }

                p++;

                if (q != p) {
                    int[] t = a[p];
                    a[p] = a[q];
                    a[q] = t;
                }

                for (int j = p + 1; j < r; j++) {
                    int f = a[j][i] % 2 == 0 ? 0 : 1;

                    for (int k = i; k < c; k++) {
                        a[j][k] = (a[j][k] + f * a[p][k]) % 2;
                    }

                    b[j] = (b[j] + f * b[p]) % 2;
                }
            }
        }

        private int matrixRank(int[][] a, int[] b) {
            matrixGuassElimination(a, b);

            int r = a.length,
                    c = a[0].length,
                    ar = 0,
                    abr = 0;

            for (int i = 0; i < r; i++) {
                boolean f = true;

                for (int j = 0; j < c; j++) {
                    if (a[i][j] != 0) {
                        ar++;
                        abr++;
                        f = false;
                        break;
                    }
                }

                if (b[i] != 0 && f) {
                    abr++;
                }
            }

            return ar == abr ? ar : -1;
        }

        public void solve() {
            int t = in.nextInt();

            for (int i = 0; i < t; i++) {
                String s = in.nextLine();
                int n = in.nextInt();

                String[] f = new String[n];

                for (int j = 0; j < n; j++) {
                    String fj = in.nextLine();

                    f[j] = fj;
                }

                int r = s.length(),
                        c = n;
                int[][] a = new int[r][c];

                for (int j = 0; j < r; j++) {
                    for (int k = 0; k < c; k++) {
                        a[j][k] = f[k].charAt(j) == '-' ? 0 : 1;
                    }
                }

                int[] b = new int[r];

                for (int j = 0; j < r; j++) {
                    b[j] = s.charAt(j) == 'b' ? 0 : 1;
                }

                int ar = matrixRank(a, b);
                BigInteger x = BigInteger.ZERO;

                if (ar != -1) {
                    x = BigInteger.valueOf(2L).modPow(BigInteger.valueOf(n - ar), BigInteger.valueOf(1000000007));
                }

                out.println(x);
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
