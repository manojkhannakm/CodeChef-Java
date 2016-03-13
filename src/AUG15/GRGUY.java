package AUG15;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class GRGUY {

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

        private int gravity(String l1, String l2, int l) {
            int g = 0;

            for (int i = 1; i < l1.length(); i++) {
                char c1 = l1.charAt(i),
                        c2 = l2.charAt(i);

                if (c1 == '#' && c2 == '#') {
                    return -1;
                }

                if (l == 1) {
                    if (c1 == '#' && c2 == '.') {
                        l = 2;
                        g++;
                    }
                } else {
                    if (c2 == '#' && c1 == '.') {
                        l = 1;
                        g++;
                    }
                }
            }

            return g;
        }

        public void solve() {
            int t = in.nextInt();

            for (int i = 0; i < t; i++) {
                String l1 = in.nextLine(),
                        l2 = in.nextLine();

                char c1 = l1.charAt(0),
                        c2 = l2.charAt(0);
                int g;

                if (c1 == '.' && c2 == '#') {
                    g = gravity(l1, l2, 1);
                } else if (c1 == '#' && c2 == '.') {
                    g = gravity(l1, l2, 2);
                } else if (c1 == '.' && c2 == '.') {
                    g = Math.min(gravity(l1, l2, 1), gravity(l1, l2, 2));
                } else {
                    g = -1;
                }

                if (g != -1) {
                    out.println("Yes");
                    out.println(g);
                } else {
                    out.println("No");
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
