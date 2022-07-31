package nivel1.skipping_work;

public class Solution {

    public static int solution(int[] x, int[] y) {
        int sum_x = sum(x);
        int sum_y = sum(y);
        if (y.length > x.length) {
            return sum_y - sum_x;
        }
        return sum_x - sum_y;
    }

    private static int sum(int[] array) {
        int s = 0;
        for (int x : array) {
            s += x;
        }
        return s;
    }

    public static void main(String[] args) {
        {
            int[] x = { 13, 5, 6, 2, 5 };
            int[] y = { 5, 2, 5, 13 };

            int result = solution(x, y);

            assert (result == 6);

            System.out.printf("Result: %d\n", result);
        }

        {
            int[] x = { 14, 27, 1, 4, 2, 50, 3, 1 };
            int[] y = { 2, 4, -4, 3, 1, 1, 14, 27, 50 };

            int result = solution(x, y);

            assert (result == -4);

            System.out.printf("Result: %d\n", result);
        }

        {
            int[] x = { 2, 4, -4, 3, 1, 1, 14, 27, 50 };
            int[] y = { 14, 27, 1, 4, 2, 50, 3, 1 };

            int result = solution(x, y);

            assert (result == -4);

            System.out.printf("Result: %d\n", result);
        }

        {
            int[] x = { 3, 2, 1 };
            int[] y = { 3, 2, 4, 1 };

            int result = solution(x, y);

            assert (result == -4);

            System.out.printf("Result: %d\n", result);
        }
    }

}
