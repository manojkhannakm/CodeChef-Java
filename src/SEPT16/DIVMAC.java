package SEPT16;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class DIVMAC {

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

        private static final int A_MAX = 1000010;

        private int[] p = new int[A_MAX];

        public void solve() {
            p[1] = 1;

            for (int i = 2; i < A_MAX; i++) {
                if (p[i] == 0) {
                    for (int j = i; j < A_MAX; j += i) {
                        if (p[j] == 0) {
                            p[j] = i;
                        }
                    }
                }
            }

            int t = in.nextInt();

            for (int i = 0; i < t; i++) {
                int n = in.nextInt(),
                        m = in.nextInt();

                int[] a = new int[n];

                for (int j = 0; j < n; j++) {
                    int aj = in.nextInt();

                    a[j] = aj;
                }

                int[] q = new int[m],
                        l = new int[m],
                        r = new int[m];

                for (int j = 0; j < m; j++) {
                    int qj = in.nextInt(),
                            lj = in.nextInt() - 1,
                            rj = in.nextInt() - 1;

                    q[j] = qj;
                    l[j] = lj;
                    r[j] = rj;
                }

                SegmentTree segmentTree = new SegmentTree(n, a);
                ArrayList<Integer> resultList = new ArrayList<>();

                for (int j = 0; j < m; j++) {
                    switch (q[j]) {
                        case 0:
                            segmentTree.update(l[j], r[j]);
                            break;

                        case 1:
                            resultList.add(segmentTree.get(l[j], r[j]));
                            break;
                    }
                }

                for (int j = 0; j < resultList.size(); j++) {
                    if (j > 0) {
                        out.print(" ");
                    }

                    out.print(resultList.get(j));
                }

                out.println("");
            }
        }

        private class SegmentTree {

            private int n;

            private int[] s;

            public SegmentTree(int n, int[] a) {
                this.n = n;

                s = new int[2 * (int) Math.pow(2, Math.ceil(Math.log(n) / Math.log(2))) - 1];

                create(a, 0, 0, n - 1);
            }

            private int create(int[] a, int i, int l, int r) {
                if (l == r) {
                    s[i] = a[l];

                    return p[s[i]];
                }

                int m = l + (r - l) / 2;

                s[i] = Math.max(create(a, 2 * i + 1, l, m),
                        create(a, 2 * i + 2, m + 1, r));

                return s[i];
            }

            private int update(int ul, int ur, int i, int l, int r) {
                if (ur < l || r < ul || s[i] == 1) {
                    if (l == r) {
                        return p[s[i]];
                    }

                    return s[i];
                }

                if (l == r) {
                    s[i] /= p[s[i]];

                    return p[s[i]];
                }

                int m = l + (r - l) / 2;

                s[i] = Math.max(update(ul, ur, 2 * i + 1, l, m),
                        update(ul, ur, 2 * i + 2, m + 1, r));

                return s[i];
            }

            private int value(int vl, int vr, int i, int l, int r) {
                if (vr < l || r < vl) {
                    return 1;
                }

                if (vl <= l && r <= vr) {
                    if (l == r) {
                        return p[s[i]];
                    }

                    return s[i];
                }

                int m = l + (r - l) / 2;

                return Math.max(value(vl, vr, 2 * i + 1, l, m),
                        value(vl, vr, 2 * i + 2, m + 1, r));
            }

            public void update(int l, int r) {
                update(l, r, 0, 0, n - 1);
            }

            public int get(int l, int r) {
                return value(l, r, 0, 0, n - 1);
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
