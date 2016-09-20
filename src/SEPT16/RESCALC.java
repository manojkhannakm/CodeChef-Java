package SEPT16;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class RESCALC {

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

                int[] c = new int[n];
                int[][] d = new int[n][];

                for (int j = 0; j < n; j++) {
                    int cj = in.nextInt();

                    c[j] = cj;
                    d[j] = new int[cj];

                    for (int k = 0; k < cj; k++) {
                        int djk = in.nextInt();

                        d[j][k] = djk;
                    }
                }

                int[] p = new int[n];

                for (int j = 0; j < n; j++) {
                    int pj = c[j];
                    int[] q = new int[6];

                    for (int k = 0; k < c[j]; k++) {
                        q[d[j][k] - 1]++;
                    }

                    Arrays.sort(q);

                    for (int k = 0; k < 3; k++) {
                        int qk = q[k];

                        for (int l = k; l < 6; l++) {
                            q[l] -= qk;
                        }

                        if (k == 0) {
                            pj += qk * 4;
                        } else if (k == 1) {
                            pj += qk * 2;
                        } else if (k == 2) {
                            pj += qk;
                        }
                    }

                    p[j] = pj;
                }

                Player[] players = new Player[n];

                for (int j = 0; j < n; j++) {
                    players[j] = new Player(j, p[j]);
                }

                Arrays.sort(players, (o1, o2) -> Integer.compare(o1.p, o2.p));

                if (n >= 2 && players[n - 2].p == players[n - 1].p) {
                    out.println("tie");
                } else {
                    int j = players[n - 1].i;

                    if (j == 0) {
                        out.println("chef");
                    } else {
                        out.println(j + 1);
                    }
                }
            }
        }

        private class Player {

            private final int i, p;

            public Player(int i, int p) {
                this.i = i;
                this.p = p;
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
