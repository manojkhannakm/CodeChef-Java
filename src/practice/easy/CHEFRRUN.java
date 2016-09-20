package practice.easy;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class CHEFRRUN {

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

                int[] a = new int[n];

                for (int j = 0; j < n; j++) {
                    int aj = in.nextInt();

                    a[j] = aj;
                }

                for (int j = 0; j < n; j++) {
                    a[j] = (j + a[j] + 1) % n;
                }

                boolean[] v = new boolean[n],
                        c = new boolean[n];

                for (int j = 0; j < n; j++) {
                    if (v[j]) {
                        continue;
                    }

                    v[j] = true;

                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(j);

                    int aj = a[j];

                    while (!v[aj]) {
                        v[aj] = true;

                        list.add(aj);

                        aj = a[aj];
                    }

                    if (list.contains(aj)) {
                        c[aj] = true;

                        int ak = a[aj];

                        while (ak != aj) {
                            c[ak] = true;

                            ak = a[ak];
                        }
                    }
                }

                int s = 0;

                for (boolean cj : c) {
                    if (cj) {
                        s++;
                    }
                }

                out.println(s);
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
