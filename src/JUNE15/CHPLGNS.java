package JUNE15;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class CHPLGNS {

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

                Node[] nodes = new Node[n];
                for (int j = 0; j < n; j++) {
                    int m = in.nextInt();

                    int maxX = 0;
                    for (int k = 0; k < m; k++) {
                        int x = in.nextInt(),
                                y = in.nextInt();

                        x = Math.abs(x);
                        if (maxX < x) {
                            maxX = x;
                        }
                    }

                    nodes[j] = new Node(j, maxX);
                }

                Arrays.sort(nodes, new Comparator<Node>() {

                    @Override
                    public int compare(Node o1, Node o2) {
                        return Integer.compare(o1.x, o2.x);
                    }

                });

                int[] counts = new int[n];
                for (int j = 0; j < n; j++) {
                    counts[nodes[j].index] = j;
                }

                for (int j = 0; j < n; j++) {
                    out.print(counts[j] + " ");
                }
            }
        }

        private class Node {

            private int index, x;

            public Node(int index, int x) {
                this.index = index;
                this.x = x;
            }

            @Override
            public String toString() {
                return index + " -> " + x;
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
