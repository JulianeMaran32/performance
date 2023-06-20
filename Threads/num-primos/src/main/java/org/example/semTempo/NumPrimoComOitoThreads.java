package org.example.semTempo;

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
                {1000000, 1062500},
                {1062501, 1125000},
                {1125001, 1187500},
                {1187501, 1250000},
                {1250001, 1312500},
                {1312501, 1375000},
                {1375001, 1437500},
                {1437501, 1500000},
                {2000000, 2062500},
                {2062501, 2125000},
                {2125001, 2187500},
                {2187501, 2250000},
                {2250001, 2312500},
                {2312501, 2375000},
                {2375001, 2437500},
                {2437501, 2500000}
        };

        Arrays.stream(subIntervalos)
                .map(subIntervalo -> "Sub-intervalo: " + subIntervalo[0] + "-" + subIntervalo[1])
                .forEach(System.out::println);

        System.out.print("\n");

        List<Thread> threadList = new ArrayList<>();

        for (int[] subIntervalo : subIntervalos) {
            Thread thread = new Thread(() -> {
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
