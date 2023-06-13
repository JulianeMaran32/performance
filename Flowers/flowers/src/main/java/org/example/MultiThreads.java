package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MultiThreads {

    public static final String ARQUIVO_ORIGEM = "./resources/many-flowers.jpg";
    public static final String ARQUIVO_DESTINO = "./out/many-flowers-multithreads.jpg";

    public static void main(String[] args) throws IOException {

        BufferedImage imgOrigem = ImageIO.read(new File(ARQUIVO_ORIGEM));
        var imgResultado = new BufferedImage(imgOrigem.getWidth(), imgOrigem.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        int numberOfThreads = 8;
        long[] durations = new long[numberOfThreads];

        for (int i = 1; i <= numberOfThreads; i++) {
            durations[i - 1] = recolorMultitThreaded(imgOrigem, imgResultado, i);
        }


        var outputFile = new File(ARQUIVO_DESTINO);
        ImageIO.write(imgResultado, "jpg", outputFile);

        // gerar gráfico com número de threads e duração
        generateGraph(numberOfThreads, durations);

    }

    private static void generateGraph(int numberOfThreads, long[] durations) {

    }

    private static long recolorMultitThreaded(BufferedImage imgOrigem, BufferedImage imgResultado, int numberOfThreads) {

        List<Thread> threads = new ArrayList<>();
        int height = imgOrigem.getHeight() / numberOfThreads;
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numberOfThreads; i++) {
            final int threadIndex = i;
            var thread = new Thread(() -> {
                int xInicio = 0;
                int yInicio = height * threadIndex;
                recolorirImagem(imgOrigem, imgResultado, xInicio, yInicio, imgOrigem.getWidth(), height);
            });
            threads.add(thread);
        }

        for (Thread thread : threads)
            thread.start();

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;

        System.out.println("Número de Threads: " + numberOfThreads);
        System.out.println("Tempo de execução: " + duration + "ms");

        return duration;

    }

    public static void recolorirImagem(BufferedImage imgOrigem, BufferedImage imgResultado,
                                       int leftCorner, int topCorner, int width, int height) {
        for (int x = leftCorner; x < leftCorner + width && x < imgOrigem.getWidth(); x++) {
            for (int y = topCorner; y < topCorner + height && y < imgOrigem.getHeight(); y++) {
                recolorirPixel(imgOrigem, imgResultado, x, y);
            }
        }
    }

    public static void recolorirPixel(BufferedImage imgOrigem, BufferedImage imgResultado, int x, int y) {
        int rgb = imgOrigem.getRGB(x, y);

        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int newRed;
        int newGreen;
        int newBlue;

        if (ehNivelDeCinza(red, green, blue)) {
            newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green - 90);
            newBlue = Math.max(0, blue - 20);
        } else {
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }

        int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
        setRGB(imgResultado, x, y, newRGB);
    }

    public static void setRGB(BufferedImage image, int x, int y, int rgb) {
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }

    public static boolean ehNivelDeCinza(int red, int green, int blue) {
        return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;
    }

    public static int createRGBFromColors(int red, int green, int blue) {
        int rgb = 0;
        rgb |= blue;
        rgb |= green << 8;
        rgb |= red << 16;
        rgb |= 0xFF000000;
        return rgb;
    }

    public static int getRed(int rgb) {
        return (rgb & 0x00FF0000) >> 16;
    }

    public static int getGreen(int rgb) {
        return (rgb & 0x0000FF00) >> 8;
    }

    public static int getBlue(int rgb) {
        return rgb & 0x000000FF;
    }

}