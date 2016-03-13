import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class CHN15C {

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

        private int s = Integer.MAX_VALUE;

        private void inversions(int[] a, int n, int l, int r, LinkedList<Integer> list, int s) {
            if (s >= this.s) {
                return;
            }

            if (l > r) {
                if (s < this.s) {
                    this.s = s;
                }

                return;
            }

            if (a[l] <= list.getFirst()) {
                LinkedList<Integer> list1 = new LinkedList<>(list);
                list1.addFirst(a[l]);
                inversions(a, n, l + 1, r, list1, s);
            } else if (a[l] >= list.getLast()) {
                LinkedList<Integer> list2 = new LinkedList<>(list);
                list2.addLast(a[l]);
                inversions(a, n, l + 1, r, list2, s);
            } else {
                LinkedList<Integer> list1 = new LinkedList<>(list);
                list1.addFirst(a[l]);
                inversions(a, n, l + 1, r, list1, s + 1);

                LinkedList<Integer> list2 = new LinkedList<>(list);
                list2.addLast(a[l]);
                inversions(a, n, l + 1, r, list2, s + 1);
            }

            if (a[r] <= list.getFirst()) {
                LinkedList<Integer> list3 = new LinkedList<>(list);
                list3.addFirst(a[r]);
                inversions(a, n, l, r - 1, list3, s);
            } else if (a[r] >= list.getLast()) {
                LinkedList<Integer> list4 = new LinkedList<>(list);
                list4.addLast(a[r]);
                inversions(a, n, l, r - 1, list4, s);
            } else {
                LinkedList<Integer> list3 = new LinkedList<>(list);
                list3.addFirst(a[r]);
                inversions(a, n, l, r - 1, list3, s + 1);

                LinkedList<Integer> list4 = new LinkedList<>(list);
                list4.addLast(a[r]);
                inversions(a, n, l, r - 1, list4, s + 1);
            }
        }

        public void solve() {
            int t = in.nextInt();

            for (int i = 0; i < t; i++) {
                int n = in.nextInt();

                int[] a = new int[n];

                for (int j = 0; j < n; j++) {
                    int aj = in.nextInt();

                    a[j] = aj;
                }

                LinkedList<Integer> list1 = new LinkedList<>();
                list1.add(a[0]);
                inversions(a, n, 1, n - 1, list1, 0);

                LinkedList<Integer> list2 = new LinkedList<>();
                list2.add(a[n - 1]);
                inversions(a, n, 0, n - 2, list2, 0);

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
