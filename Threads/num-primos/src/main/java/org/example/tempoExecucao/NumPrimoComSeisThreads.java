package org.example.tempoExecucao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumPrimoComSeisThreads {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("+--------------------+");
        System.out.println("|   Números Primos   |");
        System.out.println("|   Seis Threads     |");
        System.out.println("+--------------------+\n");

        int[][] subIntervalos = {
                {1000000, 1083333},
                {1083334, 1166666},
                {1166667, 1250000},
                {1250001, 1333333},
                {1333334, 1416666},
                {1416667, 1500000},
                {2000000, 2083333},
                {2083334, 2166666},
                {2166667, 2250000},
                {2250001, 2333333},
                {2333334, 2416666},
                {2416667, 2500000}
        };

        Arrays.stream(subIntervalos)
                .map(subIntervalo -> "Sub-intervalo: " + subIntervalo[0] + "-" + subIntervalo[1])
                .forEachOrdered(System.out::println);

        System.out.print("\n");

        for (int[] subIntervalo : subIntervalos) {
            long startTime = System.currentTimeMillis();
            List<Thread> threadList = new ArrayList<>();

            for (int i = 0; i < 6; i++) {
                int start = subIntervalo[0] + i * ((subIntervalo[1] - subIntervalo[0] + 1) / 6);
                int end = subIntervalo[0] + (i + 1) * ((subIntervalo[1] - subIntervalo[0] + 1) / 6) - 1;
                var thread = new Thread(() -> {
                    for (int j = start; j <= end; j++) {
                        if (isPrimo(j)) {
                            System.out.println("Número primo: " + j);
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
