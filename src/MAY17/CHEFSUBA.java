package MAY17;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class CHEFSUBA {

    private static InputReader in;
    private static PrintWriter out;

    public static void main(String[] args) {
        List<String> list = Arrays.asList(args);

        if (!list.contains("-in")) {
            in = new InputReader(System.in);
        } else {
            try {
                in = new InputReader(new FileInputStream("in.txt"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        if (!list.contains("-out")) {
            out = new PrintWriter(System.out, true);
        } else {
            try {
                out = new PrintWriter(new FileOutputStream("out.txt"), true);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        long t = System.currentTimeMillis();

        new Solution().solve(false);

        if (list.contains("-debug")) {
            out.println("");
            out.println("Run time: " + ((System.currentTimeMillis() - t) / 1000.0f) + "s");
        }

        in.close();
        out.close();
    }

    private static class Solution {

        private static final int MOD = 1000000007;

        public Solution() {
        }

        private void solve(int t) {
            int n = in.nextInt(),
                    k = in.nextInt(),
                    p = in.nextInt();

            int[] a = in.nextIntArray(n);

            char[] s = in.nextLine().toCharArray();

            if (k > n) {
                k = n;
            }

            int[] c = new int[n];

            for (int i = 0; i < k; i++) {
                c[0] += a[i];
            }

            for (int i = 1; i < n; i++) {
                c[i] = c[i - 1] - a[i - 1] + a[(i + k - 1) % n];
            }

            SegmentTree segmentTree = new SegmentTree(c, n);

            int m = 0;

            for (int i = 0; i < p; i++) {
                if (s[i] == '?') {
                    int l = -m;

                    if (l < 0) {
                        l = n + l;
                    }

                    int r = n - k - m;

                    if (r < 0) {
                        r = n + r;
                    }

                    if (l <= r) {
                        out.println(segmentTree.get(l, r));
                    } else {
                        out.println(Math.max(segmentTree.get(l, n - 1), segmentTree.get(0, r)));
                    }
                } else if (s[i] == '!') {
                    m = (m + 1) % n;
                }
            }
        }

        public void solve(boolean f) {
            int t = f ? in.nextInt() : 1;

            for (int i = 1; i <= t; i++) {
                solve(i);
            }
        }

        public static class SegmentTree {

            public final int n;
            public final int[] s;

            public SegmentTree(int[] a, int n) {
                this.n = n;

                s = new int[2 * (int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2))) - 1];

                create(a, 0, n - 1, 0);
            }

            private int create(int[] a, int l, int r, int i) {
                if (l == r) {
                    s[i] = a[l];

                    return s[i];
                }

                int m = (l + r) / 2;

                s[i] = Math.max(create(a, l, m, 2 * i + 1),
                        create(a, m + 1, r, 2 * i + 2));

                return s[i];
            }

            private int get(int gl, int gr, int l, int r, int i) {
                if (l > gr || r < gl) {
                    return 0;
                }

                if (l >= gl && r <= gr) {
                    return s[i];
                }

                int m = (l + r) / 2;

                return Math.max(get(gl, gr, l, m, 2 * i + 1),
                        get(gl, gr, m + 1, r, 2 * i + 2));
            }

            public int get(int l, int r) {
                return get(l, r, 0, n - 1, 0);
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

        public int[] nextIntArray(int n) {
            int[] a = new int[n];

            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt();
            }

            return a;
        }

        public long[] nextLongArray(int n) {
            long[] a = new long[n];

            for (int i = 0; i < n; i++) {
                a[i] = in.nextLong();
            }

            return a;
        }

        public float[] nextFloatArray(int n) {
            float[] a = new float[n];

            for (int i = 0; i < n; i++) {
                a[i] = in.nextFloat();
            }

            return a;
        }

        public double[] nextDoubleArray(int n) {
            double[] a = new double[n];

            for (int i = 0; i < n; i++) {
                a[i] = in.nextDouble();
            }

            return a;
        }

        public String nextLine() {
            stringTokenizer = null;

            try {
                String line;

                do {
                    line = bufferedReader.readLine();
                } while (line != null && line.isEmpty());

                if (line == null) {
                    throw new NullPointerException();
                }

                return line;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void close() {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
