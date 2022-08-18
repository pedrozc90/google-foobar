package nivel4.escape_pods;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

public class Solution {

    private static final int INF = Integer.MAX_VALUE;

    private static int[] bfs(final int[][] residual_network) {
        return bfs(residual_network, 0);
    }

    private static int[] bfs(final int[][] residual_network, final int s) {
        // find a path from s to t that every (u, v) in p satisfies c_f(u, v) > 0
        int[] parents = new int[residual_network.length];
        Arrays.fill(parents, -1);

        final Queue<Integer> queue = new ArrayDeque<>();
        queue.add(s);

        while (!queue.isEmpty() && parents[parents.length - 1] == -1) {
            int u = queue.remove();
            for (int v = 0; v < parents.length; v++) {
                if (residual_network[u][v] > 0 && parents[v] == -1) {
                    queue.add(v);
                    parents[v] = u;
                }
            }
        }

        final List<Integer> path = new ArrayList<>();
        // u = parents[parents.length - 1];
        // while (u != 0) {
        //     if (u == -1) return null;
        //     path.add(u);
        //     u = parents[u];
        // }
        for (int at = parents[parents.length - 1]; at >= 0; at = parents[at]) {
            if (at == 0) break;
            path.add(at);
        }

        if (path.size() == 0) return null;

        return IntStream.range(0, path.size())
            .map(i -> path.get(path.size() - i - 1))
            .toArray();
    }

    public static int[][] transform(int[] entrances, int[] exits, int[][] path) {
        int length = path.length;
        int new_length = length + 2;
        int[][] residual_path = new int[new_length][new_length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                residual_path[i + 1][j + 1] = path[i][j];
            }
        }
        for (int e : entrances) {
            residual_path[0][e + 1] = INF;
        }
        for (int e : exits) {
            residual_path[e + 1][new_length - 1] = INF;
        }
        return residual_path;
    }

    public static int solution(int[] entrances, int[] exits, int[][] path) {
        int[][] residual_path = transform(entrances, exits, path);

        int max_flow = 0;
        int[] route;
        while ((route = bfs(residual_path)) != null) {
            int residual_capacity = INF;
            int u = 0;
            for (int v : route) {
                residual_capacity = Math.min(residual_capacity, residual_path[u][v]);
                u = v;
            }
            max_flow += residual_capacity;
            u = 0;
            for (int v : route) {
                residual_path[u][v] -= residual_capacity;
                residual_path[v][u] += residual_capacity;
                u = v;
            }
        }

        return max_flow;
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
                Elapsed: 35637502 ns
                Elapsed: 35.637502 ms
             */
            run_solution(entrances, exits, path, 16);
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
                Elapsed: 35267 ns
                Elapsed: 0.035267 ms
             */
            run_solution(entrances, exits, path, 6);
        }
    }

    private static void run_solution(int[] entrances, int[] exists, int[][] path, int expected) {
        final long start = System.nanoTime();

        final int result = solution(entrances, exists, path);

        assert (result == expected);

        final long elapsed = (System.nanoTime() - start);

        System.out.printf("Result: %d\n", result);
        System.out.printf("Expected: %d\n", expected);
        System.out.printf("Elapsed: %d ns\n", elapsed);
        System.out.printf("Elapsed: %f ms\n", ((double) elapsed / 1_000_000));
        System.out.println("--------------------------------------------------");
    }

}
