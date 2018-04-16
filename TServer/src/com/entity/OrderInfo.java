package com.entity;

import java.math.BigDecimal;

import Bean.Bean;
public class OrderInfo implements Bean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3303406077895640086L;

	private long id;
	private long group_id;
	private BigDecimal distance;
	private int speed;
	private int angle;
	private String direction;
	//private String 
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}
	public double getDistance() {
		return distance.doubleValue();
	}
	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getAngle() {
		return angle;
	}
	public void setAngle(int angle) {
		this.angle = angle;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
