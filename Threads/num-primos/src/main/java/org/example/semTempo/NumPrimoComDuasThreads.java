package org.example.semTempo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author juliane.maran
 * <p>
 * Criar 2 threads: Uma para primeira metade (começo até metade) e outra para (metade +1 até final).
 */
public class NumPrimoComDuasThreads {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("+--------------------+");
        System.out.println("|   Números Primos   |");
        System.out.println("|   Duas Threads     |");
        System.out.println("+--------------------+\n");

        int[][] subIntervalos = {
                {1000000, 1250000},
                {1250001, 1500000},
                {2000000, 2250000},
                {2250001, 2500000}
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
