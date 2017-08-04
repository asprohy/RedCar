package com.car.basic;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DealImg {
    /**
     * 
     * @Title: 构造图片
     * @Description: 生成水印并返回java.awt.image.BufferedImage
     * @param file
     *            背景文件(图片)
     * @param waterFile
     *            文件(图片)
     * @param x
     *            距离右下角的X偏移量
     * @param y
     *            距离右下角的Y偏移量
     * @param alpha
     *            透明度, 选择值从0.0~1.0: 完全透明~完全不透明
     * @return BufferedImage
     * @throws IOException
     */
    public static BufferedImage combinImg(File file, File waterFile, int x, int y, float alpha) throws IOException {
        // 获取底图
        BufferedImage buffImg = ImageIO.read(file);
        // 获取层图
        BufferedImage waterImg = ImageIO.read(waterFile);
        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = buffImg.createGraphics();
        int waterImgWidth = waterImg.getWidth();// 获取层图的宽度
        int waterImgHeight = waterImg.getHeight();// 获取层图的高度
        // 在图形和图像中实现混合和透明效果
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        // 绘制
        g2d.drawImage(waterImg, x, y, waterImgWidth, waterImgHeight, null);
        g2d.dispose();// 释放图形上下文使用的系统资源
        return buffImg;
    }
    
    //输入两个图片，返回将两个图片和成为后的图片
    public static BufferedImage combinImg(BufferedImage backgroundImage, BufferedImage drawImage, int x, int y) throws IOException {

        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = backgroundImage.createGraphics();
        int waterImgWidth = drawImage.getWidth();// 获取层图的宽度
        int waterImgHeight = drawImage.getHeight();// 获取层图的高度
        // 在图形和图像中实现混合和透明效果
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1));
        // 绘制
        g2d.drawImage(drawImage, x, y, waterImgWidth, waterImgHeight, null);
        g2d.dispose();// 释放图形上下文使用的系统资源
        
        //将两个图片合成了一个图片
        return backgroundImage;
    }
}
