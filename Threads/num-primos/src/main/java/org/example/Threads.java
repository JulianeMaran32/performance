package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Threads {

    public static void main(String[] args) {

        System.out.println("+---------+");
        System.out.println("| THREADS |");
        System.out.println("+---------+\n");

        var scanner = new Scanner(System.in);
        System.out.println("+-----------------------------+");
        System.out.println("| Informe o intervalo desejado |");
        System.out.println("+-----------------------------+");

        System.out.println("Início: ");
        int inicio = scanner.nextInt();

        System.out.println("Fim: ");
        int fim = scanner.nextInt();

        scanner.close();

        System.out.println("Intervalo: [" + inicio + ";" + fim + "]\n");

        List<Thread> threads = new ArrayList<>();

        for (int i = inicio; i <= fim; i++) {
            final int numero = i;
            var thread = new Thread(() -> {
                if (isPrimo(numero))
                    System.out.println("Primo: " + numero);
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    // verifica se o número é primo ou não
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
