package OCT15;

import java.io.*;
import java.util.*;

/**
 * @author Manoj Khanna
 */

class TIMEASR {

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
            HashMap<Float, ArrayList<String>> angleMap = new HashMap<>();

            for (int i = 0; i <= 11; i++) {
                for (int j = 0; j <= 59; j++) {
                    float a = Math.abs(60.0f * i - 11.0f * j) / 2.0f;

                    if (a > 180) {
                        a = 360 - a;
                    }

                    ArrayList<String> timeList = angleMap.get(a);

                    if (timeList == null) {
                        timeList = new ArrayList<>();
                        angleMap.put(a, timeList);
                    }

                    timeList.add(String.format("%02d:%02d", i, j));
                }
            }

            int t = in.nextInt();

            for (int i = 0; i < t; i++) {
                float a = in.nextFloat();

                for (Map.Entry<Float, ArrayList<String>> entry : angleMap.entrySet()) {
                    Float angle = entry.getKey();

                    if (angle >= a - 0.0083f && angle <= a + 0.0083f) {
                        for (String time : entry.getValue()) {
                            out.println(time);
                        }
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
