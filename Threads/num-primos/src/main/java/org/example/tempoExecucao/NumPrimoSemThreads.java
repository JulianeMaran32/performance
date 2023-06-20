package org.example.tempoExecucao;

import java.util.Arrays;

/**
 * @author juliane.maran
 *
 * Imprime os números primos dentro de determinado intervalo
 */
public class NumPrimoSemThreads {

    public static void main(String[] args) {

        System.out.println("+--------------------+");
        System.out.println("|   Números Primos   |");
        System.out.println("|   Sem Threads      |");
        System.out.println("+--------------------+\n");

        int[][] subIntervalos = {
                {1000000, 1500000},
                {2000000, 2500000}
        };

        Arrays.stream(subIntervalos)
                .map(subIntervalo -> "Sub-intervalo: " + subIntervalo[0] + "-" + subIntervalo[1])
                .forEach(System.out::println);

        System.out.print("\n");

        for (int[] subIntervalo : subIntervalos) {
            long startTime = System.currentTimeMillis();
            for (int i = subIntervalo[0]; i <= subIntervalo[1]; i++) {
                if (isPrimo(i)) {
                    System.out.println("Número primo: " + i);
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Tempo de execução: " + (endTime - startTime) + " ms\n");
        }
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