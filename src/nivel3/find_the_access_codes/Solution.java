package nivel3.find_the_access_codes;

public class Solution {

    // lucky triple is a tuple ( x, y, z ) where x divides y and y divides z.
    // count the number of lucky triple of (lu, lj, lk), where i < j < k
    // if there is not access codes, no triples are found, so return 0.
    public static int solution(int[] l) {
        int n = 0;
        // int[][] tuples = new int[0][3];
        for (int i = 0; i < l.length; i++) {
            if (l[i] == 0) continue;
            for (int j = i + 1; j < l.length; j++) {
                // skip if l[i] do not divide l[j]
                if (l[j] == 0 || l[j] % l[i] != 0) continue;
                for (int k = j + 1; k < l.length; k++) {
                    if (l[k] % l[j] == 0) {
                        // register tuples
                        // tuples = Arrays.copyOf(tuples, n + 1);
                        // tuples[n++] = new int[]{ l[i], l[j], l[k] };
                        n++;
                    }
                }
            }
        }
        return n;
    }

    private static void test(int[] list, int expected) {
        long start = System.nanoTime();

        int result = solution(list);

        long end = System.nanoTime();

        assert (result == expected);

        System.out.printf("RESULT: %d\n", result);
        System.out.println("ELAPSED: " + (end - start) + "ms");
    }

    public static void main(String[] args) {
        test(new int[]{ 1, 2, 3, 4, 5, 6 }, 3);
        test(new int[]{ 1, 1, 1 }, 1);
        test(new int[]{ 1, 1, 1, 1 }, 2);
        test(new int[]{ 1, 2, 0, 4 }, 2);
    }

}
