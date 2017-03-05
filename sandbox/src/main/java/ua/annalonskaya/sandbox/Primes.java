package ua.annalonskaya.sandbox;

public class Primes {

  public static boolean isPrime(int n) {   // int - 32-x битные числа
    for (int i = 2; i < n; i++) {  // i++ инкремент (увеличение числа на единицу)
      if (n % i == 0) {    // % - получение остатка от деления (если n делится на i без остатка)
        return false;   // число не простое
      }
    }
    return true;
  }

  public static boolean isPrimeFast(int n) {
    int m = (int) Math.sqrt(n);
    for (int i = 2; i < m / 2; i++) {
      if (n % i == 0) {
        return false;
      }
    }
    return true;
  }

  public static boolean isPrimeWhile(int n) {
    int i = 2;
    while (i < n && n % i != 0) {    // && - "и"
      i++;
    }
    return i == n;
  }

  public static boolean isPrime(long n) {   // long - 64-x битные числа
    for (long i = 2; i < n; i++) {
      if (n % i == 0) {
        return false;
      }
    }
    return true;
  }

}
