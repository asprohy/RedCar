package com.car.basic;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import sun.applet.Main;



public class Map{
    
    private static final int X = 661;
    private static final int Y = 575;
    
    static String mapImgString = "../resources/Map.png";
    static File mapImgFile = new File(mapImgString);
    static BufferedImage mapImg;
    public Map() {
	// TODO Auto-generated constructor stub
	try {
	    mapImg = ImageIO.read(mapImgFile);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public static BufferedImage getMapImg() {
	try {
	    mapImg = ImageIO.read(mapImgFile);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return mapImg;
    }
    
    public static void main(String[] args) {
	Map map = new Map();
    }

}
