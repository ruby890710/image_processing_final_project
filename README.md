# Image Processing

![](https://i.imgur.com/OAnONgD.png)

## 原圖
![](https://i.imgur.com/mASWoXQ.jpg)

## [灰階](https://medium.com/javarevisited/converting-rgb-image-to-the-grayscale-image-in-java-9e1edc5bd6e7)
**convertOriginalToGrayScale**
1. 取得每 pixel 的 RGB
2. 代入灰階公式：
    :::info 
    gray = (0.3 * r + 0.59 * g + 0.11 * b)
    :::
3. 計算出的 gray 值取代原圖的 RGB
4. 圖片的每個 pixel 皆 repeat 相同步驟
5. 轉換前後：
    ![](https://i.imgur.com/QvCmBfd.jpg)


## [負片](https://www.geeksforgeeks.org/image-processing-in-java-colored-image-to-negative-image-conversion/?ref=lbp)
**convertGrayScaleToNegative**
1. 取得每 pixel 的 RGB
2. 將 RGB 帶入負片公式：
    :::info 
    R = 255 – R
    G = 255 – G
    B = 255 – B
    :::
3. 將原圖 RGB 替換成計算出的負片 RGB
4. 圖片的每個 pixel 皆 repeat 相同步驟
5. 轉換前後：
    ![](https://i.imgur.com/KAVV0HN.png)

## [Gamma correction <1、=1、>1](https://drive.google.com/file/d/1x54pkPdmo8aGKWwPlEEgvSgSzNuEPjGD/view?usp=sharing)
**convertGrayScaleToGamma**
1. 取得每 pixel 的 RGB
2. 將 RGB 帶入 gamma 公式：
    ![](https://i.imgur.com/yLpsPvg.png)
:::info
[ (p(i,j)-min / max-min)^gamma ] * 255
:::
3. 將原圖 RGB 替換成計算出的灰階值
4. 圖片的每個 pixel 皆 repeat 相同步驟
5. 轉換前後：
    ![](https://i.imgur.com/nF5uNDo.png)
    ![](https://i.imgur.com/wpBbN0y.png)
    ![](https://i.imgur.com/jO2N3v6.png)

## [Salt and Pepper](https://www.cnblogs.com/oomusou/archive/2006/12/21/598795.html)
**convertGammaToSaltAndPepper**
如果我們考慮 8 位圖像，胡椒鹽雜訊會隨機出現一定數量的像素，分為兩個極端，即 0 或 255。
1. 設定 salt 和 pepper 比例
2. 隨機取 0~1 間的值
    1. 若小於 salt，設定 pixel 灰階值為 0 (black)
    2. 若大於 1-pepper，設定 pixel 灰階值為 255 (white)
    3. 其他不變
3. 圖片的每個 pixel 皆 repeat 相同步驟
4. 轉換前後：
    ![](https://i.imgur.com/IWIYm92.jpg)

## [3 × 3 中值濾波器](https://github.com/praserocking/MedianFilter/blob/master/MedianFilter.java)
**convertSaltAndPepperToMedianFilter**
1. 取得目標 pixel 以及周圍 8 個 pixel 的顏色。共 9 個 pixel
2. 將每個像素點的 RGB 值分離出來放到 array 裡，把 RGB 的 array 進行排序，得到 array 的中值
3. 9 個 pixel 灰階值取中值。將目標 pixel 設定為中值並 repeat
4. 轉換前後：
    ![](https://i.imgur.com/F3bJ4fU.jpg)

## [Laplacian 邊緣偵測](https://introcs.cs.princeton.edu/java/31datatype/LaplaceFilter.java.html)
**convertGammaToLaplacian**
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
1. 取得目標 pixel 以及周圍 8 個 pixel 的顏色。共 9 個 pixel
2. 將每個像素點的 R, G, B 值透過二階 Laplacian 矩陣計算出 RGB
3. 將所有灰階值皆設定介於 0~255 間 (<0 設為 0，>255 設為 255)
4. 把目標 pixel 的值取代成與 Laplacian 邊緣偵測內積的結果並 repeat
5. 轉換前後：
    ![](https://i.imgur.com/SekQUYc.jpg)

## [3 × 3 最大值濾波器](https://github.com/praserocking/MedianFilter/blob/master/MedianFilter.java)
**convertLaplacianToMaximumFilter**
1. 取得目標 pixel 以及周圍 8 個 pixel 的顏色。共 9 個 pixel
2. 將每個像素點的 R, G, B 值分離出來放到 array 裡
3. 9 個 pixel 灰階值取最大值。將目標 pixel 設定為最大值並 repeat
4. 轉換前後：
    ![](https://i.imgur.com/l9Kr3O0.jpg)

## [二值化 (以 Otsu 作為門檻值)](https://developer.aliyun.com/article/47939)
**convertGammaToOtsuBinarization**
1. 取得每 pixel 的灰階值
2. 取得直方圖
3. 找出 otsu 門檻值 (擁有最小組內變異數的灰階值)
    :::info
    -  若 pixel 值 > otsu threshold，pixel 灰階值 = 255(白)
    -  若 pixel 值 <= otsu threshold，pixel 灰階值 = 0(黑)
    :::
    >  [二值化前後的直方圖分布](https://hackmd.io/@Youwe/SkBHZmcrI)
    >      ![](https://i.imgur.com/qPfJN0E.png)
4. 圖片的每個 pixel 皆 repeat 相同步驟
5. 轉換前後：
    ![](https://i.imgur.com/ddKqv0o.png)
