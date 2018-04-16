package com.entity;

import java.math.BigDecimal;

import Bean.Bean;

public class CarCoordinateInfo implements Bean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1913627066135322696L;
	private long id;
	private BigDecimal coordinate_x;
	private double coordinate_x1;
	private BigDecimal coordinate_y;
	private double coordinate_y1;
	private int direction;
	private long road_id;
	private long hall;
	private String channel;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getCoordinate_x() {
		return coordinate_x.doubleValue();
	}
	public void setCoordinate_x(BigDecimal coordinate_x) {
		this.coordinate_x = coordinate_x;
	}
	public double getCoordinate_x1() {
		return coordinate_x1;
	}
	public void setCoordinate_x1(double coordinate_x1) {
		this.coordinate_x1 = coordinate_x1;
	}
	public double getCoordinate_y() {
		return coordinate_y.doubleValue();
	}
	public void setCoordinate_y(BigDecimal coordinate_y) {
		this.coordinate_y = coordinate_y;
	}
	public double getCoordinate_y1() {
		return coordinate_y1;
	}
	public void setCoordinate_y1(double coordinate_y1) {
		this.coordinate_y1 = coordinate_y1;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public long getRoad_id() {
		return road_id;
	}
	public void setRoad_id(long road_id) {
		this.road_id = road_id;
	}
	public long getHall() {
		return hall;
	}
	public void setHall(long hall) {
		this.hall = hall;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
}
