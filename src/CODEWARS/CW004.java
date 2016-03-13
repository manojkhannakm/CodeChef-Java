package CODEWARS;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class CW004 {

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

                long[] s = new long[n],
                        p = new long[n];

                for (int j = 0; j < n; j++) {
                    long sj = in.nextLong(),
                            pj = in.nextLong();

                    s[j] = sj;
                    p[j] = pj;
                }

                int c = in.nextInt();

                for (int j = 0; j < c; j++) {
                    long tj = in.nextLong();
                    int kj = in.nextInt();

                    Node[] nodes = new Node[n];

                    for (int k = 0; k < n; k++) {
                        nodes[k] = new Node(p[k] - s[k] * tj, k + 1);
                    }

                    Arrays.sort(nodes);

                    out.println(nodes[kj - 1].i);
                }
            }
        }

        private class Node implements Comparable<Node> {

            private long p;
            private int i;

            public Node(long p, int i) {
                this.p = p;
                this.i = i;
            }

            @Override
            public String toString() {
                return "[" + p + ", " + i + "]";
            }

            @Override
            public int compareTo(Node o) {
                if (p != o.p) {
                    return Long.compare(p, o.p);
                }

                return Integer.compare(i, o.i);
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
