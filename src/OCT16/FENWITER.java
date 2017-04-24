package OCT16;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class FENWITER {

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

        private int bitZeroIndex(char[] s) {
            for (int i = s.length - 1; i >= 0; i--) {
                if (s[i] == '0') {
                    return i;
                }
            }

            return -1;
        }

        private int bitOneCount(char[] s, int l, int r) {
            int c = 0;

            for (int i = l; i <= r; i++) {
                if (s[i] == '1') {
                    c++;
                }
            }

            return c;
        }

        public void solve() {
            int t = in.nextInt();

            for (int i = 0; i < t; i++) {
                char[] l1 = in.next().toCharArray(),
                        l2 = in.next().toCharArray(),
                        l3 = in.next().toCharArray();
                int n = in.nextInt();

                int x = bitZeroIndex(l3), c;

                if (x != -1) {
                    c = bitOneCount(l1, 0, l1.length - 1)
                            + n * bitOneCount(l2, 0, l2.length - 1)
                            + bitOneCount(l3, 0, x - 1) + 1;
                } else {
                    x = bitZeroIndex(l2);

                    if (x != -1) {
                        c = bitOneCount(l1, 0, l1.length - 1)
                                + (n - 1) * bitOneCount(l2, 0, l2.length - 1)
                                + bitOneCount(l2, 0, x - 1) + 1;
                    } else {
                        x = bitZeroIndex(l1);

                        if (x != -1) {
                            c = bitOneCount(l1, 0, x - 1) + 1;
                        } else {
                            c = 1;
                        }
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
