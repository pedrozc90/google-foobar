package graphs;

// https://www.youtube.com/watch?v=M6cm8UeeziI&ab_channel=WilliamFiset
// https://www.youtube.com/watch?v=_SdF4KK_dyM&ab_channel=WilliamFiset
// https://github.com/williamfiset/Algorithms/blob/master/src/main/java/com/williamfiset/algorithms/graphtheory/networkflow/examples/DinicsExample.java

import java.util.*;

public class DinicAlgorithm {

    private static class Edge {

        protected int from, to;
        protected Edge residual;
        protected long flow;
        protected final long capacity;

        public Edge(final int from, final int to, final long capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
        }

        public boolean isResidual() {
            return (capacity == 0);
        }

        public long getRemainingCapacity() {
            return (capacity - flow);
        }

        public void augment(final long bottle_neck) {
            flow += bottle_neck;
            residual.flow -= bottle_neck;
        }

        public String toString(final int s, final int t) {
            final String u = (from == s) ? "s" : ((from == t) ? "t" : String.valueOf(from));
            final String v = (to == s) ? "s" : ((to == t) ? "t" : String.valueOf(to));
            return String.format("Edge: %s -> %s | flow: %3d | capacity: %3d | is_residual: %s", u, v, flow, capacity, isResidual());
        }
    }

    private static class Graph {

        static final long INF = Long.MAX_VALUE / 2;

        protected int n = 0;            // number of nodes
        protected int s = 0;            // source node
        protected int t = 0;            // sink node
        protected long max_flow = 0;    // sink node
        protected List<Edge>[] graph;

        public static Graph create(int[][] path) {
            final Graph graph = new Graph();
            for (int i = 0; i < path.length; i++) {
                for (int j = 0; j < path[i].length; j++) {
                    graph.addEdge(i, j, path[i][j]);
                }
            }
            return graph;
        }

        public void addEdge(final int from, final int to, final int capacity) {
            if (capacity == 0) return;

            final Edge e1 = new Edge(from, to, capacity);
            final Edge e2 = new Edge(to, from, 0);
            e1.residual = e2;
            e2.residual = e1;

            int min = Math.min(from, to);
            int max = Math.max(from, to);
            int size = max + 1;
            if (graph == null) {
                graph = new List[1];
            }
            if (graph.length < size) {
                graph = Arrays.copyOf(graph, size);
                n = size;
            }
            for (int i = min; i <= max; i++) {
                if (graph[i] == null) {
                    graph[i] = new ArrayList<Edge>();
                }
            }
            graph[from].add(e1);
            graph[to].add(e2);
        }

        private boolean bfs(final int[] level, final int s, final int t) {
            Arrays.fill(level, -1);
            PriorityQueue<Integer> queue = new PriorityQueue<>();
            queue.add(s);
            level[s] = 0;
            while (!queue.isEmpty()) {
                int n = queue.poll();
                for (Edge edge : graph[n]) {
                    long cap = edge.getRemainingCapacity();
                    if (cap > 0 && level[edge.to] == -1) {
                        level[edge.to] = level[n] + 1;
                        queue.add(edge.to);
                    }
                }
            }
            return level[t] != -1;
        }

        public long dfs(final int at, final int t, final int[] level, final int[] next, final long flow) {
            if (at == t) return flow;
            final int size = graph[at].size();
            for (; next[at] < size; next[at]++) {
                Edge edge = graph[at].get(next[at]);
                long cap = edge.getRemainingCapacity();
                if (cap > 0 && level[edge.to] == level[at] + 1) {
                    long bottle_neck = dfs(edge.to, t, level, next, Math.min(flow, cap));
                    if (bottle_neck > 0) {
                        edge.augment(bottle_neck);
                        return bottle_neck;
                    }
                }
            }
            return 0;
        }

        public long dinic(final int[] entrances, final int[] exits) {
            long max_flow = 0;
            for (int s : entrances) {
                for (int t : exits) {
                    long flow = dinic(s, t);
                    if (flow > max_flow) {
                        max_flow = flow;
                    }
                }
            }
            return max_flow;
        }

        public long dinic(final int s, final int t) {
            int[] level = new int[graph.length];
            int[] next = new int[graph.length];
            while (bfs(level, s, t)) {
                Arrays.fill(next, 0);
                for (long f = dfs(s, t, level, next, INF); f != 0; f = dfs(s, t, level, next, INF)) {
                    max_flow += f;
                }
            }

            return max_flow;
        }

    }

//    RESULT: 16
//    ELAPSED: 290834 ns
//    ELAPSED: 290 ms
//    RESULT: 6
//    ELAPSED: 8135 ns
//    ELAPSED: 8 ms

    public static void main(String[] args) {
        {
            int[] entrances = { 0, 1 };
            int[] exits = { 4, 5 };
            int[][] path = {
                { 0, 0, 4, 6, 0, 0 },
                { 0, 0, 5, 2, 0, 0 },
                { 0, 0, 0, 0, 4, 4 },
                { 0, 0, 0, 0, 6, 6 },
                { 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0 }
            };

            long start = System.nanoTime();

            final Graph graph = Graph.create(path);

            long max_flow = graph.dinic(entrances, exits);

            long end = System.nanoTime();

            System.out.printf("max flow: %d\n", max_flow);
            System.out.printf("elapsed: %d ns\n", (end - start));
            System.out.printf("elapsed: %d ms\n", (end - start) / 1000);
        }
        {
            int[] entrances = { 0 };
            int[] exits = { 3 };
            int[][] path = {
                { 0, 7, 0, 0 },
                { 0, 0, 6, 0 },
                { 0, 0, 0, 8 },
                { 9, 0, 0, 0 }
            };

            long start = System.nanoTime();

            final Graph graph = Graph.create(path);

            long max_flow = graph.dinic(entrances, exits);

            long end = System.nanoTime();

            System.out.printf("max flow: %d\n", max_flow);
            System.out.printf("elapsed: %d ns\n", (end - start));
            System.out.printf("elapsed: %d ms\n", (end - start)/1000);
        }
    }

}
