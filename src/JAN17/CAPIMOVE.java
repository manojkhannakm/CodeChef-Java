package JAN17;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class CAPIMOVE {

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

                int[] p = new int[n];

                for (int j = 0; j < n; j++) {
                    p[j] = in.nextInt();
                }

                int[] v = new int[n - 1],
                        u = new int[n - 1];

                for (int j = 0; j < n - 1; j++) {
                    v[j] = in.nextInt();
                    u[j] = in.nextInt();
                }

                HashMap<Integer, Planet> map = new HashMap<>();

                for (int j = 0; j < n; j++) {
                    Planet planet = new Planet(j + 1, p[j]);
                    planet.adjList.add(j + 1);

                    map.put(j + 1, planet);
                }

                for (int j = 0; j < n - 1; j++) {
                    map.get(v[j]).adjList.add(u[j]);
                    map.get(u[j]).adjList.add(v[j]);
                }

                ArrayList<Planet> list = new ArrayList<>(map.values());
                list.sort((o1, o2) -> Integer.compare(o2.p, o1.p));

                for (int j = 0; j < n; j++) {
                    if (j > 0) {
                        out.print(" ");
                    }

                    Planet planet = map.get(j + 1);
                    boolean f = false;

                    for (Planet capitalPlanet : list) {
                        if (!planet.adjList.contains(capitalPlanet.i)) {
                            out.print(capitalPlanet.i);

                            f = true;
                            break;
                        }
                    }

                    if (!f) {
                        out.print(0);
                    }
                }

                out.println("");
            }
        }

        private class Planet {

            private final int i, p;

            private ArrayList<Integer> adjList = new ArrayList<>();

            public Planet(int i, int p) {
                this.i = i;
                this.p = p;
            }

            @Override
            public String toString() {
                return i + " (" + p + ") -> " + adjList;
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
