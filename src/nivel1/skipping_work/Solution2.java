package nivel1.skipping_work;

import java.util.Arrays;

public class Solution2 {

    public static int solution(int[] x, int[] y) {
        if (x.length == y.length) {
            throw new IllegalArgumentException("Invalud input. x[] and y[] can't have the same number of elements.");
        }

        Arrays.sort(x);
        Arrays.sort(y);

        final int size = Math.min(x.length, y.length);

        for (int i = 0; i < size; i++) {
            if (x[i] != y[i]) {
                return (y.length > x.length) ? y[i] : x[i];
            }
        }
        if (x.length > size) return x[size - 1];
        return y[size];
    }

    public static void main(String[] args) {
        {
            int[] x = { 13, 5, 6, 2, 5 };
            int[] y = { 5, 2, 5, 13 };

            run_solution(x, y, 6);
        }
        {
            int[] x = { 14, 27, 1, 4, 2, 50, 3, 1 };
            int[] y = { 2, 4, -4, 3, 1, 1, 14, 27, 50 };

            run_solution(x, y, -4);
        }
        {
            int[] x = { 2, 4, -4, 3, 1, 1, 14, 27, 50 };
            int[] y = { 14, 27, 1, 4, 2, 50, 3, 1 };

            run_solution(x, y, -4);
        }
        {
            int[] x = { 3, 2, 1 };
            int[] y = { 3, 2, 4, 1 };

            run_solution(x, y, -4);
        }
    }

    public static void run_solution(int[] x, int[] y, int expected) {
        try {
            final long start = System.nanoTime();

            final int result = solution(x, y);

            assert (result == expected);

            final long elapsed = (System.nanoTime() - start);

            System.out.printf("Result: %d\n", result);
            System.out.printf("Expected: %d\n", expected);
            System.out.printf("Elapsed: %d ns\n", elapsed);
            System.out.printf("Elapsed: %f ms\n", ((double) elapsed / 1_000_000));
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
