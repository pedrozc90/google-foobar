package nivel4.escape_pods;

import java.util.LinkedList;

public class Solution3 {

    public static boolean bfs(int[][] graph, int s, int t, int[] parent) {
        boolean[] visited = new boolean[graph.length];

        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < graph.length; v++) {
                if (!visited[v] && graph[u][v] > 0) {
                    if (v == t) {
                        parent[v] = u;
                        return true;
                    }
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return false;
    }

    public static int ford_fulkerson(int[][] grapth, int s, int t) {
        int u, v;

        int[][] residual = new int[grapth.length][grapth[0].length];
        for (int i = 0; i < grapth.length; i++) {
            System.arraycopy(grapth[i], 0, residual[i], 0, grapth[0].length);
        }

        int[] parent = new int[grapth.length];

        int max_flow = 0;

        while (bfs(residual, s, t, parent)) {
            int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path_flow = Math.min(path_flow, residual[u][v]);
            }

            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                residual[u][v] -= path_flow;
                residual[v][u] += path_flow;
            }

            max_flow += path_flow;
        }

        return max_flow;
    }

    public static int solution(int[] entrances, int[] exits, int[][] path) {
        int total = 0;
        for (int i = 0; i < entrances.length; i++) {
            total += ford_fulkerson(path, entrances[i], exits   [i]);
        }
        return total;
    }

    private static void test(int[] entrances, int[] exits, int[][] path, int expected) {
        final long start = System.nanoTime();

        final int result = solution(entrances, exits, path);

        final long elapsed = (System.nanoTime() - start);

        assert (result == expected);

        System.out.printf("Result: %d\n", result);
        System.out.printf("Expected: %d\n", expected);
        System.out.printf("Elapsed: %d ns\n", elapsed);
        System.out.printf("Elapsed: %f ms\n", ((double) elapsed / 1_000_000));
        System.out.println("--------------------------------------------------");
    }

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

            /*
                Result: 16
                Expected: 16
                Elapsed: 419277 ns
                Elapsed: 0.419277 ms
             */
            test(entrances, exits, path, 16);
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

            /*
                Result: 6
                Expected: 6
                Elapsed: 9317 ns
                Elapsed: 0.009317 ms
             */
            test(entrances, exits, path, 6);
        }
    }

}
