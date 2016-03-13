package JAN16;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class CHEFTMA {

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
                        k = in.nextInt(),
                        m = in.nextInt();

                int[] a = new int[n];

                for (int j = 0; j < n; j++) {
                    int aj = in.nextInt();

                    a[j] = aj;
                }

                int[] b = new int[n];

                for (int j = 0; j < n; j++) {
                    int bj = in.nextInt();

                    b[j] = bj;
                }

                int[] c = new int[k];

                for (int j = 0; j < k; j++) {
                    int cj = in.nextInt();

                    c[j] = cj;
                }

                int[] d = new int[m];

                for (int j = 0; j < m; j++) {
                    int dj = in.nextInt();

                    d[j] = dj;
                }

                int[] e = new int[n];

                for (int j = 0; j < n; j++) {
                    e[j] = a[j] - b[j];
                }

                Arrays.sort(e);

                int[] f = new int[k + m];

                for (int j = 0; j < k; j++) {
                    f[j] = c[j];
                }

                for (int j = 0; j < m; j++) {
                    f[j + k] = d[j];
                }

                Arrays.sort(f);

                int l = f.length - 1;

                for (int j = n - 1; j >= 0; j--) {
                    while (l >= 0 && f[l] > e[j]) {
                        l--;
                    }

                    if (l < 0) {
                        break;
                    }

                    e[j] -= f[l];
                    l--;
                }

                long s = 0;

                for (int j = 0; j < n; j++) {
                    s += e[j];
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
