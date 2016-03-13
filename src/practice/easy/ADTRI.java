package practice.easy;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class ADTRI {

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

        public static final int MAX = 5000000;
        public static final int[][] A = new int[][]{
                new int[]{1, -2, 2},
                new int[]{2, -1, 2},
                new int[]{2, -2, 3}
        }, B = new int[][]{
                new int[]{1, 2, 2},
                new int[]{2, 1, 2},
                new int[]{2, 2, 3}
        }, C = new int[][]{
                new int[]{-1, 2, 2},
                new int[]{-2, 1, 2},
                new int[]{-2, 2, 3}
        }, P = new int[][]{
                new int[]{3},
                new int[]{4},
                new int[]{5}
        };

        private int[][] matrixMultiply(int[][] m1, int[][] m2) {
            int r1 = m1.length,
                    c1 = m1[0].length,
                    r2 = m2.length,
                    c2 = m2[0].length;
            int[][] m3 = new int[r1][c2];

            for (int i = 0; i < r1; i++) {
                for (int j = 0; j < c2; j++) {
                    int s = 0;

                    for (int k = 0; k < c1; k++) {
                        s += m1[i][k] * m2[k][j];
                    }

                    m3[i][j] = s;
                }
            }

            return m3;
        }

        private void pythagoreanTriples(int[][] p, ArrayList<Integer> list) {
            int p20 = p[2][0];

            if (p20 < MAX) {
                list.add(p20);

                pythagoreanTriples(matrixMultiply(A, p), list);
                pythagoreanTriples(matrixMultiply(B, p), list);
                pythagoreanTriples(matrixMultiply(C, p), list);
            }
        }

        public void solve() {
            ArrayList<Integer> list = new ArrayList<>();

            pythagoreanTriples(P, list);

            boolean[] p = new boolean[MAX + 10];

            for (Integer c : list) {
                for (int j = 1; j * c <= MAX; j++) {
                    p[j * c] = true;
                }
            }

            int t = in.nextInt();

            for (int i = 0; i < t; i++) {
                int n = in.nextInt();

                out.println(p[n] ? "YES" : "NO");
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
