package com.entity;

import Bean.Bean;

public class CoordinateXY implements Bean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7558535803641813759L;
	private int x;
	private int y;
	private int direction;
	
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
