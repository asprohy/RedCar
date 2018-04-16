package com.entity;

import java.math.BigDecimal;

import Bean.Bean;

public class CarBendInfo implements Bean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2455466795747511015L;
	private long id;
	private long road_id;
	private long channel;
	private long hall_count;
	private long distance;
	private long x;
	private long y;
	private long direction;
	
	
	public long getRoad_id() {
		return road_id;
	}
	public void setRoad_id(long road_id) {
		this.road_id = road_id;
	}

	public long getHall_count() {
		return hall_count;
	}
	public void setHall_count(long hall_count) {
		this.hall_count = hall_count;
	}
	public long getDistance() {
		return distance;
	}
	public void setDistance(long distance) {
		this.distance = distance;
	}
	public long getX() {
		return x;
	}
	public void setX(long x) {
		this.x = x;
	}
	public long getY() {
		return y;
	}
	public void setY(long y) {
		this.y = y;
	}
	public long getDirection() {
		return direction;
	}
	public void setDirection(long direction) {
		this.direction = direction;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getChannel() {
		return channel;
	}
	public void setChannel(long channel) {
		this.channel = channel;
	}
	
}
