package org.example.utils;

/**
 * Classe responsável por verificar se é ou não número primo
 */
public class NumPrimo {

    public NumPrimo() {
    }

    public static boolean isPrimo(int num) {
        if (num <= 1)
            return false;

        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0)
                return false;
        }

        return true;
    }

}
