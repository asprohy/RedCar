package com.car.carState;


public class CarInfo {
    public Point site;
    public int direction;
    
    public CarInfo() {
	this.site = new Point();
	this.direction = 0;
    }
    
    
    public CarInfo(int x, int y, int direction) {
	this.site = new Point(x, y);
	this.direction = direction;
    }
}
