package com.lyc.test;

import java.awt.Graphics;

public class Point {
	public int x;
	public int y;
	
	public Point(int x, int y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
	}
	
	public Point() {
		x = 0;
		y = 0;
	}
	
	public static void drawPoint(Point p, Graphics g)
	{
	    int r = 5;
	    g.fillOval(p.x, p.y, r, r);
	}
	
}
