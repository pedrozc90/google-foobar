package nivel3.find_the_access_codes;

import java.util.Arrays;

public class Solution2 {

    // lucky triple is a tuple ( x, y, z ) where x divides y and y divides z.
    // count the number of lucky triple of (lu, lj, lk), where i < j < k
    // if there is not access codes, no triples are found, so return 0.
    public static int solution(int[] l) {
        int n = 0;
        int[][] sub = new int[0][3];
        for (int i = 0; i < l.length; i++) {
            if (l[i] == 0) {
                sub = Arrays.copyOf(sub, n + 1);
                sub[n++] = new int[0];
            } else {
                int[] tmp = new int[0];
                for (int j = i + 1, k = 0; j < l.length; j++) {
                    if (l[j] % l[i] == 0) {
                        tmp = Arrays.copyOf(tmp, k + 1);
                        tmp[k++] = l[j];
                    }
                }
                sub = Arrays.copyOf(sub, n + 1);
                sub[n++] = tmp;
            }
        }

        int count = 0;
        for (int i = 0; i < sub.length; i++) {
            l[i] = 0;
            int[] tmp = Arrays.copyOf(l, l.length);
            for (int u : sub[i]) {
                int index = -1;
                for (int k = 0; k < tmp.length; k++) {
                    if (tmp[k] == u) {
                        index = k;
                        break;
                    }
                }
                if (index == -1) break;
                count += sub[index].length;
                tmp[index] = 0;
            }
        }

        return count;
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
    }

}
