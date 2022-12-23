###### tags: `高等影像處理` `課程` `碩一上`
# 高等影像處理期末專案

## 期末專案題目
![](https://i.imgur.com/OAnONgD.png)


## 前置作業

## [灰階](https://medium.com/javarevisited/converting-rgb-image-to-the-grayscale-image-in-java-9e1edc5bd6e7)
**Color image to Grayscale image**

1. 取得 RGB
3. 代入公式：
    :::info 
    gray = (0.3 * r + 0.59 * g + 0.11 * b)
    :::
5. 計算出的 gray 值取代 R、G、B

## [負片](https://www.geeksforgeeks.org/image-processing-in-java-colored-image-to-negative-image-conversion/?ref=lbp)
1. Get the RGB value of the pixel.
2. Calculate new RGB values as follows:
    :::info 
    R = 255 – R
    G = 255 – G
    B = 255 – B
    :::
3. Replace the R, G, and B values of the pixel with the values calculated in step 2
4. Repeat Step 1 to Step 3 for each pixel of the image.

## [Gamma correction <1、=1、>1](https://drive.google.com/file/d/1x54pkPdmo8aGKWwPlEEgvSgSzNuEPjGD/view?usp=sharing)
![](https://i.imgur.com/yLpsPvg.png)
:::info
[ (p(i,j)-min / max-min)^gamma ] * 255
:::

## [Salt and Pepper](https://www.cnblogs.com/oomusou/archive/2006/12/21/598795.html)
如果我們考慮 8 位圖像，胡椒鹽雜訊會隨機出現一定數量的像素，分為兩個極端，即 0 或 255。
Salt and Pepper 公式：
:::info 
I(nim, i, j) = 0 if uniform(0, 1) < salt
I(nim, i, j) = 255 if uniform(0, 1) > 1 - pepper
I(nim, i, j) = I(im, i, j) otherwise
uniform(0, 1) : ramdom variable uniformly distributed over[0, 1]
:::

1. 設定 salt 和 pepper 比例
2. 隨機取 0~1 間的值
    1. 若小於 salt，設定 pixel 灰階值為 0 (black)
    2. 若大於 1-pepper，設定 pixel 灰階值為 255 (white)
    3. 其他不變

## [3 × 3 中值濾波器](https://github.com/praserocking/MedianFilter/blob/master/MedianFilter.java)
1. 取得目標 pixel 以及周圍 8 個 pixel 的顏色。共 9 個 pixel
2. 將每個像素點的 R, G, B 值分離出來放到 array 裡，把 R, G, B 的 array 進行排序，得到 array 的中值
3. 9 個 pixel 灰階值取中值。將目標 pixel 設定為中值並 repeat

## [Laplacian 邊緣偵測](https://introcs.cs.princeton.edu/java/31datatype/LaplaceFilter.java.html)
-  原影像
    ![](https://i.imgur.com/SsUi9xB.png)
    
-  一階 Laplacian 微分
    ![](https://i.imgur.com/xe3llhj.png)
    -  一階3*3
        ![](https://i.imgur.com/LjsGoKn.png)

-  二階 Laplacian 微分
    ![](https://i.imgur.com/qiPKsow.png)
    -  二階3*3
        ![](https://i.imgur.com/Y83lkSm.png)

把目標 pixel 的值取代成與 Laplacian 邊緣偵測內積的結果

# [二值化 (Otsu 門檻值)](https://developer.aliyun.com/article/47939)
若 pixel 值 > otsu threshold，pixel 灰階值 = 255(白)

若 pixel 值 <= otsu threshold，pixel 灰階值 = 0(黑)
