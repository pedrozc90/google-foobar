package nivel2.en_route_salute;

public class Solution {

    private static final char EMPLOYEE_RIGHT = '>';
    private static final char EMPLOYEE_LEFT = '<';
    private static final char EMPTY_SPACE = '-';

    public static int solution(final String s) {
        int salutes = 0;
        int next = 0;
        int i = 0;
        while (i < s.length()) {
            if (s.charAt(i) == EMPLOYEE_RIGHT) {
                next = 0;
                for (int j = i + 1; j < s.length(); j++) {
                    int c = s.charAt(j);
                    if (c == EMPLOYEE_LEFT) {
                        salutes += 2;
                    } else if (c == EMPLOYEE_RIGHT && next == 0) {
                        next = j;
                    }
                }
            }
            if (next > 0) {
                i = next;
            } else {
                i++;
            }
        }
        return salutes;
    }

    public static void main(String[] args) {
        {
            final String hallway = "--->-><-><-->-";

            final int result = solution(hallway);

            assert (result == 10);

            System.out.printf("HALLWAY: %s\n", hallway);
            System.out.printf("SALUTES: %d\n", result);
        }
        {
            final String hallway = ">----<";
            
            final int result = solution(hallway);
            
            assert (result == 2);
            
            System.out.printf("HALLWAY: %s\n", hallway);
            System.out.printf("SALUTES: %d\n", result);
        }
        {
            final String hallway = "<<>><";
            
            final int result = solution(hallway);
            
            assert (result == 4);
            
            System.out.printf("HALLWAY: %s\n", hallway);
            System.out.printf("SALUTES: %d\n", result);
        }
    }

}
