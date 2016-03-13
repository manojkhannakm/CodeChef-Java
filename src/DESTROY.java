import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class DESTROY {

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

                Arrays.sort(a);

                ArrayList<Integer> list1 = new ArrayList<>();

                for (int j = 0, k = 0; k < n; k++) {
                    if (k == n - 1 || a[j] != a[k + 1]) {
                        list1.add(k - j + 1);

                        j = k + 1;
                    }
                }

                Collections.sort(list1);

//                out.println(list1);

                ArrayList<Integer> list2 = new ArrayList<>();

                for (int j = 0, k = 0; k < list1.size(); k++) {
                    if (k == list1.size() - 1 || !list1.get(j).equals(list1.get(k + 1))) {
                        list2.add(list1.get(j) * (k - j + 1));

                        j = k + 1;
                    }
                }

//                out.println(list2);

                long s = 0;

                for (int j = 0; j < list2.size(); j++) {
                    int lj = list2.get(j);

                    s += lj / 2;

                    list2.set(j, lj % 2);
                }

                out.println(list2);
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
