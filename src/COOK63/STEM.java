package COOK63;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * @author Manoj Khanna
 */

class STEM {

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

        private TreeSet<String> commonSubstring(String w1, String w2) {
            TreeSet<String> set = new TreeSet<>(new Comparator());
            set.add("");

            for (int i = 0; i < w1.length(); i++) {
                for (int j = 0; j < w2.length(); j++) {
                    if (w1.charAt(i) == w2.charAt(j)) {
                        int k = 0;

                        while (i + k < w1.length()
                                && j + k < w2.length()
                                && w1.charAt(i + k) == w2.charAt(j + k)) {
                            k++;
                        }

                        set.add(w1.substring(i, i + k));
                    }
                }
            }

            return set;
        }

        public void solve() {
            int t = in.nextInt();

            for (int i = 0; i < t; i++) {
                int n = in.nextInt();

                String[] w = new String[n];

                for (int j = 0; j < n; j++) {
                    String wj = in.next();

                    w[j] = wj;
                }

                Arrays.sort(w);

                TreeSet<String> set1 = new TreeSet<>(new Comparator());
                set1.add(w[0]);

                for (int j = 1; j < n; j++) {
                    TreeSet<String> set2 = new TreeSet<>(new Comparator());

                    for (String s : set1) {
                        set2.addAll(commonSubstring(s, w[j]));
                    }

                    set1 = set2;
                }

                out.println(set1.first());
            }
        }

        private class Comparator implements java.util.Comparator<String> {

            @Override
            public int compare(String o1, String o2) {
                int j = Integer.compare(o2.length(), o1.length());

                if (j == 0) {
                    j = o1.compareTo(o2);
                }

                return j;
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
