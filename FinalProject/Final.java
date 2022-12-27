import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.imageio.ImageIO;

public class Final {
    public static void main(String args[]) {
        double gammaValueLessThan1 = 0.2;
        double gammaValueGreaterThan1 = 2.0;
        int n = 3;
        String rootPath = "D:/backup/NCHU/Advanced_Image_Processing/image_processing_final_project/FinalProject/images/";
        String original = rootPath + "cat.jpg";
        String grayScale = rootPath + "grayScale.jpg";
        String gammaLessThan1 = rootPath + "Gamma_" + gammaValueLessThan1 + ".jpg";
        String saltAndPepper = rootPath + "SaltAndPepper.jpg";
        String gamma1 = rootPath + "Gamma_1.0.jpg";
        String laplacian = rootPath + "Laplacian.jpg";
        String gammaGreaterThan1 = rootPath + "Gamma_" + gammaValueGreaterThan1 + ".jpg";

        ImageConverter imageConverter = new ImageConverter();

        imageConverter.convertOriginalToGrayScale(original);
        imageConverter.convertGrayScaleToNegative(grayScale);
        imageConverter.convertGrayScaleToGamma(grayScale, gammaValueLessThan1);
        imageConverter.convertGrayScaleToGamma(grayScale, 1.0);
        imageConverter.convertGrayScaleToGamma(grayScale, gammaValueGreaterThan1);
        double salt = 0.05, pepper = 0.05;
        imageConverter.convertGammaToSaltAndPepper(gammaLessThan1, salt, pepper);
        imageConverter.convertSaltAndPepperToMedianFilter(saltAndPepper, n);
        imageConverter.convertGammaToLaplacian(gamma1);
        imageConverter.convertLaplacianToMaximumFilter(laplacian, n);
        imageConverter.convertGammaToOtsuBinarization(gammaGreaterThan1);
    }
}

class ImageConverter {

    /**
     * 輸出轉換後的圖片
     *
     * @param convertedImage 經過轉換後的圖片
     * @param newFileName    輸出圖片的名稱
     */
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

    /**
     * 由原圖轉換為灰階
     *
     * @param filePath 欲轉換的圖片路徑
     */
    void convertOriginalToGrayScale(String filePath) {
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

    /**
     * 由灰階圖轉換為負片
     *
     * @param filePath 欲轉換的圖片路徑
     */
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

    /**
     * 由灰階圖片轉換為 gamma (<1、=1、>1)
     *
     * @param filePath   欲轉換的圖片路徑
     * @param gammaValue gamma值(型態為 double)
     */
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
                    // (because the input image is grayscale, the gray scale values of RGB are the
                    // same.)
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

    /**
     * 由 gamma 圖 (gamma < 1) 轉換為胡椒鹽
     *
     * @param filePath 欲轉換的圖片路徑
     * @param salt     撒鹽的比例
     * @param pepper   灑胡椒的比例
     */
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

    /**
     * 由胡椒鹽雜訊圖轉換為 n*n 中值濾波器
     *
     * @param filePath 欲轉換的圖片路徑
     * @param n
     */
    void convertSaltAndPepperToMedianFilter(String filePath, int n) {
        String newFileName = n + "x" + n + " MedianFilter.jpg";
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(filePath));
            BufferedImage saltAndPepperImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                    bufferedImage.getType());

            // convert salt and pepper image to n*n median filter
            Color[] pixel = new Color[n * n];
            int[] R = new int[n * n];
            int[] B = new int[n * n];
            int[] G = new int[n * n];
            for (int x = 1; x < bufferedImage.getWidth() - 1; x++)
                for (int y = 1; y < bufferedImage.getHeight() - 1; y++) {
                    pixel[0] = new Color(bufferedImage.getRGB(x - 1, y - 1));
                    pixel[1] = new Color(bufferedImage.getRGB(x - 1, y));
                    pixel[2] = new Color(bufferedImage.getRGB(x - 1, y + 1));
                    pixel[3] = new Color(bufferedImage.getRGB(x, y + 1));
                    pixel[4] = new Color(bufferedImage.getRGB(x + 1, y + 1));
                    pixel[5] = new Color(bufferedImage.getRGB(x + 1, y));
                    pixel[6] = new Color(bufferedImage.getRGB(x + 1, y - 1));
                    pixel[7] = new Color(bufferedImage.getRGB(x, y - 1));
                    pixel[8] = new Color(bufferedImage.getRGB(x, y));
                    for (int k = 0; k < n * n; k++) {
                        R[k] = pixel[k].getRed();
                        B[k] = pixel[k].getBlue();
                        G[k] = pixel[k].getGreen();
                    }
                    Arrays.sort(R);
                    Arrays.sort(G);
                    Arrays.sort(B);
                    saltAndPepperImage.setRGB(x, y, new Color(R[(n * n) / 2], B[(n * n) / 2], G[(n * n) / 2]).getRGB());
                }
            System.out.println("coverted to " + n + "x" + n + " median filter");
            outputImage(saltAndPepperImage, newFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 由 gamma 圖 (gamma = 1) 轉換為一階 laplacian
     *
     * @param filePath 欲轉換的圖片路徑
     */
    void convertGammaToLaplacian(String filePath) {
        String newFileName = "Laplacian.jpg";
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(filePath));
            BufferedImage gammaImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                    bufferedImage.getType());

            // convert gamma to laplacian (1st derivative)
            for (int y = 1; y < bufferedImage.getHeight() - 1; y++) {
                for (int x = 1; x < bufferedImage.getWidth() - 1; x++) {
                    Color c00 = new Color(bufferedImage.getRGB(x - 1, y - 1));
                    Color c01 = new Color(bufferedImage.getRGB(x - 1, y));
                    Color c02 = new Color(bufferedImage.getRGB(x - 1, y + 1));
                    Color c10 = new Color(bufferedImage.getRGB(x, y - 1));
                    Color c11 = new Color(bufferedImage.getRGB(x, y));
                    Color c12 = new Color(bufferedImage.getRGB(x, y + 1));
                    Color c20 = new Color(bufferedImage.getRGB(x + 1, y - 1));
                    Color c21 = new Color(bufferedImage.getRGB(x + 1, y));
                    Color c22 = new Color(bufferedImage.getRGB(x + 1, y + 1));
                    int r = -c00.getRed() - c01.getRed() - c02.getRed() +
                            -c10.getRed() + 8 * c11.getRed() - c12.getRed() +
                            -c20.getRed() - c21.getRed() - c22.getRed();
                    int g = -c00.getGreen() - c01.getGreen() - c02.getGreen() +
                            -c10.getGreen() + 8 * c11.getGreen() - c12.getGreen() +
                            -c20.getGreen() - c21.getGreen() - c22.getGreen();
                    int b = -c00.getBlue() - c01.getBlue() - c02.getBlue() +
                            -c10.getBlue() + 8 * c11.getBlue() - c12.getBlue() +
                            -c20.getBlue() - c21.getBlue() - c22.getBlue();
                    // grayscale value between 0~255
                    r = Math.min(255, Math.max(0, r));
                    g = Math.min(255, Math.max(0, g));
                    b = Math.min(255, Math.max(0, b));
                    Color color = new Color(r, g, b);
                    gammaImage.setRGB(x, y, color.getRGB());
                }
            }
            System.out.println("coverted to laplacian");
            outputImage(gammaImage, newFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 由 laplacian 圖轉換為 n*n 最大值濾波器
     *
     * @param filePath
     * @param n
     */
    void convertLaplacianToMaximumFilter(String filePath, int n) {
        String newFileName = n + "x" + n + " MaximumFilter.jpg";
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(filePath));
            BufferedImage laplacianImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                    bufferedImage.getType());

            // convert salt and pepper image to n*n maximum filter
            Color[] pixel = new Color[n * n];
            int[] R = new int[n * n];
            int[] B = new int[n * n];
            int[] G = new int[n * n];
            for (int x = 1; x < bufferedImage.getWidth() - 1; x++)
                for (int y = 1; y < bufferedImage.getHeight() - 1; y++) {
                    pixel[0] = new Color(bufferedImage.getRGB(x - 1, y - 1));
                    pixel[1] = new Color(bufferedImage.getRGB(x - 1, y));
                    pixel[2] = new Color(bufferedImage.getRGB(x - 1, y + 1));
                    pixel[3] = new Color(bufferedImage.getRGB(x, y + 1));
                    pixel[4] = new Color(bufferedImage.getRGB(x + 1, y + 1));
                    pixel[5] = new Color(bufferedImage.getRGB(x + 1, y));
                    pixel[6] = new Color(bufferedImage.getRGB(x + 1, y - 1));
                    pixel[7] = new Color(bufferedImage.getRGB(x, y - 1));
                    pixel[8] = new Color(bufferedImage.getRGB(x, y));
                    for (int k = 0; k < n * n; k++) {
                        R[k] = (pixel[k].getRed());
                        B[k] = (pixel[k].getBlue());
                        G[k] = (pixel[k].getGreen());
                    }
                    int rMax = Arrays.stream(R).max().getAsInt();
                    int gMax = Arrays.stream(G).max().getAsInt();
                    int bMax = Arrays.stream(B).max().getAsInt();

                    laplacianImage.setRGB(x, y, new Color(rMax, gMax, bMax).getRGB());
                }
            System.out.println("coverted to " + n + "x" + n + " maximum filter");
            outputImage(laplacianImage, newFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 由 gamma 圖 (gamma > 1) 轉換為二值法 (otsu 為 threshold)
     *
     * @param filePath 欲轉換的圖片路徑
     */
    void convertGammaToOtsuBinarization(String filePath) {
        String newFileName = "OtsuBinarization.jpg";
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(filePath));
            BufferedImage gammaImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                    bufferedImage.getType());
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            // convert gamma to otsu binarization
            int[] inPixels = new int[width * height];
            int[] outPixels = new int[width * height];
            int index = 0;

            // get grayscale value
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    index = y * width + x;
                    inPixels[index] = bufferedImage.getRGB(x, y);
                }
            }

            // get histogram
            int[] histogram = new int[256];
            for (int row = 0; row < height; row++) {
                int tr = 0;
                for (int col = 0; col < width; col++) {
                    index = row * width + col;
                    tr = (inPixels[index] >> 16) & 0xff;
                    histogram[tr]++;
                }
            }
            // otsu binarization
            double total = width * height;
            double[] variances = new double[256];
            for (int i = 0; i < variances.length; i++) {
                double bw = 0;
                double bmeans = 0;
                double bvariance = 0;
                double count = 0;
                for (int t = 0; t < i; t++) {
                    count += histogram[t];
                    bmeans += histogram[t] * t;
                }
                bw = count / total;
                bmeans = (count == 0) ? 0 : (bmeans / count);
                for (int t = 0; t < i; t++) {
                    bvariance += (Math.pow((t - bmeans), 2) * histogram[t]);
                }
                bvariance = (count == 0) ? 0 : (bvariance / count);
                double fw = 0;
                double fmeans = 0;
                double fvariance = 0;
                count = 0;
                for (int t = i; t < histogram.length; t++) {
                    count += histogram[t];
                    fmeans += histogram[t] * t;
                }
                fw = count / total;
                fmeans = (count == 0) ? 0 : (fmeans / count);
                for (int t = i; t < histogram.length; t++) {
                    fvariance += (Math.pow((t - fmeans), 2) * histogram[t]);
                }
                fvariance = (count == 0) ? 0 : (fvariance / count);
                variances[i] = bw * bvariance + fw * fvariance;
            }

            // find the minimum within class variance
            double min = variances[0];
            int threshold = 0;
            for (int m = 1; m < variances.length; m++) {
                if (min > variances[m]) {
                    threshold = m;
                    min = variances[m];
                }
            }
            System.out.println("final min variances : " + min);
            System.out.println("final otsu threshold value : " + threshold);
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    index = row * width + col;
                    int gray = (inPixels[index] >> 8) & 0xff;
                    if (gray > threshold) {
                        gray = 255;
                        outPixels[index] = (0xff << 24) | (gray << 16) | (gray << 8) | gray;
                    } else {
                        gray = 0;
                        outPixels[index] = (0xff << 24) | (gray << 16) | (gray << 8) | gray;
                    }
                    gammaImage.setRGB(col, row, outPixels[index]);
                }
            }

            System.out.println("coverted to otsu binarization");
            outputImage(gammaImage, newFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}