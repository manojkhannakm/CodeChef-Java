package SEPT16;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class JTREE {

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

        private static final int N_MAX = 100010;

        public void solve() {
            int n = in.nextInt(),
                    m = in.nextInt();

            int[] a = new int[n - 1],
                    b = new int[n - 1];

            for (int i = 0; i < n - 1; i++) {
                int ai = in.nextInt(),
                        bi = in.nextInt();

                a[i] = ai;
                b[i] = bi;
            }

            int[] v = new int[m],
                    k = new int[m],
                    w = new int[m];

            for (int i = 0; i < m; i++) {
                int vi = in.nextInt(),
                        ki = in.nextInt(),
                        wi = in.nextInt();

                v[i] = vi;
                k[i] = ki;
                w[i] = wi;
            }

            int q = in.nextInt();

            int[] h = new int[q];

            for (int i = 0; i < q; i++) {
                int hi = in.nextInt();

                h[i] = hi;
            }

            Ticket[] tickets = new Ticket[m];

            for (int i = 0; i < m; i++) {
                tickets[i] = new Ticket(v[i], k[i], w[i]);
            }

            Arrays.sort(tickets, (o1, o2) -> {
                int c = Integer.compare(o1.w, o2.w);

                if (c == 0) {
                    c = Integer.compare(o2.k, o1.k);
                }

                return c;
            });

            Tree tree = new Tree(n, a, b, m, tickets);
            tree.dfs();

            for (int hi : h) {
                out.println(tree.value(hi));
            }
        }

        private class Ticket {

            private int v, k, w;

            public Ticket(int v, int k, int w) {
                this.v = v;
                this.k = k;
                this.w = w;
            }

        }

        private class Tree {

            private HashMap<Integer, Node> nodeMap;
            private Node rootNode;

            public Tree(int n, int[] a, int[] b, int m, Ticket[] tickets) {
                nodeMap = new HashMap<>(n);

                for (int i = 1; i <= n; i++) {
                    nodeMap.put(i, new Node());
                }

                for (int i = 0; i < n - 1; i++) {
                    nodeMap.get(b[i]).nodeList.add(nodeMap.get(a[i]));
                }

                for (int i = 0; i < m; i++) {
                    nodeMap.get(tickets[i].v).costList.add(new Cost(tickets[i].k, tickets[i].w));
                }

                rootNode = nodeMap.get(1);
            }

            private void dfs(Node node, SegmentTree segmentTree, int i) {
                int mk = 0;
                long mw = Long.MAX_VALUE;

                for (Cost cost : node.costList) {
                    if (mk > i) {
                        break;
                    }

                    if (cost.k > mk) {
                        long sw = cost.w + segmentTree.value(i > cost.k ? i - cost.k : 0, i - 1);

                        if (sw < mw) {
                            mw = sw;
                        }

                        mk = cost.k;
                    }
                }

                node.w = mw;

                segmentTree.update(mw, i);

                for (Node childNode : node.nodeList) {
                    dfs(childNode, segmentTree, i + 1);
                }
            }

            public void dfs() {
                SegmentTree segmentTree = new SegmentTree();
                segmentTree.update(0, 0);

                for (Node childNode : rootNode.nodeList) {
                    dfs(childNode, segmentTree, 1);
                }
            }

            public long value(int hi) {
                return nodeMap.get(hi).w;
            }

            private class Node {

                private ArrayList<Node> nodeList = new ArrayList<>();
                private ArrayList<Cost> costList = new ArrayList<>();
                private long w;

            }

            private class Cost {

                private int k, w;

                public Cost(int k, int w) {
                    this.k = k;
                    this.w = w;
                }

            }

        }

        private class SegmentTree {

            private long[] s;

            public SegmentTree() {
                s = new long[2 * (int) Math.pow(2, Math.ceil(Math.log(N_MAX) / Math.log(2))) - 1];

                create(0, 0, N_MAX - 1);
            }

            private long create(int i, int l, int r) {
                if (l == r) {
                    s[i] = Long.MAX_VALUE;

                    return s[i];
                }

                int m = l + (r - l) / 2;

                s[i] = Math.min(create(2 * i + 1, l, m),
                        create(2 * i + 2, m + 1, r));

                return s[i];
            }

            private long update(long su, int u, int i, int l, int r) {
                if (u < l || r < u) {
                    return s[i];
                }

                if (l == r) {
                    s[i] = su;

                    return s[i];
                }

                int m = l + (r - l) / 2;

                s[i] = Math.min(update(su, u, 2 * i + 1, l, m),
                        update(su, u, 2 * i + 2, m + 1, r));

                return s[i];
            }

            private long value(int vl, int vr, int i, int l, int r) {
                if (vr < l || r < vl) {
                    return Long.MAX_VALUE;
                }

                if (vl <= l && r <= vr) {
                    return s[i];
                }

                int m = l + (r - l) / 2;

                return Math.min(value(vl, vr, 2 * i + 1, l, m),
                        value(vl, vr, 2 * i + 2, m + 1, r));
            }

            public void update(long su, int u) {
                update(su, u, 0, 0, N_MAX - 1);
            }

            public long value(int vl, int vr) {
                return value(vl, vr, 0, 0, N_MAX - 1);
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
