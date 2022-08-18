package nivel4.escape_pods;

import graphs.DinicAlgorithm;

import java.util.*;
import java.util.stream.IntStream;

public class Solution4 {

    private static final int INF = Integer.MAX_VALUE;

    public static int solution(int[] entrances, int[] exits, int[][] path) {
        int[][][] aug = new int[path.length][path[0].length][2];
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path.length; j++) {
                aug[i][j] = new int[]{ 0, path[i][j] };
            }
        }
        for (int i : exits) {
            aug[i][path.length - 1][1] = INF;
        }

        return -1;
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
