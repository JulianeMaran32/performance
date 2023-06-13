package org.example.tempoExecucao;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Medir o tempo para executar o programa SEM thread
 */
public class SemThread {

    public static final String ARQUIVO_ORIGEM = "./resources/many-flowers.jpg";
    public static final String ARQUIVO_DESTINO = "./out/many-flowers-sem-thread.jpg";

    public static void main(String[] args) throws IOException {

        BufferedImage imgOrigem = ImageIO.read(new File(ARQUIVO_ORIGEM));
        var imgResultado = new BufferedImage(imgOrigem.getWidth(), imgOrigem.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        long startTime = System.currentTimeMillis();
        recolorirUmaThread(imgOrigem, imgResultado);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        var outputFile = new File(ARQUIVO_DESTINO);
        ImageIO.write(imgResultado, "jpg", outputFile);
        System.out.println("Tempo de execução sem threads: " + duration + "ms");

    }

    public static void recolorirUmaThread(BufferedImage imgOrigem, BufferedImage imgResultado) {
        recolorirImagem(imgOrigem, imgResultado, 0, 0, imgOrigem.getWidth(),
                imgOrigem.getHeight());
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
