package practice.extcontest;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class GCDMAX1 {

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

        private int n, k;
        private int[] a;

        private int gcd(int a, int b) {
            while (b != 0) {
                int t = b;
                b = a % b;
                a = t;
            }

            return a;
        }

        private boolean check(int m) {
            int[] p = new int[n],
                    s = new int[n];

            for (int i = 0; i < n; i += m) {
                int l = i,
                        r = Math.min(i + m, n) - 1;

                p[l] = a[l];

                for (int j = l + 1; j <= r; j++) {
                    p[j] = gcd(p[j - 1], a[j]);
                }

                s[r] = a[r];

                for (int j = r - 1; j >= l; j--) {
                    s[j] = gcd(a[j], s[j + 1]);
                }
            }

            for (int i = 0; i <= n - m; i++) {
                if (gcd(p[i + m - 1], s[i]) >= k) {
                    return true;
                }
            }

            return false;
        }

        public void solve() {
            n = in.nextInt();
            k = in.nextInt();

            a = new int[n];

            for (int i = 0; i < n; i++) {
                int ai = in.nextInt();

                a[i] = ai;
            }

            int l = 0,
                    r = n;

            while (l < r) {
                int m = (l + r + 1) / 2;

                if (check(m)) {
                    l = m;
                } else {
                    r = m - 1;
                }
            }

            out.println(l);
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
