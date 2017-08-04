package com.lyc.test;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;

import com.car.dao.MyDaoImpl;

public class Test3 extends Applet {

    	private static final long serialVersionUID = 1L;
	private static final int X = 770;
	private static final int Y = 618;
	

	
	
	public void init(){
		super.init();
		resize(X, Y);
	}
	
	private Image bgImage;
	private Graphics bg;
	private Graphics cg;
	Graphics car;
	Image carImage;
	
	
	
	
	public void update(Graphics g)
	{

	    Car c1 = new Car(1, 280, 290);
	    Car c2 = new Car(2, 485, 480);
	    c2.direction = 2;
//	    c1.drawCar(90, g);
	    for(int i = 0; i < 60; i++)
	    {
		if (bgImage == null)
		{
		    bgImage = createImage (this.getSize().width, this.getSize().height);
		    bg = bgImage.getGraphics();
		}
		g.drawImage(bgImage, 0, 0, this);
		
		Map p = new Map();
		p.drawMap(bg);
//		int time = 530881;
	    	if(i <= 30 || i >= 35)
	    	{
	    	    c1.centerPointOfCar.x += 5;
	    	}

		c2.centerPointOfCar.y -= 5;

//		System.out.println(c1.centerPointOfCar.x + " " + c1.centerPointOfCar.y);
		c1.drawCar(bg);
		c2.drawCar(bg);
		
		try {
		    Thread.sleep(200);
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
			
	}
	   
	}
	
	
	public void paint(Graphics g)  
	{
	    repaint();
	}
	
	
	
	/*
	public void paint(Graphics g)  
	{  
	    Map m = new Map();
	    m.drawMap(g);
	    Point p = new Point(25, 30);
	    g.setColor(Color.RED);
	    Point.drawPoint(p, g);
	    System.out.println(p.x + " " + p.y);
	    for(int i = 0; i < 9; i++){
		p.y = p.y + 50;
		Point.drawPoint(p, g);
		System.out.println(p.x + " " + p.y);
	    }
	    p.x += 40;
	    p.y += 40;
	    Point.drawPoint(p, g);
	    System.out.println(p.x + " " + p.y);
	    for(int i = 0; i < 8; i++){
		p.x = p.x + 50;
		Point.drawPoint(p, g);
		System.out.println(p.x + " " + p.y);
	    }
	    p.x += 20;
	    p.y -= 40;
	    Point.drawPoint(p, g);
	    System.out.println(p.x + " " + p.y);
	    for(int i = 0; i < 8; i++){
		p.y = p.y - 50;
		Point.drawPoint(p, g);
		System.out.println(p.x + " " + p.y);
	    }
	    p.y -= 50;
	    p.x -= 10;
	    Point.drawPoint(p, g);
	    System.out.println(p.x + " " + p.y);

	    
	    c2.direction = 2;
	    c2.channel = "outside";
	    c2.drawCar(g);
	    
	    for(int i = 0; i < 8; i++){
		p.x = p.x - 50;
		Point.drawPoint(p, g);
		System.out.println(p.x + " " + p.y);
	    }
	}
	*/
/*
	25 30
	25 80
	25 130
	25 180
	25 230
	25 280
	25 330
	25 380
	25 430
	25 480
	65 520
	115 520
	165 520
	215 520
	265 520
	315 520
	365 520
	415 520
	465 520
	485 480
	485 430
	485 380
	485 330
	485 280
	485 230
	485 180
	485 130
	485 80
	475 30
	425 30
	375 30
	325 30
	275 30
	225 30
	175 30
	125 30
	75 30
*/
	/*
	public void paint(Graphics g) 
	{   
	    Map m = new Map();
	    m.drawMap(g);
	    Point p = new Point(110, 320);
	    g.setColor(Color.RED);
	    Point.drawPoint(p, g);
	    
	    for(int i = 0; i < 5; i++)
	    {
		p.y += 50;
		Point.drawPoint(p, g);
	    }
	    
	    p.x += 40;
	    p.y += 20;
	    Point.drawPoint(p, g);
	    
	    for(int i = 0; i < 8; i++)
	    {
		p.x += 50;
		Point.drawPoint(p, g);
	    }
	    
	    p.x += 50;
	    p.y -= 20;
	    Point.drawPoint(p, g);
	    
	    for(int i = 0; i < 5; i++)
	    {
		p.y-=50;
		Point.drawPoint(p, g);
	    }
	   
	    p.x -= 40;
	    p.y -= 30;
	    Point.drawPoint(p, g);
	    
	    for(int i = 0; i < 8; i++)
	    {
//		System.out.println(" x: " + p.x + " y: " + p.y);
		p.x -= 50;
		Point.drawPoint(p, g);
	    }
	    
	    Car c1 = new Car(1, 450, 290);
	    c1.drawCar(g);
	    
	    
	}
	*/
}
