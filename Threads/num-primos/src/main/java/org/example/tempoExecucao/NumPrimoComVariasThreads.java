package org.example.tempoExecucao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumPrimoComVariasThreads {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("+----------------------+");
        System.out.println("|  Números Primos      |");
        System.out.println("|  Múltiplas Threads   |");
        System.out.println("+----------------------+\n");

        int[][] subIntervalos = {
                {1000000, 1500000},
                {2000000, 2500000}
        };

        Arrays.stream(subIntervalos)
                .map(subIntervalo -> "Sub-intervalo: " + subIntervalo[0] + "-" + subIntervalo[1])
                .forEachOrdered(System.out::println);

        System.out.println();

        long startTime = System.currentTimeMillis();

        List<Thread> threadList = new ArrayList<>();

        for (int[] subIntervalo : subIntervalos) {
            var thread = new Thread(() -> {
                for (int i = subIntervalo[0]; i <= subIntervalo[1]; i++) {
                    if (isPrimo(i)) {
                        System.out.println("Número primo: " + i);
                    }
                }
            });
            threadList.add(thread);
            thread.start();
        }

        for (Thread thread : threadList) {
            thread.join();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("\nTempo de execução: " + (endTime - startTime) + " ms");
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
