public boolean isPrimeSixKSqrt(int n) {
    compares = 1;

    if (n <= 1) {
        return false;
    }

    compares += 1;
    if (n <= 3) {
        return true;
    }

    compares += 2;
    if (n % 2 == 0 || n % 3 == 0) {
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
