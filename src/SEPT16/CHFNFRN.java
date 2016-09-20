package SEPT16;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class CHFNFRN {

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
                        m = in.nextInt();

                int[] a = new int[m],
                        b = new int[m];

                for (int j = 0; j < m; j++) {
                    int aj = in.nextInt(),
                            bj = in.nextInt();

                    a[j] = aj;
                    b[j] = bj;
                }

                Graph graph = new Graph(n, m, a, b);
                graph.complement();

                out.println(graph.bipartite() ? "YES" : "NO");
            }
        }

        private class Graph {

            private int n;

            private boolean[][] g;
            private boolean[] e;
            private int[] c;

            public Graph(int n, int m, int[] a, int[] b) {
                this.n = n;

                g = new boolean[n][n];

                for (int i = 0; i < m; i++) {
                    int ai = a[i],
                            bi = b[i];

                    if (ai != bi) {
                        g[ai - 1][bi - 1] = g[bi - 1][ai - 1] = true;
                    }
                }

                e = new boolean[n];
                c = new int[n];
            }

            public void complement() {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        g[i][j] = !g[i][j];
                    }
                }
            }

            private boolean dfs(int i) {
                int ci = c[i];

                for (int j = 0; j < n; j++) {
                    if (i != j && g[i][j]) {
                        int cj = c[j];

                        if (cj == 0) {
                            c[j] = ci == 1 ? 2 : 1;
                        } else if (cj == ci) {
                            return false;
                        }
                    }
                }

                e[i] = true;

                for (int j = 0; j < n; j++) {
                    if (i != j && g[i][j] && !e[j] && !dfs(j)) {
                        return false;
                    }
                }

                return true;
            }

            public boolean bipartite() {
                for (int i = 0; i < n; i++) {
                    if (!e[i]) {
                        c[i] = 1;

                        if (!dfs(i)) {
                            return false;
                        }
                    }
                }

                return true;
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
