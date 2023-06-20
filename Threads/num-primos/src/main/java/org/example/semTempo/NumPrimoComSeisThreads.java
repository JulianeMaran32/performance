package org.example.semTempo;

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

        for (int[] subIntervalo : subIntervalos)
            System.out.println("Sub-intervalo: " + subIntervalo[0] + "-" + subIntervalo[1]);

        System.out.print("\n");

        List<Thread> threadList = new ArrayList<>();

        Arrays.stream(subIntervalos).map(subIntervalo -> new Thread(() -> {
            for (int i = subIntervalo[0]; i <= subIntervalo[1]; i++) {
                if (isPrimo(i)) {
                    System.out.println("Número primo: " + i);
                }
            }
        })).forEach(thread -> {
            threadList.add(thread);
            thread.start();
        });

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
