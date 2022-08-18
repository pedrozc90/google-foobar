package nivel4.escape_pods;

import java.util.PriorityQueue;

public class Solution2 {

    public static int solution(int[] entrances, int[] exits, int[][] path) {
        final PriorityQueue<Node> queue = new PriorityQueue<>();

        for (int i : entrances) {
            for (int j = 0; j < path[i].length; j++) {
                if (path[i][j] == 0) continue;
                queue.add(new Node(i, j, path[i][j]));
            }
        }

        int sum = 0;
        while (!queue.isEmpty()) {
            final Node top = queue.poll();

            if (indexOf(exits, top.next_row) != -1) {
                sum += top.bunnies;
                continue;
            }

            int i = top.next_row;
            for (int j = 0; j < path[i].length; j++) {
                if (path[i][j] == 0) continue;

                // int v = path[i][j];
                // int u = top.bunnies;
                //top.bunnies = Math.max(v - u, 0);

                queue.add(new Node(i, j, Math.min(path[i][j], top.bunnies)));

                break;
            }
        }

        return sum;
    }

    private static int indexOf(int[] a, int v) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == v) return i;
        }
        return -1;
    }

    private static class Node implements Comparable<Node> {

        int prev_row;
        int next_row;
        int bunnies;

        public Node(final int prev_row, final int next_row, final int bunnies) {
            this.prev_row = prev_row;
            this.next_row = next_row;
            this.bunnies = bunnies;
        }

        @Override
        public int compareTo(final Node o) {
            return (next_row - o.next_row);
        }

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
                Elapsed: 576503 ns
                Elapsed: 0.576503 ms
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
                Elapsed: 5069 ns
                Elapsed: 0.005069 ms
             */
            test(entrances, exits, path, 6);
        }
    }

}
