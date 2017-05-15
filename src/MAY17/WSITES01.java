package MAY17;

import java.io.*;
import java.util.*;

/**
 * @author Manoj Khanna
 */

class WSITES01 {

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

        new Solution().solve(false);

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

        private void filter(Trie.Node<Character> node, ArrayList<Character> list, ArrayList<String> filterList) {
            for (Trie.Node<Character> childNode : node.childNodeMap.values()) {
                list.add(childNode.e);

                if (childNode.childNodeMap.isEmpty()) {
                    StringBuilder s = new StringBuilder();

                    for (Character c : list) {
                        s.append(c);
                    }

                    filterList.add(s.toString());
                } else {
                    filter(childNode, list, filterList);
                }

                list.remove(list.size() - 1);
            }
        }

        private void solve(int t) {
            int n = in.nextInt();

            char[] c = new char[n];

            String[] s = new String[n];

            for (int i = 0; i < n; i++) {
                c[i] = in.nextChar();

                s[i] = in.next();
            }

            Trie<Character> websiteTrie = new Trie<>('\0');

            for (int i = 0; i < n; i++) {
                if (c[i] == '+') {
                    Character[] si = new Character[s[i].length()];

                    for (int j = 0; j < s[i].length(); j++) {
                        si[j] = s[i].charAt(j);
                    }

                    websiteTrie.add(si);
                }
            }

            Trie<Character> filterTrie = new Trie<>('\0');

            for (int i = 0; i < n; i++) {
                if (c[i] == '-') {
                    Trie.Node<Character> websiteNode = websiteTrie.rootNode,
                            filterNode = filterTrie.rootNode;

                    boolean b = false;

                    for (int j = 0; j < s[i].length(); j++) {
                        char sij = s[i].charAt(j);

                        filterNode.addChild(sij);

                        if (!websiteNode.childNodeMap.containsKey(sij)) {
                            b = true;
                            break;
                        }

                        websiteNode = websiteNode.childNodeMap.get(sij);
                        filterNode = filterNode.childNodeMap.get(sij);
                    }

                    if (!b) {
                        out.println(-1);
                        return;
                    }
                }
            }

            ArrayList<String> filterList = new ArrayList<>();

            filter(filterTrie.rootNode, new ArrayList<>(), filterList);

            out.println(filterList.size());

            for (String filter : filterList) {
                out.println(filter);
            }
        }

        public void solve(boolean f) {
            int t = f ? in.nextInt() : 1;

            for (int i = 1; i <= t; i++) {
                solve(i);
            }
        }

        public static class Trie<E> {

            public final Node<E> rootNode;

            public Trie(E e) {
                rootNode = new Node<>(e, null);
            }

            public Trie() {
                this(null);
            }

            @Override
            public String toString() {
                return toString(rootNode, 0);
            }

            private String toString(Node<E> node, int d) {
                StringBuilder string = new StringBuilder();

                if (d > 0) {
                    string.append("\n");
                }

                for (int i = 0; i < 4 * d; i++) {
                    string.append(" ");
                }

                string.append(node);

                for (Node<E> childNode : node.childNodeMap.values()) {
                    string.append(toString(childNode, d + 1));
                }

                return string.toString();
            }

            @SafeVarargs
            public final void add(E... e) {
                Node<E> node = rootNode;

                for (E ei : e) {
                    node.addChild(ei);

                    node = node.childNodeMap.get(ei);
                }
            }

            @SafeVarargs
            public final void remove(E... e) {
                Node<E> node = rootNode;

                for (E ei : e) {
                    if (!node.childNodeMap.containsKey(ei)) {
                        return;
                    }

                    node = node.childNodeMap.get(ei);
                }

                int i = e.length - 1;

                while (node != rootNode) {
                    node = node.parentNode;

                    node.removeChild(e[i]);

                    i--;
                }
            }

            @SafeVarargs
            public final boolean contains(E... e) {
                Node<E> node = rootNode;

                for (E ei : e) {
                    if (!node.childNodeMap.containsKey(ei)) {
                        return false;
                    }

                    node = node.childNodeMap.get(ei);
                }

                return true;
            }

            public static class Node<E> {

                public final E e;
                public final Node<E> parentNode;
                public final TreeMap<E, Node<E>> childNodeMap;

                public Node(E e, Node<E> parentNode) {
                    this.e = e;
                    this.parentNode = parentNode;

                    childNodeMap = new TreeMap<>();
                }

                @Override
                public String toString() {
                    return String.valueOf(e);
                }

                public void addChild(E e) {
                    if (!childNodeMap.containsKey(e)) {
                        childNodeMap.put(e, new Node<>(e, this));
                    }
                }

                public void removeChild(E e) {
                    if (childNodeMap.containsKey(e)) {
                        if (childNodeMap.get(e).childNodeMap.isEmpty()) {
                            childNodeMap.remove(e);
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
