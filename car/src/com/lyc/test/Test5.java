package com.lyc.test;

import java.applet.Applet;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test5 extends Applet{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private static final int X = 770;
    private static final int Y = 700;
    
    public void init(){
	super.init();
	resize(X, Y);
    }

    File file = new File("D:/Workspaces/car/src/resource/map.png");
    File waterFile = new File("D:/Workspaces/car/src/resource/22.5car.png");
    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        super.paint(g);
        // 获取底图
       
        // 获取层图
        
        BufferedImage carImg;
        BufferedImage mapImg;
        BufferedImage carImg2;
        BufferedImage bfBufferedImage = new BufferedImage(X, Y, 1);
	try {
	    carImg = ImageIO.read(waterFile);
	    carImg2 = ImageIO.read(waterFile);
	    mapImg = ImageIO.read(file);
	    bfBufferedImage = watermark(mapImg, carImg, 50, 50, 1);
	    bfBufferedImage = watermark(mapImg, carImg2, 50, 100, 1);
	} catch (IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
       
        g.drawImage(bfBufferedImage, 0, 0, this);
        
    }
    
    public static BufferedImage watermark(BufferedImage bg, BufferedImage car, int x, int y, float alpha) throws IOException {

        // 创建Graphics2D对象，用在底图对象上绘图
//	car = rotateImage(car, 80);
        Graphics2D g2d = bg.createGraphics();
        int waterImgWidth = car.getWidth();// 获取层图的宽度
        int waterImgHeight = car.getHeight();// 获取层图的高度
        
        // 在图形和图像中实现混合和透明效果
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        
        // 绘制
        g2d.drawImage(car, x, y, waterImgWidth, waterImgHeight, null);
        g2d.dispose();// 释放图形上下文使用的系统资源
        return bg;
    }
    
    
    public static BufferedImage rotateImage(final BufferedImage bufferedimage,
            final int degree) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }
    
    /*
    public static void main(String[] args) {
	JFrame jf = new JFrame("Test");
	jf.setSize(X, Y);
	jf.setVisible(true);
	Map map = new Map();
	jf.add(map);
	
	Car car = new Car();
	car.setSize(10, 20);
	jf.add(car);

	jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//	map.setVisible(true);

    }
    
    */

}
