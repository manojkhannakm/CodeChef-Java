package COOK67;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class PPCTS {

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

        public static final int XY_MAX = 1000000;

        private int left(int[] xc, int xx) {
            xx += XY_MAX;

            if (xx < 0) {
                return 0;
            } else if (xx >= xc.length) {
                return xc[xc.length - 1];
            } else {
                return xc[xx];
            }
        }

        private int right(int[] xc, int xx) {
            xx += XY_MAX;

            if (xx <= 0) {
                return xc[xc.length - 1];
            } else if (xx >= xc.length) {
                return 0;
            } else {
                return xc[xc.length - 1] - xc[xx - 1];
            }
        }

        private int down(int[] yc, int yy) {
            yy += XY_MAX;

            if (yy < 0) {
                return 0;
            } else if (yy >= yc.length) {
                return yc[yc.length - 1];
            } else {
                return yc[yy];
            }
        }

        private int up(int[] yc, int yy) {
            yy += XY_MAX;

            if (yy <= 0) {
                return yc[yc.length - 1];
            } else if (yy >= yc.length) {
                return 0;
            } else {
                return yc[yc.length - 1] - yc[yy - 1];
            }
        }

        public void solve() {
            int n = in.nextInt(),
                    m = in.nextInt();

            int[] x = new int[n],
                    y = new int[n];

            for (int i = 0; i < n; i++) {
                int xi = in.nextInt(),
                        yi = in.nextInt();

                x[i] = xi;
                y[i] = yi;
            }

            String s = in.nextLine();

            long d = 0;

            for (int i = 0; i < n; i++) {
                d += Math.abs(x[i]) + Math.abs(y[i]);
            }

            int[] xc = new int[2 * XY_MAX + 10],
                    yc = new int[2 * XY_MAX + 10];

            for (int i = 0; i < n; i++) {
                xc[x[i] + XY_MAX]++;
                yc[y[i] + XY_MAX]++;
            }

            for (int i = 1; i < xc.length; i++) {
                xc[i] += xc[i - 1];
                yc[i] += yc[i - 1];
            }

            int xx = 0,
                    yy = 0;

            for (int i = 0; i < m; i++) {
                switch (s.charAt(i)) {
                    case 'L':
                        d -= left(xc, xx - 1);
                        d += right(xc, xx);
                        xx--;
                        break;

                    case 'R':
                        d += left(xc, xx);
                        d -= right(xc, xx + 1);
                        xx++;
                        break;

                    case 'D':
                        d -= down(yc, yy - 1);
                        d += up(yc, yy);
                        yy--;
                        break;

                    case 'U':
                        d += down(yc, yy);
                        d -= up(yc, yy + 1);
                        yy++;
                        break;
                }

                out.println(d);
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
