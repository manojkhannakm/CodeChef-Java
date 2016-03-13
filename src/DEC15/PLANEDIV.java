package DEC15;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class PLANEDIV {

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
                int n = in.nextInt();

                HashSet<Line> lineSet = new HashSet<>();

                for (int j = 0; j < n; j++) {
                    int aj = in.nextInt(),
                            bj = in.nextInt(),
                            cj = in.nextInt();

                    lineSet.add(new Line(aj, bj, cj));
                }

                HashMap<Double, Integer> map = new HashMap<>();

                for (Line line : lineSet) {
                    double si = line.slope();
                    Integer mi = map.get(si);

                    if (mi == null) {
                        mi = 0;
                    }

                    map.put(si, mi + 1);
                }

                int m = 0;

                for (Integer mi : map.values()) {
                    if (mi > m) {
                        m = mi;
                    }
                }

                out.println(m);
            }
        }

        private class Line {

            private int a, b, c;

            public Line(int a, int b, int c) {
                int g = gcd(gcd(a, b), c);

                this.a = a / g;
                this.b = b / g;
                this.c = c / g;
            }

            @Override
            public int hashCode() {
                return toString().hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return obj instanceof Line
                        && ((Line) obj).a == a
                        && ((Line) obj).b == b
                        && ((Line) obj).c == c;
            }

            @Override
            public String toString() {
                return a + "x + " + b + "y + " + c + " = 0";
            }

            private int gcd(int a, int b) {
                while (b != 0) {
                    int t = b;
                    b = a % b;
                    a = t;
                }

                return a;
            }

            public double slope() {
                return b != 0 ? Math.round((double) -a / b * 1000000.0) / 1000000.0 : Double.POSITIVE_INFINITY;
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
