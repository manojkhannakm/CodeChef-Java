package OCT16;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class CHEFTWOS {

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

        public void solve() {
            int t = in.nextInt();

            for (int i = 0; i < t; i++) {
                char[] a = in.nextLine().toCharArray(),
                        b = in.nextLine().toCharArray();

                int n = a.length,
                        c = 0;
                boolean f = true;

                for (int j = 0; j < n - 1; ) {
                    if (a[j] == b[j]) {
                        if (a[j] == '1') {
                            j++;
                        } else {
                            if (j + 3 >= n
                                    || a[j + 1] != '2' || b[j + 1] != '2'
                                    || a[j + 2] != '1' || b[j + 2] != '1') {
                                f = false;
                                break;
                            }

                            j += 3;
                        }
                    } else {
                        if (j + 1 >= n) {
                            f = false;
                            break;
                        }

                        if (a[j + 1] == b[j + 1]) {
                            if (j + 4 >= n
                                    || a[j + 1] != '2'
                                    || a[j + 2] == b[j + 2]
                                    || a[j + 3] != '1' && b[j + 3] != '1') {
                                f = false;
                                break;
                            }

                            if (a[j + 3] == b[j + 3]) {
                                c++;
                            } else {
                                c += 2;
                            }

                            j += 3;
                        } else {
                            if (j + 2 >= n) {
                                f = false;
                                break;
                            }

                            if (a[j + 2] == b[j + 2]) {
                                if (j + 3 >= n
                                        || a[j + 2] != '1') {
                                    f = false;
                                    break;
                                }

                                c++;

                                j += 2;
                            } else {
                                if (j + 3 >= n) {
                                    f = false;
                                    break;
                                }

                                c += 2;

                                j += 2;
                            }
                        }
                    }
                }

                int s = 0;

                if (f) {
                    s = 1;

                    for (int j = 0; j < n - c; j++) {
                        s = s * 2 % MOD;
                    }
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
