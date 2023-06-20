package org.example.tempoExecucao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumPrimoComOitoThreads {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("+--------------------+");
        System.out.println("|   Números Primos   |");
        System.out.println("|   Oito Threads     |");
        System.out.println("+--------------------+\n");

        int[][] subIntervalos = {
                {1000000, 1031250},
                {1031251, 1062500},
                {1062501, 1093750},
                {1093751, 1125000},
                {1125001, 1156250},
                {1156251, 1187500},
                {1187501, 1218750},
                {1218751, 1250000},
                {2000000, 2031250},
                {2031251, 2062500},
                {2062501, 2093750},
                {2093751, 2125000},
                {2125001, 2156250},
                {2156251, 2187500},
                {2187501, 2218750},
                {2218751, 2250000},
                {2250001, 2281250},
                {2281251, 2312500},
                {2312501, 2343750},
                {2343751, 2375000},
                {2375001, 2406250},
                {2406251, 2437500},
                {2437501, 2468750},
                {2468751, 2500000}
        };

        Arrays.stream(subIntervalos)
                .map(subIntervalo -> "Sub-intervalo: " + subIntervalo[0] + "-" + subIntervalo[1])
                .forEachOrdered(System.out::println);

        System.out.print("\n");

        for (int[] subIntervalo : subIntervalos) {
            long startTime = System.currentTimeMillis();
            List<Thread> threadList = new ArrayList<>();

            for (int i = 0; i < 8; i++) {
                int start = subIntervalo[0] + i * ((subIntervalo[1] - subIntervalo[0] + 1) / 8);
                int end = subIntervalo[0] + (i + 1) * ((subIntervalo[1] - subIntervalo[0] + 1) / 8) - 1;

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
