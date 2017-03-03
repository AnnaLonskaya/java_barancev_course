package ua.annalonskaya.sandbox;

public class Primes {

    public static boolean isPrime (int n) {
        for (int i = 2; i < n; i++) {  // i++ инкремент (увеличение числа на единицу)
            if (n % i == 0) {    // % - получение остатка от деления (если n делится на i без остатка)
                return false;   // число не простое
            }
        }
        return true;
    }

    public static boolean isPrimeWhile (int n) {
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
