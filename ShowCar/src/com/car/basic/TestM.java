package com.car.basic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestM {
    
    
    private static final int X = 661;
    private static final int Y = 575;
    
    public static void main(String[] args) {
	
	
	JFrame jFrame = new JFrame();
	JPanel jPanel = new JPanel();
	jFrame.setSize(X, Y);
//	jFrame.add(jPanel);
	Graphics g;
	
	BufferedImage bImage = Map.getMapImg();
	g = bImage.getGraphics();
	jFrame.paint(g);
	
	
	
	
	jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	jFrame.setVisible(true);
    }
}
