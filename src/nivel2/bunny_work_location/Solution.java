package nivel2.bunny_work_location;

public class Solution {

    public static String solution(long x, long y) {
        long r = calculate(x, y);
        return Long.toString(r);
    }

    public static long calculate(long x, long y) {
        if ((x < 1 || y < 1) && (x > 100_000 || y > 100_000)) return -1L;
        if (x == 1) {
            return 1 + (((y * y) - y) / 2);
        } else if (y == 1) {
            return (x * (x + 1)) / 2;
        }
        long v = calculate(1, x + y - 1);
        return v + (x - 1);
    }

    public static void main(String[] args) {
        {
            int x = 1;
            int y = 1;

            final String id = solution(x, y);

            assert (id.equals("1"));

            System.out.printf("(%d, %d) = %s\n", x, y, id);
        }
        {
            int x = 3;
            int y = 2;

            final String id = solution(x, y);

            assert (id.equals("9"));

            System.out.printf("(%d, %d) = %s\n", x, y, id);
        }
        {
            int x = 5;
            int y = 10;

            final String id = solution(x, y);

            assert (id.equals("96"));

            System.out.printf("(%d, %d) = %s\n", x, y, id);
        }
    }

}
