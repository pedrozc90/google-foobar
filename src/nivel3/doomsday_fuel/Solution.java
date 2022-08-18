package nivel3.doomsday_fuel;


import java.util.Arrays;
import java.util.HashSet;

public class Solution {

    public static int[] solution(int[][] m) {
        int rows = m.length;
        int cols = m[0].length;

        if (rows < 2) {
            return new int[]{ 1, 1 };
        }

        final HashSet<Integer> absorbing = new HashSet<>();

        // sum the number of state changes.
        // if state changes are 0, its a terminal node.
        int[] sum = new int[rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum[i] += m[i][j];
            }
            if (sum[i] == 0) {
                absorbing.add(i);
            }
        }

        int p = absorbing.size();
        int n = rows - p;

        final double[][] r_sub = new double[n][p];
        final double[][] q_sub = new double[n][n];

        for (int i = 0, ir = 0, iq = 0; i < rows; i++) {
            if (absorbing.contains(i)) continue;
            for (int j = 0, jr = 0, jq = 0; j < cols; j++) {
                // sum[i] won't be zero, because if sum is 0 its a absorb node
                double v = (double) m[i][j] / sum[i];
                if (absorbing.contains(j)) {
                    r_sub[ir][jr++] = v;
                } else {
                    q_sub[iq][jq++] = v;
                }
            }
            ir++;
            iq++;
        }

        final double[][] identity = Matrix.identity(q_sub.length);
        final double[][] sub = Matrix.subtraction(identity, q_sub);
        final double[][] f_sub = Matrix.invert(sub);
        final double[][] fr_sub = Matrix.multiply(f_sub, r_sub);

        return fractionAns(fr_sub[0]);
    }

    public static int[] fractionAns(double[] m) {
        int[] ans = new int[m.length + 1];
        int[] denominators = new int[m.length];
        int[] numerators = new int[m.length];
        for (int i = 0; i < m.length; i++) {
            int[] fraction = convertDecimalToFraction(m[i]);
            numerators[i] = fraction[0];
            denominators[i] = fraction[1];
        }
        int lcm = (int) lcm_of_array_elements(denominators);
        for (int i = 0; i < m.length; i++) {
            ans[i] = numerators[i] * (lcm / denominators[i]);
        }
        ans[ans.length - 1] = lcm;
        return ans;
    }

    static private int[] convertDecimalToFraction(double x) {
        double tolerance = 1E-10;
        double h1 = 1;
        double h2 = 0;
        double k1 = 0;
        double k2 = 1;
        double b = x;
        do {
            double a = Math.floor(b);
            double aux = h1;
            h1 = a * h1 + h2;
            h2 = aux;
            aux = k1;
            k1 = a * k1 + k2;
            k2 = aux;
            b = 1 / (b - a);
        } while (Math.abs(x - h1 / k1) > x * tolerance);

        return new int[]{ (int) h1, (int) k1 };
    }

    public static long lcm_of_array_elements(int[] denominators) {
        int[] element_array = Arrays.copyOf(denominators, denominators.length);
        long lcm_of_array_elements = 1;
        int divisor = 2;

        while (true) {
            int counter = 0;
            boolean divisible = false;

            for (int i = 0; i < element_array.length; i++) {

                // lcm_of_array_elements (n1, n2, ... 0) = 0.
                // For negative number we convert into
                // positive and calculate lcm_of_array_elements.

                if (element_array[i] == 0) {
                    return 0;
                } else if (element_array[i] < 0) {
                    element_array[i] = element_array[i] * (-1);
                }
                if (element_array[i] == 1) {
                    counter++;
                }

                // Divide element_array by devisor if complete
                // division i.e. without remainder then replace
                // number with quotient; used for find next factor
                if (element_array[i] % divisor == 0) {
                    divisible = true;
                    element_array[i] = element_array[i] / divisor;
                }
            }

            // If divisor able to completely divide any number
            // from array multiply with lcm_of_array_elements
            // and store into lcm_of_array_elements and continue
            // to same divisor for next factor finding.
            // else increment divisor
            if (divisible) {
                lcm_of_array_elements = lcm_of_array_elements * divisor;
            } else {
                divisor++;
            }

            // Check if all element_array is 1 indicate
            // we found all factors and terminate while loop.
            if (counter == element_array.length) {
                return lcm_of_array_elements;
            }
        }
    }

    private static class Matrix {

        private static double[][] minor(double[][] matrix, int row, int column) {
            double[][] minor = new double[matrix.length - 1][matrix.length - 1];

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; i != row && j < matrix[i].length; j++) {
                    if (j != column) {
                        minor[i < row ? i : i - 1][j < column ? j : j - 1] = matrix[i][j];
                    }
                }
            }
            return minor;
        }

        private static double determinant(double[][] m) {
            if (m.length != m[0].length) {
                throw new IllegalStateException("matrix is not square.");
            }

            int rows = m.length;

            if (rows == 2) {
                return (m[0][0] * m[1][1]) - (m[0][1] * m[1][0]);
            }

            double det = 0;
            for (int i = 0; i < rows; i++) {
                det += Math.pow(-1, i) * m[0][i] * determinant(minor(m, 0, i));
            }
            return det;
        }

        private static double[][] multiply(double[][] a, double[][] b) {
            if (a[0].length != b.length)
                throw new IllegalStateException("invalid dimensions");

            double[][] matrix = new double[a.length][b[0].length];
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < b[0].length; j++) {
                    double sum = 0;
                    for (int k = 0; k < a[i].length; k++)
                        sum += a[i][k] * b[k][j];
                    matrix[i][j] = sum;
                }
            }

            return matrix;
        }

        private static double[][] subtraction(double[][] a, double[][] b) {
            if (a.length != b.length || a[0].length != b[0].length)
                throw new IllegalStateException("invalid dimensions");
            final double[][] sub = new double[a.length][a[0].length];
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a[i].length; j++) {
                    sub[i][j] = a[i][j] - b[i][j];
                }
            }
            return sub;
        }

        public static double[][] invert(double a[][]) {
            int n = a.length;
            int[] index = new int[n];
            double[][] x = new double[n][n];
            double[][] b = identity(n);

            // Transform the matrix into an upper triangle
            gaussian(a, index);

            // Update the matrix b[i][j] with the ratios stored
            for (int i = 0; i < n - 1; ++i) {
                for (int j = i + 1; j < n; ++j) {
                    for (int k = 0; k < n; ++k) {
                        b[index[j]][k] -= a[index[j]][i] * b[index[i]][k];
                    }
                }
            }

            // Perform backward substitutions
            for (int i = 0; i < n; ++i) {
                x[n - 1][i] = b[index[n - 1]][i] / a[index[n - 1]][n - 1];
                for (int j = n - 2; j >= 0; --j) {
                    x[j][i] = b[index[j]][i];
                    for (int k = j + 1; k < n; ++k) {
                        x[j][i] -= a[index[j]][k] * x[k][i];
                    }
                    x[j][i] /= a[index[j]][j];
                }
            }
            return x;
        }

        public static void gaussian(double[][] a, int[] index) {
            int n = index.length;
            double[] c = new double[n];

            // Initialize the index
            for (int i = 0; i < n; ++i) {
                index[i] = i;
            }

            // Find the rescaling factors, one from each row
            for (int i = 0; i < n; ++i) {
                double c1 = 0;
                for (int j = 0; j < n; ++j) {
                    double c0 = Math.abs(a[i][j]);
                    if (c0 > c1) c1 = c0;
                }
                c[i] = c1;
            }

            // Search the pivoting element from each column
            int k = 0;
            for (int j = 0; j < n - 1; ++j) {
                double pi1 = 0;
                for (int i = j; i < n; ++i) {
                    double pi0 = Math.abs(a[index[i]][j]);
                    pi0 /= c[index[i]];
                    if (pi0 > pi1) {
                        pi1 = pi0;
                        k = i;
                    }
                }

                // Interchange rows according to the pivoting order
                int itmp = index[j];
                index[j] = index[k];
                index[k] = itmp;
                for (int i = j + 1; i < n; ++i) {
                    double pj = a[index[i]][j] / a[index[j]][j];

                    // Record pivoting ratios below the diagonal
                    a[index[i]][j] = pj;

                    // Modify other elements accordingly
                    for (int l = j + 1; l < n; ++l)
                        a[index[i]][l] -= pj * a[index[j]][l];
                }
            }
        }

        private static double[][] identity(int size) {
            final double[][] array = new double[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (i == j) {
                        array[i][j] = 1;
                    }
                }
            }
            return array;
        }

        private static int[][] transpose(int[][] m) {
            final int rows = m.length;
            final int cols = m[0].length;
            int[][] t = new int[cols][rows];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    t[j][i] = m[i][j];
                }
            }
            return t;
        }
    }

    public static void main(String[] args) {
        {
            int[][] array = {
                { 0, 2, 1, 0, 0 },
                { 0, 0, 0, 3, 4 },
                { 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0 }
            };

            int[] result = solution(array);

            int[] answer = { 7, 6, 8, 21 };

            assert (result.length == answer.length);
            for (int i = 0; i < result.length; i++) {
                assert (result[i] == answer[i]);
            }
        }
        {
            int[][] array = {
                { 0, 1, 0, 0, 0, 1 },
                { 4, 0, 0, 3, 2, 0 },
                { 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0 }
            };

            int[] result = solution(array);

            int[] answer = { 0, 3, 2, 9, 14 };

            assert (result.length == answer.length);
            for (int i = 0; i < result.length; i++) {
                assert (result[i] == answer[i]);
            }
        }

    }

}
