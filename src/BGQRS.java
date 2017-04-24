import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Manoj Khanna
 */

class BGQRS {

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

        private static final int MAX = 100010;

        private int[] c2 = new int[MAX];
        private int[] c5 = new int[MAX];

        private int factors(int n, int f) {
            int c = 0;

            while (n > 0 && n % f == 0) {
                n /= f;
                c++;
            }

            return c;
        }

        public void solve() {
            c2[2] = 1;
            c5[5] = 1;

            for (int i = 2 + 2; i < MAX; i += 2) {
                c2[i] = c2[i / 2] + 1;
            }

            for (int i = 5 + 5; i < MAX; i += 5) {
                c5[i] = c5[i / 5] + 1;
            }

            for (int i = 1; i < MAX; i++) {
                c2[i] += c2[i - 1];
                c5[i] += c5[i - 1];
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
                        r = new int[m],
                        x = new int[m];

                for (int j = 0; j < m; j++) {
                    q[j] = in.nextInt();
                    l[j] = in.nextInt() - 1;
                    r[j] = in.nextInt() - 1;

                    if (q[j] <= 2) {
                        x[j] = in.nextInt();
                    }
                }

                Element[] elements = new Element[n];

                for (int j = 0; j < n; j++) {
                    elements[j] = new Element(factors(a[j], 2), factors(a[j], 5));
                }

                SegmentTree<Element, Update> segmentTree = new SegmentTree<Element, Update>(elements) {

                    @Override
                    protected Element get(Element e, Element le, Element re, int l, int r) {
                        if (e == null) {
                            return new Element(le.c2 + re.c2, le.c5 + re.c5);
                        }

                        e.c2 = le.c2 + re.c2;
                        e.c5 = le.c5 + re.c5;

                        return e;
                    }

                    @Override
                    protected Element set(Element e, Update u, int l, int r) {
                        if (u.q == 1) {
                            e.c2 += (r - l + 1) * u.c2;
                            e.c5 += (r - l + 1) * u.c5;
                        } else if (u.q == 2) {
                            e.c2 = c2[r - u.l + 1] - c2[l - u.l + 1 - 1] + (r - l + 1) * u.c2;
                            e.c5 = c5[r - u.l + 1] - c5[l - u.l + 1 - 1] + (r - l + 1) * u.c5;
                        }

                        return e;
                    }

                    @Override
                    protected Update lazy(Update ou, Update nu, int l, int r) {
                        if (ou == null) {
                            return new Update(nu.q, nu.l, nu.c2, nu.c5);
                        }

                        if (nu.q == 1) {
                            ou.c2 += nu.c2;
                            ou.c5 += nu.c5;
                        } else if (nu.q == 2) {
                            ou.q = 2;
                            ou.l = nu.l;
                            ou.c2 = nu.c2;
                            ou.c5 = nu.c5;
                        }

                        return ou;
                    }

                };

//                System.out.println(segmentTree);
//                System.out.println("");

                int c = 0;

                for (int j = 0; j < m; j++) {
                    if (q[j] <= 2) {
                        int c2 = factors(x[j], 2),
                                c5 = factors(x[j], 5);

                        if (q[j] == 1) {
                            if (c2 > 0 || c5 > 0) {
                                segmentTree.lazySet(l[j], r[j], new Update(1, c2, c5));
                            }
                        } else if (q[j] == 2) {
                            segmentTree.lazySet(l[j], r[j], new Update(2, l[j], c2, c5));
                        }

//                        System.out.println(segmentTree);
//                        System.out.println("");
                    } else {
                        Element element = segmentTree.lazyGet(l[j], r[j]);

                        c += Math.min(element.c2, element.c5);

//                        System.out.println(segmentTree);
//                        System.out.println("");
                    }
                }

                out.println(c);
            }
        }

        private static class Element {

            public int c2, c5;

            public Element(int c2, int c5) {
                this.c2 = c2;
                this.c5 = c5;
            }

            @Override
            public String toString() {
                return "(" + c2 + ", " + c5 + ")";
            }

        }

        private static class Update {

            public int q, c2, c5, l;

            public Update(int q, int c2, int c5) {
                this.q = q;
                this.c2 = c2;
                this.c5 = c5;
            }

            public Update(int q, int l, int c2, int c5) {
                this(q, c2, c5);

                this.l = l;
            }

            @Override
            public String toString() {
                if (q == 1) {
                    return q + " (" + c2 + ", " + c5 + ")";
                } else {
                    return q + " [" + l + ", ] (" + c2 + ", " + c5 + ")";
                }
            }

        }

        private static abstract class SegmentTree<E, U> {

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

                if (node.l > r || node.r < l) {
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

            private E lazyGet(int l, int r, int i) {
                Node<E, U> node = nodes[i];

                if (node.l > r || node.r < l) {
                    return null;
                }

                if (node.l >= l && node.r <= r) {
                    return node.e;
                }

                Node<E, U> lNode = nodes[2 * i + 1],
                        rNode = nodes[2 * i + 2];

                if (node.u != null) {
                    lNode.u = lazy(lNode.u, node.u, lNode.l, lNode.r);
                    lNode.e = set(lNode.e, lNode.u, lNode.l, lNode.r);

                    rNode.u = lazy(rNode.u, node.u, rNode.l, rNode.r);
                    rNode.e = set(rNode.e, rNode.u, rNode.l, rNode.r);

                    node.u = null;
                }

                E le = lazyGet(l, r, 2 * i + 1),
                        re = lazyGet(l, r, 2 * i + 2);

                node.e = get(node.e, lNode.e, rNode.e, node.l, node.r);

                if (le != null && re != null) {
                    return get(null, le, re, node.l, node.r);
                } else if (le != null) {
                    return le;
                } else {
                    return re;
                }
            }

            private void lazySet(int l, int r, U u, int i) {
                Node<E, U> node = nodes[i];

                if (node.l > r || node.r < l) {
                    return;
                }

                if (node.l >= l && node.r <= r) {
                    node.u = lazy(node.u, u, node.l, node.r);
                    node.e = set(node.e, node.u, node.l, node.r);
                    return;
                }

                Node<E, U> lNode = nodes[2 * i + 1],
                        rNode = nodes[2 * i + 2];

                if (node.u != null) {
                    lNode.u = lazy(lNode.u, node.u, lNode.l, lNode.r);
                    lNode.e = set(lNode.e, lNode.u, lNode.l, lNode.r);

                    rNode.u = lazy(rNode.u, node.u, rNode.l, rNode.r);
                    rNode.e = set(rNode.e, rNode.u, rNode.l, rNode.r);

                    node.u = null;
                }

                lazySet(l, r, u, 2 * i + 1);
                lazySet(l, r, u, 2 * i + 2);

                node.e = get(node.e, lNode.e, rNode.e, node.l, node.r);
            }

            private void lazyUpdate(int l, int r, U u, int i) {
                Node<E, U> node = nodes[i];

                if (node.l > r || node.r < l) {
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
                    lNode.p = true;
                    lNode.u = lazy(lNode.u, node.u, lNode.l, lNode.r);

                    rNode.p = true;
                    rNode.u = lazy(rNode.u, node.u, rNode.l, rNode.r);

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
                    lNode.p = true;
                    lNode.u = lazy(lNode.u, node.u, lNode.l, lNode.r);

                    rNode.p = true;
                    rNode.u = lazy(rNode.u, node.u, rNode.l, rNode.r);

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

            public E lazyGet(int l, int r) {
                return lazyGet(l, r, 0);
            }

            public void lazySet(int l, int r, U u) {
                lazySet(l, r, u, 0);
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
