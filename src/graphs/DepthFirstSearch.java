package graphs;

import java.util.Arrays;
import java.util.LinkedList;

public class DepthFirstSearch {

    // depth first search
    private static void dfs(int[][] graph, int v) {
        boolean[] visited = new boolean[graph.length];

        System.out.printf("[%d] ", v);
        dfs(graph, v, visited);
        System.out.printf("\b\b\b\b\n");
    }

    private static void dfs(int[][] graph, int v, boolean[] visited) {
        visited[v] = true;

        System.out.printf("%d -> ", v);

        for (int j = 0; j < graph[v].length; j++) {
            if (graph[v][j] == 0 || v == j) continue;
            if (!visited[j]) {
                dfs(graph, j, visited);
            }
        }
    }

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

        protected void dfs(int v, boolean[] visited) {
            visited[v] = true;
            System.out.printf("%d ", v);

            for (int n : adj[v]) {
                if (!visited[n]) {
                    dfs(n, visited);
                }
            }
        }

        protected void dfs(int v) {
            boolean[] visited = new boolean[size];
            dfs(v, visited);
        }

    }

    public static void main(String[] args) {
        int[][] graph = {
            /*0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 */
            { 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0 },  // 0
            { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // 1
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0 },  // 2
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0 },  // 3
            { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // 4
            { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },  // 5
            { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },  // 6
            { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },  // 7
            { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },  // 8
            { 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },  // 9
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },  // 10
            { 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // 11
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // 12
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },  // 13
            { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0 },  // 14
            { 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0 },  // 15
            { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // 16
            { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  // 17
        };

         for (int i = 0; i < graph.length; i++) {
            dfs(graph, i);
         }

        final Graph g = new Graph();
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                if (graph[i][j] != 0) {
                    g.addEdge(i, j);
                }
            }
        }

        g.dfs(0);

        // building graph
        int [][] adj = new int[0][0];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                if (graph[i][j] == 0) continue;
                int size = i + 1;
                if (adj.length < size) {
                    adj = Arrays.copyOf(adj, size);
                }
                if (adj[i] == null) {
                    adj[i] = new int[1];
                } else {
                    adj[i] = Arrays.copyOf(adj[i], adj[i].length + 1);
                }
                adj[i][adj[i].length - 1] = j;
            }
        }

    }

}
