package practice.easy;

import java.io.*;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class STRQ {

    private static InputReader in;
    private static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) {
        if (System.getProperty("OFFLINE_JUDGE") != null) {
            try {
                in = new InputReader(new FileInputStream("in.txt"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            do {
                new Solution().solve();

                out.println("");
            } while (in.nextLine() != null);
        } else {
            in = new InputReader(System.in);

            new Solution().solve();
        }

        out.close();
    }

    private static class Solution {

        public static final int MAX = 1000010;
        public static final int[] CHAR_INDEXES = new int[26];

        private static long[][] charCounts = new long[4][MAX];
        private static long[][][] substringCounts = new long[4][4][MAX];

        private int indexOf(char c) {
            return CHAR_INDEXES[c - 'a'];
        }

        public void solve() {
            char[] chars = {'c', 'h', 'e', 'f'};
            for (int i = 0; i < chars.length; i++) {
                CHAR_INDEXES[chars[i] - 'a'] = i;
            }

            String p = in.nextLine();
            int n = p.length();

            for (int i = 1; i <= n; i++) {
                for (int j = 0; j < 4; j++) {
                    charCounts[j][i] = charCounts[j][i - 1];
                }

                charCounts[indexOf(p.charAt(i - 1))][i]++;
            }

            for (int i = 1; i <= n; i++) {
                for (int j = 0; j < 4; j++) {
                    for (int k = 0; k < 4; k++) {
                        substringCounts[j][k][i] = substringCounts[j][k][i - 1];
                    }
                }

                int piIndex = indexOf(p.charAt(i - 1));
                for (int j = 0; j < 4; j++) {
                    substringCounts[j][piIndex][i] += charCounts[j][i];
                }
            }

            int q = in.nextInt();
            for (int i = 0; i < q; i++) {
                char a = in.nextChar(),
                        b = in.nextChar();
                int l = in.nextInt(),
                        r = in.nextInt();

                int aIndex = indexOf(a),
                        bIndex = indexOf(b);
                out.println(substringCounts[aIndex][bIndex][r] - substringCounts[aIndex][bIndex][l - 1]
                        - charCounts[aIndex][l - 1] * (charCounts[bIndex][r] - charCounts[bIndex][l - 1]));
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
            stringTokenizer = null;

            try {
                return bufferedReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
