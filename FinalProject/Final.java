import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import java.awt.Color;


public class Final {
    public static void main(String args[]) {
        String rootPath = "D:/backup/NCHU/Advanced_Image_Processing/image_processing_final_project/FinalProject/images/";
        String original = rootPath + "cat.jpg";
        String grayScale = rootPath + "grayScale.jpg";

        ImageConverter imageConverter = new ImageConverter();
        imageConverter.convertToGrayScale(original);
        imageConverter.convertGrayScaleToNegative(grayScale);
        imageConverter.convertGrayScaleToGamma(grayScale, 0.2);
        imageConverter.convertGrayScaleToGamma(grayScale, 1.0);
        imageConverter.convertGrayScaleToGamma(grayScale, 2.0);

    }
}

class ImageConverter {

    ArrayList pixelsArrayList = new ArrayList<>();

    // output converted image
    void outputImage(BufferedImage convertedImage, String newFileName) {
        String rootPath = "D:/backup/NCHU/Advanced_Image_Processing/image_processing_final_project/FinalProject/images/";
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
                    // get pixel color of the image
                    Color color = new Color(bufferedImage.getRGB(x, y));

                    int r = color.getRed();
                    int g = color.getGreen();
                    int b = color.getBlue();

                    // calculate the formula of gray scale
                    int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);

                    grayImage.setRGB(x, y, new Color(gray, gray, gray).getRGB());
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
                    // get pixel color of the image
                    Color color = new Color(bufferedImage.getRGB(x, y));

                    int r = color.getRed();
                    int g = color.getGreen();
                    int b = color.getBlue();

                    // subtract RGB from 255
                    r = 255 - r;
                    g = 255 - g;
                    b = 255 - b;

                    grayImage.setRGB(x, y, new Color(r, g, b).getRGB());
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

            // get the max and min gray scale values of the image
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    // get pixel color of the image
                    Color color = new Color(bufferedImage.getRGB(x, y));

                    // store each pixel (because the input image is gray scale, the gray scale values of RGB are the same.)
                    pixelsArrayList.add(color.getRed());
                }
            }
            int maxGrayScaleValue = (int) Collections.max(pixelsArrayList);
            int minGrayScaleValue = (int) Collections.min(pixelsArrayList);

            // convert gray scale to gamma value
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    // get pixel color of the image
                    Color color = new Color(bufferedImage.getRGB(x, y));

                    int gray = color.getRed();

                    // calculate the formula of gamma
                    gray = (int)Math.round((double)255 * Math.pow((double)(gray - minGrayScaleValue) / (maxGrayScaleValue - minGrayScaleValue), (double)gammaValue));

                    grayImage.setRGB(x, y, new Color(gray, gray, gray).getRGB());
                }
            }
            System.out.println("coverted to gamma: " + gammaValue);
            outputImage(grayImage, newFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}