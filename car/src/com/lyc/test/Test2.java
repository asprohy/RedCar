package com.lyc.test;

import java.applet.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.soap.Detail;

public class Test2 extends Applet  
{  
	/**
     * 
     */
    	private static final long serialVersionUID = 1L;
	private static final int X = 1105;
	private static final int Y = 947;
	


	public void init(){
		super.init();
		resize(X, Y);
		
	}
	public void cal(Point p1, Point p2) {
	    double ang = 0;
	    double detaX = p2.x - p1.x;
	    double detaY = p2.y - p1.y;
	    ang = Math.atan2(detaX, detaY) * 180 / 3.14;
	    System.out.println((270 + ang) % 360);
	}

	public void mousePressed(MouseEvent e) {
            int x1 = e.getX();
            int y1 = e.getY();
            System.out.println("按下时的坐标点: x="+x1+",y="+y1);
        }
	public void paint(Graphics g)  
	{  
	    Map map = new Map();
	    map.drawMap(g);

	    Point p0 = new Point(120, 480);
	    Point p1 = new Point(110, 470);
	    Point p2 = new Point(102, 460);
	    Point p3 = new Point(97, 450);
	    Point p4 = new Point(92, 440);
	    Point p5 = new Point(90, 430);
	    Point p6 = new Point(90, 420);
	    Point p7 = new Point(550, 780);
	    Point p8 = new Point(540, 770);
	    
	    Point p9 = new Point(935, 460); 
	    Point p10 = new Point(930, 470);
	    Point p11 = new Point(925, 475);
	    
	    Point p12 = new Point(140, 860);
	    Point p13 = new Point(160, 865);
	    Point p14 = new Point(170, 865);
	    
	    Point p15 = new Point(890, 110);
	    Point p16 = new Point(880, 120);
	    
	    Point p17 = new Point(120, 893);
	    Point p18 = new Point(135, 898);
	    Point p19 = new Point(150, 902);
	    Point p20 = new Point(170, 905);
//	    Point p21 = new Point(852, 90);
//	    Point p22 = new Point(845, 100);
	    cal(p0, p1);
	    cal(p1, p2);
	    cal(p2, p3);
	    cal(p3, p4);
	    cal(p4, p5);
	    cal(p5, p6);
//	    cal(p6, p7);
//	    cal(p7, p8);
//	    cal(p8, p9);
//	    cal(p9, p10);
//	    cal(p10, p11);
//	    cal(p11, p12);
//	    cal(p12, p13);
//	    cal(p13, p14);
//	    cal(p14, p15);
//	    cal(p15, p16);
//	    cal(p16, p17);
//	    cal(p17, p18);
//	    cal(p18, p19);
//	    cal(p19, p20);
//	    cal(p20, p21);
//	    cal(p21, p22);

	    


	    
	    g.setColor(Color.RED);
	    Point.drawPoint(p0, g);
	    Point.drawPoint(p1, g);
	    Point.drawPoint(p2, g);
	    Point.drawPoint(p3, g);
	    Point.drawPoint(p4, g);
	    Point.drawPoint(p5, g);
	    Point.drawPoint(p6, g);
	    Point.drawPoint(p7, g);
	    Point.drawPoint(p8, g);
	    Point.drawPoint(p9, g);
	    Point.drawPoint(p10, g);
	    Point.drawPoint(p11, g);
	    Point.drawPoint(p12, g);
	    Point.drawPoint(p13, g);
	    Point.drawPoint(p14, g);
	    Point.drawPoint(p15, g);
	    Point.drawPoint(p16, g);
	    Point.drawPoint(p17, g);
	    Point.drawPoint(p18, g);
	    Point.drawPoint(p19, g);
	    Point.drawPoint(p20, g);
//	    Point.drawPoint(p21, g);
//	    Point.drawPoint(p22, g);


	}
 
	/*road 1 channel 2 hallCount 2
	     Point p0 = new Point(50, 180);
	    Point p1 = new Point(52, 165);
	    Point p2 = new Point(55, 150);
	    Point p3 = new Point(60, 135);
	    Point p4 = new Point(66, 120);
	    Point p5 = new Point(74, 108);
	    Point p6 = new Point(85, 95);
	    Point p7 = new Point(95, 85);
	    Point p8 = new Point(105, 78);
	    Point p9 = new Point(118, 72);
	    Point p10 = new Point(130, 68);
	    Point p11 = new Point(140, 65);
	    Point p12 = new Point(155, 62);
	    Point p13 = new Point(170, 60);
	 */
	
	/*road 1 channel 2 hallCount 14
	 * 
	    Point p0 = new Point(940, 350);
	    Point p1 = new Point(950, 360);
	    Point p2 = new Point(960, 370);
	    Point p3 = new Point(966, 380);
	    Point p4 = new Point(972, 390);
	    Point p5 = new Point(976, 400);
	    Point p6 = new Point(978, 410);
	    Point p7 = new Point(978, 420);
	    Point p8 = new Point(978, 430);
	    Point p9 = new Point(978, 440);
	    Point p10 = new Point(976, 450);
	    Point p11 = new Point(972, 460);
	    Point p12 = new Point(968, 475);
	    Point p13 = new Point(962, 485);
	    Point p14 = new Point(955, 495);
	    Point p15 = new Point(950, 500);
	 * 
	 */

} 