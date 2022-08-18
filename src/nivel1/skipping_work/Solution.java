package nivel1.skipping_work;

public class Solution {

    public static int solution(int[] x, int[] y) throws Exception {
        if (x.length == y.length) {
            throw new IllegalArgumentException("Invalid arrays. x[] and y[] can't have the same number of elements.");
        } else if (Math.abs(x.length - y.length) != 1) {
            throw new Exception(String.format("Invalid arrays, x(%d) != y(%d).", x.length, y.length));
        }
        int sum_x = sum(x);
        int sum_y = sum(y);
        if (y.length > x.length) {
            return sum_y - sum_x;
        }
        return sum_x - sum_y;
    }

    private static int sum(final int[] array) {
        int s = 0;
        for (int v : array) {
            s += v;
        }
        return s;
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
