package com.car.carState;


public class CarInfo {
    /**
     * 
     */
    private int x;
    private int y;
    private int direction;
    
    public CarInfo() {
	this.x = 0;
	this.y = 0;
	this.direction = 0;
    }
    
    
    public CarInfo(int x, int y, int direction) {
	this.x = x;
	this.y = y;
	this.direction = direction;
    }


    public int getX() {
        return x;
    }


    public void setX(int x) {
        this.x = x;
    }


    public int getY() {
        return y;
    }


    public void setY(int y) {
        this.y = y;
    }


    public int getDirection() {
        return direction;
    }


    public void setDirection(int direction) {
        this.direction = direction;
    }
    
}
