package practice.easy;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class DEVSTR {

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
                char[] s = in.nextLine().toCharArray();

                int r = 0;

                if (k == 1) {
                    int c0 = 0, c1 = 0;

                    for (int j = 0; j < n; j++) {
                        if (j % 2 == 0) {
                            if (s[j] != '0') {
                                c0++;
                            } else {
                                c1++;
                            }
                        } else {
                            if (s[j] != '1') {
                                c0++;
                            } else {
                                c1++;
                            }
                        }
                    }

                    if (c0 < c1) {
                        r = c0;

                        for (int j = 0; j < n; j++) {
                            s[j] = j % 2 == 0 ? '0' : '1';
                        }
                    } else {
                        r = c1;

                        for (int j = 0; j < n; j++) {
                            s[j] = j % 2 == 0 ? '1' : '0';
                        }
                    }
                } else {
                    int l = 0;

                    for (int j = 1; j <= n; j++) {
                        if (j == n || s[j] != s[j - 1]) {
                            int c = j - l;

                            if (c > k) {
                                for (int m = l + k; m < j - 1; m += k + 1) {
                                    r++;

                                    s[m] = s[l] == '0' ? '1' : '0';
                                }

                                if (c % (k + 1) == 0) {
                                    r++;

                                    s[j - 2] = s[l] == '0' ? '1' : '0';
                                }
                            }

                            l = j;
                        }
                    }
                }

                out.println(r);
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
