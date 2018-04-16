package com.map;

import Bean.Bean;

public class CongestionCoordinate implements Bean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -682748427647878983L;
	private long x1;
	private long y1;
	private long x2;	
	private long y2;
	private double deltaAngle;
	
	private long special;
	
	public CongestionCoordinate() {
	    // TODO Auto-generated constructor stub
	    x1 = 0;
	    y1 = 0;
	    x2 = 0;
	    y2 = 0;
	    deltaAngle = 0;
	    special = 0;
	}
	
	
	public double getDeltaAngle() {
		return deltaAngle;
	}
	
	public void setDeltaAngle(double deltaAngle) {
		this.deltaAngle = deltaAngle;
	}
	
	public long getX1() {
		return x1;
	}
	public void setX1(long x1) {
		this.x1 = x1;
	}
	public long getX2() {
		return x2;
	}
	public void setX2(long x2) {
		this.x2 = x2;
	}
	public long getY1() {
		return y1;
	}
	public void setY1(long y1) {
		this.y1 = y1;
	}
	public long getY2() {
		return y2;
	}
	public void setY2(long y2) {
		this.y2 = y2;
	}
	
	public long getSpecial() {
		return special;
	}
	public void setSpecial(long special){
		
		this.special = special;
	}
	
	public String toString(){

		return "P1 " + x1 + " " + y1 + " " + "p2 " + x2 + " " + y2 + " special: " + special;
	}
	
	public String drawString() {
	    return "drawMyLine(canvas, " + x1 * 2 + ", " + y1 * 2 + ", " + x2 * 2 + ", " + y2 * 2 + ");";
	}
}
