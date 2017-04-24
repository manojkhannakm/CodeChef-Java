import java.io.*;
import java.util.*;

/**
 * @author Manoj Khanna
 */

class SCHEDULE {

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

        new Solution().solve(true);

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
                    k = in.nextInt();

            String a = in.nextLine();

            TreeMap<Integer, Integer> map = new TreeMap<>();

            for (int i = 0, j; i < n; ) {
                for (j = i; j < n; j++) {
                    if (a.charAt(j) != a.charAt(i)) {
                        break;
                    }
                }

                int s = j - i;

                map.put(s, map.computeIfAbsent(s, key -> 0) + 1);

                i += s;
            }

            while (k > 0) {
                Map.Entry<Integer, Integer> entry = map.lastEntry();

                int s = entry.getKey();

                if (s <= 2) {
                    break;
                }

                int v = entry.getValue(),
                        c = Math.min(v, k);

                if (s == 5) {
                    if (k >= map.get(2) + map.get(3) + 2 * map.get(4) + 2 * v) {
                        map.put(1, map.computeIfAbsent(1, key -> 0) + 5 * c);
                    } else {
                        map.put((s - 1) / 2, map.computeIfAbsent((s - 1) / 2, key -> 0) + 2 * c);
                        map.put(1, map.computeIfAbsent(1, key -> 0) + c);
                    }
                } else if (s % 2 == 0) {
                    map.put(s / 2 - 1, map.computeIfAbsent(s / 2 - 1, key -> 0) + c);
                    map.put(1, map.computeIfAbsent(1, key -> 0) + c);
                    map.put(s / 2, map.computeIfAbsent(s / 2, key -> 0) + c);
                } else {
                    map.put((s - 1) / 2, map.computeIfAbsent((s - 1) / 2, key -> 0) + 2 * c);
                    map.put(1, map.computeIfAbsent(1, key -> 0) + c);
                }

                if (c == v) {
                    map.remove(s);
                } else {
                    map.put(s, v - k);
                }

                k -= c;
            }

            if (n >= 4) {
                if (k > 0) {
                    if (a.charAt(0) == a.charAt(1) && a.charAt(1) != a.charAt(2)) {
                        map.put(2, map.computeIfAbsent(2, key -> 0) - 1);
                        map.put(1, map.computeIfAbsent(1, key -> 0) + 2);
                    }

                    k--;
                }

                if (k > 0) {
                    if (a.charAt(n - 1) == a.charAt(n - 2) && a.charAt(n - 2) != a.charAt(n - 3)) {
                        map.put(2, map.computeIfAbsent(2, key -> 0) - 1);
                        map.put(1, map.computeIfAbsent(1, key -> 0) + 2);
                    }

                    k--;
                }

                if (map.lastEntry().getValue() == 0) {
                    map.remove(2);
                }
            }

            out.println(map.lastEntry().getKey());
        }

        public void solve(boolean f) {
            int t = f ? in.nextInt() : 1;

            for (int i = 1; i <= t; i++) {
                solve(i);
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
