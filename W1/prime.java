class prime {

    static int compares = 0;

    static boolean isPrimeSixKSqrt(int n) {

        compares = 1;

        if (n <= 1) {
            return false;
        }

        if (n <= 3) {
            compares += 1;
            return true;
        }


        if (n % 2 == 0 || n % 3 == 0) {
            compares += 2;
            return false;
        }

        for (int i = 5; i * i <= n; i += 6) {
            compares += 2;

            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {

        isPrimeSixKSqrt(19);
        System.out.println(compares);
    }
}
