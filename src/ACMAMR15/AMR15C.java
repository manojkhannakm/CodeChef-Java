package ACMAMR15;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class AMR15C {

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
                        k = in.nextInt();

                int[] a = new int[n];

                if (k == 1) {
                    if (n % 2 == 0) {
                        for (int j = 0; j < n; j += 2) {
                            a[j] = j + 2;
                            a[j + 1] = j + 1;
                        }
                    } else {
                        for (int j = 0; j < n; j += 2) {
                            a[j] = j + 2;

                            if (j + 1 < n) {
                                a[j + 1] = j + 1;
                            }
                        }

                        int m = a[n - 1] - 1;
                        a[n - 1] = a[n - 2];
                        a[n - 2] = m;
                    }
                } else if (k <= n / 2) {
                    for (int j = 0; j < n - k; j++) {
                        a[j] = j + k + 1;
                    }

                    for (int j = n - k; j < n; j++) {
                        a[j] = j - n + k + 1;
                    }
                } else {
                    a = new int[]{-1};
                }

                for (int j = 0; j < a.length; j++) {
                    out.print(a[j]);
                    out.print(j < a.length - 1 ? " " : "\n");
                }
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
