package com.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import Bean.Bean;

public class CarWarnInfo implements Bean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2344666395803160784L;
	private long id;
	private long car_id;
	private BigDecimal speed;
	private double speed1;
	private BigDecimal drive_distance;
	private double drive_distance1;
	//private String channel;
	private boolean congestion;
	private boolean stop;
	private long road_id;//规划路线
	private Timestamp create_time;
	private Timestamp update_time;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCar_id() {
		return car_id;
	}
	public void setCar_id(long car_id) {
		this.car_id = car_id;
	}
	public BigDecimal getSpeed() {
		return speed;
	}
	public void setSpeed(BigDecimal speed) {
		this.speed = speed;
	}
	public BigDecimal getDrive_distance() {
		return drive_distance;
	}
	public void setDrive_distance(BigDecimal dirive_distance) {
		this.drive_distance = dirive_distance;
	}
//	public String getChannel() {
//		return channel;
//	}
//	public void setChannel(String channel) {
//		this.channel = channel;
//	}
	public boolean isCongestion() {
		return congestion;
	}
	public void setCongestion(boolean congestion) {
		this.congestion = congestion;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	public double getSpeed1() {
		return speed1;
	}

	public void setSpeed1(double speed1) {
		this.speed1 = speed1;
	}

	public double getDrive_distance1() {
		return drive_distance1;
	}

	public void setDirive_distance1(double drive_distance1) {
		this.drive_distance1 = drive_distance1;
	}
	public long getRoad_id() {
		return road_id;
	}
	public void setRoad_id(long road_id) {
		this.road_id = road_id;
	}
	public boolean isStop() {
		return stop;
	}
	public void setStop(boolean stop) {
		this.stop = stop;
	}

}
