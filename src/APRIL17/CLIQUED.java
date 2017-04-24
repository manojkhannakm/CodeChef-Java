package APRIL17;

import java.io.*;
import java.util.*;

/**
 * @author Manoj Khanna
 */

class CLIQUED {

    private static InputReader in;
    private static PrintWriter out;

    public static void main(String[] args) {
        List<String> list = Arrays.asList(args);

        if (!list.contains("-in")) {
            in = new InputReader(System.in);
        } else {
            try {
                in = new InputReader(new FileInputStream("in.txt"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        if (!list.contains("-out")) {
            out = new PrintWriter(System.out, true);
        } else {
            try {
                out = new PrintWriter(new FileOutputStream("out.txt"), true);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        long t = System.currentTimeMillis();

        new Solution().solve(true);

        if (list.contains("-debug")) {
            out.println("");
            out.println("Run time: " + ((System.currentTimeMillis() - t) / 1000.0f) + "s");
        }

        in.close();
        out.close();
    }

    private static class Solution {

        private static final int MOD = 1000000007;

        public Solution() {
        }

        private void solve(int t) {
            int n = in.nextInt(),
                    k = in.nextInt(),
                    x = in.nextInt(),
                    m = in.nextInt(),
                    s = in.nextInt();

            int[] a = new int[m],
                    b = new int[m],
                    c = new int[m];

            for (int i = 0; i < m; i++) {
                a[i] = in.nextInt();
                b[i] = in.nextInt();
                c[i] = in.nextInt();
            }

            Graph<Integer, Integer> graph = new Graph<>();

            for (int i = 0; i < n; i++) {
                graph.addVertex(i, i + 1);
            }

            graph.addVertex(n, Integer.MAX_VALUE);

            for (int i = 0; i < m; i++) {
                graph.addEdge(a[i] - 1, b[i] - 1, c[i] * 2);
                graph.addEdge(b[i] - 1, a[i] - 1, c[i] * 2);
            }

            for (int i = 0; i < k; i++) {
                graph.addEdge(n, i, x);
                graph.addEdge(i, n, x);
            }

            n++;

            BinaryHeap<Long> binaryHeap = new BinaryHeap<>(n);

            for (int i = 0; i < n; i++) {
                binaryHeap.add(i, Long.MAX_VALUE);
            }

            binaryHeap.set(s - 1, 0L);

            long[] d = new long[n];

            while (!binaryHeap.isEmpty()) {
                BinaryHeap.Node<Long> node = binaryHeap.nodes[binaryHeap.q[0]];

                d[node.i] = node.e;

                binaryHeap.remove(node.i);

                for (Graph.Edge<Integer> edge : graph.vertexMap.get(node.i).edgeMap.values()) {
                    long w = node.e + edge.w;

                    if (binaryHeap.contains(edge.v) && w < binaryHeap.get(edge.v)) {
                        binaryHeap.set(edge.v, w);
                    }
                }
            }

            n--;

            for (int i = 0; i < n; i++) {
                if (i > 0) {
                    out.print(" ");
                }

                out.print(d[i] / 2);
            }

            out.println("");
        }

        public void solve(boolean f) {
            int t = f ? in.nextInt() : 1;

            for (int i = 1; i <= t; i++) {
                solve(i);
            }
        }

        @SuppressWarnings("unused")
        public static class Graph<E, W> {

            public HashMap<Integer, Vertex<E, W>> vertexMap;

            public Graph() {
                vertexMap = new HashMap<>();
            }

            public Graph(int c) {
                vertexMap = new HashMap<>(c);
            }

            @Override
            public String toString() {
                StringBuilder string = new StringBuilder();

                for (Vertex<E, W> vertex : vertexMap.values()) {
                    if (string.length() > 0) {
                        string.append("\n");
                    }

                    string.append(vertex);
                }

                return string.toString();
            }

            public void addVertex(int u, E e) {
                if (!vertexMap.containsKey(u)) {
                    vertexMap.put(u, new Vertex<>(u, e));
                }
            }

            public void removeVertex(int u) {
                if (vertexMap.containsKey(u)) {
                    for (Integer v : vertexMap.remove(u).edgeMap.keySet()) {
                        removeEdge(v, u);
                    }
                }
            }

            public void addEdge(int u, int v, W w) {
                if (vertexMap.containsKey(u)) {
                    vertexMap.get(u).edgeMap.put(v, new Edge<>(v, w));
                }
            }

            public void removeEdge(int u, int v) {
                if (vertexMap.containsKey(u)) {
                    vertexMap.get(u).edgeMap.remove(v);
                }
            }

            public void clear() {
                vertexMap.clear();
            }

            public boolean contains(int u) {
                return vertexMap.containsKey(u);
            }

            public int size() {
                return vertexMap.size();
            }

            public boolean isEmpty() {
                return vertexMap.isEmpty();
            }

            public E get(int u) {
                if (vertexMap.containsKey(u)) {
                    return vertexMap.get(u).e;
                }

                return null;
            }

            public void set(int u, E e) {
                if (vertexMap.containsKey(u)) {
                    vertexMap.get(u).e = e;
                }
            }

            public W getWeight(int u, int v) {
                if (vertexMap.containsKey(u)) {
                    HashMap<Integer, Edge<W>> edgeMap = vertexMap.get(u).edgeMap;

                    if (edgeMap.containsKey(v)) {
                        return edgeMap.get(v).w;
                    }
                }

                return null;
            }

            public void setWeight(int u, int v, W w) {
                if (vertexMap.containsKey(u)) {
                    HashMap<Integer, Edge<W>> edgeMap = vertexMap.get(u).edgeMap;

                    if (edgeMap.containsKey(v)) {
                        edgeMap.get(v).w = w;
                    }
                }
            }

            public static class Vertex<E, W> {

                public int u;
                public E e;
                public HashMap<Integer, Edge<W>> edgeMap;

                public Vertex(int u, E e) {
                    this.u = u;
                    this.e = e;

                    edgeMap = new HashMap<>();
                }

                @Override
                public String toString() {
                    StringBuilder string = new StringBuilder();

                    for (Edge<W> edge : edgeMap.values()) {
                        if (string.length() > 0) {
                            string.append(", ");
                        }

                        string.append(edge);
                    }

                    return u + "(" + e + ") -> " + string.toString();
                }

            }

            public static class Edge<W> {

                public int v;
                public W w;

                public Edge(int v, W w) {
                    this.v = v;
                    this.w = w;
                }

                @Override
                public String toString() {
                    return v + (w != null ? "(" + w + ")" : "");
                }

            }

        }

        @SuppressWarnings("unused")
        public static class BinaryHeap<E> {

            public int c, n;
            public Comparator<? super E> comparator;
            public Node<E>[] nodes;
            public int[] p, q;

            @SuppressWarnings("unchecked")
            public BinaryHeap(int c, Comparator<? super E> comparator) {
                this.c = c;
                this.comparator = comparator;

                nodes = new Node[c];
                p = new int[c];
                q = new int[c];
            }

            @SuppressWarnings("unchecked")
            public BinaryHeap(int c) {
                this(c, (o1, o2) -> ((Comparable<E>) o1).compareTo(o2));
            }

            @Override
            public String toString() {
                return toString(0);
            }

            private int parentIndex(int i) {
                return (i - 1) / 2;
            }

            private int leftIndex(int i) {
                return 2 * i + 1;
            }

            private int rightIndex(int i) {
                return 2 * i + 2;
            }

            public String toString(int i) {
                if (i >= n) {
                    return "";
                }

                StringBuilder string = new StringBuilder();

                int d = (int) Math.floor(Math.log(i + 1) / Math.log(2));

                if (d > 0) {
                    string.append("\n");
                }

                for (int j = 0; j < 4 * d; j++) {
                    string.append(" ");
                }

                string.append(nodes[q[i]])
                        .append(toString(leftIndex(i)))
                        .append(toString(rightIndex(i)));

                return string.toString();
            }

            private void swap(int i, int j) {
                int x = p[q[i]];
                p[q[i]] = p[q[j]];
                p[q[j]] = x;

                int y = q[i];
                q[i] = q[j];
                q[j] = y;
            }

            private void siftUp(int i) {
                while (i > 0) {
                    int j = parentIndex(i);

                    if (comparator.compare(nodes[q[i]].e, nodes[q[j]].e) >= 0) {
                        break;
                    }

                    swap(i, j);

                    i = j;
                }
            }

            private void siftDown(int i) {
                while (i < n) {
                    int j = leftIndex(i), k = rightIndex(i);

                    if (k < n && comparator.compare(nodes[q[j]].e, nodes[q[k]].e) >= 0) {
                        j = k;
                    }

                    if (j >= n || comparator.compare(nodes[q[i]].e, nodes[q[j]].e) <= 0) {
                        break;
                    }

                    swap(i, j);

                    i = j;
                }
            }

            public void add(int i, E e) {
                if (nodes[i] == null) {
                    nodes[i] = new Node<>(i, e);
                    p[i] = n;
                    q[n] = i;

                    n++;

                    siftUp(n - 1);
                }
            }

            public void remove(int i) {
                if (nodes[i] != null) {
                    int c = comparator.compare(nodes[q[n - 1]].e, nodes[i].e);

                    nodes[i] = null;
                    p[q[n - 1]] = p[i];
                    q[p[i]] = q[n - 1];

                    n--;

                    if (c < 0) {
                        siftUp(p[i]);
                    } else if (c > 0) {
                        siftDown(p[i]);
                    }
                }
            }

            public E peek() {
                if (nodes[q[0]] != null) {
                    return nodes[q[0]].e;
                }

                return null;
            }

            public E poll() {
                if (nodes[q[0]] != null) {
                    E e = nodes[q[0]].e;

                    remove(q[0]);

                    return e;
                }

                return null;
            }

            public void clear() {
                for (int i = 0; i < c; i++) {
                    nodes[i] = null;
                }

                n = 0;
            }

            public boolean contains(int i) {
                return nodes[i] != null;
            }

            public int size() {
                return n;
            }

            public boolean isEmpty() {
                return n == 0;
            }

            public E get(int i) {
                if (nodes[i] != null) {
                    return nodes[i].e;
                }

                return null;
            }

            public void set(int i, E e) {
                if (nodes[i] != null) {
                    int c = comparator.compare(e, nodes[i].e);

                    nodes[i].e = e;

                    if (c < 0) {
                        siftUp(p[i]);
                    } else if (c > 0) {
                        siftDown(p[i]);
                    }
                }
            }

            public static class Node<E> {

                public int i;
                public E e;

                public Node(int i, E e) {
                    this.i = i;
                    this.e = e;
                }

                @Override
                public String toString() {
                    return i + "(" + e + ")";
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

        public int[] nextIntArray(int n) {
            int[] a = new int[n];

            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt();
            }

            return a;
        }

        public long[] nextLongArray(int n) {
            long[] a = new long[n];

            for (int i = 0; i < n; i++) {
                a[i] = in.nextLong();
            }

            return a;
        }

        public float[] nextFloatArray(int n) {
            float[] a = new float[n];

            for (int i = 0; i < n; i++) {
                a[i] = in.nextFloat();
            }

            return a;
        }

        public double[] nextDoubleArray(int n) {
            double[] a = new double[n];

            for (int i = 0; i < n; i++) {
                a[i] = in.nextDouble();
            }

            return a;
        }

        public String nextLine() {
            stringTokenizer = null;

            try {
                String line;

                do {
                    line = bufferedReader.readLine();
                } while (line != null && line.isEmpty());

                if (line == null) {
                    throw new NullPointerException();
                }

                return line;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void close() {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
