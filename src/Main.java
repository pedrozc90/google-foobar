public class  Main {

    public static void main(String[] args) {
        double value = .444444444;
        int[] fraction = convertDecimalToFraction(value);
        System.out.printf("v: %f -> f: %d/%d\n", value, fraction[0], fraction[1]);

        int[] f = decimalToFraction(value);
        System.out.printf("v: %f -> f: %d/%d\n", value, f[0], f[1]);
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

    // Recursive function to
    // return GCD of a and b
    static long gcd(long a, long b)
    {
        if (a == 0)
            return b;
        else if (b == 0)
            return a;
        if (a < b)
            return gcd(a, b % a);
        else
            return gcd(b, a % b);
    }

    // Function to convert decimal to fraction
    static int[] decimalToFraction(double number) {

        // Fetch integral value of the decimal
        double intVal = Math.floor(number);

        // Fetch fractional part of the decimal
        double fVal = number - intVal;

        // Consider precision value to
        // convert fractional part to
        // integral equivalent
        final long pVal = 1000000000;

        // Calculate GCD of integral
        // equivalent of fractional
        // part and precision value
        long gcdVal = gcd(Math.round(
            fVal * pVal), pVal);

        // Calculate num and deno
        long num = Math.round(fVal * pVal) / gcdVal;
        long deno = pVal / gcdVal;

        // Print the fraction
        return new int[]{ (int) ((intVal * deno) + num), (int) deno };
    }

}
