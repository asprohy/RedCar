package com.lyc.test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.car.entity.Bean;

public class CarState implements Bean{


	/**
	 * 
	 */
	private static final long serialVersionUID = 77975516830049132L;
	private long id;
	private BigDecimal speed; 
	private BigDecimal dirive_distance;
	private double speed1;
	private double dirive_distance1;
	private long history_hall_count;
	private long car_id;
	private long heading_dgree;
	private long send_time;
	private String channel;
	private Timestamp create_time;
	private Timestamp update_time;
	public long getId() {
		return id;
		
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCar_id(int car_id) {
		this.car_id = car_id;
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

	public double getSpeed() {
		return speed.doubleValue();
	}

	public void setSpeed(BigDecimal speed) {
		this.speed = speed;
	}
	

	public double getDirive_distance() {
		return dirive_distance.doubleValue();
	}

	public void setDirive_distance(BigDecimal dirive_distance) {
		this.dirive_distance = dirive_distance;
	}
	


	public long getHistory_hall_count() {
		return history_hall_count;
	}

	public void setHistory_hall_count(long history_hall_count) {
		this.history_hall_count = history_hall_count;
	}

	public long getCar_id() {
		return car_id;
	}

	public void setCar_id(long car_id) {
		this.car_id = car_id;
	}

	public long getSend_time() {
		return send_time;
	}

	public void setSend_time(long send_time) {
		this.send_time = send_time;
	}

	public double getSpeed1() {
		return speed1;
	}

	public void setSpeed1(double speed1) {
		this.speed1 = speed1;
	}

	public double getDirive_distance1() {
		return dirive_distance1;
	}

	public void setDirive_distance1(double dirive_distance1) {
		this.dirive_distance1 = dirive_distance1;
	}

	public long getHeading_gree() {
		return heading_dgree;
	}

	public void setHeading_gree(long heading_gree) {
		this.heading_dgree = heading_gree;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}


	
}
