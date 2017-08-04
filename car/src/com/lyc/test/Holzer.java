package com.lyc.test;

public class Holzer {  
	int direction;	
	Point p;
	
	
	public Holzer()
	{
	    	p = new Point();
		direction = 1;
	}
	
	public Holzer(int holzerCounting) {
		// TODO Auto-generated constructor stub
		this.p = getPoint(holzerCounting);
		direction = getDirection(holzerCounting);

	}
	
	// test
	public static Point getPoint(int hozerCounting) {
		Point p = new Point();
		switch (hozerCounting) {
		case 0:
			p.x = 40;
			p.y = 50;
			break;
			
		case 1:
			p.x = 40;
//			p.y = 100;
			p.y = 50;
			break;
			
		case 2:
			p.x = 40;
			p.y = 150;
			p.y = 50;
			break;
			
		case 3:
			p.x = 40;
			p.y = 200;
			p.y = 50;
			break;
			
		case 4:
			p.x = 40;
			p.y = 250;
			p.y = 50;
			break;
			
		case 5:
			p.x = 40;
			p.y = 300;
			p.y = 50;
			break;
			
		case 6:
			p.x = 40;
			p.y = 350;
			p.y = 50;
			break;
			
		case 7:
			p.x = 40;
			p.y = 400;
			p.y = 50;
			break;
			
			
		case 8:
			p.x = 40;
			p.y = 450;
			p.y = 50;
			break;
			
			
		case 9:
			p.x = 40;
			p.y = 500;
			p.y = 50;
			break;
			
			
		case 10:
			p.x = 40;
			p.y = 550;
			p.y = 50;
			break;

		default:
			break;
		}
		p.x = 35;
		p.y = 0;
		return p;
	}
	
	//根据霍尔计数返回小车行驶方向
	public static int getDirection(int holzerCounting){
		return 2;
	}
}

