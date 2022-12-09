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
        String gamma = rootPath + "grayScale.jpg";

        ImageConverter imageConverter = new ImageConverter();
        imageConverter.convertToGrayScale(original);
        imageConverter.convertGrayScaleToNegative(grayScale);
        imageConverter.convertGrayScaleToGamma(grayScale, 0.2);
        imageConverter.convertGrayScaleToGamma(grayScale, 1.0);
        imageConverter.convertGrayScaleToGamma(grayScale, 2.0);
        double salt = 0.05, pepper = 0.05;
        imageConverter.convertGammaToSaltAndPepper(gamma, salt, pepper);

    }
}

class ImageConverter {

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
        String newFileName = "GrayScale.jpg";
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
        String newFileName = "Negative.jpg";
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
    void convertGrayScaleToGamma(String filePath, double gammaValue) {
        String newFileName = "Gamma_" + gammaValue + ".jpg";
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(filePath));
            BufferedImage grayImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                    bufferedImage.getType());

            // get the max and min gray scale values of the image
            ArrayList pixelsArrayList = new ArrayList<>();
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    // get pixel color of the image
                    Color color = new Color(bufferedImage.getRGB(x, y));

                    // store the gray scale values of each pixel
                    // (because the input image is grayscale, the gray scale values of RGB are the same.)
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
                    gray = (int) Math.round((double) 255
                            * Math.pow((double) (gray - minGrayScaleValue) / (maxGrayScaleValue - minGrayScaleValue),
                                    (double) gammaValue));

                    grayImage.setRGB(x, y, new Color(gray, gray, gray).getRGB());
                }
            }
            System.out.println("coverted to gamma: " + gammaValue);
            outputImage(grayImage, newFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // convert gamma image to salt and pepper
    void convertGammaToSaltAndPepper(String filePath, double salt, double pepper) {
        String newFileName = "SaltAndPepper.jpg";
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(filePath));
            BufferedImage gammaImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                    bufferedImage.getType());

            // convert gray scale to gamma value
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                for (int x = 0; x < bufferedImage.getWidth(); x++) {

                    // select random variable uniformly distributed over[0, 1]
                    double randomVariable = (double) (Math.random() * 100 + 1) / 100;

                    if (randomVariable < salt) {
                        gammaImage.setRGB(x, y, new Color(0, 0, 0).getRGB());
                    } else if (randomVariable > (double) 1 - pepper) {
                        gammaImage.setRGB(x, y, new Color(255, 255, 255).getRGB());
                    } else {
                        gammaImage.setRGB(x, y, bufferedImage.getRGB(x, y));
                    }
                }
            }
            System.out.println("coverted to salt and pepper");
            outputImage(gammaImage, newFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}