package org.example.speedup;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 3036 x 4048, 1519 x 2024, 759 x 1012, 379 x 506, 189 x 253
 */
public class SpeedUp {

    public static final String ARQUIVO_ORIGEM = "./resources/many-flowers.jpg";
    // public static final String ARQUIVO_DESTINO = "./out/many-flowers-multithreads.jpg";

    public static void main(String[] args) throws IOException {

        // Tamanhos da imagem a serem testados
        int[][] resolutions = {
                { 3036, 4048 },
                { 1519, 2024 },
                { 759, 1012 },
                { 379, 506 },
                { 189, 253 }
        };
        int numberOfThreads = 6; // Número de threads a ser fixado

        System.out.println("+-----------------------+");
        System.out.println("| Número de Threads: " + numberOfThreads + "  |");
        System.out.println("+-----------------------+\n");

        List<Double> speedUps = new ArrayList<>();

        for (int[] resolution : resolutions) {

            BufferedImage imagemOriginal = ImageIO.read(new File(ARQUIVO_ORIGEM));

            // Redimensiona a imagem para a resolução desejada
            BufferedImage imagemRedimensionada = redimensionarImagem(imagemOriginal, resolution[0], resolution[1]);

            var imagemResultado = new BufferedImage(imagemRedimensionada.getWidth(),
                    imagemRedimensionada.getHeight(),
                    BufferedImage.TYPE_INT_RGB);

            long startTime = System.currentTimeMillis();
            recolorMultitThreaded(imagemRedimensionada, imagemResultado, numberOfThreads);
            long endTime = System.currentTimeMillis();

            long durationMultithreaded = endTime - startTime;

            startTime = System.currentTimeMillis();
            recolorirUmaThread(imagemRedimensionada, imagemResultado);
            endTime = System.currentTimeMillis();

            long durationSingleThreaded = endTime - startTime;

            double speedUp = (double) durationSingleThreaded / durationMultithreaded;
            speedUps.add(speedUp);

            System.out.println("Tamanho da Imagem: " + Arrays.toString(resolution));
            System.out.println("Duração Multi Thread: " + durationMultithreaded);
            System.out.println("Duração Single Thread: " + durationSingleThreaded);
            System.out.println("Speed Up: " + speedUp + "\n");

        }

    }

    public static BufferedImage redimensionarImagem(BufferedImage imagemOriginal, int width, int height) {
        Image tmp = imagemOriginal.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage imagemRedimensionada = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        imagemRedimensionada.createGraphics().drawImage(tmp, 0, 0, null);

        return imagemRedimensionada;
    }

    // imagem redimensionada
//    private static BufferedImage redimensionarImagem(BufferedImage imagemOriginal, int resolution) {
//
//        int width = imagemOriginal.getWidth();
//        int height = imagemOriginal.getHeight();
//
//        // Calcula as novas dimensões mantendo a proporção da imagem
//        double ratio = (double) width / height;
//        int newWidth = resolution;
//        int newHeight = (int) (newWidth / ratio);
//
//        Image tmp = imagemOriginal.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
//        BufferedImage imagemRedimensionada = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
//
//        imagemRedimensionada.createGraphics().drawImage(tmp, 0, 0, null);
//
//        return imagemRedimensionada;
//    }

    public static void recolorirUmaThread(BufferedImage originalImage, BufferedImage imageResult) {
        recolorirImagem(originalImage, imageResult, 0, 0,
                originalImage.getWidth(), imageResult.getHeight());
    }

    private static void recolorMultitThreaded(BufferedImage ImagemOriginal, BufferedImage ImagemResultado, int numberOfThreads) {

        List<Thread> threads = new ArrayList<>();
        int height = ImagemOriginal.getHeight() / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            final int multiplicadorInicio = i;
            int xInicio = 0;
            int yInicio = height * multiplicadorInicio;

            Thread thread = new Thread(() -> {
                recolorirImagem(ImagemOriginal, ImagemResultado, xInicio, yInicio, ImagemOriginal.getWidth(), height);
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

    public static void recolorirImagem(BufferedImage imgOrigem, BufferedImage imageResult,
                                       int leftCorner, int topCorner, int width, int height) {
        for (int x = leftCorner; x < leftCorner + width && x < imgOrigem.getWidth(); x++) {
            for (int y = topCorner; y < topCorner + height && y < imgOrigem.getHeight(); y++) {
                recolorirPixel(imgOrigem, imageResult, x, y);
            }
        }
    }

    public static void recolorirPixel(BufferedImage originalImage, BufferedImage imageResult, int x, int y) {
        int rgb = originalImage.getRGB(x, y);

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
        setRGB(imageResult, x, y, newRGB);
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
