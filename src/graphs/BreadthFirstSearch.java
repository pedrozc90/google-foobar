package graphs;

import java.util.Arrays;
import java.util.LinkedList;

public class BreadthFirstSearch {

    private static class Graph {

        protected int size = 0;
        protected LinkedList<Integer>[] adj;

        public void addEdge(int v, int w) {
            int max = v + 1;
            if (adj == null) {
                adj = new LinkedList[1];
            }
            if (adj.length < max) {
                adj = Arrays.copyOf(adj, max);
                this.size = max;
            }
            if (adj[v] == null) {
                adj[v] = new LinkedList<>();
            }
            adj[v].add(w);
        }

        protected int[] bfs(int s, int t) {
            final LinkedList<Integer> queue = new LinkedList<>();
            queue.add(s);

            boolean[] visited = new boolean[size];
            visited[s] = true;

            int[] prev = new int[size];
            Arrays.fill(prev, -1);

            while (!queue.isEmpty()) {
                int x = queue.pop();

                for (int n : adj[x]) {
                    if (!visited[n]) {
                        visited[n] = true;
                        queue.add(n);
                        prev[n] = x;
                    }
                }
            }

            int[] path = new int[0];
            for (int at = t; at >= 0; at = prev[at]) {
                path = Arrays.copyOf(path, path.length + 1);
                path[path.length - 1] = at;
            }

            for (int i = 0, j = path.length - 1; i < j; i++, j--) {
                int tmp = path[i];
                path[i] = path[j];
                path[j] = tmp;
            }

            return path;
        }

    }

    public static void main(String[] args) {
        int[][] graph = {
            /*0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 */
            { 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0 },  // 0
            { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0 },  // 1
            { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },  // 2
            { 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // 3
            { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // 4
            { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // 5
            { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // 6
            { 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },  // 7
            { 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0 },  // 8
            { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0 },  // 9
            { 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },  // 10
            { 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // 11
            { 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // 12
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // 13
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // 14
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // 15
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // 16
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // 17
        };

        final Graph g = new Graph();
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                if (graph[i][j] != 0) {
                    g.addEdge(i, j);
                }
            }
        }

        int[] path = g.bfs(0, 4);
    }

}
