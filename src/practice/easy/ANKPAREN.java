package practice.easy;

import java.io.*;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class ANKPAREN {

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
                String s = in.nextLine();
                int k = in.nextInt();

                Stack<Character> stack = new Stack<>();
                boolean b = true;

                for (int j = 0; j < s.length(); j++) {
                    if (s.charAt(j) == '(') {
                        stack.push('(');
                    } else {
                        if (!stack.isEmpty()) {
                            stack.pop();
                        } else {
                            b = false;
                            break;
                        }
                    }
                }

                b = b && stack.isEmpty();

                if (b) {
                    int n = 0;
                    String r = null;

                    for (int j = 1; j < s.length(); j++) {
                        char c1 = s.charAt(j - 1),
                                c2 = s.charAt(j);

                        if (c1 != c2 && c2 == ')') {
                            n++;

                            if (n == k) {
                                r = s.substring(0, j) + s.substring(j + 1);
                                break;
                            }
                        }
                    }

                    for (int j = s.length() - 2; j >= 0; j--) {
                        char c1 = s.charAt(j),
                                c2 = s.charAt(j + 1);

                        if (c1 != c2 && c1 == '(') {
                            n++;

                            if (n == k) {
                                r = s.substring(0, j) + s.substring(j + 1);
                                break;
                            }
                        }
                    }

                    if (n >= k) {
                        out.println(r);
                    } else {
                        out.println(-1);
                    }
                } else {
                    if (k == 1) {
                        out.println(s);
                    } else {
                        out.println(-1);
                    }
                }
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
