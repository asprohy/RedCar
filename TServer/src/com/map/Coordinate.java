package com.map;


public class Coordinate {
    /**
     * 
     */
    private int x;
    private int y;
    private int direction;
    private double speed; 
    private long carId;
    
    public Coordinate() {
	this.x = 0;
	this.y = 0;
	this.direction = 0;
	this.speed = 0;
    }
    
    
    public Coordinate(int x, int y, int direction,double speed) {
	this.x = x;
	this.y = y;
	this.direction = direction;
	this.speed = speed;
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


	public double getSpeed() {
		return speed;
	}


	public void setSpeed(double speed) {
		this.speed = speed;
	}


	public long getCarId() {
		return carId;
	}


	public void setCarId(long carId) {
		this.carId = carId;
	}
    
}
