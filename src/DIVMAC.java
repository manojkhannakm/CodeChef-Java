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
                    a[j] = in.nextInt();
                }

                int[] q = new int[m],
                        l = new int[m],
                        r = new int[m];

                for (int j = 0; j < m; j++) {
                    q[j] = in.nextInt();
                    l[j] = in.nextInt() - 1;
                    r[j] = in.nextInt() - 1;
                }

                Element[] elements = new Element[n];

                for (int j = 0; j < n; j++) {
                    elements[j] = new Element(a[j], p[a[j]]);
                }

                SegmentTree<Element, Integer> segmentTree = new SegmentTree<Element, Integer>(elements) {

                    @Override
                    protected Element get(Element e, Element le, Element re, int l, int r) {
                        if (e == null) {
                            e = new Element();
                        }

                        if (le.p > re.p) {
                            e.n = le.n;
                            e.p = le.p;
                        } else {
                            e.n = re.n;
                            e.p = re.p;
                        }

                        return e;
                    }

                    @Override
                    protected Element set(Element e, Integer u, int l, int r) {
                        for (int i = 0; i < u && e.n > 1; i++) {
                            e.n /= p[e.n];
                        }

                        e.p = p[e.n];

                        return e;
                    }

                    @Override
                    protected Integer lazy(Integer ou, Integer nu, int l, int r) {
                        if (ou == null) {
                            return nu;
                        }

                        return ou + nu;
                    }

                };

                ArrayList<Integer> list = new ArrayList<>();

                for (int j = 0; j < m; j++) {
                    if (q[j] == 0) {
                        segmentTree.lazyUpdate(l[j], r[j], 1);
                    } else if (q[j] == 1) {
                        segmentTree.lazyPropagate(l[j], r[j]);

                        list.add(segmentTree.get(l[j], r[j]).p);
                    }
                }

                for (int j = 0; j < list.size(); j++) {
                    if (j > 0) {
                        out.print(" ");
                    }

                    out.print(list.get(j));
                }

                out.println("");
            }
        }

        private static class Element {

            public int n, p;

            public Element() {
            }

            public Element(int n, int p) {
                this.n = n;
                this.p = p;
            }

        }

        private static abstract class SegmentTree<E extends Element, U> {

            private final Node<E, U>[] nodes;

            @SuppressWarnings("unchecked")
            public SegmentTree(E[] e) {
                this.nodes = new Node[2 * (int) Math.pow(2, Math.ceil(Math.log(e.length) / Math.log(2))) - 1];

                create(e, 0, e.length - 1, 0);
            }

            protected abstract E get(E e, E le, E re, int l, int r);

            protected abstract E set(E e, U u, int l, int r);

            protected U lazy(U ou, U nu, int l, int r) {
                return null;
            }

            @Override
            public String toString() {
                return treeString();
            }

            private void create(E[] e, int l, int r, int i) {
                Node<E, U> node = nodes[i] = new Node<>(l, r);

                if (l == r) {
                    node.e = e[l];
                    return;
                }

                int m = (l + r) / 2;

                create(e, l, m, 2 * i + 1);
                create(e, m + 1, r, 2 * i + 2);

                node.e = get(null, nodes[2 * i + 1].e, nodes[2 * i + 2].e, l, r);
            }

            private E get(int l, int r, int i) {
                Node<E, U> node = nodes[i];

                if (node.l > r || node.r < l) {
                    return null;
                }

                if (node.l >= l && node.r <= r) {
                    return node.e;
                }

                E le = get(l, r, 2 * i + 1),
                        re = get(l, r, 2 * i + 2);

                if (le != null && re != null) {
                    return get(null, le, re, node.l, node.r);
                } else if (le != null) {
                    return le;
                } else {
                    return re;
                }
            }

            private void set(int l, int r, U u, int i) {
                Node<E, U> node = nodes[i];

                if (node.l > r || node.r < l || node.e.n == 1) {
                    return;
                }

                if (node.l == node.r) {
                    node.e = set(node.e, u, node.l, node.r);
                    return;
                }

                set(l, r, u, 2 * i + 1);
                set(l, r, u, 2 * i + 2);

                node.e = get(node.e, nodes[2 * i + 1].e, nodes[2 * i + 2].e, node.l, node.r);
            }

            private void lazyUpdate(int l, int r, U u, int i) {
                Node<E, U> node = nodes[i];

                if (node.l > r || node.r < l || node.e.n == 1) {
                    return;
                }

                if (node.l >= l && node.r <= r) {
                    node.p = true;
                    node.u = lazy(node.u, u, node.l, node.r);
                    return;
                }

                Node<E, U> lNode = nodes[2 * i + 1],
                        rNode = nodes[2 * i + 2];

                if (node.u != null) {
                    if (lNode.e.n != 1) {
                        lNode.p = true;
                        lNode.u = lazy(lNode.u, node.u, lNode.l, lNode.r);
                    }

                    if (rNode.e.n != 1) {
                        rNode.p = true;
                        rNode.u = lazy(rNode.u, node.u, rNode.l, rNode.r);
                    }

                    node.u = null;
                }

                lazyUpdate(l, r, u, 2 * i + 1);
                lazyUpdate(l, r, u, 2 * i + 2);

                node.p = lNode.p || rNode.p;
            }

            private void lazyPropagate(int l, int r, int i) {
                Node<E, U> node = nodes[i];

                if (node.l > r || node.r < l || !node.p) {
                    return;
                }

                if (node.l == node.r) {
                    node.e = set(node.e, node.u, node.l, node.r);
                    node.p = false;
                    node.u = null;
                    return;
                }

                Node<E, U> lNode = nodes[2 * i + 1],
                        rNode = nodes[2 * i + 2];

                if (node.u != null) {
                    if (lNode.e.n != 1) {
                        lNode.p = true;
                        lNode.u = lazy(lNode.u, node.u, lNode.l, lNode.r);
                    }

                    if (rNode.e.n != 1) {
                        rNode.p = true;
                        rNode.u = lazy(rNode.u, node.u, rNode.l, rNode.r);
                    }

                    node.u = null;
                }

                lazyPropagate(l, r, 2 * i + 1);
                lazyPropagate(l, r, 2 * i + 2);

                node.e = get(node.e, lNode.e, rNode.e, node.l, node.r);
                node.p = lNode.p || rNode.p;
            }

            private String treeString(int i, int d) {
                String s = "";

                if (i > 0) {
                    s += "\n";
                }

                for (int j = 0; j < 4 * d; j++) {
                    s += " ";
                }

                Node<E, U> node = nodes[i];

                s += node;

                if (node.l < node.r) {
                    s += treeString(2 * i + 1, d + 1);
                    s += treeString(2 * i + 2, d + 1);
                }

                return s;
            }

            private String arrayString(int i) {
                String s = "";

                if (i == 0) {
                    s += "[";
                }

                Node<E, U> node = nodes[i];

                if (node.l == node.r) {
                    if (node.l > 0) {
                        s += ", ";
                    }

                    s += node.e;
                } else {
                    s += arrayString(2 * i + 1);
                    s += arrayString(2 * i + 2);
                }

                if (i == 0) {
                    s += "]";
                }

                return s;
            }

            public E get(int l, int r) {
                return get(l, r, 0);
            }

            public void set(int l, int r, U u) {
                set(l, r, u, 0);
            }

            public void lazyUpdate(int l, int r, U u) {
                lazyUpdate(l, r, u, 0);
            }

            public void lazyPropagate(int l, int r) {
                lazyPropagate(l, r, 0);
            }

            public String treeString() {
                return treeString(0, 0);
            }

            public String arrayString() {
                return arrayString(0);
            }

            private static class Node<E, U> {

                private final int l, r;

                private E e;
                private U u;
                private boolean p;

                public Node(int l, int r) {
                    this.l = l;
                    this.r = r;
                }

                @Override
                public String toString() {
                    return "[" + l + ", " + r + "] = " + e + (u != null ? " -> " + u : "");
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
