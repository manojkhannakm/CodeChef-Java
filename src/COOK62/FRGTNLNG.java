package COOK62;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class FRGTNLNG {

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

                String[] w = new String[n];

                for (int j = 0; j < n; j++) {
                    String wj = in.next();

                    w[j] = wj;
                }

                boolean[] b = new boolean[n];

                for (int j = 0; j < k; j++) {
                    int l = in.nextInt();

                    String[] p = new String[l];

                    for (int m = 0; m < l; m++) {
                        String pm = in.next();

                        p[m] = pm;
                    }

                    for (int m = 0; m < n; m++) {
                        String wm = w[m];

                        for (int o = 0; o < l; o++) {
                            String po = p[o];

                            if (po.equals(wm)) {
                                b[m] = true;
                                break;
                            }
                        }
                    }
                }

                for (int j = 0; j < n; j++) {
                    if (j > 0) {
                        out.print(" ");
                    }

                    out.print(b[j] ? "YES" : "NO");
                }

                out.println("");
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
