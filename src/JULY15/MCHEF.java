package JULY15;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class MCHEF {

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
                    a[j] = in.nextInt();
                }

                Interval[] intervals = new Interval[m];

                for (int j = 0; j < m; j++) {
                    int l = in.nextInt(),
                            r = in.nextInt(),
                            c = in.nextInt();

                    intervals[j] = new Interval(l - 1, r - 1, c);
                }

                Arrays.sort(intervals, new Comparator<Interval>() {

                    @Override
                    public int compare(Interval o1, Interval o2) {
                        int i = Integer.compare(o1.c, o2.c);

                        if (i == 0) {
                            i = Integer.compare(o1.l, o2.l);

                            if (i == 0) {
                                i = Integer.compare(o2.r, o1.r);
                            }
                        }

                        return i;
                    }

                });

                int[] x = new int[n],
                        y = new int[n];
                int z = 0;

                for (int j = 0; j < n; j++) {
                    int aj = a[j];

                    if (aj < 0) {
                        for (Interval interval : intervals) {
                            if (interval.l <= j && interval.r >= j) {
                                x[z] = -aj;
                                y[z] = interval.c;

                                z++;
                                break;
                            }
                        }
                    }
                }

                long[][] dp = new long[z + 1][k + 1];

                for (int j = 0; j < z + 1; j++) {
                    dp[j][0] = 0;
                }

                for (int j = 0; j < k + 1; j++) {
                    dp[0][j] = 0;
                }

                for (int p = 1; p < z + 1; p++) {
                    for (int q = 1; q < k + 1; q++) {
                        if (y[p - 1] > q) {
                            dp[p][q] = dp[p - 1][q];
                        } else {
                            dp[p][q] = Math.max(x[p - 1] + dp[p - 1][q - y[p - 1]], dp[p - 1][q]);
                        }
                    }
                }

                long s = dp[z][k];

                for (int ai : a) {
                    s += ai;
                }

                out.println(s);
            }
        }

        private class Interval {

            private int l, r, c;

            public Interval(int l, int r, int c) {
                this.l = l;
                this.r = r;
                this.c = c;
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
