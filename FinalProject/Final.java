import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Final {
    public static void main(String args[]) {
        String rootPath = "D:/backup/NCHU/Advanced_Image_Processing/FinalProject/images/";
        String original = rootPath + "cat.jpg";
        String grayScale = rootPath + "grayScale.jpg";

        ImageConverter imageConverter = new ImageConverter();
        imageConverter.convertToGrayScale(original);
        imageConverter.convertGrayScaleToNegative(grayScale);
        imageConverter.convertGrayScaleToGamma(grayScale, 1.0);

    }
}

class ImageConverter {

    // output converted image
    void outputImage(BufferedImage convertedImage, String newFileName) {
        String rootPath = "D:/backup/NCHU/Advanced_Image_Processing/FinalProject/images/";
        try {
            File newFile = new File(rootPath + newFileName);
            ImageIO.write(convertedImage, "jpg", newFile);
            System.out.println("The file " + newFileName + " is already output :)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // convert image to gray scale
    void convertToGrayScale(String filePath) {
        String newFileName = "grayScale.jpg";
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(filePath));
            BufferedImage grayImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                    bufferedImage.getType());

            // convert to grayscale
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    // get pixel of the image
                    int pixel = bufferedImage.getRGB(x, y);

                    int r = (pixel >> 16) & 0xff;
                    int g = (pixel >> 8) & 0xff;
                    int b = pixel & 0xff;

                    // calculate the formula of gray scale
                    int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);

                    // replace RGB value with gray scale value
                    int newPixel = colorToRGB(255, gray, gray, gray);

                    grayImage.setRGB(x, y, newPixel);
                }
            }
            System.out.println("coverted to gray scale");
            outputImage(grayImage, newFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // convert gray scale image to negative
    void convertGrayScaleToNegative(String filePath) {
        String newFileName = "negative.jpg";
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(filePath));
            BufferedImage grayImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                    bufferedImage.getType());

            // convert gray scale to negative
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    // get pixel of the image
                    int pixel = bufferedImage.getRGB(x, y);

                    int r = (pixel >> 16) & 0xff;
                    int g = (pixel >> 8) & 0xff;
                    int b = pixel & 0xff;

                    // subtract RGB from 255
                    r = 255 - r;
                    g = 255 - g;
                    b = 255 - b;

                    // set new RGB value
                    int newPixel = colorToRGB(255, r, g, b);

                    grayImage.setRGB(x, y, newPixel);
                }
            }
            System.out.println("coverted to negative");
            outputImage(grayImage, newFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // convert gray scale image to gamma value
    void convertGrayScaleToGamma(String filePath, Double gammaValue){
        String newFileName = "gamma_" + gammaValue + ".jpg";
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(filePath));
            BufferedImage grayImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                    bufferedImage.getType());

            // convert gray scale to gamma value
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    // get pixel of the image
                    int pixel = bufferedImage.getRGB(x, y);
                    int intensity = GetPixelIntensity(x, y);

                    int r = (pixel >> 16) & 0xff;
                    int g = (pixel >> 8) & 0xff;
                    int b = pixel & 0xff;

                    // calculate the formula of gamma
                    r = (int)Math.round(Math.pow((double)255 * (double)(Red(intensity) / 255), (double)1 / gammaValue));
                    g = (int)Math.round(Math.pow((double)255 * (double)(Red(intensity) / 255), (double)1 / gammaValue));
                    b = (int)Math.round(Math.pow((double)255 * (double)(Red(intensity) / 255), (double)1 / gammaValue));

                    // set new RGB value
                    int newPixel = colorToRGB(255, r, g, b);

                    grayImage.setRGB(x, y, newPixel);
                }
            }
            System.out.println("coverted to gamma: " + gammaValue);
            outputImage(grayImage, newFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int colorToRGB(int alpha, int red, int green, int blue) {
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;
        return newPixel;
    }

}