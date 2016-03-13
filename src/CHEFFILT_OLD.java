import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class CHEFFILT_OLD {

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

        private int matrixRank(int[][] a, int[] b, int p) {
            int m = 10,
                    n = a[0].length,
                    ans = 0;

            boolean used[] = new boolean[m];

            // Using Gauss-Jordan in [ A : b ] to make it in row-echellon form.
            // So that we can find the rank. We also need to make sure that the
            // rank of [ A ] is equal to the rank of [A : b], else there are
            // no solutions
            for (int j = 0; j < n; j++) {
                int i = 0;
                while ((i < m) && (used[i] || (a[i][j] == 0))) {
                    i++;
                }
                if (i == m) continue;
                ans++;
                used[i] = true;
                for (int k = 0; k < m; k++)
                    if (!used[k]) {
                        // Find a value of coef equal to (-a[k][j] / a[i][j]);
                        // we can just try 0,1,..,p-1 until we find one.
                        int coef = 0;
                        while ((a[i][j] * coef + a[k][j]) % p != 0) {
                            coef++;
                        }
                        for (int l = 0; l < n; l++) {
                            a[k][l] = (a[k][l] + a[i][l] * coef) % p;
                        }
                        b[k] = (b[k] + b[i] * coef) % p;
                    }
            }

            // If rank of [A] is not equal to the rank of [A : b]
            for (int i = 0; i < m; i++) {
                if (!used[i] && b[i] != 0) {
                    return -1;
                }
            }

            return ans;
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

                int m = 10;
                int[][] a = new int[m][n];
                int[] b = new int[m];

                for (int j = 0; j < m; j++) {
                    for (int k = 0; k < n; k++) {
                        a[j][k] = f[k].charAt(j) == '-' ? 0 : 1;
                    }
                }

                for (int j = 0; j < m; j++) {
                    b[j] = s.charAt(j) == 'b' ? 0 : 1;
                }

                BigInteger c = BigInteger.ZERO;
                int r = matrixRank(a, b, 2);

                int[][] x = new int[m][n + 1];

                for (int j = 0; j < m; j++) {
                    for (int k = 0; k < n; k++) {
                        x[j][k] = a[j][k];
                    }

                    x[j][n] = b[j];
                }

                out.println(Arrays.deepToString(x));

                if (r != -1) {
                    c = BigInteger.valueOf(2).modPow(BigInteger.valueOf(n - r), BigInteger.valueOf(1000000007));
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
